import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Text field implementation
 * 
 * Trying different ways of implementing the game. This class uses text field to take in our guess.
 * 
 * @author Nathnael Fekade
 *
 */
public class WordlePractice3 extends Application {
    
    static String word = Words.list.get(new Random().nextInt(Words.list.size()));
    static int guessCounter = 6;
    static TextField tfield = new TextField();
    static Label[][] labels;
    static int row = 0;
    static int col = 0;
    boolean row_col = false;
    static boolean gameStart =true;
    
    public void start(Stage primaryStage) {
        BorderPane bpane = new BorderPane();
        Scene scene = new Scene(bpane, 400, 400);
        
        GridPane gpane = new GridPane();
        gpane.requestFocus();
        labels = new Label[5][6];
        for (int i = 0; i < labels.length; i++) {
            for (int j = 0; j < labels[i].length; j++) {
                labels[i][j] = new Label();
                labels[i][j].setStyle("-fx-background-color: white; -fx-border-color: black");
                labels[i][j].setPrefSize(50, 50);
                gpane.add(labels[i][j], i, j);
            }
        }
        gpane.setVgap(5);
        gpane.setHgap(5);
        gpane.setAlignment(Pos.CENTER);
        
        bpane.setCenter(gpane);
        
        HBox hpane = new HBox(10);
        hpane.setAlignment(Pos.BASELINE_RIGHT);
        hpane.setPadding(new Insets(5,5,5,5));
        hpane.setStyle("-fx-border-color: green");
        Button btn1 = new Button("Restart");
        btn1.setOnAction(new Restart());
        
        
        Button btn2 = new Button("Instruction");
        btn2.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent ae) {
                Stage instructionWindow = new Stage();
                instructionWindow.initModality(Modality.APPLICATION_MODAL);
                StackPane spane = new StackPane();
                Text instruction = new Text("Welcome to Jordle!");
                spane.getChildren().add(instruction);
                Scene scene2 = new Scene(spane, 200, 200);
                instructionWindow.setScene(scene2);
                instructionWindow.showAndWait();
            }
        });
        
        Button btnEnter = new Button("Enter");
        
        btnEnter.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent ae) {
                while (tfield.getText().length() != 5) {
                    Stage alertWindow = new Stage();
                    alertWindow.initModality(Modality.APPLICATION_MODAL);
                    StackPane spane2 = new StackPane();
                    Text alertText = new Text("Must be 5 character words");
                    spane2.getChildren().add(alertText);
                    Scene scene3 = new Scene(spane2, 200,200);
                    alertWindow.setScene(scene3);
                    alertWindow.showAndWait();
                    tfield.clear();
                    
                    return;
                }
                
                if (!gameStart) {
                    return;
                }
                if (!row_col) {
                    for (int i = 0; i < tfield.getText().length(); i++) {
                        labels[col][row].setText("" + tfield.getText().charAt(i));
                        labels[col][row].setAlignment(Pos.CENTER);
                        col++;
                    }
                }
                row_col = col == 5;                
                if (row_col) {
                    int greenCounter = 0;
                    for (int i = 0; i < word.length(); i++) {
                        if (labels[i][row].getText().equalsIgnoreCase("" + word.charAt(i))) {
                            labels[i][row].setStyle("-fx-background-color: green");
                            greenCounter++;
                            if (greenCounter == 5) {
                                gameStart = false;
                            }
                        } else if (word.contains(labels[i][row].getText().toLowerCase())) {
                            labels[i][row].setStyle("-fx-background-color: yellow");
                        } else {
                            labels[i][row].setStyle("-fx-background-color: red");
                        }
                    }
                    row_col = false;
                    col = 0;
                    row++;
                    guessCounter--;
                    
                    if(row == 6) {
                        gameStart = false;
                    }
                }
                
                check(tfield.getText());
                
                System.out.println(tfield.getText());
                System.out.println(word);
                tfield.clear();
            }
        });
       
        hpane.getChildren().addAll(tfield, btnEnter, btn1, btn2);
        bpane.setBottom(hpane);
        
        VBox vpane = new VBox(10);
        vpane.setAlignment(Pos.TOP_RIGHT);
        vpane.setPadding(new Insets(5,5,5,5));
        vpane.setStyle("-fx-border-color: green");
        RadioButton checkBgd = new RadioButton("Dark Mode");
        
        EventHandler<ActionEvent> handler = e -> {
            if (checkBgd.isSelected()) {
                bpane.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                bpane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        };
        checkBgd.setOnAction(handler);
        
        Button btn3 = new Button("History");
        vpane.getChildren().addAll(checkBgd, btn3);
        bpane.setRight(vpane);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void check(String guess) {
        if (!gameStart && guess.equals(word)) {
            Stage congraStage = new Stage();
            VBox vpaneWin = new VBox(10);
            Image image = new Image("Thumbs UP.png");
            ImageView imageViewer = new ImageView(image);
            imageViewer.setFitHeight(300);
            imageViewer.setFitWidth(300);
            Label congraLabel = new Label("CONGRAULATIONS");
            vpaneWin.getChildren().addAll(imageViewer, congraLabel);
            
            Scene sceneCongra = new Scene(vpaneWin, 300, 320);
            congraStage.setScene(sceneCongra);
            congraStage.showAndWait();
        } 
        if (!gameStart && !guess.equals(word)) { 
            Stage lostStage = new Stage();
            VBox vpaneLost = new VBox(10);
            Image image = new Image("Sad.jpg");
            ImageView imageViewer = new ImageView(image);
            imageViewer.setFitHeight(300);
            imageViewer.setFitWidth(300);
            Label lostLabel = new Label("Sorry, the word was " + word);
            vpaneLost.getChildren().addAll(imageViewer, lostLabel);
            
            Scene lostScene = new Scene(vpaneLost, 300, 330);
            lostStage.setScene(lostScene);
            lostStage.showAndWait();
        }
    }
    
    class Restart implements EventHandler<ActionEvent> {
        public void handle(ActionEvent ae) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 6; j++) {
                    labels[i][j].setText("");
                    labels[i][j].setStyle("-fx-background-color: white; -fx-border-color: black");
                    //System.out.println("adfaefea");
                    row = 0;
                    col = 0;
                    guessCounter = 6;
                }
            }
            word = Words.list.get(new Random().nextInt(Words.list.size()));
            System.out.println(word);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

