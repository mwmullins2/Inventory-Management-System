package mwmullinswgu.apphomescreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Product Class.
 * Creates an instance of a Product.
 */
public class Product {
    /** Associated Parts list.
     *
      */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /** Used for product ID.
     *
     */
    private int id;
    /** Used for product name.
     *
     */
    private String name;
    /** Used for product price.
     *
     */
    private double price;
    /** Used for product stock.
     *
     */
    private int stock;
    /** Used for product min stock.
     *
     */
    private int min;
    /** Used for product max stock.
     *
     */
    private int max;

    /** Product constructor.
     *
     * @param id the product id.
     * @param name the product name.
     * @param price the product price.
     * @param stock the product stock.
     * @param min the product min stock.
     * @param max the product max stock.
     */
    public Product(int id,String name,double price,int stock,int min,int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Product ID setter.
     *
     * @param id the id to set.
     */
    public void setId(int id) { this.id = id; }

    /** Product Name setter.
     *
     * @param name the name to set.
     */
    public void setName(String name) { this.name = name; }

    /** Product Price setter.
     *
     * @param price the price to set.
     */
    public void setPrice(double price) {this.price = price; }

    /** Product Stock setter.
     *
     * @param stock the stock to set.
     */
    public void setStock(int stock) { this.stock = stock; }

    /** Product Min setter.
     *
     * @param min the min to set.
     */
    public void setMin(int min) { this.min = min; }

    /** Product Max setter.
     *
     * @param max the max to set.
     */
    public void setMax(int max) { this.max = max;}

    /** Product ID getter.
     *
     * @return the id.
     */
    public int getId(){ return id; }

    /** Product Name getter.
     *
     * @return the name.
     */
    public String getName(){ return name; }

    /** Product Price getter.
     *
     * @return the price.
     */
    public double getPrice(){ return price; }

    /** Product Stock getter.
     *
     * @return the stock.
     */
    public int getStock(){ return stock;}

    /** Product Min getter.
     *
     * @return the min.
     */
    public int getMin(){ return min;}

    /** Product Max getter.
     *
     * @return the max.
     */
    public int getMax(){ return max; }

    /** Adds the part to the associated parts list.
     *
     * @param part the part to add to associated parts.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** Deletes the part from the associated parts list.
     *
     * @param selectedAssociatedPart the part to delete from associated parts.
     * @return true or false
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else
            return false;
    }

    /** Associated Parts getter.
     *
     * @return all associated parts.
     */
    public ObservableList<Part> getAllAssociatedParts() {

        return associatedParts;
    }
}
