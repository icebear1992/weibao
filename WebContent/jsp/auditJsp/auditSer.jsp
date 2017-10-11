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
		<div style="padding: 20px;" id="main">
			<form class="layui-form" role="form" action='AuditSER.do' method="post" id="myform">
					<input value="${serId }" id="serId" name="serId" type="hidden"/>
						
				   
				   <div class="layui-form-item">
				    <label class="layui-form-label">通过与否：</label>
				    <div class="layui-input-block">
				      <input type="checkbox" checked="" value="1" name="pass" lay-skin="switch" lay-filter="switchTest" lay-text="通过|退回">
				    </div>
				  </div>
				   
					<div class="layui-form-item layui-form-text">
				   		<label class="layui-form-label">附加评价：</label>
					    <div class="layui-input-block">
					      <textarea placeholder="请输入评价内容" class="layui-textarea" name="reviewContent"></textarea>
					    </div>
					  </div>
					  <div style="text-align:center">
						  <button class="layui-btn" id="submitBtn">提交</button>
						  <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
					  </div>
				</form>
					</div>
		
		<script src="../layui/layui.js"></script>
		<script>
		var id =${serId };
			layui.use(['element','form','layer','jquery'], function() {
				var element = layui.element
				,form = layui.form
				,layer = layui.layer
				,$ = layui.$;
				
				$('#submitBtn').on('click', function(){
					window.parent.location.href="ViewSer?serId="+id+"&msg=204";
				});
				$('#cancelBtn').on('click', function(){
					window.parent.location.reload();
				});
			});
		</script>
	</body>

</html>