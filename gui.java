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
    private JButton undoButton;
    private BufferedImage originalImage;

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

        undoButton = new JButton("Undo");
        undoButton.setEnabled(false);
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (originalImage != null) {
                    invertImage(originalImage);
                    saveInvertedImage(originalImage, inputFile);
                    JOptionPane.showMessageDialog(frame, "Image inverted to the original image");
                    undoButton.setEnabled(false);
                }
            }
        });
        panel.add(undoButton);

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
                JOptionPane.showMessageDialog(frame, "Image inverted and saved to " + inputFile.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error reading or writing image file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

        private void invertImage(BufferedImage image) {
        originalImage = image;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgba = image.getRGB(x, y);
                int alpha = (rgba >> 24) & 0xff;
                int red = (rgba >> 16) & 0xff;
                int green = (rgba >> 8) & 0xff;
                int blue = rgba & 0xff;
                red = 255 - red;
                green = 255 - green;
                blue = 255 - blue;
                int newRgba = (alpha << 24) | (red << 16) | (green << 8) | blue;
                image.setRGB(x, y, newRgba);
            }
        }
    }
    private void saveInvertedImage(BufferedImage image, File inputFile) {
        String fileName = inputFile.getAbsolutePath();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        try {
            ImageIO.write(image, extension, inputFile);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error writing image file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args) {
        new ImageInverter();
    }
}


