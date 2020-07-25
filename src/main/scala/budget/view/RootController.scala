package budget.view

import budget.Main
import scalafx.scene.control.{Alert, ButtonType}
import scalafxml.core.macros.sfxml

@sfxml
class RootController(){
    
    // Called when "Options > Clear All" menu option clicked
    // Gets confirmation from user through alert
    // On confirmation: resets budget
    def handleClear() {
        
        // If no transactions found, inform user
        if (Main.overview.isEmpty) {
            new Alert(Alert.AlertType.Information, "No Transactions Found!").showAndWait()
        } 
        
        // Else: ask for confirmation
        else {
            val alert : Alert = new Alert(Alert.AlertType.Confirmation);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure you want to clear all transactions?");
            alert.setContentText("This action is irreversible!");

            val result = alert.showAndWait();
            // On confirmation: reset
            if (result.get == ButtonType.OK){
                Main.overview.clear()
                Main.showOverview()
            }
        }
    }

    // Called when "Help > About" menu option clicked
    // Displays alert with app info to user
    def handleAbout() {
            new Alert(Alert.AlertType.Information, "This is a Budgeting app! \nYou can record all your spendings and income based on their categories. If you want to reset data, go to:\n\n'Options > Clear All'. \n\n Happy budgeting!").showAndWait()
    }
}