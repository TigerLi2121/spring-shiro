package com.mm.shiro.realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

public class SecondRealm extends AuthenticatingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("[SecondRealm] doGetAuthenticationInfo");
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
            credentials = "ce2f6417c7e1d32c1d81a797ee0b499f87c5de06";
        } else if ("user".equals(username)) {
            credentials = "073d4c3ae812935f23cb3f2a71943f49e082a718";
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
        String hashAlgorithmName = "SHA1";
        Object credentials = "123456";
        Object salt = ByteSource.Util.bytes("admin");
        int hashIterations = 1024;
        Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        System.out.println(result);
    }
}
