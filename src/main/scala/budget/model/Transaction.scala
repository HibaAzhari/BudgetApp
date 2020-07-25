package budget.model

import scalafx.beans.property.{StringProperty, ObjectProperty}
import java.time.LocalDate;
import budget.util.DateUtil._

/**
* Represents a single money transaction
**/
case class Transaction ( desc : String, amnt : String, day : String){

  // Transaction details:
	var description  = new StringProperty(desc)

  var amount   = new StringProperty(amnt) 

	var date = ObjectProperty[LocalDate](day.parseLocalDate)

  var category = ObjectProperty[Category](new SpendingCategory(0,""))

  // Retrieves category description for display
  def getCategory() : StringProperty = {
    new StringProperty(category.value.desc)
  }
}