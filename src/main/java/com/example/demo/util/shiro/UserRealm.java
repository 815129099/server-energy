package com.example.demo.util.shiro;


import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if (this.userService == null) {
            this.userService = (UserService)SpringBeanFactoryUtils.getBean("userService");
        } else {
            System.out.println("UserRealm is not NULL");
        }

        authorizationInfo.setRoles(this.userService.findRoles(username));
        authorizationInfo.setStringPermissions(this.userService.findPermissions(username));
        return authorizationInfo;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();
        System.out.println(username);
        if (this.userService == null) {
            this.userService = (UserService)SpringBeanFactoryUtils.getBean("userService");
        } else {
            System.out.println("UserRealmsss is not NULL");
        }
        System.out.println(username);
        User user = this.userService.getUserByNumber(username);
        if (user == null) {
            throw new UnknownAccountException();
        } else {
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getGeNumber(), user.getPassword(), this.getName());
            return authenticationInfo;
        }
    }
}
