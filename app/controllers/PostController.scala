package controllers

import play.api.mvc._
import model.{ PostForm, Post }
import services.PostService
import parsers.PostJson
import play.api.libs.json._
import scala.concurrent.ExecutionContext.Implicits.global
import play.mvc.BodyParser.MultipartFormData
import org.joda.time.DateTime
import scala.concurrent.Future

class PostController extends Controller {

  def getAction(id: Long) = Action.async {
    PostService.getPost(id).map {
      case Some(post) => Ok(JsObject("content" -> PostJson.parseSingle(post) :: Nil))
      case None       => NotFound(JsObject("content" -> JsString("Post does not exist") :: Nil))
    }
  }

  def postAction = Action.async(parse.multipartFormData) { implicit request =>
    PostForm.form.bindFromRequest.fold(
      formWithErrors => {
        Future.successful(BadRequest("Errors"))
      },
      postData => {
        val now = new DateTime()
        val post = Post(0, postData.title, postData.content, 1, now, now)
        PostService.addPost(post).map(response => Ok(JsObject("content" -> JsString(response) :: Nil)))
      })
  }

  def putAction(id: Long) = Action { request =>
    NotImplemented
  }

  def deleteAction(id: Long) = Action.async {
    PostService.deletePost(id) map {
      case 0 => NotFound(JsObject("content" -> JsString("Post does not exist") :: Nil))
      case 1 => Ok(JsObject("content" -> JsString(s"Post id $id has been deleted") :: Nil))
    }
  }

}