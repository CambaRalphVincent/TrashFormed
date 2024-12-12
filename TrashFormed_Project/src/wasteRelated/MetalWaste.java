package wasteRelated;

public class MetalWaste extends WasteItem {
    public MetalWaste(int itemId, int userId, String itemName, int quantity) {
        super(itemId, userId, itemName, quantity, "Metal");
    }

    @Override
    public String getRecycleTip() {
        return "Know What’s Recyclable: Most metals are recyclable, including old appliances, cans, pots, pans, and even some electronics. Ensure that any materials you plan to recycle are clean and free of non-metal components like plastic or rubber.";
    }
}

