$(".answerWrite input[type=submit]").click(function(e) {
	e.preventDefault();
	
	var queryString = $("form[name=answer]").serialize();
	
	$.ajax({
	    type : 'post',
	    url : '/qna/addanswer',
	    data : queryString,
	    dataType : 'json',
	    error: function(xhr, status, error){
	        console.log(error);
	    },
	    success : function(data){
	    	var answer = "<div class='answer'><b>" + data.writer + "</b><p>" + data.contents + "</p></div>";
	        $(".answers").prepend(answer)
	    },
	});
});