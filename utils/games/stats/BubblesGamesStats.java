package utils.games.stats;

import javafx.scene.Scene;

public class BubblesGamesStats extends ShootGamesStats{

    public BubblesGamesStats(Scene scene) {

        super(scene);
        this.gameName = "bubbles";
    }

    public void incNbGoals(){

        long last = System.currentTimeMillis() - beginTime;
        nbGoals++;
        length += last;
        lengthBetweenGoals.add((new Long(last)).intValue());
    }
}
