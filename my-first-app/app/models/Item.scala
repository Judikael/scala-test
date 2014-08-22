/**
 *
 */
package models

// Importing the slick driver
import play.api.db.slick.Config.driver.simple._
import dao.UserDao

case class Item(var id: Long, var userId: Long, var name: String, var model: String) extends Identity

class ItemsTable(tag: play.api.db.slick.Config.driver.simple.Tag) extends IdentityTable[Item](tag, "ITEMS") {
  
  def userId = column[Long]("USER_ID")
  def name = column[String]("NAME")
  def model = column[String]("MODEL")

  def user = foreignKey("USERS",userId, UserDao.tableQuery)(_.id)
  
  def * = (id, userId, name, model) <> (Item.tupled, Item.unapply)
}