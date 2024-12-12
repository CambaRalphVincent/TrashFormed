package wasteRelated;

public class OrganicWaste extends WasteItem {
    public OrganicWaste(int itemId, int userId, String itemName, int quantity) {
        super(itemId, userId, itemName, quantity, "Organic");
    }

    @Override
    public String getRecycleTip() {
        return "Organic recycling tip: Compost organic waste to enrich soil and reduce landfill contributions.";
    }
}


//Container Size: Ensure that your composting or organic waste container is appropriately sized for the volume of waste you generate. A container that's too small may lead to improper disposal of organic waste into regular trash.
