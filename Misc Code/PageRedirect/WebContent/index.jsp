<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Page-redirection</title>
</head>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script>
$(document).ready(function(){
							
								$("button").click(function(){
							    $.ajax({url: "internal-MyLoadbalancer-1099373821.eu-west-1.elb.amazonaws.com/DemoPageRedirect/demo.txt", 
							    		success: function(result){
							        $("#div1").html(result);
							    }});
							});
});
</script>
<body>
<h2 align="center"> Welcome</h2>
<button> Click me </button>
<div id = "div1" > </div>
</body>
</html>