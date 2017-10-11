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
		<link rel="stylesheet" href='<c:url value="/css/bootstrap.min.css"/>' />
		<style type="text/css">
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
								<a href='<c:url value="/provider/ProviderMain"/>' style="color: red;font-size: 15px;">当前有<c:out value="${input.nonDealed}"/>条记录被退回</a>
							</marquee>
						</li>
						<li>
							<a href="#" style="">欢迎,<c:out value="${input.name}"/></a>
						</li>
						<li>
							<a href='<c:url value="/provider/ProviderMain"/>' style="font-size: large;">首&nbsp;页</a>
						</li>
						<li>
							<a href="#" style="font-size: large;">查&nbsp;询</a>
						</li>
						<li>
							<a href="#" style="font-size: large;">报&nbsp;表</a>
						</li>
						<li>
							<a href="#" style="font-size: large;">设&nbsp;置</a>
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
			<div class="col-lg-1">
				</div>
			<div class="col-lg-7">
			<h3 align="center" style="color: red;">待处理维保单</h3>
			<table class="table table-striped">
								<thead>
									<tr>
										<th>子区域</th>
										<th>维保类型</th>
										<th>厂家负责人</th>
										<th>开始时间</th>
										<th>结束时间</th>
										<th>审核人</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach var="hmNonDealed" items="${input.hmNonDealedList}" varStatus="status">
								<fmt:formatDate value="${hmNonDealed.launchTime}" type="both" dateStyle="long" pattern="yyyy-MM-dd" var="launchDate" />
								<fmt:formatDate value="${hmNonDealed.completionTime}" type="both" dateStyle="long" pattern="yyyy-MM-dd" var="completionDate" /> 
									<tr>
										<td id=""><c:out value="${hmNonDealed.subRegion.subRegionName}"/></td>
										<td id="">硬件返修</td>
										<td id=""><c:out value="${hmNonDealed.mp.mpName}"/></td>
										<td id=""><c:out value="${hmNonDealed.launchTime}"/></td>
										<td id="cjjs1">			
										<c:if test="${completionDate >= launchDate}">
										<c:out value="${hmNonDealed.completionTime}"/>
										</c:if>										
										</td>
										<td id="cjcs1"><c:out value="${hmNonDealed.auditor.tpName}"/></td>
										<td>
								<a href="<c:url value="/provider/ChgHM.do?hmId=${hmNonDealed.hmId}"/>"><span class="glyphicon glyphicon-cog" style="font-size: 14px;" >修改&nbsp;&nbsp;</span></a>
								<a href="#"><span class="glyphicon glyphicon-remove" data-toggle="modal" data-target="#yjxq" style="font-size: 14px;" onclick="getdetails(${hmNonDealed.hmId});appeardel();hmaction()">详情&nbsp;&nbsp;</span></a>
							    </td>
									</tr>
								</c:forEach>
								<c:forEach var="serNonDealed" items="${input.serNonDealedList}" varStatus="status">
								<fmt:formatDate value="${serNonDealed.startTime}" type="both" dateStyle="long" pattern="yyyy-MM-dd HH:mm" var="launchDate" />
								<fmt:formatDate value="${serNonDealed.endTime}" type="both" dateStyle="long" pattern="yyyy-MM-dd HH:mm" var="completionDate" /> 
									<tr>
										<td id=""><c:out value="${serNonDealed.subRegion.subRegionName}"/></td>
										<td id="">服务响应</td>
										<td id=""><c:out value="${serNonDealed.mp.mpName}"/></td>
										<td id="">			
										<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${serNonDealed.startTime}" />
										</td>
										<td id="">
										<c:if test="${completionDate >= launchDate}">
										<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${serNonDealed.endTime}" />
										</c:if>
										</td>
										<td id=""><c:out value="${serNonDealed.auditor.tpName}"/></td>
										<td>
								<a href="<c:url value="/provider/ChgSER.do?serId=${serNonDealed.serId}"/>"><span class="glyphicon glyphicon-cog" style="font-size: 14px;" >修改&nbsp;&nbsp;</span></a>
								<a href="#"><span class="glyphicon glyphicon-remove" data-toggle="modal" data-target="#yjxq" style="font-size: 14px;" onclick="getserdetails(${serNonDealed.serId});appeardel();seraction()">详情&nbsp;&nbsp;</span></a>
							</td>
									</tr>
								</c:forEach>
									</tbody>
									</table>
						<h3 align="center" style="color: #006DCC;">未审核维保单</h3>
						
						<table class="table table-striped">
								<thead>
									<tr>
										<th>子区域</th>
										<th>维保类型</th>
										<th>创建人</th>
										<th>开始时间</th>
										<th>结束时间</th>
										<th>审核人</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="hmNonReviewed" items="${input.hmNonReviewedList}" varStatus="status"> 
									<tr>
										<td id=""><c:out value="${hmNonReviewed.subRegion.subRegionName}"/></td>
										<td id="">硬件返修</td>
										<td id=""><c:out value="${hmNonReviewed.mp.mpName}"/></td>
										<td id=""><c:out value="${hmNonReviewed.launchTime}"/></td>
										<td id="cjjs1"><c:out value="${hmNonReviewed.completionTime}"/></td>
										<td id="cjcs1"><c:out value="${hmNonReviewed.auditor.tpName}"/></td>
										<td>
								<a href="#"><span class="glyphicon glyphicon-remove" style="font-size: 14px;" data-toggle="modal" data-target="#yjxq" onclick="getdetails(${hmNonReviewed.hmId});disappeardel()">详情&nbsp;&nbsp;</span></a>
							</td>
									</tr>
								</c:forEach>
								<c:forEach var="serNonReviewed" items="${input.serNonReviewedList}" varStatus="status"> 
									<tr>
										<td id=""><c:out value="${serNonReviewed.subRegion.subRegionName}"/></td>
										<td id="">服务响应</td>
										<td id=""><c:out value="${serNonReviewed.mp.mpName}"/></td>
										<td id=""><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${serNonReviewed.startTime}"/></td>
										<td id="cjjs1"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${serNonReviewed.endTime}" /></td>
										<td id="cjcs1"><c:out value="${serNonReviewed.auditor.tpName}"/></td>
										<td>
								<a href="#"><span class="glyphicon glyphicon-remove" style="font-size: 14px;" data-toggle="modal" data-target="#yjxq" onclick="getserdetails(${serNonReviewed.serId});disappeardel()">详情&nbsp;&nbsp;</span></a>
							</td>
									</tr>
								</c:forEach>
									</tbody>
									</table>
						
			</div>
			<div class="col-lg-2">
				<br />
				<div class="list-group" style="font-size: 18px;">
							<a href="#" class="list-group-item active">
								<h3 class="list-group-item-heading" align="center">
								<span class="glyphicon glyphicon-edit"></span>
									录入维保信息
								</h3>
							</a>
							<a href="#" class="list-group-item">
								<p class="list-group-item-text" align="center">
									驻点服务录入
								</p>
							</a>
							<a href="#" class="list-group-item">
								<p class="list-group-item-text" align="center">
									巡检录入
								</p>
							</a>
							<a href="#" class="list-group-item">
								<p class="list-group-item-text" align="center">
									培训录入
								</p>
							</a>
							<a href='<c:url value="/provider/AddSER"/>' class="list-group-item">
								<p class="list-group-item-text" align="center">
									服务支撑录入
								</p>
							</a>
							<a href='<c:url value="/provider/AddHM"/>' class="list-group-item">
								<p class="list-group-item-text" align="center">
									硬件返修录入
								</p>
							</a>
							<a href="#" class="list-group-item">
								<p class="list-group-item-text" align="center">
									紧急备件服务录入
								</p>
							</a>
						</div>
			</div>
			<div class="col-lg-2">
			</div>
			<!--
            	作者：offline
            	时间：2017-06-03
            	描述：详情modal
            -->
			<div class="modal fade" id="yjxq">
			<div class="modal-dialog modal-dialog"> 
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
						 <ul class="list-group">
						  <li class="list-group-item" id="HMregion"></li>
						  <li class="list-group-item" id="HMmajor"></li>
						  <li class="list-group-item" id="HMmp"></li>
						  <li class="list-group-item" id="HMauditor"></li>
						  <li class="list-group-item" id="HMtime"></li>
						  <li class="list-group-item" id="HMnemodals"></li>
						  <li class="list-group-item" id="HMreviewcontent"></li>
						</ul>
						</ul>
					<div class="modal-footer">
						<form method="post" action='<c:url value="/provider/DelHM.do"/>' id="detailsform">
						<input name="" id="eventId" value="" type="hidden"/>
						<button type="button"  class="btn btn-primary" data-dismiss="modal" id="delbutton" >取消</button>
						<!--<button type="submit"  class="btn btn-primary"  id="delbutton" >删除</button>-->
						</form>
					</div>
				</div>
			</div>
		</div>			
	</div>
	</section>
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
		if(window.XMLHttpRequest){
			xmlHttp = new XMLHttpRequest();
		}else{
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		function getdetails(hmId){
			url = '<c:url value="/provider/ViewHM.get"/>';   		
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
			url = '<c:url value="/provider/ViewSER.get"/>';   		
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
			+"<br />服务时长(现场/远程 单位：小时)："+ser[0].fieldServDuration+"/"+ser[0].telnetServDuration
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
		function disappeardel(){
		document.getElementById("delbutton").style.visibility="hidden"; 
	}
		function appeardel(){
			document.getElementById("delbutton").style.visibility="visible";
		}
		function hmaction(){
			document.getElementById("detailsform").action='<c:url value="/provider/DelHM.do"/>';
		}
		function seraction(){
			document.getElementById("detailsform").action='<c:url value="/provider/DelSER.do"/>';
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
		
		function judge(){
		if(<c:out value="${input.nonDealed}"/>==0){
			document.getElementById("redspan").style.display="none";
		}
	}
	</script>
</html>
