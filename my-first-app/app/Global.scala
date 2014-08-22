import play.api.db.DB
import play.api.GlobalSettings
// Use H2Driver to connect to an H2 database
import play.api.db.slick.Config.driver.simple._

import play.api.Application
import play.api.Play.current

import models._
import dao._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    InitialData.insert()
  }

  object InitialData {  
    
    def createDdl() = {
      play.api.db.slick.DB.withSession {
        implicit session: Session =>
          ItemDao.tableQuery.ddl.create(session)
      }
    }
    
    def insert() = {
 
      play.api.db.slick.DB.withSession {
        implicit session: Session =>

          // Insert only if not already inserted
          
          var numberOfSuppliers = ItemDao.tableQuery.length.run
          
          if (numberOfSuppliers == 0) 
            ItemDao.tableQuery ++= Seq(
              (new Item(-1,-1,"Name 1","Model 1")),
              (new Item(-1,-1,"Name 2","Model 2")),
              (new Item(-1,-1,"Name 3","Model 3")),
              (new Item(-1,-1,"Name 4","Model 4"))
            )
          
      }
      
    }
    
  }
}
	