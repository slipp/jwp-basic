$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e) {
  e.preventDefault();

  var queryString = $("form[name=answer]").serialize();

  $.ajax({
    type : 'post',
    url : '/api/qna/addanswer',
    data : queryString,
    dataType : 'json',
    error: onError,
    success : onSuccess,
  });
}

function onSuccess(json, status){
  var answer =
      "<article class='article'>" +
        "<div class='answer'><b>" + json.writer + "</b><p>" + json.contents + "</p></div>" +
      "</article>";
  $(".qna-comment-slipp-articles").prepend(answer);
}

function onError(xhr, status) {
  alert("error");
}
