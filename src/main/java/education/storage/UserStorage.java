package education.storage;

import education.exception.UserNotFoundException;
import education.model.User;
import education.util.FileUtil;


import java.util.HashMap;
import java.util.Map;

public class UserStorage {

    private Map<String, User> users = new HashMap<>();


    public void add(User user) {
        users.put(user.getEmail(), user);
        serialize();
    }

    public void print() {
        for (User value : users.values()) {
            System.out.println(value);
        }
    }

    public User getByEmail(String email) throws UserNotFoundException {
        if (users.get(email) != null) {
            return users.get(email);
        }
        throw new UserNotFoundException("Email doesn't exist - " + email);
    }

    public void initData() {
        Map<String, User> userMap = FileUtil.deserializeUserMap();
        if (userMap != null) {
            users = userMap;
        }
    }

   public void serialize() {
       FileUtil.serializeUsersMap(users);
   }

}
