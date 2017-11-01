package net.gazeplay;

import gaze.GazeUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import net.gazeplay.utils.stats.SceneScreenDimensionFactory;
import net.gazeplay.utils.stats.Stats;

public class GameSpec {

    public interface GameLauncher {

        Stats launchGame(GameSpec gameSpec, Stats stats, Scene scene, Group root, ChoiceBox<String> cbxGames);

    }

    public interface NewStatsFactory {

        Stats create();

    }

    private final String label;

    private final NewStatsFactory newStatsFactory;

    private final GameLauncher gameLauncher;

    public GameSpec(String label, NewStatsFactory newStatsFactory, GameLauncher gameLauncher) {
        this.label = label;
        this.newStatsFactory = newStatsFactory;
        this.gameLauncher = gameLauncher;
    }

    public String getLabel() {
        return label;
    }

    public Stats launch(Scene scene, Group root, ChoiceBox<String> cbxGames) {
        Stats stats = newStatsFactory.create();
        stats.start(SceneScreenDimensionFactory.getSingleton().getScreenDimension(scene));

        GazeUtils.addStats(stats);
        scene.addEventHandler(MouseEvent.ANY, stats.getRecordMouseMovements());

        return gameLauncher.launchGame(this, stats, scene, root, cbxGames);
    }
}
