package repository

import slick.jdbc.PostgresProfile.api._
import model.Product
import javax.inject.{Inject, Singleton}

import play.api.db.slick.DatabaseConfigProvider
import repository.table.ProductTable

import scala.concurrent.{ExecutionContext, Future}

@Singleton()
class ProductRepository @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit executionContext: ExecutionContext) {

  val Products = TableQuery[ProductTable]
  val db = dbConfigProvider.get.db

  def all(): Future[Seq[Product]] = db.run(Products.result)

  def get(id: Long): Future[Option[Product]] =
    db.run(Products.filter(_.id === id).result.headOption)

  def insert(products: Seq[Product]): Future[Unit] =
    db.run(Products ++= products).map(_ => ())

  def create(): Future[Unit] = db.run(Products.schema.create)

  def count(): Future[Int] = db.run(Products.length.result)
}
