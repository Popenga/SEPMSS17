package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by krirs on 24.03.2017.
 */
public class ControllerMain implements Initializable {

@FXML
private Button boxMngButton;

@FXML
private Button reservationButton;



    public void initialize(URL location, ResourceBundle resources) {

        boxMngButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                BoxMngWindow boxMngWindow= new BoxMngWindow();
                try {
                    boxMngWindow.init();
                    boxMngWindow.start(new Stage());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        reservationButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                ReservationWindow reservationWindow= new ReservationWindow();
                try {
                    reservationWindow.init();
                    reservationWindow.start(new Stage());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }
}
