package TestRajaa;

import creampie.Hand;
import creampie.Target;
import gaze.GazeEvent;
import gaze.SecondScreen;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.File;
import java.io.FilenameFilter;
import javafx.scene.layout.Pane;
import javafx.animation.*;
import javafx.util.Duration;



/**************
 *
 * */

import gaze.SecondScreen;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;

import javafx.scene.control.ProgressIndicator;
import utils.games.Utils;

/**
 * Created by initial on 09/03/2017.
 */





public class testImg  extends Application {

    public static void main(String[] args) {
        Application.launch(TestRajaa.testImg.class, args);
    }
    public testImg(Group root, Scene scene){
       // Application.launch(TestRajaa.testImg.class, args);
        launch(root,scene);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TesT -- Rajaa");

        primaryStage.setFullScreen(true);

        Group root = new Group();

        Scene scene = new Scene(root, com.sun.glass.ui.Screen.getScreens().get(0)
                .getWidth()-500, com.sun.glass.ui.Screen.getScreens().get(0).getHeight()-500,
                Color.BLACK);

        launch(root, scene);

        primaryStage.setOnCloseRequest((WindowEvent we)-> System.exit(0));

        primaryStage.setScene(scene);

        primaryStage.show();

        SecondScreen secondScreen = SecondScreen.launch();
    }




