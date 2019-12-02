package life.biubiu.community.controller;

import life.biubiu.community.dto.AccessTokenDto;
import life.biubiu.community.dto.GithubUser;
import life.biubiu.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * create by Muser on 2019/12/02
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @RequestMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        String acceesToken = githubProvider.getAcceesToken(accessTokenDto);
        GithubUser user = githubProvider.getUser(acceesToken);
        System.out.println(user.getName());
        return "index";
    }
}
