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
	    success : onSuccess
	});
}

function onSuccess(json, status){
	var answer = "<div class='answer'><b>" + json.writer + "</b><p>" + json.contents + "</p></div>";
    $(".answers").prepend(answer);
}

function onError(xhr, status) {
	alert("error");
}

$(".answerDelete").click(deleteAnswer);

function deleteAnswer(e) {
	e.preventDefault();
	
	var answerEle = $(this);
		
	$.ajax({
	    type : 'get',
	    url : answerEle.attr("href"),	    
	    dataType : 'json',
	    error: function(xhr, status, error){
            console.log(error);
        },
        success : function(json){
            if (json.status) {
            	answerEle.parent().remove();
            }
        }
	});
}