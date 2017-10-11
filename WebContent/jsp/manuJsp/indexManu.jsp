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

	<body >
		<div class="layui-layout layui-layout-admin">
			<div class="layui-header">
				<div class="layui-logo">中国电信安徽分公司维保系统</div>
				<!-- 头部区域（可配合layui已有的水平导航） -->

				<ul class="layui-nav layui-layout-right">
					<li class="layui-nav-item">
						<a href="javascript:;">当前工单</a><span class="layui-badge-dot" id="alert"></span>
						<dl class="layui-nav-child">
							<dd style="width: 180px;">
								<a href=""><i class="layui-icon" style="font-size: 21px; color: #009688;">&#xe63c; </i>未完成工单 <span class="layui-badge layui-bg-red">${input.nonFin}</span></a>
							</dd style="width: 180px;">
							<dd>
								<a href=""><i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe6b2; </i> 被退回工单<span class="layui-badge layui-bg-red">${input.nonDealed}</span></a>
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
										<h1 id="count1">${input.nonFin}</h1>
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
										<h1 id="count2">${input.nonDealed}</h1>
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
										<h1 id="count3">${input.nonReview}</h1>
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
										<h1 id="count4">${input.nonAudit}</h1>
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
														<a href="ViewSer?serId=${re.number }&msg=0">${re.title }</a>
													</c:if>
													<c:if test='${re.type=="硬件返修"}'>
														<a href="ViewHM?HMId=${re.number }&msg=0">${re.title }</a>
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
											<c:forEach items="${input.unfinList }" var="uf">
											<tr>
												<td>${uf.type }</td>
												<td>${uf.subRegion }</td>
												<td>
													<c:if test='${uf.type=="服务响应"}'>
														<a href="ViewSer?serId=${uf.number }&msg=0">${uf.title }</a>
													</c:if>
													<c:if test='${uf.type=="硬件返修"}'>
														<a href="ViewHM?HMId=${uf.number }&msg=0">${uf.title }</a>
													</c:if>
												</td>
												<td>${uf.auditor }</td>
												<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${uf.creatTime}"/></td>
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
														<a href="ViewSer?serId=${nr.number }&msg=0">${nr.title }</a>
													</c:if>
													<c:if test='${nr.type=="硬件返修"}'>
														<a href="ViewHM?HMId=${nr.number }&msg=0">${nr.title }</a>
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
														<a href="ViewSer?serId=${na.number }&msg=0">${na.title }</a>
													</c:if>
													<c:if test='${na.type=="硬件返修"}'>
														<a href="ViewHM?HMId=${na.number }&msg=0">${na.title }</a>
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
		<script>
		var msg = ${input.msg};
		layui.use(['layer','jquery','element'], function(){
			  var layer = layui.layer
			  ,$ = layui.$
			  ,element = layui.element;
			  if(msg==200){
				  layer.msg('<i class="layui-icon" style="font-size: 25px;">&#xe618;</i> 工单竣工成功')
			  }else if(msg==201){
				  layer.msg('<i class="layui-icon" style="font-size: 25px;">&#xe618;</i> 工单创建成功')
			  }
		});
		</script>
	</body>

</html>