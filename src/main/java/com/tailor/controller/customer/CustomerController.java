package com.tailor.controller.customer;

import com.tailor.entity.Customer;
import com.tailor.entity.Employee;
import com.tailor.interfaces.CustomerInterface;
import com.tailor.interfaces.EmployeeInterface;
import com.tailor.model.EmployeeModel;

import static com.tailor.interfaces.EmployeeInterface.EMPLOYEELIST;
import com.tailor.model.CustomerModel;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class CustomerController implements Initializable, CustomerInterface {

    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Long> idColumn;
    @FXML
    private TableColumn<Customer, String> firstnameColumn, lastnameColumn,
             phoneColumn, addressColumn , emailColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Button editButton, deleteButton;
    private CustomerModel model;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private Button menu;
    @FXML
    private VBox drawer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new CustomerModel();

        drawerAction();
        loadData();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        customerTable.setItems(CUSTOMERLIST);

        filterData();

        editButton
                .disableProperty()
                .bind(Bindings.isEmpty(customerTable.getSelectionModel().getSelectedItems()));
        deleteButton
                .disableProperty()
                .bind(Bindings.isEmpty(customerTable.getSelectionModel().getSelectedItems()));
    }

    private void filterData() {
        FilteredList<Customer> searchedData = new FilteredList<>(CUSTOMERLIST, e -> true);
        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                searchedData.setPredicate(employee -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (employee.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (employee.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (employee.getPhone().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (employee.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Customer> sortedData = new SortedList<>(searchedData);
            sortedData.comparatorProperty().bind(customerTable.comparatorProperty());
            customerTable.setItems(sortedData);
        });
    }

    private void loadData() {

        if (!CUSTOMERLIST.isEmpty()) {
            CUSTOMERLIST.clear();
        }
        CUSTOMERLIST.addAll(model.getCustomers());
    }

    private void drawerAction() {

        TranslateTransition openNav = new TranslateTransition(new Duration(350), drawer);
        openNav.setToX(0);
        TranslateTransition closeNav = new TranslateTransition(new Duration(350), drawer);
        menu.setOnAction((ActionEvent evt) -> {
            if (drawer.getTranslateX() != 0) {
                openNav.play();
                menu.getStyleClass().remove("hamburger-button");
                menu.getStyleClass().add("open-menu");
            } else {
                closeNav.setToX(-(drawer.getWidth()));
                closeNav.play();
                menu.getStyleClass().remove("open-menu");
                menu.getStyleClass().add("hamburger-button");
            }
        });
    }

    @FXML
    public void adminAction(ActionEvent event) throws Exception {
        windows("/fxml/Admin.fxml", "Admin", event);
    }

    @FXML
    public void productAction(ActionEvent event) throws Exception {

        windows("/fxml/Product.fxml", "Product", event);
    }

    @FXML
    public void categoryAction(ActionEvent event) throws Exception {

        windows("/fxml/Category.fxml", "Category", event);
    }

    @FXML
    public void purchaseAction(ActionEvent event) throws Exception {

        windows("/fxml/Purchase.fxml", "Purchase", event);
    }

    @FXML
    public void salesAction(ActionEvent event) throws Exception {

        windows("/fxml/Sales.fxml", "Sales", event);
    }
    
    @FXML
    public void staffAction(ActionEvent event) throws Exception {
        windows("/fxml/Employee.fxml", "Employee", event);
    }

    @FXML
    public void reportAction(ActionEvent event) throws Exception {
        windows("/fxml/Report.fxml", "Report", event);
    }

    @FXML
    public void supplierAction(ActionEvent event) throws Exception {
        windows("/fxml/Supplier.fxml", "Supplier", event);
    }

    @FXML
    public void logoutAction(ActionEvent event) throws Exception {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        Stage stage = new Stage();
        root.setOnMousePressed((MouseEvent e) -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent e) -> {
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });
        Scene scene = new Scene(root);
        stage.setTitle("Inventory:: Version 1.0");
        stage.getIcons().add(new Image("/images/logo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

    }

    private void windows(String path, String title, ActionEvent event) throws Exception {

        double width = ((Node) event.getSource()).getScene().getWidth();
        double height = ((Node) event.getSource()).getScene().getHeight();

        Parent root = FXMLLoader.load(getClass().getResource(path));
        Scene scene = new Scene(root, width, height);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.getIcons().add(new Image("/images/logo.png"));
        stage.setScene(scene);
        stage.show();
    }

    public void addAction(ActionEvent event) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/customer/Add.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        root.setOnMousePressed((MouseEvent e) -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent e) -> {
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("New Customer");
        stage.getIcons().add(new Image("/images/logo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void editAction(ActionEvent event) throws Exception {

        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        int selectedCustomerId = customerTable.getSelectionModel().getSelectedIndex();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/fxml/customer/Edit.fxml")));
        EditController controller = new EditController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        root.setOnMousePressed((MouseEvent e) -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent e) -> {
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Update Details");
        stage.getIcons().add(new Image("/images/logo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        //controller.setEmployee(selectedCustomer, selectedCustomerId);
        customerTable.getSelectionModel().clearSelection();

    }

    @FXML
    public void deleteAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove");
        alert.setHeaderText("Remove Customer");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

            model.deleteCustomer(selectedCustomer);
            CUSTOMERLIST.remove(selectedCustomer);
        }

        customerTable.getSelectionModel().clearSelection();
    }
}
