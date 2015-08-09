package sample;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableRow {

    private final StringProperty type;
    private final ObjectProperty<Integer> time;
    private final StringProperty desc;

    public TableRow() {
        this.type = new SimpleStringProperty();
        this.time = new SimpleObjectProperty<>();
        this.desc = new SimpleStringProperty();
    }

    public TableRow(String type, Integer time, String desc) {
        this.type = new SimpleStringProperty(type);
        this.time = new SimpleObjectProperty<>(time);
        this.desc = new SimpleStringProperty(desc);
    }

    public String getType() {
        return type.get();
    }

    public Integer getTime() {
        return time.get();
    }

    public String getDesc() {
        return desc.get();
    }


    public void setType(String type) {
        this.type.set(type);
    }

    public void setTime(Integer time) {
        this.time.set(time);
    }

    public void setDesc(String desc) {
        this.desc.set(desc);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TableRow@");
        sb.append(this.hashCode());
        sb.append("{");
        sb.append("type=").append(type.get());
        sb.append(", time=").append(time.get());
        sb.append(", desc=").append(desc.get());
        sb.append('}');
        return sb.toString();
    }
}
