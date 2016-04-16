<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/include/tags.jspf" %>

<!DOCTYPE html>
<html lang="kr">
<head>
    <%@ include file="/include/header.jspf" %>
</head>
<body>
<%@ include file="/include/navigation.jspf" %>

<div class="container" id="main">
    <div class="col-md-6 col-md-offset-3">
        <div class="panel panel-default content-main">
        	<form:form name="user" modelAttribute="user" action="/users" method="post">
        		<form:hidden path="id"/>
                <div class="form-group">
                    <label for="userId">사용자 아이디</label>
                    <form:input path="userId" cssClass="form-control"/>
                    <form:errors path="userId" cssClass="error" />
                </div>
                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <form:password path="password" cssClass="form-control"/>
                    <form:errors path="password" cssClass="error" />
                </div>
                <div class="form-group">
                    <label for="name">이름</label>
                    <form:input path="name" cssClass="form-control"/>
                    <form:errors path="name" cssClass="error" />
                </div>
                <div class="form-group">
                    <label for="email">이메일</label>
                    <form:input path="email" cssClass="form-control"/>
                    <form:errors path="email" cssClass="error" />
                </div>
                <button type="submit" class="btn btn-success clearfix pull-right">회원가입</button>
                <div class="clearfix" />
            </form:form>
        </div>
    </div>
</div>

<%@ include file="/include/footer.jspf" %>
</body>
</html>
