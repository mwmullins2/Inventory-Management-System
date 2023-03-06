package mwmullinswgu.apphomescreen;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/** The Main Application~~~FUTURE ENHANCEMENT~~~.
 *
 *      A future enhancement that would extend the functionality the application would be to adda feature that would all the user to quickly remove associated parts from the main screen.
 *    As the application is right now, the user would have to enter into the modify product form and remove the associated parts one at a time.
 *    This feature could be a confirmation alert that lists all the parts that are associated with the product.
 *    Then the user could click if they would like to remove the associated parts and delete the product or cancel.
 *
 *
 *  Javadocs folder located in \AppHomeScreen\javadoc
 */
public class InventoryManagement extends Application {

    /** This method sets the primaryStage and calls the programExit method if "X" is clicked.
     *
     * @param primaryStage the primary stage.
     * @throws IOException exception.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Main-Form.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 1100, 400));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            programExit(primaryStage);
        });
  }

    /** This method is used to confirm program exit.
     *  Displays a confirmation box and asks for exit confirmation.
     *
     * @param stage the stage.
     */
    public void programExit(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Program");
        alert.setHeaderText("Exiting Program");
        alert.setContentText("Are you sure you want to exit the program?");
        alert.showAndWait();
        if(alert.getResult().getText().equals("OK")) {
            stage.close();

        }
    }

    /** The main application method.
     * Launches the application.
     *
     * @param args args.
     */
    public static void main(String[] args) {
        launch();
    }
}
