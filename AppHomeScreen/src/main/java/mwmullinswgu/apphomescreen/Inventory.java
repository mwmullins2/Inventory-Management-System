package mwmullinswgu.apphomescreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Abstract Inventory Class.
 *
 */
public abstract class Inventory {
    /** List that contains all parts.
     *
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /** List that contains all products.
     *
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /** Add part method.
     * Adds a new part to the all parts list.
     * @param newPart part to be added to the all part list.
     */
    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }

    /** Add product method.
     *  Adds a new product to the all products list.
     *
     * @param newProduct product to be added to the all product list.
     */
    public static void addProduct(Product newProduct) {

        allProducts.add(newProduct);
    }

    /** LookUp Part Method.
     *  Used to search part IDs in the all parts tables.
     *
     * @param partID the part id.
     * @return null and the part searched by ID.
     */
    public static Part lookupPart(int partID) {
        for (Part partIDSearch : allParts) {
            if (partIDSearch.getId() == partID) {
                return partIDSearch;
            }
        }
        return null;
    }

    /** LookUp Product Method.
     *  Used to search product IDs in the all products table.
     *
     * @param productID the product ID.
     * @return null and the product searched by ID.
     */
    public static Product lookupProduct(int productID) {
        for (Product productIDSearch : allProducts) {
            if (productIDSearch.getId() == productID) {
                return productIDSearch;
            }
        }

        return null;
    }

    /** LookUp Part Method.
     *  Used to search a part by name.
     * @param partName the part name.
     * @return The list of parts with matching names.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partSearch = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part pSearch :allParts){
            if(pSearch.getName().toLowerCase().contains(partName)){
                partSearch.add(pSearch);
            }
        }
        return partSearch;
    }

    /** LookUp Product Method.
     *  Used to search a product by name.
     * @param productName the productName.
     * @return The list of products with matching names.
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> productSearch = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(Product pSearch :allProducts){
            if(pSearch.getName().toLowerCase().contains(productName)){
                productSearch.add(pSearch);
            }
        }
        return productSearch;
    }

    /** Updates a part in the modify part form.
     *
     * @param index the index of the part.
     * @param selectedPart the selected part to update.
     */
    public static void updatePart(int index, Part selectedPart) {

        for(int j = 0;j < getAllParts().size(); ++j) {
           Part updatedPart = allParts.get(j);
           if(updatedPart.getId() == index) {
                getAllParts().set(j, selectedPart);
                 }
        }
    }

    /** Updates a product in the modify product form.
     *
     * @param index the index of a product.
     * @param newProduct the selected product to update.
     */
    public static void updateProduct(int index, Product newProduct) {
        for(int j = 0;j < getAllProducts().size(); ++j) {
            Product updatedProduct = allProducts.get(j);
            if(updatedProduct.getId() == index) {
                getAllProducts().set(j, newProduct);
            }
        }
    }

    /** Deletes a part from the all parts table.
     *
     * @param selectedPart the selected part to delete.
     * @return true.
     */
    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;

    }

    /** Deletes a product from the all product table.
     *
     * @param selectedProduct the selected product to delete.
     * @return true.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;


    }

    /** All Parts getter.
     *
     * @return the list of all parts.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** All products getter.
     *
     * @return the list of all products.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /** Test parts and products used to test functionality.
     *
     */
    public static void addTestData() {
        InHouse ih = new InHouse(100999, "Wheel", 5.00, 2, 1, 2, 1234);
        Outsourced os = new Outsourced(100998, "Hub", 5.00, 8, 2, 15, "Hub Mart");
        Inventory.addPart(ih);
        Inventory.addPart(os);
        Product prd = new Product(100999,"Bike",15.00,2,1,20);
        Inventory.addProduct(prd);
        Inventory.addProduct(new Product(100998,"Crank",3.00,1,1,2));
        }

    /** Calls the addTestData method and makes it static.
     *
     */
    static {
        addTestData();
    }

}

