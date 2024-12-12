package wasteRelated;

public abstract class WasteItem {
    private int itemId;
    private int userId;
    private String itemName;
    private int quantity;
    private String wasteType;

    // Constructor
    public WasteItem(int itemId, int userId, String itemName, int quantity, String wasteType) {
        this.itemId = itemId;
        this.userId = userId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.wasteType = wasteType;
    }

    // Getters and Setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getWasteType() {
        return wasteType;
    }

    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    // Abstract method for recycling tips
    public abstract String getRecycleTip();
}
