package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ToDoItem {
    private String task;
    private Date date;

    private BooleanProperty done = new SimpleBooleanProperty(false);

    public ToDoItem(String task, Date date, boolean isDone) {
        this.task = task;
        this.date = date;
        this.done = new SimpleBooleanProperty(isDone);
    }

    public boolean isDone() {
        return done.get();
    }

    public BooleanProperty doneProperty() {
        return done;
    }

    public void setDone(boolean done){
        this.done.set(done);
    }

}
