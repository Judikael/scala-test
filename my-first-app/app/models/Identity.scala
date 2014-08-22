package models

import play.api.db.slick.Config.driver.simple._

trait Identity {
  def id: Long
}

abstract class IdentityTable[A <: Identity](_tableTag: play.api.db.slick.Config.driver.simple.Tag, _schemaName: Option[String], _tableName: String) extends Table[A](_tableTag, _schemaName, _tableName) {
	def this(_tableTag: play.api.db.slick.Config.driver.simple.Tag, _tableName: String) = this(_tableTag, None, _tableName)
	def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
}