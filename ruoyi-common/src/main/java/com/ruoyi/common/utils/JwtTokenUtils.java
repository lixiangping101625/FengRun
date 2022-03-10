package com.ruoyi.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ruoyi.common.exception.user.TokenAuthFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: hxfy
 * @Package: com.hlkj.classics.util
 * @ClassName: JwtToken
 * @Author: Administrator
 * @Description: jwt-auth0 工具类
 * @Date: 2020/12/9 14:52
 * @Version: 1.0
 */
@Component
public class JwtTokenUtils {

    private static String secret;
    private static Integer expire;
    private static String header;
    private static Integer defaultScope = 8;//C端用户无权限分别时，统一定义为8

    @Value("${token.secret}")
    public void setSecret(String secret) {
        JwtTokenUtils.secret = secret;
    }
    @Value("${token.expire}")
    public void setExpire(Integer expire) {
        JwtTokenUtils.expire = expire;
    }
    @Value("${token.header}")
    public void setHeader(String header) {
        JwtTokenUtils.header = header;
    }
    public static String getHeader() {
        return header;
    }

    /**
     * 生成token
     * @param uId
     * @param scope 权限判断：用户令牌的scope和访问api的scope作比较
     * @return
     */
    public static String makeToken(String uId, Integer scope){
        return JwtTokenUtils.getToken(uId);
    }
//    public static String makeToken(String uId, Integer scope){
//        return JwtTokenUtils.getToken(uId,scope);
//    }

    //取默认等级
    public static String makeToken(String uId){
        return JwtTokenUtils.getToken(uId);
    }
//    public static String makeToken(String uId){
//        return JwtTokenUtils.getToken(uId, JwtTokenUtils.defaultScope);
//    }

    /**
     * 获取令牌
     * @param uId
     * @param scope
     * @return
     */
//    public static String getToken(String uId, Integer scope){
    public static String getToken(String uId){
        //入参为加密盐值
        Algorithm algorithm = Algorithm.HMAC256(JwtTokenUtils.secret);

        //签发时间
        Map<String, Date> dateMap = JwtTokenUtils.calculateExpiredIssues();
        return JWT.create()
                .withClaim("uid", uId)//根据业务写入
//                .withClaim("scope", scope)//根据业务写入
                .withIssuedAt(dateMap.get("issueTime"))//设置令牌签发时间
                .withExpiresAt(dateMap.get("expireTime"))//设置令牌过期时间
//                .withIssuer("虎啸风林")
                .sign(algorithm);
    }

    private static Map<String, Date> calculateExpiredIssues(){
        Map<String, Date> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.MINUTE, JwtTokenUtils.expire);
        map.put("issueTime", now);
        map.put("expireTime", calendar.getTime());
        return map;
    }

    /**
     * token校验：
     *  成功：返回claims
     *  失败：
     *      校验失败：throw new TokenAuthFailedException()
     *      过期：throw new TokenExpiredException()
     * @param token
     * @return
     */
    public static Map<String, Claim> getClaims(String token){
        Map<String, Claim> claims = null;
        Algorithm algorithm = Algorithm.HMAC256(JwtTokenUtils.secret);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = jwtVerifier.verify(token);
        } catch (com.auth0.jwt.exceptions.TokenExpiredException e) {//已过期
            //由用户输入引起的异常一般不记录日志。（特殊场景除外）
            e.printStackTrace();
            throw new TokenExpiredException("token已过期");
        } catch (JWTVerificationException e) {//token校验失败
            e.printStackTrace();
            throw new TokenAuthFailedException("token非法~");
            //由用户输入引起的异常一般不记录日志。（特殊场景除外）
        }
        claims = decodedJWT.getClaims();

        return claims;
    }

}