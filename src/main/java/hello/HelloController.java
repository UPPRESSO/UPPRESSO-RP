package hello;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
import sdk.Bean.TokenManager;
import sdk.Bean.UserInfo;
import sdk.Bean.UserManager;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import sdk.UPPRESSOInstance;
import sdk.UPPRESSOToken;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class HelloController {
    UPPRESSOInstance UPPRESSOInstance = new UPPRESSOInstance();
    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }
    @RequestMapping("/login")
    public ModelAndView login() {
        return UPPRESSOInstance.getInit();
    }


    @RequestMapping(value = "/authorization", method = RequestMethod.POST)
    public String authorization(@RequestBody String body, HttpServletRequest request, HttpSession session, HttpServletResponse response){
        System.out.println("/authorization");
        UPPRESSOInstance.receiveToken(request, body);
        UPPRESSOToken token = UPPRESSOInstance.getToken();
        if(token.isValid()) {
            System.out.println(token.getSubject());
            UserInfo localUserInfo = UserManager.getUserByID(token.getSubject());
            if (localUserInfo != null) {
                return "{\"result\":\"ok\", \"username\": \""+ localUserInfo.getUsername() +"\"}";
            } else {
                TokenManager.insertToken(token);
                Cookie cookie = new Cookie("token_id", token.getId());
                response.addCookie(cookie);
                return "{\"result\":\"register\"}";
            }
        }
        return "{\"result\":\"error\"}";
    }

    @RequestMapping(value = "/updateUsername", method = RequestMethod.POST)
    public String updateUsername(@RequestBody String body, HttpServletRequest request, HttpSession session, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        String ID = null;
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("token_id")) {
                ID = cookies[i].getValue();
            }
        }
        JSONObject jsonRequestBody = JSONObject.parseObject(body);
        String username = jsonRequestBody.getString("username");
        UPPRESSOToken token = TokenManager.getTokenbyID(ID);
        UserInfo user = new UserInfo();
        user.setID(token.getSubject());
        user.setUsername(username);
        UserManager.setUser(user);
        return "{\"result\":\"ok\"}";
    }

}