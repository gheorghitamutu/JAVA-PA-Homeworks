package lab07.view;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;

public class UsersWindow extends Application {

    private Color sceneColor = Color.CADETBLUE;
    private Color buttonColor = Color.IVORY;

    private String fontFamily = "verdana";
    private String stageTitle = "Scrabble";
    private String player01 = "Player01";
    private String player02 = "Player02";
    private String player03 = "Player03";
    private String player04 = "Player04";
    private String backButtonMessage = "Back";


    private int sceneWidth = 1280;
    private int sceneHeight = 960;
    private int buttonWidth = 300;
    private int buttonHeight = 60;
    private int backButtonX = 500;
    private int backButtonY = 470;
    private int buttonFontSize = 30;

    private Button backButton = new Button(backButtonMessage);
    private Group root = new Group();
    private ObservableList sceneObjectsList = root.getChildren();
    private Scene scene = new Scene(root ,sceneWidth, sceneHeight);

    @Override
    public void start(Stage usersStage) {

        //Creating play button
        backButton.setLayoutX(backButtonX);
        backButton.setLayoutY(backButtonY);
        backButton.setPrefSize(buttonWidth, buttonHeight);
        backButton.setFont(Font.font(fontFamily, FontPosture.REGULAR, buttonFontSize));
        backButton.setTextFill(buttonColor);

        // back event
        backButton.setOnMouseClicked((event -> {
            usersStage.hide();
            new MainWindow();
        }));

        //Setting the text object as a node to the group object
        sceneObjectsList.addAll(backButton);

        //setting color to the scene
        scene.setFill(sceneColor);

        //Setting the title to Stage.
        usersStage.setTitle(stageTitle);

        //Adding the scene to Stage
        usersStage.setScene(scene);

        //Displaying the contents of the stage
        usersStage.show();
    }
}
