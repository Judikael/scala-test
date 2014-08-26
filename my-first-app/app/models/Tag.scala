/**
 *
 */
package models

// Importing the slick driver
import play.api.db.slick.Config.driver.simple._

case class Tag(var id: Long, var name: String) extends Identity

class TagsTable(tag: play.api.db.slick.Config.driver.simple.Tag) extends IdentityTable[Tag](tag, "TAGS") {
  
  def name = column[String]("NAME")
  
  def * = (id, name) <> (Tag.tupled, Tag.unapply)
}