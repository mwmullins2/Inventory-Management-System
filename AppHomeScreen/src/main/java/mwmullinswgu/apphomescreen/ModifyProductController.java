package mwmullinswgu.apphomescreen;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller for Modify-Product-Form fxml
 *
 */
public class ModifyProductController implements Initializable {

    /** Table used to display all parts in Inventory.
     *
     */
    public TableView<Part> allPartTable;
    /** Part ID Column for all part table.
     *
     */
    public TableColumn<Part, Integer> allPartIDCol;
    /** Part Name Column for all part table.
     *
     */
    public TableColumn<Part, String> allPartNameCol;
    /** Part Stock Column for all part table.
     *
     */
    public TableColumn<Part, Integer> allPartInvCol;
    /** Part Price Column for all part table.
     *
     */
    public TableColumn<Part, Double> allPartPriceCol;
    /** Table used to display parts that are associated with selected product.
     *
     */
    public TableView<Part> associatedPartTable;
    /** Part ID Column for associated part table.
     *
     */
    public TableColumn<Part, Integer> assocPartIDCol;
    /** Part Name Column for associated part table.
     *
     */
    public TableColumn<Part, String> assocPartNameCol;
    /** Part Stock Column for associated part table.
     *
     */
    public TableColumn<Part, Integer> assocPartInvCol;
    /** Part Price Column for associated part table.
     *
     */
    public TableColumn<Part, Double> assocPartPriceCol;
    /** Used to display and edit Product Name.
     *
     */
    public TextField productNameTextField;
    /** Used to display and edit Product Stock.
     *
     */
    public TextField productInvTextField;
    /** Used to display and edit Product Price.
     *
     */
    public TextField productPriceTextField;
    /** Used to display and edit Product Max Stock.
     *
     */
    public TextField productMaxTextField;
    /** Used to display and edit Product Min Stock.
     *
     */
    public TextField productMinTextField;
    /** Used to display auto generated Product ID.
     *
     */
    public TextField productIDTextField;
    /** Used to search the all parts table.
     *
     */
    public TextField partSearch;
    /** Selected product to modify brought from the main screen form.
     *
     */
    Product productToModify = MainFormController.selectedProductToModify();

    /** This method searches for parts in the all parts table.
     *  If a part is found it add that part to the search results.
     *  If no part is found a message pops up stating no results found.
     *  If no part is found the search field is reset.
     *  Search Results are added into the all parts table.
     */
    public void partSearchHandler() {
        String searchText = partSearch.getText();
        ObservableList<Part> searchedPartResults = Inventory.lookupPart(searchText);
        if ( searchedPartResults.size() == 0) {
            try {
                int partID = Integer.parseInt(searchText);
                Part part = Inventory.lookupPart(partID);
                if (part != null) {
                    searchedPartResults.add(part);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No Results Found");
                    alert.setHeaderText("No Results Found");
                    alert.setContentText("No results found for " + searchText);
                    alert.showAndWait();
                    partSearch.setText("");
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Results Found");
                alert.setHeaderText("No Results Found");
                alert.setContentText("No results found for " + searchText);
                alert.showAndWait();
                partSearch.setText("");

            }
        }
        allPartTable.setItems( searchedPartResults);
    }

    /** This is the initialize method.
     *  This runs first.
     *  This sets up the tables and puts the selected product information into the text fields.
     *
     * @param url url.
     * @param resourceBundle rb.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");
        allPartTable.setItems(Inventory.getAllParts());
        associatedPartTable.setItems(productToModify.getAllAssociatedParts());
        allPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        assocPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIDTextField.setText(Integer.toString(productToModify.getId()));
        productNameTextField.setText(productToModify.getName());
        productInvTextField.setText(Integer.toString(productToModify.getStock()));
        productMinTextField.setText(Integer.toString(productToModify.getMin()));
        productMaxTextField.setText(Integer.toString(productToModify.getMax()));
        productPriceTextField.setText(Double.toString(productToModify.getPrice()));

    }


    /** Takes the selected part from the all parts table and associates it with the product.
     *  This also displays it in the associated parts table.
     *
     * @param actionEvent Add button clicked.
     */
    public void modifyProductFormAddClick(ActionEvent actionEvent) {
        Part partToAssociate = allPartTable.getSelectionModel().getSelectedItem();
        productToModify.addAssociatedPart(partToAssociate);
    }

    /** Used to remove an associated part from a product.
     *  Error Message pops up if not part is selected.
     *  Confirmation box pops up before part is deleted.
     *  Removes associated part from a product and associated part table.
     * @param actionEvent Remove Associated Part button clicked.
     */
    public void modifyProductFormRAPClick(ActionEvent actionEvent) {
        if (associatedPartTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Remove Part Error");
            alert.setContentText("No associated part is selected to remove");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                Part partToRemove = associatedPartTable.getSelectionModel().getSelectedItem();
                productToModify.deleteAssociatedPart(partToRemove);
            }
        }
    }

    /** Saves the modifications to the selected product.
     *  Contains error handling for the modify product form.
     *  Returns user to the main screen if product is modified successfully.
     *
     * @param actionEvent Save Button Clicked.
     */
    public void modifyProductFormSaveClick(ActionEvent actionEvent) {
        try{
            int id = Integer.parseInt(productIDTextField.getText());
            String name = productNameTextField.getText();
            int stock = Integer.parseInt(productInvTextField.getText());
            double price = Double.parseDouble(productPriceTextField.getText());
            int min = Integer.parseInt(productMinTextField.getText());
            int max = Integer.parseInt(productMaxTextField.getText());
            boolean productUpdateSuccess = false;

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
            else {
                Inventory.updateProduct(id, productToModify);
                productUpdateSuccess = true;
            }
            if(productUpdateSuccess){
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

    /** Cancels the modifications to the product.
     *  Confirmation box appears beofre exit.
     *  Returns the user to the main screen after confirmation.
     *
     * @param actionEvent Cancel Button Clicked.
     * @throws IOException exception.
     */
    public void modifyProductFormCancelClick(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to cancel? Product will not be changed and you will be returned to the Main Screen." );
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

    /** This method calls the search handler when a key is pressed within the search text field.
     *  Resets the table to include all parts if the search field is empty.
     *
     * @param keyEvent Key pressed within search text field.
     */
    public void partSearchKeyPress(KeyEvent keyEvent) {
        partSearchHandler();
        if(partSearch.getText().equals("")){
            allPartTable.setItems(Inventory.getAllParts());
        }
    }
}