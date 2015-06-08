<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="oauth" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>授权页面</title>

	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">

	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</head>
<body>
<center>
			<h2>您将授予${client}站点以下权限：</h2>

			<form  style="margin-top:30px;" id="confirmationForm" name="confirmationForm"
				action="<%=request.getContextPath()%>/oauth/authorize" method="post">
				<input name="user_oauth_approval" value="true" type="hidden" />
					<c:forEach items="${scopeMap}" var="scope">
    							<font size="5px" style="margin-right: 10px"><c:out value="${scope.key}" /></font>
								<input type="radio" name="${scope.key}" value="true" ${scope.value=='true'?'checked':''} >授予
								<input type="radio" name="${scope.key}" value="false" ${scope.value=='false'?'checked':''}  >拒绝
					</c:forEach>
					<br>
							<button class="btn btn-primary" style="margin-top: 10px" type="submit">确认</button>
			</form>
</center>

</body>
</html>