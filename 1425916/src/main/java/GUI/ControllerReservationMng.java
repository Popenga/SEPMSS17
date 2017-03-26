package GUI;

import Entities.Reservation;
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
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by krirs on 25.03.2017.
 */
public class ControllerReservationMng implements Initializable {

    @FXML
    private TableView<ReservationLink> tableView;
    @FXML
    private TableColumn<ReservationLink,Integer> tabOrderId;
    @FXML
    private TableColumn<ReservationLink,Integer> tabInvoiceId;
    @FXML
    private TableColumn<ReservationLink,Integer> tabBoxId;
    @FXML
    private TableColumn<ReservationLink,String> tabCustomer;
    @FXML
    private TableColumn<ReservationLink,String> tabFrom;
    @FXML
    private TableColumn<ReservationLink,String> tabTo;
    @FXML
    private TableColumn<ReservationLink,Double> tabPrice;
    @FXML
    private TableColumn<ReservationLink,String> tabHorse;

    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button refreshButton;

    @FXML
    private TextField filterField;

    @FXML
    private TextField filterField2;


    private ObservableList<ReservationLink> tabReservationLinks = FXCollections.observableArrayList();
    private ArrayList<Reservation> reservations;
    private ArrayList<ReservationLink> reservationLinks;
    private IntegerProperty changeInt= new SimpleIntegerProperty();
    private int deleteServiceInt;
    private int idFilter;
    private SimpleService simpleService;

