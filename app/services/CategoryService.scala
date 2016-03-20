package services

import model.{ Category, Categories }
import scala.concurrent.Future

object CategoryService {

  def addCategory(category: Category): Future[String] = {
    Categories.add(category)
  }

  def deleteCategory(id: Long): Future[Int] = {
    Categories.delete(id)
  }

  def getCategory(id: Long): Future[Option[Category]] = {
    Categories.get(id)
  }

  def getAllCategorys: Future[Seq[Category]] = {
    Categories.listAll
  }

  def updateCategory(category: Category): Future[Int] = {
    Categories.update(category)
  }

}