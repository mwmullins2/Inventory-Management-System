package mwmullinswgu.apphomescreen;

/** InHouse Class that extends the Part Class.
 *
 */
public class InHouse extends Part{
    /** Variable used for the Machine ID unique to InHouse parts.
     *
     */
    private int machineID;

    /** InHouse constructor.
     *
     * @param id the part ID.
     * @param name the part Name.
     * @param price the part Price.
     * @param stock the part Stock.
     * @param min the part Min.
     * @param max the part Max.
     * @param machineID the InHouse part Machine ID.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name,price,stock,min,max);
        this.machineID = machineID;
    }

    /** Part MachineID setter.
     *
     * @param machineID the InHouse part machineID.
     */
    public void setMachineID(int machineID) {

        this.machineID = machineID;
    }

    /** Part MachineID getter.
     *
     * @return the InHouse part machine ID.
     */
    public int getMachineID() {

        return machineID;
    }
}
