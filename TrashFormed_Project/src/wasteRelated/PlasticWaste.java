package wasteRelated;

public class PlasticWaste extends WasteItem {
    public PlasticWaste(int itemId, int userId, String itemName, int quantity) {
        super(itemId, userId, itemName, quantity, "Plastic");
    }

    @Override
    public String getRecycleTip() {
        return "Plastic recycling tip: Rinse plastic containers before recycling and avoid recycling items with food residue.";
    }
}


// Clean and Rinse: Remove any food or liquid residues. Contaminated plastics can spoil entire batches of recyclables.
