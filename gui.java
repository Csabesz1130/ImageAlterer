import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ImageInverter {
    private JFrame frame;
    private JButton selectButton;

    public ImageInverter() {
        frame = new JFrame("Image Inverter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        selectButton = new JButton("Select Image");
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileChooser();
            }
        });
        panel.add(selectButton);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
  
  private void showFileChooser() {
    JFileChooser fileChooser = new JFileChooser();
    int returnValue = fileChooser.showOpenDialog(frame);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
        File inputFile = fileChooser.getSelectedFile();
        try {
            BufferedImage image = ImageIO.read(inputFile);
            invertImage(image);
            saveInvertedImage(image, inputFile);
            JOptionPane.showMessageDialog(frame, "Image inverted and saved to the place called as " + inputFile.getAbsolutePath());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading or writing image file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
