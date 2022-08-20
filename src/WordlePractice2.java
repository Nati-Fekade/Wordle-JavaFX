import java.util.ArrayList;
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
import javafx.scene.input.KeyCode;
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
 * Set on KEY implementation
 * 
 * This is a trial implementation of the Wordle game. This is not fully working game
 * 
 * @author Nathnael Fekade
 *
 */
public class WordlePractice2 extends Application {
    // Random word from our 'word' dictionary
    static String word = Words.list.get(new Random().nextInt(Words.list.size()));
    GridPane gpane = new GridPane();
    Label[][] labels;
    int row = 0;
    int col = 0;
    boolean row_col = false;
    int guessCounter = 6;
    int greenCounter = 0;
    boolean gameStart =true;
    Text text = new Text("Welcome to Jordle;)");
    static ArrayList<String> history = new ArrayList<>();
    
	public void start(Stage primaryStage) {
		BorderPane bpane = new BorderPane();
		Scene scene = new Scene(bpane, 400, 400, Color.WHITE);
		
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
		
		
		hpane.getChildren().addAll(text, btn1, btn2);
		bpane.setBottom(hpane);
		
		VBox vpane = new VBox(10);
		vpane.setAlignment(Pos.TOP_RIGHT);
		vpane.setPadding(new Insets(5,5,5,5));
		vpane.setStyle("-fx-border-color: green");
		RadioButton checkBgd = new RadioButton("Dark Mode");
		
		EventHandler<ActionEvent> handler = e -> {
		    if (checkBgd.isSelected()) {
		        bpane.setBackground(new Background(new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY)));
		    } else {
		        bpane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		    }
		};
		checkBgd.setOnAction(handler);
		
		Button btn3 = new Button("History");
		vpane.getChildren().addAll(checkBgd, btn3);
		bpane.setRight(vpane);
		
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
		
		gpane.requestFocus();
		scene.setOnKeyPressed(e -> {
		    gpane.requestFocus();
		    if (!gameStart) {
		        return;
		    }
		    gpane.requestFocus();
		    if (e.getCode().equals(KeyCode.BACK_SPACE)) {
		        if(col > 0) {
	               col--;
	               labels[col][row].setText("");
		        }
	        } else if (!row_col && e.getCode().isLetterKey()) {
		        labels[col][row].setText(e.getCode().toString());
		        labels[col][row].setAlignment(Pos.CENTER);
	            col++;
		    }		    
		   row_col = col == 5;
		   gpane.requestFocus();
		   if (!row_col && e.getCode().equals(KeyCode.ENTER)) {
		       System.out.println("adfewf");
		       Stage alertStage = new Stage();
		       VBox alert = new VBox(10);
		       alert.setAlignment(Pos.CENTER);
		       for (int i = 0; i < 5; i++) {
		           labels[i][row].setStyle("-fx-background-color: grey");
		       }
		       
		       Text alertText = new Text("Must be 5 characters");
		       alert.getChildren().add(alertText);
		       Scene alertScene = new Scene(alert, 200, 200);
		       alertStage.setScene(alertScene);
		       alertStage.showAndWait();
		       col = 0;
		       row++;
		       guessCounter--;
		       if (guessCounter == 0) {
		           text.setText("You Lost. The word was " + word);
		           gameStart = false;
		       }
		   }
		   gpane.requestFocus();
		   if (row_col && e.getCode().equals(KeyCode.ENTER)) {
		       System.out.println(word); 
		       gpane.requestFocus();
		       for (int i = 0; i < word.length(); i++) {
		           if (labels[i][row].getText().equalsIgnoreCase("" + word.charAt(i))) {
		               labels[i][row].setStyle("-fx-background-color: green");
		               greenCounter++;
		               if (greenCounter == 5) {
		                   text.setText("Congra");
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

		       gpane.requestFocus();
		       if (row == 6) {
                   text.setText("You Lost. The word was " + word);
                   gameStart = false;
               } else {
                   text.setText("You have " + guessCounter + " tries left.");
               }
		   }
		});

        
		gpane.setVgap(5);
        gpane.setHgap(5);
        gpane.setAlignment(Pos.CENTER);
		
        bpane.setCenter(gpane);
        
        gpane.requestFocus();
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	class Restart implements EventHandler<ActionEvent> {
	    public void handle(ActionEvent ae) {
	        gameStart = true;
	        for (int i = 0; i < 5; i++) {
	            for (int j = 0; j < 6; j++) {
	                labels[i][j].setText("");
	                labels[i][j].setStyle("-fx-background-color: white; -fx-border-color: black");
	                //System.out.println("adfaefea");
	                row = 0;
	                col = 0;
	                guessCounter = 6;
	                text.setText("The target word is changed. You have " + guessCounter + " tries left.");  
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



