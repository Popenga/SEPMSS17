package GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Created by krirs on 25.03.2017.
 */
public class ReservationWindow extends Application{

    private FXMLLoader loader;

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void init() throws Exception {
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/reservationMng.fxml"));
    }

    public void start(Stage primaryStage) throws Exception {

        anchorPane=loader.load();
        primaryStage.setMinHeight(567);
        primaryStage.setMinWidth(693);
        Scene scene=new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Reservation-Management");
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }


}
