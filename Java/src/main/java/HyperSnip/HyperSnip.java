package HyperSnip;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class HyperSnip extends Application {
    @Override
    public void start(Stage primaryStage) {
        
    Parent root;
    
    try {
        root = FXMLLoader.load(getClass().getResource("Scene.fxml"));
        Scene scene = new Scene(root);
  
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    
    
    }
 
 public static void main(String[] args) {
        launch(args);
    }
} 