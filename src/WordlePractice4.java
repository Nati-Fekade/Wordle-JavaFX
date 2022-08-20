import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class WordlePractice4 extends Application {
    public void start(Stage primaryStage) {
        BorderPane bpane = new BorderPane();
        Scene scene = new Scene(bpane, 400, 400);
        
        Label label = new Label();
        label.setPrefHeight(50);
        label.setPrefWidth(50);
        label.setStyle("-fx-border-color: black");
        Label label2 = new Label();
        label.setPrefHeight(50);
        label.setPrefWidth(50);
        label.setStyle("-fx-border-color: black");
        
        GridPane gpane = new GridPane();
        gpane.setHgap(10); gpane.setVgap(10);
        gpane.add(label, 0, 0);
        gpane.add(label2, 1, 0);
       
        
        bpane.setCenter(gpane);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
