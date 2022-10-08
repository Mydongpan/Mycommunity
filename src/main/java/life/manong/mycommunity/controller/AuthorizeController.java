package life.manong.mycommunity.controller;

import life.manong.mycommunity.dto.AccessTokenDTO;

import life.manong.mycommunity.dto.GithubUser;
import life.manong.mycommunity.mapper.UserMapper;
import life.manong.mycommunity.model.User;
import life.manong.mycommunity.provider.GithubProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@Slf4j
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    /**
     * 调用方法，返回access_token
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletRequest request
                           ){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null){
            User user = new User();
            user.setAccountId(githubUser.getId().toString());
            user.setToken(UUID.randomUUID().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setName(githubUser.getName());
//            userMapper.insert(user);
            request.getSession().setAttribute("user",githubUser);
        }else {
            return "redirect:/";
        }

        return "index";
    }

    @GetMapping("/insert")
    public String testInsert(){

        User user = new User();

        user.setName("马远");
        user.setAccountId("123245");
        user.setToken("1241235245");
        user.setGmtModified(System.currentTimeMillis());
        user.setGmtCreate(System.currentTimeMillis());
        userMapper.insert(user);

        return "success";
    }
}
