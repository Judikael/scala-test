package views.utils

import models.Item
import scala.collection.immutable.TreeMap 

object TableSectionHelper {

  def getSections(items: List[Item]): Map[Char, List[Item]] = {
    val toto = items.groupBy(_.name.toUpperCase().charAt(0))
    val sortedMap = new TreeMap[Char, List[Item]]
    return sortedMap ++ toto
  }
  
}