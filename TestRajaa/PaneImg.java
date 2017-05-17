package TestRajaa;

import gaze.GazeEvent;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.*;
import javafx.scene.Scene;
import javafx.scene.shape.*;
import javafx.animation.FadeTransition.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.*;

/**
 * Created by initial on 16/03/2017.
 */
public class PaneImg{
    Hashtable<Integer,Image> listImgUti= new Hashtable<Integer,Image>();
    Hashtable<String,Image> listImgSys= new Hashtable<String,Image>();
    public static ArrayList<Integer> indexS=new ArrayList<Integer>();
    public static ArrayList<Integer> indexU=new ArrayList<Integer>();
    public static int[] indexPane=new int[4];
   // ArrayList <Image>listImgUti=new ArrayList<Image>();/* on doit avoir au moins 4 images */
    //ArrayList <Image>listImgSys=new ArrayList<Image>();
    //public static int indexS=0;
    //public static int indexU=0;
    public PaneImg(Group root, Scene scene, double X, double Y, Hashtable<Integer,Image> listImgUti,
                   Hashtable<String,Image> listImgSys, int nIm, int nbrRechecher){

        this.listImgUti=listImgUti;
        this.listImgSys=listImgSys;
        //System.out.println("Bonjour");
        double widthEcran= com.sun.glass.ui.Screen.getScreens().get(0).getWidth();
        double heightEcran= com.sun.glass.ui.Screen.getScreens().get(0).getHeight();
        double partW=widthEcran/5;
        double partH=heightEcran/5;
        int index=0;
        if(listImgUti.isEmpty()){
           // System.out.println(" ******* listImgUti.isEmpty ");
            index= new Random().nextInt(listImgSys.size());
                    //*( listImgSys.size()-1)+1);
           // listImgSys.remove(index);
           while(indexS.contains(index) && indexS.size() < 4){
                index= new Random().nextInt(listImgSys.size());
                System.out.println("while : "+index);
            }
            indexS.add(index);
            //System.out.println("******* index Sys :"+indexS);

        }
        else{
            System.out.println(" ******* NOT listImgUti.isEmpty indexU "+listImgUti.size());
           // index= (int)( Math.random()*( listImgUti.size()-1)+1)-1;
            index= new Random().nextInt(listImgUti.size());

            while(indexU.contains(index) && indexU.size() < 4 ){
                index= new Random().nextInt(listImgUti.size());
                System.out.println("while : "+index);
            }

            indexU.add(index);
            System.out.println("*******size index Uti :"+indexU.toString());

        }
       // System.out.println("Bonjour");
        Rectangle r=new Rectangle();
        r.setWidth(partW+50);
        r.setHeight(partH+50);
        r.setFill(new ImagePattern(listImgSys.get("question"), 0, 0, 1, 1, true));
        r.setStroke(Color.ORANGE);
        ProgressIndicator pi = new ProgressIndicator(0);
        EventHandler<Event> enterEvent;
        Pane myPane=new Pane();
        EventHandler<Event> deleteEvent;
        deleteEvent=new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                // on click actions here

                myPane.removeEventFilter(MouseEvent.ANY, this); // at the bottom
            }
        };

        enterEvent= new EventHandler<Event>() {

            long timeEntry=-1,timMin=2000;
            long s=0;
            Rectangle rE=null;
            final Timeline timeline = new Timeline();
            boolean nnF=true;


            @Override
            public void handle(Event e) {


                if(nnF){
                if(e.getTarget() instanceof javafx.scene.shape.Rectangle){
                    rE=(Rectangle) e.getTarget();

                }

                if(e.getEventType()==MouseEvent.MOUSE_ENTERED|| e.getEventType() == GazeEvent.GAZE_ENTERED){
                   // System.out.println("MOUSE_ENTERED"+e.getTarget());
                    System.out.println(" PAAAAAAAAAAAAAANE ::: "+myPane.getId());
                    // indexPane[nIm]=Integer.parseInt(myPane.getId());
                    if(rE!=null)
                    rE.setOpacity(1);
                    pi.setOpacity(0.5);
                    timeline.play();
                    timeEntry=(new Date()).getTime();
                     //final Timeline timeline = new Timeline();
                    // timeline.setCycleCount(5);
                    //timeline.setAutoReverse(true);
                    /* translate rectangle jusqu'a end value  selon X ou Y
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5000),
                            new KeyValue (rE.translateYProperty(), 25)));
                    timeline.play();*/
                    //System.out.println(" **** "+timeline.getKeyFrames());

                    pi.setTranslateX(r.getX()+(r.getWidth())*0.05);
                    pi.setTranslateY(r.getY()+(r.getHeight())*0.2);
                    pi.setMinWidth(r.getWidth()*0.9);
                    pi.setMinHeight(r.getHeight()*0.9);

                }if(e.getEventType() == MouseEvent.MOUSE_EXITED || e.getEventType() == GazeEvent.GAZE_EXITED){
                    //System.out.println("MOUSE_EXITED");

                    timeline.stop();
                    if(nIm != nbrRechecher) {
                        rE.setFill(new ImagePattern(listImgSys.get("question"), 0, 0, 1, 1, true));
                    }
                    pi.setOpacity(0);
                    rE.setOpacity(1);

                }
                if(e.getEventType()==MouseEvent.MOUSE_MOVED || e.getEventType() == GazeEvent.GAZE_MOVED ){
                    //System.out.println("MOUSE_MOVED");

                    long timeNow = (new Date()).getTime();
                    double x=(timeNow - timeEntry);
                    x=x/timMin;
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1500),
                            new KeyValue (rE.opacityProperty(),x)));
                    pi.setProgress(x);

                    if(pi.getProgress()>=1){
                        timeline.stop();
                        pi.setOpacity(0);



                        if(listImgUti.isEmpty()){
                            rE.setFill(new ImagePattern(listImgSys.get(indexS.get(nIm)), 0, 0, 1, 1, true));
                            rE.setOpacity(1);
                        }
                        else{
                            rE.setFill(new ImagePattern(listImgUti.get(indexU.get(nIm)), 0, 0, 1, 1, true));
                            rE.setOpacity(1);
                            if(nIm == nbrRechecher && nnF ){

                                nnF=false;
                                myPane.removeEventHandler(MouseEvent.ANY,this);
                                myPane.getChildren().remove(pi);
                                rE.setFill(new ImagePattern(listImgUti.get(indexU.get(nIm)), 0, 0, 1, 1, true));
                                rE.setOpacity(1);
                                new PaneFelicitation(root,scene,myPane,X,Y);
                            }

                        }


                    }

                }


            }
            }
        };


        myPane.setTranslateX(X);
        myPane.setTranslateY(Y);
        System.out.println(myPane +" Y "+Y+ " X "+X);
        myPane.setStyle("-fx-background-color: white;");
        myPane.setPrefSize(partW+50,partH+50);
        myPane.addEventFilter(MouseEvent.ANY, enterEvent);
        myPane.getChildren().add(r);
        pi.setOpacity(0);
        myPane.getChildren().add(pi);
        root.getChildren().add(myPane);

    }



}