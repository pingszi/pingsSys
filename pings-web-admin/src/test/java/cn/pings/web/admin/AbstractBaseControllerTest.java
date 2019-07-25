package cn.pings.web.admin;

import cn.pings.sys.commons.entity.AbstractBaseEntity;
import cn.pings.sys.commons.util.ApiResponse;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public abstract class AbstractBaseControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    protected String accessToken;
    public static String ACCESS_TOKEN_KEY = "Authorization";
    protected String saveUrl;
    protected String deleteByIdUrl;
    protected Integer id;

    public AbstractBaseControllerTest(String saveUrl, String deleteByIdUrl){
        this.saveUrl = saveUrl;
        this.deleteByIdUrl = deleteByIdUrl;
    }

    @Before
    public void insertTestData() {
        this.account();

        this.request(this.saveUrl, HttpMethod.POST, this.getTestEntity());
    }

    @Test
    public void deleteTestData() {
        this.request(this.deleteByIdUrl + "/" + this.id, HttpMethod.DELETE);

        this.logout();
    }

    /**
     *********************************************************
     ** @desc ： 获取一个用于测试的实体对象
     ** @author Pings
     ** @date   2019/06/12
     ** @return entity
     * *******************************************************
     */
    protected abstract AbstractBaseEntity getTestEntity();

    protected ResponseEntity<ApiResponse> request(String url, HttpMethod method){
        return this.request(url, method, null, true);
    }

    protected ResponseEntity<ApiResponse> request(String url, HttpMethod method, boolean hasHeader){
        return this.request(url, method, null, hasHeader);
    }

    protected ResponseEntity<ApiResponse> request(String url, HttpMethod method, Object params){
        return this.request(url, method, params, true);
    }

    /**
     *********************************************************
     ** @desc ： 发送请求，获取响应
     ** @author Pings
     ** @date   2019/06/12
     ** @param  url    请求地址
     ** @param  method 请求方法
     ** @param  params 请求参数
     ** @param  hasHeader 是否包含头信息(如果访问有访问权限的资源，需要包含头信息)
     ** @return ResponseEntity
     * *******************************************************
     */
    protected ResponseEntity<ApiResponse> request(String url, HttpMethod method, Object params, boolean hasHeader){
        params = toMultiValueMap(params);
        HttpEntity<Object> entity = hasHeader ? new HttpEntity<>(params, this.getHeaders()) : new HttpEntity<>(params);

        return restTemplate.exchange(url, method, entity, ApiResponse.class);
    }

    /**
     *********************************************************
     ** @desc ： 获取请求头信息(头信息中添加accessToken)
     ** @author Pings
     ** @date   2019/06/12
     ** @return MultiValueMap
     * *******************************************************
     */
    private MultiValueMap<String, String> getHeaders(){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(ACCESS_TOKEN_KEY, this.accessToken);

        return headers;
    }

    private void account() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("userName", "pings");
        params.add("password", "123456");

        ResponseEntity<ApiResponse> result = this.request("/api/login/account", HttpMethod.POST, params, false);
        Assert.assertTrue("login failed", result.getBody() != null && result.getBody().getCode() == 200);

        List<String> tokens = result.getHeaders().get(ACCESS_TOKEN_KEY);
        if(tokens != null && !tokens.isEmpty())
            this.accessToken = tokens.get(0);
    }

    private void logout() {
        ResponseEntity<ApiResponse> result = this.request("/api/login/logout", HttpMethod.GET);
        Assert.assertTrue("logout failed", result.getBody() != null && result.getBody().getCode() == 200);
    }

    /**
     *********************************************************
     ** @desc ： 把请求参数转换为MultiValueMap对象
     ** @author Pings
     ** @date   2019/07/25
     ** @return MultiValueMap
     * *******************************************************
     */
    private static MultiValueMap<String, ?> toMultiValueMap(Object params) {
        if(params == null) return null;

        MultiValueMap<String, ?> rst;

        if(params instanceof MultiValueMap)
            rst = (MultiValueMap)params;
        else if(params instanceof Map){
            rst = new LinkedMultiValueMap<>();
            rst.setAll((Map)params);
        }
        else if(params instanceof AbstractBaseEntity){
            List<Field> fields = FieldUtils.getAllFieldsList(params.getClass());
            Map newParams = fields.stream().map(field -> {
                String key = field.getName();
                Object value;
                try {
                    value = FieldUtils.readField(field, params, true);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("get field value error: " + field.getName());
                }

                return new AbstractMap.SimpleEntry<>(key, value);
            }).filter(entry -> entry.getValue() != null)
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            rst = new LinkedMultiValueMap<>();
            rst.setAll(newParams);
        }
        else throw new IllegalArgumentException("Unsupported parameter types: " + params.getClass());

        return rst;
    }
}