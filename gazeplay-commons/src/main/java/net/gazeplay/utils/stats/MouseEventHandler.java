package net.gazeplay.utils.stats;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MouseEventHandler implements EventHandler<MouseEvent> {

    private final Stats stats;

    @Override
    public void handle(MouseEvent e) {
        stats.getHeatMapState().appendToHeatMap((int) e.getX(), (int) e.getY());
    }
}
