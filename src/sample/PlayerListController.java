package sample;

import Database.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.List;

public class PlayerListController {
    List<Player> players;
    int index=0;
    Stage stage;
    @FXML
    private Label name;

    @FXML
    private Label country;

    @FXML
    private Label age;

    @FXML
    private Label height;

    @FXML
    private Label club;

    @FXML
    private Label position;

    @FXML
    private Label number;

    @FXML
    private Label salary;

    @FXML
    private Button prev;

    @FXML
    private Button close;

    @FXML
    private Button next;

    @FXML
    void clickClose(ActionEvent event) {
        stage.close();
    }

    @FXML
    void clickNext(ActionEvent event) {
        if(index!=players.size()-1){
            index++;
            init();
        }
    }

    @FXML
    void clickPrevious(ActionEvent event) {
        if(index!=0){
            index--;
            init();
        }
    }

    public void init(){
        Player p= players.get(index);
        String playerName=p.getName();
        this.name.setText(playerName);
        String countryName=p.getPlayerCountry().getName();
        this.country.setText(countryName);
        String clubName=p.getPlayerClub().getName();
        this.club.setText(clubName);
        String playerAge =Integer.toString(p.getAge());
        this.age.setText(playerAge);
        String playerHeight =Double.toString(p.getHeight());
        this.height.setText(playerHeight);
        String playerSalary =Double.toString(p.getSalary());
        this.salary.setText(playerSalary);
        String playerPosition=p.getPosition();
        this.position.setText(playerPosition);
        String playerNumber=Integer.toString(p.getNumber());
        this.number.setText(playerNumber);
        if(index==0)this.prev.setDisable(true);
        else this.prev.setDisable(false);
        if(index==players.size()-1)this.next.setDisable(true);
        else this.next.setDisable(false);
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
