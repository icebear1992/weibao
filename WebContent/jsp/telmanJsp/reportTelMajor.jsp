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
	</head>

	<body>
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
							<a href="Main"><i class="layui-icon" style="font-size: 22px; color: #009688;">&#xe629; </i> 综合分析</a>
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
					  <legend>专业项目月报表</legend>
				</fieldset>
					<blockquote class="layui-elem-quote news_search">
		<div class="layui-inline">
		    <div class="layui-input-inline">
		    <form action="GetMonthRegion" method="post">
		    	<input name="time" type="text" value="" id="time" placeholder="请选择时间段" class="layui-input search_input" readonly="readonly" style="color:gray">
		    </div>
		    <div class="layui-input-inline">
		    	<select name="major" id="major" class="layui-input search_input" style="width:170px;color:gray;font-family:Microsoft YaHei" >
					<option value="0">请选择专业</option>
					<option value="交换">交换</option>
					<option value="传输">传输</option>
					<option value="数据">数据</option>
					<option value="动环">动环</option>
					<option value="接入网">接入网</option>
					<option value="平台">平台</option>
					<option value="C网核心网">C网核心网</option>
					<option value="C网分组域">C网分组域</option>
					<option value="C网无线网">C网无线网</option>
					<option value="EPC网核心网">EPC网核心网</option>
					<option value="EPC网无线网">EPC网无线网</option>
				</select>
		    </div>
		    <input type="button" class="layui-btn" id="timebtn" data-type="reload" value="查询"/>
		</div>
		<div class="layui-inline">
			<button type="submit" class="layui-btn layui-btn-normal ">导出报表</button>
		</div>
			</form>
	</blockquote>
	
				<table id="mytable"></table>
   				
			<div class="layui-footer" align="center">
				<!-- 底部固定区域 -->
				2017 © 中国电信股份有限公司安徽分公司
			</div>
		</div>
		
		<script src="../layui/layui.js"></script>
		<script>
		
			layui.use(['element','form','laydate','table'], function() {
				var element = layui.element
				,form = layui.form
				,laydate = layui.laydate
				,table = layui.table;

				//监听提交
				form.on('submit(formDemo)', function(data) {
					layer.msg(JSON.stringify(data.field));
					return false;
				});
			laydate.render({
			    elem: '#time'
			    ,type: 'month'
			    ,range: true
			    ,theme: 'molv'
			  });
			
			table.render({
				  elem: '#mytable'
				  ,url: 'GetMonthMajor'
				  ,cols: [[
				      {field:'TIME', title: '月份', width:80, sort: true, fixed: true}
				      ,{field:'MAJOR_NAME', title: '专业', width:100,sort: true,fixed: true}
				      ,{field:'SERPERSONNEL', title: '驻点服务人数', width:120, sort: true}
				      ,{field:'TOTALEQUIPMENT', title: '巡检覆盖设备', width:115}
				      ,{field:'TOTALENGINERROOM', title: '巡检覆盖业务节点', width:145}
				      ,{field:'TRAININGDURATION', title: '培训时长*人数', sort: true, width:130}
				      ,{field:'CONSULTATION', title: '方案咨询数', sort: true, width:110}
				      ,{field:'COMPLETION', title: '完结处理故障', width:115}
				      ,{field:'INCOMPLETION', title: '未完结故障', sort: true, width:110}
				      ,{field:'SSUM', title: '硬件返修批次(增)', sort: true, width:144}
				      ,{field:'INSSUM', title: '硬件返修批次(存)', sort: true, width:144}
				      ,{field:'AVGTIME', title: '硬件返修平均历时', sort: true, width:149}
				      ,{field:'SPRCOUNT', title: '紧急备件服务', sort: true, width:120}
				    ]]
				    ,id: 'testReload'
				    ,page: true
				    ,height: 'full-285'
				    ,even: true
				  });
			
			var $ = layui.$, active = {
				    reload: function(){
				      var time = $('#time');
				      var major = $('#major')
				      table.reload('testReload', {
				        where: {
				            time: time.val()
				            ,major: major.val()
				        }
				      });
				    }
				  };
				  
				  $('#timebtn').on('click', function(){
				    var type = $(this).data('type');
				    active[type] ? active[type].call(this) : '';
				  });
				});
		</script>
	</body>

</html>