package dao

import play.api.db.slick.Config.driver.simple._
import scala.slick.jdbc.StaticQuery
import scala.slick.lifted.TableQuery
import scala.slick.jdbc.JdbcBackend
import models.Identity
import models.IdentityTable

trait GenericDao[A <: Identity, T <: IdentityTable[A]] {

	val tableQuery: TableQuery[T]

	def getById(id: Long)(implicit session: JdbcBackend#Session): A = {
			return tableQuery.filter(_.id === id).first 
	}
	
	def getAll(implicit session: JdbcBackend#Session): List[A] = {
			return tableQuery.list
	}  
	
	def delete(id: Long)(implicit session: JdbcBackend#Session) = {
		tableQuery.filter(_.id === id).delete
	} 
	
	def save(supplier: A)(implicit session: JdbcBackend#Session) = {
	  tableQuery.insert(supplier)
	}
	
	def update(supplier: A)(implicit session: JdbcBackend#Session) = {
		tableQuery.filter(_.id === supplier.id).update(supplier)
	}

//	/**
//	 * To dynamically generate ids for insert, use sequences (they are created when schema is created).
//	 */
//	def getNextId(seqName: String)(implicit session: Session) =
//	  (StaticQuery[Int] + "select nextval('" + seqName + "_seq') ").first
//
//	/**
//	 * Find a specific entity by id.
//	 */
//	def findById(id: Int)(implicit session: Session): Option[A] = {
//	  val byId = tableQuery.findBy(_.id)
//		byId(id).list.headOption
//  }
//  
//  /**
//   * Delete a specific entity by id. If successfully completed return true, else false
//   */
//  def delete(id: Int)(implicit session: Session): Boolean =
//    findById(id) match {
//      case Some(entity) => { tableQuery.where(_.id === id).delete; true }
//      case None => false
//    }
//  
//  /**
//   * Update a specific entity by id. If successfully completed return true, else false
//   */
//  def update(id: Int, entity: A)(implicit session: Session): Boolean = {
//  	findById(id) match {
//  	  case Some(e) => { tableQuery.where(_.id === id).update(entity); true }
//  	  case None => false
//  	}
//  }
	
}