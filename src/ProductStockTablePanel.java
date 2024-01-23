import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class ProductStockTablePanel extends JPanel {
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
    public ProductStockTablePanel(Function<JPanel, Void> setMainPanelCallback) {
        super();
        this.setMainPanelCallback = setMainPanelCallback;
        this.returnCallback  = (Void v) -> {
            setMainPanelCallback.apply(this);
            refreshUI.apply(null);
            return null;
        };
        this.editCallback = (String productStockName) -> {
            JPanel productStockRowPanel = new ProductStockRowPanel(
                    DbUtils.selectProductStockAllActiveWhere(productStockName),
                    returnCallback
            );
            setMainPanelCallback.apply(productStockRowPanel);
            return null;
        };
        setupUI();
    }
    private void setupUI() {
        LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);

        JPanel titlePanel = UiUtils.createTitlePanel("Product Stock");

        String[] columnNames = DbObject.convertListToArray(ProductStock.getFields());

        String[][] data = DbObject.convertListToMatrix(DbUtils.selectAllProductStocks());

        JPanel dataTablePanel = UiUtils.createDataTablePanel(
                data,
                columnNames,
                editCallback,
                DbUtils::softDeleteProductStock,
                setMainPanelCallback,
                returnCallback,
                refreshUI
        );

        add(titlePanel);
        add(dataTablePanel);
    }
}
