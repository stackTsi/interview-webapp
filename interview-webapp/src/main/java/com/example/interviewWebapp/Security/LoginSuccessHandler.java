package com.example.interviewWebapp.Security;

import com.example.interviewWebapp.Entity.Users;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        Users user = (Users) authentication.getPrincipal();

        String sessionId = request.getSession().getId();
        var writer = response.getWriter();
        writer.write(String.format("""
            {
              "user": {
                "id": "%s",
                "username": "%s",
                "role": "%s",
                "fullName": "%s"
              },
              "sessionId": "%s"
            }
        """, user.getId(), user.getUsername(), user.getRole(), user.getFullName(),sessionId));
        writer.flush();
    }
}
