import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class CultureRowPanel extends JPanel {
    private final JTextField cultureNameTextField = new JTextField();
    private final Function<Void, Void> returnCallback;
    private final Function<Void, Void> saveCallback;
    private final Function<Void, Void> cancelCallback;
    private boolean isNewEntry = true;
    private String oldCultureName = "";

    public CultureRowPanel(Function<Void, Void> returnCallback) {
        super();

        this.returnCallback = returnCallback;
        this.saveCallback = (Void v) -> {
            String cultureName = cultureNameTextField.getText();
            if (isNewEntry) {
                DbUtils.insertCulture(cultureName);
            } else {
                DbUtils.updateCulture(oldCultureName, cultureName);
            }
            returnCallback.apply(null);
            return null;
        };
        this.cancelCallback = (Void v) -> {
            cultureNameTextField.setText("");
            returnCallback.apply(null);
            return null;
        };

        setupUI();
    }

    CultureRowPanel(Culture culture, Function<Void, Void> returnCallback) {
        this(returnCallback);
        this.isNewEntry = false;
        this.oldCultureName = culture.getName();
        this.cultureNameTextField.setText(oldCultureName);
    }

    private void setupUI() {
        LayoutManager layout = new BorderLayout();
        setLayout(layout);

        JPanel titlePanel = UiUtils.createTitlePanel("Culture");

        JPanel cultureNamePanel = UiUtils.createRowPanel("Name:", cultureNameTextField);

        JPanel dataPanel = UiUtils.createDataPanel(new JPanel[]{cultureNamePanel});

        JPanel buttonPanel = UiUtils.createButtonPanel(saveCallback, cancelCallback);

        //UiUtils.colorPanels(new JPanel[]{titlePanel, dataPanel, buttonPanel});

        add(titlePanel, BorderLayout.NORTH);
        add(dataPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
