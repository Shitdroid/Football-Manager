package sample;

import Database.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class HomeController {
    ObservableList<Player> data;
    private Client client;
    @FXML
    private AnchorPane anchor;

    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonClubSearch;

    @FXML
    public Button buttonCredits;

    @FXML
    public Button buttonExit;

    @FXML
    public Button buttonRecruit;

    @FXML
    public Button buttonPlayerSearch;

    @FXML
    public TableView playerList;

    @FXML
    private Label name;

    @FXML
    private Label count;

    @FXML
    private ImageView logo;

    private boolean init = true;

    private void initializeColumns() {
        TableColumn<Player, String> nameCol = new TableColumn<>("Name");
        nameCol.setPrefWidth(174);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Player, String> countryNameCol = new TableColumn<>("Country");
        countryNameCol.setPrefWidth(90);
        countryNameCol.setCellValueFactory(new PropertyValueFactory<>("playerCountry"));

        TableColumn<Player, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setPrefWidth(45);
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Player, Double> heightCol = new TableColumn<>("Height");
        heightCol.setPrefWidth(55);
        heightCol.setCellValueFactory(new PropertyValueFactory<>("height"));

        TableColumn<Player, String> clubNameCol = new TableColumn<>("Club");
        clubNameCol.setPrefWidth(100);
        clubNameCol.setCellValueFactory(new PropertyValueFactory<>("playerClub"));

        TableColumn<Player, String> positionCol = new TableColumn<>("Position");
        positionCol.setPrefWidth(90);
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn<Player, Integer> numberCol = new TableColumn<>("Number");
        numberCol.setPrefWidth(55);
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<Player, Double> salaryCol = new TableColumn<>("Salary");
        salaryCol.setPrefWidth(95);
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));


        playerList.getColumns().addAll(nameCol, countryNameCol, ageCol, heightCol,clubNameCol,positionCol,numberCol,salaryCol);
        playerList.getSortOrder().addAll(positionCol,nameCol);
        playerList.setRowFactory( tv -> {
            TableRow<Player> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Player rowData = row.getItem();
                    List<Player> p=new ArrayList<>();
                    p.add(rowData);
                    try {
                        client.showPlayerList(p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });
        name.setText(Client.clubs.get(0).getName());
        count.setText(String.valueOf(Client.clubs.get(0).getPlayers().size()));
        logo.setImage(new Image(new ByteArrayInputStream(client.img)));
    }

    public void load() {
        if (init) {
            initializeColumns();
            init = false;
        }
        buttonAdd.setStyle("-fx-background-image: url('/Resources/Add.png')");
        data = FXCollections.observableArrayList(Client.players);

        playerList.setEditable(true);
        playerList.setItems(data);
        playerList.refresh();
    }


    @FXML
    void onClickAdd(ActionEvent event) {
        try {
            client.showAddPlayer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClickClubSearch(ActionEvent event) {
        try {
            client.showClubSearch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onClickRecruit(ActionEvent actionEvent) {
        try {

            client.launchMarket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onClickPlayerSearch(ActionEvent actionEvent) {
        try {
            client.showPlayerSearch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickExit(ActionEvent actionEvent) throws IOException {
        client.getNetworkUtil().closeConnection();
        exit(0);
    }

    public void onClickCredits(ActionEvent actionEvent) {
        try {
            client.showCredits();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void setMain(Client client){
        this.client = client;
    }
}
