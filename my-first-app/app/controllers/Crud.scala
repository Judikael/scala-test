package controllers

import play.api._
import play.api.mvc._
import dao._
import play.api.db.slick._
import models.Item
import models.User
import models.ItemWithAll
import play.api.data._
import play.api.data.Forms._
//import play.api.data.validation.Constraints._
import play.api.data.format.Formats._
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB

object Crud extends Controller {

  val itemForm = Form(
    mapping(
      "id" -> longNumber,
      "userId" -> longNumber.verifying("An user must be selected", {_ > 0}),
      "parentItemId" -> optional(longNumber),
      "name" -> text(minLength = 3),
      "model" -> text(minLength = 3)
    )(Item.apply)(Item.unapply)
  )			

  def itemEdit(id: Long) = DBAction { implicit rs =>
    val itemLoaded = ItemDao.getUserWithAll(id);
    Ok(views.html.crudItem(itemForm.fill(itemLoaded.item),Some(itemLoaded),ItemDao.sortAll))
  } 

  def itemDisplay = DBAction { implicit rs =>
    Ok(views.html.crudItem(itemForm,None,ItemDao.sortAll))
  } 

  def itemDelete(id: Long) = DBAction { implicit rs =>
    ItemDao.delete(id)
    Redirect(routes.Crud.itemDisplay()).flashing("success" -> ("Supplier "+id+" deleted!"))
  } 

  def itemCreate = DBAction { implicit rs =>
    // Print POST data 
    // println(rs.body.asFormUrlEncoded)
    
    itemForm.bindFromRequest.fold(
      formWithErrors => { 
        var itemLoaded: Option[ItemWithAll] = None  
        
        // Do we need to get additional information
        if (formWithErrors("userId").value.isDefined || formWithErrors("parentItemId").value.isDefined) {
        	// Fill aditional info from the user selected if possible
        	val user = formWithErrors("userId").value match {
        	case Some("") | None => User(-1,"","")
        	  case Some(userId) => UserDao.getById(userId.toLong)
        	}
        	val parentItem = formWithErrors("parentItemId").value match {
        	case Some("") | None => None
        	  case Some(parentItemId) => Some(ItemDao.getById(parentItemId.toLong))
        	}
        	val item = Item(-1, -1, None, "", "")
        	
          itemLoaded = Some(ItemWithAll(item,user,parentItem))
        }
        BadRequest(views.html.crudItem(formWithErrors,itemLoaded,ItemDao.sortAll)) 
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