package controllers

import akka.actor.ActorSystem
import javax.inject._
import play.api._
import play.api.libs.json._
import play.api.mvc._
import model.{Comments, Comment}
import java.sql.Timestamp
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.concurrent.duration._



@Singleton
class CommentController @Inject() (actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends Controller {
  def list() = Action.async {  
    implicit val commentFormat = Json.format[Comment]   
    Comments.listAll.map { comments => Ok(Json.toJson(comments)) }
  }
}
