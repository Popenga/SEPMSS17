package GUI;

import Entities.Box;
import Service.SimpleService;
import Service.WendySimpleService;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.DoubleStringConverter;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by krirs on 24.03.2017.
 */
public class ControllerBoxMng implements Initializable {

    @FXML
    private TableView<BoxLink> tableView;
    @FXML
    private TableColumn<BoxLink,Integer> tabId;
    @FXML
    private TableColumn<BoxLink,String> tabPic;
    @FXML
    private TableColumn<BoxLink,String> tabName;
    @FXML
    private TableColumn<BoxLink,Double> tabDailyRate;
    @FXML
    private TableColumn<BoxLink,Boolean> tabWindow;
    @FXML
    private TableColumn<BoxLink, String> tabComment;

    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button refreshButton;


    private ObservableList<BoxLink> tabBoxLinks = FXCollections.observableArrayList();
    private ArrayList<Box> boxes;
    private ArrayList<BoxLink> boxLinks;
    private IntegerProperty changeInt= new SimpleIntegerProperty();
    private int deleteServiceInt;
    private int idFilter;
    private BoxBuildWindow boxBuildWindow;

    public BoxBuildWindow getBoxBuildWindow() {
        return boxBuildWindow;
    }

    public void setBoxBuildWindow(BoxBuildWindow boxBuildWindow) {
        this.boxBuildWindow = boxBuildWindow;
    }

    @FXML
    private TextField filterField;

    private SimpleService simpleService;



