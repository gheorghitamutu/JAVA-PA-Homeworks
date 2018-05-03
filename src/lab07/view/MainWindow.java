package lab07.view;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainWindow extends Application {

    private Color sceneColor = Color.CADETBLUE;
    private Color buttonColor = Color.IVORY;

    private String fontFamily = "verdana";
    private String stageTitle = "Scrabble";
    private String welcomeMessage = "Welcome to Scrabble";
    private String playButtonMessage = "Start Game";
    private String exitButtonMessage = "Exit Game";

    private int sceneWidth = 1280;
    private int sceneHeight = 960;
    private int welcomeTextX = 240;
    private int welcomtTextY = 380;
    private int buttonWidth = 300;
    private int buttonHeight = 60;
    private int playButtonX = 500;
    private int playButtonY = 470;
    private int exitButtonX = 500;
    private int exitButtonY = 580;
    private int buttonFontSize = 30;
    private int welcomeMessageFontSize = 70;

    private Text welcomeText = new Text(welcomeMessage);
    private Button playButton = new Button(playButtonMessage);
    private Button exitButton = new Button(exitButtonMessage);
    private Group mainSceneRoot = new Group();
    private ObservableList mainSceneObjectsList = mainSceneRoot.getChildren();
    private Scene mainScene = new Scene(mainSceneRoot,sceneWidth, sceneHeight);

    private void setMainSceneElements(Stage stage) {
        //Creating a welcome text
        welcomeText.setFont(Font.font(fontFamily, FontWeight.BOLD, FontPosture.REGULAR, welcomeMessageFontSize));
        welcomeText.setX(welcomeTextX);
        welcomeText.setY(welcomtTextY);
        welcomeText.setFill(Color.WHITE);

        //Creating play button
        playButton.setLayoutX(playButtonX);
        playButton.setLayoutY(playButtonY);
        playButton.setPrefSize(buttonWidth, buttonHeight);
        playButton.setFont(Font.font(fontFamily, FontPosture.REGULAR, buttonFontSize));
        playButton.setTextFill(buttonColor);

        //Creating exit button
        exitButton.setLayoutX(exitButtonX);
        exitButton.setLayoutY(exitButtonY);
        exitButton.setPrefSize(buttonWidth, buttonHeight);
        exitButton.setFont(Font.font(fontFamily, FontPosture.REGULAR, buttonFontSize));
        exitButton.setTextFill(buttonColor);

        // play event
        playButton.setOnMouseClicked((event -> {
            stage.setScene(usersScene);
        }));

        // exit event
        exitButton.setOnMouseClicked((event -> System.exit(0)));

        //Setting the text object as a node to the group object
        mainSceneObjectsList.addAll(welcomeText, playButton, exitButton);

        //setting color to the mainScene
        mainScene.setFill(sceneColor);

    }

    private String player01 = "Player01";
    private String player02 = "Player02";
    private String player03 = "Player03";
    private String player04 = "Player04";
    private String usersMessage = "Pick your names";
    private String playerTextFieldMessage = "Enter Your Name";
    private String usersPlayGameButtonMessage = "Play";
    private String backButtonMessage = "Back";

    private int backButtonX = 540;
    private int backButtonY = 780;
    private int usersPlayGameButtonY = 680;
    private int usersTextX = 510;
    private int usersTextY = 180;
    private int playerGridVGap = 20;
    private int playerGridHGap = 60;
    private int playerFontSize = 40;

    private Text usersText = new Text(usersMessage);
    private Text player01Text = new Text(player01);
    private Text player02Text = new Text(player02);
    private Text player03Text = new Text(player03);
    private Text player04Text = new Text(player04);
    private TextField player01TextField = new TextField();
    private TextField player02TextField = new TextField();
    private TextField player03TextField = new TextField();
    private TextField player04TextField = new TextField();
    private Button usersPlayGameButton = new Button(usersPlayGameButtonMessage);
    private Button backButton = new Button(backButtonMessage);
    private GridPane playersGrid = new GridPane();
    private Insets playerGridInsets = new Insets(220, 10, 10, 160);
    private Group usersSceneRoot = new Group();
    private ObservableList usersSceneObjectsList = usersSceneRoot.getChildren();
    private Scene usersScene = new Scene(usersSceneRoot,sceneWidth, sceneHeight);

    private void setUsersSceneElements(Stage stage) {
        //Creating back button
        backButton.setLayoutX(backButtonX);
        backButton.setLayoutY(backButtonY);
        backButton.setPrefSize(buttonWidth, buttonHeight);
        backButton.setFont(Font.font(fontFamily, FontPosture.REGULAR, buttonFontSize));
        backButton.setTextFill(buttonColor);

        // back event
        backButton.setOnMouseClicked((event -> {
            stage.setScene(mainScene);
            player01TextField.clear();
            player02TextField.clear();
            player03TextField.clear();
            player04TextField.clear();
        }));

        //Creating play game button
        usersPlayGameButton.setLayoutX(backButtonX);
        usersPlayGameButton.setLayoutY(usersPlayGameButtonY);
        usersPlayGameButton.setPrefSize(buttonWidth, buttonHeight);
        usersPlayGameButton.setFont(Font.font(fontFamily, FontPosture.REGULAR, buttonFontSize));
        usersPlayGameButton.setTextFill(buttonColor);

        // play event
        usersPlayGameButton.setOnMouseClicked((event -> {
            // should change the stage here
            System.out.println(player01TextField.getText().length() == 0 ? "AI Player!" : player01TextField.getText());
            player01TextField.clear();
            System.out.println(player02TextField.getText().length() == 0 ? "AI Player!" : player02TextField.getText());
            player02TextField.clear();
            System.out.println(player03TextField.getText().length() == 0 ? "AI Player!" : player03TextField.getText());
            player03TextField.clear();
            System.out.println(player04TextField.getText().length() == 0 ? "AI Player!" : player04TextField.getText());
            player04TextField.clear();
        }));

        //Creating a users text
        usersText.setFont(Font.font(fontFamily, FontWeight.BOLD, FontPosture.REGULAR, playerFontSize));
        usersText.setX(usersTextX);
        usersText.setY(usersTextY);
        usersText.setFill(Color.WHITE);

        playersGrid.setPadding(playerGridInsets);
        playersGrid.setVgap(playerGridVGap);
        playersGrid.setHgap(playerGridHGap);

        //Creating a player01 text
        player01Text.setFont(Font.font(fontFamily, FontWeight.BOLD, FontPosture.REGULAR, playerFontSize));
        player01Text.setFill(Color.WHITE);

        //Creating a player01 text field
        player01TextField.setFont(Font.font(fontFamily, FontPosture.REGULAR, playerFontSize));
        player01TextField.setPromptText(playerTextFieldMessage);

        //Creating a player02 text
        player02Text.setFont(Font.font(fontFamily, FontWeight.BOLD, FontPosture.REGULAR, playerFontSize));
        player02Text.setFill(Color.WHITE);

        //Creating a player02 text field
        player02TextField.setFont(Font.font(fontFamily, FontPosture.REGULAR, playerFontSize));
        player02TextField.setPromptText(playerTextFieldMessage);

        //Creating a player03 text
        player03Text.setFont(Font.font(fontFamily, FontWeight.BOLD, FontPosture.REGULAR, playerFontSize));
        player03Text.setFill(Color.WHITE);

        //Creating a player03 text field
        player03TextField.setFont(Font.font(fontFamily, FontPosture.REGULAR, playerFontSize));
        player03TextField.setPromptText(playerTextFieldMessage);

        //Creating a player04 text
        player04Text.setFont(Font.font(fontFamily, FontWeight.BOLD, FontPosture.REGULAR, playerFontSize));
        player04Text.setFill(Color.WHITE);

        //Creating a player04 text field
        player04TextField.setFont(Font.font(fontFamily, FontPosture.REGULAR, playerFontSize));
        player04TextField.setPromptText(playerTextFieldMessage);

        GridPane.setConstraints(player01Text, 0, 0);
        playersGrid.getChildren().add(player01Text);
        GridPane.setConstraints(player01TextField, 1, 0);
        playersGrid.getChildren().add(player01TextField);

        GridPane.setConstraints(player02Text, 0, 1);
        playersGrid.getChildren().add(player02Text);
        GridPane.setConstraints(player02TextField, 1, 1);
        playersGrid.getChildren().add(player02TextField);

        GridPane.setConstraints(player03Text, 0, 2);
        playersGrid.getChildren().add(player03Text);
        GridPane.setConstraints(player03TextField, 1, 2);
        playersGrid.getChildren().add(player03TextField);

        GridPane.setConstraints(player04Text, 0, 3);
        playersGrid.getChildren().add(player04Text);
        GridPane.setConstraints(player04TextField, 1, 3);
        playersGrid.getChildren().add(player04TextField);

        //Setting the text object as a node to the group object
        usersSceneObjectsList.addAll(
                usersText,
                playersGrid,
                usersPlayGameButton,
                backButton);

        //setting color to the scene
        usersScene.setFill(sceneColor);
    }

    @Override
    public void start(Stage stage) {
        setMainSceneElements(stage);
        setUsersSceneElements(stage);

        //Setting the title to Stage.
        stage.setTitle(stageTitle);

        //Adding the mainScene to Stage
        stage.setScene(mainScene);

        //Displaying the contents of the stage
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
