package service

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}
import dto.{GetCategory, PostCategory}
import repository.CategoryRepository

@Singleton()
class CategoryService @Inject()(categoryRepository: CategoryRepository)(
    implicit executionContext: ExecutionContext) {

  def getAll: Future[Seq[GetCategory]] = {
    categoryRepository.all().map(_.map(GetCategory.categoryToGetCategory))
  }

  def get(id: Long): Future[Option[GetCategory]] = {
    categoryRepository.get(id).map(_.map(GetCategory.categoryToGetCategory))
  }

  def save(processorFull: PostCategory): Future[GetCategory] = {
    categoryRepository
      .insert(processorFull)
      .map(GetCategory.categoryToGetCategory)
  }

  def delete(id: Long): Future[Int] = {
    categoryRepository.delete(id)
  }

  def update(id: Long, processorFull: PostCategory): Future[Int] = {
    categoryRepository.update(id, processorFull)
  }
}
