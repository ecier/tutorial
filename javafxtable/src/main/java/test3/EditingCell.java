package test3;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;


public class EditingCell extends TableCell<Person, String> {
  private TextField textField;

  public EditingCell() {
  }

  @Override
  public void startEdit() {
    if (!isEmpty()) {
      super.startEdit();
      if (textField == null) {
        createTextField();
      }
      setGraphic(textField);
      setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//      Platform.runLater(() -> {
//        textField.requestFocus();
//        textField.selectAll();
//      });
    }
  }

  @Override
  public void cancelEdit() {
    super.cancelEdit();
    setText(getItem());
    textField.setText(getItem());
    setGraphic(null);
    setContentDisplay(ContentDisplay.TEXT_ONLY);
  }

  @Override
  public void updateItem(String item,
                         boolean empty) {
    super.updateItem(item, empty);
    if (empty) {
      setText(null);
      setGraphic(null);
    } else {
      if (isEditing()) {
        if (textField != null) {
          textField.setText(getString());
        }
        setGraphic(textField);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
      } else {
        setText(getString());
        setContentDisplay(ContentDisplay.TEXT_ONLY);
      }
    }
  }

  private void createTextField() {
    textField = new TextField(getString());
    textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
    textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) {
        commitEdit(textField.getText());
      }
    });
    textField.setOnKeyPressed(t -> {
      if (t.getCode() == KeyCode.ENTER) {
        commitEdit(textField.getText());
      } else if (t.getCode() == KeyCode.ESCAPE) {
        cancelEdit();
      } else if (t.getCode() == KeyCode.TAB) {
        commitEdit(textField.getText());
        TableColumn nextColumn = getNextColumn(!t.isShiftDown());
        if (nextColumn != null) {
          getTableView().edit(getTableRow().getIndex(), nextColumn);
        }
      }
    });
  }

  private String getString() {
    return getItem() == null ? "" : getItem();
  }

  private TableColumn<Person, ?> getNextColumn(boolean forward) {
    List<TableColumn<Person, ?>> columns = new ArrayList<>();
    for (TableColumn<Person, ?> column : getTableView().getColumns()) {
      columns.addAll(getLeaves(column));
    }
    if (columns.size() < 2) {
      return null;
    }
    int currentIndex = columns.indexOf(getTableColumn());
    int nextIndex = currentIndex;
    if (forward) {
      nextIndex++;
      if (nextIndex > columns.size() - 1) {
        nextIndex = 0;
      }
    } else {
      nextIndex--;
      if (nextIndex < 0) {
        nextIndex = columns.size() - 1;
      }
    }
    return columns.get(nextIndex);
  }

  private List<TableColumn<Person, ?>> getLeaves(TableColumn<Person, ?> root) {
    List<TableColumn<Person, ?>> columns = new ArrayList<>();
    if (root.getColumns().isEmpty()) { /* We only want the leaves that are editable.*/
      if (root.isEditable()) {
        columns.add(root);
      }
      return columns;
    } else {
      for (TableColumn<Person, ?> column : root.getColumns()) {
        columns.addAll(getLeaves(column));
      }
      return columns;
    }
  }
}
