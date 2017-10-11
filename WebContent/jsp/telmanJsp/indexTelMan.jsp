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

					<div class="row state-overview">
						<div class="col-lg-3 col-sm-6">
							<section class="panel">
								<div class="symbol userblue"><i class="layui-icon">&#xe630;</i>
								</div>
								<div class="value">
									<a href="#">
										<h1 id="count1">10</h1>
									</a>
									<p>维保事件总量</p>
								</div>
							</section>
						</div>
						<div class="col-lg-3 col-sm-6">
							<section class="panel">
								<div class="symbol commred"> <i class="layui-icon">&#xe60a;</i>
								</div>
								<div class="value">
									<a href="#">
										<h1 id="count2">1</h1>
									</a>
									<p>今日进展事件</p>
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
										<h1 id="count3">50</h1>
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
										<h1 id="count4">10</h1>
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
									<span class='span-title'>趋势综合图</span>
								</header>
								<div class="panel-body">

									<div class="layui-row">
										<form class="layui-form" action="">
											<div class="layui-form-item">
												<div class="col-lg-3">
													<select name="region" id="region">
														<option value="0">请选择区域</option>
														<c:forEach var="region" items="${input.regionList}" varStatus="status">
															<option value="${region.regionId}">${region.regionName}</option>
														</c:forEach>
													</select>
												</div>
												<div class="col-lg-3">
													<select name="network" id="network" onchange="getNE()">
														<option value="0">请选择专业</option>
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
												<div class="col-lg-3">
													<select name="manu" id="manu">
														<option value="0">请选择厂家</option>
														<c:forEach var="mf" items="${input.mfList}" varStatus="status">
															<option value="${mf.manufacturerId}" />${mf.manufacturerName}</option>
														</c:forEach>
													</select>
												</div>
												<button class="layui-btn" onclick="getTrend()">展示图表</button>
											</div>
										</form>
									</div>

									<div id="main" style="height:350px;display:block"></div>
								</div>
							</section>
							<!-- 类型分布图 -->
							<section class="panel">
								<header class="panel-heading bm0">
									<span class='span-title'>类型分布图</span>
								</header>
								<div class="panel-body">
									<div class="layui-row" id="circle-top">
										<form class="layui-form" action="">
											<div class="layui-form-item">
												<div class="col-lg-3">
													<select name="region" id="region">
														<option value="0">请选择区域</option>
														<c:forEach var="region" items="${input.regionList}" varStatus="status">
															<option value="${region.regionId}">${region.regionName}</option>
														</c:forEach>
													</select>
												</div>
												<div class="col-lg-3">
													<select name="network" id="network" onchange="getNE()">
														<option value="0">请选择专业</option>
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
												<div class="col-lg-3">
													<select name="manu" id="manu">
														<option value="0">请选择厂家</option>
														<c:forEach var="mf" items="${input.mfList}" varStatus="status">
															<option value="${mf.manufacturerId}" />${mf.manufacturerName}</option>
														</c:forEach>
													</select>
												</div>
												<button class="layui-btn" onclick="getTrend()">展示图表</button>
											</div>
										</form>
									</div>
									<div id="main1" style="height:350px;display:block" class="chart2"></div>
								</div>
							</section>
						</div>
						<div class="col-lg-6">
							<!-- 快捷操作 -->
							<section class="panel">
								<header class="panel-heading bm0">
									<span class='span-title'>事件分布图</span>
								</header>
								<div class="panel-body">

									<div class="layui-row" id="circle-top">
										<form class="layui-form" action="">
											<div class="layui-form-item">
												<div class="col-lg-3">
													<select name="region" id="region">
														<option value="0">请选择区域</option>
														<c:forEach var="region" items="${input.regionList}" varStatus="status">
															<option value="${region.regionId}">${region.regionName}</option>
														</c:forEach>
													</select>
												</div>
												<div class="col-lg-3">
													<select name="network" id="network" onchange="getNE()">
														<option value="0">请选择专业</option>
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
												<div class="col-lg-3">
													<select name="manu" id="manu">
														<option value="0">请选择厂家</option>
														<c:forEach var="mf" items="${input.mfList}" varStatus="status">
															<option value="${mf.manufacturerId}" />${mf.manufacturerName}</option>
														</c:forEach>
													</select>
												</div>
												<button class="layui-btn" onclick="getTrend()">展示图表</button>
											</div>
										</form>
									</div>

									<div id="main2" style="height:350px;display:block" class="chart3"></div>
								</div>
							</section>

							<!-- 最新文章 -->
							<section class="panel">
								<header class="panel-heading bm0">
									<span class='span-title'>最新工单</span>
									<span class="badge" style="background-color:#FF3333;"> new </span>
								</header>
								<div class="panel-body">
									<table class="table table-hover personal-task">
										<tbody>
											<tr>
												<td>备件返修</td>
												<td>
													<a href="#" target="_blank">合肥分公司城域网9306电源模块返修</a>
												</td>
												<td class="col-md-5">接口人：周力平 &nbsp;&nbsp;&nbsp;&nbsp; 09-11 10:00:37</td>
											</tr>
											<tr>
												<td>备件返修</td>
												<td>
													<a href="#" target="_blank">合肥分公司城域网9306电源模块返修</a>
												</td>
												<td class="col-md-5">接口人：周力平 &nbsp;&nbsp;&nbsp;&nbsp; 09-11 10:00:37</td>
											</tr>
											<tr>
												<td>备件返修</td>
												<td>
													<a href="#" target="_blank">合肥分公司城域网9306电源模块返修</a>
												</td>
												<td class="col-md-5">接口人：周力平 &nbsp;&nbsp;&nbsp;&nbsp; 09-11 10:00:37</td>
											</tr>
											<tr>
												<td>备件返修</td>
												<td>
													<a href="#" target="_blank">合肥分公司城域网9306电源模块返修</a>
												</td>
												<td class="col-md-5">接口人：周力平 &nbsp;&nbsp;&nbsp;&nbsp; 09-11 10:00:37</td>
											</tr>
											<tr>
												<td>备件返修</td>
												<td>
													<a href="#" target="_blank">合肥分公司城域网9306电源模块返修</a>
												</td>
												<td class="col-md-5">接口人：周力平 &nbsp;&nbsp;&nbsp;&nbsp; 09-11 10:00:37</td>
											</tr>
										</tbody>
									</table>
								</div>
							</section>

							<!-- 系统公告 -->
							<section class="panel">
								<header class="panel-heading bm0">
									<span class='span-title'>系统公告</span>
								</header>
								<div class="panel-body">
									<table class="table table-hover personal-task">
										<tr>
											<td>
												<p class="larry_github">
													感谢大家的点赞支持和热心关注，本系统由中国电信安徽分公司XXX代码小队编写完成<br> 版本号：

													<span>v1.01</span>
												</p>
											</td>
										</tr>
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