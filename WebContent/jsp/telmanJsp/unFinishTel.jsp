<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		<link rel="stylesheet" href="../css/bootstrap.min.css">
		<link rel="stylesheet" href="../css/main.css">
		<link rel="stylesheet" href="../css/global.css">
	</head>

	<body onload="getTrend()">
		<div class="layui-layout layui-layout-admin">
			<div class="layui-header">
				<div class="layui-logo">中国电信安徽分公司维保系统</div>
				<!-- 头部区域（可配合layui已有的水平导航） -->

				<ul class="layui-nav layui-layout-right">
					<li class="layui-nav-item">
						<a href="javascript:;">当前工单</a><span class="layui-badge-dot" id="alert"></span>
						<dl class="layui-nav-child">
							<dd style="width: 180px;">
								<a href=""><i class="layui-icon" style="font-size: 21px; color: #009688;">&#xe63c; </i> 未点评工单<span class="layui-badge layui-bg-red">12</span></a>
							</dd style="width: 180px;">
							<dd>
								<a href=""><i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe6b2; </i> 未审核工单<span class="layui-badge layui-bg-red">${input.nonDealed}</span></a>
							</dd>
						</dl>
					</li>
					<li class="layui-nav-item">
						<a href="javascript:;">
							<img src="../img/timg.jpg" class="layui-nav-img"> 管理员，${input.name}
						</a>
						<dl class="layui-nav-child">
							<dd>
								<a href=""><i class="layui-icon" style="font-size: 18px; color: #1E9FFF;">&#xe612;</i> 个人信息</a>
							</dd>
							<dd>
								<a href=""><i class="layui-icon" style="font-size: 18px; color: #1E9FFF;">&#xe614;</i> 设置</a>
							</dd>
							<dd>
								<a href='<c:url value="/Logout"/>'><i class="layui-icon" style="font-size: 18px; color: #1E9FFF;">&#xe633;</i> 注销</a>
							</dd>
						</dl>
					</li>
				</ul>
			</div>

			<div class="layui-side layui-bg-black">
				<div class="layui-side-scroll">
					<!-- 左侧导航区域 -->
					<ul class="layui-nav layui-nav-tree" lay-filter="test">
						<li class="layui-nav-item">
							<a href=""><i class="layui-icon" style="font-size: 22px; color: #009688;">&#xe629; </i> 综合分析</a>
						</li>
						<li class="layui-nav-item">
							<a href="NonFinish"><i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe609; </i> 未完成追踪</a>
						</li>
						<li class="layui-nav-item">
							<a href=""><i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe6af; </i> 维保单点评</a>
						</li>
						<li class="layui-nav-item">
							<a href=""><i class="layui-icon" style="font-size: 21px; color: #009688;">&#xe63c; </i> 维保单审核</a>
						</li>
						<li class="layui-nav-item ">
							<a class="" href="javascript:;"><i class="layui-icon" style="font-size: 23px; color: #009688;">&#xe62d; </i> 报表统计</a>
							<dl class="layui-nav-child">
								<dd>
									<a href="MonthRegion"><i class="layui-icon" style="font-size: 17px; color: #009688;">&#xe62a; </i> 分公司项目月报表</a>
								</dd>
								<dd>
									<a href="MonthManu"><i class="layui-icon" style="font-size: 17px; color: #009688;">&#xe62a; </i> 厂家维保月报表</a>
								</dd>
								<dd>
									<a href="MonthMajor"><i class="layui-icon" style="font-size: 17px; color: #009688;">&#xe62a; </i> 专业项目月报表</a>
							</dl>
						</li>
						<li class="layui-nav-item ">
							<a class="" href="javascript:;"><i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe615; </i> 维保单查询</a>
							<dl class="layui-nav-child">
								<dd>
									<a href="SERPandect"><i class="layui-icon" style="font-size: 19px; color: #009688;">&#xe60e; </i> 服务响应支撑</a>
								</dd>
								<dd>
									<a href="HMPandect"><i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe631; </i> 备件返修</a>
								</dd>
								<dd>
									<a href="javascript:;"><i class="layui-icon" style="font-size: 19px; color: #009688;">&#x1002; </i> 紧急备件替换</a>
								</dd>
								<dd>
									<a href="javascript:;"><i class="layui-icon" style="font-size: 16px; color: #009688;">&#xe613; </i> 驻点服务</a>
								</dd>
								<dd>
									<a href="javascript:;"><i class="layui-icon" style="font-size: 19px; color: #009688;">&#xe660; </i> 巡检</a>
								</dd>
								<dd>
									<a href="javascript:;"><i class="layui-icon" style="font-size: 18px; color: #009688;">&#xe705; </i> 培训</a>
								</dd>
							</dl>
						</li>

						<li class="layui-nav-item">
							<a href="javascript:;"><i class="layui-icon" style="font-size: 22px; color: #009688;">&#xe620; </i> 系统管理</a>
							<dl class="layui-nav-child">
								<dd>
									<a href="javascript:;"><i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe612; </i> 用户管理</a>
								</dd>
								<dd>
									<a href="javascript:;"><i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe637; </i> 日志管理</a>
								</dd>
								<dd>
									<a href="javascript:;"><i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe715; </i> 区域管理</a>
								</dd>
								<dd>
									<a href="javascript:;"><i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe628; </i> 设备型号管理</a>
								</dd>
							</dl>
						</li>

					</ul>
				</div>
			</div>

			<div class="layui-body">
				<!-- 内容主体区域 -->
				<div style="padding: 15px;">
					<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
					  <legend>未完成返修备件</legend>
					</fieldset>
					
					<div class="row state-overview">
						<div class="col-lg-4 col-sm-4">
							<section class="panel">
								<div class="symbol userblue"><i class="layui-icon">&#xe630;</i>
								</div>
								<div class="value">
									<a href="#">
										<h1 id="count1">${input.nonCompletedFault}</h1>
									</a>
									<p>未完成故障</p>
								</div>
							</section>
						</div>
						<div class="col-lg-4 col-sm-4">
							<section class="panel">
								<div class="symbol commred"> <i class="layui-icon">&#xe60a;</i>
								</div>
								<div class="value">
									<a href="#">
										<h1 id="count2">${input.nonCompletedHm}</h1>
									</a>
									<p>未完成返修备件</p>
								</div>
							</section>
						</div>
						<div class="col-lg-4 col-sm-4">
							<section class="panel">
								<div class="symbol articlegreen">
									<i class="layui-icon">&#xe60c;</i>
								</div>
								<div class="value">
									<a href="#">
										<h1 id="count3">${input.nonCompletedDanger}</h1>
									</a>
									<p>未完成隐患整治</p>
								</div>
							</section>
						</div>
					</div>
					<div class="row">
						<table id="mytable"></table>
					</div>
					

				</div>
			</div>

			<div class="layui-footer" align="center">
				<!-- 底部固定区域 -->
				2017 © 中国电信股份有限公司安徽分公司
			</div>
		</div>
		<script src="../layui/layui.js"></script>
		<script src="../js/echart/echarts.js"></script>
		<script>
			var xmlHttp = null;
    	if(window.XMLHttpRequest){
    		xmlHttp = new XMLHttpRequest();
    	}else{
    		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    	}
	    var url=null;
	    
		
		
			layui.use(['element','table','jquery'], function() {
				var element = layui.element
				,table = layui.table
				,$ = layui.$;
				

				
				
			table.render({
				  elem: '#mytable'
				  ,url: 'GetNonFinishHM'
				  ,cols: [[
				      {field:'REGION_NAME', title: '区域', width:80, sort: true, fixed: true}
				      ,{field:'SUBREGION_NAME', title: '子区域', width:100,sort: true,fixed: true}
				      ,{field:'MAJOR_NAME', title: '专业', width:80, sort: true}
				      ,{field:'MANUFACTURER_NAME', title: '厂家', width:95}
				      ,{field:'NE_NAME', title: '网元名称', width:145}
				      ,{field:'NEMODEL_NAME', title: '网元型号', sort: true, width:165}
				      ,{field:'LAUNCH_TIME', title: '发起时间', sort: true, width:165}
				      ,{field:'COMPLETION_TIME', title: '完成时间', width:107}
				      ,{field:'MP_NAME', title: '主要处理人', sort: true, width:100}
				      ,{field:'TP_NAME', title: '审核人', sort: true, width:144}
				      ,{field:'TOTAL_NUM', title: '总数量', sort: true, width:144}
				      ,{field:'REPAIR_NUM', title: '修复数量', sort: true, width:100}
				      ,{field:'SATISFACTION', title: '满意度', sort: true, width:107}
				    ]]
				    ,id: 'testReload'
				    ,page: true
				    ,height: 'full-320'
				    ,even: true
				  });
				});
		</script>
	</body>
</html>