package com.SweetBen.SweetBen.web;

import com.SweetBen.SweetBen.entities.AppRole;
import com.SweetBen.SweetBen.entities.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public void addRoleToUser(@RequestBody RoleUserForm roleUserForm){
        accountService.addRoleToUser(roleUserForm.getUsername(),roleUserForm.getRolename());
    }


}
@Data
class RoleUserForm{
    private String username;
    private String rolename;
}
