<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SLiPP :: 회원가입</title>

<%@ include file="/include/header.jspf" %>

</head>
<body>
	<%@ include file="/include/navigation.jspf" %>

    <div class="container">
        <div class="row">
            <div class="span12">
                <section id="typography">
                <div class="page-header">
                    <h1>회원가입</h1>
                </div>
                
                <form name="user" method="post" action="/user/create">
                    <table>
                        <tr>
                            <td>사용자 아이디</td>
                            <td><input type="text" name="userId"></td>
                        </tr>
                        <tr>
                            <td>비밀번호</td>
                            <td><input type="password" name="password"></td>
                        </tr>
                        <tr>
                            <td>이름</td>
                            <td><input type="text" name="name"></td>
                        </tr>
                        <tr>
                            <td>이메일</td>
                            <td><input type="text" name="email"></td>
                        </tr>
                    </table>
                    <input type="submit" value="회원가입" />
                </form>
            </div>
        </div>
    </div>
</body>
</html>