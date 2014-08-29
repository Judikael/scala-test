/**
 *
 */
package models

// Importing the slick driver
import play.api.db.slick.Config.driver.simple._
import dao.UserDao
import dao.ItemDao

/**
 * Item
 */
case class Item(var id: Long, var userId: Long, var parentItemId: Option[Long], var name: String, var model: String) extends Identity

/**
 * Item database mapping
 */
class ItemsTable(tag: play.api.db.slick.Config.driver.simple.Tag) extends IdentityTable[Item](tag, "ITEMS") {
  
  def userId = column[Long]("USER_ID")
  def parentItemId = column[Option[Long]]("PARENT_ITEM_ID")
  def name = column[String]("NAME")
  def model = column[String]("MODEL")

  def user = foreignKey("ITEMS_USERS_FK",userId, UserDao.tableQuery)(_.id)
  def parentItem = foreignKey("ITEMS_ITEMS_FK",parentItemId, ItemDao.tableQuery)(_.id)
  
  def * = (id, userId, parentItemId, name, model) <> (Item.tupled, Item.unapply)
}