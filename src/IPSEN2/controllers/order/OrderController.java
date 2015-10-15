package IPSEN2.controllers.order;

import IPSEN2.ContentLoader;
import IPSEN2.models.guest.Guest;
import IPSEN2.models.order.Order;
import IPSEN2.models.order.OrderStatus;
import IPSEN2.services.order.OrderService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderController extends ContentLoader implements Initializable{

   private int selectedOrderID;

   @FXML
   private TableView table_view;
   @FXML private TableColumn idColumn;
   @FXML private TableColumn lastNameColumn;
   @FXML private TableColumn totalAmountColumn;
   @FXML private TableColumn statusColumn;
   @FXML private TableColumn invoiceColumn;

   public OrderController() {

   }

   public void handleAddButton() throws IOException {
      addContent(new AddOrderController(), EDIT_ORDER_DIALOG);
   }

   public void handleEditButton() throws IOException {
      if (selectedOrderID != 0) {
         addContent(new EditOrderController(), EDIT_ORDER_DIALOG);
      }
   }

   public void handleRemoveButton() {

   }

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      ContentLoader.setMainFrameTitle(ContentLoader.ORDERS_TITLE);
      table_view.setPlaceholder(new Label("Er is geen content om te weergeven"));
      OrderService service = new OrderService();

      table_view.setItems(FXCollections.observableArrayList(service.all()));

      idColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
      lastNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Order, String>, ObservableValue<String>>() {
         public ObservableValue<String> call(TableColumn.CellDataFeatures<Order, String> param) {
            if (param.getValue() != null && param.getValue().getGuest().getLastName() != null) {
               return new SimpleStringProperty(param.getValue().getGuest().getLastName());
            }
            return new SimpleStringProperty("");
         }
      });
      totalAmountColumn.setCellValueFactory(new PropertyValueFactory<Order, Double>("totalAmount"));
      statusColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Order, String>, ObservableValue<String>>() {
         public ObservableValue<String> call(TableColumn.CellDataFeatures<Order, String> param) {
            if (param.getValue() != null && param.getValue().getStatus().getName() != null) {
               return new SimpleStringProperty(param.getValue().getStatus().getName());
            }
            return new SimpleStringProperty("");
         }
      });

   }

   private class RowSelectChangeListener implements ChangeListener {

      @Override
      public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//         try {
//            selectedGuestID = table_view.getSelectionModel().getSelectedItem().getId();
//            System.out.println(selectedGuestID);} catch (NullPointerException e){
//            System.out.print("No items in table to select");
//            selectedGuestID = 0;
//         }

      }
   }

}