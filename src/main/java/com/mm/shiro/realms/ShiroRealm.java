package com.mm.shiro.realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;

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
        Object credentials = "e10adc3949ba59abbe56e057f20f883e";
        // 3.realName: 当前realm对象的name。调用父类的getName()方法即可
        String realName = getName();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, realName);
        return info;
    }


    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        Object credentials = "123456";
        Object salt = null;
        int hashIterations = 1;
        Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        System.out.println(result);
    }
}
