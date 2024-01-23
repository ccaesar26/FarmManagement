import javax.swing.*;
import java.awt.*;

public class MainClass {
    public static void main(String[] args) {
        initUI();
    }

    private static void initUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            customizeLookAndFeel();

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e.getMessage());
        }

        new FarmAppFrame();
    }

    private static void customizeLookAndFeel() {
        UIDefaults defaults = UIManager.getLookAndFeelDefaults();

        defaults.put("Panel.background", new Color(230, 255, 253)); // Panel background color
        defaults.put("Button.background", new Color(230, 255, 253)); // Button background color

        UIManager.getDefaults().putAll(defaults);
    }


}