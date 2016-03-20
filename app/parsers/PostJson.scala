package parsers

import play.api.libs.json._
import model.Post

object PostJson {

  def parseSingle(post: Post): JsValue = {
    JsObject(
      "id" -> JsNumber(post.id) ::
      "title" -> JsString(post.title) ::
      "content" -> JsString(post.content) ::
      "date_created" -> JsString(post.createDate.toString()):: 
      "date_updated" -> JsString(post.updateDate.toString())::Nil)
  }
  
  def parseLists(posts: List[Post]) : JsValue = {
    JsObject("posts" -> JsArray(posts.map{ post => parseSingle(post) }) ::Nil)
  }

}