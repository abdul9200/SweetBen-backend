package com.SweetBen.SweetBen.web;

import com.SweetBen.SweetBen.entities.AppRole;
import com.SweetBen.SweetBen.entities.AppUser;

import java.util.List;

public interface AccountService {
   AppUser addUser(AppUser appUser);
   AppRole addRole(AppRole appRole);
   void addRoleToUser(String userName,String roleName);
   AppUser loadUserByUsername(String userName);
   List<AppUser> listUsers();
   void deleteUser(String userName);
}
