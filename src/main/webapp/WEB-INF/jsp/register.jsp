<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
	  <%@include file="common/head.jsp"%>

	  <title>首页</title>
</head>
<body>
	<form>
		<div class="col-md-4 col-md-offset-4 middle" >
			<div class="input-group">
				<span class="input-group-addon" id="basic-addon1">U</span>
				<input type="text" class="form-control" name="username" id="username" placeholder="Username" aria-describedby="basic-addon1">
			</div>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon1">A</span>
			  <input type="text" class="form-control" name="account" id="account" placeholder="Account" aria-describedby="basic-addon1">
			</div>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon1">P</span>
			  <input type="password" class="form-control" name="passwd" id="passwd" placeholder="Password" aria-describedby="basic-addon1">
			</div>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon1">R</span>
			  <input type="password" class="form-control" name="repeat" id="repeat" placeholder="Repeat" aria-describedby="basic-addon1">
			</div>
			<div class="input-group col-md-2 col-md-offset-5">
			<input type="button" value="注册" class="btn btn-info" onclick="submitForm()">
			</div>
		</div>
		
	</form>

</body>
	<script>
		function submitForm() {
			var username = $("#username").val();
			var account = $("#account").val();
			var passwd = $("#passwd").val();
			var repeat = $("#repeat").val();
			if (passwd != repeat){
				alert("两次密码输入不一致！")
			} else {
				var formData = {"username": username, "account": account, "passwd": passwd, "repeat": repeat};
				$.ajax({
					url:"${pageContext.request.contextPath}/register.do",
					type:'POST',
					data:JSON.stringify(formData),
					dataType:'json',
					contentType:'application/json',
					success:function (data, status, xhr) {
						console.log("success:"+JSON.stringify(data)["code"]);
						var jsondata = eval(data);
						if (jsondata["code"] == "200"){
							$(window).attr("location", "${pageContext.request.contextPath}/loginPage.do");
						} else {
							alert(jsondata["message"]);
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