package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ClubSearchController {
    Client client;
    Stage stage;

    @FXML
    private Button buttonRich;

    @FXML
    private Button buttonSenior;

    @FXML
    private Button buttonTall;

    @FXML
    private Button buttonSalary;

    @FXML
    private Button buttonBack;

    @FXML
    void clickBack(ActionEvent event) {
        stage.close();
    }

    @FXML
    void clickRich(ActionEvent event) throws Exception {
        client.showClubNameInput("Rich");
    }

    @FXML
    void clickSalary(ActionEvent event) throws Exception {
        client.showClubNameInput("Salary");
    }

    @FXML
    void clickSenior(ActionEvent event) throws Exception {
        client.showClubNameInput("Age");
    }

    @FXML
    void clickTall(ActionEvent event) throws Exception {
        client.showClubNameInput("Tall");
    }

    public void setMainStage(Client client, Stage stage) {
        this.client = client;
        this.stage = stage;
    }
}
