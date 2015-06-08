<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>登陆页面</title>
	
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">

	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</head>
<body>
	<form class="form-horizontal" style="margin-top:30px;" action="/loginHandle" method="POST">
  <div class="form-group">
    <label for="username" class="col-xs-4 control-label">用户名</label>
    <div class="col-xs-3">
      <input type="text" class="form-control" id="username" name="username" placeholder="username">
    </div>
  </div>
  <div class="form-group">
    <label for="password" class="col-xs-4 control-label">密码</label>
    <div class="col-xs-3">
      <input type="password" class="form-control" id="password" name="password" placeholder="password">
    </div>
  </div>
  <div class="form-group">
    <div class="col-xs-5 control-label">
      <button type="submit" class="btn btn-default">Sign in</button>
    </div>
  </div>
</form>

 <c:if test="${not empty error}">
    	<div class="row">
    	  <div style="text-align : center; color: red;">
    	     Sorry! 认证失败！ 
    	  </div>
    	</div>
      </c:if>
</body>
</html>