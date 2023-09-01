package com.yc.resfoods.configs;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.bean.Resuser;
import com.yc.resfoods.dao.ResuserDao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtTokenUtil implements Serializable {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; // 5 hours

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private ResuserDao resuserDao;

//    private String secret= "javainuse";

    public static void main(String[] args) {
        UserDetails ud = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return "$2a$10$Xa6.fSB1DgMHP7D9fIuAWO3UfQDojOFkRir7T.HF2PqJuzq7QWsh6";
            }

            @Override
            public String getUsername() {
                return "a";
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
        JwtTokenUtil jtu = new JwtTokenUtil();
        String token= jtu.generateToken(ud );
        System.out.println(token);
//        System.out.println(new BCryptPasswordEncoder().encode("a"));
        Claims c=jtu.getAllClaimsFromToken(token);
        System.out.println(c.get("sub")+"\t"+c.get("exp")+"\t"+c.get("iat")+"\t"+c.get("pwd")+"\t"+c.get("userid")+"\t"+c.get("username"));
    }

    // 生成token
    public String generateToken(UserDetails userDetails) {
        //存载荷：七个默认值：sub ，iss 。。。
        Map<String, Object> claims = new HashMap<>();
        //自己增加的载荷
        claims.put("username", userDetails.getUsername());
        claims.put("pwd", userDetails.getPassword());
        QueryWrapper<Resuser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", userDetails.getUsername());
        wrapper.eq("pwd", userDetails.getPassword());
        Resuser user=this.resuserDao.selectOne(wrapper);
        claims.put("userid", user.getUserid());
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    // 从token中获取用户名
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // 从token中获取过期日期
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // 利用secret密钥从token中获取信息
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // 检测token是否过期
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username =getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
