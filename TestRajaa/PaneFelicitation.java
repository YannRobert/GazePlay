package TestRajaa;

import bubbles.Bubble;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.*;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import javafx.scene.layout.Pane;
import java.util.*;

import java.util.Collection;


/**
 * Created by initial on 16/05/2017.
 */
public class PaneFelicitation {

    public PaneFelicitation(Group root, Scene scene, Pane myPane, double X, double Y){
        myPane.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0);");

        System.out.println(" *********************** FOUND "+myPane +" Y "+Y+ " X "+X);

        Collection c=root.getChildren();
        Iterator iterator = c.iterator();

        while (iterator.hasNext()) {
            Object o=iterator.next();
            if( o instanceof Pane && o != myPane){
                iterator.remove();
                root.getChildren().remove(o);
            }

        }

        double widthEcran = com.sun.glass.ui.Screen.getScreens().get(0).getWidth();
        double heightEcran = com.sun.glass.ui.Screen.getScreens().get(0).getHeight();
        double partW = widthEcran / 5;
        double partH = heightEcran / 5;
        Path path = new Path();
        MoveTo moveTo = new MoveTo();

        moveTo.setX(X);
        moveTo.setY(-1);

        LineTo lineTo = new LineTo();

             lineTo.setX((widthEcran/2));

        lineTo.setY((heightEcran/2)+100);
        path.getElements().add(moveTo);
        path.getElements().add(lineTo);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(3000));
        pathTransition.setPath(path);
        pathTransition.setNode(myPane);
        pathTransition.play();
        double hF=myPane.getHeight()*2;
        double wF=myPane.getWidth()*2;
        Timeline timeline=new Timeline();

        c=myPane.getChildren();
        iterator = c.iterator();
        Rectangle rE=null;
        while (iterator.hasNext()) {
            Object o=iterator.next();
            if( o instanceof Rectangle){
                rE=(Rectangle)o;

            }

        }
        rE.setStroke(Color.BLACK);
        timeline.getKeyFrames().add(new KeyFrame
                (new Duration(3000),
                        new KeyValue(rE.heightProperty(), hF)));
        timeline.getKeyFrames().add(new KeyFrame
                (new Duration(3000),
                        new KeyValue(rE.widthProperty(), wF)));




        FadeTransition ft = new FadeTransition(Duration.millis(3000), rE);
        ft.setFromValue(1.0);
        ft.setToValue(0.5);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();

        myPane.setPrefSize(wF,hF);
        Bubble bubble=new Bubble(scene,true);
        root.getChildren().add(bubble);

        timeline.play();


       System.out.println("FIN");





    }
}
