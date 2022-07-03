package sdk.Bean;

import java.util.HashMap;

public class UserManager {
    static HashMap<String, UserInfo> userStorage = new HashMap<>();
    static public void setUser(UserInfo user){
        userStorage.put(user.getID(), user);
    }
    static public UserInfo getUserByID(String ID){
        return userStorage.get(ID);
    }
}
