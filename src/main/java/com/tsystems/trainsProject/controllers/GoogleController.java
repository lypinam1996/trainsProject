package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.config.JwtRequestFilter;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Google2Api;
import org.scribe.builder.api.MyConstants;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class GoogleController
{
    @Autowired
    JwtRequestFilter            filter;

    private static final String NETWORK_NAME           = "Google";

    private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v2/userinfo?alt=json";

    private static final String SCOPE                  = "https://mail.google.com/ https://www.googleapis.com/auth/userinfo.email";

    private static final Token  EMPTY_TOKEN            = null;


    @RequestMapping(value = "/google", method = RequestMethod.GET)
    public RedirectView get()
    {
        String apiKey = MyConstants.GOOGLE_CLIENT_ID;
        String apiSecret = MyConstants.GOOGLE_CLIENT_SECRET;
        String callbackUrl = MyConstants.GOOGLE_REDIRECT_URL;
        OAuthService service = new ServiceBuilder().provider(Google2Api.class).apiKey(apiKey).apiSecret(apiSecret).callback(callbackUrl)
                .scope(SCOPE).build();
        String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
        return new RedirectView(authorizationUrl);
    }
}
