package GUI;

import Entities.Reservation;
import Service.SimpleService;
import Service.WendySimpleService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by krirs on 26.03.2017.
 */
public class ControllerStatistics implements Initializable {

    @FXML
    private Button generateStatisticsButton;

    @FXML
    private BarChart<String,Number> barChart;

    @FXML
    private TextField textFrom;

    @FXML
    private TextField textTo;

    private boolean refresh=false;

    final CategoryAxis xAxis= new CategoryAxis();
    final NumberAxis yAxis= new NumberAxis(0,10,1);


    private SimpleService simpleService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        generateStatisticsButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
            simpleService=new WendySimpleService();
            simpleService.setBoxDao();
            simpleService.setReservationDao();



            barChart.setTitle("Overview");
            xAxis.setLabel("Boxes");
            yAxis.setLabel("WeekDays");
            boolean toBol;
            boolean fromBol;
            Date parsedDateTo=new Date();
            Date parsedDateFrom=new Date();
            boolean abort;
            String msg= "Error(s):";
                try {
                    parsedDateTo = new SimpleDateFormat("YYYY-MM-DD").parse(textTo.getText());
                    toBol=true;
                    parsedDateFrom =  new SimpleDateFormat("YYYY-MM-DD").parse(textFrom.getText());
                    fromBol=true;
                    if(parsedDateFrom.compareTo(parsedDateTo)>0){
                        abort=true;
                        msg += "Date: DateFrom is after DateTo!"+"\n";
                    }
                } catch (ParseException e) {
                    abort=true;
                    textTo.clear();
                    textFrom.clear();
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("INPUT-ERROR");
                    alert.setHeaderText("Expected Format:YYY-MM-DD");
                    alert.setContentText(msg);
                    alert.showAndWait();
                    return;
                    }

             ArrayList<Reservation> reservations= simpleService.findAllIntersectReservations(textFrom.getText(),textTo.getText());

            //xAxis
                XYChart.Series monday = new XYChart.Series();
                monday.setName("Monday");
                for(Reservation reservation: reservations){
                    try {
                        parsedDateFrom= new SimpleDateFormat("YYYY-MM-DD").parse(reservation.getReservationFrom());
                        parsedDateTo =  new SimpleDateFormat("YYYY-MM-DD").parse(reservation.getReservationTo());
                    }catch (ParseException e){
                        e.printStackTrace();
                    }
                    monday.getData().add(new XYChart.Data(reservation.getBoxId()+"",simpleService.getDaysBetweenTwoDates(parsedDateFrom,parsedDateTo)[0]));
                }
                XYChart.Series tuesday = new XYChart.Series();
                tuesday.setName("Tuesday");
                for(Reservation reservation: reservations){
                    try {
                        parsedDateFrom= new SimpleDateFormat("YYYY-MM-DD").parse(reservation.getReservationFrom());
                        parsedDateTo =  new SimpleDateFormat("YYYY-MM-DD").parse(reservation.getReservationTo());
                    }catch (ParseException e){
                        e.printStackTrace();
                    }
                    tuesday.getData().add(new XYChart.Data(reservation.getBoxId()+"",simpleService.getDaysBetweenTwoDates(parsedDateFrom,parsedDateTo)[1]));
                }
                XYChart.Series wednesday = new XYChart.Series();
                wednesday.setName("Wednesday");
                for(Reservation reservation: reservations){
                    try {
                        parsedDateFrom= new SimpleDateFormat("YYYY-MM-DD").parse(reservation.getReservationFrom());
                        parsedDateTo =  new SimpleDateFormat("YYYY-MM-DD").parse(reservation.getReservationTo());
                    }catch (ParseException e){
                        e.printStackTrace();
                    }
                    wednesday.getData().add(new XYChart.Data(reservation.getBoxId()+"",simpleService.getDaysBetweenTwoDates(parsedDateFrom,parsedDateTo)[1]));
                }
                XYChart.Series thursday = new XYChart.Series();
                thursday.setName("Thursday");
                for(Reservation reservation: reservations){
                    try {
                        parsedDateFrom= new SimpleDateFormat("YYYY-MM-DD").parse(reservation.getReservationFrom());
                        parsedDateTo =  new SimpleDateFormat("YYYY-MM-DD").parse(reservation.getReservationTo());
                    }catch (ParseException e){
                        e.printStackTrace();
                    }
                    thursday.getData().add(new XYChart.Data(reservation.getBoxId()+"",simpleService.getDaysBetweenTwoDates(parsedDateFrom,parsedDateTo)[1]));
                }
                XYChart.Series friday = new XYChart.Series();
                friday.setName("Friday");
                for(Reservation reservation: reservations){
                    try {
                        parsedDateFrom= new SimpleDateFormat("YYYY-MM-DD").parse(reservation.getReservationFrom());
                        parsedDateTo =  new SimpleDateFormat("YYYY-MM-DD").parse(reservation.getReservationTo());
                    }catch (ParseException e){
                        e.printStackTrace();
                    }
                    friday.getData().add(new XYChart.Data(reservation.getBoxId()+"",simpleService.getDaysBetweenTwoDates(parsedDateFrom,parsedDateTo)[1]));
                }
                XYChart.Series saturday = new XYChart.Series();
                saturday.setName("Saturday");
                for(Reservation reservation: reservations){
                    try {
                        parsedDateFrom= new SimpleDateFormat("YYYY-MM-DD").parse(reservation.getReservationFrom());
                        parsedDateTo =  new SimpleDateFormat("YYYY-MM-DD").parse(reservation.getReservationTo());
                    }catch (ParseException e){
                        e.printStackTrace();
                    }
                    saturday.getData().add(new XYChart.Data(reservation.getBoxId()+"",simpleService.getDaysBetweenTwoDates(parsedDateFrom,parsedDateTo)[1]));
                }
                XYChart.Series sunday = new XYChart.Series();
                sunday.setName("Saturday");
                for(Reservation reservation: reservations){
                    try {
                        parsedDateFrom= new SimpleDateFormat("YYYY-MM-DD").parse(reservation.getReservationFrom());
                        parsedDateTo =  new SimpleDateFormat("YYYY-MM-DD").parse(reservation.getReservationTo());
                    }catch (ParseException e){
                        e.printStackTrace();
                    }
                    sunday.getData().add(new XYChart.Data(reservation.getBoxId()+"",simpleService.getDaysBetweenTwoDates(parsedDateFrom,parsedDateTo)[1]));
                }

                barChart.getData().addAll(monday,tuesday,wednesday,thursday,friday,saturday,sunday);
            }
        });
    }
}
