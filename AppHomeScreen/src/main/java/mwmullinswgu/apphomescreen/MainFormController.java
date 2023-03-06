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
import java.util.*;

/** The Controller for Main-Form fxml.
 *
 */
public class MainFormController implements Initializable {
    /** Is used to display all parts and part search results.
     *
     */
    public TableView<Part> mainFormPartsTable;
    /**  Is used to display all products and product search results.
     *
     */
    public TableView<Product> mainFormProductsTable;
    /** PartID Column within the parts table.
     *
     */
    public TableColumn<Part, Integer> partID;
    /** Part Name Column within the parts table.
     *
     */
    public TableColumn<Part, String> partName;
    /** Part Stock Column within the parts table.
     *
     */
    public TableColumn<Part, Integer> partInventoryLevel;
    /** Part Price Column withing the parts table.
     *
     */
    public TableColumn<Part, Double> partPrice;
    /** Product ID Column within the products table.
     *
     */
    public TableColumn<Product, Integer> productID;
    /** Product Name Column within the products table.
     *
     */
    public TableColumn<Product, String> productName;
    /** Product Stock Column within the products table.
     *
     */
    public TableColumn<Product, Integer> productInventoryLevel;
    /** Product Price Column within the products table.
     *
     */
    public TableColumn<Product, Double> productPrice;
    /** Search Text Field used to search the parts table.
     *
     */
    public TextField partSearch;
    /** Search Text Field used to search the products table.
     *
     */
    public TextField productSearch;

    /** This method searches for parts in the mainFormPartsTable.
     *  If a part is found it add that part to the search results.
     *  If no part is found a message pops up stating no results found.
     *  If no part is found the search field is reset.
     *  Search Results are added into the mainFormPartsTable.
     */
    public void partSearchHandler() {
        String searchText = partSearch.getText();
        ObservableList<Part>  searchedPartResults = Inventory.lookupPart(searchText);
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
        mainFormPartsTable.setItems( searchedPartResults);
    }

    /** This method searches for products in the mainFormProductsTable.
     *  If a product is found it adds it to the search results.
     *  If no product is found a message pops up stating no results found.
     *  If no product is found the search field is reset.
     *  Search results are added into the mainFormProductsTable.
     */
    public void productSearchHandler() {
        String search = productSearch.getText();
        ObservableList<Product> searchedProductsResults = Inventory.lookupProduct(search);
        if (searchedProductsResults.size() == 0) {
            try {
                int productID = Integer.parseInt(search);
                Product product = Inventory.lookupProduct(productID);
                if (product != null) {
                    searchedProductsResults.add(product);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No Results Found");
                    alert.setHeaderText("No Results Found");
                    alert.setContentText("No results found for " + search);
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Results Found");
                alert.setHeaderText("No Results Found");
                alert.setContentText("No results found for " + search);
                alert.showAndWait();
                productSearch.setText("");

            }
        }
        mainFormProductsTable.setItems(searchedProductsResults);
    }

    /** This is the initialize method.
     *  This runs first and sets the part and product tables up.
     *
     * @param url url.
     * @param resourceBundle rb.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainFormPartsTable.setItems(Inventory.getAllParts());
        mainFormProductsTable.setItems(Inventory.getAllProducts());

        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /** This is method called when the Add(part) button is clicked.
     *  This takes the user to the Add Part Form.
     * @param actionEvent Add button clicked
     * @throws IOException exception.
     */
    public void mainFormAddPartClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Add-Part-Form.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Part Form");
        stage.setScene(scene);
        stage.show();
    }

    /** Variable to be used in the selectedPartToModify method.
     *
     */
    public static Part partToBeModified;

    /** Variable to be used in the selectedProductToModify method.
     *
     */
    public static Product productToBeModified;

    /** This is the method called when the Modify(part) button is clicked.
     *  This takes the user to the Modify Part Form.
     *  An error message is generated if no part is selected.
     *  This method uses the partToBeModified variable.
     *
     * @param actionEvent Modify button clicked.
     * @throws IOException exception.
     */
    public void mainFormModifyPartClick(ActionEvent actionEvent) throws IOException {
       partToBeModified = mainFormPartsTable.getSelectionModel().getSelectedItem();

       if (partToBeModified == null) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Modify Part ERROR!");
           alert.setContentText("No part selected to be modified!");
           alert.showAndWait();
       }

