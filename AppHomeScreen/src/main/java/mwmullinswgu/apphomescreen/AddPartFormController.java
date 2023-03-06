package mwmullinswgu.apphomescreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** The controller for Add-Part-Form fxml.
 *
 */
public class AddPartFormController implements Initializable {
    /** Label used for inHouseRadio and outSourceRadio selection.
     *
     */
    @FXML
    private Label partSourceLabel;
    /** Used to set a part as InHouse
     *
     */
    @FXML
    private RadioButton inHouseRadio;
    /** Used to set a part as Outsourced
     *
     */
    @FXML
    private RadioButton outsourcedRadio;
    /** Used to enter a part name.
     *
     */
    @FXML
    private TextField nameTextField;
    /** Used to enter stock of a part.
     *
     */
    @FXML
    private TextField invTextField;
    /** Used to enter price of a part.
     *
     */
    @FXML
    private TextField priceTextField;
    /** Used to enter the max stock of a part.
     *
     */
    @FXML
    private TextField maxTextField;
    /** Used to enter the machine ID or the Outsourced Company name.
     *
     */
    @FXML
    private TextField partSourceTextField;
    /** Used to enter the min stock of a part.
     *
     */
    @FXML
    private TextField minTextField;

    /** This is the initialize method.
     *  It runs first.
     * It contains the auto part ID generation method and variable.
     * @param url url.
     * @param resourceBundle rb.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /** Variable used for auto Part ID generation.
     *
     */
    public static int autoPartID = 1;

    /** Used for auto generation of Part IDs.
     *  Returns the autoPartID variable and increments it.
     *
     * @return autoPartID
     */
    public static int autoGenPartID() {

        return autoPartID++;
    }

    /** This method adds a part into Inventory.
     *  Gets the yser entered text and assigns them to a variable.
     *  Contains error handling for the Add Part Form.
     *  Determines if a part is InHouse or Outsourced and adds them appropriately.
     *  Returns to the main screen when a part is added successfully.
     *
     * @param actionEvent Save button clicked.
     * @throws IOException exception.
     */
    public void addPartFormSaveClick(ActionEvent actionEvent) throws IOException {

    try {


        String name = nameTextField.getText();
        int stock = Integer.parseInt(invTextField.getText());
        double price = Double.parseDouble(priceTextField.getText());
     int min = Integer.parseInt(minTextField.getText());
        int max = Integer.parseInt(maxTextField.getText());
        int machineID;
        String companyName;
        boolean partAddedSuccess = false;

        if (min > stock) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory Error");
            alert.setContentText("Inventory amount can not be less than Min!");
            alert.showAndWait();
         } else if (stock > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory Error");
            alert.setContentText("Inventory amount can not be greater than Max!");
            alert.showAndWait();
        } else if (Objects.equals(name, "")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Name");
            alert.setContentText("Part must have a name!");
            alert.showAndWait();
        } else if (inHouseRadio.isSelected()) {
            try {
                machineID = Integer.parseInt(partSourceTextField.getText());
                int id = autoGenPartID();
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
                partAddedSuccess = true;
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Value Error");
                alert.setContentText("Machine ID value must be an Integer");
                alert.showAndWait();
            }

        } else if (outsourcedRadio.isSelected()) {
                companyName = partSourceTextField.getText();
                int id = autoGenPartID();
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                partAddedSuccess = true;
        }
        if (partAddedSuccess) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Part Add Success");
            alert.setContentText("Part Successfully added. Returning to Main Screen.");
            alert.showAndWait();

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main-Form.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1100, 400);
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);

            stage.show();
        }

    }
    catch(Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Field Error");
        alert.setContentText("All fields must contain a valid value!");
        alert.showAndWait();
}
    }

    /** This returns the user to the Main Screen.
     *  A confirmation box appears and if OK is clicked the user is returned to the main screen.
     * @param actionEvent Cancel button clicked.
     * @throws IOException exception.
     */
    public void addPartFormCancelClick(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to cancel? Fields will be cleared and you will be returned to the Main Screen." );
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main-Form.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1100, 400);
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** Used to set the part to InHouse.
     *  Changes the part source label to "Machine ID".
     *
     * @param actionEvent Radio Button Selected.
     */
    public void inHouseRadioSelected(ActionEvent actionEvent) {
        partSourceLabel.setText("Machine ID");
        inHouseRadio.setSelected(true);
    }

    /** Used to set the part to Outsourced.
     *  Changes the part source label to "Company Name".
     *
     * @param actionEvent Radio Button Selected.
     */
    public void outsourcedRadioSelected(ActionEvent actionEvent) {
        partSourceLabel.setText("Company Name");
        outsourcedRadio.setSelected(true);
    }

}