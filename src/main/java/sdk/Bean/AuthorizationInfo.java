package sdk.Bean;

public class AuthorizationInfo {
    String ID;
    String id_token;
    String sk_client;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }

    public String getSk_client() {
        return sk_client;
    }

    public void setSk_client(String sk_client) {
        this.sk_client = sk_client;
    }
}