    public void initialize(URL location, ResourceBundle resources) {

        simpleService =new WendySimpleService();
        simpleService.setBoxDao();
        tabId.setCellValueFactory(new PropertyValueFactory<BoxLink, Integer>("colId"));
        tabPic.setCellValueFactory(new PropertyValueFactory<BoxLink, String>("colPic"));
        tabName.setCellValueFactory(new PropertyValueFactory<BoxLink, String>("colName"));
        tabDailyRate.setCellValueFactory(new PropertyValueFactory<BoxLink, Double>("colDailyRate"));
        tabWindow.setCellValueFactory(new PropertyValueFactory<BoxLink, Boolean>("colYN_Window"));
        tabComment.setCellValueFactory(new PropertyValueFactory<BoxLink, String>("colComment"));
        tableView.setEditable(true);
        tableView.setVisible(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        boxBuildWindow= new BoxBuildWindow();
        boxBuildWindow.setMngController(this);
        addButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {

                try {
                    ControllerBoxMng.this.getBoxBuildWindow().init();
                    ControllerBoxMng.this.getBoxBuildWindow().start(new Stage());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
              simpleService.deleteBox(deleteServiceInt);
              boxLinks.remove(changeInt.get());
              tableView.getSelectionModel().clearSelection();
              manageView();
            }
        });

        refreshButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            ControllerBoxMng.this.manageView();
            }
        });

       changeInt.addListener(new ChangeListener<Number>() {
           public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
           }
       });

       tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BoxLink>() {
           public void changed(ObservableValue<? extends BoxLink> observable, BoxLink oldValue, BoxLink newValue) {
               BoxLink boxLink = (BoxLink) newValue;
               if(newValue!=null){
                   deleteServiceInt= boxLink.getColId();
                   changeInt.set(tabBoxLinks.indexOf(newValue));
               }
           }
       });
        manageView();
    }

    public void manageView(){
        ObservableList<BoxLink> update = FXCollections.observableArrayList();
        boxes=simpleService.findAllBoxes();
        boxLinks = new ArrayList<BoxLink>();

        for(Box box: boxes){
            if(!box.getIsDeleted()) {
                boxLinks.add(new BoxLink(box));
            }
        }

        for(BoxLink boxLink : boxLinks) {
            update.add(boxLink);
        }

        tabBoxLinks=update;
        tableView.setItems(tabBoxLinks);

        tabName.setCellFactory(TextFieldTableCell.<BoxLink>forTableColumn());
        tabName.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<BoxLink, String>>() {
                    public void handle(TableColumn.CellEditEvent<BoxLink, String> event) {
                        BoxLink updateMe = new BoxLink(event.getTableView().getItems().get(event.getTablePosition().getRow()));

                            if(event.getNewValue()!=null){
                            updateMe.setColName(event.getNewValue());
                             if(updateMe!=null){
                                 simpleService.updateBox(updateMe.getColId(),updateMe.getColPic(),updateMe.getColName(),updateMe.getColDailyRate(),updateMe.isColYN_Window(), updateMe.isColYN_Deleted(), updateMe.getColComment());

                             }
                            }
                    }
                }
        );

        tabDailyRate.setCellFactory(TextFieldTableCell.<BoxLink,Double>forTableColumn(new DoubleStringConverter()));
        tabDailyRate.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<BoxLink, Double>>() {
                    public void handle(TableColumn.CellEditEvent<BoxLink, Double> event) {
                        BoxLink updateMe = new BoxLink(event.getTableView().getItems().get(event.getTablePosition().getRow()));

                        if(event.getNewValue()!=null){
                            updateMe.setColDailyRate(event.getNewValue());
                            if(updateMe!=null){
                                String check = updateMe.getColDailyRate()+"";
                                if(check.contains(",")){
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Input-Error");
                                    alert.setHeaderText("Cannot process Input: "+ check);
                                    alert.setContentText("Please use [.] instead of [,]");
                                    alert.showAndWait();
                                }
                                simpleService.updateBox(updateMe.getColId(),updateMe.getColPic(),updateMe.getColName(),updateMe.getColDailyRate(),updateMe.isColYN_Window(), updateMe.isColYN_Deleted(), updateMe.getColComment());

                            }
                        }
                    }

                }
        );


        tabWindow.setCellFactory(TextFieldTableCell.<BoxLink,Boolean>forTableColumn(new BooleanStringConverter()));
        tabWindow.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<BoxLink, Boolean>>() {
                    public void handle(TableColumn.CellEditEvent<BoxLink, Boolean> event) {
                        BoxLink updateMe = new BoxLink(event.getTableView().getItems().get(event.getTablePosition().getRow()));

                        if(event.getNewValue()!=null){
                            updateMe.setColYN_Window(event.getNewValue());
                            simpleService.updateBox(updateMe.getColId(),updateMe.getColPic(),updateMe.getColName(),updateMe.getColDailyRate(),updateMe.isColYN_Window(), updateMe.isColYN_Deleted(), updateMe.getColComment());
                        }
                    }
                }
        );


        tabComment.setCellFactory(TextFieldTableCell.<BoxLink>forTableColumn());
        tabComment.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<BoxLink, String>>() {
                    public void handle(TableColumn.CellEditEvent<BoxLink, String> event) {
                        BoxLink updateMe = new BoxLink(event.getTableView().getItems().get(event.getTablePosition().getRow()));

                        if(event.getNewValue()!=null){
                            updateMe.setColComment(event.getNewValue());
                            if(updateMe!=null){
                                simpleService.updateBox(updateMe.getColId(),updateMe.getColPic(),updateMe.getColName(),updateMe.getColDailyRate(),updateMe.isColYN_Window(), updateMe.isColYN_Deleted(), updateMe.getColComment());

                            }
                        }
                    }
                }
        );

        FilteredList<BoxLink> filteredList = new FilteredList<BoxLink>(tabBoxLinks,p-> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate((boxLink -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                try {
                    idFilter = Integer.parseInt(newValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (boxLink.getColName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (boxLink.getColId() == idFilter) {
                    return true;
                }
                return false;
            }));
        });

        SortedList sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
    }




    public static class BoxLink {
        private final SimpleIntegerProperty colId;
        private final SimpleStringProperty colPic;
        private final SimpleStringProperty colName;
        private final SimpleDoubleProperty colDailyRate;
        private final SimpleBooleanProperty colYN_Window;
        private final SimpleBooleanProperty colYN_Deleted;
        private final SimpleStringProperty colComment;

        public BoxLink(Box copyMe) {
            colId = new SimpleIntegerProperty(copyMe.getId());
            colPic= new SimpleStringProperty(copyMe.getPic());
            colName = new SimpleStringProperty(copyMe.getName());
            colDailyRate = new SimpleDoubleProperty(copyMe.getDailyRate());
            colYN_Window = new SimpleBooleanProperty(copyMe.getWindow());
            colYN_Deleted = new SimpleBooleanProperty(copyMe.getIsDeleted());
            colComment=new SimpleStringProperty(copyMe.getComment());
        }

        public BoxLink(BoxLink copyMe) {
            colId =copyMe.colIdProperty();
            colPic= copyMe.colPicProperty();
            colName = copyMe.colNameProperty();
            colDailyRate = copyMe.colDailyRateProperty();
            colYN_Window = copyMe.colYN_WindowProperty();
            colYN_Deleted = copyMe.colYN_DeletedProperty();
            colComment=copyMe.colCommentProperty();
        }

        public int getColId() {
            return colId.get();
        }
        public void setColId(int colId) {
            this.colId.set(colId);
        }

        public String getColComment() {
            return colComment.get();
        }

        public SimpleStringProperty colCommentProperty() {
            return colComment;
        }

        public void setColComment(String colComment) {
            this.colComment.set(colComment);
        }

        public SimpleIntegerProperty colIdProperty() {
            return colId;
        }

        public String getColPic() {
            return colPic.get();
        }

        public SimpleStringProperty colPicProperty() {
            return colPic;
        }

        public void setColPic(String colPic) {
            this.colPic.set(colPic);
        }







        public String getColName() {
            return colName.get();
        }

        public SimpleStringProperty colNameProperty() {
            return colName;
        }

        public void setColName(String colName) {
            this.colName.set(colName);
        }

        public double getColDailyRate() {
            return colDailyRate.get();
        }

        public SimpleDoubleProperty colDailyRateProperty() {
            return colDailyRate;
        }

        public void setColDailyRate(double colDailyRate) {
            this.colDailyRate.set(colDailyRate);
        }

        public boolean isColYN_Window() {
            return colYN_Window.get();
        }

        public SimpleBooleanProperty colYN_WindowProperty() {
            return colYN_Window;
        }

        public void setColYN_Window(boolean colYN_Window) {
            this.colYN_Window.set(colYN_Window);
        }

        public boolean isColYN_Deleted() {
            return colYN_Deleted.get();
        }

        public SimpleBooleanProperty colYN_DeletedProperty() {
            return colYN_Deleted;
        }

        public void setColYN_Deleted(boolean colYN_Deleted) {
            this.colYN_Deleted.set(colYN_Deleted);
        }

    }
}
