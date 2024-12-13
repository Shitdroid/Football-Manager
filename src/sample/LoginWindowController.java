package sample;
import TransferObjects.LoginRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginWindowController {
    private Client menu;
    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    void enter(ActionEvent event) throws IOException {
        login();
    }

    void setMain(Client menu){
        this.menu=menu;
    }

    @FXML
    public void enterId(ActionEvent actionEvent) {
        password.requestFocus();
    }

    public void login(){
        LoginRequest login=new LoginRequest();
        login.setName(name.getText());
        login.setPassword(password.getText());
        try {
            menu.getNetworkUtil().write(login);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void clickLogin(ActionEvent actionEvent) {
        login();
    }
}

