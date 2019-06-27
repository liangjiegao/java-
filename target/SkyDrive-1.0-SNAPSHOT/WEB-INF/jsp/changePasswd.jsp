<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
	  <%@include file="common/head.jsp"%>

	  <title>修改密码</title>
</head>
<body>
	<form>
		<div class="col-md-4 col-md-offset-4 middle" >
			<div class="input-group">
				<span class="input-group-addon" id="basic-addon1">U</span>
				<input type="text" class="form-control" name="username" id="username"
					   value="${user.username}" placeholder="Username" disabled aria-describedby="basic-addon1">
			</div>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon2">A</span>
				<input type="text" class="form-control" name="account" id="account" disabled value="${user.account}">			</div>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon3">P</span>
			  <input type="password" class="form-control" name="passwd" id="passwd" placeholder="请输入旧密码！" aria-describedby="basic-addon1">
			</div>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon4">N</span>
			  <input type="password" class="form-control" name="newPasswd" id="newPasswd" placeholder="请输入新密码！" aria-describedby="basic-addon1">
			</div>
			<div class="input-group">
				<span class="input-group-addon" id="basic-addon5">R</span>
				<input type="password" class="form-control" name="repeat" id="repeat" placeholder="请输重复入新密码！" aria-describedby="basic-addon1">
			</div>
			<div class="input-group col-md-2 col-md-offset-5">
			<input type="button" value="保存" class="btn btn-info" onclick="submitForm()">
			</div>
		</div>
		
	</form>

</body>
	<script>
		function submitForm() {
			var account = $("#account").val();
			var passwd = $("#passwd").val();
			var newPasswd = $("#newPasswd").val();
			var repeat = $("#repeat").val();

			if (newPasswd != repeat){
				alert("两次密码输入不一致！")
			} else {
				var formData = {"account": account, "passwd": passwd, "newPasswd": newPasswd};
				$.ajax({
					url:"${pageContext.request.contextPath}/changePasswd.do",
					type:'POST',
					data:formData,
					success:function (data, status, xhr) {
						console.log("success:"+JSON.stringify(data)["code"]);
						var jsondata = eval(data);
						alert(jsondata["message"]);
						if (jsondata["code"] == "200"){
							$(window).attr("location", "${pageContext.request.contextPath}/loginPage.do");
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
		}
	</script>
</html>