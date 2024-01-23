import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class FarmerRowPanel extends JPanel {
    private final JTextField farmerNameTextField = new JTextField();
    private final JComponent farmerEmplDateDatePicker = UiUtils.createDatePicker();
    private final JTextField farmerCultureTextField = new JTextField();
    private final Function<Void, Void> returnCallback;
    private final Function<Void, Void> saveCallback;
    private final Function<Void, Void> cancelCallback;
    private boolean isNewEntry = true;
    private String oldFarmerName = "";

    public FarmerRowPanel(Function<Void, Void> returnCallback) {
        super();

        this.returnCallback = returnCallback;
        this.saveCallback = (Void v) -> {
            String cultureName = farmerNameTextField.getText();
            String emplDate = ((JDatePickerImpl) farmerEmplDateDatePicker).getJFormattedTextField().getText();
            String culture = farmerCultureTextField.getText();
            if (isNewEntry) {
                DbUtils.insertFarmer(cultureName, emplDate, culture);
            } else {
                DbUtils.updateFarmer(oldFarmerName, cultureName, emplDate, culture);
            }
            returnCallback.apply(null);
            return null;
        };
        this.cancelCallback = (Void v) -> {
            farmerNameTextField.setText("");
            returnCallback.apply(null);
            return null;
        };

        setupUI();
    }

    FarmerRowPanel(Farmer farmer, Function<Void, Void> returnCallback) {
        this(returnCallback);
        this.isNewEntry = false;
        this.oldFarmerName = farmer.getName();
        this.farmerNameTextField.setText(farmer.getName());
        ((JDatePickerImpl) farmerEmplDateDatePicker).getJFormattedTextField().setText(farmer.getEmploymentDate());
        this.farmerCultureTextField.setText(farmer.getCulture());
    }

    private void setupUI() {
        LayoutManager layout = new BorderLayout();
        setLayout(layout);

        JPanel titlePanel = UiUtils.createTitlePanel("Culture");

        JPanel farmerNamePanel = UiUtils.createRowPanel("Name:", farmerNameTextField);
        JPanel farmerEmploymentDatePanel = UiUtils.createRowPanel("Emplyment date:", farmerEmplDateDatePicker);
        JPanel farmerCulturePanel = UiUtils.createRowPanel("Culture:", farmerCultureTextField);

        JPanel dataPanel = UiUtils.createDataPanel(new JPanel[]{farmerNamePanel, farmerEmploymentDatePanel, farmerCulturePanel});

        JPanel buttonPanel = UiUtils.createButtonPanel(saveCallback, cancelCallback);

        add(titlePanel, BorderLayout.NORTH);
        add(dataPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
