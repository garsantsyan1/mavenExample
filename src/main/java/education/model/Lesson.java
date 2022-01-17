package education.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Lesson implements Serializable {

    private String name;
    private String duration;
    private String lecturerName;
    private double price;

}
