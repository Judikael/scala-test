/**
 *
 */
package models

// Importing the slick driver
import play.api.db.slick.Config.driver.simple._

/**
 * Tag
 */
case class Tag(var id: Long, var name: String) extends Identity

/**
 * Tag database mapping
 */
class TagsTable(tag: play.api.db.slick.Config.driver.simple.Tag) extends IdentityTable[Tag](tag, "TAGS") {
  
  def name = column[String]("NAME")
  
  def * = (id, name) <> (Tag.tupled, Tag.unapply)
}