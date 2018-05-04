package lab07.view;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import lab07.model.TimeKeeper;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;

public class GameScene {
    MainWindow mw;
    TimeKeeper tk;

    private Color sceneColor = Color.CADETBLUE;
    private Color buttonColor = Color.IVORY;

    private String fontFamily = "verdana";
    private String allowedChars = "qwertyuiopasdfghjklzxcvbnm ";
    private String promptText = "how many letters";
    private String extractButtonMessage = "Replace Letters";
    private String passButtonMessage = "Pass This Turn";
    private String submitWordButtonMessage = "Submit Word";
    private String exitButtonMessage = "Quit Game";

    private int sceneWidth = 1280;
    private int sceneHeight = 960;
    private int gameTimeKeeperTextX = 30;
    private int gameTimeKeeperTextY = 50;
    private int matrixSize = 15;
    private int tileSize = 45;
    private int maxNumberOfCharsInTile = 1;
    private int gameGridVGap = 20;
    private int gameGridHGap = 60;
    private int boardTileGridVGap = 10;
    private int boardTileGridHGap = 10;
    private int howManyTextWidth = 310;
    private int tileFontSize = 20;
    private int buttonFontSize = 30;

    private Text gameTimeKeeperText = new Text();
    private ArrayList<TextField> fieldMatrixText = new ArrayList<>();

    private Text userTilesText = new Text();
    private Button replaceButton = new Button(extractButtonMessage);
    private TextField howManyText = new TextField();
    private Button passButton = new Button(passButtonMessage);
    private Button submitWordButton = new Button(submitWordButtonMessage);
    private Button exitButton = new Button(exitButtonMessage);
    private GridPane gameSceneGrid = new GridPane();
    private Insets gameSceneGridInsets = new Insets(10, 10, 10, 10);
    private GridPane boardTileSceneGrid = new GridPane();
    private Insets boardTileGridInsets = new Insets(100, 20, 20, 20);
    private GridPane menuSceneGrid = new GridPane();
    private Insets menuGridInsets = new Insets(100, 20, 20, 0);
    private Group gameSceneRoot = new Group();
    private ObservableList gameSceneObjectsList = gameSceneRoot.getChildren();

    private Scene gameScene = new Scene(gameSceneRoot,sceneWidth, sceneHeight);

