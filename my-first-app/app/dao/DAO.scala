package dao

import models._
import scala.slick.jdbc.JdbcBackend
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB

object ItemDao extends GenericDao[Item, ItemsTable] {
  
  // Initialize the tableQuery
  val tableQuery = TableQuery[ItemsTable]
  
  // Specific method
	def sortAll(implicit session: JdbcBackend#Session): List[Item] = {
			return tableQuery.sortBy(_.name.asc.nullsFirst).list
	}  
  
}

object TagDao extends GenericDao[models.Tag, TagsTable] {
  
  // Initialize the tableQuery
  val tableQuery = TableQuery[TagsTable]
  
}

object UserDao extends GenericDao[User, UsersTable] {

  // Initialize the tableQuery
  val tableQuery = TableQuery[UsersTable]

}

object ValueDao extends GenericDao[Value, ValuesTable] {

  // Initialize the tableQuery
  val tableQuery = TableQuery[ValuesTable]

}
