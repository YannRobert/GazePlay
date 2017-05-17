package TestRajaa;

import gaze.GazeEvent;
import gaze.SecondScreen;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.image.*;

import java.util.Date;

/**
 * Created by initial on 17/03/2017.
 */
public class index extends Application {

    public static void main(String[] args) {

        Application.launch(TestRajaa.index.class, args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("TesT -- Rajaa");

        primaryStage.setFullScreen(true);

        Group root = new Group();

        Scene scene = new Scene(root, com.sun.glass.ui.Screen.getScreens().get(0)
                .getWidth()-100, com.sun.glass.ui.Screen.getScreens().get(0).getHeight()-100,
                Color.BLACK);

        launch(root, scene);

        primaryStage.setOnCloseRequest((WindowEvent we)-> System.exit(0));

        primaryStage.setScene(scene);

        primaryStage.show();

        SecondScreen secondScreen = SecondScreen.launch();
    }
    public static void launch(Group root, Scene scene) {
        new paneIndex(root,scene);
    }

}
