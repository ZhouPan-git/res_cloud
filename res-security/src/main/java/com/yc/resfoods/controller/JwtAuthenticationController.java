package com.yc.resfoods.controller;

import com.yc.bean.Resuser;
import com.yc.resfoods.configs.JwtTokenUtil;
import com.yc.resfoods.model.JwtResponse;
import com.yc.resfoods.service.JwtUserDetailsService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("ressecurity/resfood")
@Api(tags = "用户登录鉴权管理")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager; // Spring Security 提供的身份验证管理器

    @Autowired
    private JwtTokenUtil jwtTokenUtil; // 生成和验证 token 的实用类

    @Autowired
    private JwtUserDetailsService userDetailsService; // 访问数据库，验证用户名和密码的业务类

    @RequestMapping("/hello")
    public Claims firstPage(@RequestHeader("Authorization") String bearerToken) {
        System.out.println("接受到的token为："+bearerToken);
        String token=bearerToken.substring(7);
        Claims c=jwtTokenUtil.getAllClaimsFromToken(token);
        return c;
    }

    @LoadBalanced
    @ApiOperation(value = "用户登录操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "pwd", value = "密码", required = true)
    })
    @RequestMapping(value = "/resuser.action", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(String  username,String pwd) throws Exception {
        authenticate(username, pwd);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            // 调用认证管理器对输入的用户名和密码进行认证
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
