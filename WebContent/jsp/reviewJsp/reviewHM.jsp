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
			<form class="layui-form" role="form" action='ReviewHM.do' method="post" id="myform">
					<input value="${hmId }" id="hmId" name="hmId" type="hidden"/>
						<div class="container">
							<input name="timeliness" hidden="" id="timeliness"/>
							<input name="satisfaction" hidden="" id="satisfaction"/>
								<span>返修响应及时度：</span>
							<span onclick="test1('1')" id="1"  style="color: #DFF0D8;"><i class="layui-icon" style="font-size: 28px;">&#xe600; </i></span>
							<span id="2"  onclick="test1('2')"  style="color: #DFF0D8;"><i class="layui-icon" style="font-size: 28px;">&#xe600; </i></span>
							<span id="3"  onclick="test1('3')"   style="color: #DFF0D8;"><i class="layui-icon" style="font-size: 28px;">&#xe600; </i></span>
							<span id="4"  onclick="test1('4')"   style="color: #DFF0D8;"><i class="layui-icon" style="font-size: 28px;">&#xe600; </i></span>
							<span id="5"  onclick="test1('5')"   style="color: #DFF0D8;"><i class="layui-icon" style="font-size: 28px;">&#xe600; </i></span>
								<br>
								<span>返修结果满意度：</span>
							<span id="6"  onclick="test2('6')"   style="color: #DFF0D8;"><i class="layui-icon" style="font-size: 28px;">&#xe600; </i></span>
							<span id="7"  onclick="test2('7')"   style="color: #DFF0D8;"><i class="layui-icon" style="font-size: 28px;">&#xe600; </i></span>
							<span id="8"  onclick="test2('8')"   style="color: #DFF0D8;"><i class="layui-icon" style="font-size: 28px;">&#xe600; </i></span>
							<span id="9"  onclick="test2('9')"   style="color: #DFF0D8;"><i class="layui-icon" style="font-size: 28px;">&#xe600; </i></span>
							<span id="10"  onclick="test2('10')"   style="color: #DFF0D8;"><i class="layui-icon" style="font-size: 28px;">&#xe600; </i></span>
				   </div>
				   
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
		var id = ${hmId};
		function test1(myvalue){
			document.getElementById("timeliness").value=myvalue;
			for(var i=1;i<=5;i++){//将所有都变白星星
			document.getElementById(""+i).setAttribute("style","color:#DFF0D8");
			}
			for(var i=1;i<=myvalue;i++){//通过传入的id（也是一个数字）确定id以下的div都是橙色星星。
			document.getElementById(""+i).setAttribute("style","color:orange");
			} 
			}
		function test2(myvalue){
			document.getElementById("satisfaction").value=myvalue-5;
			for(var i=6;i<=10;i++){//将所有都变白星星
			document.getElementById(""+i).setAttribute("style","color:#DFF0D8");
			}
			for(var i=6;i<=myvalue;i++){//通过传入的id（也是一个数字）确定id以下的div都是橙色星星。
			document.getElementById(""+i).setAttribute("style","color:orange");
			} 
			}
			layui.use(['element','form','layer','table','jquery'], function() {
				var element = layui.element
				,form = layui.form
				,layer = layui.layer
				,table = layui.table
				,$ = layui.$;
				
				$('#submitBtn').on('click', function(){
					window.parent.location.href="ViewHM?HMId="+id+"&msg=203";
				});
				$('#cancelBtn').on('click', function(){
					window.parent.location.reload();
				});
			});
		</script>
	</body>

</html>