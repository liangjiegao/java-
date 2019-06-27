<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/6/26 0026
  Time: 上午 9:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="common/head.jsp"%>
    <title>个人信息修改</title>
</head>
<body>
<form>
    <div class="col-md-4 col-md-offset-4 middle" >
        <div class="input-group">
            <span class="input-group-addon" id="basic-addon1">U</span>
            <input type="text" class="form-control" name="username" id="username"
                   value="${user.username}" placeholder="Username" aria-describedby="basic-addon1">
        </div>
        <div class="input-group">
            <span class="input-group-addon" id="basic-addon2">A</span>
            <input type="text" class="form-control" name="account" id="account" disabled value="${user.account}">
        </div>
        <div class="input-group">
            <a href="${pageContext.request.contextPath}/showCPPage.do?account=${user.account}" type="button" class="btn btn-info" >修改密码</a>
        </div>
        <div class="input-group col-md-2 col-md-offset-5">
            <input type="button" value="保存个人信息" class="btn btn-info" onclick="submitForm()">
        </div>
    </div>

</form>
</body>
<script>
    function submitForm() {
        var username = $("#username").val();
        var account = $("#account").val();

        var formData = {"username": username, "account": account};
        $.ajax({
            url:"${pageContext.request.contextPath}/saveUserInfo.do",
            type:'POST',
            data:JSON.stringify(formData),
            dataType:'json',
            contentType:'application/json',
            success:function (data, status, xhr) {
                console.log("success:"+JSON.stringify(data)["code"]);
                var jsondata = eval(data);
                alert(jsondata["message"]);
                if (jsondata["code"] == "200"){
                    $(window).attr("location", "${pageContext.request.contextPath}/index.do");
                }
            },
            error:function (jqXHR, error, exception) {
                console.log("调用失败！");
                console.log(jqXHR);
                console.log(error);
                console.log(exception);
            },
            complete:function (jqXHR) {
                console.log("调用完成！");
            }
        });
    }

</script>
</html>
