package TestRajaa;

import gaze.GazeEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Date;

/**
 * Created by initial on 24/03/2017.
 */

public class paneIndex {
    public paneIndex(Group root,Scene scene) {

    /*Revoir le random*/

        double widthEcran = com.sun.glass.ui.Screen.getScreens().get(0).getWidth();
        double heightEcran = com.sun.glass.ui.Screen.getScreens().get(0).getHeight();
        double partW = widthEcran / 7;
        double partH = heightEcran / 8;

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(partH-20, partW, partH, partW));

        grid.getColumnConstraints().add(new ColumnConstraints(5 * partW));
        for (int i = 0; i < 7; i++)
            grid.getRowConstraints().add(new RowConstraints(partH));

        Image imgG = new Image("file:data/imgPerso/images/gazeplay.jpg");
        ImageView imageViewG = new ImageView(imgG);
        imageViewG.setFitHeight(partH);
        imageViewG.setFitWidth(5 * partW);
        grid.add(imageViewG, 0, 0);

        Image imgW = new Image("file:data/imgPerso/images/welcome.jpg");
        ImageView imageViewW = new ImageView(imgW);
        imageViewW.setFitHeight(partH);
        imageViewW.setFitWidth(5 * partW);
        grid.add(imageViewW, 0, 1);

        /* TextField userName = new TextField("");
        userName.setFont(Font.font("Tahoma", FontWeight.LIGHT, 20));

        userName.setId("ajouterNom");
        userName.setStyle("-fx-border-color: #E8D630;");
        grid.add(userName, 0, 1);
        */
        Image imgA = new Image("file:data/imgPerso/images/ajouterPh.jpg");
        ImageView imageViewA = new ImageView(imgA);
        imageViewA.setFitHeight(partH/1.5);
        imageViewA.setFitWidth(4 * partW);

        Button buttonAddIm = new Button("", imageViewA);
        buttonAddIm.setId("ajouterImg");
        buttonAddIm.setBackground(new Background(new BackgroundFill(
                Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        buttonAddIm.setStyle("-fx-border-color: #E8D630;-fx-border-radius: 10px; ");
        buttonAddIm.setMaxWidth(5 * partW);
        buttonAddIm.setMaxHeight(partH);
        grid.add(buttonAddIm, 0, 3);

        Image img = new Image("file:data/imgPerso/images/jouer.jpg");
        ImageView imageView = new ImageView(img);
        imageView.setFitHeight(partH/2);
        imageView.setFitWidth(4 * partW);

        Button buttonPlay = new Button("", imageView);
        buttonPlay.setId("play");
        buttonPlay.setBackground(new Background(new BackgroundFill(
                Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        buttonPlay.setStyle("-fx-border-color: #E8D630;-fx-border-radius: 10px; ");
        buttonPlay.setMaxWidth(5 * partW);
        buttonPlay.setMaxHeight(partH);


        EventHandler<Event> enterEvent;
        enterEvent = new EventHandler<Event>() {
            long timeEntry = -1, timMin = 1000;
            long s = 0;
            Button bE = null;


            @Override
            public void handle(Event e) {

                if (e.getTarget() instanceof javafx.scene.control.Button) {
                    bE = (Button) e.getTarget();
                }

                if (e.getEventType() == MouseEvent.MOUSE_ENTERED || e.getEventType() == GazeEvent.GAZE_ENTERED) {
                    //System.out.println("MOUSE_ENTERED"+e.getTarget());
                    timeEntry = (new Date()).getTime();
                }
                if (e.getEventType() == MouseEvent.MOUSE_EXITED || e.getEventType() == GazeEvent.GAZE_EXITED) {
                    //System.out.println("MOUSE_EXITED");
                }
                if (e.getEventType() == MouseEvent.MOUSE_MOVED || e.getEventType() == GazeEvent.GAZE_MOVED) {
                    //  System.out.println("MOUSE_MOVED");
                    long timeNow = (new Date()).getTime();
                    double x = (timeNow - timeEntry);
                    x = x / timMin;
                    System.out.println(" time " + x);
                    if (x >= 0.5) {
                        if (bE != null) {
                            if (bE.getId().equals("play")) {

                                new testImg(root,scene);
                                /*final Image img1 = new Image("file:data/imgPerso/images/heureux.jpg");
                                final ImageView imageView1 = new ImageView(img1);
                                bE.setGraphic(imageView1);*/
                            }
                            if (bE.getId().equals("ajouterImg")) {
                                // final Image img1= new Image("file:data/imgPerso/images/.jpg");
                                //final ImageView imageView1=new ImageView(img1);
                                bE.setGraphic(null);
                            }


                    }
                }


            }
        }};


        buttonPlay.addEventFilter(MouseEvent.ANY, enterEvent);
        buttonAddIm.addEventFilter(MouseEvent.ANY, enterEvent);
        //userName.addEventFilter(MouseEvent.ANY, enterEvent);
        grid.add(buttonPlay, 0, 5);

        //grid.setGridLinesVisible(true);
        root.getChildren().add(grid);
    }
}


