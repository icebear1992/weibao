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
		<div class="layui-layout layui-layout-admin">
			<div class="layui-header">
				<div class="layui-logo">中国电信安徽分公司维保系统</div>
				<!-- 头部区域（可配合layui已有的水平导航） -->

				<ul class="layui-nav layui-layout-right">
					<li class="layui-nav-item">
						<a href="javascript:;">当前工单</a><span class="layui-badge-dot" id="alert"></span>
						<dl class="layui-nav-child">
							<dd style="width: 180px;">
								<a href=""><i class="layui-icon" style="font-size: 21px; color: #009688;">&#xe63c; </i> 被退回工单<span class="layui-badge layui-bg-red">${input.nonFin}</span></a>
							</dd style="width: 180px;">
							<dd>
								<a href=""><i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe6b2; </i> 未完成工单<span class="layui-badge layui-bg-red">${input.nonDealed}</span></a>
							</dd>
						</dl>
					</li>
					<li class="layui-nav-item">
						<a href="javascript:;">
							<img src="../img/timg.jpg" class="layui-nav-img"> ${input.manufacturer.manufacturerName }，${input.name}
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
							<a href="ProviderMain"><i class="layui-icon" style="font-size: 22px; color: #009688;">&#xe629; </i>主页</a>
						</li>
						<li class="layui-nav-item">
							<a href="NonFinish"><i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe609; </i> 未完成追踪</a>
						</li>
						<li class="layui-nav-item">
							<a href=""><i class="layui-icon" style="font-size: 21px; color: #009688;">&#xe63c; </i> 已归档维保单</a>
						</li>
						
						<li class="layui-nav-item ">
							<a class="" href="javascript:;"><i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe615; </i> 发起新的维保单</a>
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
						<li class="layui-nav-item ">
							<a class="" href="javascript:;"><i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe615; </i> 个人设置</a>
						</li>

					</ul>
				</div>
			</div>

			<div class="layui-body">
				<!-- 内容主体区域 -->
				<div style="padding: 15px;">
				
					<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
					  <legend>发起新维保工单</legend>
					</fieldset>
					<br><br>
					<form class="layui-form layui-form-pane" action="AddWork" method="post">
					  <div class="layui-form-item">
					    <label class="layui-form-label">工单标题</label>
					    <div class="layui-input-block">
					      <input type="text" name="title" autocomplete="off" placeholder="请输入标题" class="layui-input">
					    </div>
					  </div>
					  
					  
					  <div class="layui-form-item">
					    <label class="layui-form-label">维保类型</label>
					    <div class="layui-input-block">
					      <select name="type" lay-filter="type">
					        <option value=""></option>
					        <option value="ser">服务响应</option>
					        <option value="hm">硬件返修</option>
					        <option value="ss">紧急备件替换</option>
					        <option value="2">驻点服务</option>
					        <option value="3">巡检</option>
					        <option value="4">培训</option>
					      </select>
					    </div>
					  </div>
					  
					
					  <div class="layui-form-item" pane="">
					    <label class="layui-form-label">完成情况</label>
					    <div class="layui-input-block">
					      <input type="radio" name="status" value="1" title="已完成" checked="">
					      <input type="radio" name="status" value="0" title="未完成">
					    </div>
					  </div>
					  <br>
					  <div class="layui-form-item">
					    <button class="layui-btn" lay-submit="" lay-filter="demo2">下一步</button>
					  </div>
					</form>
			
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
					  window.location.href="getSer?id="+data.SER_ID;
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