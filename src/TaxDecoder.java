package kurz.java.taxdecoder;

import java.io.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TaxDecoder extends Application
{
    
    public static void main(String[] args) throws IOException 
    {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception
    {      
        BorderPane root = new BorderPane();
        MainPane mainPane = new MainPane();
        root.setCenter(mainPane);
        
        Scene mainScene = new Scene(root, 1000, 500);        
        
        primaryStage.setTitle("TaxDecoder");
        primaryStage.setScene(mainScene);
        primaryStage.getIcons().add(new Image("file:logo.png"));
        primaryStage.show();
    }
}
