// src/main/java/com/snow/ecomproject/controller/UserInfoController.java
package com.snow.ecomproject.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserInfoController {

    @GetMapping("/userinfo")
    public Map<String, Object> userInfo(Principal principal) {

        if (principal == null) {
            return Map.of("authenticated", false);
        }


        if (principal instanceof OAuth2AuthenticationToken token) {
            OAuth2User oauthUser = token.getPrincipal();

            return Map.of(
                    "authenticated", true,
                    "attributes", oauthUser.getAttributes()
            );
        }

        // Fallback if it's not an OAuth2 token
        return Map.of("authenticated", true, "name", principal.getName());
    }
}
