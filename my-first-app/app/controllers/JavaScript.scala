package controllers

import play.api._
import play.api.mvc._
import dao._
import play.api.db.slick._
import models._
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB
import play.api.libs.json._
import play.api.libs.functional.syntax._
import views.utils.JSONConverter._

object JavaScript extends Controller {

    def javascriptRoutes = Action { implicit request =>
    Ok(
      Routes.javascriptRouter("jsRoutes")(
        routes.javascript.JavaScript.listUsers,
        routes.javascript.JavaScript.getUser,
        routes.javascript.JavaScript.searchUsers
        )
      ).as("text/javascript")
  }
    
  def listUsers = DBAction { implicit rs =>
    val json = Json.toJson(UserDao.getAll)
    Ok(json)
  }
  
  def getUser(id: Long) = DBAction { implicit rs =>
	  val json = Json.toJson(UserDao.getById(id))
	  Ok(json)
  }
  
  def searchUsers(query: String) = DBAction { implicit rs =>
  	val json = Json.toJson(UserDao.search(query))
  	Ok(json)
  }
  
  def listTags = DBAction { implicit rs =>
    val json = Json.toJson(TagDao.getAll)
    Ok(json)
  }
  

}