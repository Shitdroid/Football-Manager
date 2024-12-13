package sample;

import Database.Country;
import Database.Player;
import TransferObjects.BuyRequest;
import TransferObjects.SellCancelRequest;
import TransferObjects.SellRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.NetworkUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuySellController {
    ObservableList<Player> playerData;
    ObservableList<Player> sellData;
    ObservableList<Player> buyData;
    NetworkUtil networkUtil;
    private boolean init=true;
    @FXML
    private Label clubName;

    @FXML
    private Label yearlySalary;

    @FXML
    private Label budget;

    @FXML
    private ImageView Logo;

    @FXML
    public TableView playerList;

    @FXML
    public TableView sellList;

    @FXML
    public TableView buyList;

    @FXML
    private Button sellButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button buyButton;

    @FXML
    private Button close;

    private Client client;

    public void clickSell(ActionEvent actionEvent) throws IOException {
        Player player = (Player) playerList.getSelectionModel().getSelectedItem();
        Player p=Client.SearchPlayer(player.getName());
        client.showSetPrice(p);
    }

    public void clickCancel(ActionEvent actionEvent) {
        Player p = (Player) sellList.getSelectionModel().getSelectedItem();
        SellCancelRequest request=new SellCancelRequest();
        request.setPlayer(p);
        p.setPlayerClub(Client.clubs.get(0));
        p.setPlayerCountry(Client.SearchCountry(p.getPlayerCountry().getName()));
        Client.players.add(p);
        try {
            networkUtil.write(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clickBuy(ActionEvent actionEvent) {
        Player p = (Player) buyList.getSelectionModel().getSelectedItem();
        if(p.getPrice()> client.budget){
            client.showAlert("money");
        }
        else{
            BuyRequest request=new BuyRequest();
            request.setPlayer(p);
            request.setToClub(Client.clubs.get(0).getName());
            try {
                networkUtil.write(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            p.setPlayerClub(Client.clubs.get(0));
            Country cn=Client.SearchCountry(p.getPlayerCountry().getName());
            if(cn==null){
                cn=new Country(p.getPlayerCountry().getName());
                Client.countries.add(cn);
            }
            p.setPlayerCountry(cn);
            cn.getPlayers().add(p);
            p.setPlayerCountry(Client.SearchCountry(p.getPlayerCountry().getName()));
            Client.clubs.get(0).getPlayers().add(p);
            p.setSalary(p.getSalary());
            //Client.players.add(p);
            for(var pl:Client.players) System.out.println(pl);
        }
    }

    public void load(){
        if(init){
            initializeTable();
            init=false;
        }
        playerData = FXCollections.observableArrayList(Client.players);
        sellData=FXCollections.observableArrayList(Client.sellList);
        buyData=FXCollections.observableArrayList(Client.buyList);
        clubName.setText(Client.clubs.get(0).getName());
        yearlySalary.setText(String.format("%,.2f",Client.clubs.get(0).Total_Salary()));
        budget.setText(String.format("%,.2f",client.budget));
        playerList.setEditable(true);
        playerList.setItems(playerData);
        playerList.refresh();
        sellList.setEditable(true);
        sellList.setItems(sellData);
        sellList.refresh();
        buyList.setEditable(true);
        buyList.setItems(buyData);
        buyList.refresh();
        sellButton.setDisable(true);
        buyButton.setDisable(true);
        cancelButton.setDisable(true);
    }

    private void initializeTable() {
        TableColumn<Player, String> nameCol = new TableColumn<>("Name");
        nameCol.setPrefWidth(172);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Player, String> sellNameCol = new TableColumn<>("Name");
        sellNameCol.setPrefWidth(175);
        sellNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Player, String> buyNameCol = new TableColumn<>("Name");
        buyNameCol.setPrefWidth(175);
        buyNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Player, String> countryNameCol = new TableColumn<>("Country");
        countryNameCol.setPrefWidth(90);
        countryNameCol.setCellValueFactory(new PropertyValueFactory<>("playerCountry"));

        TableColumn<Player, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setPrefWidth(45);
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Player, Integer> sellAgeCol = new TableColumn<>("Age");
        sellAgeCol.setPrefWidth(45);
        sellAgeCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Player, Integer> buyAgeCol = new TableColumn<>("Age");
        buyAgeCol.setPrefWidth(45);
        buyAgeCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Player, Double> heightCol = new TableColumn<>("Height");
        heightCol.setPrefWidth(55);
        heightCol.setCellValueFactory(new PropertyValueFactory<>("height"));

        TableColumn<Player, String> clubNameCol = new TableColumn<>("Club");
        clubNameCol.setPrefWidth(100);
        clubNameCol.setCellValueFactory(new PropertyValueFactory<>("playerClub"));

        TableColumn<Player, String> positionCol = new TableColumn<>("Position");
        positionCol.setPrefWidth(90);
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn<Player, String> sellPositionCol = new TableColumn<>("Position");
        sellPositionCol.setPrefWidth(90);
        sellPositionCol.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn<Player, String> buyPositionCol = new TableColumn<>("Position");
        buyPositionCol.setPrefWidth(90);
        buyPositionCol.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn<Player, Integer> numberCol = new TableColumn<>("Number");
        numberCol.setPrefWidth(55);
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<Player, Double> salaryCol = new TableColumn<>("Salary");
        salaryCol.setPrefWidth(88);
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        setFormat(salaryCol);

        TableColumn<Player, Double> sellSalaryCol = new TableColumn<>("Salary");
        sellSalaryCol.setPrefWidth(90);
        sellSalaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        setFormat(sellSalaryCol);

        TableColumn<Player, Double> buySalaryCol = new TableColumn<>("Salary");
        buySalaryCol.setPrefWidth(90);
        buySalaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        setFormat(buySalaryCol);

        TableColumn<Player, Double> priceCol = new TableColumn<>("Price");
        priceCol.setPrefWidth(95);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        setFormat(priceCol);
        playerList.getColumns().addAll(nameCol,ageCol,positionCol,salaryCol);
        playerList.getSortOrder().addAll(positionCol,nameCol);
        sellList.getColumns().addAll(sellNameCol,  sellAgeCol,sellPositionCol,sellSalaryCol);
        sellList.getSortOrder().addAll(sellPositionCol,sellNameCol);
        buyList.getColumns().addAll(buyNameCol, buyAgeCol,buySalaryCol, priceCol, heightCol,clubNameCol,buyPositionCol,numberCol,countryNameCol);
        buyList.getSortOrder().addAll(priceCol,buySalaryCol,buyNameCol);
        playerList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                sellList.getSelectionModel().clearSelection();
                buyList.getSelectionModel().clearSelection();
                sellButton.setDisable(false);
                buyButton.setDisable(true);
                cancelButton.setDisable(true);
            }
        });

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

        buyList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                sellList.getSelectionModel().clearSelection();
                playerList.getSelectionModel().clearSelection();
                sellButton.setDisable(true);
                buyButton.setDisable(false);
                cancelButton.setDisable(true);
            }
        });

        buyList.setRowFactory( tv -> {
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

        sellList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                playerList.getSelectionModel().clearSelection();
                buyList.getSelectionModel().clearSelection();
                sellButton.setDisable(true);
                buyButton.setDisable(true);
                cancelButton.setDisable(false);
            }
        });

        sellList.setRowFactory( tv -> {
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
        Logo.setImage(new Image(new ByteArrayInputStream(client.img)));
    }

    private void setFormat(TableColumn<Player,Double> tableCol){
        tableCol.setCellFactory(tc -> new TableCell<Player, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format("%,.2f",price));
                }
            }
        });
    }

    public void setMain(Client client) {
        this.client=client;
    }

    public void setNetworkUtil(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }

    public void clickClose(ActionEvent actionEvent) throws Exception {
        client.launchHome();
    }
}

