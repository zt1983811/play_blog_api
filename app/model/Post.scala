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

case class Post(id: Long, title: String, content: String, status: Int, createDate: DateTime, updateDate: DateTime)

case class PostForm(title: String, content: String)

object PostForm {
  val form = Form(
    mapping(
      "title" -> nonEmptyText,
      "content" -> nonEmptyText)(PostForm.apply)(PostForm.unapply))
}

class PostTable(tag: Tag) extends Table[Post](tag, "post") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def title = column[String]("title")
  def content = column[String]("content")
  def status = column[Int]("status")
  def createDate = column[DateTime]("create_date")
  def updateDate = column[DateTime]("update_date")

  override def * =
    (id, title, content, status, createDate, updateDate) <> (Post.tupled, Post.unapply)

}

object Posts {

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  val posts = TableQuery[PostTable]

  def add(post: Post): Future[String] = {
    dbConfig.db.run(posts += post).map(res => "Post successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def delete(id: Long): Future[Int] = {
    dbConfig.db.run(posts.filter(_.id === id).delete)
  }

  def get(id: Long): Future[Option[Post]] = {
    dbConfig.db.run(posts.filter(_.id === id).result.headOption)
  }

  def update(post: Post): Future[Int] = {
    dbConfig.db.run(posts.filter(_.id === post.id)
        .map(x=> (x.title,x.content,x.updateDate))
        .update((post.title,post.content,post.updateDate)))
  }

  def listAll: Future[Seq[Post]] = {
    dbConfig.db.run(posts.result)
  }

}