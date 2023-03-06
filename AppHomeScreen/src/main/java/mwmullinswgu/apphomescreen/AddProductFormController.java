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

/** Controller for Add-Product-Form fxml
 *
 */
public class AddProductFormController implements Initializable {

    /** Used to auto generate product ID.
     *
     */
    public static int autoProductID = 1;
    /** Used to enter product name.
     *
     */
    public TextField prodNameTextField;
    /** Used to enter product stock.
     *
     */
    public TextField prodInvTextField;
    /** Used to enter product price.
     *
     */
    public TextField prodPriceTextField;
    /** Used to enter product max stock.
     *
     */
    public TextField prodMaxTextField;
    /** Used to enter product min stock.
     *
     */
    public TextField prodMinTextField;
    /** Used to display all parts in inventory.
     *
     */
    public TableView<Part> allPartsTable;
    /** Used to display parts that are associated with the product.
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
    /** Part ID Column for all parts table.
     *
     */
    public TableColumn<Part, Integer> allPartIDCol;
    /** Part Name Column for all parts table.
     *
     */
    public TableColumn<Part, String> allPartNameCol;
    /** Part Stock Column for all parts table.
     *
     */
    public TableColumn<Part, Integer> allPartInvCol;
    /** Part Price Column for all parts table.
     *
     */
    public TableColumn<Part, Double> allPartPriceCol;
    /** Used to search all parts table.
     *
     */
    public TextField partSearch;
    /** New product that is made during the add product process.
     *
     */
    Product newProduct = new Product(0,null,0,0,0,0);

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
        allPartsTable.setItems( searchedPartResults);
    }


    /** This method is used to aut generate a Product ID.
     *  Returns and increments the autoProductID variable.
     *
     * @return autoProductID
     */
    public static int autoGenProductID(){
        return autoProductID++;
    }

    /** This is the initialize method.
     *  This runs first.
     *  Sets up the tables and columns used on this form.
     *
     * @param url url.
     * @param resourceBundle rb.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");
        allPartsTable.setItems(Inventory.getAllParts());
        associatedPartTable.setItems(newProduct.getAllAssociatedParts());


        allPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        assocPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    /** Takes a part from the all parts table and associates it with a product.
     *
     * @param actionEvent Add Associated Part Button Clicked
     */
    public void addProductFormAddClick(ActionEvent actionEvent) {
        Part partToAssociate = allPartsTable.getSelectionModel().getSelectedItem();
        newProduct.addAssociatedPart(partToAssociate);

    }

    /** Used to remove an associated part from a product.
     *  Error Message pops up if not part is selected.
     *  Confirmation box pops up before part is deleted.
     *  Removes associated part from a product and associated part table.
     * @param actionEvent Remove Associated Part Button Clicked
     */
    public void addProductFormRAPClick(ActionEvent actionEvent) {
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
                newProduct.deleteAssociatedPart(partToRemove);
            }
        }
    }

    /** Saves the new product into Inventory.
     * Contains error handling for the add product form.
     * Takes text input from text fields and assigns them to variables.
     * Returns to main screen if product is added successfully.
     *
     * @param actionEvent Save Button Clicked.
     */
    public void addProductFormSaveClick(ActionEvent actionEvent) {
        try {
            int id = autoGenProductID();
            String name = prodNameTextField.getText();
            double price = Double.parseDouble(prodPriceTextField.getText());
            int stock = Integer.parseInt(prodInvTextField.getText());
            int max = Integer.parseInt(prodMaxTextField.getText());
            int min = Integer.parseInt(prodMinTextField.getText());
            boolean productAddedSuccess = false;

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
            else if (Objects.equals(name, "")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Name");
                alert.setContentText("Part must have a name!");
                alert.showAndWait();
            }
            else {
                newProduct.setId(id);
                newProduct.setName(name);
                newProduct.setPrice(price);
                newProduct.setMax(max);
                newProduct.setMin(min);
                newProduct.setStock(stock);
                Inventory.addProduct(newProduct);
                productAddedSuccess =true;
            }

            if(productAddedSuccess) {
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

    /** Returns user to main screen.
     * Confirmation Box appears before exiting screen.
     *
     * @param actionEvent Cancel Button Click.
     * @throws IOException exception.
     */
    public void addProductFormCancelClick(ActionEvent actionEvent) throws IOException {
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

    /** This method calls the search handler when a key is pressed within the search text field.
     *  Resets the table to include all parts if the search field is empty.
     *
     * @param keyEvent Key pressed in search text field.
     */
    public void partSearchKeyPress(KeyEvent keyEvent) {
        partSearchHandler();
        if (partSearch.getText().equals("")) {
            allPartsTable.setItems(Inventory.getAllParts());
        }
    }
}