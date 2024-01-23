import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class LandLotTablePanel extends JPanel {
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
    public LandLotTablePanel(Function<JPanel, Void> setMainPanelCallback) {
        super();
        this.setMainPanelCallback = setMainPanelCallback;
        this.returnCallback  = (Void v) -> {
            setMainPanelCallback.apply(this);
            refreshUI.apply(null);
            return null;
        };
        this.editCallback = (String landLotLocation) -> {
            JPanel landLotRowPanel = new LandLotRowPanel(
                    DbUtils.selectLandLotAllActiveWhere(landLotLocation),
                    returnCallback
            );
            setMainPanelCallback.apply(landLotRowPanel);
            return null;
        };
        setupUI();
    }
    private void setupUI() {
        LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);

        JPanel titlePanel = UiUtils.createTitlePanel("Land Lot");

        String[] columnNames = DbObject.convertListToArray(LandLot.getFields());

        String[][] data = DbObject.convertListToMatrix(DbUtils.selectAllLandLots());

        JPanel dataTablePanel = UiUtils.createDataTablePanel(
                data,
                columnNames,
                editCallback,
                DbUtils::softDeleteLandLot,
                setMainPanelCallback,
                returnCallback,
                refreshUI
        );

        add(titlePanel, BorderLayout.NORTH);
        add(dataTablePanel, BorderLayout.CENTER);
    }
}
