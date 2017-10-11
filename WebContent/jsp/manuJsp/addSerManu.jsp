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
				<div style="padding: 20px;">
				
					<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
					  <legend>发起服务响应工单
					  	<c:if test='${input.status=="0"}'>
						    （未完成）
						</c:if>
						<c:if test='${input.status=="1"}'>
						  （已完成）
						</c:if>
					  </legend>
					</fieldset>
					<form class="layui-form layui-form-pane" action="AddSER" method="post">
						
						<input name="title" value="${input.title }" style="display:none">
						
						<input name="status" value="${input.status }" style="display:none">
					 	
					 	<div class="layui-row">
						  <div class="layui-col-md5">
						    <c:if test='${input.status=="0"}'>
						    	<div class="layui-inline">
							      <label class="layui-form-label">开始日期</label>
							      <div class="layui-input-inline">
							        <input type="text" class="layui-input" name="date" lay-verify="required" id="limit1" placeholder="只能选择过去一周内">
							      </div>
							    </div>
						    </c:if>
						    <c:if test='${input.status=="1"}'>
						    	<div class="layui-inline">
							      <label class="layui-form-label">时间范围</label>
							      <div class="layui-input-inline" style="width:266px">
							        <input type="text" name="date" id="timeRange" lay-verify="required" placeholder="只能选择过去一周内" autocomplete="off" class="layui-input" readonly="readonly">
							      </div>
							    </div>
						    </c:if>
						    <br><br>
						    
						    <div class="layui-inline">
							      <label class="layui-form-label">分公司</label>
							      <div class="layui-input-inline">
									<select name="region" id="region" lay-filter="region" lay-verify="required">
								        <option value="">请选择分公司</option>
								        <c:forEach var="region" items="${input.regionList}" varStatus="status">
											<option value="${region.regionId}">${region.regionName}</option>
										</c:forEach>
							        </select>
							      </div>
							</div>
						    <br><br>
						    
						    <div class="layui-inline">
							    <label class="layui-form-label">子区域</label>
							    <div class="layui-input-inline">
							      <select name="subRegion" id="subRegion" lay-filter="subRegion" lay-verify="required">
							        <option value="">需先选择分公司</option>
							      </select>
							    </div>
							</div>
							<br><br>
							
						    <div class="layui-inline">
							    <label class="layui-form-label">专业</label>
							    <div class="layui-input-inline">
							      <select name="major" id="major" lay-filter="major" lay-verify="required">
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
							</div>
							<br><br>
							
							<div class="layui-inline">
							    <label class="layui-form-label">接口人</label>
							    <div class="layui-input-inline">
							      <select name="majorContact" id="majorContact" lay-filter="majorContact" lay-verify="required">
							        <option value="">需先选子区域和专业</option>
							      </select>
							    </div>
							</div>
							<br><br>
							
							<div class="layui-inline">
							    <label class="layui-form-label">网络</label>
							    <div class="layui-input-inline">
							      <select name="network" id="network" lay-filter="network" lay-verify="required">
							        <option value="">请先选择专业</option>
							      </select>
							    </div>
							</div>
							<br><br>
							
							<div class="layui-inline">
							    <label class="layui-form-label">网元</label>
							    <div class="layui-input-inline">
							      <select name="ne" id="ne" lay-filter="ne" lay-verify="required">
							        <option value="">请先选择网络</option>
							      </select>
							    </div>
							</div>
							<br><br>
							
							<div class="layui-inline">
							    <label class="layui-form-label">网元型号</label>
							    <div class="layui-input-inline">
							      <select name="neModels" id="NEModels" lay-filter="NEModels" lay-verify="required">
							        <option value="">请先选择网元</option>
							      </select>
							    </div>
							</div>
							<br><br>
							
							<div class="layui-inline">
							    <label class="layui-form-label">联系方式</label>
							    <div class="layui-input-inline">
							      <select name="contactinfo" id="contactinfo" lay-filter="contactinfo" lay-verify="required">
							        <option value="">请选择联系方式</option>
							        <option value="电话">电话</option>
									<option value="邮箱">邮箱</option>
									<option value="微信/QQ等及时通讯">微信/QQ等及时通讯</option>
									<option value="多形式">多形式</option>
							      </select>
							    </div>
							</div>
							<br><br> 
							
							<div class="layui-inline">
							    <label class="layui-form-label">支撑方式</label>
							    <div class="layui-input-inline">
							      <select name="supportMethod" id="supportMethod" lay-filter="supportMethod" lay-verify="required">
							        <option value="">请选择支撑方式</option>
							        <option value="远程">远程</option>
									<option value="现场">现场</option>
									<option value="远程+现场">远程+现场</option>
							      </select>
							    </div>
							</div>
							<br><br> 
						  		<div class="layui-form-item">
									<label class="layui-form-label">主要处理人</label>
									<div class="layui-input-inline">
										<input type="text" name="majorHandler" lay-verify="majorHandler" placeholder="如：熊美琛" autocomplete="off" class="layui-input">
									</div>
								</div>	
							</div>	
						  <div class="layui-col-md5">
								
								<div class="layui-form-item">
									<label class="layui-form-label">远程时长(h)</label>
									<div class="layui-input-inline">
										<input type="text" name="telnetservDuration" lay-verify="numberdot" placeholder="如：1" autocomplete="off" class="layui-input">
									</div>
								</div>
								
								<div class="layui-form-item">
									<label class="layui-form-label">现场时长(h)</label>
									<div class="layui-input-inline">
										<input type="text" name="fieldservDuration" lay-verify="numberdot" placeholder="如：5" autocomplete="off" class="layui-input">
									</div>
								</div>
								
								<div class="layui-form-item">
									<label class="layui-form-label">需求类别</label>
									<div class="layui-input-inline">
										<select name="demand" id="demand" lay-filter="demand" lay-verify="required">
								       		<option value="">请选择问题及需求类别</option>
								       		<c:forEach var="de" items="${input.demandList}" varStatus="status">
												<option value="${de.demandId}">${de.demandName}</option>
											</c:forEach>
								      	</select>
									</div>
								</div>
								
								<div class="layui-form-item layui-form-text">
									<label class="layui-form-label">问题及需求描述</label>
										<div class="layui-input-block">
											<textarea placeholder="请输入内容" class="layui-textarea" name="demandinfo" lay-verify="required"></textarea>
										</div>
								</div>
								
								<div class="layui-form-item layui-form-text">
									<label class="layui-form-label">解决方案</label>
										<div class="layui-input-block">
											<textarea rows="1" placeholder="请输入内容" class="layui-textarea" name="solution" lay-verify="required"></textarea>
										</div>
								</div>
								
								<div class="layui-form-item layui-form-text">
									<label class="layui-form-label">备注信息</label>
										<div class="layui-input-block">
											<textarea placeholder="请输入内容" class="layui-textarea" name="memoinfo"></textarea>
										</div>
								</div>
								
								<div class="row" >
									<button class="layui-btn" lay-submit="" lay-filter="submit">提交工单</button>
								</div>
							  </div>
							  <div class="layui-col-md2"></div>
						  </div>
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
							var subRegion = document.getElementById("subRegion");
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
			    
			    //选择转专业触发接口人和网络方法
				form.on('select(major)', function(data){
					var subRegionId = document.getElementById("subRegion").value;
					var datas1="subRegionId="+subRegionId+"&majorId="+data.value;
					var datas2="majorId="+data.value;
					$.ajax({
						url:'<c:url value="/common/GetContact"/>',
						type: "POST",
						data:datas1,
						success: function(json){
							var sr = eval("("+json+")");
							var majorContact = document.getElementById("majorContact");
							majorContact.options.length=1;
			    			for(var i=0;i<sr.length;i++){
			    				var tpId = sr[i].tp.tpId;
			    				var tpName = sr[i].tp.tpName;
			    				majorContact.options.add(new Option(tpName,tpId))
			    			}
			    			form.render('select');
					    }
					});
					$.ajax({
						url:'<c:url value="/common/GetNetwork"/>',
						type: "POST",
						data:datas2,
						success: function(json){
							var nw = eval("("+json+")");
							var network = document.getElementById("network");
							network.options.length=1;
							for(var i=0;i<nw.length;i++){
		        				var networkId = nw[i].networkId;
		        				var networkName = nw[i].networkName;
		        				network.options.add(new Option(networkName,networkId))
		        			}
			    			form.render('select');
					    }
					});
				}); 
				
				 //选择网络触发网元ajax方法
				form.on('select(network)', function(data){
					var datas="networkId="+data.value;
					$.ajax({
						url:'<c:url value="/common/GetNE"/>',
						type: "POST",
						data:datas,
						success: function(json){
							var n = eval("("+json+")");
							var ne = document.getElementById("ne");
							ne.options.length=1;
							for(var i=0;i<n.length;i++){
		        				var neId = n[i].neId;
		        				var neName = n[i].neName;
		        				ne.options.add(new Option(neName,neId))
		        			}
			    			form.render('select');
					    }
					});
				}); 
				 //选择网元触发网元型号ajax方法
				form.on('select(ne)', function(data){
					var datas="neId="+data.value;
					$.ajax({
						url:'<c:url value="/common/GetNEModel"/>',
						type: "POST",
						data:datas,
						success: function(json){
							var nm = eval("("+json+")");
							var NEModels = document.getElementById("NEModels");
							NEModels.options.length=1;
							for(var i=0;i<nm.length;i++){
		        				var NEModel_id = nm[i].neModelId;
		        				var NEModel_name = nm[i].neModelName;
		        				NEModels.options.add(new Option(NEModel_name,NEModel_id))
		        		}
			    			form.render('select');
					    }
					});
				}); 
			    
				
				form.verify({
					majorHandler: function(value, item){ //value：表单的值、item：表单的DOM对象
					    if(!new RegExp("^[\u4e00-\u9fa5]").test(value)){
					      return '人名只能使用汉字';
					    	}
					 	 },
					numberdot:function(value, item){ //value：表单的值、item：表单的DOM对象
					    if(!new RegExp("^[0-9]*$").test(value)){
						      return '只能使用数字';
						    }
						}
					  }); 

				//未完成的时间
				  laydate.render({
				    elem: '#limit1'
				    ,type:'datetime'
				    ,theme:'molv'
				    ,min: -7
					,max: 0
				  });
				//已完成的时间
				  laydate.render({
					    elem: '#timeRange'
					    ,type: 'datetime'
					    ,range: '到'
					    ,format: 'yyyy-MM-dd HH:mm'
					    ,theme:'molv'
					    ,min: -7
						,max: 0
					  });
				//监听提交
				});
		</script>
	</body>

</html>