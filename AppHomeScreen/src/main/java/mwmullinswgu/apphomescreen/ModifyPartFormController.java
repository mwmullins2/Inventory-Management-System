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
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller for Modify-Part-Form fxml
 *
 */
public class ModifyPartFormController implements Initializable {

    /** Used to show MachineID or Company Name.
     *
     */
    @FXML
    private Label partSourceLabel;
    /** Used to set a part as InHouse.
     *
     */
    @FXML
    private RadioButton inHouseRadio;
    /** Used to set a part as Outsourced.
     *
     */
    @FXML
    private RadioButton outsourcedRadio;
    /** Used to display the PartID.
     *
     */
    @FXML
    private TextField iDTextField;
    /** Used to display and edit the part name.
     *
     */
    @FXML
    private TextField nameTextField;
    /** Used to display and edit the part stock.
     *
     */
    @FXML
    private TextField invTextField;
    /** Used to display and edit the part price.
     *
     */
    @FXML
    private TextField priceTextField;
    /** Used to display and edit the part max stock.
     *
     */
    @FXML
    private TextField maxTextField;
    /** Used to display and edit the part machineID or Company Name.
     *
     */
    @FXML
    private TextField partSourceTextField;
    /**  Used to display and edit the part min stock.
     *
     */
    @FXML
    private TextField minTextField;

    /** This is the intialize method.
     *  This runs first.
     *  This sets the text within the text fields.
     *  Determines if a part is InHouse or Outsourced and pulls the correct part info.
     *
     * @param url url.
     * @param resourceBundle rb.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");
        Part partToModify = MainFormController.selectedPartToModify();


        iDTextField.setText(Integer.toString(partToModify.getId()));
        nameTextField.setText(partToModify.getName());
        invTextField.setText(Integer.toString(partToModify.getStock()));
        priceTextField.setText(Double.toString(partToModify.getPrice()));
        maxTextField.setText(Integer.toString(partToModify.getMax()));
        minTextField.setText(Integer.toString(partToModify.getMin()));


        if(partToModify instanceof InHouse){
            inHouseRadio.setSelected(true);
            partSourceTextField.setText(Integer.toString(((InHouse) partToModify).getMachineID()));
       }
       else if(partToModify instanceof Outsourced){
           outsourcedRadio.setSelected(true);
            partSourceTextField.setText(((Outsourced) partToModify).getCompanyName());
        }

    }

    /** This saves the part modifications.
     *  Modifies the part using the text in the text fields.
     *  Contains error handling for the Modify Part Screen.
     *  Returns to Main Screen if part is modified successfully.
     *
     * @param actionEvent Save Button clicked.
     * @throws IOException exception.
     */
    public void modifyPartFormSaveClick(ActionEvent actionEvent) throws IOException {
        try {

            int id = Integer.parseInt(iDTextField.getText());
            String name = nameTextField.getText();
            int stock = Integer.parseInt(invTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());
            int min = Integer.parseInt(minTextField.getText());
            int max = Integer.parseInt(maxTextField.getText());
            int machineID;
            String companyName;
            boolean partUpdateSuccess = false;

            if (min > stock) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inventory Error");
                alert.setContentText("Inventory amount can not be less than Min!");
                alert.showAndWait();

            }
            else if (stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inventory Error");
                alert.setContentText("Inventory amount can not be greater than Max!");
                alert.showAndWait();
            }
            else if (Objects.equals(name, "")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Name");
                alert.setContentText("Part must have a name!");
                alert.showAndWait();
            }
            else if(inHouseRadio.isSelected()) {
                try {
                    machineID = Integer.parseInt(partSourceTextField.getText());
                    Inventory.updatePart(id, new InHouse(id, name, price, stock, min, max, machineID));
                    partUpdateSuccess = true;

                }
                catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Value Error");
                    alert.setContentText("Machine ID value must be an Integer");
                    alert.showAndWait();
                }
            }
            else if(outsourcedRadio.isSelected()){
                companyName = partSourceTextField.getText();
                Inventory.updatePart(id, new Outsourced(id, name, price, stock, min, max, companyName));
                partUpdateSuccess = true;

            }
            if (partUpdateSuccess) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Part Modify Success");
                alert.setContentText("Part Successfully modified. Returning to Main Screen.");
                alert.showAndWait();

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main-Form.fxml")));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1100, 400);
                stage.setTitle("Inventory Management System");
                stage.setScene(scene);

                stage.show();
            }

        }
        catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Field Error");
            alert.setContentText("All fields must contain a valid value!");
            alert.showAndWait();
        }
    }

    /** This is used to cancel modification and returns to main screen.
     *  Confirmation box appears before returning.
     *
     * @param actionEvent Cancel button clicked.
     * @throws IOException exception.
     */
    public void modifyPartFormCancelClick(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to cancel? Part will not be changed and you will be returned to the Main Screen." );
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

    /** This sets the part source label to MachineID if inhouse radio button is selected.
     *
     * @param actionEvent Radio Button Selected.
     */
    public void inHouseRadioSelected(ActionEvent actionEvent) {
        partSourceLabel.setText("Machine ID");
    }

    /** This sets the part source label to Company Name if outsourced radio button is selected.
     *
     * @param actionEvent Radio Button Selected.
     */
    public void outsourcedRadioSelected(ActionEvent actionEvent) {
        partSourceLabel.setText("Company Name");
    }
}