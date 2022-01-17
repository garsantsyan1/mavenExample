package education.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
public class Student implements Serializable {

    private String name;
    private String surname;
    private int age;
    private String email;
    private String phone;
    private Date registeredDate;
    private Lesson[] lessons;

}