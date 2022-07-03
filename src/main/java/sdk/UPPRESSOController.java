package sdk;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import sdk.Bean.*;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;


@RestController
public class UPPRESSOController {

    BigInteger bigIntP = new BigInteger(Configuration.hexP,16);
    BigInteger bigIntID_RP = new BigInteger(Configuration.hexID_RP,16);


    @RequestMapping(value = "/redir", method = RequestMethod.GET)
    public ModelAndView redir(){
        return new ModelAndView("redirect:" + Configuration.scriptRedirectUri);
    }


    @RequestMapping(value = "/startNegotiation", method = RequestMethod.POST)
    public String startNegotiation(@RequestBody String body, HttpServletResponse response){
        System.out.println("/startNegotiation");
        String ID = new Random().nextLong() + "";
        Cookie cookie = new Cookie("SSO_Session", ID);
        response.addCookie(cookie);
        JSONObject jsonRequestBody = JSONObject.parseObject(body);
        String t = jsonRequestBody.getString("t");
        LoginInstance loginInstance = new LoginInstance();
        BigInteger bigIntT = new BigInteger(t);
        BigInteger PID_RP = bigIntID_RP.modPow(bigIntT, bigIntP);
        loginInstance.setT(t);
        loginInstance.setPID_RP(PID_RP.toString());
        LoginInstanceManager.put(ID, loginInstance);
        JSONObject jsonResponseBody = new JSONObject();
        jsonResponseBody.put("result", "ok");
        jsonResponseBody.put("Cert_RP", Configuration.Cert_RP);
        jsonResponseBody.put("scope", "openid%20email");
        return jsonResponseBody.toJSONString();
    }





}
