package sample;

import Database.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class ClubCountryInputController {
    private Client client;
    @FXML
    public TextField cLubInput;

    @FXML
    public TextField countryInput;

    @FXML
    public Button submit;

    @FXML
    public Button reset;
    private Stage stage;

    @FXML
    public void enterCountry(ActionEvent actionEvent) throws Exception {
        if(countryInput.getText()==null || countryInput.getText().length()==0)countryInput.setText("Any");
        submit();
    }
    @FXML
    public void enterClub(ActionEvent actionEvent) {
        if(cLubInput.getText()==null || cLubInput.getText().length()==0)cLubInput.setText("Any");
        countryInput.requestFocus();
    }
    @FXML
    public void clickReset(ActionEvent actionEvent) {
        cLubInput.setText(null);
        countryInput.setText(null);
    }
    @FXML
    public void clickSubmit(ActionEvent actionEvent) throws Exception {
        if(cLubInput.getText()==null || cLubInput.getText().length()==0 )cLubInput.setText("Any");
        if(countryInput.getText()==null || countryInput.getText().length()==0)countryInput.setText("Any");
        submit();
    }

    public void setMainStage(Client client, Stage stage) {
        this.client = client;
        this.stage=stage;
    }

    public void submit() throws Exception {
        String clubName=cLubInput.getText();
        String countryName=countryInput.getText();
        List<Player> playerList= Client.SearchByClubCountry(countryName,clubName);
        stage.close();
        client.showPlayerList(playerList);
    }
}