    public static void launch(Group root, Scene scene){
/*

        Image img= new Image("file:data/creampie/images/gateau.png");
        Rectangle r=new Rectangle();
        Rectangle r1=new Rectangle();
        Rectangle r2=new Rectangle();
        Rectangle r3=new Rectangle();

        r3.setWidth(300);
        r3.setHeight(200);
        r3.setFill(new ImagePattern(img, 0, 0, 1, 1, true));
        r3.setStroke(Color.ORANGE);


        r1.setWidth(300);
        r1.setHeight(200);
        r1.setFill(new ImagePattern(img, 0, 0, 1, 1, true));
        r1.setStroke(Color.ORANGE);

        r2.setWidth(300);
        r2.setHeight(200);
        r2.setFill(new ImagePattern(img, 0, 0, 1, 1, true));
        r2.setStroke(Color.ORANGE);


        r.setWidth(300);
        r.setHeight(200);
        r.setFill(new ImagePattern(img, 0, 0, 1, 1, true));
        r.setStroke(Color.ORANGE);

        EventHandler<Event> enterEvent;
        ProgressIndicator pi = new ProgressIndicator(0);
        enterEvent= new EventHandler<Event>() {
            long timeEntry=-1,timMin=2000;
            long s=0;
            Rectangle rE=null;
            @Override
            public void handle(Event e) {

                if(e.getTarget() instanceof javafx.scene.shape.Rectangle){
                        rE=(Rectangle) e.getTarget();
                }

                if(e.getEventType()==MouseEvent.MOUSE_ENTERED|| e.getEventType() == GazeEvent.GAZE_ENTERED){
                   System.out.println("MOUSE_ENTERED"+e.getTarget());
                    pi.setOpacity(0.5);
                    timeEntry=(new Date()).getTime();


                    //final Timeline timeline = new Timeline();
                   // timeline.setCycleCount(5);
                   //timeline.setAutoReverse(true);
                    /* translate rectangle jusqu'a end value  selon X ou Y
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5000),
                            new KeyValue (rE.translateYProperty(), 25)));
                    timeline.play();*/
                    //System.out.println(" **** "+timeline.getKeyFrames());
/*
                    pi.setTranslateX(r.getX()+(r.getWidth())*0.05);
                    pi.setTranslateY(r.getY()+(r.getHeight())*0.2);
                    pi.setMinWidth(r.getWidth()*0.9);
                    pi.setMinHeight(r.getHeight()*0.9);

                }if(e.getEventType() == MouseEvent.MOUSE_EXITED || e.getEventType() == GazeEvent.GAZE_EXITED){
                    System.out.println("MOUSE_EXITED");
                    pi.setOpacity(0);
                }
                if(e.getEventType()==MouseEvent.MOUSE_MOVED || e.getEventType() == GazeEvent.GAZE_MOVED ){
                    System.out.println("MOUSE_MOVED");
                    long timeNow = (new Date()).getTime();
                    double x=(timeNow - timeEntry);
                    x=x/timMin;
                    pi.setProgress(x);

                    if(pi.getProgress()>=1){

                        Image imgE = new Image("file:data/creampie/images/hand.png");
                        rE.setFill(new ImagePattern(imgE, 0, 0, 1, 1, true));
                        pi.setOpacity(0);
                    }

                }


            }
        };

        Pane myPane=new Pane();
        myPane.setTranslateX(200);
        myPane.setTranslateY(200);
        myPane.setStyle("-fx-background-color: white;");
        myPane.setPrefSize(300,200);
        myPane.addEventFilter(MouseEvent.ANY, enterEvent);
        myPane.getChildren().add(r);
        pi.setOpacity(0);
        myPane.getChildren().add(pi);
        root.getChildren().add(myPane);



        Pane myPane1=new Pane();
        myPane1.setTranslateX(700);
        myPane1.setTranslateY(200);
        myPane1.setStyle("-fx-background-color: white;");
        myPane1.setPrefSize(300,200);
        myPane1.addEventFilter(MouseEvent.ANY, enterEvent);
        myPane1.getChildren().add(r1);
        pi.setOpacity(0);
        myPane1.getChildren().add(pi);
        root.getChildren().add(myPane1);





        Pane myPane2=new Pane();
        myPane2.setTranslateX(200);
        myPane2.setTranslateY(600);
        myPane2.setStyle("-fx-background-color: white;");
        myPane2.setPrefSize(300,200);
        myPane2.addEventFilter(MouseEvent.ANY, enterEvent);
        myPane2.getChildren().add(r2);
        pi.setOpacity(0);
        myPane2.getChildren().add(pi);
        root.getChildren().add(myPane2);

        Pane myPane3=new Pane();
        myPane3.setTranslateX(700);
        myPane3.setTranslateY(600);
        myPane3.setStyle("-fx-background-color: white;");
        myPane3.setPrefSize(300,200);
        myPane3.addEventFilter(MouseEvent.ANY, enterEvent);
        myPane3.getChildren().add(r3);
        pi.setOpacity(0);
        myPane3.getChildren().add(pi);
        root.getChildren().add(myPane3);

        /*Rectangle r1=new Rectangle();
        r1.setX(r.getX()+(r.getWidth())*0.05);
        r1.setY(r.getY()+(r.getHeight())*0.2);
        r1.setWidth(r.getWidth()*0.9);
        r1.setHeight(r.getHeight()*0.9);
        r1.setStroke(Color.ORANGE);
        myPane.getChildren().add(r1);*/

        double widthEcran= com.sun.glass.ui.Screen.getScreens().get(0).getWidth();
        double heightEcran= com.sun.glass.ui.Screen.getScreens().get(0).getHeight();
        double partW=widthEcran/5;
        double partH=heightEcran/6;
        root.getChildren().remove(0);

        Image imgG = new Image("file:data/imgPerso/images/gazeplay.jpg");
        ImageView imageViewG = new ImageView(imgG);
        double h=heightEcran/8;
        double w=widthEcran/7;
        imageViewG.setFitHeight(h);
        imageViewG.setFitWidth(5 * w);
        imageViewG.setTranslateY(h-20);
        imageViewG.setTranslateX(partW-70);
        root.getChildren().add(imageViewG);
        Hashtable<Integer,Image> listImgUti= new Hashtable<Integer,Image>();
        Hashtable<String,Image> listImgSys= new Hashtable<String,Image>();





        String r=System.getProperties().getProperty("user.home");
        String p=r+File.separator+"GazePlay"+File.separator+"files"+File.separator+"images"+File.separator;
        //+"animaux"
        System.out.println("************************PATH :"+p);
        ArrayList<String>  foldersName=Utils.getFolder(p);
        int index= (int)( Math.random()* foldersName.size());
        System.out.println("index "+index+" -- "+foldersName.get(index));
        Image[] images= Utils.getImages(foldersName.get(index));

        System.out.println(images.length);

        Image img= new Image("file:data/imgPerso/images/question.jpeg");
        listImgSys.put("question",img);
        img = new Image("file:data/creampie/images/hand.png");
        listImgSys.put("hand",img);
        img= new Image("file:data/imgPerso/images/heureux.jpg");

        for(int i=0;i<images.length;i++)
        {
            System.out.println(i+" ** "+listImgUti.size()+"***"+listImgSys.size());
            listImgUti.put(i,images[i]);
        }

        // revoir
        int nbrRechecher=new Random().nextInt(4);
        System.out.println(" nbr recherche "+nbrRechecher);
        new PaneImg(root,scene,partW,1.5*partH,listImgUti,listImgSys,0,nbrRechecher);
        new PaneImg(root,scene,3*partW,1.5*partH,listImgUti,listImgSys,1,nbrRechecher);
        new PaneImg(root,scene,partW,4*partH,listImgUti,listImgSys,2,nbrRechecher);
        new PaneImg(root,scene,3*partW,4*partH,listImgUti,listImgSys,3,nbrRechecher);

    }

}

