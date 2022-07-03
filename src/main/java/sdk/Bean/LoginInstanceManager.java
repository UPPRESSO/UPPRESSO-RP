package sdk.Bean;

import java.util.HashMap;

public class LoginInstanceManager {
    static private HashMap<String, Object> sessionStorage = new HashMap<>();
    static public void put(String name, Object value){
        sessionStorage.put(name, value);
    }
    static public Object getByName(String name){
        return sessionStorage.get(name);
    }
    static public boolean hasID(String name){
        if(sessionStorage.containsKey(name))
            return true;
        else
            return false;
    }
}
