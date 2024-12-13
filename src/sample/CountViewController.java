package sample;

import Database.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CountViewController {

    @FXML
    public Button close;
    @FXML
    private TableView tableView;

    ObservableList<Country> data;
    private boolean init=true;
    private Stage stage;

    private void initializeColumns() {
        TableColumn<Country, String> nameCol = new TableColumn<>("Country");
        nameCol.setPrefWidth(145);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Country, String> countCol = new TableColumn<>("Number of Players");
        countCol.setPrefWidth(144);
        countCol.setCellValueFactory(new PropertyValueFactory<>("count"));
        countCol.setSortType(TableColumn.SortType.DESCENDING);
        tableView.getColumns().addAll(nameCol, countCol);
        tableView.getSortOrder().addAll(countCol);
    }
    public void load() {
        if (init) {
            initializeColumns();
            init = false;
        }
        data = FXCollections.observableArrayList(Client.countries);

        tableView.setEditable(true);
        tableView.setItems(data);
    }

    @FXML
    public void onClick(ActionEvent actionEvent) {
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
