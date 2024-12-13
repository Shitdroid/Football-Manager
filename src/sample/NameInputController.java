package sample;

import Database.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class NameInputController {
    Client client;
    Stage stage;
    String playerName;
    @FXML
    private Label label;

    @FXML
    private TextField textInput;

    @FXML
    private Button reset;

    @FXML
    private Button submit;

    @FXML
    void clickReset(ActionEvent event) {
        textInput.setText(null);
    }

    @FXML
    void clickSubmit(ActionEvent event) throws Exception {
        playerName=textInput.getText();
        getBack();
    }

    private void getBack() throws Exception {
        Player p= Client.SearchPlayer(playerName);
        List<Player> playerList=new ArrayList<>();
        if(p!=null)playerList.add(p);
        stage.close();
        client.showPlayerList(playerList);
    }

    @FXML
    void enter(ActionEvent event) throws Exception {
        playerName=textInput.getText();
        getBack();
    }

    void setMainStage(Client client, Stage stage){
        this.stage=stage;
        this.client = client;
    }
}
