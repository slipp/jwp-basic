<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SLiPP :: 로그인</title>

<%@ include file="/include/header.jspf" %>

</head>
<body>
	<%@ include file="/include/navigation.jspf" %>

	<div class="container">
		<div class="row">
			<div class="span12">
				<section id="typography">
				<div class="page-header">
					<h1>로그인</h1>
				</div>
				<div id="main">
					<form name="login" method="post" action="/login">
						<table>
							<tr>
								<td>사용자 아이디</td>
								<td><input type="text" name="userId"></td>
							</tr>
							<tr>
								<td>비밀번호</td>
								<td><input type="password" name="password"></td>
							</tr>
						</table>
						<input type="submit" value="로그인" />
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>