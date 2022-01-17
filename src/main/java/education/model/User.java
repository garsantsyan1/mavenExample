package education.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;



@Data
@AllArgsConstructor
public class User implements Serializable {

    private String name;
    private String surname;
    private String email;
    private String password;
    private Type type;

}
