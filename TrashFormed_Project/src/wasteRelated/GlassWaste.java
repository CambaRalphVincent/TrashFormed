package wasteRelated;

public class GlassWaste extends WasteItem {
    public GlassWaste(int itemId, int userId, String itemName, int quantity) {
        super(itemId, userId, itemName, quantity, "Glass");
    }

    @Override
    public String getRecycleTip() {
        return "Clean Containers: Rinse out any food or beverage residues from glass containers. While they don’t need to be spotless, removing contaminants helps maintain the quality of recycled glass."; 
        		
    }
}


