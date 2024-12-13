package sample;

import Database.Player;
import TransferObjects.AddRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddPlayerController {
    private String name,position,countryName,clubName;
    private int number, age;
    private double height,salary;
    private boolean flag=false;
    private Stage stage;
    private Client client;

    @FXML
    public Label errorName;

    @FXML
    public Label errorName1;

    @FXML
    public Label errorCountry;


    @FXML
    private TextField nameInput;

    @FXML
    private TextField countryInput;

    @FXML
    private TextField ageInput;

    @FXML
    private TextField heightInput;

    @FXML
    private ChoiceBox clubInput;

    @FXML
    private ChoiceBox<String> positionInput;

    @FXML
    private TextField numberInput;

    @FXML
    private TextField salaryInput;

    @FXML
    private Label errorAge;

    @FXML
    private Label errorHeight;

    @FXML
    private Label errorNumber;

    @FXML
    private Label errorSalary;

    @FXML
    private Label errorPosition;

    @FXML
    private Label errorClub;

    @FXML
    private Button reset;

    @FXML
    private Button submit;

    @FXML
    void clickReset(ActionEvent event) {
        nameInput.setText(null);
        countryInput.setText(null);
        ageInput.setText(null);
        heightInput.setText(null);
        clubInput.valueProperty().set(null);
        positionInput.valueProperty().set(null);
        numberInput.setText(null);
        heightInput.setText(null);
        errorName.setVisible(false);
        errorClub.setVisible(false);
        errorName1.setVisible(false);
        errorCountry.setVisible(false);
        errorHeight.setVisible(false);
        errorPosition.setVisible(false);
        errorSalary.setVisible(false);
        errorAge.setVisible(false);
        errorNumber.setVisible(false);
    }

    @FXML
    void clickSubmit(ActionEvent event) throws Exception {
        flag=true;
        checkSalary();
        checkNumber();
        checkPosition();
        checkClub();
        checkHeight();
        checkAge();
        checkCountry();
        checkName();
        if(flag){
            Player p=Client.addPlayer(name,countryName,clubName,age,height,position,number,salary);
            AddRequest request=new AddRequest();
            request.setPlayer(p);
            client.getNetworkUtil().write(request);
        }
        stage.close();
        client.launchHome();
    }

    @FXML
    void enterAge(ActionEvent event) {
        checkAge();
    }

    void checkAge(){
        try{
            age=Integer.parseInt(ageInput.getText());
            if(!flag)heightInput.requestFocus();
            errorAge.setVisible(false);
        }catch (Exception e){
            ageInput.setText(null);
            ageInput.requestFocus();
            errorAge.setVisible(true);
            flag=false;
        }
    }

    void checkClub() {
        if(!clubInput.getSelectionModel().isEmpty()){
            errorClub.setVisible(false);
            clubName= (String) clubInput.getValue();
            if(!flag)numberInput.requestFocus();;
        }
        else{
            clubInput.requestFocus();
            errorClub.setVisible(true);
            flag=false;
        }
    }

    @FXML
    void enterCountry(ActionEvent event) {
        checkCountry();
    }

    void checkCountry(){
        countryName=countryInput.getText();
        if(countryName==null||countryName.length()==0){
            errorCountry.setVisible(true);
            countryInput.setText(null);
            countryInput.requestFocus();
            flag=false;
        }
        else{
            errorCountry.setVisible(false);
            if(!flag)ageInput.requestFocus();
        }
    }

    @FXML
    void enterHeight(ActionEvent event) {
        checkHeight();
    }

    void checkHeight(){
        try{
            height=Double.parseDouble(heightInput.getText());
            if(!flag)clubInput.requestFocus();
            errorHeight.setVisible(false);
        }catch (Exception e){
            heightInput.setText(null);
            heightInput.requestFocus();
            errorHeight.setVisible(true);
            flag=false;
        }
    }

    @FXML
    void enterName(ActionEvent event) {
        checkName();
    }

    void checkName(){
        name=nameInput.getText();
        if(name==null||name.length()==0){
            errorName.setVisible(false);
            errorName1.setVisible(true);
            nameInput.setText(null);
            nameInput.requestFocus();
            flag=false;
        }
        else if(!(Client.SearchPlayer(name)==null)){
            errorName1.setVisible(false);
            errorName.setVisible(true);
            nameInput.setText(null);
            nameInput.requestFocus();
            flag=false;
        }
        else{
            if(!flag)countryInput.requestFocus();
            errorName.setVisible(false);
            errorName1.setVisible(false);
        }
    }

    @FXML
    void enterNumber(ActionEvent event) {
        checkNumber();
    }

    void checkNumber(){
        try{
            number=Integer.parseInt(numberInput.getText());
            if(!flag)salaryInput.requestFocus();
            errorNumber.setVisible(false);
        }catch (Exception e){
            numberInput.setText(null);
            numberInput.requestFocus();
            errorNumber.setVisible(true);
            flag=false;
        }
    }

    void checkPosition(){
        if(!positionInput.getSelectionModel().isEmpty()){
            errorPosition.setVisible(false);
            position= (String) positionInput.getValue();
            if(!flag)countryInput.requestFocus();
        }
        else{
            positionInput.requestFocus();
            errorPosition.setVisible(true);
            flag=false;
        }
    }

    @FXML
    void enterSalary(ActionEvent event) {
        checkSalary();
    }

    void checkSalary(){
        try{
            salary=Double.parseDouble(salaryInput.getText());
            if(!flag)submit.requestFocus();
            errorSalary.setVisible(false);
        }catch (Exception e){
            salaryInput.setText(null);
            salaryInput.requestFocus();
            errorSalary.setVisible(true);
            flag=false;
        }
    }

    public void setMainStage(Client client, Stage stage) {
        this.client = client;
        this.stage = stage;
    }
    public void load(){
        for (var x: Client.positions)positionInput.getItems().add(x);
        for(var c: Client.clubs)clubInput.getItems().add(c.getName());
    }
}
