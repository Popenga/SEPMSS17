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
public class BoxBuildWindow extends Application {
    private FXMLLoader loader;

    @FXML
    private AnchorPane anchorMng;


    public ControllerBoxMng getMngController() {
        return mngController;
    }

    public void setMngController(ControllerBoxMng mngController) {
        this.mngController = mngController;
    }

    private ControllerBoxMng mngController;

    public void init() throws Exception {
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/boxBuild.fxml"));
    }

    public void start(Stage primaryStage) throws Exception {

        anchorMng=loader.load();
        primaryStage.setMinHeight(567);
        primaryStage.setMinWidth(693);
        Scene scene=new Scene(anchorMng);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Box-Building");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }


}
