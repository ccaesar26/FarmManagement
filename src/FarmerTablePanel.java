import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Function;

public class FarmerTablePanel extends JPanel {
    private final Function<JPanel, Void> setMainPanelCallback;
    private final Function<Void, Void> returnCallback;
    private final Function<String, Void> editCallback;
    private final Function<Void, Void> refreshUI = (Void v) -> {
        removeAll();
        setupUI();
        revalidate();
        repaint();
        return null;
    };
    public FarmerTablePanel(Function<JPanel, Void> setMainPanelCallback) {
        super();
        this.setMainPanelCallback = setMainPanelCallback;
        this.returnCallback  = (Void v) -> {
            setMainPanelCallback.apply(this);
            refreshUI.apply(null);
            return null;
        };
        this.editCallback = (String farmerName) -> {
            JPanel farmerRowPanel = new FarmerRowPanel(
                    DbUtils.selectFarmerAllActiveWhere(farmerName),
                    returnCallback
            );
            setMainPanelCallback.apply(farmerRowPanel);
            return null;
        };
        setupUI();
    }
    private void setupUI() {
        LayoutManager layout = new BorderLayout();
        setLayout(layout);

        JPanel titlePanel = UiUtils.createTitlePanel("Farmer");

        String[] columnNames = DbObject.convertListToArray(Farmer.getFields());

        List<? extends DbObject> list = DbUtils.selectAllFarmers();
        String[][] data = DbObject.convertListToMatrix(list);

        JPanel dataTablePanel = UiUtils.createDataTablePanel(
                data,
                columnNames,
                editCallback,
                DbUtils::softDeleteFarmer,
                setMainPanelCallback,
                returnCallback,
                refreshUI
        );

        add(titlePanel, BorderLayout.NORTH);
        add(dataTablePanel, BorderLayout.CENTER);
    }
}
