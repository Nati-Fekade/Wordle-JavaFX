import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TestDordle extends Application {
    static Label[][] labels;
    static Label[][] labelsTop;
    static Label[][] labelsBottom;
    //static TextField tfield1 = new TextField();
    static TextField tfield2 = new TextField();
    //static Button btn1 = new Button("PLAY");
    static Button btn2 = new Button("PLAY");
    static Button btnResDor = new Button("RESTART");
    static Button btnInstDor = new Button("Instruction");
    static RadioButton btn5 = new RadioButton("Dark Mode");
    static int row = 0;
    static int col = 0;
    static boolean row_col = false;
    static boolean row_col_Dordle1 = false;
    static boolean row_col_Dordle2 = false;
    static boolean gameStart =true;
    static boolean gameStart2 =true;
    static String target1 = Words.list.get(new Random().nextInt(Words.list.size()));
    static String target2 = Words.list.get(new Random().nextInt(Words.list.size()));
    static int guessCounter = 6;
    
    public void start(Stage primaryStage) {
        BorderPane bpane = new BorderPane();
        
        GridPane gpane = new GridPane();
        gridCreator(gpane, labels);
        bpane.setCenter(gpane);
        
        CheckBox main = new CheckBox("Play");

        EventHandler<ActionEvent> handler = dordle -> {
            if (main.isSelected()) {
                VBox vpane = new VBox(5);
                GridPane gridTop = new GridPane();
                labelsTop = gridCreator(gridTop, labelsTop);
                GridPane gridBottom = new GridPane();
                labelsBottom = gridCreator(gridBottom, labelsBottom);
                
                HBox hpane = new HBox(10);
                hpane.getChildren().addAll(btnResDor, btnInstDor);
                hpane.setAlignment(Pos.CENTER);
                
                btnResDor.setOnAction(new RestartDordle());
                
                btnInstDor.setOnAction(new EventHandler<ActionEvent>(){
                    public void handle(ActionEvent ae) {
                        Stage instructionWindow = new Stage();
                        instructionWindow.initModality(Modality.APPLICATION_MODAL);
                        StackPane spane = new StackPane();
                        Text instruction = new Text("Welcome to Dordle!");
                        spane.getChildren().add(instruction);
                        Scene scene2 = new Scene(spane, 200, 200);
                        instructionWindow.setScene(scene2);
                        instructionWindow.showAndWait();
                    }
                });
                
                Label top = new Label("TOP");
                Label bottom = new Label("Bottom");
                
                vpane.getChildren().addAll(top, gridTop, bottom, gridBottom, tfield2, btn2, hpane);
                vpane.setAlignment(Pos.CENTER);
                
//                btn1.setOnAction(top -> {
//                    check(tfield1, labelsTop, target1);
//                });
                btn2.setOnAction(dordlePlay -> {
                    check(tfield2, labelsTop, labelsBottom, target1 ,target2);
                });
                
                bpane.setCenter(vpane);
            } else {
                gridCreator(gpane, labels);
                bpane.setCenter(gpane);
            }
        };
        main.setOnAction(handler);
        bpane.setRight(main);

        Scene scene = new Scene(bpane, 500, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    protected Label[][] gridCreator(GridPane grid, Label[][] cell) {
        grid.requestFocus();
        cell = new Label[5][6];
        for (int i = 0; i < cell.length; i++) {
            
            for (int j = 0; j < cell[i].length; j++) {
                cell[i][j] = new Label();
                cell[i][j].setStyle("-fx-background-color: white; -fx-border-color: black");
                cell[i][j].setPrefSize(40, 40);
                grid.add(cell[i][j], i, j);
            }
        }
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);
        
        return cell;
    }
    
    protected static void check(TextField tfield, Label[][] labels1, Label[][] labels2, String word1, String word2) {
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
        
        if (!gameStart && !gameStart2) {
            return;
        }
        if (!row_col) {
            for (int i = 0; i < tfield.getText().length(); i++) {
                if (gameStart) {
                    labels1[col][row].setText("" + tfield.getText().charAt(i));
                    labels1[col][row].setAlignment(Pos.CENTER);
                    
                    
                }
                if (gameStart2) {
                    labels2[col][row].setText("" + tfield.getText().charAt(i));
                    labels2[col][row].setAlignment(Pos.CENTER);
                }
                col++;
            }
        }
//        row_col = true;
//        row_col_Dordle1 = col == 5;    
//        row_col_Dordle2 = col == 5;
//        if (row_col && row_col_Dordle1 && row_col_Dordle2) {
            int greenCounter1 = 0;
            int greenCounter2 = 0;
            for (int i = 0; i < word1.length() && gameStart; i++) {
                System.out.println("asdfbdsfdasfgdsafgh");
                if (labels1[i][row].getText().equalsIgnoreCase("" + word1.charAt(i))) {
                    labels1[i][row].setStyle("-fx-background-color: green");
                    greenCounter1++;
                    if (greenCounter1 == 5) {
                        gameStart = false;
                    }
                } else if (word1.contains(labels1[i][row].getText().toLowerCase())) {
                    labels1[i][row].setStyle("-fx-background-color: yellow");
                } else {
                    labels1[i][row].setStyle("-fx-background-color: red");
                }
            }
            for (int i = 0; i < word2.length() && gameStart2; i++) {
                System.out.println("fdgsedgfedsfgd");
                if (labels2[i][row].getText().equalsIgnoreCase("" + word2.charAt(i))) {
                    labels2[i][row].setStyle("-fx-background-color: green");
                    greenCounter2++;
                    if (greenCounter2 == 5) {
                        gameStart2 = false;
                    }
                } else if (word2.contains(labels2[i][row].getText().toLowerCase())) {
                    labels2[i][row].setStyle("-fx-background-color: yellow");
                } else {
                    labels2[i][row].setStyle("-fx-background-color: red");
                }
            }
//            row_col_Dordle1 = false;
//            row_col_Dordle2 = false;
            row_col = false;
            col = 0;
            row++;
            guessCounter--;
            
            if(row == 6) {
                gameStart = false;
                gameStart2 = false;
            }
       // }

        System.out.println(tfield.getText());
        System.out.println(word1);
        System.out.println(word2);
        tfield.clear();
    }
    
    protected static void restartDordle(Label[][] labels) {
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
        target1 = Words.list.get(new Random().nextInt(Words.list.size()));
        System.out.println(target1);
        target2 = Words.list.get(new Random().nextInt(Words.list.size()));
        System.out.println(target2);
    }
    
    class RestartDordle implements EventHandler<ActionEvent> {
        public void handle(ActionEvent ae) {
            restartDordle(labelsTop);
            restartDordle(labelsBottom);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
