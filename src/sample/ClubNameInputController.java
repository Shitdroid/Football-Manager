package sample;

import Database.Club;
import Database.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ClubNameInputController {
    Client client;
    Stage stage;
    String clubName;
    String cmd;
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
        clubName=textInput.getText();
        getBack();
    }

    private void getBack() throws Exception {
        Club c = Client.SearchClub(clubName);
        List<Player> playerList=new ArrayList<>();
        stage.close();
        if(c==null) client.showAlert("club");
        else if(cmd.equalsIgnoreCase("Salary")){
            client.showTotalSalary(c);
        }
        else{
            if(cmd.equalsIgnoreCase("Age"))playerList=c.Max_Age_Players();
            else if(cmd.equalsIgnoreCase("Rich"))playerList=c.Max_Salary_Players();
            else if(cmd.equalsIgnoreCase("Tall"))playerList=c.Max_Height_Players();
            client.showPlayerList(playerList);
        }
    }

    @FXML
    void enter(ActionEvent event) throws Exception {
        clubName=textInput.getText();
        getBack();
    }

    void setMainStage(Client client, Stage stage){
        this.stage=stage;
        this.client = client;
    }

    public void setcmd(String cmd) {
        this.cmd=cmd;
    }
}
