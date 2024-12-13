package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class PlayerSearchController {
    Stage stage;

    private Client client;


    @FXML
    private Button buttonName;

    @FXML
    private Button buttonClub;

    @FXML
    private Button buttonPosition;

    @FXML
    private Button buttonSalary;

    @FXML
    private Button buttonBack;

    @FXML
    private Button buttonCountCountryPlayer;

    @FXML
    void clickBack(ActionEvent event) {
        stage.close();
    }

    @FXML
    void clickClub(ActionEvent event) throws Exception {
        client.showClubCountryInput();
    }

    @FXML
    void clickName(ActionEvent event) throws Exception {
        client.showNameInput();
    }

    @FXML
    void clickPosition(ActionEvent event) throws Exception {
        client.showPositionInput();
    }

    @FXML
    void clickSalary(ActionEvent event) throws Exception {
        client.showSalaryRangeInput();
    }

    @FXML
    void clickCountCountryPlayer(ActionEvent event) throws Exception {
        client.showCountView();
    }

    public void setMainStage(Client client, Stage stage) {
        this.client = client;
        this.stage=stage;
    }
}
