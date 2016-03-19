package controllers

import javax.inject._
import play.api._
import play.api.libs.json._
import play.api.mvc._
import model.{Comments, Comment}
import java.sql.Timestamp


@Singleton
class CommentController @Inject() extends Controller {
  def list() = Action.async {  
    implicit val commentFormat = Json.format[Comment]   
    Comments.listAll.map { comments => Ok(Json.toJson(comments)) }
  }
}
