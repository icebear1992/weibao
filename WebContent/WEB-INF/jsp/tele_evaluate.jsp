<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width, initial-sacale=1" />
		<title>中国电信安徽分公司维保管理系统 v1.0</title>
		<link rel="stylesheet" href="../css/bootstrap.min.css" />
		<link rel="stylesheet" href='<c:url value="/css/bootstrap.min.css"/>' />
		<style type="text/css">
			h1{
				font-size: 47px;
			}
			.myfloatleft{float:left;}
			html {
				height: 100%;
			}
			
			body {
				display: flex;
				flex-direction: column;
				height: 100%;
			}
			
			.d1-right {
				float: right;
				margin-right: 80px;
			}
			
			.div-inline1{ 
			    display:inline;
			    margin-right:20px;
			} 
			.div-inline2{ 
			    display:inline;
			    margin-right:20px;
			} 
			
			#title-bule {
				color: #00F;
				font-size: x-large;
				margin-left: 80px;
			}
			
			#grszbule {
				color: #204D74;
			}
			
			#redspan {
				background-color: red;
				color: white;
			}
			
			header {
				flex: 0 0 auto;
			}
			
			.main-content {
				flex: 1 0 auto;
			}
			
			footer {
				flex: 0 0 auto;
				text-align: center;
				color: gray;
			downline{
				position: relative;
				top: 10px;
			}
			}
		</style>
	</head>

	<body onload="judge()">
		<header>
			<div class="navbar navbar-default">
				<div class="navbar-header">
					<a href="#" class="navbar-brand" id="title-bule">中国电信安徽分公司维保管理系统 v1.0</a>
					</span>
				</div>
				<div class="d1-right">
					<ul class="nav navbar-nav">
						<li style="width: 240px;">
							<marquee><br />
								<a href='<c:url value="/user/Pendings"/>' style="color: red;font-size: 15px;">当前有<c:out value="${input.nonDealed}"/>条记录未点评</a>
							</marquee>
						</li>
						<li>
							<a href="#" style="">欢迎,<c:out value="${input.name}"/></a>
						</li>
						<li>
							<a href='<c:url value="/man/Main"/>' style="font-size: large;">首&nbsp;页</a>
						</li>
						<li>
							<a href='<c:url value="/user/CreatSER"/>' style="font-size: large;">录&nbsp;入</a>
						</li>
						<li>
							<a href='<c:url value="/user/Pendings"/>' style="font-size: large;">审&nbsp;核&nbsp;<span class="badge" id="redspan"><c:out value="${input.nonDealed}"/></span></a>
						</li>
						<li>
							<a href='#' style="font-size: large;">设&nbsp;置</a>
						</li>
						<li>
							<a href='<c:url value="/Logout"/>' style="font-size: large;">退&nbsp;出</a>
						</li>
						<li>
						</li>
					</ul>
				</div>
			</div>
		</header>
		<section class="main-content">
		<div class="container">
			<div class="col-lg-2">
				
			</div>
			<h3 align="center" style="color: red;">待点评维保单</h3><br />
			<table class="table table-striped">
								<thead>
									<tr>
										<th>序号</th>
										<th>维保ID</th>
										<th>子区域</th>
										<th>维保类型</th>
										<th>厂家</th>
										<th>厂家负责人</th>
										<th>开始时间</th>
										<th>结束时间</th>
										<th>审核人</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach var="hmNonReviewed" items="${input.hmNonReviewedList}" varStatus="status"> 
									<tr>
										<td id=""><c:out value="${states.index}"/></td>
										<td id="hm${states.index}"><c:out value="${hmNonReviewed.hmId}"/></td>
										<td id=""><c:out value="${hmNonReviewed.subRegion.subRegionName}"/></td>
										<td id="">硬件返修</td>
										<td id=""><c:out value="${hmNonReviewed.mp.manufacturer.manufacturerName}"/></td>
										<td id=""><c:out value="${hmNonReviewed.mp.mpName}"/></td>
										<td id=""><c:out value="${hmNonReviewed.launchTime}"/></td>
										<td id=""><c:out value="${hmNonReviewed.completionTime}"/></td>
										<td id=""><c:out value="${hmNonReviewed.auditor.tpName}"/></td>
										
										<td>
								<a href="#"><span class="glyphicon glyphicon-thumbs-up" data-toggle="modal" data-target="#yjdp" style="font-size: 14px;" onclick="gethmdpid(${hmNonReviewed.hmId})">点评&nbsp;&nbsp;</span></a>
								<a href="#"><span class="glyphicon glyphicon-zoom-in" data-toggle="modal" data-target="#yjxq" style="font-size: 14px;" onclick="getdetails(${hmNonReviewed.hmId});displaya();">详情&nbsp;&nbsp;</span></a>
							</td>
									</tr>
								</c:forEach>
								<c:forEach var="serNonReviewed" items="${input.serNonReviewedList}" varStatus="status"> 
									<tr>
										<td id=""><c:out value="${states.index}"/></td>
										<td id="hm${states.index}"><c:out value="${serNonReviewed.serId}"/></td>
										<td id=""><c:out value="${serNonReviewed.subRegion.subRegionName}"/></td>
										<td id="">服务响应</td>
										<td id=""><c:out value="${serNonReviewed.mp.manufacturer.manufacturerName}"/></td>
										<td id=""><c:out value="${serNonReviewed.mp.mpName}"/></td>
										<td id=""><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${serNonReviewed.startTime}"/></td>
										<td id=""><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${serNonReviewed.endTime}"/></td>
										<td id=""><c:out value="${serNonReviewed.auditor.tpName}"/></td>
										
										<td>
								<a href="#"><span class="glyphicon glyphicon-thumbs-up" data-toggle="modal" data-target="#serdp" style="font-size: 14px;" onclick="getserdpid(${serNonReviewed.serId})">点评&nbsp;&nbsp;</span></a>
								<a href="#"><span class="glyphicon glyphicon-zoom-in" data-toggle="modal" data-target="#yjxq" style="font-size: 14px;" onclick="getserdetails(${serNonReviewed.serId});displaya();">详情&nbsp;&nbsp;</span></a>
							</td>
									</tr>
								</c:forEach>
									</tbody>
							</table><br />
						<h3 align="center" style="color: #006DCC;">待审核维保单</h3><br />
						
						<table class="table table-striped">
								<thead>
									<tr>
										<!--<th>项目简述</th> <!--服务内容、培训内容、问题及需求描述、维修详情 -->
										<th>序号</th>
										<th>维保ID</th>
										<th>子区域</th>
										<th>维保类型</th>
										<th>厂家</th>
										<th>厂家负责人</th>
										<th>开始时间</th>
										<th>结束时间</th>
										<th>审核人</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="hmNonVerify" items="${input.hmNonVerifyList}" varStatus="status"> 
									<tr>
										<td id=""><c:out value="${states.index}"/></td>
										<td id="hm${states.index}"><c:out value="${hmNonVerify.hmId}"/></td>
										<td id=""><c:out value="${hmNonVerify.subRegion.subRegionName}"/></td>
										<td id="">硬件返修</td>
										<td id=""><c:out value="${hmNonVerify.mp.manufacturer.manufacturerName}"/></td>
										<td id=""><c:out value="${hmNonVerify.mp.mpName}"/></td>
										<td id=""><c:out value="${hmNonVerify.launchTime}"/></td>
										<td id="cjjs1"><c:out value="${hmNonVerify.completionTime}"/></td>
										<td id="cjcs1"><c:out value="${hmNonVerify.auditor.tpName}"/></td>
										<td>
								<a href="#"><span class="glyphicon glyphicon-zoom-in" data-toggle="modal" data-target="#yjxq" style="font-size: 14px;" onclick="getdetails(${hmNonVerify.hmId});displayb();">详情&nbsp;&nbsp;</span></a>
							</td>
									</tr>
								</c:forEach>
								<c:forEach var="serNonVerify" items="${input.serNonVerifyList}" varStatus="status"> 
									<tr>
										<td id=""><c:out value="${states.index}"/></td>
										<td id="ser${states.index}"><c:out value="${serNonVerify.serId}"/></td>
										<td id=""><c:out value="${serNonVerify.subRegion.subRegionName}"/></td>
										<td id="">服务响应</td>
										<td id=""><c:out value="${serNonVerify.mp.manufacturer.manufacturerName}"/></td>
										<td id=""><c:out value="${serNonVerify.mp.mpName}"/></td>
										<td id=""><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${serNonVerify.startTime}"/></td>
										<td id=""><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${serNonVerify.endTime}"/></td>
										<td id=""><c:out value="${serNonVerify.auditor.tpName}"/></td>										
										<td>
								<a href="#"><span class="glyphicon glyphicon-zoom-in" data-toggle="modal" data-target="#yjxq" style="font-size: 14px;" onclick="getserdetails(${serNonVerify.serId});displayb();">详情&nbsp;&nbsp;</span></a>
							</td>
									</tr>
								</c:forEach>
									</tbody>
									</table>
							<br/>		
							<h3 align="center" style="color: #006DCC;">其他关联维保单</h3><br />
			                <table class="table table-striped">
								<thead>
									<tr>
										<!--<th>项目简述</th> <!--服务内容、培训内容、问题及需求描述、维修详情 -->
										<th>序号</th>
										<th>维保ID</th>
										<th>子区域</th>
										<th>维保类型</th>
										<th>厂家</th>
										<th>厂家负责人</th>
										<th>开始时间</th>
										<th>结束时间</th>
										<th>审核人</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach var="hmRelated" items="${input.hmRelatedList}" varStatus="status"> 
									<tr>
										<td id=""><c:out value="${states.index}"/></td>
										<td id="hm${states.index}"><c:out value="${hmRelated.hmId}"/></td>
										<td id=""><c:out value="${hmRelated.subRegion.subRegionName}"/></td>
										<td id="">硬件返修</td>
										<td id=""><c:out value="${hmRelated.mp.manufacturer.manufacturerName}"/></td>
										<td id=""><c:out value="${hmRelated.mp.mpName}"/></td>
										<td id=""><c:out value="${hmRelated.launchTime}"/></td>
										<td id=""><c:out value="${hmRelated.completionTime}"/></td>
										<td id=""><c:out value="${hmRelated.auditor.tpName}"/></td>
										
								<td>
								<a href="#"><span class="glyphicon glyphicon-zoom-in" data-toggle="modal" data-target="#yjxq" style="font-size: 14px;" onclick="getdetails(${hmRelated.hmId});displayc();">详情&nbsp;&nbsp;</span></a>
							    </td>
									</tr>
								</c:forEach>
								<c:forEach var="serRelated" items="${input.serRelatedList}" varStatus="status"> 
									<tr>
										<td id=""><c:out value="${states.index}"/></td>
										<td id="hm${states.index}"><c:out value="${serRelated.serId}"/></td>
										<td id=""><c:out value="${serRelated.subRegion.subRegionName}"/></td>
										<td id="">服务响应</td>
										<td id=""><c:out value="${serRelated.mp.manufacturer.manufacturerName}"/></td>
										<td id=""><c:out value="${serRelated.mp.mpName}"/></td>
										<td id=""><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${serRelated.startTime}"/></td>
										<td id=""><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${serRelated.endTime}"/></td>
										<td id=""><c:out value="${serRelated.auditor.tpName}"/></td>				
										<td>
								<a href="#"><span class="glyphicon glyphicon-zoom-in" data-toggle="modal" data-target="#yjxq" style="font-size: 14px;" onclick="getserdetails(${serRelated.serId});displayc();">详情&nbsp;&nbsp;</span></a>
							</td>
									</tr>
								</c:forEach>
									</tbody>
							</table><br />
									<div class="col-lg-2">
								</div>
							</div>
							<!--
                            	作者：offline
                            	时间：2017-06-02
                            	描述：modal
                            -->
		<div class="modal fade" id="yjdp">
			<div class="modal-dialog modal-dialog"> <!-- dialog对话框,sm小 lg大-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" onclick="closexx()" data-dismiss="modal">
							<span aria-hidden="true">&times;</span>
							<span class="sr-only" >Close</span>
						</button>
						<h4 class="modal-title" style="text-align: center;font-size: 25px;">点&nbsp;&nbsp;&nbsp;&nbsp;评</h4>
					</div>
					
					
					
					<div class="modal-body">
					<form role="form" action='<c:url value="/user/ReviewHM.do"/>' method="post" >
					<input value="" id="hmdpid" name="hmId" type="hidden"/>
						<div class="container">
							
							<div class="col-md-3">
								<h4>响应及时度</h4><input name="timeliness" hidden="" id="timeliness"/>
							<span id="1" class="myfloatleft" onclick="test('1')" onmouseout="myclear()" style="float:left;color: #DFF0D8;"><h1>★</h1></span>
							<span id="2" class="myfloatleft" onclick="test('2')" onmouseout="myclear()" style="float:left;color: #DFF0D8;"><h1>★</h1></span>
							<span id="3" class="myfloatleft" onclick="test('3')" onmouseout="myclear()" style="float:left;color: #DFF0D8;"><h1>★</h1></span>
							<span id="4" class="myfloatleft" onclick="test('4')" onmouseout="myclear()" style="float:left;color: #DFF0D8;"><h1>★</h1></span>
							<span id="5" class="myfloatleft" onclick="test('5')" onmouseout="myclear()" style="float:left;color: #DFF0D8;"><h1>★</h1></span>
							</div>
							<div class="col-md-5">
								<h4>服务满意度</h4><input name="satisfaction" hidden="" id="satisfaction"/>
							<span id="6" class="myfloatleft" onclick="test2('6')" onmouseout="myclear()" style="float:left;color: #DFF0D8;"><h1>★</h1></span>
							<span id="7" class="myfloatleft" onclick="test2('7')" onmouseout="myclear()" style="float:left;color: #DFF0D8;"><h1>★</h1></span>
							<span id="8" class="myfloatleft" onclick="test2('8')" onmouseout="myclear()" style="float:left;color: #DFF0D8;"><h1>★</h1></span>
							<span id="9" class="myfloatleft" onclick="test2('9')" onmouseout="myclear()" style="float:left;color: #DFF0D8;"><h1>★</h1></span>
							<span id="10" class="myfloatleft" onclick="test2('10')" onmouseout="myclear()" style="float:left;color: #DFF0D8;"><h1>★</h1></span>
							</div>
					<div id="mydiv"></div>
					</div>
					<div class="">
				 <p>
				 	<button type="button" class="btn btn-default" data-toggle="button" style="font-size: 13px;border-radius:12px" onclick="changecolor(1)" id="phrase1"> 很好！及时地完成了支撑</button>
				 	<button type="button" class="btn btn-default" data-toggle="button" style="font-size: 13px;border-radius:12px" onclick="changecolor(2)" id="phrase2"> 满意！将障碍的影响程度降到了最低</button>
				 	<button type="button" class="btn btn-default" data-toggle="button" style="font-size: 13px;border-radius:12px" onclick="changecolor(3)" id="phrase3"> 不错！非常专业</button>
				 	<div class="downline">
				 		<button type="button" class="btn btn-default" data-toggle="button" style="font-size: 13px;border-radius:12px" onclick="changecolor(4)" id="phrase4"> 差评！支撑不到位，没有解决问题</button>
				 		<button type="button" class="btn btn-default" data-toggle="button" style="font-size: 13px;border-radius:12px" onclick="changecolor(5)" id="phrase5"> 不满意！响应太慢</button>
				 		<button type="button" class="btn btn-default" data-toggle="button" style="font-size: 13px;border-radius:12px" onclick="changecolor(6)" id="phrase6"> 一般！服务态度有待提高</button>
				 	</div>
				 	<br />
					<div class="form-group">
						<label for="name">附加评价</label>
						<textarea class="form-control" rows="3" id="text-pingjia" name="reviewContent"></textarea>
					</div>
				</p> 
							</div>
					<div class="modal-footer">
						<br /><br />
						<button type="button"  class="btn btn-default" onclick="closexx()">重置</button>
						<button type="submit"  class="btn btn-primary">提交</button>
					</div></form>
				</div>
			</div>
		</div>			
	</div>	
	
	
	<!-- serdp -->
	<div class="modal fade" id="serdp">
			<div class="modal-dialog modal-dialog"> <!-- dialog对话框,sm小 lg大-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" onclick="closexx()" data-dismiss="modal">
							<span aria-hidden="true">&times;</span>
							<span class="sr-only" >Close</span>
						</button>
						<h4 class="modal-title" style="text-align: center;font-size: 25px;">点&nbsp;&nbsp;&nbsp;&nbsp;评</h4>
					</div>
					<div class="modal-body">
					<form role="form" action='<c:url value="/user/ReviewSER.do"/>' method="post" >
					<input value="" id="serdpid" name="serId" type="hidden"/>
						<div class="container">
							
							<div class="col-md-3">
								<h4>服务响应满意度</h4><input name="serviceSatisfaction" hidden="" id="serviceSatisfaction"/>
							<span id="11" class="myfloatleft" onclick="test3('11')" onmouseout="myclear()" style="float:left;color: #DFF0D8;"><h1>★</h1></span>
							<span id="12" class="myfloatleft" onclick="test3('12')" onmouseout="myclear()" style="float:left;color: #DFF0D8;"><h1>★</h1></span>
							<span id="13" class="myfloatleft" onclick="test3('13')" onmouseout="myclear()" style="float:left;color: #DFF0D8;"><h1>★</h1></span>
							<span id="14" class="myfloatleft" onclick="test3('14')" onmouseout="myclear()" style="float:left;color: #DFF0D8;"><h1>★</h1></span>
							<span id="15" class="myfloatleft" onclick="test3('15')" onmouseout="myclear()" style="float:left;color: #DFF0D8;"><h1>★</h1></span>
							</div>
							<div class="col-md-5">
								<h4>服务结果满意度</h4><input name="resultSatisfaction" hidden="" id="resultSatisfaction"/>
							<span id="16" class="myfloatleft" onclick="test4('16')" onmouseout="myclear()" style="float:left;color: #DFF0D8;"><h1>★</h1></span>
							<span id="17" class="myfloatleft" onclick="test4('17')" onmouseout="myclear()" style="float:left;color: #DFF0D8;"><h1>★</h1></span>
							<span id="18" class="myfloatleft" onclick="test4('18')" onmouseout="myclear()" style="float:left;color: #DFF0D8;"><h1>★</h1></span>
							<span id="19" class="myfloatleft" onclick="test4('19')" onmouseout="myclear()" style="float:left;color: #DFF0D8;"><h1>★</h1></span>
							<span id="20" class="myfloatleft" onclick="test4('20')" onmouseout="myclear()" style="float:left;color: #DFF0D8;"><h1>★</h1></span>
							</div>
					<div id="mydiv"></div>
					</div>
					<div class="">
				 <p>
				 	<button type="button" class="btn btn-default" data-toggle="button" style="font-size: 13px;border-radius:12px" onclick="changecolor(7)" id="phrase7"> 很好！及时地完成了支撑</button>
				 	<button type="button" class="btn btn-default" data-toggle="button" style="font-size: 13px;border-radius:12px" onclick="changecolor(8)" id="phrase8"> 满意！将障碍的影响程度降到了最低</button>
				 	<button type="button" class="btn btn-default" data-toggle="button" style="font-size: 13px;border-radius:12px" onclick="changecolor(9)" id="phrase9"> 不错！非常专业</button>
				 	<div class="downline">
				 		<button type="button" class="btn btn-default" data-toggle="button" style="font-size: 13px;border-radius:12px" onclick="changecolor(10)" id="phrase10"> 差评！支撑不到位，没有解决问题</button>
				 		<button type="button" class="btn btn-default" data-toggle="button" style="font-size: 13px;border-radius:12px" onclick="changecolor(11)" id="phrase11"> 不满意！响应太慢</button>
				 		<button type="button" class="btn btn-default" data-toggle="button" style="font-size: 13px;border-radius:12px" onclick="changecolor(12)" id="phrase12"> 一般！服务态度有待提高</button>
				 	</div>
				 	<br />
					<div class="form-group">
						<label for="name">附加评价</label>
						<textarea class="form-control" rows="3" id="text-pingjia2" name="reviewContent"></textarea>
					</div>
				</p> 
							</div>
					<div class="modal-footer">
						<br /><br />
						<button type="button"  class="btn btn-default" onclick="closexx()">重置</button>
						<button type="submit"  class="btn btn-primary">提交</button>
					</div></form>
				</div>
			</div>
		</div>			
	</div>	
	
	<div class="modal fade" id="yjxq">
			<div class="modal-dialog modal-dialog"> <!-- dialog对话框,sm小 lg大-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" onclick="closexx()" data-dismiss="modal">
							<span aria-hidden="true">&times;</span>
							<span class="sr-only" >Close</span>
						</button>
						<h4 class="modal-title" style="text-align: center;font-size: 25px;">详&nbsp;&nbsp;&nbsp;&nbsp;情</h4>
					</div>
					<div class="modal-body">
						<ul class="list-group">
						  <li class="list-group-item" id="HMregion">子区域:</li>
						  <li class="list-group-item" id="HMmajor">专业:</li>
						  <li class="list-group-item" id="HMmp">厂家负责人:</li>
						  <li class="list-group-item" id="HMauditor">审核人:</li>
						  <li class="list-group-item" id="HMtime">历时:</li>
						  <li class="list-group-item" id="HMnemodals">网元型号(修复数量/总数量):</li>
						  <li class="list-group-item" id="HMreviewcontent">点评内容:</li>
						  <li class="list-group-item">满意度/及时性:</li>
						</ul>
					<div class="modal-footer" id="doview">
						<form method="post" action="" id="detailsform">
						<input name="" id="eventId" value="" type="hidden"/>
						<textarea class="form-control" rows="3" id="text-advice" name="reviewContent">意见</textarea>
						<button type="submit"  class="btn btn-primary" onclick="pass()" id="passbutton">审核通过</button>
						<button type="submit"  class="btn btn-danger" onclick="fail()" id="backbutton">退回此单</button>
						</form>
					</div>
				</div>
			</div>
		</div>			
	</div>	
