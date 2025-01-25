package HyperSnip;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;


public class SceneController {

    // Reference to Button in FXML
    @FXML
    private Button snipButton;
    
    @FXML
    private ImageView imageView;

    @FXML
    void buttonClicked(ActionEvent event) {  // Corrected event type to ActionEvent
        // Example: Print statement on button click
        System.out.println("Button Clicked");
        try {
            // Create an instance of SnippingTool
            SnippingTool snippingTool = new SnippingTool();

            // Start the snipping process
            snippingTool.startSnipping();

            InputStream stream = new FileInputStream("C:\\Users\\brian\\OneDrive\\Documents");
            Image image = new Image(stream);
            //Creating the image view
            ImageView imageView = new ImageView();
            //Setting image to the image view
            imageView.setImage(image);
            imageView.setX(10);
            imageView.setY(10);
            imageView.setFitWidth(575);
            imageView.setPreserveRatio(true);
            //Setting the Scene object
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    void initialize() {
        // Optional: Initial setup for components (e.g., set initial progress of the progress bar)
    }
}
