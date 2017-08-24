package utils.games;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.tc33.jheatchart.HeatChart;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class StatsDisplay {

    static void dislayStats(Stats stats, Scene scene, Group root, ChoiceBox<String> cbxGames){

        stats.stop();

        Utils.clear(scene, root, cbxGames);

        Text statistics = new Text("Statistiques");

        statistics.setX(scene.getWidth()*0.4);
        statistics.setY(60);
        statistics.setFont(new Font(60));
        statistics.setFill(new Color(1,1,1,1));

        Text totalTime = new Text("Temps total de jeu : " + convert(stats.getTotalTime()));

        totalTime.setX(100);
        totalTime.setY(150);
        totalTime.setFont(new Font(20));
        totalTime.setFill(new Color(1,1,1,1));

        Text shoots = new Text("Tirs : " + stats.getNbshoots());

        shoots.setX(100);
        shoots.setY(200);
        shoots.setFont(new Font(20));
        shoots.setFill(new Color(1,1,1,1));

        Text length = new Text("Temps effectif de jeu : " + convert(stats.getLength()));

        length.setX(100);
        length.setY(250);
        length.setFont(new Font(20));
        length.setFill(new Color(1,1,1,1));

        Text averageLength = new Text("Temps de réaction moyen : " + convert(stats.getAverageLength()));

        averageLength.setX(100);
        averageLength.setY(300);
        averageLength.setFont(new Font(20));
        averageLength.setFill(new Color(1,1,1,1));

        Text standDev = new Text("Écart-type : " + convert((long)stats.getSD()));

        standDev.setX(100);
        standDev.setY(350);
        standDev.setFont(new Font(20));
        standDev.setFill(new Color(1,1,1,1));

        Text UncountedShoot = new Text("Tirs non comptés : " + stats.getNbUnCountedShoots());

        UncountedShoot.setX(scene.getWidth()/2);
        UncountedShoot.setY(150);
        UncountedShoot.setFont(new Font(20));
        UncountedShoot.setFill(new Color(1,1,1,1));

        LineChart<String,Number> chart = buildLineChart(stats, scene);

        chart.setLegendVisible(false);

        Rectangle heatChart = BuildHeatChart(stats, scene);

        heatChart.setX(scene.getWidth()*5/9);
        heatChart.setY(scene.getHeight()/2+15);
        heatChart.setWidth(scene.getWidth()*0.35);
        heatChart.setHeight(scene.getHeight()*0.35);

        root.getChildren().addAll(statistics, shoots, totalTime, length, averageLength, standDev, UncountedShoot, chart, heatChart);

        Utils.home(scene, root, cbxGames, null);
    }

    static LineChart<String,Number> buildLineChart(Stats stats, Scene scene) {

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        LineChart<String,Number> lineChart =
                new LineChart<String,Number>(xAxis,yAxis);

        // lineChart.setTitle("Réaction");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        //series.setName("Temps de réaction");

        XYChart.Series average = new XYChart.Series();
        // average.setName("Moyenne");

        XYChart.Series sdp = new XYChart.Series();
        //sdp.setName("Moyenne");

        XYChart.Series sdm = new XYChart.Series();
        //sdm.setName("Moyenne");

        //  xAxis.setLabel("Lancer");
        //  yAxis.setLabel("ms");

        //populating the series with data

        ArrayList<Integer> shoots = stats.getShoots();

        double sd = stats.getSD();

        int i = 0;

        average.getData().add(new XYChart.Data(0+"", stats.getAverageLength()));
        sdp.getData().add(new XYChart.Data(0+"", stats.getAverageLength()+sd));
        sdm.getData().add(new XYChart.Data(0+"", stats.getAverageLength()-sd));

        for(Integer I: shoots){

            i++;
            series.getData().add(new XYChart.Data(i+"", I.intValue()));
            average.getData().add(new XYChart.Data(i+"", stats.getAverageLength()));

            sdp.getData().add(new XYChart.Data(i+"", stats.getAverageLength()+sd));
            sdm.getData().add(new XYChart.Data(i+"", stats.getAverageLength()-sd));
        }

        i++;
        average.getData().add(new XYChart.Data(i+"", stats.getAverageLength()));
        sdp.getData().add(new XYChart.Data(i+"", stats.getAverageLength()+sd));
        sdm.getData().add(new XYChart.Data(i+"", stats.getAverageLength()-sd));

        lineChart.setCreateSymbols(false);

        lineChart.getData().add(average);
        lineChart.getData().add(sdp);
        lineChart.getData().add(sdm);
        lineChart.getData().add(series);

        series.getNode().setStyle("-fx-stroke-width: 3; -fx-stroke: red; -fx-stroke-dash-offset:5;");
        average.getNode().setStyle("-fx-stroke-width: 1; -fx-stroke: lightgreen;");
        sdp.getNode().setStyle("-fx-stroke-width: 1; -fx-stroke: grey;");
        sdm.getNode().setStyle("-fx-stroke-width: 1; -fx-stroke: grey;");

        EventHandler<Event> openLineChartEvent = openLineChart(lineChart, scene);

        lineChart.addEventHandler(MouseEvent.MOUSE_CLICKED, openLineChartEvent);

        lineChart.setTranslateX(scene.getWidth()*1/10);
        lineChart.setTranslateY(scene.getHeight()/2);
        lineChart.setMaxWidth(scene.getWidth()*0.4);
        lineChart.setMaxHeight(scene.getHeight()*0.4);

        return lineChart;
    }

    private static Rectangle BuildHeatChart(Stats stats, Scene scene){

        double[][] data = stats.getHeatMap();

        // Step 1: Create our heat map chart using our data.
        HeatChart map = new HeatChart(data);

        map.setHighValueColour(java.awt.Color.RED);
        map.setLowValueColour(java.awt.Color.lightGray);

        map.setShowXAxisValues(false);
        map.setShowYAxisValues(false);
        map.setChartMargin(0);

        // Step 2: Customise the chart.
       // map.setTitle("This is my heat chart title");
       // map.setXAxisLabel("X Axis");
       // map.setYAxisLabel("Y Axis");

        try {
            map.saveToFile(new File("/Users/schwab/Documents/TET-Communicator/src/java-heat-chart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Rectangle heatMap = new Rectangle();
        //Rectangle heatMap = new Rectangle();
        //heatMap.setVisible(true);

        heatMap.setFill(new ImagePattern(new Image("file:/Users/schwab/Documents/TET-Communicator/src/java-heat-chart.png"),0,0,1,1, true));

        EventHandler<Event> openHeatMapEvent = openHeatMap(heatMap, scene);

        heatMap.addEventHandler(MouseEvent.MOUSE_CLICKED, openHeatMapEvent);

        return heatMap;
    }

    private static EventHandler<Event> closeLineChart(LineChart<String,Number> lineChart, Scene scene){

        return new EventHandler<Event>() {

            @Override
            public void handle(Event e) {

                if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {

                    lineChart.setTranslateX(scene.getWidth()*1/10);
                    lineChart.setTranslateY(scene.getHeight()/2);
                    lineChart.setMinWidth(scene.getWidth()*0.4);
                    lineChart.setMinHeight(scene.getHeight()*0.4);

                  /*  lineChart.setTranslateX(scene.getWidth()*1/9);
                    lineChart.setTranslateY(scene.getHeight()/2+15);
                    lineChart.setMinWidth(scene.getWidth()*0.35);
                    lineChart.setMinHeight(scene.getHeight()*0.35);*/

                    lineChart.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);

                    lineChart.addEventHandler(MouseEvent.MOUSE_CLICKED, openLineChart(lineChart, scene));

                }
            }

        };
    }

    private static EventHandler<Event> openLineChart(LineChart<String,Number> lineChart, Scene scene){

        return new EventHandler<Event>() {

            @Override
            public void handle(Event e) {

                if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {

                    lineChart.setTranslateX(scene.getWidth()*0.05);
                    lineChart.setTranslateY(scene.getHeight()*0.05);
                    lineChart.setMinWidth(scene.getWidth()*0.9);
                    lineChart.setMinHeight(scene.getHeight()*0.9);

                    lineChart.toFront();

                    lineChart.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);

                    lineChart.addEventHandler(MouseEvent.MOUSE_CLICKED, closeLineChart(lineChart, scene));

                }
            }
        };
    }

    private static EventHandler<Event> closeHeatMap(Rectangle heatMap, Scene scene){

        return new EventHandler<Event>() {

        @Override
        public void handle(Event e) {

            if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {

                heatMap.setX(scene.getWidth()*5/9);
                heatMap.setY(scene.getHeight()/2+15);
                heatMap.setWidth(scene.getWidth()*0.35);
                heatMap.setHeight(scene.getHeight()*0.35);

                heatMap.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);

                heatMap.addEventHandler(MouseEvent.MOUSE_CLICKED, openHeatMap(heatMap, scene));

            }
        }

        };
    }

    private static EventHandler<Event> openHeatMap(Rectangle heatMap, Scene scene){

        return new EventHandler<Event>() {

            @Override
            public void handle(Event e) {

                if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {

                    heatMap.setX(scene.getWidth()*0.05);
                    heatMap.setY(scene.getHeight()*0.05);
                    heatMap.setWidth(scene.getWidth()*0.9);
                    heatMap.setHeight(scene.getHeight()*0.9);

                    heatMap.toFront();

                    heatMap.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);

                    heatMap.addEventHandler(MouseEvent.MOUSE_CLICKED, closeHeatMap(heatMap, scene));

                }
            }

        };

    }

    private static String convert(long totalTime) {

        long days = TimeUnit.MILLISECONDS.toDays(totalTime);
        totalTime -= TimeUnit.DAYS.toMillis(days);

        long hours = TimeUnit.MILLISECONDS.toHours(totalTime);
        totalTime -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(totalTime);
        totalTime -= TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.MILLISECONDS.toSeconds(totalTime);
        totalTime -= TimeUnit.SECONDS.toMillis(seconds);

        StringBuilder builder = new StringBuilder(1000);

        if(days>0) {
            builder.append(days);
            builder.append(" d ");
        }
        if(hours>0) {
            builder.append(hours);
            builder.append(" h ");
        }
        if(minutes>0) {
            builder.append(minutes);
            builder.append(" m ");
        }
        if(seconds>0) {
            builder.append(seconds);
            builder.append(" s ");
        }
        if(totalTime>0) {
            builder.append(totalTime);
            builder.append(" ms");
        }

        return builder.toString();
    }
}