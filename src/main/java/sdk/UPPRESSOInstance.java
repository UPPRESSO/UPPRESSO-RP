package sdk;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import sdk.Bean.Configuration;
import sdk.Bean.LoginInstance;
import sdk.Bean.LoginInstanceManager;
import sdk.Tools.Util;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class UPPRESSOInstance {
    BigInteger bigIntP = new BigInteger(Configuration.hexP,16);
    BigInteger bigIntQ = new BigInteger(Configuration.hexQ,16);

    UPPRESSOToken UPPRESSOToken;





    public void receiveToken(HttpServletRequest request, String body) {
        JSONObject jsonRequestBody = JSONObject.parseObject(body);
        String id_token = jsonRequestBody.getString("Token");
        String ID = null;
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("SSO_Session")) {
                ID = cookies[i].getValue();
            }
        }
        LoginInstance loginInstance = (LoginInstance) LoginInstanceManager.getByName(ID);
        DecodedJWT token = decodeToken(id_token);
        UPPRESSOToken = new UPPRESSOToken();
        if(token != null) {
            if(token.getAudience().contains(loginInstance.getPID_RP())){
                UPPRESSOToken.setValid(true);
            }else {
                UPPRESSOToken.setValid(false);
            }
            BigInteger temp[] = ExtendEculid(new BigInteger(loginInstance.getT()), bigIntQ);
            BigInteger _result = temp[1];
            BigInteger sub = new BigInteger(token.getSubject());
            BigInteger userIdentity = sub.modPow(_result, bigIntP);
            UPPRESSOToken.init(token, userIdentity.toString());
        }else {
            UPPRESSOToken.setValid(false);
        }
    }
    public BigInteger[] ExtendEculid(BigInteger a, BigInteger b)
    {
        BigInteger x,  y;
        if (b.compareTo(new BigInteger("0"))==0)
        {
            x = new BigInteger("1");
            y = new BigInteger("0");
            BigInteger[] t = new BigInteger[3];
            t[0] = a; t[1] = x; t[2] = y;
            return t;
        }
        BigInteger[] t = ExtendEculid(b, a.mod(b));
        BigInteger result = t[0];
        x = t[1];
        y = t[2];
        BigInteger temp = x;
        x = y;
        y = temp.subtract(a.divide(b).multiply(y));
        BigInteger[] t1 = new BigInteger[3];
        t1[0] = result; t1[1] = x; t1[2] = y;
        return t1;
    }
    DecodedJWT decodeToken(String token){
        String estr = Util.bytes2HexString(Base64.getUrlDecoder().decode(Configuration.PK_IDP.e));
        String nstr = Util.bytes2HexString(Base64.getUrlDecoder().decode(Configuration.PK_IDP.n));
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(new BigInteger(nstr, 16), new BigInteger(estr, 16));
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            return null;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
            return null;
        } catch (InvalidKeySpecException e1) {
            e1.printStackTrace();
            return null;
        }
    }
    public UPPRESSOToken getToken() {
        return UPPRESSOToken;
    }

    public ModelAndView getInit() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("script");
        return mv;
    }
}
