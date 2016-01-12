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

	<div class="container">
		<div class="row">
			<div class="span12">
			<div class="post">
			    <h2 class="post-title">
			        <a href="">${question.title}</a>
			    </h2>
			    <div class="post-metadata">
			        <span class="post-author">${question.writer}</span>,
			        <span class="post-date"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${question.createdDate}" /></span>
			    </div>
			    <div class="post-content">
			        <div class="about">내용 : </div>
			        ${question.contents}
			    </div>
			</div>
			
			<br /> 
		  	<a href="">수정</a>&nbsp;&nbsp;
		  	<a href="">삭제</a>&nbsp;&nbsp;
		  	<a href="/">목록으로</a>
		  	<br/>
			<br/>
			<div id="main">
				<form name="answer" method="post" action="/qna/addAnswer">
					<input type="hidden" name="questionId" value="${question.questionId}">
					<table>
						<tr>
							<td>이름</td>
							<td><input type="text" name="writer" id="writer" class="span3"/></td>
						</tr>
						<tr>
							<td>내용</td>
							<td><textarea name="content" id="content" class="span5" rows="5"></textarea></td>
						</tr>
					</table>
					<input type="submit" value="저장" />
				</form>
			</div>
			
		    <!-- comments start -->
			<div class="comments">
			    <h3>
			        댓글 수 : ${question.countOfComment}
			    </h3>
			    <c:forEach items="${answers}" var="each">
			    <div class="comment">
			    	<b>${each.writer}</b><p>${each.contents}</p>
			    </div>
			    </c:forEach>
			</div>
		</div>
	</div>
</body>
</html>