<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SLiPP</title>

<%@ include file="/include/header.jspf" %>

</head>
<body>
	<%@ include file="/include/navigation.jspf" %>
   
	<header class="jumbotron subhead" id="overview">
	<div class="container">
		<h1>SLiPP</h1>
		<p class="lead">Sustaninable Life, Programming, Programmer</p>
	</div>
	</header>     
    
	<div class="container">
		<div class="row">
			<div class="span12">
		        <table class="table">
		            <tr>
		                <td span="3">사용자 아이디</td>
		                <td span="3">이름</td>
		                <td span="3">이메일</td>
		                <td></td>
		            </tr>
		            <c:forEach items="${users}" var="user">
		            <tr>
	                    <td>${user.userId}</td>
	                    <td>${user.name}</td>
	                    <td>${user.email}</td>
	                    <td><a href="/user/updateForm?userId=${user.userId}">수정</a></td>
	                </tr>
	                </c:forEach>
		        </table>
			</div>
		</div>
	</div>
</body>
</html>