package dw.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dw.utils.Utils;

/**
 * AuthFilter.java <br>
 * comment
 * 
 * @author Sunny (tufeiping@gmail.com)
 * @version 2014年8月30日 上午8:47:47
 */

public class AuthFilter implements Filter {

    private FilterConfig config;
    private String[] ecludeUrls;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String url = httpRequest.getRequestURI().toLowerCase();
        String user = Utils.getUser(httpRequest);
        if (user == null && !needIgnoeFromPath(url)) { // 用户为空，且是非资源，非登录和首页，都需要验证
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index");
            return;
        }
        // 所有的资源都默认处理
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        String ecludeUrl = this.config.getInitParameter("ecludeUrl");
        if (ecludeUrl != null) {
            ecludeUrls = ecludeUrl.trim().toLowerCase().split(";");
        }
    }

    private boolean needIgnoeFromPath(String url) {
        if (ecludeUrls == null) //没有启用过滤，默认全部通过
            return true;
        for (String _url : ecludeUrls) { // 启用后，指通过特定的
            if (url.indexOf(_url) != -1)
                return true;
        }
        return false;
    }
}
