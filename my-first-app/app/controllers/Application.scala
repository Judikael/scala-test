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

object Application extends Controller {

  val itemForm = Form(
    mapping(
      "id" -> of[Long],
      "userId" -> of[Long],
      "name" -> text(minLength = 3),
      "model" -> text(minLength = 3)
    )(Item.apply)(Item.unapply)
  )			

  def javascriptRoutes = Action { implicit request =>
    Ok(
      Routes.javascriptRouter("jsRoutes")(
        routes.javascript.Application.supplierDisplay,
        routes.javascript.Application.supplierDelete,
        routes.javascript.Application.supplierCreate,
        routes.javascript.Application.supplierEdit)).as("text/javascript")
  }

  def supplierEdit(id: Long) = DBAction { implicit rs =>
    val data = ItemDao.getById(id);
    Ok(views.html.supplier(itemForm.fill(data),ItemDao.sortAll))
  } 

  def supplierDisplay = DBAction { implicit rs =>
    Ok(views.html.supplier(itemForm,ItemDao.sortAll))
  } 

  def supplierDelete(id: Long) = DBAction { implicit rs =>
    ItemDao.delete(id)
    Redirect(routes.Application.supplierDisplay()).flashing("success" -> ("Supplier "+id+" deleted!"))
  } 

  def supplierCreate = DBAction { implicit rs =>
    itemForm.bindFromRequest.fold(
    formWithErrors => { 
      BadRequest(views.html.supplier(formWithErrors,ItemDao.sortAll)) 
    },
    supplier => {
      if (supplier.id == -1) {
    	  ItemDao.save(supplier)
      } else {
    	  ItemDao.update(supplier)
      }
      Redirect(routes.Application.supplierDisplay()).flashing("success" -> "Supplier saved!")
    }
  )
  }

}