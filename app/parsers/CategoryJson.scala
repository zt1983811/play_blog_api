package parsers

import play.api.libs.json._
import model.Category

object CategoryJson {

  def parseSingle(category: Category): JsValue = {
    JsObject(
      "id" -> JsNumber(category.id) ::
      "title" -> JsString(category.title) ::
      "description" -> JsString(category.description) ::
      "date_created" -> JsString(category.createDate.toString()):: 
      "date_updated" -> JsString(category.updateDate.toString())::Nil)
  }
  
  def parseLists(categorys: List[Category]) : JsValue = {
    JsObject("categories" -> JsArray(categorys.map{ category => parseSingle(category) }) ::Nil)
  }

}