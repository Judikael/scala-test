/**
 *
 */
package models

// Importing the slick driver
import play.api.db.slick.Config.driver.simple._
//import dao.Dao

case class Item(var id: Long, var userId: Long, var name: String, var model: String) extends Identity

class ItemsTable(tag: play.api.db.slick.Config.driver.simple.Tag) extends IdentityTable[Item](tag, "ITEMS") {
  
  //def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def userId = column[Long]("USER_ID")
  def name = column[String]("NAME")
  def model = column[String]("MODEL")

  //def user = foreignKey("USERS",userId,new UsersTable(tag))(_.id)
  
  def * = (id, userId, name, model) <> (Item.tupled, Item.unapply)
}