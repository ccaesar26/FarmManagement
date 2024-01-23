import java.util.Arrays;
import java.util.List;

public class ProductStock implements DbObject {
    private final int ProductStockId;
    private final String Name;
    private final int Quantity;
    private final String Culture;
    public ProductStock(
        int ProductStockId,
        String Name,
        int Quantity,
        String Culture
    ) {
        this.ProductStockId = ProductStockId;
        this.Name = Name;
        this.Quantity = Quantity;
        this.Culture = Culture;
    }
    public int getProductStockId() {
        return ProductStockId;
    }
    public String getName() {
        return Name;
    }
    public int getQuantity() {
        return Quantity;
    }
    public String getCulture() {
        return Culture;
    }
    static public String getTableName() {
        return "Product Stock";
    }
    static public List<String> getFields() {
        return Arrays.asList(
            "Name",
            "Quantity",
            "Culture"
        );
    }
    @Override
    public List<String> getValues() {
        return Arrays.asList(
            Name,
            String.valueOf(Quantity),
            Culture
        );
    }
}
