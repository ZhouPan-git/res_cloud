package com.yc.resfoods;

import com.yc.resfoods.configs.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

/**
 * @Author zp
 * @Date 2023/8/21 16:59
 * @PackageName:com.yc.resfoods
 * @ClassName: TestToken
 * @Description:
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityApp.class})
@Slf4j
public class TestToken {
    @Autowired
    private JwtTokenUtil jtu;

    @Test
    public void test(){
        UserDetails ud=new UserDetails(){

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
                return false;
            }

        };

        String token=jtu.generateToken(ud);
        System.out.println(token);

        Claims c=jtu.getAllClaimsFromToken(token);
        System.out.println(c.get("sub")+"\t"+c.get("exp")+"\t"+c.get("iat")+"\t"+c.get("pwd")+"\t"+c.get("userid")+"\t"+c.get("username"));
    }
}