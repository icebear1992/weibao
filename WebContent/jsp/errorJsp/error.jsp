<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>中国电信安徽分公司维保管理系统</title>
		<link rel="stylesheet" href="../layui/css/layui.css">
	</head>

	<body>
		${input.errorMsg }
		
		<script src="../layui/layui.js"></script>
		<script>
			layui.use(['element','form','layer','jquery'], function() {
				var element = layui.element
				,form = layui.form
				,layer = layui.layer
				,$ = layui.$;
				
				$('#submitBtn').on('click', function(){
					window.parent.location.reload();
				});
				$('#cancelBtn').on('click', function(){
					window.parent.location.reload();
				});
			});
		</script>
	</body>

</html>