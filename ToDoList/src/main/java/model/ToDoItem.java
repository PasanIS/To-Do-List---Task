package model;

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
    private boolean isDone;
}
