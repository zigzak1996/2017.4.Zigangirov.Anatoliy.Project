package kz.greetgo.education.stand.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.education.stand.util.WebAppContextRegistration;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumSet;

@Bean
public class PrintRequestParamsRegistration implements WebAppContextRegistration, Filter {
  @Override
  public void registerTo(WebAppContext webAppContext) {
    webAppContext.addFilter(new FilterHolder(this), "/*", EnumSet.of(DispatcherType.REQUEST));
    printRegistration();
  }

  @Override
  public double priority() {
    return -200;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void destroy() {
  }


  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
      throw new ServletException(getClass().getSimpleName() + " can work only with HTTP protocol");
    }
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    {
      StringBuilder out = new StringBuilder(request.getMethod());
      while (out.length() < 10) out.append(' ');
      out.append(' ');
      out.append(request.getRequestURI());
      System.out.println(out);
    }

    filterChain.doFilter(request, response);
  }
}
