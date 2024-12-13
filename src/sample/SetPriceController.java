package sample;

import Database.Player;
import TransferObjects.SellRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.NetworkUtil;

import java.util.List;

public class SetPriceController {
    Player p;
    Stage stage;
    Double price;
    Client client;
    NetworkUtil networkUtil;
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
    private Label error;

    @FXML
    private Button close;

    @FXML
    private Button submit;

    @FXML
    private TextField priceInput;

    @FXML
    void clickClose(ActionEvent event) {
        stage.close();
    }

    @FXML
    void enterSubmit(ActionEvent event) {
        try{
            price=Double.parseDouble(priceInput.getText());
            p.setPrice(price);
            Client.players.remove(p);
            SellRequest request=new SellRequest();
            request.setPlayer(p);
            request.setPrice(price);
            networkUtil.write(request);
            stage.close();
        }catch (Exception e){
            priceInput.setText(null);
            priceInput.requestFocus();
            error.setVisible(true);
        }
    }

    public void init(Player player){
        p=Client.SearchPlayer(player.getName());
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
    }

    public void setMainStage(Client client,Stage stage) {
        this.stage = stage;
        this.client=client;
    }

    public void setNetworkUtil(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }

    public void clickSubmit(ActionEvent actionEvent) {
        try{
            price=Double.parseDouble(priceInput.getText());
            p.setPrice(price);
            System.out.println(price);
            Client.players.remove(p);
            SellRequest request=new SellRequest();
            request.setPlayer(p);
            networkUtil.write(request);
            stage.close();
            client.launchMarket();
        }catch (Exception e){
            priceInput.setText(null);
            priceInput.requestFocus();
            error.setVisible(true);
        }
    }
}