</section>		
			<!--
            	作者：offline
            	时间：2017-06-02
            	描述：footer
            -->				
	
		<footer class="bottom" id="footer">
			<hr />
			copyright © 安徽电信2017年运维人员IT培训营第9组,All Rights Reserved
			<ol class="breadcrumb" style="background-color: white;">
				<li>
					<a href="../关于我们.html">关于我们</a>
				</li>
				<li>
					<a href="#">使用指南</a>
				</li>
				<li>
					<a href="#">版权所有</a>
				</li>
			</ol>
		</footer>
	</body>
	
	<script type="text/javascript" src="../js/jquery-3.2.1.min.js" ></script>
	<script type="text/javascript" src="../js/bootstrap.min.js">
		
	</script>
	<script type="text/javascript">
	var xmlHttp = null;
	if(window.XMLHttpRequest){
		xmlHttp = new XMLHttpRequest();
	}else{
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
    var url=null;
    function changecolor(i){
    	if(i==1){
    		document.getElementById("text-pingjia").value+=" 很好！及时地完成了支撑！";
    	}else if(i==2){
    		document.getElementById("text-pingjia").value+=" 满意！将障碍的影响程度降到了最低！";
    	}else if(i==3){
    		document.getElementById("text-pingjia").value+=" 不错！非常专业 ！";
    	}else if(i==4){
    		document.getElementById("text-pingjia").value+=" 差评！支撑不到位，没有解决问题！";
    	}else if(i==5){
    		document.getElementById("text-pingjia").value+=" 一般！服务态度有待提高！";
    	}else if(i==6){
    		document.getElementById("text-pingjia").value+=" 不满意！响应太慢！";
    	}else if(i==7){
    		document.getElementById("text-pingjia2").value+=" 很好！及时地完成了支撑！";
    	}else if(i==8){
    		document.getElementById("text-pingjia2").value+=" 满意！将障碍的影响程度降到了最低！";
    	}else if(i==9){
    		document.getElementById("text-pingjia2").value+=" 不错！非常专业 ！";
    	}else if(i==10){
    		document.getElementById("text-pingjia2").value+=" 差评！支撑不到位，没有解决问题！";
    	}else if(i==11){
    		document.getElementById("text-pingjia2").value+=" 一般！服务态度有待提高！";
    	}else if(i==12){
    		document.getElementById("text-pingjia2").value+=" 不满意！响应太慢！";
    	}
    	document.getElementById("phrase"+i).setAttribute("style","color: orange;font-size: 13px;border-radius:12px;border: orange 1px solid;background-color: white;")
    }
    
	function test(myvalue){
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
	function test3(myvalue){
		document.getElementById("serviceSatisfaction").value=myvalue-10;
		for(var i=11;i<=15;i++){//将所有都变白星星
		document.getElementById(""+i).setAttribute("style","color:#DFF0D8");
		}
		for(var i=1;i<=myvalue;i++){//通过传入的id（也是一个数字）确定id以下的div都是橙色星星。
		document.getElementById(""+i).setAttribute("style","color:orange");
		} 
		}
function test4(myvalue){
		document.getElementById("resultSatisfaction").value=myvalue-15;
		for(var i=16;i<=20;i++){//将所有都变白星星
		document.getElementById(""+i).setAttribute("style","color:#DFF0D8");
		}
		for(var i=16;i<=myvalue;i++){//通过传入的id（也是一个数字）确定id以下的div都是橙色星星。
		document.getElementById(""+i).setAttribute("style","color:orange");
		} 
		}
	function closexx(){
		for(var i=1;i<=20;i++){//将所有都变白星星
			document.getElementById(""+i).setAttribute("style","color:#DFF0D8");
			document.getElementById("text-pingjia").value="";
			document.getElementById("text-pingjia2").value="";
		}
	}
	
	
	function getdetails(hmId){
		url = '<c:url value="/user/GetHM.get"/>';   		
		xmlHttp.open("post",url,true);
    		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    		xmlHttp.send("hmId=" + hmId);     		
    		xmlHttp.onreadystatechange=setdetails;
    	}
	function setdetails(){
	if(xmlHttp.readyState==4&&xmlHttp.status==200){
		var HMdetails = xmlHttp.responseText;
		var hd = eval("("+HMdetails+")");
		document.getElementById("eventId").name="hmId";
		document.getElementById("eventId").value=hd[0].hmId;
		document.getElementById("HMregion").innerHTML="子区域："+hd[0].subRegion.subRegionName;
		document.getElementById("HMmajor").innerHTML="专业："+hd[0].major.majorName;
		document.getElementById("HMmp").innerHTML="厂商人员："+hd[0].mp.mpName;
		document.getElementById("HMauditor").innerHTML="电信接口人："+hd[0].auditor.tpName;
		document.getElementById("HMtime").innerHTML="历时："+hd[0].launchTime+"--"+hd[0].completionTime;
		var size=0,key;
		for(key in hd[0].hmInfoList){
			if(size==0){
				document.getElementById("HMnemodals").innerHTML="<div class='div-inline1'>网元型号："+hd[0].hmInfoList[size].neModel.neModelName+"</div>";
				document.getElementById("HMnemodals").innerHTML+="<div class='div-inline2'>总数量："+hd[0].hmInfoList[size].totalNumber+"</div>";
				document.getElementById("HMnemodals").innerHTML+="<div class='div-inline2'>修复数量："+hd[0].hmInfoList[size].repairNumber+"</div>";
			}
			else{
				document.getElementById("HMnemodals").innerHTML+="<br />";
				document.getElementById("HMnemodals").innerHTML+="<div class='div-inline1'>网元型号："+hd[0].hmInfoList[size].neModel.neModelName+"</div>";
				document.getElementById("HMnemodals").innerHTML+="<div class='div-inline2'>总数量："+hd[0].hmInfoList[size].totalNumber+"</div>";
				document.getElementById("HMnemodals").innerHTML+="<div class='div-inline2'>修复数量："+hd[0].hmInfoList[size].repairNumber+"</div>";
			}
			size++;
		}
		size=0;
		for(key in hd[0].hmReviewList){
			size++;
		}
		if(size>0){
			document.getElementById("HMreviewcontent").innerHTML="点评内容："+hd[0].hmReviewList[size-1].reviewContent;
		}
		else{
		    document.getElementById("HMreviewcontent").innerHTML="点评内容：";
		}
    }
}
	function getserdetails(serId){
		url = '<c:url value="/user/GetSER.get"/>';   		
		xmlHttp.open("post",url,true);
    		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    		xmlHttp.send("serId=" + serId);     		
    		xmlHttp.onreadystatechange=setserdetails;
    	}
	function setserdetails(){
	if(xmlHttp.readyState==4&&xmlHttp.status==200){
		var serdetails = xmlHttp.responseText;
		var ser = eval("("+serdetails+")");
		document.getElementById("eventId").name="serId";
		document.getElementById("eventId").value=ser[0].serId;
		document.getElementById("HMregion").innerHTML="子区域："+ser[0].subRegion.subRegionName;
		document.getElementById("HMmajor").innerHTML="专业："+ser[0].neModel.ne.network.major.majorName;
		document.getElementById("HMmp").innerHTML="厂商人员："+ser[0].mp.mpName;
		document.getElementById("HMauditor").innerHTML="电信接口人："+ser[0].auditor.tpName;
		var str1=timeStamp2String(ser[0].startTime.time);
		var str2=timeStamp2String(ser[0].endTime.time);
		document.getElementById("HMtime").innerHTML="历时："+str1+"--"+str2;
		document.getElementById("HMnemodals").innerHTML="服务类别："+ser[0].category.demandName
		+"<br />需求描述："+ser[0].demandInfo
		+"<br />主要处理人："+ser[0].majorHandler 
		+"<br />服务时长(现场/远程)："+ser[0].fieldServDuration+"/"+ser[0].telnetServDuration
		+"<br />解决方案："+ser[0].solution;
		var size=0;
		for(key in ser[0].serReviewList){
			size++;
		}
		if(size>0){
			document.getElementById("HMreviewcontent").innerHTML="点评内容："+ser[0].serReviewList[size-1].reviewContent;
		}
		else{
		    document.getElementById("HMreviewcontent").innerHTML="点评内容：";
		}		
    }
}
	
	function timeStamp2String(time){
	    var datetime = new Date();
	    datetime.setTime(time);
	    var year = datetime.getFullYear();
	    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
	    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
	    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
	    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
	    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
	    return year + "-" + month + "-" + date+" "+hour+":"+minute;
	}
	
	function gethmdpid(hmId){
	document.getElementById("hmdpid").value=hmId;
	}
	function getserdpid(serId){
		document.getElementById("serdpid").value=serId;
	}
	function pass(){
		if(document.getElementById("eventId").name=="hmId")
		{
			document.getElementById("detailsform").action = '<c:url value="/user/VerifyHM.do"/>'; 
		}
		if(document.getElementById("eventId").name=="serId")
		{
			document.getElementById("detailsform").action = '<c:url value="/user/VerifySER.do"/>'; 
		}		 
	    document.getElementById("detailsform").submit();
	}
	function fail(){
		if(document.getElementById("eventId").name=="hmId")
		{
			document.getElementById("detailsform").action = '<c:url value="/user/FallbackHM.do"/>'; 
		}
		if(document.getElementById("eventId").name=="serId")
		{
			document.getElementById("detailsform").action = '<c:url value="/user/FallbackSER.do"/>'; 
		}		 
	    document.getElementById("detailsform").submit();
	}
	
	function displaya(){
		document.getElementById("text-advice").style.display="inline";
		document.getElementById("passbutton").style.display="none";
		document.getElementById("backbutton").style.display="inline";
	}
	
	function displayb(){
		document.getElementById("text-advice").style.display="inline";
		document.getElementById("passbutton").style.display="inline";
		document.getElementById("backbutton").style.display="inline";
	}
	
	function displayc(){
		document.getElementById("text-advice").style.display="none";
		document.getElementById("passbutton").style.display="none";
		document.getElementById("backbutton").style.display="none";
	}	
	function judge(){
		if(<c:out value="${input.nonDealed}"/>==0){
			document.getElementById("redspan").style.display="none";
		}
	}
	</script>
</html>