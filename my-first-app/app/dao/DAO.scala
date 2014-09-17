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
  
  def getUserWithAll(userId: Long)(implicit session: JdbcBackend#Session): ItemWithAll = {
    
    val q = for {
      ((item, user), parentItem) <- tableQuery filter(_.id === userId) leftJoin(UserDao.tableQuery) on(_.userId === _.id) leftJoin(ItemDao.tableQuery) on(_._1.parentItemId === _.id)
      // FIXME Replace by a projection which allow parentItem to be null
    } yield (item, user, parentItem.id.?, parentItem.userId.?, parentItem.parentItemId, parentItem.name.?, parentItem.model.?)
    val result = q.first
    
    // FIXME Replace by a projection which allow parentItem to be null
    val parentOfItem = result._3 match {
      case Some(id) => Some(Item(result._3.get, result._4.get, result._5, result._6.get, result._7.get))
      case None => None
    }
    
    ItemWithAll(result._1,result._2, parentOfItem)
  }
  
  def search(query: String)(implicit session: JdbcBackend#Session): List[Item] = {
    var q = tableQuery.filter(i => i.name.toLowerCase like "%"+query.toLowerCase+"%")
    if (query forall Character.isDigit) {
    	q = tableQuery.filter(i => (i.id === query.toLong) || (i.name.toLowerCase like "%"+query.toLowerCase+"%"))
    }
    q.sortBy(_.name.asc.nullsFirst).list
  }

}

object TagDao extends GenericDao[models.Tag, TagsTable] {
  
  // Initialize the tableQuery
  val tableQuery = TableQuery[TagsTable]
  
  def search(query: String)(implicit session: JdbcBackend#Session): List[models.Tag] = {
    var q = tableQuery.filter(i => i.name.toLowerCase like "%"+query.toLowerCase+"%")
    if (query forall Character.isDigit) {
    	q = tableQuery.filter(i => (i.id === query.toLong) || (i.name.toLowerCase like "%"+query.toLowerCase+"%"))
    }
    q.sortBy(_.name.asc.nullsFirst).list
  }
}

object UserDao extends GenericDao[User, UsersTable] {

  // Initialize the tableQuery
  val tableQuery = TableQuery[UsersTable]

  def search(query: String)(implicit session: JdbcBackend#Session): List[User] = {
    var q = tableQuery.filter(i => i.login.toLowerCase like "%"+query.toLowerCase+"%")
    if (query forall Character.isDigit) {
    	q = tableQuery.filter(i => (i.id === query.toLong) || (i.login.toLowerCase like "%"+query.toLowerCase+"%"))
    }
    q.sortBy(_.login.asc.nullsFirst).list
  }
}

object ValueDao extends GenericDao[Value, ValuesTable] {

  // Initialize the tableQuery
  val tableQuery = TableQuery[ValuesTable]

}
