package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CreditsController {
    Stage stage;
    @FXML
    private Button button;

    @FXML
    void onClick(ActionEvent event) throws Exception {
        stage.close();
    }

    void setStage(Stage stage){
        this.stage=stage;
    }
}
