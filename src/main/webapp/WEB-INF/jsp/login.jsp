<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
	  <%@include file="common/head.jsp"%>

	  <title>登录</title>
</head>
<body>
	<form id="userForm">
		<div class="col-md-4 col-md-offset-4 middle" >
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon1">A</span>
			  <input type="text" class="form-control" name="account" placeholder="Account" aria-describedby="basic-addon1">
			</div>
			<div class="input-group">
			  <span class="input-group-addon" id="basic-addon1">P</span>
			  <input type="password" class="form-control" name="passwd" placeholder="Password" aria-describedby="basic-addon1">
			</div>
<%--			<div class="input-group">--%>
<%--			  <span class="input-group-addon" id="basic-addon1">R</span>--%>
<%--			  <input type="text" class="form-control" placeholder="Repeat" aria-describedby="basic-addon1">--%>
<%--			</div>--%>
			<div class="input-group col-md-2 col-md-offset-5">
			<input type="button" value="登录" class="btn btn-info" onclick="submitForm()">
			</div>
		</div>
		
	</form>

</body>
	<script>
		function submitForm() {
			var formData = {};
			var dataArray = $("#userForm").serializeArray();
			var dataSize = dataArray.length;
			for (var i = 0; i < dataSize; i++) {
				formData[dataArray[i].name] = dataArray[i].value;
				console.log(dataArray[i].value);
			}
			$.ajax({
				url:"${pageContext.request.contextPath}/login.do",
				type:'POST',
				data:JSON.stringify(formData),
				dataType:'json',
				contentType:'application/json',
				success:function (data, status, xhr) {
					console.log("success:"+JSON.stringify(data)["code"]);
					var jsondata = eval(data);
					if (jsondata["code"] == "200"){
						$(window).attr("location", "${pageContext.request.contextPath}/index.do")
					} else {
						alert("用户名或密码错误！")
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