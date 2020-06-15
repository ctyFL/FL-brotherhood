<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<input type="button" value="rest" onclick="restRequest()">
</body>
<script src="../res/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	function restRequest() {
		$.ajax({
			type: "GET",
			url: "<%=request.getContextPath()%>/restFL/jerseyrestdemo/hi",
			//async: false,//默认为true 异步
			data: {str:"1"},
			dataType: "json",
			success: function(data) {
				
			}
		});
	}
</script>
</html>