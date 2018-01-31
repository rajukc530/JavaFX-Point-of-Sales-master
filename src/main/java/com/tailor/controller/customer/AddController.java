package com.tailor.controller.customer;

import com.tailor.entity.Customer;
import com.tailor.entity.Employee;
import com.tailor.interfaces.CustomerInterface;
import com.tailor.interfaces.EmployeeInterface;
import com.tailor.model.CustomerModel;
import com.tailor.model.EmployeeModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

public class AddController implements Initializable, CustomerInterface {

    @FXML
    private TextField firstField, lastField, emailField, phoneField;
    @FXML
    private TextArea addressArea;
    @FXML
    private Button saveButton;
    private CustomerModel customerModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerModel = new CustomerModel();
        }

    @FXML
    public void handleCancel(ActionEvent event) {
        firstField.setText("");
        lastField.setText("");
        phoneField.setText("");
        addressArea.setText("");
        emailField.setText("");
        }

    @FXML
    public void handleSave(ActionEvent event) {

        if (validateInput()) {

            Customer customer = new Customer(
                    firstField.getText(),
                    lastField.getText(),
                    phoneField.getText(),
                    addressArea.getText(),
                    emailField.getText()
            );
            customerModel.saveCustomer(customer);
            CUSTOMERLIST.clear();
            CUSTOMERLIST.addAll(customerModel.getCustomers());

            ((Stage) saveButton.getScene().getWindow()).close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful");
            alert.setHeaderText("Customer Created!");
            alert.setContentText("Customer is created successfully");
            alert.showAndWait();
        }
    }

    private boolean validateInput() {

        String errorMessage = "";

        if (firstField.getText() == null || firstField.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }

        if (lastField.getText() == null || lastField.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }

        
        if (phoneField.getText() == null || phoneField.getText().length() == 0) {
            errorMessage += "No valid phone number!\n";
        }

        if (addressArea.getText() == null || addressArea.getText().length() == 0) {
            errorMessage += "No email address!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }

    @FXML
    public void closeAction(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
