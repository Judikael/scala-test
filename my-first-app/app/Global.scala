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
    
//    def createDdl() = {
//      play.api.db.slick.DB.withSession {
//        implicit session: Session =>
//          ItemDao.tableQuery.ddl.create(session)
//      }
//    }
    
    def insert() = {
 
      play.api.db.slick.DB.withSession {
        implicit session: Session =>

          // Insert only if not already inserted
          var numberOfUsers = UserDao.tableQuery.length.run
          if (numberOfUsers == 0) 
        	  UserDao.tableQuery ++= Seq(
        			  (new User(1,"User01","pwd01")),
        			  (new User(2,"User02","pwd02")),
        			  (new User(3,"User03","pwd03")),
        			  (new User(4,"User04","pwd04")),
        			  (new User(5,"Albert","pwd04")),
        			  (new User(6,"Joseft","pwd04")),
        			  (new User(7,"Alberta","pwd04")),
        			  (new User(8,"Benoit","pwd04")),
        			  (new User(9,"David","pwd04")),
        			  (new User(10,"Julie","pwd04")),
        			  (new User(11,"Romain","pwd04")),
        			  (new User(12,"Bertrand","pwd04"))
        			  )
            
            
          // Insert only if not already inserted
          var numberOfSuppliers = ItemDao.tableQuery.length.run
          if (numberOfSuppliers == 0) 
            ItemDao.tableQuery ++= Seq(
              (new Item(-1,1,None,"Name 1","Model 1")),
              (new Item(-1,1,Some(1),"Name 2","Model 2")),
              (new Item(-1,2,Some(1),"Name 3","Model 3")),
              (new Item(-1,2,None,"Name 4","Model 4"))
            )
          
      }
      
    }
    
  }
}
	