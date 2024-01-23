import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class LandLot implements DbObject {
    private final int LandLotId;
    private final String Location;
    private final float Area;
    private final String PlantingDate;
    private final String HarvestDate;
    private final String Culture;
    public LandLot(
        int LandLotId,
        String Location,
        float Area,
        String PlantingDate,
        String HarvestDate,
        String Culture
    ) {
        this.LandLotId = LandLotId;
        this.Location = Location;
        this.Area = Area;
        this.PlantingDate = PlantingDate;
        this.HarvestDate = HarvestDate;
        this.Culture = Culture;
    }
    public int getLandLotId() {
        return LandLotId;
    }
    public String getLocation() {
        return Location;
    }
    public float getArea() {
        return Area;
    }
    public String getPlantingDate() {
        return PlantingDate;
    }
    public String getHarvestDate() {
        return HarvestDate;
    }
    public String getCulture() {
        return Culture;
    }
    static public String getTableName() {
        return "Land Lot";
    }
    static public List<String> getFields() {
        return Arrays.asList(
            "Location",
            "Area",
            "Planting Date",
            "Harvest Date"
        );
    }
    @Override
    public List<String> getValues() {
        return Arrays.asList(
            Location,
            String.valueOf(Area),
            PlantingDate.toString(),
            HarvestDate.toString()
        );
    }
}
