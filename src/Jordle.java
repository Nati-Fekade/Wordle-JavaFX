import java.util.Random;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * This is a simple, but long implementation for creating Jordle.
 * This class extends Application, that allows us to have access to JavaFx.
 * @author Nathnael Fekade
 * @version 1.0
 */
public class Jordle extends Application {
    static String word = Words.list.get(new Random().nextInt(Words.list.size()));
    static String target1 = Words.list.get(new Random().nextInt(Words.list.size()));
    static String target2 = Words.list.get(new Random().nextInt(Words.list.size()));
    static BorderPane bpane = new BorderPane(); //The main pane that holds the rest of the game
    static GridPane gpane = new GridPane();
    static Label[][] labels;
    static Label[][] labelsTop;
    static Label[][] labelsBottom;
    static TextField tfieldDor = new TextField();
    static Button btnPlayDor = new Button("PLAY");
    static Button btnResDor = new Button("RESTART");
    static Button btnInstDor = new Button("Instruction");
    static RadioButton btn5 = new RadioButton("Dark Mode");
    static RadioButton checkBgd = new RadioButton("Dark Mode");
    static CheckBox checkDordle = new CheckBox("DORDLE");
    static int row = 0;
    static int col = 0;
    static boolean rowCol = false;
    static int guessCounter = 6;
    static int greenCounter = 0;
    static boolean gameStart = true;
    static boolean gameStartDordle1 = true;
    static boolean gameStartDordle2 = true;
    static Text text = new Text("Welcome to Jordle;)");
    static Image icon = new Image("https://bit.ly/3Eim8hX");
    /**
     * Overrides the start method in order to set the whole gaming window.
     * @param primaryStage - it is the main stage of our window.
     */
    public void start(Stage primaryStage) {
        Scene scene = new Scene(bpane, 600, 750);
        HBox hpane = new HBox(20); //Horizontal pane that has buttons at the bottom.
        hpane.setAlignment(Pos.BASELINE_RIGHT);
        hpane.setPadding(new Insets(5, 5, 5, 5));
        hpane.setStyle("-fx-border-color: green");
        Button btnRes = new Button("Restart");
        btnRes.setOnAction(new RestartJordle()); //Inner class application for the Restart button
        Button btnInst = new Button("Instruction");
        btnInst.setOnAction(new EventHandler<ActionEvent>() { //Anonymous class for Instruction button
            public void handle(ActionEvent ae) {
                instructionWindow();
            }
        });
        hpane.getChildren().addAll(text, btnRes, btnInst);
        bpane.setBottom(hpane);
        gpane.requestFocus();
        labels = new Label[5][6]; // Creates a 5x6 cell grid for the words to display.
        for (int i = 0; i < labels.length; i++) {
            for (int j = 0; j < labels[i].length; j++) {
                labels[i][j] = new Label();
                labels[i][j].setStyle("-fx-background-color: white; -fx-border-color: black");
                labels[i][j].setPrefSize(75, 75);
                gpane.add(labels[i][j], i, j);
            }
        }
        gpane.requestFocus();
        scene.setOnKeyPressed(e -> { //Lambda expression application for the setOnKeyPressed
            gpane.requestFocus();
            if (!gameStart) {
                return;
            }
            gpane.requestFocus();
            if (e.getCode().equals(KeyCode.BACK_SPACE)) { // if the back space is clicked, it deletes the character
                if (col > 0) {
                    col--;
                    labels[col][row].setText("");
                }
            } else if (!rowCol && e.getCode().isLetterKey()) { //displays characters on the grid
                labels[col][row].setText(e.getCode().toString());
                labels[col][row].setFont(new Font("Bahnschrift SemiBold Condensed", 40));
                labels[col][row].setAlignment(Pos.CENTER);
                col++;
            }
            rowCol = col == 5;
            gpane.requestFocus();
            if (!rowCol && e.getCode().equals(KeyCode.ENTER)) {
                gpane.requestFocus();
                checkLength(); //If we click Enter without having 5 characters, then alert window opens
            }
            gpane.requestFocus();
            if (rowCol && e.getCode().equals(KeyCode.ENTER)) { //Checks if the characters match with the random word.
                gpane.requestFocus();
                checkCharacters();
            }
        });
        gpane.setVgap(5);
        gpane.setHgap(5);
        gpane.setAlignment(Pos.CENTER);
        gpane.requestFocus();
        bpane.setCenter(gpane); //add the grid to the center of the original border pane
        VBox vpane = new VBox(10);
        vpane.setAlignment(Pos.CENTER);
        vpane.setPadding(new Insets(5, 5, 5, 5));
        vpane.setStyle("-fx-border-color: green");
        vpane.getChildren().addAll(checkBgd, checkDordle);
        bpane.setRight(vpane);
        EventHandler<ActionEvent> handler = e -> {
            if (checkBgd.isSelected()) {
                bpane.setBackground(new Background(new BackgroundFill(Color.DARKGRAY,
                        CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                bpane.setBackground(new Background(new BackgroundFill(Color.WHITE,
                        CornerRadii.EMPTY, Insets.EMPTY)));
            }
        };
        checkBgd.setOnAction(handler);
        EventHandler<ActionEvent> dordleHandler = dordle -> {
            restartJordle();
            if (checkDordle.isSelected()) {
                dordle();
            } else {
                row = 0;
                word = Words.list.get(new Random().nextInt(Words.list.size())); //create a new target word
                gpane.requestFocus();
                labels = new Label[5][6];
                for (int i = 0; i < labels.length; i++) {
                    for (int j = 0; j < labels[i].length; j++) {
                        labels[i][j] = new Label();
                        labels[i][j].setStyle("-fx-background-color: white; -fx-border-color: black");
                        labels[i][j].setPrefSize(75, 75);
                        gpane.add(labels[i][j], i, j);
                    }
                }
                gameStart = true;
                gpane.requestFocus();
                bpane.setCenter(gpane);
            }
        };
        checkDordle.setOnAction(dordleHandler); //used to display Dordle game.
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Jordle");
        primaryStage.show();
    }
    /**
     * Inner class implementation for the Restart button for Dordle.
     */
    class RestartJordle implements EventHandler<ActionEvent> {
        public void handle(ActionEvent ae) {
            restartJordle();
            word = Words.list.get(new Random().nextInt(Words.list.size()));
            //System.out.println(word); //sanity check
        }
    }
    /**
     * Displays instruction window.
     */
    protected static void instructionWindow() {
        Stage instructionWindow = new Stage();
        instructionWindow.initModality(Modality.APPLICATION_MODAL);
        VBox spane = new VBox(10);
        Text welcome = new Text("Welcome to JORDLE!");
        welcome.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
        Text instruction = new Text("* Guess the HIDDEN five-letter word.\n"
                + "* You have six tries to guess a five-letter word.\n"
                + "* Hit 'Enter' when finished.\n"
                + "* The cell will turn GREEN if it is in the correct place.\n"
                + "* The cell will turn YELLOW if the character is in the word, but not in the correct place.\n"
                + "* The cell will turn GRAY if the character does not exist in the word.\n"
                + "* Click the restart button to change the target word and reset your tries.");
        instruction.setFont(new Font("Bahnschrift SemiBold Condensed", 15));
        spane.setAlignment(Pos.CENTER);
        spane.getChildren().addAll(welcome, instruction);
        Scene scene2 = new Scene(spane, 600, 200);
        instructionWindow.getIcons().add(icon);
        instructionWindow.setScene(scene2);
        instructionWindow.showAndWait();
    }
    /**
     * Checks the length of each characters entered.
     */
    protected static void checkLength() {
        Stage alertStage = new Stage();
        VBox alert = new VBox(10);
        alert.setAlignment(Pos.CENTER);
        for (int i = 0; i < 5; i++) {
            labels[i][row].setStyle("-fx-background-color: red");
        }
        ImageView alertImage = new ImageView(new Image("https://bit.ly/3xzJ0bf"));
        alertImage.setFitWidth(300);
        alertImage.setFitHeight(150);
        Text alertText = new Text("Must be 5 characters.\nExit to get back to the game.");
        alertText.setFont(new Font("Bahnschrift Bold Condensed", 15));
        alert.getChildren().addAll(alertImage, alertText);
        Scene alertScene = new Scene(alert, 300, 200);
        alertStage.getIcons().add(icon);
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
    /**
     * Checks the characters in Jordle and displays them with their corresponding color.
     */
    protected static void checkCharacters() {
      //System.out.println(word); // this is used to check my program
        gpane.requestFocus();
        for (int i = 0; i < word.length(); i++) {
            if (labels[i][row].getText().equalsIgnoreCase("" + word.charAt(i))) {
                labels[i][row].setStyle("-fx-background-color: green");
                greenCounter++;
                if (greenCounter == 5) { //if every character match...CONGRATULATIONS
                    text.setText("Congratulations, you found the word: " + word);
                    Image happy = new Image("https://bit.ly/3rzNivE");
                    Text happyText = new Text("CONGRATULATIONS. You found the word!");
                    smallStageCreator(happy, happyText);
                    gameStart = false;
                }
            } else if (word.contains(labels[i][row].getText().toLowerCase())) {
                labels[i][row].setStyle("-fx-background-color: yellow");
            } else {
                labels[i][row].setStyle("-fx-background-color: grey");
            }
        }
        rowCol = false;
        col = 0;
        row++;
        guessCounter--;
        //if we finish our tries and we did not guess the word...LOST
        gpane.requestFocus();
        if (row == 6) {
            Image sad = new Image("https://bit.ly/3jNF8eQ");
            Text sadText = new Text("You Lost. The word was " + word);
            smallStageCreator(sad, sadText);
            text.setText("You Lost. The word was " + word);
            gameStart = false;
        } else {
            text.setText("You have " + guessCounter + " tries left.");
        }
    }
    /**
     * Restarts the Jordle game.
     */
    protected static void restartJordle() {
        gameStart = true;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                labels[i][j].setText("");
                labels[i][j].setStyle("-fx-background-color: white; -fx-border-color: black");
                row = 0;
                col = 0;
                guessCounter = 6;
                text.setText("The target word is changed. You have " + guessCounter + " tries left.");
            }
        }
    }
    /**
     * This method creates a small stage that displays images and messages.
     * @param image - a picture.
     * @param message - a text message.
     */
    protected static void smallStageCreator(Image image, Text message) {
        Stage smallStage = new Stage();
        VBox vp = new VBox(10);
        ImageView iv = new ImageView(image);
        iv.setFitHeight(300);
        iv.setFitWidth(300);
        vp.getChildren().addAll(iv, message);
        vp.setAlignment(Pos.CENTER);
        Scene smallScene = new Scene(vp, 300, 350);
        smallStage.getIcons().add(icon);
        smallStage.setScene(smallScene);
        smallStage.show();
    }
    /**
     * This method is used to create each cell for the grid.
     * @param grid - a GridPane that is going to be centered.
     * @param cell - a 2d Label array for each words to be displayed.
     * @return the 2d Label array.
     */
    protected static Label[][] gridCreator(GridPane grid, Label[][] cell) {
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
    /**
     * Displays the dordle window.
     */
    protected static void dordle() {
        VBox vpaneDordle = new VBox(5);
        GridPane gridTop = new GridPane();
        labelsTop = gridCreator(gridTop, labelsTop);
        GridPane gridBottom = new GridPane();
        labelsBottom = gridCreator(gridBottom, labelsBottom);
        HBox hpaneDordle = new HBox(10);
        hpaneDordle.getChildren().addAll(btnResDor, btnInstDor);
        hpaneDordle.setAlignment(Pos.CENTER);
        btnResDor.setOnAction(event -> {
            restartDordle(labelsTop);
            restartDordle(labelsBottom);
        });
        btnInstDor.setOnAction(new EventHandler<ActionEvent>() { //Anonymous Inner Class implementation
            public void handle(ActionEvent ae) {
                Stage dordleInstructionWindow = new Stage();
                dordleInstructionWindow.initModality(Modality.APPLICATION_MODAL);
                VBox spane = new VBox(10);
                Text welcomeDordle = new Text("Welcome to DORDLE!");
                welcomeDordle.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
                Text instructionDordle = new Text("* DARE TO TAKE THE CHALLENGE.\n"
                        + "* You have six tries to guess a five-letter word for each grid.\n"
                        + "* Click 'Enter' when finished.\n"
                        + "* The cell will turn GREEN if it is in the correct place.\n"
                        + "* The cell will turn YELLOW if the character is in the word,"
                        + "but not in the correct place.\n"
                        + "* The cell will turn GRAY if the character does not exist in the word.\n"
                        + "* Click the restart button to change the target"
                        + "word and reset your tries of both grids.");
                instructionDordle.setFont(new Font("Bahnschrift SemiBold Condensed", 15));
                spane.setAlignment(Pos.CENTER);
                spane.getChildren().addAll(welcomeDordle, instructionDordle);
                Scene scene2 = new Scene(spane, 600, 200);
                dordleInstructionWindow.getIcons().add(icon);
                dordleInstructionWindow.setScene(scene2);
                dordleInstructionWindow.showAndWait();
            }
        });
        Text titleTop = new Text("TOP");
        Text titleBottom = new Text("Bottom");
        vpaneDordle.getChildren().addAll(titleTop, gridTop, titleBottom,
                gridBottom, tfieldDor, btnPlayDor, hpaneDordle);
        vpaneDordle.setAlignment(Pos.CENTER);
        vpaneDordle.requestFocus();
        btnPlayDor.setOnAction(top -> { //calls the check method that checks each characters
            check(tfieldDor, labelsTop, labelsBottom, target1, target2);
        });
        bpane.setCenter(vpaneDordle);
    }
    /**
     * This method is for Dordle that is used to check individual characters with the target word.
     * @param tfield - is a text field that we insert into.
     * @param labels - is a 2d Label array for the grid.
     * @param word - the target word we are comparing too.
     */
    protected static void check(TextField tfield, Label[][] labels1, Label[][] labels2, String word1, String word2) {
        text.setText("");
        while (tfield.getText().length() != 5) {
            Stage alertWindow = new Stage();
            alertWindow.initModality(Modality.APPLICATION_MODAL);
            StackPane spane2 = new StackPane();
            Text alertText = new Text("Must be 5 character words");
            spane2.getChildren().add(alertText);
            Scene scene3 = new Scene(spane2, 200, 200);
            alertWindow.setScene(scene3);
            alertWindow.showAndWait();
            tfield.clear();
            return;
        }
        if (!gameStartDordle1 && !gameStartDordle2) {
            return;
        }
        // Starts the game. If game start is false, we are going to stop.
        if (!rowCol) { // Displays the each characters to individual grids.
            for (int i = 0; i < tfield.getText().length(); i++) {
                if (gameStartDordle1) {
                    labels1[col][row].setText("" + tfield.getText().charAt(i));
                    labels1[col][row].setAlignment(Pos.CENTER);
                }
                if (gameStartDordle2) {
                    labels2[col][row].setText("" + tfield.getText().charAt(i));
                    labels2[col][row].setAlignment(Pos.CENTER);
                }
                col++;
            }
        }
        int greenCounter1 = 0;
        int greenCounter2 = 0;
        for (int i = 0; i < word1.length() && gameStartDordle1; i++) {
            if (labels1[i][row].getText().equalsIgnoreCase("" + word1.charAt(i))) {
                labels1[i][row].setStyle("-fx-background-color: green");
                greenCounter1++;
                if (greenCounter1 == 5) {
                    gameStartDordle1 = false;
                }
            } else if (word1.contains(labels1[i][row].getText().toLowerCase())) {
                labels1[i][row].setStyle("-fx-background-color: yellow");
            } else {
                labels1[i][row].setStyle("-fx-background-color: red");
            }
        }
        for (int i = 0; i < word2.length() && gameStartDordle2; i++) {
            if (labels2[i][row].getText().equalsIgnoreCase("" + word2.charAt(i))) {
                labels2[i][row].setStyle("-fx-background-color: green");
                greenCounter2++;
                if (greenCounter2 == 5) {
                    gameStartDordle1 = false;
                }
            } else if (word2.contains(labels2[i][row].getText().toLowerCase())) {
                labels2[i][row].setStyle("-fx-background-color: yellow");
            } else {
                labels2[i][row].setStyle("-fx-background-color: red");
            }
        }
        rowCol = false;
        col = 0;
        row++;
        guessCounter--;
        if (row == 6) { // If we finish our tries, the game ends.
            gameStartDordle1 = false;
            gameStartDordle2 = false;
        }
        System.out.println(tfield.getText());
        System.out.println(word1);
        System.out.println(word2);
        tfield.clear();
    }
    /**
     * This is a method that clears the grids and resets the target words.
     */
    protected static void restartDordle(Label[][] cell) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                cell[i][j].setText("");
                cell[i][j].setStyle("-fx-background-color: white; -fx-border-color: black");
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
    /**
     * This is the main method that lets us run this class.
     * @param args - String array argument that is being launched.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
