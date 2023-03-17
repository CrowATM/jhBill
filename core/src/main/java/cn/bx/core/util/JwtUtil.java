package cn.bx.core.util;

import cn.bx.core.config.security.model.SecurityModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class JwtUtil {

    private final static Logger log = LoggerFactory.getLogger(JwtUtil.class);

    // 默认过期时间
    private static String jwtVersion = SpringBeanUtil.getBeanByName("jwtVersion");

    public static void setJwtVersion(String jwtVersion) {
        JwtUtil.jwtVersion = jwtVersion;
    }

    /**
     * 生成签名,5min后过期,会加时间戳
     * @param username 用户名
     * @param secret 用户的密码
     * @return 加密的token
     */
    //可选 requestComponent.getIpAddress(); ip作为盐
    public static String sign(String username, String secret) {
        SecurityModel securityModel = SpringBeanUtil.getBeanByClass(SecurityModel.class);
        long expireTime = securityModel.getToken().getExpireTime();
        Date expireDate = new Date(System.currentTimeMillis() + expireTime);
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withClaim("username", username)
                    .withClaim("version", jwtVersion)
                    .withExpiresAt(expireDate)
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
//            throw new RuntimeException("生成token签名失败！", e);
        }
    }

    /**
     * 校验token是否正确
     * @param token 密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .withClaim("version", jwtVersion)
                    .build();
            //验证失败会抛出异常
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 从token中获取指定字段属性
     * @param claim 字段名
     * @return token中保存的字段
     */
    public static String getClaim(String token, String claim){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
