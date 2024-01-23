import java.util.Arrays;
import java.util.List;

public class Culture implements DbObject {
    private final int CultureId;
    private final String Name;
    public Culture(
        int CultureId,
        String Name
    ) {
        this.CultureId = CultureId;
        this.Name = Name;
    }
    public int getCultureId() {
        return CultureId;
    }
    public String getName() {
        return Name;
    }
    static public String getTableName() {
        return "Culture";
    }
    static public List<String> getFields() {
        return Arrays.asList(
            "Name"
        );
    }
    @Override
    public List<String> getValues() {
        return Arrays.asList(
            Name
        );
    }
}
