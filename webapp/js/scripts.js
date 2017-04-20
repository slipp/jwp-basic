// $(".qna-comment").on("click", ".answerWrite input[type=submit]", addAnswer);
$(".answerWrite input[type=submit]").click(addAnswer);
$(".qna-comment-slipp-articles").on("click", ".link-delete-article", deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();
    let deleteBtn = $(this).siblings("input");
    let answerId = deleteBtn.prop("value");
    let question = document.getElementById("question-id");
    $.ajax({
        type: 'post',
        url: '/api/qna/deleteAnswer',
        data : {
            answerId: answerId,
            questionId: question.value
        },
        dataType : 'json',
        error: onError,
        success: function(data, status) {
            deleteBtn.closest("article").remove();
            let countOfCommentText = $(".qna-comment-count strong").text();
            let countOfComment = Number(countOfCommentText);
            countOfComment -= 1;
            $(".qna-comment-count strong").text(countOfComment);
        }
    });
}

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
  var answerTemplate = $("#answerTemplate").html();
  var template = answerTemplate.format(answer.writer, new Date(answer.createdDate), answer.contents, answer.answerId, answer.answerId);
  $(".qna-comment-slipp-articles").prepend(template);

  let countOfCommentText = $(".qna-comment-count strong").text();
  let countOfComment = Number(countOfCommentText);
  countOfComment += 1;
  $(".qna-comment-count strong").text(countOfComment);
}

function onError(xhr, status) {
  console.log("error: ", xhr, status);
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