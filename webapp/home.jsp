<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
		                <td span="6">제목</td>
		                <td span="2">작성자</td>
		                <td span="2">작성일</td>
		            </tr>
		            <c:forEach items="${questions}" var="each">
		            <tr>
	                    <td><a href="/qna/show?questionId=${each.questionId}">${each.title}</a></td>
	                    <td>${each.writer}</td>
	                    <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${each.createdDate}" /></td>
	                </tr>
	                </c:forEach>
		        </table>
			</div>
		</div>
	</div>
</body>
</html>