        else {
           Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Modify-Part-Form.fxml")));
           Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
           Scene scene = new Scene(root);
           stage.setTitle("Modify Part Form");
           stage.setScene(scene);
           stage.show();
       }
    }

    /** This method is used to send a selected part to the Modify Part Form.
     *  When called it returns the partToBeModified variable.
     * @return partToBeModified
     */
    public static Part selectedPartToModify(){
        return partToBeModified;
    }

    /** This method is used to send a selected product to the Modify Product Form.
     *  When called it returns the productToBeModified variable.
     * @return productToBeModified
     */
    public static Product selectedProductToModify(){
        return productToBeModified;
    }

    /** This method deletes a part.
     * If not part is selected an error message appears.
     * If a part is selected a confirmation appears.
     */
    public void mainFormDeletePartClick() {

        if (mainFormPartsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Part ERROR!");
            alert.setContentText("No part selected to be deleted!");
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                partSearch.setText("");
                mainFormPartsTable.setItems(Inventory.getAllParts());
                Part selectedPart = mainFormPartsTable.getSelectionModel().getSelectedItem();
                Inventory.deletePart(selectedPart);
            }
        }
    }

    /** This method takes the user to the Add Product Form.
     *
     * @param actionEvent Add button clicked.
     * @throws IOException exception.
     */
    public void mainFormAddProductClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Add-Product-Form.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Product Form");
        stage.setScene(scene);
        stage.show();
    }


    /** This method sends the user to the Modify Product Form.
     *  An error message appears if no product is selected.
     *  This method uses the productToBeModified variable.
     *
     * @param actionEvent Modify button clicked.
     * @throws IOException exception.
     */
    public void mainFormModifyProductClick(ActionEvent actionEvent) throws IOException {
        productToBeModified = mainFormProductsTable.getSelectionModel().getSelectedItem();

        if (productToBeModified == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modify Part ERROR!");
            alert.setContentText("No part selected to be modified!");
            alert.showAndWait();
        }

        else {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Modify-Product-Form.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Product Form");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** This method deletes a product.
     *  An error appears if no product is selected.
     *  An error appears if a product has an associated part.
     *  If a part is selected and has no associated parts a confirmation box appears.
     *
     * @param actionEvent Delete button clicked.
     */
    public void mainFormDeleteProductClick(ActionEvent actionEvent){
        if (mainFormProductsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Product ERROR!");
            alert.setContentText("No product selected to be deleted!");
            alert.showAndWait();
        } else {
            Product selectedProduct = mainFormProductsTable.getSelectionModel().getSelectedItem();
            if (selectedProduct.getAllAssociatedParts().size() > 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Product ERROR!");
                alert.setContentText("Can not delete product while it still has associated parts!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    productSearch.setText("");
                    mainFormProductsTable.setItems(Inventory.getAllProducts());
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        }
    }


    /** This method exits the program when EXIT is clicked.
     *  A confirmation box appears before exiting.
     *
     * @param actionEvent Exit button clicked.
     */
    public void mainFormExitClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Program");
        alert.setHeaderText("Exiting Program");
        alert.setContentText("Are you sure you want to exit the program?");
        alert.showAndWait();
        if(alert.getResult().getText().equals("OK")) {
            System.exit(0);
        }
    }

    /** This method triggers the partSearchHandler when a key is pressed in the part search text field.
     * This method resets the table to include all parts if the parts search field is empty.
     * @param keyEvent Key pressed in part Search Field.
     */
    public void partSearchKeyPressed(KeyEvent keyEvent) {
        partSearchHandler();
        if (partSearch.getText().equals("")){
            mainFormPartsTable.setItems(Inventory.getAllParts());
        }
    }

    /** This method triggers the productSearchHandler when a key is pressing in the product search text field RunTime Error Section Here.
     *  This method resets the table to include all products if the products search field is empty.
     *
     *  ~~~~~RUNTIME ERROR~~~~~~
     *      One of the errors I encountered while making this application happened within the search feature.
     *   This method previously would only call the search handler method. This properly searched for products but had a small bug.
     *   The search handler would reset the search field to be empty but the table wouldn't repopulate.
     *   To solve this issue I added an IF statement to this method that would repopulate the table if the search field was empty.
     *
     * @param keyEvent Key pressed in product search field.
     */
    public void productSearchKeyPressed(KeyEvent keyEvent) {
        productSearchHandler();
        if (productSearch.getText().equals("")){
            mainFormProductsTable.setItems(Inventory.getAllProducts());
        }
    }
}