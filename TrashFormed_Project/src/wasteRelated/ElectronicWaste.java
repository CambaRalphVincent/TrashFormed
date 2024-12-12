package wasteRelated;

public class ElectronicWaste extends WasteItem {
    public ElectronicWaste(int itemId, int userId, String itemName, int quantity) {
        super(itemId, userId, itemName, quantity, "Electronic");
    }

    @Override
    public String getRecycleTip() {
        return "Before recycling any electronic device, it’s crucial to securely erase all personal data. Simply deleting files may not be sufficient, as data can often be recovered.";
    }
}

// Before recycling any electronic device, it’s crucial to securely erase all personal data. Simply deleting files may not be sufficient, as data can often be recovered.