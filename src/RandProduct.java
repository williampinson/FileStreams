import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class RandProduct implements Serializable {

    private String ID;
    private String name;
    private String description;
    private double cost;

    public static final int ID_MAX_LENGTH = 6;
    public static final int NAME_MAX_LENGTH = 35;
    public static final int DESCRIPTION_MAX_LENGTH = 75;

    private ArrayList<Byte[]> formattedRecord;

    public RandProduct(String ID, String name, String description, double cost) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public byte[] getFormattedID() {
        return String.format("%" + ID_MAX_LENGTH + "s", ID).getBytes(StandardCharsets.UTF_8);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public byte[] getFormattedName() {
        return String.format("%" + NAME_MAX_LENGTH + "s", name).getBytes(StandardCharsets.UTF_8);
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public byte[] getFormattedDescription() {
        return String.format("%" + DESCRIPTION_MAX_LENGTH + "s", description).getBytes(StandardCharsets.UTF_8);
    }
    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "RandProduct{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }
}