package budget.view

import budget.model.Transaction
import budget.Main
import scalafx.scene.control.{TextField, ChoiceBox, Alert}
import scalafxml.core.macros.sfxml
import scalafx.collections.ObservableBuffer
import scalafx.stage.Stage
import scalafx.Includes._
import budget.util.DateUtil._
import java.time.format.DateTimeFormatter;
import budget.util.NumberUtil._
import scalafx.event.ActionEvent

@sfxml
class TransactionEditDialogController (

    private val  descriptionField : TextField,
    private val       amountField : TextField,
    private val         dateField : TextField,
    private val    categoryChoice : ChoiceBox[String]

){
  
  // Set up ChoiceBox
  // Retrieve category names
  var categories = new ObservableBuffer[String]()
  for (category <- Main.overview.categories)
    categories += category.desc
  // Assign as category choice box options
  categoryChoice.items = categories

  var dialogStage : Stage = null
  var okClicked = false
  private var _transaction : Transaction = null

  // Display selected transaction info in dialog
  def transaction = _transaction
  def transaction_=(t : Transaction) {
        _transaction = t

        descriptionField.text = _transaction.description.value
        amountField.text  = _transaction.amount.value.toString
        dateField.text  = _transaction.date.value.asString
        dateField.setPromptText("dd.mm.yyyy");
        categoryChoice.getSelectionModel.select(_transaction.category.value.id)
  }

  // Called when Ok clicked in dialog
  def handleOk(action :ActionEvent){

    // Valid input > update transaction info
    if (validInput()) {
      transaction.description <== descriptionField.text
      transaction.amount <== amountField.text
      transaction.date.value = dateField.text.value.parseLocalDate
      transaction.category.value = Main.overview.categories(categoryChoice.selectionModel().selectedIndex.value)
      
      okClicked = true;
      dialogStage.close()
    }
  }

  // Cancel clicked > no changes
  def handleCancel(action :ActionEvent) {
        dialogStage.close();
  }

  def isNull (x : String) = x == null || x.length == 0

  // Function for input validation
  def validInput() : Boolean = {
    var error = ""

    // Description cannot be null
    if (isNull(descriptionField.text.value))
      error += "No Description Entered\n"

    // Amount must be parsable to double and non-negative
    if (isNull(amountField.text.value))
      error += "No Amount Entered\n"
    else {
      try {
        val input = parseDouble(amountField.getText());
        if (input.get < 0) 
          throw new NumberFormatException
      } catch {
          case e : NumberFormatException =>
            error += "Amount must be a positive number\n"
      }
    }

    // Date must be parsable to LocalDate
    if (isNull(dateField.text.value))
      error += "No Date Entered\n"
    else {
      if (!dateField.text.value.isValid) {
          error += "Invalid Date Format (Use dd.mm.yyyy)\n";
      }
    }

    // If an error was found, display alert
    if (error.length() == 0) {
        return true;
    } else {
        val alert = new Alert(Alert.AlertType.Error){
          initOwner(dialogStage)
          title = "Invalid Input"
          headerText = "Please check your input values"
          contentText = error
        }.showAndWait()

        return false;
    }
   }
}