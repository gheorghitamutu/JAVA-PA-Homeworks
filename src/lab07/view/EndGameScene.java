package lab07.view;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class EndGameScene {
    MainWindow mw = null;
    private Color sceneColor = Color.CADETBLUE;
    private Color buttonColor = Color.IVORY;

    private String fontFamily = "verdana";
    private String backButtonMessage = "Back to Main Menu";
    public static String gameOver = "";

    private int sceneWidth = 1280;
    private int sceneHeight = 960;
    private int endTextX = 300;
    private int endTextY = 200;
    private int backButtonY = 400;
    private int buttonFontSize = 30;

    private Text endText = new Text(gameOver);
    private Button backMainButton = new Button(backButtonMessage);
    private Group endSceneRoot = new Group();
    private ObservableList endSceneObjectsList = endSceneRoot.getChildren();
    private Scene endScene = new Scene(endSceneRoot, sceneWidth, sceneHeight);

    EndGameScene(MainWindow mw) {
        this.mw = mw;

        endText.setFont(Font.font(fontFamily, FontWeight.BOLD, FontPosture.REGULAR, buttonFontSize));
        endText.setFill(Color.WHITE);
        endText.setX(endTextX);
        endText.setY(endTextY);

        backMainButton.setFont(Font.font(fontFamily, FontWeight.BOLD, FontPosture.REGULAR, buttonFontSize));
        backMainButton.setTextFill(buttonColor);
        backMainButton.setLayoutX(endTextX);
        backMainButton.setLayoutY(backButtonY);

        backMainButton.setOnMouseClicked((event -> mw.setMainScene()));

        //Setting the text object as a node to the group object
        endSceneObjectsList.addAll(
                endText,
                backMainButton);

        //setting color to the mainScene
        endScene.setFill(sceneColor);
    }

    Scene getEndScene() {
        return endScene;
    }

    public void setEndScene(Scene getScene) {
        this.endScene = getScene;
    }
}
