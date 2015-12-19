<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@	taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<body>
<h2>Hello World!</h2>
<sec:authorize access="">

This content will only be visible to users who have the "supervisor" authority in their list of <tt>GrantedAuthority</tt>s.

</sec:authorize>
<br>
<sec:accesscontrollist hasPermission="read,write" domainObject="#Role">
	accesscontrolllist
</sec:accesscontrollist>
<br>
<sec:authorize access="hasPermission('Role','read') or hasPermission('Role','write')">
	hasPermission
</sec:authorize>
<br>
<sec:authorize access="hasPermission(12345,'Role','read') or hasPermission(12121,'Role','write')">
	hasPermission
</sec:authorize>
</body>
</html>
