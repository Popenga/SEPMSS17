package GUI;

import Service.SimpleService;
import Service.WendySimpleService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by krirs on 26.03.2017.
 */
public class ControllerInvoices implements Initializable {

    @FXML
    private Button generateInvoiceButton;

    @FXML
    private TextField textCustomer;

    @FXML
    private TextArea invoiceArea;

    private SimpleService simpleService;
   /* Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Input-Error");
                                    alert.setHeaderText("Cannot process Input: "+ check);
                                    alert.setContentText("Please use [.] instead of [,]");
                                    alert.showAndWait();
*/


    public void initialize(URL location, ResourceBundle resources) {
        simpleService=new WendySimpleService();
        simpleService.setBoxDao();
        simpleService.setReservationDao();

        generateInvoiceButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
            if(simpleService.generateInvoice(textCustomer.getText()).equals("")){
                textCustomer.clear();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input-Error");
                alert.setHeaderText("Cannot process Input:");
                alert.setContentText("No such Customer exists or Input-Field is Empty!");
                alert.showAndWait();
            }
            else{
                ControllerInvoices.this.invoiceArea.setText(simpleService.generateInvoice(textCustomer.getText()));
            }
            }
        });

    }
}

