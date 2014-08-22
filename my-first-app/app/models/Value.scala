/**
 *
 */
package models

// Importing the slick driver
import play.api.db.slick.Config.driver.simple._

case class Value(var id: Long, var itemId: Long, var tagId: Long, var value: String, var model: String) extends Identity

class ValuesTable(tag: play.api.db.slick.Config.driver.simple.Tag) extends IdentityTable[Value](tag, "VALUES") {
  
  //def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
	def itemId = column[Long]("ITEM_ID")
	def tagId = column[Long]("TAG_ID")
  def value = column[String]("VALUE")
  def model = column[String]("MODEL")
  
  def * = (id, itemId, tagId, value, model) <> (Value.tupled, Value.unapply)
}