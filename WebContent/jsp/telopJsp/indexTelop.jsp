<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
								<a href=""><i class="layui-icon" style="font-size: 21px; color: #009688;">&#xe63c; </i>待点评工单 <span class="layui-badge layui-bg-red">${input.nonReviewNum}</span></a>
							</dd style="width: 180px;">
							<dd>
								<a href=""><i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe6b2; </i> 待审核工单<span class="layui-badge layui-bg-red">${input.nonAuditNum}</span></a>
							</dd>
						</dl>
					</li>
					<li class="layui-nav-item">
						<a href="javascript:;">
							<img src="../img/timg.jpg" class="layui-nav-img"> ${input.region }，${input.name}
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
							<a href=""><i class="layui-icon" style="font-size: 22px; color: #009688;">&#xe629; </i>主页</a>
						</li>
						<li class="layui-nav-item">
							<a href="NonFinish"><i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe609; </i> 未完成追踪</a>
						</li>
						<li class="layui-nav-item">
							<a href=""><i class="layui-icon" style="font-size: 21px; color: #009688;">&#xe63c; </i> 已归档维保单</a>
						</li>
						
						<li class="layui-nav-item ">
							<a class="" href="AddWork"><i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe615; </i> 发起新的维保单</a>
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

					<div class="row state-overview">
						<div class="col-lg-3 col-sm-6">
							<section class="panel">
								<div class="symbol userblue"><i class="layui-icon">&#xe630;</i>
								</div>
								<div class="value">
									<a href="#">
										<h1 id="count1">${input.nonFinNum}</h1>
									</a>
									<p>未完成工单</p>
								</div>
							</section>
						</div>
						<div class="col-lg-3 col-sm-6">
							<section class="panel">
								<div class="symbol commred"> <i class="layui-icon">&#xe60a;</i>
								</div>
								<div class="value">
									<a href="#">
										<h1 id="count2">${input.returnNum}</h1>
									</a>
									<p>被退回工单</p>
								</div>
							</section>
						</div>
						<div class="col-lg-3 col-sm-6">
							<section class="panel">
								<div class="symbol articlegreen">
									<i class="layui-icon">&#xe60c;</i>
								</div>
								<div class="value">
									<a href="#">
										<h1 id="count3">${input.nonReviewNum}</h1>
									</a>
									<p>待评事件总数</p>
								</div>
							</section>
						</div>
						<div class="col-lg-3 col-sm-6">
							<section class="panel">
								<div class="symbol rsswet">
									<i class="layui-icon">&#xe6b2;</i>
								</div>
								<div class="value">
									<a href="#">
										<h1 id="count4">${input.nonAuditNum}</h1>
									</a>
									<p>待审事件总数</p>
								</div>
							</section>
						</div>
					</div>

					<div class="row">
						<div class="col-lg-6">
							
							<section class="panel">
								<header class="panel-heading bm0">
									<span class='span-title'><i class="layui-icon" style="font-size:23px;color:red">&#xe60a;</i>&nbsp;被退回维保单</span>
								</header>
								<div class="panel-body">
									<table class="table table-hover personal-task">
										<tbody>
										<c:forEach items="${input.returnList }" var="re">
											<tr>
												<td>${re.type }</td>
												<td>${re.subRegion }</td>
												<td>
													<c:if test='${re.type=="服务响应"}'>
														<a href="ViewSer?serId=${re.number }">${re.title }</a>
													</c:if>
													<c:if test='${re.type=="硬件返修"}'>
														<a href="ViewHM?HMId=${re.number }">${re.title }</a>
													</c:if>
												</td>
												<td>${re.auditor }</td>
												<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${re.creatTime}"/></td>
											</tr>
										</c:forEach>
										</tbody>
									</table>
								</div>
							</section>
							
							<section class="panel">
								<header class="panel-heading bm0">
									<span class='span-title'><i class="layui-icon" style="font-size:22px;color:blue">&#xe630;</i>&nbsp;未完成维保单</span>
								</header>
								<div class="panel-body">
									<table class="table table-hover personal-task">
										<tbody>
											<c:forEach items="${input.unCompletedList }" var="uc">
											<tr>
												<td>${uc.type }</td>
												<td>${uc.subRegion }</td>
												<td>
													<c:if test='${uc.type=="服务响应"}'>
														<a href="ViewSer?serId=${uc.number }">${uc.title }</a>
													</c:if>
													<c:if test='${uc.type=="硬件返修"}'>
														<a href="ViewHM?HMId=${uc.number }">${uc.title }</a>
													</c:if>
												</td>
												<td>${uc.auditor }</td>
												<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${uc.creatTime}"/></td>
											</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</section>
						</div>
						<div class="col-lg-6">
							<!-- 快捷操作 -->
							

							<!-- 最新文章 -->
							<section class="panel">
								<header class="panel-heading bm0">
									<span class='span-title'><i class="layui-icon first" style="font-size:22px;color:green">&#xe60c;</i>&nbsp;待点评维保单</span>
								</header>
								<div class="panel-body">
									<table class="table table-hover personal-task">
										<tbody>
										<c:forEach items="${input.noreviewList }" var="nr">
											<tr>
												<td>${nr.type }</td>
												<td>${nr.subRegion }</td>
												<td>
													<c:if test='${nr.type=="服务响应"}'>
														<a href="ViewSer?serId=${nr.number }">${nr.title }</a>
													</c:if>
													<c:if test='${nr.type=="硬件返修"}'>
														<a href="ViewHM?HMId=${nr.number }">${nr.title }</a>
													</c:if>
												</td>
												<td>${nr.auditor }</td>
												<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${nr.creatTime}"/></td>
											</tr>
										</c:forEach>
										</tbody>
									</table>
								</div>
							</section>

							<!-- 待审核维保单 -->
							<section class="panel">
								<header class="panel-heading bm0">
									<span class='span-title'><i class="layui-icon first" style="font-size:22px;">&#xe6b2;</i>&nbsp;待审核维保单</span>
								</header>
								<div class="panel-body">
									<table class="table table-hover personal-task">
										<tbody>
											<c:forEach items="${input.noauditList }" var="na">
											<tr>
												<td>${na.type }</td>
												<td>${na.subRegion }</td>
												<td>
													<c:if test='${na.type=="服务响应"}'>
														<a href="ViewSer?serId=${na.number }">${na.title }</a>
													</c:if>
													<c:if test='${na.type=="硬件返修"}'>
														<a href="ViewHM?HMId=${na.number }">${na.title }</a>
													</c:if>
												</td>
												<td>${na.auditor }</td>
												<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${na.creatTime}"/></td>
											</tr>
										</c:forEach>
										</tbody>
									</table>
								</div>
							</section>

						</div>
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
			layui.use('element', function() {
				var element = layui.element;

			});

			//Demo
			layui.use('form', function() {
				var form = layui.form;

				//监听提交
				form.on('submit(formDemo)', function(data) {
					layer.msg(JSON.stringify(data.field));
					return false;
				});
			});

			//老版js
			var xmlHttp = null;
			if(window.XMLHttpRequest) {
				xmlHttp = new XMLHttpRequest();
			} else {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			var url = null;

			function getTrend() {
				url = '<c:url value="/man/GetTrend"/>';
				xmlHttp.open("post", url, true);
				xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				var network = document.getElementById("network").value;
				var region = document.getElementById("region").value;
				var manu = document.getElementById("manu").value;
				xmlHttp.send("network=" + network + "&region=" + region + "&manu=" + manu);
				xmlHttp.onreadystatechange = setTrend;
			}

			function setTrend() {
				if(xmlHttp.readyState == 4 && xmlHttp.status == 200) {
					var trendContactText = xmlHttp.responseText;
					var trend = eval("(" + trendContactText + ")");
					var serResultSatisfaction = new Array();
					var planCount = new Array();
					var hmCount = new Array();
					var hmSatisfaction = new Array();
					var faultCount = new Array();
					var time = new Array();

					for(var i = 0; i < trend.length; i++) {
						serResultSatisfaction[i] = trend[i].serResultSatisfaction;
						planCount[i] = trend[i].planCount;
						hmCount[i] = trend[i].hmCount;
						hmSatisfaction[i] = trend[i].hmSatisfaction;
						faultCount[i] = trend[i].faultCount;
						time[i] = trend[i].time;
					}
					require.config({
						paths: {
							echarts: '../js/echart'
						}
					}); 
					require(['echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie', 'echarts/theme/macarons' // 使用柱状图就加载bar模块，按需加载
							        
						],
						function(ec, theme) {
							// 基于准备好的dom，初始化echarts图表

							var myChart = ec.init(document.getElementById('main'), 'macarons');

							var option = {
								tooltip: {
									trigger: 'axis'
								},
								toolbox: {
									show: false,
									feature: {
										mark: { show: true },
										dataView: { show: true, readOnly: false },
										magicType: { show: true, type: ['line', 'bar'] },
										restore: { show: true },
										saveAsImage: { show: true }
									}
								},
								calculable: true,
								legend: {
									data: ['服务满意度', '方案支撑数', '硬件返修数', '故障查修数']
								},
								xAxis: [{
									type: 'category',
									data: [time[0], time[1], time[2], time[3], time[4], time[5]]
								}],
								yAxis: [{
										type: 'value',
										name: '维保事件',
										axisLabel: {
											formatter: '{value} 次'
										}
									},
									{
										type: 'value',
										name: '满意度',
										axisLabel: {
											formatter: '{value} 分'
										}
									}
								],
								series: [

									{
										"name": "服务满意度",
										"type": "line",
										"yAxisIndex": 1,
										"data": [serResultSatisfaction[0], serResultSatisfaction[1], serResultSatisfaction[2], serResultSatisfaction[3], serResultSatisfaction[4], serResultSatisfaction[5]]
									}, {
										"name": "方案支撑数",
										"type": "bar",
										"data": [planCount[0], planCount[1], planCount[2], planCount[3], planCount[4], planCount[5]]
									}, {
										"name": "硬件返修数",
										"type": "bar",
										"data": [hmCount[0], hmCount[1], hmCount[2], hmCount[3], hmCount[4], hmCount[5]]
									}, {
										"name": "故障查修数",
										"type": "bar",
										"data": [faultCount[0], faultCount[1], faultCount[2], faultCount[3], faultCount[4], faultCount[5]]
									}
								]
							};
							// 为echarts对象加载数据 
							myChart.setOption(option);

							var myChart1 = ec.init(document.getElementById('main1'), 'macarons');

							var data = [];
							var series = [];
							<c:forEach items="${input.serTypeList}" var="ser">
                	var map={};
                	map["value"]="${ser.total}";
                	map["name"]="${ser.demandName}${ser.total}";
                    series.push(map);
                </c:forEach>

							var option1 = {
								title: {
									text: '',
									subtext: '',
									x: 'center'
								},
								tooltip: {
									trigger: 'item',
									formatter: "{a} <br/>{b} : {c} ({d}%)"
								},
								legend: {
									orient: 'vertical',
									x: 'left',
									data: data
								},
								toolbox: {
									show: true,
									feature: {
										mark: { show: false },
										dataView: { show: false, readOnly: false },
										magicType: {
											show: false,
											type: ['pie', 'funnel'],
											option: {
												funnel: {
													x: '25%',
													width: '50%',
													funnelAlign: 'left',
													max: 1548
												}
											}
										},
										restore: { show: false },
										saveAsImage: { show: false }
									}
								},
								calculable: true,
								series: [{
									name: '维保事件',
									type: 'pie',
									radius: '55%',
									center: ['50%', '60%'],
									data: series
								}]
							};

							// 为echarts对象加载数据 
							myChart1.setOption(option1);

							var myChart2 = ec.init(document.getElementById('main2'), 'macarons');

							var data = [];
							var series = [];
							<c:forEach items="${input.eventList}" var="eve">
            	data.push("${eve.get(eventName)}");
            	var map={};
            	map["value"]="${eve.eventCount}";
            	map["name"]="${eve.eventName}${eve.eventCount}";
                series.push(map);
          		</c:forEach>

							var option2 = {
								title: {
									text: '',
									subtext: '',
									x: 'center'
								},
								tooltip: {
									trigger: 'item',
									formatter: "{a} <br/>{b} : {c} ({d}%)"
								},
								legend: {
									orient: 'vertical',
									x: 'left',
									data: data
								},
								toolbox: {
									show: false,
									feature: {
										mark: { show: false },
										dataView: { show: false, readOnly: false },
										magicType: {
											show: true,
											type: ['pie', 'funnel'],
											option: {
												funnel: {
													x: '25%',
													width: '50%',
													funnelAlign: 'left',
													max: 1548
												}
											}
										},
										restore: { show: true },
										saveAsImage: { show: true }
									}
								},
								calculable: true,
								series: [{
									name: '维保事件',
									type: 'pie',
									radius: '55%',
									center: ['50%', '60%'],
									data: series
								}]
							};
							myChart2.setOption(option2);
						}
					);
				}
			}
		</script>
	</body>

</html>