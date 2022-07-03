package sdk.Bean;

import sdk.UPPRESSOToken;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TokenManager {
    static Map<String, UPPRESSOToken> map = new HashMap<String, UPPRESSOToken>();
    public static void insertToken(UPPRESSOToken token){
        map.put(token.getId(), token);
    }
    public static void deleteTokenbyID(String ID){
        map.remove(ID);
    }
    public static UPPRESSOToken getTokenbyID(String ID){
        return map.get(ID);
    }
}
