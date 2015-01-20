package com.shop.security;

import com.google.common.base.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Natalia
 * Date: 07.06.14
 * Time: 11:48
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    String context = request.getSession().getServletContext().getContextPath();
    Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
    if (roles.contains("ROLE_ADMIN")){
      response.sendRedirect((!Strings.isNullOrEmpty(context) ? context : "") + "/admin/orders");
      return;
    }
    response.sendRedirect((!Strings.isNullOrEmpty(context) ? context : "") + "/user/orders");
  }

}
