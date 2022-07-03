package sdk.Bean;

import java.security.KeyPair;

public class KeyPairManager {
    static KeyPair k;

    static public KeyPair getK() {
        return k;
    }

    static public void setK(KeyPair k1) {
        k = k1;
    }
}
