package IPSEN2.controllers.guest;

import IPSEN2.ContentLoader;
import IPSEN2.generators.csv.ImportCSV;
import IPSEN2.models.guest.Guest;
import IPSEN2.services.guest.GuestService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class GuestController extends ContentLoader implements Initializable{

    @FXML private  TableView<Guest> table_view;
    @FXML private TableColumn idColumn;
    @FXML private TableColumn firstNameColumn;
    @FXML private TableColumn lastNameColumn;
    @FXML private TableColumn emailColumn;
    @FXML private TableColumn checkBoxColumn;
    @FXML private TableColumn attendedColumn;

    public int selectedGuestID;
    private GuestService service;
    private static ObservableList<Guest> guestData;
    private static ArrayList<Integer> selectedRows;
    private CheckBox selectAllCheckBox;
    private static  boolean selected;
    private static boolean keepCurrentData = false;

    @FXML
    private Pane removeButton;


    public void handleAddButton() throws IOException {
        keepCurrentData = false;
       addContent(new AddGuestController(), EDIT_GUEST_DIALOG);
    }

    public void handleRemoveButton() {


        if (selectedRows.size() != 0) {
              selected = false;

            for (Integer row : selectedRows) {
                //if (guestData.get(selectedRows.indexOf(row)).getAttended()) {
                System.out.println("removing " + row);
                service.remove(row);

                //}
            }
        } else {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Information Dialog");
//            alert.setHeaderText("Opgelet!");
//            alert.setContentText("U heeft geen items geselecteerd om te verwijderen!");
//
//            alert.showAndWait();
        }


        guestData = FXCollections.observableArrayList(service.all());
        addContent(GUESTS);

    }


    public void openEditGuestMenu() throws IOException{
        if (selectedGuestID != 0 ) {

            selected = false;
            addContent(new EditGuestController(selectedGuestID), EDIT_GUEST_DIALOG);
        }


    }

    @FXML
    private void importCSVFile() {
        //TODO: delete test code, debug only.
        try {
            ImportCSV importCSV = new ImportCSV();
            importCSV.importGuests();
        } catch (Exception e) {
            e.printStackTrace();
        }
        keepCurrentData = false;
        addContent(GUESTS);
    }

    private void setOnTableRowClickedListener() {
        table_view.setRowFactory(table -> {
            TableRow<Guest> row = new TableRow<>();
            row.getStyleClass().add("pane");
            row.setOnMouseClicked(event -> {
                selectedGuestID = row.getTableView().getSelectionModel().getSelectedItem().getId();

                try {
                    openEditGuestMenu();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            return row;
        });
    }

    private void createSelectAllCheckBox() {
        selectAllCheckBox = new CheckBox();
        selectAllCheckBox.setSelected(selected);
        checkBoxColumn.setGraphic(selectAllCheckBox);
        selectAllCheckBox.setOnAction(event -> {
            selected = selectAllCheckBox.isSelected();
            if (selected) {
                selectedRows.clear();
            }
            guestData.forEach(guest -> {
                guest.setSelected(selected);
                if (selected) {
                    selectedRows.add(guest.getId());
                    System.out.println(selectedRows.size());
                } else {
                    selectedRows.clear();
                    System.out.println(selectedRows.size());
                }
            });

            addContent(GUESTS);
            selectAllCheckBox.setSelected(selected);
        });

    }

    private Callback createCheckBoxCellCallBack() {
        Callback checkBoxCellCallBack = new Callback<TableColumn.CellDataFeatures<Guest, CheckBox>, ObservableValue<CheckBox>>() {

            @Override
            public ObservableValue<CheckBox> call(TableColumn.CellDataFeatures<Guest, CheckBox> cellDataFeatures) {
                CheckBox checkBox = new CheckBox();
                checkBox.setSelected(cellDataFeatures.getValue().getSelected());
                checkBox.selectedProperty().addListener((ObservableValue<? extends Boolean> observableValue,
                                                         Boolean oldValue, Boolean newValue) -> {
                    cellDataFeatures.getValue().setSelected(newValue.booleanValue());

                    selectedGuestID = cellDataFeatures.getValue().getId();
                    if (newValue.booleanValue()) {
                        selectedRows.add(selectedGuestID);
                    } else if (!newValue.booleanValue()) {
                        selectedRows.remove(selectedRows.indexOf(selectedGuestID));
                        selectedGuestID = 0;
                    }
                });
                return new SimpleObjectProperty(checkBox);
            }
        };
        return  checkBoxCellCallBack;
    }

    private Callback createAttendedCellCallBack() {
        Callback attendedCellCallBack = new Callback<TableColumn.CellDataFeatures<Guest, CheckBox>, ObservableValue<CheckBox>>() {

            @Override
            public ObservableValue<CheckBox> call(TableColumn.CellDataFeatures<Guest, CheckBox> cellDataFeatures) {
                CheckBox checkBox = new CheckBox();
                checkBox.setSelected(cellDataFeatures.getValue().getAttended());
                checkBox.selectedProperty().addListener((ObservableValue<? extends Boolean> observableValue,
                                                         Boolean oldValue, Boolean newValue) -> {
                    cellDataFeatures.getValue().setAttended(newValue.booleanValue());

                });
                return new SimpleObjectProperty(checkBox);
            }
        };
        return  attendedCellCallBack;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ContentLoader.setMainFrameTitle(ContentLoader.GUESTS_TITLE);
        service = new GuestService();

        if (!keepCurrentData) {
            guestData = FXCollections.observableArrayList(service.all());
            selectedRows = new ArrayList<>();
            keepCurrentData = true;
        }

        table_view.setItems(guestData);
        setOnTableRowClickedListener();

<<<<<<< HEAD
        checkBoxColumn.setCellValueFactory(createCheckBoxCellCallBack());
=======
>>>>>>> 6f4d19643fc21dc50138126e59f1189e51a21892
        idColumn.setCellValueFactory(new PropertyValueFactory<Guest, Integer>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Guest, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Guest, String>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Guest, String>("email"));
        attendedColumn.setCellValueFactory(createAttendedCellCallBack());


        createSelectAllCheckBox();

        table_view.setPlaceholder(new Label("Er is geen content om te weergeven"));
        }
    }