package GUI;

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
import Service.SimpleService;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by krirs on 25.03.2017.
 */
public class ControllerBoxBuild implements Initializable {


    @FXML
    private TextField textId;
    @FXML
    private TextField textName;
    @FXML
    private TextField textDailyRate;
    @FXML
    private TextField textComment;
    @FXML
    private CheckBox checkWindow;
    @FXML
    private Button addButton;

    private SimpleService simpleService;


    public void initialize(URL location, ResourceBundle resources) {

        addButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
            boolean abort=false;
            int id;
            String name;
            Double dailyRate;
            boolean window;
            String comment;

            try {
                id=Integer.parseInt(textId.getText());
                dailyRate=Double.parseDouble(textDailyRate.getText());
            }catch (Exception e){
                abort=true;
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("INPUT-ERROR");
                alert.setHeaderText("Box was not created!");
                alert.setContentText("ID oder DailyRate falsch");
                alert.showAndWait();
                textId.clear();
                textDailyRate.clear();
                id=0;
                dailyRate=0.00;
            }
            name=textName.getText();
            comment=textComment.getText();
            window=checkWindow.isSelected();
            if(name.equals("")){
                    abort=true;
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("INPUT-ERROR");
                    alert.setHeaderText("Box was not created!");
                    alert.setContentText("Field: Name is empty");
                    alert.showAndWait();
            }

            if(!abort){
                simpleService=new WendySimpleService();
                simpleService.setBoxDao();
                simpleService.createBox(id,"",name,dailyRate,window,false, comment);
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INPUT-SUCCESS");
                alert.setHeaderText("Box was created!");
                alert.setContentText("Closing Window...");
                alert.showAndWait();
                Stage stage= (Stage) addButton.getScene().getWindow();
                stage.close();

            }

            }
        });

    }
}
