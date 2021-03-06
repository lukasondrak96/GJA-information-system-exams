package cz.vutbr.fit.gja.authentication;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class for success authentication
 */
@Configuration
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

    /**
     * Processes authentication and redirect on page which show after login
     * @param request HttpRequest
     * @param response HttpResponse
     * @param authentication Authentication information
     * @throws IOException Errors
     */
    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            return;
        }
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /**
     * Controls authentication data and assign a role for logged user
     * @param authentication Data of authentication
     * @return Valid url for logged user or error url
     */
    protected String determineTargetUrl(Authentication authentication) {
        String url = "/login?error=true";

        // Fetch the roles from Authentication object
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<String>();
        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        // check user role and decide the redirect URL
        if (roles.contains("LOGGED_IN_USER")) {
            url = "/logged/exams";
        }

        return url;
    }
}
