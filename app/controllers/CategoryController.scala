package controllers

import play.api.mvc._
import model.{ CategoryForm, Category }
import services.CategoryService
import parsers.CategoryJson
import play.api.libs.json._
import scala.concurrent.ExecutionContext.Implicits.global
import play.mvc.BodyParser.MultipartFormData
import org.joda.time.DateTime
import scala.concurrent.Future

class CategoryController extends Controller {

  def getAllAction = Action.async {
    CategoryService.getAllCategorys.map(response => Ok(JsObject("content" -> CategoryJson.parseLists(response.toList) :: Nil)))
  }

  def getByIdAction(id: Long) = Action.async {
    CategoryService.getCategory(id).map {
      case Some(category) => Ok(JsObject("content" -> CategoryJson.parseSingle(category) :: Nil))
      case None       => NotFound(JsObject("content" -> JsString("Category does not exist") :: Nil))
    }
  }

  def postAction = Action.async(parse.multipartFormData) { implicit request =>
    CategoryForm.form.bindFromRequest.fold(
      formWithErrors => {
        Future.successful(BadRequest(JsObject("content" -> JsString(formWithErrors.errors.toList.toString()) :: Nil)))
      },
      categoryData => {
        val now = new DateTime()
        val category = Category(0, categoryData.title, categoryData.description, 1, now, now)
        CategoryService.addCategory(category).map(response => Ok(JsObject("content" -> JsString(response) :: Nil)))
      })
  }

  def putAction(id: Long) = Action.async(parse.multipartFormData) { implicit request =>
    CategoryForm.form.bindFromRequest.fold(
      formWithErrors => {
        Future.successful(BadRequest(JsObject("content" -> JsString(formWithErrors.errors.toList.toString()) :: Nil)))
      },
      categoryData => {
        val now = new DateTime()
        val category = Category(id, categoryData.title, categoryData.description, 1, now, now)
        CategoryService.updateCategory(category).map {
          case 0 => NotFound(JsObject("content" -> JsString("Category does not exist") :: Nil))
          case 1 => Ok(JsObject("content" -> JsString(s"Category id $id has been update") :: Nil))
        }
      })
  }

  def deleteByIdAction(id: Long) = Action.async {
    CategoryService.deleteCategory(id) map {
      case 0 => NotFound(JsObject("content" -> JsString("Category does not exist") :: Nil))
      case 1 => Ok(JsObject("content" -> JsString(s"Category id $id has been deleted") :: Nil))
    }
  }

}