package HyperSnip;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SnippingTool {

    private static int startX = -1, startY = -1, endX = -1, endY = -1;

    private Robot robot;
    private JFrame frame;

    // Constructor to initialize the SnippingTool
    public SnippingTool() throws Exception {
        this.robot = new Robot();
        this.frame = new JFrame("Snipping Tool");
    }

    // Method to start the snipping tool
    public void startSnipping() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (startX != -1 && startY != -1) {
                    int width = Math.abs(endX - startX);
                    int height = Math.abs(endY - startY);
                    // Draw the selection area with transparency
                    g.setColor(new Color(0, 0, 0, 50)); // Semi-transparent black
                    g.fillRect(Math.min(startX, endX), Math.min(startY, endY), width, height);
                    g.setColor(Color.WHITE); // Border color
                    g.drawRect(Math.min(startX, endX), Math.min(startY, endY), width, height);
                }
            }
        };

        // Set the frame to be undecorated and semi-transparent for the overlay effect
        frame.setUndecorated(true);
        frame.setOpacity(0.1f); // Semi-transparent window during selection
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setLocationRelativeTo(null);
        frame.setContentPane(panel);
        frame.setVisible(true);

        // Mouse listener to handle the selection
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Start the selection
                startX = e.getX();
                startY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // End the selection and capture the screenshot
                endX = e.getX();
                endY = e.getY();
                captureScreenshot(robot, startX, startY, endX, endY);


                // Reset start and end coordinates to remove the selection rectangle
                startX = -1;
                startY = -1;
                endX = -1;
                endY = -1;
                
                frame.repaint(); // Redraw to clear the rectangle immediately
                frame.dispose(); // Close the snipping tool window

                runPython();
            }
        });

        // Mouse motion listener for dragging to update selection
        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Update end coordinates while dragging
                endX = e.getX();
                endY = e.getY();
                frame.repaint(); // Redraw the window to show updated selection
            }
        });
    }

    // Method to capture the screenshot from the selected region
    public static void captureScreenshot(Robot robot, int startX, int startY, int endX, int endY) {
        // Ensure the coordinates are within bounds
        int x = Math.min(startX, endX);
        int y = Math.min(startY, endY);
        int width = Math.abs(endX - startX);
        int height = Math.abs(endY - startY);

        // Capture the screenshot of the selected region
        Rectangle captureRect = new Rectangle(x, y, width, height);
        try {
            BufferedImage screenshot = robot.createScreenCapture(captureRect);
            ImageIO.write(screenshot, "png", new File("image.png"));
            System.out.println("Screenshot saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    // Method to adjust brightness by changing the pixel values
    public static BufferedImage adjustBrightness(BufferedImage image, int amount) {
        // Create a new buffered image with the same dimensions
        BufferedImage adjustedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                // Get the current pixel color
                Color color = new Color(image.getRGB(x, y), true);

                // Adjust the RGB values
                int red = Math.max(0, Math.min(255, color.getRed() + amount));
                int green = Math.max(0, Math.min(255, color.getGreen() + amount));
                int blue = Math.max(0, Math.min(255, color.getBlue() + amount));

                // Set the adjusted color back to the new image
                adjustedImage.setRGB(x, y, new Color(red, green, blue, color.getAlpha()).getRGB());
            }
        }

        return adjustedImage;
    }

    
    // Method to search file names
    /* 
    public static String checkDir(String directoryPath){
        directoryPath = "images";
        String lastName = "0";
        // Create a File object for the directory
        File directory = new File(directoryPath);
        
        // Check if the directory exists and is indeed a directory
        if (directory.exists() && directory.isDirectory()) {
            // Get all files in the directory
            File[] files = directory.listFiles();
            
            if (files != null) {
                // Loop through the files
                for (File file : files) {
                    // Check if it is a file (not a directory)
                    if (file.isFile()) {
                        // Print the file name
                        lastName = file.getName().substring(4);
                    }
                }
            } else {
                System.out.println("The directory is empty or an error occurred.");
            }
        } else {
            System.out.println("The specified path is not a directory.");
        }
        int numValue = Integer.parseInt(lastName) + 1;
        return "image" + numValue;
    }
    */

    public static void runPython(){
        // Run Python Script
        try {
            // Specify the Python interpreter and script path
            String pythonScriptPath = "python/ImageReader.py";

            // Create a ProcessBuilder to run the Python script
            ProcessBuilder processBuilder = new ProcessBuilder("C:\\Users\\brian\\AppData\\Local\\Programs\\Python\\Python313\\python.exe", pythonScriptPath);

            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Read the output of the Python script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // Output the result to the console
            }

            // Wait for the script to finish executing
            int exitCode = process.waitFor();
            System.out.println("Python script executed with exit code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            SnippingTool snippingTool = new SnippingTool();
            snippingTool.startSnipping(); // Start the snipping tool
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
