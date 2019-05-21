package cn.pings.web.bill.conf;

import cn.pings.service.api.sys.service.UserService;
import cn.pings.sys.commons.jwt.JwtFilter;
import cn.pings.service.api.sys.jwt.JwtRealm;
import cn.pings.sys.commons.jwt.JwtVerifier;
import cn.pings.sys.commons.jwt.RefreshTokenJwtVerifier;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *********************************************************
 ** @desc  ： Shiro配置
 ** @author  Pings
 ** @date    2019/1/23
 ** @version v1.0
 * *******************************************************
 */
@Configuration
public class ShiroConfig {

    //**访问令牌过期时间(分钟)
    @Value("${sys.jwt.access-token.expire-time}")
    private long accessTokenExpireTime;
    //**刷新信息过期时间(分钟)
    @Value("${sys.jwt.refresh-token.expire-time}")
    private long refreshTokenExpireTime;
    //**密钥
    @Value("${sys.jwt.secret}")
    private String secret;

    @Reference(version = "${sys.service.version}")
    private UserService userService;

    @Bean
    public JwtVerifier verifier(RedisTemplate<String, Object> redisTemplate){
        return RefreshTokenJwtVerifier.Builder.newBuilder(redisTemplate)
                .accessTokenExpireTime(accessTokenExpireTime)
                .refreshTokenExpireTime(refreshTokenExpireTime)
                .secret(secret)
                .build();
    }

    @Bean
    public JwtRealm jwtRealm(JwtVerifier verifier){
        return new JwtRealm(this.userService, verifier);
    }

    @Bean
    @Scope("prototype")
    public JwtFilter jwtFilter(JwtVerifier verifier){
        return new JwtFilter(verifier);
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(JwtRealm jwtRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        //**使用自定义JwtRealm
        manager.setRealm(jwtRealm);

        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);

        return manager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager, JwtFilter jwtFilter) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        //**添加自定义过滤器jwt
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("jwt", jwtFilter);
        factoryBean.setFilters(filterMap);

        factoryBean.setSecurityManager(securityManager);

        //**自定义url规则
        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        //不拦截请求swagger-ui页面请求
        filterRuleMap.put("/webjars/**", "anon");
        //jwt过滤器拦截请求
        filterRuleMap.put("/**", "jwt");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);

        return factoryBean;
    }

    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
