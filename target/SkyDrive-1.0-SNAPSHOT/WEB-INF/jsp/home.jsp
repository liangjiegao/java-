<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="common/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
      <%@include file="common/head.jsp"%>
      <title>门户</title>
</head>
<body>
	<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
      <ul class="nav navbar-nav navbar-right">

          <li><a href="${pageContext.request.contextPath}/loginPage.do">登录</a></li>
          <li><a href="${pageContext.request.contextPath}/registerPage.do">注册</a></li>

      </ul>
  </div><!-- /.navbar-collapse -->

</nav>
</body>
</html>