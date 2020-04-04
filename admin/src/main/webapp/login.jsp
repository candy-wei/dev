<%@ page import="com.ningyuan.core.Conf" %><%--
  Created by IntelliJ IDEA.
  User: james`
  Date: 2016/11/6
  Time: 12:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    request.setAttribute("path", path);
    request.setAttribute("basePath", basePath);

    String cookie = request.getHeader("Cookie");
    if(cookie.contains("remember-me") && cookie.replaceAll(".*?remember-me=(.*?);.*?", "$1").length() > 0){
        response.sendRedirect(path + Conf.get("security.authentication.success"));
    }
%>
<html>
<head>
    <title>促销管理</title>
    <link rel="stylesheet" href="${path}/app/lib/css/bootstrap.min.css">
    <link rel="stylesheet" href="${path}/app/css/login.css">
    <script src="${path}/app/lib/js/jquery-3.3.1.min.js"></script>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
</head>
<body onload="initLoad()">
<form:form action="${pageContext.request.contextPath}/login" method="post" onsubmit="setUserNameAndPassoword()">
    <div class="wrapper">
        <div class="container">
            <h1>促销系统</h1>
            <div class="form">
                <input type="text" id="username" name="username" value="" placeholder="账号">
                <input type="password" id="password" name="password" value="" placeholder="密码">
                <input type="hidden" id="remember-me" name="remember-me" value="true">
                <button type="submit" id="login-button">登录</button>
            </div>
        </div>
    </div>
</form:form>
</body>

<script>
    function initLoad(){
        if(getQueryString('flag') == 'loginFail'){
            alert("用户名或密码错误！");
            return;
        }
    }

    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg); //获取url中"?"符后的字符串并正则匹配
        var context = "";
        if (r != null)
            context = r[2];
        reg = null;
        r = null;
        return context == null || context == "" || context == "undefined" ? "" : context;
    }
</script>
</html>
