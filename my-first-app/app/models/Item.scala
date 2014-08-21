/**
 *
 */
package models

// Importing the slick driver
import play.api.db.slick.Config.driver.simple._

case class Item(var id: Long, var name: String, var model: String)

class ItemsTable(tag: Tag) extends Table[Item](tag, "ITEMS") {
  
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def name = column[String]("NAME")
  def model = column[String]("MODEL")
  
  def * = (id, name, model) <> (Item.tupled, Item.unapply)
}