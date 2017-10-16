<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>List Page</h1>
Welcome: <shiro:principal></shiro:principal>
<shiro:hasRole name="admin">
    <br/>
    <a href="admin.jsp">Admin Page</a>
</shiro:hasRole>
<shiro:hasRole name="user">
    <br/>
    <a href="user.jsp">User Page</a>
</shiro:hasRole>
<br/>
<a href="shiro/testShiroAnnotation">Test ShiroAnnotation</a>
<br/>
<a href="shiro/logout">Logout</a>
</body>
</html>
