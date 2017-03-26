package GUI;

import Service.SimpleService;
import Service.WendySimpleService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by krirs on 26.03.2017.
 */
public class ControllerReservationBuild implements Initializable {

    @FXML
    TextField textOrderId;
    @FXML
    TextField textInvoiceId;
    @FXML
    TextField textBoxId;
    @FXML
    TextField textCustomer;
    @FXML
    TextField textFrom;
    @FXML
    TextField textTo;
    @FXML
    TextField textHorse;

    @FXML
    Button addButton;

    private SimpleService simpleService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                boolean abort=false;
                int orderId=-1;
                int invoiceId=-1;
                int boxId=-1;
                String customer="";
                String to="";
                String from="";
                String horse="";
                Double price=0.00;
                String msg="Error(s):"+ "\n";
                boolean order=false;
                boolean invoice=false;
                boolean box=false;
                boolean toBol=false;
                boolean fromBol=false;
                boolean valid= false;
                Date parsedDateTo=new Date();
                Date parsedDateFrom=new Date();

                try {
                    orderId=Integer.parseInt(textOrderId.getText());
                    order=true;
                    invoiceId=Integer.parseInt(textInvoiceId.getText());
                    invoice=true;
                    boxId = Integer.parseInt(textBoxId.getText());
                    box=true;

                }catch (Exception e){
                    abort=true;
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    if(!order) {
                        msg += "OrderId:Input was not numeric!"+"\n";
                        textOrderId.clear();
                    }
                    if(!invoice){
                        msg += "InvoiceId:Input was not numeric!"+"\n";
                        textInvoiceId.clear();
                    }
                    if(!box){
                        msg+= "BoxID:Input was not numeric!"+"\n";
                        textBoxId.clear();
                    }
                }

                try {
                 parsedDateTo = new SimpleDateFormat("YYYY-MM-DD").parse(textTo.getText());
                    to=textTo.getText();
                    toBol=true;
                 parsedDateFrom =  new SimpleDateFormat("YYYY-MM-DD").parse(textFrom.getText());
                    from=textFrom.getText();
                    fromBol=true;

                } catch (ParseException e) {
                    abort=true;
                    if(!fromBol) {
                        msg += "From: Wrong Input Format! Expected: YYYY-MM-DD"+"\n";
                        textFrom.clear();
                    }
                    if(!toBol){
                        msg += "To: Wrong Input Format! Expected: YYYY-MM-DD"+"\n";
                        textTo.clear();
                    }

                }
                customer=textCustomer.getText();
                horse=textHorse.getText();
                if(customer.trim().equals("")){
                    abort=true;
                    msg += "Cannot create Reservaton without Customer!"+"\n";
                }

                if(parsedDateFrom.compareTo(parsedDateTo)>0){
                    abort=true;
                    msg += "Date: DateFrom is after DateTo!"+"\n";
                }

                simpleService =new WendySimpleService();
                simpleService.setReservationDao();
                simpleService.setBoxDao();

                if(simpleService.findBox(boxId)==null){
                    abort=true;
                    msg += "BoxID: Box does not exist"+"\n";
                }

                if(simpleService.findReservation(orderId)!=null){
                    abort=true;
                    msg += "OrderId: Reservation with this ID does already exists"+"\n";
                    textOrderId.clear();
                }

                if(!abort){

                    simpleService.createReservation(orderId,boxId,invoiceId,customer,from,to,0,price,horse,false);
                    price=simpleService.getDayDiff(orderId)*simpleService.findBox(boxId).getDailyRate();
                    simpleService.updateReservation(orderId,boxId,invoiceId,customer,from,to,0,price,horse,false);
                    System.out.println(simpleService.getDayDiff(orderId));
                    System.out.println(simpleService.findBox(boxId).getDailyRate());

                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("INPUT-SUCCESS");
                    alert.setHeaderText("Reservation was created!");
                    alert.setContentText("Closing Window...");
                    alert.showAndWait();
                    Stage stage= (Stage) addButton.getScene().getWindow();
                    stage.close();
                }
                else {
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("INPUT-ERROR");
                    alert.setHeaderText("Reservation was not created!");
                    alert.setContentText(msg);
                    alert.showAndWait();
                }

            }
        });



    }



}
