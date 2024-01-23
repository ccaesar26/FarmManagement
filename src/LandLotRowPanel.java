import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class LandLotRowPanel extends JPanel {
    private final JTextField landLotLocationTextField = new JTextField();
    private final JTextField landLotAreaTextField = new JTextField();
    private final JComponent landLotPlantingDateDatePicker = UiUtils.createDatePicker();
    private final JComponent landLotHarvestDateDatePicker = UiUtils.createDatePicker();
    private final JTextField landLotCultureTextField = new JTextField();
    private final Function<Void, Void> returnCallback;
    private final Function<Void, Void> saveCallback;
    private final Function<Void, Void> cancelCallback;
    private boolean isNewEntry = true;
    private String oldLandLotLocation = "";

    public LandLotRowPanel(Function<Void, Void> returnCallback) {
        super();

        this.returnCallback = returnCallback;
        this.saveCallback = (Void v) -> {
            String landLotName = landLotLocationTextField.getText();
            float landLotArea = Float.parseFloat(landLotAreaTextField.getText());
            String landLotPlantingDate = ((JDatePickerImpl) landLotPlantingDateDatePicker).getJFormattedTextField().getText();
            String landLotHarvestDate = ((JDatePickerImpl) landLotHarvestDateDatePicker).getJFormattedTextField().getText();
            String landLotCulture = landLotCultureTextField.getText();
            if (isNewEntry) {
                DbUtils.insertLandLot(landLotName, landLotArea, landLotPlantingDate, landLotHarvestDate, landLotCulture);
            } else {
                DbUtils.updateLandLot(oldLandLotLocation, landLotName, landLotArea, landLotPlantingDate, landLotHarvestDate, landLotCulture);
            }
            returnCallback.apply(null);
            return null;
        };
        this.cancelCallback = (Void v) -> {
            landLotLocationTextField.setText("");
            landLotAreaTextField.setText("");
            ((JDatePickerImpl) landLotPlantingDateDatePicker).getJFormattedTextField().setText("");
            ((JDatePickerImpl) landLotHarvestDateDatePicker).getJFormattedTextField().setText("");
            landLotCultureTextField.setText("");
            returnCallback.apply(null);
            return null;
        };

        setupUI();
    }

    public LandLotRowPanel(LandLot landLot, Function<Void, Void> returnCallback) {
        this(returnCallback);
        this.isNewEntry = false;
        this.oldLandLotLocation = landLot.getLocation();
        this.landLotLocationTextField.setText(landLot.getLocation());
        this.landLotAreaTextField.setText(String.valueOf(landLot.getArea()));
        ((JDatePickerImpl) landLotPlantingDateDatePicker).getJFormattedTextField().setText(landLot.getPlantingDate());
        ((JDatePickerImpl) landLotHarvestDateDatePicker).getJFormattedTextField().setText(landLot.getHarvestDate());
        this.landLotCultureTextField.setText(landLot.getCulture());
    }

    private void setupUI() {
        LayoutManager layout = new BorderLayout();
        setLayout(layout);

        JPanel titlePanel = UiUtils.createTitlePanel("Land lot");

        JPanel landLotLocationPanel = UiUtils.createRowPanel("Location:", landLotLocationTextField);
        JPanel landLotAreaPanel = UiUtils.createRowPanel("Area:", landLotAreaTextField);
        JPanel landLotPlantingDatePanel = UiUtils.createRowPanel("Planting date:", landLotPlantingDateDatePicker);
        JPanel landLotHarvestDatePanel = UiUtils.createRowPanel("Harvest date:", landLotHarvestDateDatePicker);
        JPanel landLotCulturePanel = UiUtils.createRowPanel("Culture:", landLotCultureTextField);

        JPanel dataPanel = UiUtils.createDataPanel(new JPanel[]{landLotLocationPanel, landLotAreaPanel, landLotPlantingDatePanel, landLotHarvestDatePanel, landLotCulturePanel});

        JPanel buttonPanel = UiUtils.createButtonPanel(saveCallback, cancelCallback);

        add(titlePanel, BorderLayout.NORTH);
        add(dataPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
