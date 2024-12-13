package sample;

import Database.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.List;

public class PositionInputController {
    @FXML
    public Label label;

    @FXML
    public Button submit;

    @FXML
    public ChoiceBox choiceBox;
    private Stage stage;
    private Client client;

    public void load() {
        for (var x: Client.positions)choiceBox.getItems().add(x);

    }
    public void setMainStage(Client client, Stage stage) {
        this.client = client;
        this.stage=stage;
    }

    @FXML
    public void clickSubmit(ActionEvent actionEvent) throws Exception {
        String position = (String) choiceBox.getValue();
        List<Player> playerList= Client.SearchByPosition(position);
        stage.close();
        client.showPlayerList(playerList);
    }
}
