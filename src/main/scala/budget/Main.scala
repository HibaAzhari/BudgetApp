package budget

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.image.Image
import scalafx.Includes._
import scalafxml.core.{NoDependencyResolver, FXMLView, FXMLLoader}
import javafx.{scene => jfxs}
import scalafx.collections.{ObservableBuffer}
import budget.model.Transaction
import budget.view.TransactionEditDialogController
import budget.model._
import scalafx.stage.{ Stage, Modality }

object Main extends JFXApp {

  // Initialize observable list of all categories
  val categories = new ObservableBuffer[Category]()
    categories += new IncomeCategory(0,"Salary")
    categories += new SpendingCategory(1,"Food")
    categories += new SpendingCategory(2,"Transport")
    categories += new SpendingCategory(3,"Rent")
    categories += new SpendingCategory(4,"Leisure")
    categories += new SpendingCategory(5,"Miscellaneous")

  // Instantiate an 'Overview'
  val overview = new Overview(categories)

  // Add sample transactions to random category in overview
  overview.categories(0).addTransaction(new Transaction("Month-1","3500.0","01.01.2020")) //transaction added to 'Salary' category
  overview.categories(2).addTransaction(new Transaction("Taxi","20.0","09.01.2020")) //transaction added to 'Transport' category
  overview.categories(1).addTransaction(new Transaction("McD","12.5","15.01.2020")) //transaction added to 'Food' category
  overview.update()
  
  // Transform path of RootLayout.fxml to URI for resource location
  val rootResource = getClass.getResourceAsStream("view/RootLayout.fxml")
  // Initialize loader object
  val loader = new FXMLLoader(null, NoDependencyResolver)
  // Load root layout from fxml file
  loader.load(rootResource);
  // From FXML: retrieve root component BorderPane
  val roots = loader.getRoot[jfxs.layout.BorderPane]
  // Initialize stage
  stage = new PrimaryStage {
    title = "Budget App"
    icons.add(new Image("budget/icons/icon.png"))
    scene = new Scene {
      root = roots
    }
  }
  

  def showOverview() = {
    val resource = getClass.getResourceAsStream("view/Overview.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  } 
  
  def showTransactionEditDialog(transaction: Transaction): Boolean = {
    val resource = getClass.getResourceAsStream("view/TransactionEditDialog.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2  = loader.getRoot[jfxs.Parent]
    val control = loader.getController[TransactionEditDialogController#Controller]

    val dialog = new Stage() {
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        root = roots2
      }
    }
    control.dialogStage = dialog
    control.transaction = transaction
    dialog.showAndWait()
    control.okClicked
  } 

  // Display Overview on app launch
  
  
  showOverview()
}