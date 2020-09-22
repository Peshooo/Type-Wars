package com.typewars.web.filter;

import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Component
public class NicknameFilter implements Filter {
  private static final String NICKNAME = "nickname";
  private static final List<String> PATHS_TO_SKIP = ImmutableList.of(NICKNAME, "images", "files", "scripts", "styles", "WEB-INF");

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    doHttpFilter((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, filterChain);
  }

  private void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    String uri = request.getRequestURI();

    if (uri.contains(NICKNAME)) {
      String nickname = request.getParameter(NICKNAME);

      if (nickname != null) {
        request.getSession().setAttribute(NICKNAME, nickname);
        request.getRequestDispatcher("/").forward(request, response);
      } else {
        filterChain.doFilter(request, response);
      }

      return;
    }

    HttpSession httpSession = request.getSession();
    String nickname = (String) httpSession.getAttribute(NICKNAME);

    if (shouldForward(nickname, uri)) {
      request.getRequestDispatcher(NICKNAME).forward(request, response);
      return;
    }

    filterChain.doFilter(request, response);
  }

  private boolean shouldForward(String nickname, String uri) {
    return nickname == null && PATHS_TO_SKIP.stream().noneMatch(uri::contains);
  }

  @Override
  public void destroy() {

  }
}