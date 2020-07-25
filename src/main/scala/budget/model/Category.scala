package budget.model

import scalafx.collections.ObservableBuffer
import scalafx.beans.property.{StringProperty}
import budget.model.Transaction

/**
* Abstract class represents a category of money transactions
**/
abstract class Category(val id : Int, val desc : String) {
    // Total money in category
    var totalValue : Double = 0.0
    
    // List of all category's transactions
    var transactions : ObservableBuffer[Transaction] = new ObservableBuffer[Transaction]()
    
    // Abstract functions to Add/Remove transaction
    def addTransaction(transaction : Transaction)
    def removeTransaction(transaction : Transaction)
    
    // Get value as StringProperty
    def valueAsString() : StringProperty = {
        new StringProperty(totalValue.toString)
    }
    
    // Reset Category
    def clear() : Unit = {
        totalValue = 0.0
        transactions.clear()
    }
}