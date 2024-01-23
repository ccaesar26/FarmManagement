import javax.swing.*;
import java.awt.*;
import java.util.function.Function;
import javax.swing.UIManager.LookAndFeelInfo;

public class FarmAppFrame extends JFrame {
    private JPanel currentPanel;
    private final Function<JPanel, Void> setMainPanelContent;
    public FarmAppFrame() {
        super("Farm Management App");

        this.setMainPanelContent = (JPanel panel) -> {
            currentPanel = panel;
            setContentPane(currentPanel);
            revalidate();
            repaint();
            return null;
        };

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(960, 720);
        setLocationRelativeTo(null);

        JMenuBar menuBar = UiUtils.createMenuBar();

        JMenu menuHome = UiUtils.createMenu("Home");
        JMenuItem menuShowHome = UiUtils.createMenuItem("Show Home Page");
        menuShowHome.addActionListener(e -> setMainPanelContent.apply(new HomePanel()));
        menuHome.add(menuShowHome);
        menuBar.add(menuHome);

        JMenu menuCulture = UiUtils.createMenu("Culture");
        JMenuItem menuCultureList = UiUtils.createMenuItem("Culture List");
        menuCultureList.addActionListener(e -> setMainPanelContent.apply(new CultureTablePanel(setMainPanelContent)));
        JMenuItem menuCultureAdd = UiUtils.createMenuItem("Add Culture");
        menuCultureAdd.addActionListener(e -> setMainPanelContent.apply(new CultureRowPanel(
                (Void v) -> {
                    setMainPanelContent.apply(new CultureTablePanel(setMainPanelContent));
                    return null;
                }
        )));
        menuCulture.add(menuCultureList);
        menuCulture.add(menuCultureAdd);
        menuBar.add(menuCulture);

        JMenu menuLandLot = UiUtils.createMenu("Land Lot");
        JMenuItem menuLandLotList = UiUtils.createMenuItem("Land Lot List");
        menuLandLotList.addActionListener(e -> setMainPanelContent.apply(new LandLotTablePanel(setMainPanelContent)));
        JMenuItem menuLandLotAdd = UiUtils.createMenuItem("Add Land Lot");
        menuLandLotAdd.addActionListener(e -> setMainPanelContent.apply(new LandLotRowPanel(
                (Void v) -> {
                    setMainPanelContent.apply(new LandLotTablePanel(setMainPanelContent));
                    return null;
                }
        )));
        menuLandLot.add(menuLandLotList);
        menuLandLot.add(menuLandLotAdd);
        menuBar.add(menuLandLot);

        JMenu menuFarmer = UiUtils.createMenu("Farmer");
        JMenuItem menuFarmerList = UiUtils.createMenuItem("Farmer List");
        menuFarmerList.addActionListener(e -> setMainPanelContent.apply(new FarmerTablePanel(setMainPanelContent)));
        JMenuItem menuFarmerAdd = UiUtils.createMenuItem("Add Farmer");
        menuFarmerAdd.addActionListener(e -> setMainPanelContent.apply(new FarmerRowPanel(
                (Void v) -> {
                    setMainPanelContent.apply(new FarmerTablePanel(setMainPanelContent));
                    return null;
                }
        )));
        menuFarmer.add(menuFarmerList);
        menuFarmer.add(menuFarmerAdd);
        menuBar.add(menuFarmer);

        JMenu menuProductStock = UiUtils.createMenu("Product Stock");
        JMenuItem menuProductStockList = UiUtils.createMenuItem("Product Stock List");
        menuProductStockList.addActionListener(e -> setMainPanelContent.apply(new ProductStockTablePanel(setMainPanelContent)));
        JMenuItem menuProductStockAdd = UiUtils.createMenuItem("Add Product Stock");
        menuProductStockAdd.addActionListener(e -> setMainPanelContent.apply(new ProductStockRowPanel(
                (Void v) -> {
                    setMainPanelContent.apply(new ProductStockTablePanel(setMainPanelContent));
                    return null;
                }
        )));
        menuProductStock.add(menuProductStockList);
        menuProductStock.add(menuProductStockAdd);
        menuBar.add(menuProductStock);

        currentPanel = new HomePanel();

        setJMenuBar(menuBar);
        setContentPane(currentPanel);

        setVisible(true);
    }
}
