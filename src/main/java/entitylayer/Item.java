package entitylayer;

/**
 * Created by Thomas on 11-5-2015.
 */
public class Item {
    private int id;
    private String name;
    private double price;
    private Boolean isLiquid; //If false: solid.

    public Item(int id, String name, Boolean isLiquid) {
        this.id = id;
        this.name = name;
        this.isLiquid = isLiquid;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean getIsLiquid() {
        return isLiquid;
    }

    public double getPrice() {
        return price;
    }
}
