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
					  <legend>服务响应支撑单查询</legend>
					</fieldset>
	<!-- 行1 -->
					<form class="layui-form" action="GetSERPandect" method="post">
					 <div class="layui-form-item">
					    <div class="layui-inline">
					      <label class="layui-form-label">综合查询</label>
					      <div class="layui-input-inline">
					        <input type="text" name="number" id="comquery"  autocomplete="off" class="layui-input" placeholder="请输入关键字">
					      </div>
					    </div>
					    <div class="layui-inline">
					      <label class="layui-form-label">时间范围</label>
					      <div class="layui-input-inline" style="width:266px">
					        <input type="text" name="date" id="timeRange" lay-verify="date" placeholder="请选择时间范围" autocomplete="off" class="layui-input" readonly="readonly">
					      </div>
					    </div>
					    <div class="layui-inline">
					      <label class="layui-form-label">相关人员</label>
					      <div class="layui-input-inline">
					        <input type="tel" name="url" lay-verify="url" id="person" autocomplete="off" class="layui-input" placeholder="请输入姓名" lay-verify="name">
					      </div>
					    </div>
					    <div class="layui-inline">
					    	<input type="button" class="layui-btn" lay-filter="demo1" data-type="reload" id="queryBtn" value="查询">
					    	<input type="submit" class="layui-btn layui-btn-normal" lay-filter="demo1" value="导出Excel">
					    </div>
					  </div>
	<!-- 行2 --> 
					  <div class="layui-form-item">
					    <label class="layui-form-label">分公司</label>
					    <div class="layui-input-inline">
					      <select name="region" id="region" lay-filter="region">
					        <option value="">请选择分公司</option>
					        <c:forEach var="region" items="${input.regionList}" varStatus="status">
								<option value="${region.regionId}">${region.regionName}</option>
							</c:forEach>
					      </select>
					    </div>
					    <label class="layui-form-label">子区域</label>
					    <div class="layui-input-inline">
					      <select name="subregion" id="subregion" lay-filter="subregion">
					        <option value="">请选择子区域</option>
					      </select>
					    </div>
					    <label class="layui-form-label">专业</label>
					    <div class="layui-input-inline">
					      <select name="major" id="major">
					        <option value="">请选择专业</option>
					        <option value="101">交换</option>
							<option value="102">传输</option>
							<option value="103">数据</option>
							<option value="104">动环</option>
							<option value="105">接入网</option>
							<option value="106">平台</option>
							<option value="107">C网核心网</option>
							<option value="108">C网分组域</option>
							<option value="109">C网无线网</option>
							<option value="110">EPC网核心网</option>
							<option value="111">EPC网无线网</option>
					      </select>
					    </div>
					    <label class="layui-form-label">厂家</label>
					    <div class="layui-input-inline">
					      <select name="manufacturer" id="manufacturer">
					        <option value="">请选择厂家</option>
					        <c:forEach var="mf" items="${input.mfList}" varStatus="status">
					         <option value="${mf.manufacturerId}">${mf.manufacturerName}</option>
					        </c:forEach>
					      </select>
					    </div>
					  </div>
					</form>	
				<table id="mytable" lay-filter="test"></table>
   				
			<div class="layui-footer" align="center">
				<!-- 底部固定区域 -->
				2017 © 中国电信股份有限公司安徽分公司
			</div>
		</div>
		
		<script src="../layui/layui.js"></script>
		<script type="text/html" id="barDemo">
  			<a class="layui-btn layui-btn-mini" lay-event="detail">查看</a>
		</script>
		<script>
		
		var xmlHttp = null;
    	if(window.XMLHttpRequest){
    		xmlHttp = new XMLHttpRequest();
    	}else{
    		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    	}
	    var url=null;
	    
		
		
			layui.use(['element','form','laydate','table','jquery'], function() {
				var element = layui.element
				,form = layui.form
				,laydate = layui.laydate
				,table = layui.table
				,$ = layui.$;
				

				
			    //选择区域触发ajax方法
				form.on('select(region)', function(data){
					var datas="regionId="+data.value;
					$.ajax({
						url:'<c:url value="/common/GetSubRegion"/>',
						type: "POST",
						data:datas,
						success: function(json){
							var sr = eval("("+json+")");
							var subRegion = document.getElementById("subregion");
							subRegion.options.length=1;
			    			for(var i=0;i<sr.length;i++){
			    				var subRegionName = sr[i].subRegionName;
			    				var subRegionId = sr[i].subRegionId;
			    				subRegion.options.add(new Option(subRegionName,subRegionId))
			    			}
			    			form.render('select');
					    }
					});
				  	
				}); 
				
				//监听提交
				form.on('submit(formDemo)', function(data) {
					layer.msg(JSON.stringify(data.field));
					return false;
				});
				  laydate.render({
					    elem: '#timeRange'
					    ,type: 'datetime'
					    ,range: '到'
					    ,format: 'yyyy-MM-dd HH:mm'
					    ,theme:'molv'
					  });
			
			table.render({
				  elem: '#mytable'
				  ,url: 'GetSERPandect'
				  ,cols: [[
				      {fixed: 'right', title: '操作', width:70, align:'center', toolbar: '#barDemo',fixed: true}
				      ,{field:'REGION_NAME', title: '区域', width:80, sort: true, fixed: true}
				      ,{field:'SUBREGION_NAME', title: '子区域', width:100,sort: true,fixed: true}
				      ,{field:'MAJOR_NAME', title: '专业', width:80, sort: true}
				      ,{field:'MANUFACTURER_NAME', title: '厂家', width:95}
				      ,{field:'NE_NAME', title: '网元名称', width:145}
				      ,{field:'START_TIME', title: '开始时间', sort: true, width:165}
				      ,{field:'END_TIME', title: '结束时间', sort: true, width:165}
				      ,{field:'MAJORHANDLER', title: '专业支撑人', align:'center', width:107}
				      ,{field:'SUPPORTMETHOD', title: '支撑方式', align:'center', sort: true, width:100}
				      ,{field:'DEMAND_NAME', title: '问题及需求类别', align:'center', sort: true, width:144}
				      ,{field:'DEMAND_INFO', title: '问题及需求描述', sort: true, width:144}
				      ,{field:'DEMAND_DEGREE', title: '需求等级', align:'center', sort: true, width:100}
				      ,{field:'TP_NAME', title: '电信联系人', align:'center', sort: true, width:107}
				      ,{field:'CURRENTPROCESS', title: '当前环节', sort: true, width:100}
				    ]]
				    ,id: 'testReload'
				    ,page: true
				    ,height: 'full-320'
				  });
			
			table.on('tool(test)', function(obj){ //test是table原始容器的属性 lay-filter="对应的值"
				  var data = obj.data; //获得当前行数据
				  var layEvent = obj.event; //获得 lay-event 对应的值
				  var tr = obj.tr; //获得当前行 tr 的DOM对象
				  if(layEvent === 'detail'){ //查看
					  window.location.href="../user/GetSER?serId="+data.SER_ID;
				  } 
				});
			
			var $ = layui.$, active = {
				    reload: function(){
				    	var comquery = $("#comquery").val();
						var region = $("#region").val();
						var subregion = $("#subregion").val();
						var major = $("#major").val();
						var manufacturer = $("#manufacturer").val();
						var timeRange = $("#timeRange").val();
						var person = $("#person").val();
				      table.reload('testReload', {
				        where: {
				            comquery: comquery
				            ,region: region
				            ,subregion: subregion
				            ,major: major
				            ,manufacturer: manufacturer
				            ,timeRange: timeRange
				            ,person: person
				        }
				      });
				    }
				  };
				
				  $('#queryBtn').on('click', function(){
				    var type = $(this).data('type');
				    active[type] ? active[type].call(this) : '';
				  });
				});
		</script>
	</body>

</html>