    private ReservationBuildWindow reservationBuildWindow;
    public  ReservationBuildWindow getReservationBuildWindow() {
        return reservationBuildWindow;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        simpleService =new WendySimpleService();
        simpleService.setReservationDao();
        tabOrderId.setCellValueFactory(new PropertyValueFactory<ReservationLink, Integer>("colOrderId"));
        tabInvoiceId.setCellValueFactory(new PropertyValueFactory<ReservationLink, Integer>("colInvoiceId"));
        tabBoxId.setCellValueFactory(new PropertyValueFactory<ReservationLink, Integer>("colBoxId"));
        tabCustomer.setCellValueFactory(new PropertyValueFactory<ReservationLink, String>("colCustomer"));
        tabFrom.setCellValueFactory(new PropertyValueFactory<ReservationLink, String>("colReservationFrom"));
        tabTo.setCellValueFactory(new PropertyValueFactory<ReservationLink, String>("colReservationTo"));
        tabPrice.setCellValueFactory(new PropertyValueFactory<ReservationLink, Double>("colPrice"));
        tabHorse.setCellValueFactory(new PropertyValueFactory<ReservationLink, String>("colHorse"));
        tableView.setEditable(true);
        tableView.setVisible(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        reservationBuildWindow= new ReservationBuildWindow();
        addButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {

                try {
                    ControllerReservationMng.this.getReservationBuildWindow().init();
                    ControllerReservationMng.this.getReservationBuildWindow().start(new Stage());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                simpleService.deleteReservation(deleteServiceInt);
                tabReservationLinks.remove(changeInt.get());//
                tableView.getSelectionModel().clearSelection();
                manageView();
            }
        });

        refreshButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                ControllerReservationMng.this.manageView();
            }
        });

        changeInt.addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            }
        });

        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ReservationLink>() {
            public void changed(ObservableValue<? extends ReservationLink> observable, ReservationLink oldValue, ReservationLink newValue) {
                ReservationLink reservationLink = (ReservationLink) newValue;
                if(newValue!=null){
                    deleteServiceInt= reservationLink.getColOrderId();
                    changeInt.set(tabReservationLinks.indexOf(newValue));
                }
            }
        });
        manageView();
    }

    public void manageView(){
        ObservableList<ReservationLink> update = FXCollections.observableArrayList();
        reservations=simpleService.findAllReservations();
        reservationLinks = new ArrayList<ReservationLink>();

        for(Reservation reservation: reservations){
            if(!reservation.isDeleted()) {
                reservationLinks.add(new ReservationLink(reservation));
            }
        }

        for(ReservationLink reservationLink : reservationLinks) {
            update.add(reservationLink);
        }

        tabReservationLinks=update;
        tableView.setItems(tabReservationLinks);

        tabInvoiceId.setCellFactory(TextFieldTableCell.<ReservationLink, Integer>forTableColumn(new IntegerStringConverter()));
        tabInvoiceId.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ReservationLink, Integer>>() {
                    public void handle(TableColumn.CellEditEvent<ReservationLink, Integer> event) {
                        ReservationLink updateMe = new ReservationLink(event.getTableView().getItems().get(event.getTablePosition().getRow()));

                        if(event.getNewValue()!=null){
                            updateMe.setColInvoiceId(event.getNewValue());
                            if(updateMe!=null){
                                simpleService.updateReservation(updateMe.getColOrderId(),updateMe.getColBoxId(),updateMe.getColInvoiceId(), updateMe.getColCustomer(), updateMe.getColReservationFrom(), updateMe.getColReservationTo(), updateMe.getColDuration(), updateMe.getColPrice(), updateMe.getColHorse(), updateMe.isColIsDeleted());

                            }
                        }
                    }
                }
        );

        tabBoxId.setCellFactory(TextFieldTableCell.<ReservationLink, Integer>forTableColumn(new IntegerStringConverter()));
        tabBoxId.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ReservationLink, Integer>>() {
                    public void handle(TableColumn.CellEditEvent<ReservationLink, Integer> event) {
                        ReservationLink updateMe = new ReservationLink(event.getTableView().getItems().get(event.getTablePosition().getRow()));

                        if(event.getNewValue()!=null){
                            updateMe.setColBoxId(event.getNewValue());
                            if(updateMe!=null){
                                simpleService.updateReservation(updateMe.getColOrderId(),updateMe.getColBoxId(),updateMe.getColInvoiceId(), updateMe.getColCustomer(), updateMe.getColReservationFrom(), updateMe.getColReservationTo(), updateMe.getColDuration(), updateMe.getColPrice(), updateMe.getColHorse(), updateMe.isColIsDeleted());
                            }
                        }
                    }
                }
        );

        tabCustomer.setCellFactory(TextFieldTableCell.<ReservationLink>forTableColumn());
        tabCustomer.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ReservationLink, String>>() {
                    public void handle(TableColumn.CellEditEvent<ReservationLink, String> event) {
                        ReservationLink updateMe = new ReservationLink(event.getTableView().getItems().get(event.getTablePosition().getRow()));

                        if(event.getNewValue()!=null){
                            updateMe.setColCustomer(event.getNewValue());
                            if(updateMe!=null){
                                simpleService.updateReservation(updateMe.getColOrderId(),updateMe.getColBoxId(),updateMe.getColInvoiceId(), updateMe.getColCustomer(), updateMe.getColReservationFrom(), updateMe.getColReservationTo(), updateMe.getColDuration(), updateMe.getColPrice(), updateMe.getColHorse(), updateMe.isColIsDeleted());
                            }
                        }
                    }
                }
        );

        tabFrom.setCellFactory(TextFieldTableCell.<ReservationLink>forTableColumn());
        tabFrom.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ReservationLink, String>>() {
                    public void handle(TableColumn.CellEditEvent<ReservationLink, String> event) {
                        ReservationLink updateMe = new ReservationLink(event.getTableView().getItems().get(event.getTablePosition().getRow()));

                        if(event.getNewValue()!=null){
                            updateMe.setColReservationFrom(event.getNewValue());
                            if(updateMe!=null){
                                simpleService.updateReservation(updateMe.getColOrderId(),updateMe.getColBoxId(),updateMe.getColInvoiceId(), updateMe.getColCustomer(), updateMe.getColReservationFrom(), updateMe.getColReservationTo(), updateMe.getColDuration(), updateMe.getColPrice(), updateMe.getColHorse(), updateMe.isColIsDeleted());
                            }
                        }
                    }
                }
        );

        tabTo.setCellFactory(TextFieldTableCell.<ReservationLink>forTableColumn());
        tabTo.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ReservationLink, String>>() {
                    public void handle(TableColumn.CellEditEvent<ReservationLink, String> event) {
                        ReservationLink updateMe = new ReservationLink(event.getTableView().getItems().get(event.getTablePosition().getRow()));

                        if(event.getNewValue()!=null){
                            updateMe.setColReservationTo(event.getNewValue());
                            if(updateMe!=null){
                                simpleService.updateReservation(updateMe.getColOrderId(),updateMe.getColBoxId(),updateMe.getColInvoiceId(), updateMe.getColCustomer(), updateMe.getColReservationFrom(), updateMe.getColReservationTo(), updateMe.getColDuration(), updateMe.getColPrice(), updateMe.getColHorse(), updateMe.isColIsDeleted());
                            }
                        }
                    }
                }
        );

        tabPrice.setCellFactory(TextFieldTableCell.<ReservationLink,Double>forTableColumn(new DoubleStringConverter()));
        tabPrice.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ReservationLink, Double>>() {
                    public void handle(TableColumn.CellEditEvent<ReservationLink, Double> event) {
                        ReservationLink updateMe = new ReservationLink(event.getTableView().getItems().get(event.getTablePosition().getRow()));

                        if(event.getNewValue()!=null){
                            updateMe.setColPrice(event.getNewValue());
                            if(updateMe!=null){
                                String check = updateMe.getColPrice()+"";
                                if(check.contains(",")){
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Input-Error");
                                    alert.setHeaderText("Cannot process Input: "+ check);
                                    alert.setContentText("Please use [.] instead of [,]");
                                    alert.showAndWait();
                                }
                                simpleService.updateReservation(updateMe.getColOrderId(),updateMe.getColBoxId(),updateMe.getColInvoiceId(), updateMe.getColCustomer(), updateMe.getColReservationFrom(), updateMe.getColReservationTo(), updateMe.getColDuration(), updateMe.getColPrice(), updateMe.getColHorse(), updateMe.isColIsDeleted());
                            }
                        }
                    }

                }
        );

        FilteredList<ReservationLink> filteredList = new FilteredList<ReservationLink>(tabReservationLinks, p-> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate((reservationLink -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                try {
                    idFilter = Integer.parseInt(newValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (reservationLink.getColReservationFrom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (reservationLink.getColOrderId() == idFilter) {
                    return true;
                }
                return false;
            }));
        });

        filterField2.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate((reservationLink -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                try {
                    idFilter = Integer.parseInt(newValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (reservationLink.getColReservationFrom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (reservationLink.getColOrderId() == idFilter) {
                    return true;
                }
                return false;
            }));
        });

        SortedList sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
    }




    public static class ReservationLink {
        private final SimpleIntegerProperty colOrderId;
        private final SimpleIntegerProperty colBoxId;
        private final SimpleIntegerProperty colInvoiceId;
        private final SimpleStringProperty colCustomer;
        private final SimpleStringProperty colReservationFrom;
        private final SimpleStringProperty colReservationTo;
        private final SimpleIntegerProperty colDuration;
        private final SimpleDoubleProperty colPrice;
        private final SimpleStringProperty colHorse;
        private final SimpleBooleanProperty colIsDeleted;

        public ReservationLink(ReservationLink copyMe) {
            colOrderId = copyMe.colOrderIdProperty();
            colBoxId = copyMe.colBoxIdProperty();
            colInvoiceId = copyMe.colInvoiceIdProperty();
            colCustomer = copyMe.colCustomerProperty();
            colReservationFrom = copyMe.colReservationFromProperty();
            colReservationTo = copyMe.colReservationToProperty();
            colDuration = copyMe.colDurationProperty();
            colPrice = copyMe.colPriceProperty();
            colHorse= copyMe.colHorseProperty();
            colIsDeleted= copyMe.colIsDeletedProperty();
        }

        public ReservationLink(Reservation copyMe) {
            colOrderId = new SimpleIntegerProperty(copyMe.getOrderId());
            colBoxId = new SimpleIntegerProperty(copyMe.getBoxId());
            colInvoiceId = new SimpleIntegerProperty(copyMe.getInvoiceId());
            colCustomer = new SimpleStringProperty(copyMe.getCustomer());
            colReservationFrom = new SimpleStringProperty(copyMe.getReservationFrom());
            colReservationTo = new SimpleStringProperty(copyMe.getReservationTo());
            colDuration = new SimpleIntegerProperty(copyMe.getDuration());
            colPrice = new SimpleDoubleProperty(copyMe.getPrice());
            colHorse = new SimpleStringProperty(copyMe.getHorse());
            colIsDeleted = new SimpleBooleanProperty(copyMe.isDeleted());

        }

        public int getColOrderId() {
            return colOrderId.get();
        }

        public SimpleIntegerProperty colOrderIdProperty() {
            return colOrderId;
        }

        public void setColOrderId(int colOrderId) {
            this.colOrderId.set(colOrderId);
        }

        public int getColBoxId() {
            return colBoxId.get();
        }

        public SimpleIntegerProperty colBoxIdProperty() {
            return colBoxId;
        }

        public void setColBoxId(int colBoxId) {
            this.colBoxId.set(colBoxId);
        }

        public int getColInvoiceId() {
            return colInvoiceId.get();
        }

        public SimpleIntegerProperty colInvoiceIdProperty() {
            return colInvoiceId;
        }

        public void setColInvoiceId(int colInvoiceId) {
            this.colInvoiceId.set(colInvoiceId);
        }

        public String getColCustomer() {
            return colCustomer.get();
        }

        public SimpleStringProperty colCustomerProperty() {
            return colCustomer;
        }

        public void setColCustomer(String colCustomer) {
            this.colCustomer.set(colCustomer);
        }

        public String getColReservationFrom() {
            return colReservationFrom.get();
        }

        public SimpleStringProperty colReservationFromProperty() {
            return colReservationFrom;
        }

        public void setColReservationFrom(String colReservationFrom) {
            this.colReservationFrom.set(colReservationFrom);
        }

        public String getColReservationTo() {
            return colReservationTo.get();
        }

        public SimpleStringProperty colReservationToProperty() {
            return colReservationTo;
        }

        public void setColReservationTo(String colReservationTo) {
            this.colReservationTo.set(colReservationTo);
        }

        public int getColDuration() {
            return colDuration.get();
        }

        public SimpleIntegerProperty colDurationProperty() {
            return colDuration;
        }

        public void setColDuration(int colDuration) {
            this.colDuration.set(colDuration);
        }

        public double getColPrice() {
            return colPrice.get();
        }

        public SimpleDoubleProperty colPriceProperty() {
            return colPrice;
        }

        public void setColPrice(double colPrice) {
            this.colPrice.set(colPrice);
        }

        public String getColHorse() {
            return colHorse.get();
        }

        public SimpleStringProperty colHorseProperty() {
            return colHorse;
        }

        public void setColHorse(String colHorse) {
            this.colHorse.set(colHorse);
        }

        public boolean isColIsDeleted() {
            return colIsDeleted.get();
        }

        public SimpleBooleanProperty colIsDeletedProperty() {
            return colIsDeleted;
        }

        public void setColIsDeleted(boolean colIsDeleted) {
            this.colIsDeleted.set(colIsDeleted);
        }
    }
}
