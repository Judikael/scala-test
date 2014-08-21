package dao

import models._
import scala.slick.jdbc.JdbcBackend
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB

object ItemDao extends TableQuery(new ItemsTable(_)) {
	
	def getById(id: Long)(implicit session: JdbcBackend#Session): Item = {
			return ItemDao.filter(_.id === id).first 
	}
	
	def getAll(implicit session: JdbcBackend#Session): List[Item] = {
			return ItemDao.sortBy(_.name.asc.nullsFirst).list
	}
	
	def delete(id: Long)(implicit session: JdbcBackend#Session) = {
		ItemDao.filter(_.id === id).delete
	} 
	
	def save(supplier: Item)(implicit session: JdbcBackend#Session) = {
	  ItemDao.insert(supplier)
	}
	
	def update(supplier: Item)(implicit session: JdbcBackend#Session) = {
		ItemDao.filter(_.id === supplier.id).update(supplier)
	}
	
}  