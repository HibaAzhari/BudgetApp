package budget.view

import budget.model.{Transaction, Overview}
import budget.Main
import scalafx.scene.control.{TableView, TableColumn, Label, Alert}
import scalafxml.core.macros.sfxml
import scalafx.Includes._
import scalafx.event.ActionEvent
import java.time.LocalDate;

@sfxml
class OverviewController(
  
  private val transactionTable : TableView[Transaction],
  
  private val descriptionColumn : TableColumn[Transaction, String],

  private val amountColumn : TableColumn[Transaction, String],

  private val dateColumn : TableColumn[Transaction, LocalDate],

  private val categoryColumn : TableColumn[Transaction, String],

  private val incomeLabel : Label,
  
  private val foodLabel : Label,

  private val transportLabel : Label,

  private val rentLabel : Label,

  private val leisureLabel :  Label,

  private val  miscellaneousLabel : Label,

  private val  balanceLabel : Label   
    
){
  // initialize Table View display contents model
  def refreshTable() = {
    transactionTable.items = Main.overview.getAllTransactions()
    // initialize columns's cell values
    descriptionColumn.cellValueFactory = {_.value.description}
    amountColumn.cellValueFactory = {_.value.amount}
    dateColumn.cellValueFactory = {_.value.date}
    categoryColumn.cellValueFactory  = {_.value.getCategory} 
  }
  refreshTable()
  showTransactionDetails();
  
  private def showTransactionDetails () = {
    incomeLabel.text <== Main.overview.categories(0).valueAsString()
    foodLabel.text  <== Main.overview.categories(1).valueAsString()
    transportLabel.text    <== Main.overview.categories(2).valueAsString()
    rentLabel.text    <== Main.overview.categories(3).valueAsString()
    leisureLabel.text    <== Main.overview.categories(4).valueAsString()
    miscellaneousLabel.text    <== Main.overview.categories(5).valueAsString()
    balanceLabel.text    <== Main.overview.balanceAsString()
  }

  def handleNewTransaction(action : ActionEvent) = {
    val transaction = new Transaction("Description","0.0","01.01.2020")
    val okClicked = Main.showTransactionEditDialog(transaction);
    if (okClicked) {
      transaction.category.value.addTransaction(transaction)
      Main.overview.update()
      refreshTable()
      showTransactionDetails()
    }
  }
  def handleEditTransaction(action : ActionEvent) = {
    val selectedTransaction = transactionTable.selectionModel().selectedItem.value
    if (selectedTransaction != null) {
      selectedTransaction.category.value.removeTransaction(selectedTransaction)
      if (Main.showTransactionEditDialog(selectedTransaction)) {
        selectedTransaction.category.value.addTransaction(selectedTransaction)
        Main.overview.update()
        refreshTable()
        showTransactionDetails()
      }

    } else {
      val alert = new Alert(Alert.AlertType.Warning){
        initOwner(Main.stage)
        title       = "No Selection"
        headerText  = "No Transaction Selected"
        contentText = "Please select a transaction in the table."
      }.showAndWait()
    }
  }
  def handleDeleteTransaction(action : ActionEvent) = {
    val selectedIndex = transactionTable.selectionModel().selectedIndex.value
    if (selectedIndex >= 0) {
      val transaction = transactionTable.selectionModel().selectedItem.value

      Main.overview.categories(transaction.category.value.id).removeTransaction(transaction)
      Main.overview.update()
      transactionTable.items().remove(selectedIndex);
      showTransactionDetails()
    } 
   }
}