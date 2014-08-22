/**
 *
 */
package models

// Importing the slick driver
import play.api.db.slick.Config.driver.simple._

case class User(var id: Long, var login: String, var security: String) extends Identity

class UsersTable(tag: play.api.db.slick.Config.driver.simple.Tag) extends IdentityTable[User](tag, "USERS") {
  
  def login = column[String]("LOGIN")
  def security = column[String]("SECURITY")
  
  def * = (id, login, security) <> (User.tupled, User.unapply)
}