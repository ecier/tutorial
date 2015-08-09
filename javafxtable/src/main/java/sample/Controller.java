package sample;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class Controller {

    @FXML
    private TableColumn<TableRow, String> typeCol;
    @FXML
    private TableColumn<TableRow, Integer> timeCol;
    @FXML
    private TableColumn<TableRow, String> descCol;
    @FXML
    private TableView<TableRow> historyTable;

     public void initialize() {


//        typeCol.setCellValueFactory(new PropertyValueFactory<HistoryRow, String>("type"));
//        timeCol.setCellValueFactory(new PropertyValueFactory<HistoryRow, String>("time"));
//        descCol.setCellValueFactory(new PropertyValueFactory<HistoryRow, String>("desc"));

         typeCol.setOnEditCommit(t -> getTableRow(t).setType(t.getNewValue()));
         timeCol.setOnEditCommit(t -> getTableRow(t).setTime(Integer.valueOf(t.getNewValue())));
         descCol.setOnEditCommit(t -> getTableRow(t).setDesc(t.getNewValue()));


        buildTable();
        editableColumns();
    }

    private TableRow getTableRow(CellEditEvent<TableRow, ?> t) {
        return t.getTableView().getItems().get(t.getTablePosition().getRow());
    }


    private void buildTable() {
        List<TableRow> data = new ArrayList<>();
        data.add(new TableRow("ewew1", 1, "wew"));
        data.add(new TableRow("ewew2", 2, "wew"));
        data.add(new TableRow("ewew3", 5, "wew"));
        historyTable.setItems(FXCollections.observableArrayList(data));


    }


    private void editableColumns() {
        typeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        timeCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        descCol.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    public void addRow(ActionEvent actionEvent) {
        ObservableList<TableRow> items = historyTable.getItems();
        items.add(new TableRow());
        historyTable.setItems(FXCollections.observableArrayList(items));
    }

    public void saveTable(ActionEvent actionEvent) {
        historyTable.getItems().forEach(System.out::println);

    }
}
