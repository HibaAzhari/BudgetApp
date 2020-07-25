package budget.model

import scalafx.beans.property.{StringProperty}
import scalafx.collections.ObservableBuffer
import budget.model.Transaction

/**
* Represents the overall budget status
**/
class Overview(val categories : ObservableBuffer[Category]) {
	// Remaining balance (affected by transactions)
	var balance : Double = 0.0

	// Retrieves transactions from all categories in an ObservableBuffer
	def getAllTransactions() : ObservableBuffer[Transaction] = {
		var transactionList = new ObservableBuffer[Transaction]
		for (category <- categories) {
			for (transaction <- category.transactions) {
				transactionList += transaction
			}
		}
		transactionList
	}

	// Recalculates balance after changes
	def update() : Unit = {
		balance = 0.0
		for (category <- categories)
		balance = balance + category.totalValue
	}

	// Returns balance in StringProperty for display
	def balanceAsString() : StringProperty = {
		new StringProperty(balance.toString)
	}

	// Resets budget
	def clear() : Unit = {
		for (category <- categories) {
			category.clear()
		}
		update()
	}

	// Checks if any transactions exist
	def isEmpty() : Boolean = {
		getAllTransactions().length == 0
	}
  }