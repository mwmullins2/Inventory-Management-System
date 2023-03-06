package mwmullinswgu.apphomescreen;

/** The Outsourced class that extends the Part class.
 *
 */
public class Outsourced extends Part {
    /** Variable used for the part Company Name unique to Outsourced parts.
     *
     */
    private String  companyName;

    /** Outsourced part constructor.
     *
     * @param id the part ID.
     * @param name the part Name.
     * @param price the part Price.
     * @param stock the part Stock.
     * @param min the part Min Stock.
     * @param max the part Max Stock.
     * @param companyName the outsourced part company name.
     */
    public Outsourced(int id, String name,double price, int stock, int min, int max, String companyName) {
        super(id,name,price,stock,min,max);
        this.companyName = companyName;
    }

    /** Company Name setter.
     *
     * @param companyName the outsourced part company name.
     */
    public void setCompanyName(String companyName) {

        this.companyName = companyName;
    }

    /** Company Name getter.
     *
     * @return the outsourced part company name.
     */
    public String getCompanyName() {
        return  companyName;
    }
}
