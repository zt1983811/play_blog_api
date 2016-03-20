package model

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import org.joda.time.DateTime
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import com.github.tototoshi.slick.MySQLJodaSupport._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.data._
import play.api.data.Forms._

case class Category(id: Long, title: String, description: String, status: Int, createDate: DateTime, updateDate: DateTime)

case class CategoryForm(title: String, description: String)

object CategoryForm {
  val form = Form(
    mapping(
      "title" -> nonEmptyText,
      "description" -> nonEmptyText)(CategoryForm.apply)(CategoryForm.unapply))
}

class CategoryTable(tag: Tag) extends Table[Category](tag, "category") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def title = column[String]("title")
  def description = column[String]("description")
  def status = column[Int]("status")
  def createDate = column[DateTime]("create_date")
  def updateDate = column[DateTime]("update_date")

  override def * =
    (id, title, description, status, createDate, updateDate) <> (Category.tupled, Category.unapply)

}

object Categories {

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  val categories = TableQuery[CategoryTable]

  def add(category: Category): Future[String] = {
    dbConfig.db.run(categories += category).map(res => "Category successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def delete(id: Long): Future[Int] = {
    dbConfig.db.run(categories.filter(_.id === id).delete)
  }

  def get(id: Long): Future[Option[Category]] = {
    dbConfig.db.run(categories.filter(_.id === id).result.headOption)
  }

  def update(category: Category): Future[Int] = {
    dbConfig.db.run(categories.filter(_.id === category.id)
        .map(x=> (x.title,x.description,x.updateDate))
        .update((category.title,category.description,category.updateDate)))
  }

  def listAll: Future[Seq[Category]] = {
    dbConfig.db.run(categories.result)
  }

}