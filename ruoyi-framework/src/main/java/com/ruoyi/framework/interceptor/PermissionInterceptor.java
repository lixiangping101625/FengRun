package com.ruoyi.framework.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.ruoyi.common.exception.user.TokenAuthFailedException;
import com.ruoyi.common.utils.JwtTokenUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.ClientUser;
import com.ruoyi.system.mapper.ClientUserMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 拦截器主要功能：
 * 1、拦截进行api请求的token校验
 * 2、获取当前用户，并在请求进入时写入LocalUser类，在请求结束时释放当前线程资源
 */
@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private JwtTokenUtils jwtConfig ;
    @Resource
    private ClientUserMapper clientUserMapper;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        response.setHeader("contentType", "text/js; charset=utf-8");
        //1、获取请求头token
        String token = request.getHeader(jwtConfig.getHeader());
        if (StringUtils.isEmpty(token)){
            throw new TokenAuthFailedException("token不能为空~");
        }
        //2、Token 验证(获取claims)
        Map<String, Claim> claims = jwtConfig.getClaims(token);
        //3、scope
        //4、读取自定义注解@ScopeLevel
//        ScopeLevel scopeLevel = this.getScopeLevel(handler);
//        if(null==scopeLevel){
//            this.set2ThreadLocal(claims);
//            return true;
//        }
//        //5、比较 scope和scope level
//        boolean valid = this.hasPermission(scopeLevel, claims);
//        //set到LocalUser
//        if(valid){
//            //将
//            this.set2ThreadLocal(claims);
//        }
        //即将过期重新生成token
        Date expireTime = claims.get("exp").asDate();
//        System.out.println(expireTime);
        int remainMinutes = (int) ((expireTime.getTime() - new Date().getTime()) / (1000*60));
        // TODO: 2020/12/11 修改重新颁发token时间 & token过期时间
        if(remainMinutes<=5){//小于5分钟重新颁发token
            String refreshToken = jwtConfig.makeToken(claims.get("uid").asString());
            response.setHeader("refreshToken",refreshToken);
        }       
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //释放ThreadLocal资源
        LocalUser.clear();
        super.afterCompletion(request, response, handler, ex);
    }

//    private ScopeLevel getScopeLevel(Object handler){
//        if(handler instanceof HandlerMethod){
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            ScopeLevel scopeLevel = handlerMethod.getMethodAnnotation(ScopeLevel.class);
//            return scopeLevel;
//        }
//        return null;
//    }
//
//    //根据接口权限注解@Scopelevel和token中的level，判断用户是否具有访问接口的权限
//    private boolean hasPermission(ScopeLevel scopeLevel, Map<String, Claim> map){
//        int level = scopeLevel.value();
//        Integer scope = map.get("scope").asInt();
//        if(level>scope){
//            throw new ForbiddenException();
//        }
//        return true;
//    }

    //获取用户信息并set到LocalUser
    private void set2ThreadLocal(Map<String, Claim> claims){
        String mobile = claims.get("mobile").asString();
        Integer scope = claims.get("scope").asInt();

        ClientUser clientUser = clientUserMapper.selectByMobile(mobile);
        LocalUser.set(clientUser, scope);
    }
}