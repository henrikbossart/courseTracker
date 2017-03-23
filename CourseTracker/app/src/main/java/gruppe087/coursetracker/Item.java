package gruppe087.coursetracker;

/**
 * Created by petercbu on 14.03.2017.
 */

public class Item {
    private String itemName;
    private String itemDescription;

    public Item(String name, String description) {
        this.itemName = name;
        this.itemDescription = description;
    }

    public String getItemName() {
        return this.itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }
}
