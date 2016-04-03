// $(".qna-comment").on("click", ".answerWrite input[type=submit]", addAnswer);
$(".answerWrite input[type=submit]").click(addAnswer);
$(".qna-comment-slipp-articles").on("click", ".link-delete-article", deleteAnswer);
$(".link-delete-article").on("click", deleteQuestion);
//$(".link-delete-article").click(deleteAnswer);
function addAnswer(e) {
  e.preventDefault();

  var queryString = $("form[name=answer]").serialize();

  $.ajax({
    type : 'post',
    url : '/api/qna/addAnswer',
    data : queryString,
    dataType : 'json',
    error: onError,
    success : onSuccess,
  });
}

function onSuccess(json, status){
  var answer = json.answer;
  var questionId = json.questionId;
  var answerTemplate = $("#answerTemplate").html();
  var template = answerTemplate.format(answer.writer, new Date(answer.createdDate), answer.contents, answer.answerId, answer.answerId, questionId);
  $(".qna-comment-slipp-articles").prepend(template);
}

function onError(xhr, status) {
  alert("error");
}

String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

function deleteAnswer(e) {
  e.preventDefault();
  console.log(e.target);
  console.log(e.currentTarget);
  var targetAnswwerEle = $(this).parent().parent().parent().parent().parent();
  var queryString = $(this).parent().serialize();
  console.log(queryString);
  $.ajax({
    type : 'post',
    url : '/api/qna/deleteAnswer',
    data : queryString,
    dataType : 'json'
  }).done(function(data){
	  console.log(data.answerId);
	  $("#" + data.answerId).remove();
  }).fail(function(){
	 alert("deleteAnswer ajax fail!");
  });
}

function deleteQuestion(e) {
	  e.preventDefault();
	  var queryString = $(this).data();
	  console.log(queryString);
	  console.log($(this).data());
	  $.ajax({
	    type : 'post',
	    url : '/api/qna/deleteQuestion',
	    data : queryString,
	    dataType : 'json'
	  }).done(function(data){
		  console.log(data);
		  if(data.result === "answer not null"){
			  alert("답변이 있는 게시물은 삭제할 수 없습니다.");
		  } else if(data.result === "different user"){
			  alert("타인의 게시물은 삭제할 수 없습니다.");
		  } else if(data.result === "not logined"){
			  alert("로그인하신 후에 삭제할 수 있습니다.");
		  } else {
			  location.replace("/");
		  }
	  }).fail(function(){
		 alert("deleteAnswer ajax fail!");
	  });
	}