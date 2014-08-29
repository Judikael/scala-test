/**
 *
 */
package models

// Importing the slick driver
import play.api.db.slick.Config.driver.simple._
import dao.TagDao
import dao.ItemDao

/**
 * Value
 */
case class Value(var id: Long, var itemId: Long, var tagId: Long, var value: String, var model: String) extends Identity

/**
 * Value database mapping
 */
class ValuesTable(tag: play.api.db.slick.Config.driver.simple.Tag) extends IdentityTable[Value](tag, "VALUES") {
  
	def itemId = column[Long]("ITEM_ID")
	def tagId = column[Long]("TAG_ID")
  def value = column[String]("VALUE")
  def model = column[String]("MODEL")
  
  def item = foreignKey("VALUES_ITEMS_FK",itemId, ItemDao.tableQuery)(_.id)
  def tag = foreignKey("VALUES_TAGS_FK",tagId, TagDao.tableQuery)(_.id)
  
  def * = (id, itemId, tagId, value, model) <> (Value.tupled, Value.unapply)
}