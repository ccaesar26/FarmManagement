import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HomePanel extends JPanel {
    public HomePanel() {
        super();

        //setLayout(new BorderLayout());

        // Create and set the title label with an EmptyBorder
        JLabel titleLabel = new JLabel("Gestiunea fermei agricole", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));

        titleLabel.setBorder(new EmptyBorder(64, 0, 16, 0));

        add(titleLabel/*, BorderLayout.NORTH*/);

        // Load and set the farm image from a local file
        try {
            // Replace "assets/farmer.png" with the correct file path of your farm image
            File imageFile = new File("assets/farmer.png");
            BufferedImage farmImage = ImageIO.read(imageFile);
            ImageIcon farmImageIcon = new ImageIcon(farmImage);
            farmImageIcon.setImage(farmImageIcon.getImage().getScaledInstance(800, 450, Image.SCALE_SMOOTH));
            JLabel imageLabel = new JLabel(farmImageIcon, SwingConstants.CENTER);
            imageLabel.setPreferredSize(new Dimension(960, 540));
            add(imageLabel/*, BorderLayout.CENTER*/);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("HomePanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        HomePanel homePanel = new HomePanel();
        frame.add(homePanel);

        frame.setVisible(true);
    }
}
