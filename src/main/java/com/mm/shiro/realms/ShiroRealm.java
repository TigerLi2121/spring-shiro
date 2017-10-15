package com.mm.shiro.realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

public class ShiroRealm extends AuthenticatingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
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
        // 4.盐值
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
}
