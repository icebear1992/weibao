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
							<a class="" href="javascript:layer.msg('功能施工中');"><i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe615; </i> 个人设置</a>
						</li>

					</ul>
				</div>
			</div>

			<div class="layui-body">
				<!-- 内容主体区域 -->
				<div style="padding: 15px;">
				
					<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
					  <legend>服务响应单详情</legend>
					</fieldset>
					<button class="layui-btn" id="changeBtn">修改工单</button>
					<button class="layui-btn layui-btn-normal" id="completeBtn">竣工工单</button>
           			<button class="layui-btn layui-btn-danger" id="deleteBtn">删除工单</button>
           			<br><br>
					<div class="layui-row layui-col-space20">
            			<div class="layui-col-md6">
				            <blockquote class="layui-elem-quote">
				            	<p>标题：${input.ser.title}</p><hr>
				                <p>发起人：${input.ser.mp.manufacturer.manufacturerName}${input.ser.mp.mpName }</p><hr>
				                <p>工单号：${input.ser.serId }</p><hr>
				                <p>区域：${input.ser.serId }</p><hr>
				                <p>子区域：${input.ser.subRegion.subRegionName }</p><hr>
				                <p>专业：${input.major}</p><hr>
				                <p>电信接口人：${input.ser.auditor.tpName }</p><hr>
				                <p>服务历时：<fmt:formatDate value="${input.ser.startTime }" pattern="MM月dd日 HH:mm"/>--<fmt:formatDate value="${input.ser.endTime }" pattern="MM月dd日 HH:mm"/></p><hr>
				                <p>服务类别：${input.ser.category.demandName }</p><hr>
				                <p>需求描述：${input.ser.demandInfo }</p><hr>
				                <p>主要处理人：${input.ser.majorHandler }</p><hr>
				                <p>服务时长(现场/远程)：${input.ser.fieldServDuration }/${input.ser.telnetServDuration}</p><hr>
				                <p>解决方案：${input.ser.solution }</p>
				            </blockquote> 
            			</div>
						
            		
	            		<div class="layui-col-md6" >
				                <ul class="layui-timeline">
				                <c:if test='${input.ser.currentProcess=="2"}'>
								      	<li class="layui-timeline-item">
									      <i class="layui-icon layui-timeline-axis">&#xe624;</i>
										    <div class="layui-timeline-content layui-text">
										      <h3 class="layui-timeline-title">归档</h3>
										    </div>
								        </li>
	                        	</c:if>
	                       		  <c:if test='${input.ser.currentProcess=="1"}'>
							      	<li class="layui-timeline-item">
								      <i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop layui-timeline-axis">&#x1002;</i>
									    <div class="layui-timeline-content layui-text">
									      <h3 class="layui-timeline-title">待审核</h3>
									      <ul>
									        <li>审核人：${input.ser.auditor.tpName}</li>
									      </ul>
									    </div>
							        </li>
	                       		  </c:if>
                        		  <c:if test='${input.ser.currentProcess=="0"}'>
							      	<li class="layui-timeline-item">
								      <i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop layui-timeline-axis">&#x1002;</i>
									    <div class="layui-timeline-content layui-text">
									      <h3 class="layui-timeline-title">待点评</h3>
									      <ul>
									        <li>点评人：${input.ser.auditor.tpName}</li>
									     </ul>
									    </div>
							        </li>
                        		  </c:if>
                        		  <c:if test='${input.ser.currentProcess=="-1"}'>
							      	<li class="layui-timeline-item">
								      <i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop layui-timeline-axis">&#x1002;</i>
									    <div class="layui-timeline-content layui-text">
									      <h3 class="layui-timeline-title">待修改</h3>
									      <ul>
									        <li>操作人：${input.ser.mp.manufacturer.manufacturerName}${input.ser.mp.mpName }</li>
									      </ul>
									    </div>
							        </li>
                        		  </c:if>
				                  <c:if test='${input.ser.currentProcess=="-2"}'>
							      	<li class="layui-timeline-item">
								      <i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop layui-timeline-axis">&#x1002;</i>
									    <div class="layui-timeline-content layui-text">
									      <h3 class="layui-timeline-title">待完成</h3>
									      <ul>
									        <li>操作人：${input.ser.mp.manufacturer.manufacturerName}${input.ser.mp.mpName }</li>
									      </ul>
									    </div>
							        </li>
                        		  </c:if>
				                
					                <c:forEach items="${input.reviews }" var="review">
										<c:if test='${review.reviewType=="1"}'>
									      	<li class="layui-timeline-item">
									    		<i class="layui-icon layui-timeline-axis">&#xe6c6;</i>
									    			<div class="layui-timeline-content layui-text">
									      				<h3 class="layui-timeline-title"><fmt:formatDate value="${review.reviewTime}" pattern="yy年MM月dd日 HH:mm"/>&nbsp;点评</h3>
											  		<ul>
												        <li>处理人：${review.reviewer.tpName }</li>
												        <li>服务满意度：
												        	<c:forEach begin="1" end="${review.serviceSatisfaction }" step="1">
												        	<i class="layui-icon">&#xe600;</i>
												        	</c:forEach>
												        </li>
												        <li>服务及时度：
												        	<c:forEach begin="1" end="${review.resultSatisfaction }" step="1">
												        	<i class="layui-icon">&#xe600;</i>
												        	</c:forEach>
												        </li>
												        <li>点评内容：${review.reviewContent }</li>
												    </ul>
									     			</div>
									  		</li>
										</c:if>
										
										<c:if test='${review.reviewType=="2"}'>
									      	<li class="layui-timeline-item">
									    		<i class="layui-icon layui-timeline-axis">&#xe6b2;</i>
									    			<div class="layui-timeline-content layui-text">
									      				<h3 class="layui-timeline-title"><fmt:formatDate value="${review.reviewTime}" pattern="yy年MM月dd日 HH:mm"/>&nbsp;审核</h3>
												  		  <ul>
													        <li>处理人：${review.reviewer.tpName }</li>
													        <li>意见：${review.reviewContent }</li>
													      </ul>
									     			</div>
											</li>
										</c:if>
										
										<c:if test='${review.reviewType=="3"}'>
									      	<li class="layui-timeline-item">
									    		<i class="layui-icon layui-timeline-axis">&#xe642;</i>
									    			<div class="layui-timeline-content layui-text">
									      				<h3 class="layui-timeline-title"><fmt:formatDate value="${review.reviewTime}" pattern="yy年MM月dd日 HH:mm"/>&nbsp;修改</h3>
												  		 <ul>
													        <li>处理人：${review.reviewer.tpName }</li>
													        <li>修改内容：${review.reviewContent }</li>
													     </ul>
									     			</div>
									  		</li>
										</c:if>
									</c:forEach>
								  <li class="layui-timeline-item">
								    <i class="layui-icon layui-timeline-axis"></i>
								    <div class="layui-timeline-content layui-text">
								      <h3 class="layui-timeline-title"><fmt:formatDate value="${input.ser.creatTime }" pattern="yy年MM月dd日 HH:mm"/>&nbsp;发起</h3>
								    </div>
								  </li>
							</ul>
	        			</div>
	        		</div>
			
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
		var msg = ${input.msg};
		var id = ${input.ser.serId};
		var current = ${input.ser.currentProcess};
			layui.use(['element','form','laydate','table','jquery','layer'], function() {
				var element = layui.element
				,form = layui.form
				,laydate = layui.laydate
				,table = layui.table
				,$ = layui.$
				,layer = layui.layer;
				
				if(msg==200){
					  layer.msg('<i class="layui-icon" style="font-size: 25px;">&#xe618;</i> 工单竣工成功')
				  }else if(msg==201){
					  layer.msg('<i class="layui-icon" style="font-size: 25px;">&#xe618;</i> 工单创建成功')
				  }
			    //选择区域触发ajax方法
				
				
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
				  
				  $('#changeBtn').on('click', function(){
					if(current==-1){
						window.location.href='ChgSER.do?serId='+id;
					}else{
						layer.msg('此状态工单不允许修改');
					}
				  });
				  
				  //竣工按钮的事件们
				  $('#completeBtn').on('click', function(){
					  if(current==-2){
					  layer.open({
							title:'工单竣工'						  
						  ,content: '确定竣工该工单吗？结束时间将以此刻计算'
						  ,btn: ['竣工', '取消']
						  ,yes: function(index, layero){
							  var id = ${input.ser.serId};				  
							  window.parent.location.href='completeSer.do?serId='+id;
						  }
						  ,btn2: function(index, layero){
							  window.location.reload();
						  }
						  ,cancel: function(){ 
						  }
						});
					  }else{
						  layer.msg('该工单已经竣工');
					  }
				  });
				  $('#deleteBtn').on('click', function(){
					  if(current==-1||current==-2){
						  window.location.href="DelSER.do?serId="+${input.ser.serId }+"&currentProcess="+${input.ser.currentProcess};
						}else{
							layer.msg('此状态工单不允许删除');
						}
					  
				  });
				});
		</script>
	</body>

</html>