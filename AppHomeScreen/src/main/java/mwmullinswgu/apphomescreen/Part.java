package mwmullinswgu.apphomescreen;
/**
 * Supplied class Part.java
 */

/** Abstract Part Class
 *
 * @author Matthew Mullins
 */
public abstract class Part {
    /** Used for part id.
     *
     */
    private int id;
    /** Used for part name.
     *
     */
    private String name;
    /** Used for part price.
     *
     */
    private double price;
    /** Used for part stock.
     *
     */
    private int stock;
    /** Used for part min stock.
     *
     */
    private int min;
    /** Used for part max stock.
     *
     */
    private int max;

    /** Part Constructor.
     *
     * @param id the part id.
     * @param name the part name.
     * @param price the part price.
     * @param stock the part stock.
     * @param min the part min stock.
     * @param max the part max stock.
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Part ID getter.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /** Part ID setter.
     * @param id the id to set
     */
    public void setId(int id) { this.id = id; }

    /** Part Name getter.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /** Part Name setter.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Part Price getter.
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /** Part Price setter.
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /** Part Stock getter.
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /** Part Stock setter.
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Part Min getter.
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /** Part Min setter.
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** Part Max getter.
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /** Part Max setter.
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
    
}