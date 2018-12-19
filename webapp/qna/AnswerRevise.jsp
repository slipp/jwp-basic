<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html lang="kr">

<head>
  <%@ include file="/include/header.jspf" %> 
</head>
<body>
<%@ include file="/include/navigation.jspf" %>
<div class="container" id="main">
   <div class="col-md-12 col-sm-12 col-lg-10 col-lg-offset-1">
      <div class="panel panel-default content-main">
          <form name="question" method="post" action="/api/qna/updateAnswer?answerId=${answer.answerId}">
          	  <input type="hidden" name="questionId" value="${answer.answerId}" />
            
              <div class="form-group">
                  <label for="contents">내용</label>
                  <textarea name="contents" id="contents" rows="5" class="form-control">${answer.contents}</textarea>
              </div>
              <button type="submit" class="btn btn-success clearfix pull-right">수정하기</button>
              <div class="clearfix" />
          </form>
        </div>
    </div>
</div>

<%@ include file="/include/footer.jspf" %>
	</body>
</html>