package net.gazeplay;

import com.sun.glass.ui.Screen;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.gazeplay.commons.gaze.configuration.Configuration;
import net.gazeplay.commons.gaze.configuration.ConfigurationBuilder;
import net.gazeplay.commons.utils.Bravo;
import net.gazeplay.commons.utils.HomeButton;
import net.gazeplay.commons.utils.stats.Stats;
import net.gazeplay.commons.utils.stats.StatsDisplay;

@Slf4j
public class GameContext extends GraphicalContext {

    public static GameContext newInstance(GazePlay gazePlay) {
        Group root = new Group();

        final Screen screen = Screen.getScreens().get(0);
        log.info("Screen size: {} x {}", screen.getWidth(), screen.getHeight());

        Scene scene = new Scene(root, screen.getWidth(), screen.getHeight(), Color.BLACK);
        return new GameContext(gazePlay, root, scene);
    }

    private GameContext(GazePlay gazePlay, Group root, Scene scene) {
        super(gazePlay, root, scene);
    }

    public void createHomeButtonInGameScreen(@NonNull GazePlay gazePlay, @NonNull Stats stats) {

        double width = scene.getWidth() / 10;
        double height = width;
        double X = scene.getWidth() * 0.9;
        double Y = scene.getHeight() - height * 1.1;

        HomeButton homeButton = new HomeButton(X, Y, width, height);

        EventHandler<Event> homeEvent = new EventHandler<javafx.event.Event>() {
            @Override
            public void handle(javafx.event.Event e) {

                if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {

                    scene.setCursor(Cursor.WAIT); // Change cursor to wait style

                    log.info("stats = " + stats);

                    StatsContext statsContext = StatsContext.newInstance(gazePlay);

                    Configuration config = ConfigurationBuilder.createFromPropertiesResource().build();
                    StatsDisplay.displayStats(gazePlay, stats, statsContext, config);

                    gazePlay.onDisplayStats(statsContext);

                    scene.setCursor(Cursor.DEFAULT); // Change cursor to default style
                }
            }
        };

        homeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, homeEvent);

        root.getChildren().add(homeButton);

        this.homeButton = homeButton;
    }

    public void playWinTransition(long delay, EventHandler<ActionEvent> onFinishedEventHandler) {
        homeButton.setVisible(false);

        Bravo bravo = Bravo.getBravo();
        getChildren().add(bravo);
        bravo.playWinTransition(scene, delay, onFinishedEventHandler);
    }

}
