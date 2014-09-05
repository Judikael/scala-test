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
  
  def getUserWithAll(userId: Long)(implicit session: JdbcBackend#Session): ItemWithAll= {
    val q = tableQuery.filter(_.id === userId)
    ItemWithAll.tupled(q.join(UserDao.tableQuery).on(_.userId === _.id).first)
  }
  
}

object TagDao extends GenericDao[models.Tag, TagsTable] {
  
  // Initialize the tableQuery
  val tableQuery = TableQuery[TagsTable]
  
}

object UserDao extends GenericDao[User, UsersTable] {

  // Initialize the tableQuery
  val tableQuery = TableQuery[UsersTable]

  def search(query: String)(implicit session: JdbcBackend#Session): List[User] = {
    var q = tableQuery.filter(i => i.login like "%"+query+"%")
    if (query forall Character.isDigit) {
    	q = tableQuery.filter(i => (i.id === query.toLong) || (i.login like "%"+query+"%"))
    }
    q.sortBy(_.login.asc.nullsFirst).list
  }
}

object ValueDao extends GenericDao[Value, ValuesTable] {

  // Initialize the tableQuery
  val tableQuery = TableQuery[ValuesTable]

}
