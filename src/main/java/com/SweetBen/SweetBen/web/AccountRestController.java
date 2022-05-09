package com.SweetBen.SweetBen.web;

import com.SweetBen.SweetBen.entities.AppRole;
import com.SweetBen.SweetBen.entities.AppUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
@CrossOrigin("*")
@RestController
@AllArgsConstructor
public class AccountRestController {

    private AccountService accountService;
    @GetMapping(path = "/users")
    public List<AppUser> appUsers(){
        return accountService.listUsers();
    }
    @PostMapping(path = "/users")
    public AppUser saveUser(@RequestBody AppUser appUser){
        return accountService.addUser(appUser);
    }
    @PostMapping(path="/roles")
    public AppRole saveRole(@RequestBody AppRole appRole){
        return accountService.addRole(appRole);
    }

    @PostMapping(path = "/addRoleToUser")
    //@PostAuthorize("hasAuthority('ADMIN')")
    public void addRoleToUser(@RequestBody RoleUserForm roleUserForm){
        accountService.addRoleToUser(roleUserForm.getUsername(),roleUserForm.getRolename());

    }
    @DeleteMapping(path="/deleteUser")
    public void removeUser(@RequestBody AppUser appUser){
        accountService.deleteUser(appUser.getUsername());
    }
    @GetMapping(path="/refreshToken")
    public void  refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authToken =request.getHeader("Authorization");
        if (authToken!=null && authToken.startsWith("Bearer")){
            try{
            String jwt = authToken.substring(7);
            Algorithm algorithm = Algorithm.HMAC256("mySecret");
            JWTVerifier jwtVerifier= JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
            String username=decodedJWT.getSubject();
            AppUser appUser = accountService.loadUserByUsername(username);
            String jwtAccessToken= JWT.create()
                        .withSubject(appUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+24*60*60*1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",appUser.getAppRoles().stream().map(ga->ga.getRoleName()).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String,String> idToken=new HashMap<>();
                idToken.put("access-token",jwtAccessToken);
                idToken.put("refresh-token",jwt);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(),idToken);
            }
            catch (Exception e){
            response.setHeader("error-message",e.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }

        }
        else{
            throw new RuntimeException("Refresh token required!!!");
        }

    }



}
@Data
class RoleUserForm{
    private String username;
    private String rolename;
}
