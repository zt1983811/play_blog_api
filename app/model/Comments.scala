package model

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import java.sql.Timestamp
import java.sql.Date
import java.text.SimpleDateFormat
import play.api.Play.current
import play.api.libs.json._
import play.api.libs.functional.syntax._


case class Comment(id: Int, content: String, status: Int, createDate: Date, updateDate: Date)


// Definition of the comment table
class CommentTable(tag: Tag) extends Table[Comment](tag, "comment") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc) // This is the primary key column
  def content = column[String]("content")
  def status = column[Int]("status")
  def createDate = column[Date]("create_date")
  def updateDate = column[Date]("update_date")
  // Every table needs a * projection with the same type as the table's type parameter

  def * = (id, content, status, createDate, updateDate) <> (Comment.tupled, Comment.unapply)
  // A reified foreign key relation that can be navigated to create a join
  //   def supplier = foreignKey("SUP_FK", supID, suppliers)(_.id)
}

object Comments
{

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  val comments = TableQuery[CommentTable]

  def listAll: Future[Seq[Comment]] = {
    dbConfig.db.run(comments.result)
  }

}


