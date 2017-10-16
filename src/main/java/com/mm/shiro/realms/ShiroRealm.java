package com.mm.shiro.realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("[FirstRealm] doGetAuthenticationInfo");
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        System.out.println("从数据库中获取username：" + username);

        if ("unknown".equals(username)) {
            throw new UnknownAccountException("用户不存在！");
        }
        if ("monster".equals(username)) {
            throw new LockedAccountException("用户被锁定！");
        }
        // 1.principal: 认证实体信息
        Object principal = username;
        // 2.credentials: 密码
        Object credentials = null;//"fc1709d0a95a6be30bc5926fdb7f22f4";
        if ("admin".equals(username)) {
            credentials = "038bdaf98f2037b31f1e75b5b4c9b26e";
        } else if ("user".equals(username)) {
            credentials = "098d2c478e9c11555ce2823231e02ec1";
        }
        // 3.realName: 当前realm对象的name。调用父类的getName()方法即可
        String realName = getName();
        // 4.盐值(一般用userID)
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        SimpleAuthenticationInfo info = null;//new SimpleAuthenticationInfo(principal, credentials, realName);
        info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realName);
        return info;
    }


    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        Object credentials = "123456";
        Object salt = ByteSource.Util.bytes("user");
        int hashIterations = 1024;
        Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        System.out.println(result);
    }

    //授权会被shiro回调的方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取用户登录的信息
        Object principal = principals.getPrimaryPrincipal();
        //获取角色或权限(可能需要查询数据库)
        Set<String> roles = new HashSet<>();
        roles.add("user");
        if ("admin".equals(principal)) {
            roles.add("admin");
        }
        //设置reles属性
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        return info;
    }
}
