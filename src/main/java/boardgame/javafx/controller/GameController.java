package boardgame.javafx.controller;

import boardgame.result.GameResult;
import boardgame.result.GameResultDao;
import boardgame.state.BoardGame;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
public class GameController {

    @Inject
    private FXMLLoader fxmlLoader;

    @Inject
    private GameResultDao gameResultDao;

    private String player1Name;
    private String player2Name;
    private BoardGame gameState;
    private IntegerProperty steps = new SimpleIntegerProperty();
    private Instant startTime;
    private List<Image> stoneImages;

    @FXML
    private Label messageLabel;

    @FXML
    private GridPane gameGrid;

    @FXML
    private Label stepsLabel;

    @FXML
    private Label stopWatchLabel;

    private Timeline stopWatchTimeline;

    @FXML
    private Button resetButton;

    @FXML
    private Button giveUpButton;

    private BooleanProperty gameOver = new SimpleBooleanProperty();

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    private int row, col;

    @FXML
    public void initialize() {
        stoneImages = List.of(
                new Image(getClass().getResource("/image/EMPTY.png").toExternalForm()),
                new Image(getClass().getResource("/image/Green.png").toExternalForm()),
                new Image(getClass().getResource("/image/Red.png").toExternalForm()),
                new Image(getClass().getResource("/image/Yellow.png").toExternalForm())
        );
        stepsLabel.textProperty().bind(steps.asString());
        gameOver.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                log.info("Game is over");
                log.debug("Saving result to database...");
                try {
                    gameResultDao.persist(createGameResult());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                stopWatchTimeline.stop();
            }
        });
        resetGame();
    }

    private void resetGame() {
        gameState = new BoardGame(BoardGame.INITIAL);
        steps.set(0);
        startTime = Instant.now();
        gameOver.setValue(false);
        displayGameState();
        createStopWatch();
        Platform.runLater(() -> messageLabel.setText("Good luck, " + player1Name + ", " + player2Name + "!"));
    }

    private void displayGameState() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ImageView view = (ImageView) gameGrid.getChildren().get(i * 3 + j);
                log.trace("Image({}, {}) = {}", i, j, view.getImage());
                view.setImage(stoneImages.get(gameState.getBoard()[i][j].getValue()));
            }
        }
    }

    public void handleClickOnStone(MouseEvent mouseEvent) throws Exception {
        row = GridPane.getRowIndex((Node) mouseEvent.getSource());
        col = GridPane.getColumnIndex((Node) mouseEvent.getSource());
        log.debug("Stone ({}, {}) is picked", row, col);
        steps.set(steps.get() + 1);
        gameState.ClickStone(row, col);
        if (gameState.isFinished()) {
            if (steps.get() % 2 == 1) {
                gameOver.setValue(true);
                log.info("Player {} has solved the game in {} steps", player1Name, steps.get());
                messageLabel.setText("Congratulations, " + player1Name + "!");
                resetButton.setDisable(true);
                giveUpButton.setText("Finish");
            } else {
                gameOver.setValue(true);
                log.info("Player {} has solved the game in {} steps", player2Name, steps.get());
                messageLabel.setText("Congratulations, " + player2Name + "!");
                resetButton.setDisable(true);
                giveUpButton.setText("Finish");
            }
            displayGameState();
        }
    }

    public void handleResetButton(ActionEvent actionEvent) {
        log.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        log.info("Resetting game...");
        stopWatchTimeline.stop();
        resetGame();
    }

    public void handleGiveUpButton(ActionEvent actionEvent) throws IOException {
        String buttonText = ((Button) actionEvent.getSource()).getText();
        log.debug("{} is pressed", buttonText);
        if (buttonText.equals("Give Up")) {
            log.info("The game has been given up");
        }
        gameOver.setValue(true);
        log.info("Loading high scores scene...");
        fxmlLoader.setLocation(getClass().getResource("/fxml/highscores.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private GameResult createGameResult() throws Exception {
        GameResult result = null;
        if (steps.get() % 2 == 1) {
            result = GameResult.builder()
                    .player(player1Name)
                    .solved(gameState.isFinished())
                    .duration(Duration.between(startTime, Instant.now()))
                    .steps(steps.get() / 2)
                    .build();
        } else {
            result = GameResult.builder()
                    .player(player2Name)
                    .solved(gameState.isFinished())
                    .duration(Duration.between(startTime, Instant.now()))
                    .steps(steps.get() / 2)
                    .build();
        }
        return result;
    }

    private void createStopWatch() {
        stopWatchTimeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            long millisElapsed = startTime.until(Instant.now(), ChronoUnit.MILLIS);
            stopWatchLabel.setText(DurationFormatUtils.formatDuration(millisElapsed, "HH:mm:ss"));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        stopWatchTimeline.setCycleCount(Animation.INDEFINITE);
        stopWatchTimeline.play();
    }

}
