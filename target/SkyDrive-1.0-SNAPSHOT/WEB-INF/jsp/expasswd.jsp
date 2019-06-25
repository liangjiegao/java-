<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/6/23 0023
  Time: 下午 4:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="common/tag.jsp"%>
<%@page import="com.superl.skyDriver.pojo.User" %>
<html>
<head>
    <%@include file="common/head.jsp"%>
    <title>密码提取</title>
</head>
<body>
    <div class="container">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    请输入提取密码：
                </div>
                <div class="panel-body">
                    <form action="">
                        <input type="text" name="link" style="display: none" value="${link}">
                        <input type="text" class="col-md-8" name="passwd">
                        <input type="submit" class="btn btn-primary col-md-2 right-pill" value="确定">
                    </form>
                </div>
                <div class="panel-footer">
                    <span>来自：${user.username}的分享</span>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
