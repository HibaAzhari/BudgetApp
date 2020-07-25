package budget.model

import budget.util.NumberUtil._
import budget.model.Transaction

/**
* Represents a category of type "SpendingCategory"
**/
class SpendingCategory(id : Int, desc : String) extends Category(id : Int, desc : String) {
    
    // Add transaction to list
    // Subtract from totalValue
    def addTransaction(transaction : Transaction) {
        transactions += transaction
        totalValue = totalValue - parseDouble(transaction.amount.value).get
        transaction.category.value = this
    }

    // Remove transaction from list
    // Add to totalValue
    def removeTransaction(transaction : Transaction) {
        for (i <- 0 until transactions.length) {
            if (transactions(i) == transaction)
            transactions.remove(i)
        }
        totalValue = totalValue + parseDouble(transaction.amount.value).get
    }
}