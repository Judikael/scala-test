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
  
  
//  def tupleToItem(itemTuple: (Option[Long], Option[Long], Option[Long], Option[String], Option[String])): Option[Item]= {
//    itemTuple match {
//      case (Some(id), Some(userId), parentItemId, Some(name), Some(model)) => Some(Item(id, userId, parentItemId, name, model))
//      case (None,_,_,_,_) => None
//    }
//  }
//  
//  def itemToTuple(item: Option[Item]): (Option[Long], Option[Long], Option[Long], Option[String], Option[String]) = {
//    item match {
//      case Some(item)=> (Some(item.id), Some(item.userId), item.parentItemId, Some(item.name), Some(item.model))
//      case None => (None, None, None, None, None)
//    } 
//  }
//  def maybe = (id.?, userId.?, parentItemId, name.?, model.?) <> ((toto) => tupleToItem(toto), (order: Option[Item]) => None)
}

/**
 * Item and all associate object
 */
case class ItemWithAll(item: Item, user: User, parentItem: Option[Item])