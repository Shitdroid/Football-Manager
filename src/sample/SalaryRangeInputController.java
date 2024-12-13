package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SalaryRangeInputController {

    private Stage stage;
    private Client client;

    @FXML
    private Label label;

    @FXML
    private TextField lowerInput;

    @FXML
    private Button reset;

    @FXML
    private Button submit;

    @FXML
    private TextField upperInput;

    @FXML
    private Label errorMessage;

    @FXML
    void clickReset(ActionEvent event) {
        lowerInput.setText(null);
        upperInput.setText(null);
    }

    @FXML
    void clickSubmit(ActionEvent event) {
        try{
            double low=Double.parseDouble(lowerInput.getText());
            double high=Double.parseDouble(upperInput.getText());
            if(high<low){
                errorMessage.setVisible(true);
                clickReset(event);
            }
            else{
                stage.close();
                client.showPlayerList(Client.SearchBySalaryRange(low,high)) ;
            }
        }catch(Exception e){
            errorMessage.setVisible(true);
            clickReset(event);
        }
    }

    public void setMainStage(Client client, Stage stage) {
        this.stage = stage;
        this.client = client;
        errorMessage.setVisible(false);
    }
}
