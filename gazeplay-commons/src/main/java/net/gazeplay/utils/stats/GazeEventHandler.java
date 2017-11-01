package net.gazeplay.utils.stats;

import gaze.GazeEvent;
import javafx.event.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GazeEventHandler implements EventHandler<GazeEvent> {

    private final Stats stats;

    @Override
    public void handle(GazeEvent e) {
        stats.getHeatMapState().appendToHeatMap((int) e.getX(), (int) e.getY());
    }
}
