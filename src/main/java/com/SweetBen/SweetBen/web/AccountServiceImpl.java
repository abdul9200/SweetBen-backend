package com.SweetBen.SweetBen.web;

import com.SweetBen.SweetBen.dao.AppRoleRepository;
import com.SweetBen.SweetBen.dao.AppUserRepository;
import com.SweetBen.SweetBen.entities.AppRole;
import com.SweetBen.SweetBen.entities.AppUser;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppRoleRepository appRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public AppUser addUser(AppUser appUser) {

        String p = appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(p));
        AppRole appRole =appRoleRepository.findByRoleName("USER");
        appUser.getAppRoles().add(appRole);
        return appUserRepository.save(appUser);

    }

    @Override
    public AppRole addRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        AppUser appUser=appUserRepository.findByUsername(userName);
        AppRole appRole=appRoleRepository.findByRoleName(roleName);
        appUser.getAppRoles().add(appRole);

    }

    @Override
    public AppUser loadUserByUsername(String userName) {

        return appUserRepository.findByUsername(userName);
    }

    @Override
    public List<AppUser> listUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public void deleteUser(String userName) {
        AppUser appUser=loadUserByUsername(userName);
        appUserRepository.delete(appUser);

    }
}
