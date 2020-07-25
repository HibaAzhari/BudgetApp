package budget.model

import budget.util.NumberUtil._
import budget.model.Transaction

/**
* Represents a category of type "IncomeCategory"
**/
class IncomeCategory(id : Int, desc : String) extends Category(id : Int, desc : String) {
    
    // Add transaction to list
    // Add to totalValue
    def addTransaction(transaction : Transaction) {
        transactions += transaction
        totalValue = totalValue + parseDouble(transaction.amount.value).get //'Income' therefore totalValue increased
        transaction.category.value = this
    }
    
    // Remove transaction from list
    // Subtract from totalValue
    def removeTransaction(transaction : Transaction) {
        for (i <- 0 until transactions.length) {
            if (transactions(i) == transaction)
            transactions.remove(i)
        }
        totalValue = totalValue - parseDouble(transaction.amount.value).get
    }
}