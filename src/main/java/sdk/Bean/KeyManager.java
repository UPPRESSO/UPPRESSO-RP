package sdk.Bean;

import java.util.HashMap;

public class KeyManager {
    static HashMap<String, String> keyStorage = new HashMap<>();
    public static void put(String iss, String key){
        keyStorage.put(iss, key);
    }
    public static String get(String iss){
        return keyStorage.get(iss);
    }
}