    GameScene(MainWindow mw, TimeKeeper tk) {
        this.mw = mw;
        this.tk = tk;

        gameTimeKeeperText.setFont(Font.font(fontFamily, FontWeight.BOLD, FontPosture.REGULAR, buttonFontSize));
        gameTimeKeeperText.setFill(Color.WHITE);
        gameTimeKeeperText.setX(gameTimeKeeperTextX);
        gameTimeKeeperText.setY(gameTimeKeeperTextY);
        tk.setGameTimeKeeperText(gameTimeKeeperText);

        IntStream.range(0, matrixSize*matrixSize).forEachOrdered(n -> {

            TextField ts = new TextField();
            ts.setPrefWidth(tileSize);
            ts.setPrefHeight(tileSize);
            ts.setFont(Font.font(fontFamily, FontWeight.BOLD, FontPosture.REGULAR, tileFontSize));

            ts.setOnKeyTyped(event -> {
                    if (ts.getText().length() + 1 > maxNumberOfCharsInTile ) event.consume();

                    if (!event.getCharacter().isEmpty() &&
                            ts.isEditable() &&
                            allowedChars.contains(event.getCharacter().toLowerCase()))
                    {
                        if(event.getCharacter().equals(" ")) ts.setText("_");
                        else ts.setText(event.getCharacter().toUpperCase());

                        ts.positionCaret(1);
                    }

                    event.consume();
            });

            fieldMatrixText.add(ts);
        });

        gameSceneGrid.setPadding(gameSceneGridInsets);
        gameSceneGrid.setVgap(gameGridVGap);
        gameSceneGrid.setHgap(gameGridHGap);

        boardTileSceneGrid.setPadding(boardTileGridInsets);
        boardTileSceneGrid.setVgap(boardTileGridVGap);
        boardTileSceneGrid.setHgap(boardTileGridHGap);

        int xMatrixIndex = 0;
        int yMatrixIndex = 0;
        for (TextField ts : fieldMatrixText) {
            GridPane.setConstraints(ts, xMatrixIndex, yMatrixIndex);
            boardTileSceneGrid.getChildren().add(ts);

            xMatrixIndex++;
            if (xMatrixIndex == matrixSize) {
                yMatrixIndex++;
                if(yMatrixIndex == 15) {
                    yMatrixIndex = 0;
                }
                xMatrixIndex = 0;
            }
        }

        userTilesText.setFont(Font.font(fontFamily, FontWeight.BOLD, FontPosture.REGULAR, buttonFontSize));
        userTilesText.setFill(Color.WHITE);
        tk.setUserTilesText(userTilesText);

        replaceButton.setFont(Font.font(fontFamily, FontWeight.BOLD, FontPosture.REGULAR, buttonFontSize));
        replaceButton.setPrefWidth(howManyTextWidth);
        replaceButton.setTextFill(buttonColor);

        howManyText.setFont(Font.font(fontFamily, FontPosture.REGULAR, buttonFontSize));
        howManyText.setPrefWidth(howManyTextWidth);
        howManyText.setPromptText(promptText);
        howManyText.setAlignment(Pos.CENTER);

        replaceButton.setOnMouseClicked((event -> {
            if(!mw.game.getCurrentPlayer().getPlayerType().equals("AI")) {
                mw.game.getCurrentPlayer().extractMany(howManyText.getText());
                howManyText.clear();
            }}));

        passButton.setFont(Font.font(fontFamily, FontWeight.BOLD, FontPosture.REGULAR, buttonFontSize));
        passButton.setPrefWidth(howManyTextWidth);
        passButton.setTextFill(buttonColor);

        passButton.setOnMouseClicked((event -> {
            if(!mw.game.getCurrentPlayer().getPlayerType().equals("AI")) mw.game.getCurrentPlayer().pass();}));

        submitWordButton.setFont(Font.font(fontFamily, FontWeight.BOLD, FontPosture.REGULAR, buttonFontSize));
        submitWordButton.setPrefWidth(howManyTextWidth);
        submitWordButton.setTextFill(buttonColor);

        // submit word event
        submitWordButton.setOnMouseClicked((event -> {
            if(!mw.game.getCurrentPlayer().getPlayerType().equals("AI")) {
                List<Map.Entry<String, Integer>> letters = new ArrayList<>();
                fieldMatrixText.forEach(field -> {
                    if (!field.getText().isEmpty() && field.isEditable())
                        letters.add(new AbstractMap.SimpleEntry(field.getText(), fieldMatrixText.indexOf(field)));
                });

                if (letters.isEmpty()) return;

                // check if word if correct
                StringBuilder playerInput = new StringBuilder();
                for(AbstractMap.Entry<String, Integer> se : letters) playerInput.append(se.getKey());

                if (mw.game.getCurrentPlayer().createWord(playerInput.toString())) {
                    Color randomColor = Color.color(Math.random(), Math.random(), Math.random());
                    fieldMatrixText.forEach(field -> {
                        if (!field.getText().isEmpty() && field.isEditable()) {
                            field.setEditable(false);
                            randomStileTile(field, randomColor);
                        }
                    });

                    letters.forEach(entry -> System.out.print(entry.getKey()));
                    System.out.println();
                }
                else {
                    fieldMatrixText.forEach(field -> {
                        if (!field.getText().isEmpty() && field.isEditable()) {
                            field.clear();
                        }
                    });
                }
            }
        }));

        exitButton.setFont(Font.font(fontFamily, FontWeight.BOLD, FontPosture.REGULAR, buttonFontSize));
        exitButton.setPrefWidth(howManyTextWidth);
        exitButton.setTextFill(buttonColor);

        exitButton.setOnMouseClicked((event -> {
            Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Exit game?",
                    ButtonType.YES,
                    ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                try {
                    sleep(1000);
                    mw.game.getBag().clearBag();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }));

        menuSceneGrid.setPadding(menuGridInsets);
        menuSceneGrid.setVgap(boardTileGridVGap);
        menuSceneGrid.setHgap(boardTileGridHGap);
        menuSceneGrid.setAlignment(Pos.BOTTOM_CENTER);

        GridPane.setHalignment(userTilesText, HPos.CENTER);
        GridPane.setConstraints(userTilesText, 0, 0);
        menuSceneGrid.getChildren().add(userTilesText);
        GridPane.setHalignment(replaceButton, HPos.CENTER);
        GridPane.setConstraints(replaceButton, 0, 1);
        menuSceneGrid.getChildren().add(replaceButton);
        GridPane.setHalignment(howManyText, HPos.CENTER);
        GridPane.setConstraints(howManyText, 0, 2);
        menuSceneGrid.getChildren().add(howManyText);
        GridPane.setHalignment(passButton, HPos.CENTER);
        GridPane.setConstraints(passButton, 0, 3);
        menuSceneGrid.getChildren().add(passButton);
        GridPane.setHalignment(submitWordButton, HPos.CENTER);
        GridPane.setConstraints(submitWordButton, 0, 4);
        menuSceneGrid.getChildren().add(submitWordButton);
        GridPane.setHalignment(exitButton, HPos.CENTER);
        GridPane.setConstraints(exitButton, 0, 5);
        menuSceneGrid.getChildren().add(exitButton);

        GridPane.setConstraints(boardTileSceneGrid, 0, 0);
        gameSceneGrid.getChildren().add(boardTileSceneGrid);
        GridPane.setConstraints(menuSceneGrid, 1, 0);
        gameSceneGrid.getChildren().add(menuSceneGrid);


        //Setting the text object as a node to the group object
        gameSceneObjectsList.addAll(
                gameTimeKeeperText,
                gameSceneGrid);

        //setting color to the mainScene
        gameScene.setFill(sceneColor);
    }

    public Scene getGameScene() {
        return gameScene;
    }

    public void setGameScene(Scene gameScene) {
        this.gameScene = gameScene;
    }

    public Text getGameTimeKeeperText() {
        return gameTimeKeeperText;
    }

    public void setGameTimeKeeperText(Text gameTimeKeeperText) {
        this.gameTimeKeeperText = gameTimeKeeperText;
    }

    public void setTimeKeeper(TimeKeeper timeKeeper) {
        this.tk = timeKeeper;
    }

    public void setWordRandomlyOnTheBoard(String wordToAdd) {
        wordToAdd = wordToAdd.replace("[", "");
        wordToAdd = wordToAdd.replace("]", "");

        List<TextField> emptyTiles = new ArrayList<>();
        int countTiles = 0;
        for(int x = 0; x < matrixSize; x++) {
            if(countTiles == wordToAdd.length()) break;
            countTiles = 0;
            emptyTiles.clear();
            for(int y = 0; y < matrixSize; y++) {
                if (fieldMatrixText.get(x * matrixSize + y).isEditable()) {
                    emptyTiles.add(fieldMatrixText.get(x * matrixSize + y));
                    countTiles++;
                    if(countTiles == wordToAdd.length()) break;
                }
                else {
                    countTiles = 0;
                    emptyTiles.clear();
                }
            }
        }

        if(countTiles != wordToAdd.length()) {
            for(int x = 0; x < matrixSize; x++) {
                if(countTiles == wordToAdd.length()) break;
                countTiles = 0;
                emptyTiles.clear();
                for(int y = 0; y < matrixSize; y++) {
                    int index = y * matrixSize + x;
                    if (fieldMatrixText.get(index).isEditable()) {
                        emptyTiles.add(fieldMatrixText.get(index));
                        countTiles++;
                        if(countTiles == wordToAdd.length()) break;
                    }
                    else {
                        countTiles = 0;
                        emptyTiles.clear();
                    }
                }
            }
        }

        if(countTiles == wordToAdd.length()) {
            int tileIndex = 0;
            Color randomColor = Color.color(Math.random(), Math.random(), Math.random());
            for(char c : wordToAdd.toCharArray()) {
                synchronized (emptyTiles.get(tileIndex)) {
                    emptyTiles.get(tileIndex).setText(("" + c).toUpperCase());
                    emptyTiles.get(tileIndex).setEditable(false);
                    randomStileTile(emptyTiles.get(tileIndex), randomColor);
                    tileIndex++;
                }
            }
        }
    }

    synchronized private void randomStileTile(TextField tile, Color color) {
        String style = "-fx-text-inner-color:" + color.toString().replace("0x", "#") + ";\n";
        style += "-fx-border-width: 2px ;\n";
        style += "-fx-border-color: " + color.toString().replace("0x", "#") + ";";
        tile.setStyle(style);
    }

    public Text getUserTilesText() {
        return userTilesText;
    }

    public void setUserTilesText(Text userTilesText) {
        this.userTilesText = userTilesText;
    }
}

