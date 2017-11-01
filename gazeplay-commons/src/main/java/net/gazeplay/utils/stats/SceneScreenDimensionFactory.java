package net.gazeplay.utils.stats;

import javafx.scene.Scene;
import lombok.Getter;

public class SceneScreenDimensionFactory {

    @Getter
    private static final SceneScreenDimensionFactory singleton = new SceneScreenDimensionFactory();

    public ScreenDimension getScreenDimension(Scene scene) {
        int width = (int) scene.getWidth();
        int height = (int) scene.getHeight();
        return new ScreenDimension(width, height);
    }

}
