package sample;

import Database.Club;
import Database.Country;
import Database.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Client extends Application {
    private Stage stage;
    private NetworkUtil networkUtil;

    double budget;
    boolean inMarket=false;
    boolean inHome=false;

    static List<Player> players=new ArrayList<>();
    static List<Player> buyList=new ArrayList<>();
    static List<Player> sellList=new ArrayList<>();
    static final List<Club> clubs=new ArrayList<>();
    static final List<Country> countries=new ArrayList<>();
    static final String[] positions={"Goalkeeper","Defender","Midfielder","Forward"};

    byte[] img;

    static Player addPlayer(String name, String countryName, String clubName, int age, double height,String position, int number, double salary){
        Player p=new Player();
        p.setName(name);
        p.setAge(age);
        p.setHeight(height);
        p.setNumber(number);
        p.setPosition(position);
        Club c=clubs.get(0);
        p.setPlayerClub(c);
        c.Add_Player(p);
        Country cn=SearchCountry(countryName);
        if(cn==null){
            cn=new Country(countryName);
            countries.add(cn);
        }
        p.setPlayerCountry(cn);
        cn.Add_Player(p);
        p.setSalary(salary);
        //players.add(p);
        return p;
    }
    //*************************************************************************/
    //here starts the fxml loaders
    //*************************************************************************/
    public void showCredits() throws Exception {
        Stage anchor = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Resources/credits.fxml"));
        Parent root = loader.load();

        CreditsController controller = loader.getController();
        controller.setStage(anchor);

        anchor.setTitle("Credits");
        Scene scene=new Scene(root, 604, 320);
        scene.getStylesheets().add(getClass().getResource("/CSSfiles/menuStyle.css").toExternalForm());
        anchor.setScene(scene);
        anchor.show();
    }

    public void showPlayerSearch() throws IOException {
        Stage anchor=new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Resources/playerSearch.fxml"));
        Parent root = loader.load();

        PlayerSearchController controller = loader.getController();
        controller.setMainStage(this,anchor);

        anchor.setTitle("Search By Players");
        Scene scene=new Scene(root, 487.6, 405.5);
        scene.getStylesheets().add(getClass().getResource("/CSSfiles/menuStyle.css").toExternalForm());
        anchor.setScene(scene);
        anchor.show();
    }

    public void showClubSearch() throws IOException {
        Stage anchor=new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Resources/clubSearch.fxml"));
        Parent root = loader.load();

        ClubSearchController controller = loader.getController();
        controller.setMainStage(this,anchor);

        anchor.setTitle("Search By Club");
        Scene scene=new Scene(root, 491, 354);
        scene.getStylesheets().add(getClass().getResource("/CSSfiles/menuStyle.css").toExternalForm());
        anchor.setScene(scene);
        anchor.show();
    }

    public void showNameInput() throws Exception {
        Stage anchor = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Resources/nameInput.fxml"));
        Parent root = loader.load();

        // Loading the controller
        NameInputController controller = loader.getController();
        controller.setMainStage(this,anchor);

        anchor.setTitle("Input Name");
        Scene scene=new Scene(root, 370, 253);
        scene.getStylesheets().add(getClass().getResource("/CSSfiles/menuStyleAlt.css").toExternalForm());
        anchor.setScene(scene);
        anchor.show();
    }

    public void showClubNameInput(String cmd) throws Exception {
        Stage anchor = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Resources/clubNameInput.fxml"));
        Parent root = loader.load();

        // Loading the controller
        ClubNameInputController controller = loader.getController();
        controller.setMainStage(this,anchor);
        controller.setcmd(cmd);

        anchor.setTitle("Input Club Name");
        Scene scene=new Scene(root, 370, 253);
        scene.getStylesheets().add(getClass().getResource("/CSSfiles/menuStyleAlt.css").toExternalForm());
        anchor.setScene(scene);
        anchor.show();
    }

    public void showClubCountryInput() throws Exception {
        Stage anchor = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Resources/clubCountryInput.fxml"));
        Parent root = loader.load();

        // Loading the controller
        ClubCountryInputController controller = loader.getController();
        controller.setMainStage(this,anchor);

        anchor.setTitle("Input Club & Country Name");
        Scene scene=new Scene(root, 400, 254);
        scene.getStylesheets().add(getClass().getResource("/CSSfiles/menuStyleAlt.css").toExternalForm());
        anchor.setScene(scene);
        anchor.show();
    }

    public void showPositionInput() throws Exception {
        Stage anchor = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Resources/positionInput.fxml"));
        Parent root = loader.load();

        // Loading the controller
        PositionInputController controller = loader.getController();
        controller.setMainStage(this,anchor);
        controller.load();

        anchor.setTitle("Select Position");
        Scene scene=new Scene(root, 305, 200);
        scene.getStylesheets().add(getClass().getResource("/CSSfiles/menuStyleAlt.css").toExternalForm());
        anchor.setScene(scene);
        anchor.show();
    }

    public void showAddPlayer() throws Exception {
        Stage anchor = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Resources/addPlayer.fxml"));
        Parent root = loader.load();

        // Loading the controller
        AddPlayerController controller = loader.getController();
        controller.setMainStage(this,anchor);
        controller.load();

        anchor.setTitle("Add Player");
        Scene scene=new Scene(root, 346, 544);
        scene.getStylesheets().add(getClass().getResource("/CSSfiles/menuStyle.css").toExternalForm());
        anchor.setScene(scene);
        anchor.show();
    }

    public void showPlayerList(List<Player> playerlist) throws Exception {
        if(playerlist.size()==0){
            showAlert("player");
        }
        else{
            Stage anchor = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Resources/playerList.fxml"));
            Parent root = loader.load();

            // Loading the controller
            PlayerListController controller = loader.getController();
            controller.setStage(anchor);
            controller.setPlayers(playerlist);
            controller.init();

            anchor.setTitle("Search Result");
            Scene scene=new Scene(root, 315, 406);
            scene.getStylesheets().add(getClass().getResource("/CSSfiles/menuStyleAlt.css").toExternalForm());
            anchor.setScene(scene);
            anchor.show();
        }
    }

    public void showSalaryRangeInput() throws Exception {
        Stage anchor = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Resources/salaryRangeInput.fxml"));
        Parent root = loader.load();

        // Loading the controller
        SalaryRangeInputController controller = loader.getController();
        controller.setMainStage(this,anchor);

        anchor.setTitle("Set Range");
        Scene scene=new Scene(root,  400, 218);
        scene.getStylesheets().add(getClass().getResource("/CSSfiles/menuStyleAlt.css").toExternalForm());
        anchor.setScene(scene);
        anchor.show();
    }

    public void showCountView() throws Exception {
        Stage anchor = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Resources/countView.fxml"));
        Parent root = loader.load();

        // Loading the controller
        CountViewController controller = loader.getController();
        controller.setStage(anchor);
        controller.load();

        anchor.setTitle("Player-wise Player Count");
        Scene scene=new Scene(root,  294, 402);
        scene.getStylesheets().add(getClass().getResource("/CSSfiles/tableStyle.css").toExternalForm());
        anchor.setScene(scene);
        anchor.show();
    }

    public void launchHome() throws Exception {
        stage.setOpacity(.5);
        stage.centerOnScreen();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Resources/home.fxml"));
        Parent root = loader.load();

        HomeController controller = loader.getController();
        controller.setMain(this);
        controller.load();
        inHome=true;
        inMarket=false;

        stage.setTitle("Football Manager 2021");
        Scene scene=new Scene(root, 827, 673);
        scene.getStylesheets().add(getClass().getResource("/CSSfiles/tableStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.setOpacity(1);
        stage.show();
    }

    public void launchMarket() throws Exception {
        stage.setOpacity(.5);
        stage.centerOnScreen();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Resources/buySell.fxml"));
        Parent root = loader.load();

        BuySellController controller = loader.getController();
        controller.setMain(this);
        controller.setNetworkUtil(networkUtil);
        controller.load();
        inHome=false;
        inMarket=true;

        stage.setTitle("Football Manager 2021 - Marketplace");
        Scene scene=new Scene(root, 1008, 681);
        scene.getStylesheets().add(getClass().getResource("/CSSfiles/tableStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.setOpacity(1);
        stage.show();
    }

    public void launchLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Resources/loginWindow.fxml"));
        Parent root = loader.load();

        LoginWindowController controller = loader.getController();
        controller.setMain(this);

        stage.setTitle("Football Manager 2021");
        Scene scene=new Scene(root, 551, 416);
        scene.getStylesheets().add(getClass().getResource("/CSSfiles/loginStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void showSetPrice(Player p) throws IOException {
        Stage anchor = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Resources/setPrice.fxml"));
        Parent root = loader.load();

        // Loading the controller
        SetPriceController controller = loader.getController();
        controller.setMainStage(this,anchor);
        controller.setNetworkUtil(networkUtil);
        controller.init(p);

        anchor.setTitle("Set Price");
        Scene scene=new Scene(root, 540, 348);
        scene.getStylesheets().add(getClass().getResource("/CSSfiles/menuStyleAlt.css").toExternalForm());
        anchor.setScene(scene);
        anchor.show();
    }

    public void showAlert(String cmd) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(cmd.equals("player")) {
            alert.setTitle("Player Not Found");
            alert.setHeaderText("Player Not Found");
            alert.setContentText("No player was found with the given specifications.");
        }
        else if(cmd.equals("club")){
            alert.setTitle("Club Not Found");
            alert.setHeaderText("Club Not Found");
            alert.setContentText("No club was found with the given name.");
        }
        else if(cmd.equals("login")){
            alert.setTitle("Wrong Credentials");
            alert.setHeaderText("Wrong Credentials");
            alert.setContentText("The provided User ID and Password do not match.");
        }
        else if(cmd.equals("online")){
            alert.setTitle("User Online");
            alert.setHeaderText("User Already Online");
            alert.setContentText("The User You are identifying yourself as is already online.");
        }
        else if(cmd.equals("money")){
            alert.setTitle("Insufficient money");
            alert.setHeaderText("Insufficient Money");
            alert.setContentText("You do not have the required budget to buy the player.");
        }
        else if(cmd.equals("crash")){
            alert.setTitle("Connection Error");
            alert.setHeaderText("Connection Error");
            alert.setContentText("The Server closed the connection forcibly.");
        }
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/CSSfiles/alertStyle.css").toExternalForm());
        alert.showAndWait();
    }

    public void showTotalSalary(Club club) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Total Yearly Salary");
        alert.setHeaderText(club.getName());
        alert.setContentText("Total Yearly Salary: "+String.format("%,.4f",club.Total_Salary()));
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/CSSfiles/alertStyle.css").toExternalForm());
        alert.showAndWait();
    }

    //**************************************************************************
    //these methods executes at launch
    //**************************************************************************
    @Override
    public void start(Stage primaryStage) throws Exception{
        stage=primaryStage;
        serverConnect();
        launchLogin();
    }

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    public void serverConnect() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 44444;
        networkUtil = new NetworkUtil(serverAddress, serverPort);
        new ClientReadThread(this);
    }

    public static void main(String args[]) {
        launch(args);
    }


    //*************************************************************************/
    //here starts the database operation methods
    //*************************************************************************/
    static Player SearchPlayer(String playerName){
        Player returnPlayer=null;
        for(var p:players){
            if(playerName.equalsIgnoreCase(p.getName()))returnPlayer=p;
        }
        return returnPlayer;
    }

    static Club SearchClub(String clubName){
        Club returnClub = null;
        for(var c: clubs)if(clubName.equalsIgnoreCase(c.getName()))returnClub=c;
        return returnClub;
    }

    static Country SearchCountry(String countryName){
        Country returnCountry = null;
        for(var cn: countries)if(countryName.equalsIgnoreCase(cn.getName()))returnCountry=cn;
        return returnCountry;
    }

    static List<Player> SearchByClubCountry(String countryName,String clubName){
        List<Player> playerList=new ArrayList<>();
        if(countryName.equalsIgnoreCase("Any") && clubName.equalsIgnoreCase("Any")) playerList = players;
        else if(countryName.equalsIgnoreCase("Any")){
            Club c=SearchClub(clubName);
            if(c!=null)playerList=c.getPlayers();
        }
        else if(clubName.equalsIgnoreCase("Any")){
            Country c=SearchCountry(countryName);
            if(c!=null)playerList=c.getPlayers();
        }
        else{
            Country cn=SearchCountry(countryName);
            List<Player> countryPlayers=new ArrayList<>();
            if(cn!=null)countryPlayers=cn.getPlayers();
            for (var p: countryPlayers) {
                if(clubName.equalsIgnoreCase(p.getPlayerClub().getName())){
                    playerList.add(p);
                }
            }
        }
        return playerList;
    }
    static List<Player> SearchByPosition(String position){
        List <Player> playerList=new ArrayList<>();
        for(var p:players){
            if(position.equalsIgnoreCase(p.getPosition()))playerList.add(p);
        }
        return playerList;
    }
    static List<Player> SearchBySalaryRange(double low, double high){
        List <Player> playerList=new ArrayList<>();
        for(var p:players){
            if(p.getSalary()>=low && p.getSalary()<=high)playerList.add(p);
        }
        return playerList;
    }
}
