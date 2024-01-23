import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Farmer implements DbObject {
    private final int FarmerId;
    private final String Name;
    private final String EmploymentDate;
    private final String Culture;
    public Farmer(
        int FarmerId,
        String Name,
        String EmploymentDate,
        String Culture
    ) {
        this.FarmerId = FarmerId;
        this.Name = Name;
        this.EmploymentDate = EmploymentDate;
        this.Culture = Culture;
    }
    public int getFarmerId() {
        return FarmerId;
    }
    public String getName() {
        return Name;
    }
    public String getEmploymentDate() {
        return EmploymentDate;
    }
    public String getCulture() {
        return Culture;
    }
    static public String getTableName() {
        return "Farmer";
    }
    static public List<String> getFields() {
        return Arrays.asList(
            "Name",
            "Employment Date",
            "Culture"
        );
    }
    @Override
    public List<String> getValues() {
        return Arrays.asList(
            Name,
            EmploymentDate.toString(),
            Culture
        );
    }
}
