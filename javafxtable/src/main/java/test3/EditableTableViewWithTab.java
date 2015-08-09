package test3;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class EditableTableViewWithTab extends Application {
  private TableView<Person> table = new TableView<>();
  private final ObservableList<Person> data = FXCollections
          .observableArrayList(new Person("Jacob", "Smith", "jacob.smith@example.com"), new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
                               new Person("Ethan", "Williams", "ethan.williams@example.com"), new Person("Emma", "Jones", "emma.jones@example.com"),
                               new Person("Michael", "Brown", "michael.brown@example.com"));

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    Scene scene = new Scene(new Group());
    stage.setTitle("Table View Sample");
    stage.setWidth(450);
    stage.setHeight(550);
    final Label label = new Label("Address Book");
    label.setFont(new Font("Arial", 20));
    table.setEditable(true);
    Callback<TableColumn, TableCell> cellFactory = p -> new EditingCell();

    TableColumn firstNameCol = new TableColumn("First Name");
    firstNameCol.setMinWidth(100);
    firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
    firstNameCol.setCellFactory(cellFactory);
    firstNameCol.setOnEditCommit((EventHandler<CellEditEvent<Person, String>>) t -> getPerson(t).setFirstName(t.getNewValue()));

    TableColumn lastNameCol = new TableColumn("Last Name");
    lastNameCol.setMinWidth(100);
    lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
    lastNameCol.setCellFactory(cellFactory);
    lastNameCol.setOnEditCommit((EventHandler<CellEditEvent<Person, String>>) t -> getPerson(t).setLastName(t.getNewValue()));

    TableColumn emailCol = new TableColumn("Email");
    emailCol.setMinWidth(200);
    emailCol.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
    emailCol.setCellFactory(cellFactory);
    emailCol.setOnEditCommit((EventHandler<CellEditEvent<Person, String>>) t -> getPerson(t).setEmail(t.getNewValue()));

    table.setItems(data);
    table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);


    table.setOnMouseClicked(this::addRow);

        final VBox vbox = new VBox();
    vbox.setSpacing(5);
    vbox.setPadding(new Insets(10, 0, 0, 10));
    vbox.getChildren().addAll(label, table);
    ((Group) scene.getRoot()).getChildren().addAll(vbox);
    stage.setScene(scene);
    stage.show();
  }

  private void addRow(final MouseEvent mouseEvent) {
    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
      if(mouseEvent.getClickCount() == 2){
        System.out.println("Double clicked");
        data.add(new Person());
      }
    }
  }

  private Person getPerson(final CellEditEvent<Person, String> t) {
    final Person person = t.getTableView().getItems().get(t.getTablePosition().getRow());
    if(person == null){
      t.getTableView().getItems().set(t.getTablePosition().getRow(), new Person(null,null,null));
    }

    return person;
  }
}