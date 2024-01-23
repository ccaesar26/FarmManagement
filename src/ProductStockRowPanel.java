import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class ProductStockRowPanel extends JPanel {
    private final JTextField productStockNameTextField = new JTextField();
    private final JTextField productStockQuantityTextField = new JTextField();
    private final JTextField productStockCultureTextField = new JTextField();
    private final Function<Void, Void> returnCallback;
    private final Function<Void, Void> saveCallback;
    private final Function<Void, Void> cancelCallback;
    private boolean isNewEntry = true;
    private String oldProductStockName = "";

    public ProductStockRowPanel(Function<Void, Void> returnCallback) {
        super();

        this.returnCallback = returnCallback;
        this.saveCallback = (Void v) -> {
            String productStockName = productStockNameTextField.getText();
            int productStockQuantity = Integer.parseInt(productStockQuantityTextField.getText());
            String productStockCulture = productStockCultureTextField.getText();
            if (isNewEntry) {
                DbUtils.insertProductStock(productStockName, productStockQuantity, productStockCulture);
            } else {
                DbUtils.updateProductStock(oldProductStockName, productStockName, productStockQuantity, productStockCulture);
            }
            returnCallback.apply(null);
            return null;
        };
        this.cancelCallback = (Void v) -> {
            productStockNameTextField.setText("");
            productStockQuantityTextField.setText("");
            productStockCultureTextField.setText("");
            returnCallback.apply(null);
            return null;
        };

        setupUI();
    }

    public ProductStockRowPanel(ProductStock productStock, Function<Void, Void> returnCallback) {
        this(returnCallback);
        this.isNewEntry = false;
        this.oldProductStockName = productStock.getName();
        this.productStockNameTextField.setText(productStock.getName());
        this.productStockQuantityTextField.setText(String.valueOf(productStock.getQuantity()));
        this.productStockCultureTextField.setText(productStock.getCulture());
    }

    private void setupUI() {
        LayoutManager layout = new BorderLayout();
        setLayout(layout);

        JPanel titlePanel = UiUtils.createTitlePanel("Product Stock");

        JPanel productStockNamePanel = UiUtils.createRowPanel("Name:", productStockNameTextField);
        JPanel productStockQuantityPanel = UiUtils.createRowPanel("Quantity:", productStockQuantityTextField);
        JPanel productStockCulturePanel = UiUtils.createRowPanel("Culture:", productStockCultureTextField);

        JPanel dataPanel = UiUtils.createDataPanel(new JPanel[]{productStockNamePanel, productStockQuantityPanel, productStockCulturePanel});

        JPanel buttonPanel = UiUtils.createButtonPanel(saveCallback, cancelCallback);

        add(titlePanel, BorderLayout.NORTH);
        add(dataPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
