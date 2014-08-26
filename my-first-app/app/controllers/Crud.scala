package controllers

import play.api._
import play.api.mvc._
import dao._
import play.api.db.slick._
import models.Item
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB

object Crud extends Controller {

  val itemForm = Form(
    mapping(
      "id" -> longNumber,
      "userId" -> longNumber,
      "parentItemId" -> optional(longNumber),
      "name" -> text(minLength = 3),
      "model" -> text(minLength = 3)
    )(Item.apply)(Item.unapply)
  )			

  def javascriptRoutes = Action { implicit request =>
    Ok(
      Routes.javascriptRouter("jsRoutes")(
        routes.javascript.Crud.itemDisplay,
        routes.javascript.Crud.itemDelete,
        routes.javascript.Crud.itemCreate,
        routes.javascript.Crud.itemEdit)).as("text/javascript")
  }

  def itemEdit(id: Long) = DBAction { implicit rs =>
    val data = ItemDao.getById(id);
    Ok(views.html.crudItem(itemForm.fill(data),ItemDao.sortAll))
  } 

  def itemDisplay = DBAction { implicit rs =>
    Ok(views.html.crudItem(itemForm,ItemDao.sortAll))
  } 

  def itemDelete(id: Long) = DBAction { implicit rs =>
    ItemDao.delete(id)
    Redirect(routes.Crud.itemDisplay()).flashing("success" -> ("Supplier "+id+" deleted!"))
  } 

  def itemCreate = DBAction { implicit rs =>
    itemForm.bindFromRequest.fold(
    formWithErrors => { 
      BadRequest(views.html.crudItem(formWithErrors,ItemDao.sortAll)) 
    },
    item => {
      if (item.id == -1) {
    	  ItemDao.save(item)
      } else {
    	  ItemDao.update(item)
      }
      Redirect(routes.Crud.itemDisplay()).flashing("success" -> "Saved!")
    }
  )
  }

}