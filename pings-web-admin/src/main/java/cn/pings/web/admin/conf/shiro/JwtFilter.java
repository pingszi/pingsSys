package cn.pings.web.admin.conf.shiro;

import cn.pings.sys.commons.util.ApiResponse;
import cn.pings.web.admin.util.JwtUtil;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *********************************************************
 ** @desc  ： JwtFilter
 ** @author  Pings
 ** @date    2019/1/23
 ** @version v1.0
 * *******************************************************
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    @Autowired
    private JwtComponent jwtComponent;

    /**登录认证*/
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //**判断用户是否要登入
        if (this.isLoginAttempt(request, response)) {
            try {
                //**登录认证
                return this.executeLogin(request, response);
            } catch (Exception e) {
                //**访问令牌过期 and 刷新令牌未过期则重新生成访问令牌
                try {
                    if (e.getCause() instanceof TokenExpiredException) {
                        String userName = JwtUtil.getUserName(this.getAuthzHeader(request));
                        String token = jwtComponent.sign(userName);
                        this.executeLogin(token, request, response);

                        //**修改响应头的访问令牌
                        JwtUtil.setHttpServletResponse((HttpServletResponse) response, token);
                        return true;
                    }
                } catch (Exception ex){
                    ex.printStackTrace();
                }

                e.printStackTrace();
                this.response401(request, response, e.getMessage());
                return false;
            }
        }

        return true;
    }

    /**去掉调用executeLogin，避免循环调用doGetAuthenticationInfo方法*/
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        this.sendChallenge(request, response);
        return false;
    }

    /**检测Header里面是否包含Authorization字段*/
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        String token = this.getAuthzHeader(request);
        return token != null;
    }

    /**调用JwtRealm进行登录认证*/
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        return this.executeLogin(this.getAuthzHeader(request), request, response);
    }

    /**支持跨域*/
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));

        //**跨域时会首先发送一个OPTIONS请求，返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }

        return super.preHandle(request, response);
    }

    /**401时直接返回Response信息*/
    private void response401(ServletRequest req, ServletResponse resp, String msg) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");

        ApiResponse response = new ApiResponse(HttpStatus.UNAUTHORIZED.value(), "Unauthorized: " + msg, null);
        String data = JSONObject.toJSONString(response);
        try(PrintWriter out = httpServletResponse.getWriter()) {
            out.append(data);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**调用JwtRealm进行登录认证*/
    private boolean executeLogin(String token, ServletRequest request, ServletResponse response) throws Exception {
        //**创建JwtToken
        JwtToken jwtToken = new JwtToken(token);
        //**提交给JwtRealm认证
        this.getSubject(request, response).login(jwtToken);
        //**没有抛出异常则代表登入成功
        return true;
    }

}
