import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Function;

public class CultureTablePanel extends JPanel {
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
    public CultureTablePanel(Function<JPanel, Void> setMainPanelCallback) {
        super();
        this.setMainPanelCallback = setMainPanelCallback;
        this.returnCallback  = (Void v) -> {
            setMainPanelCallback.apply(this);
            refreshUI.apply(null);
            return null;
        };
        this.editCallback = (String cultureName) -> {
            JPanel cultureRowPanel = new CultureRowPanel(
                    DbUtils.selectCultureAllActiveWhere(cultureName),
                    returnCallback
            );
            setMainPanelCallback.apply(cultureRowPanel);
            return null;
        };
        setupUI();
    }

    private void setupUI() {
        LayoutManager layout = new BorderLayout();
        setLayout(layout);

        JPanel titlePanel = UiUtils.createTitlePanel("Culture");

        String[] columnNames = DbObject.convertListToArray(Culture.getFields());

        List<? extends DbObject> cultureList = DbUtils.selectAllCultures();
        String[][] data = DbObject.convertListToMatrix(cultureList);

        JPanel dataTablePanel = UiUtils.createDataTablePanel(
                data,
                columnNames,
                editCallback,
                DbUtils::softDeleteCulture,
                setMainPanelCallback,
                returnCallback,
                refreshUI
        );

        add(titlePanel, BorderLayout.NORTH);
        add(dataTablePanel, BorderLayout.CENTER);
    }
}
