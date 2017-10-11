<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width, initial-sacale=1" />
		<title>中国电信安徽分公司维保管理系统</title>
		<link rel="stylesheet" href='<c:url value="/css/bootstrap.min.css"/>' />
		<link rel="stylesheet" href="../css/bootstrap.min.css" />
		<script src="../date/jquery.min.js"></script>
<script src="../date/jquery-ui.min.js"></script>
<script src="../date/jquery-ui-timepicker-addon.min.js"></script>
<script type="text/javascript" src="../date/jquery-ui-timepicker-zh-CN.js"></script>
<link rel="stylesheet" href='<c:url value="/date/jquery-ui.min.css"/>' />
<script type="text/javascript">
(function($) {
	$(function() {
		$.datepicker.regional['zh-CN'] = {
			changeMonth: true,
			changeYear: true,
			clearText: '清除',
			clearStatus: '清除已选日期',
			closeText: '关闭',
			closeStatus: '不改变当前选择',
			prevText: '<上月',
			prevStatus: '显示上月',
			prevBigText: '<<',
			prevBigStatus: '显示上一年',
			nextText: '下月>',
			nextStatus: '显示下月',
			nextBigText: '>>',
			nextBigStatus: '显示下一年',
			currentText: '今天',
			currentStatus: '显示本月',
			monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
			monthNamesShort: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
			monthStatus: '选择月份',
			yearStatus: '选择年份',
			weekHeader: '周',
			weekStatus: '年内周次',
			dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
			dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
			dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
			dayStatus: '设置 DD 为一周起始',
			dateStatus: '选择 m月 d日, DD',
			dateFormat: 'yy-mm-dd',
			firstDay: 1,
			initStatus: '请选择日期',
			isRTL: false
		};
	});
	
	$(function() {
	
		var dates = new Date(new Date()-7*24*60*60*1000);
		
		$("#date_start").datepicker({minDate:dates,maxDate:new Date()});
		
		$("#date_end").datepicker({maxDate:new Date()});
		
		
		$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
		
		$("#defult1").datetimepicker();
		$("#defult2").datetimepicker();
		
		$('#date').prop("readonly", true).datetimepicker({
			timeText: '时间',
			hourText: '小时',
			minuteText: '分钟',
			secondText: '秒',
			currentText: '现在',
			closeText: '完成',
			showSecond: true, //显示秒  
			timeFormat: 'HH:mm:ss' //格式化时间  
		});

		$("#date_yy-mm-dd").prop("readonly", true).datepicker({
			changeMonth: true,
			dateFormat: "yy-mm-dd",
			onClose: function(selectedDate) {

			}

		});

		$("#date_start").prop("readonly", true).datepicker({
			changeMonth: true,
			dateFormat: "yy-mm-dd",
			onClose: function(selectedDate) {
				$("#date_end").datepicker("option", "minDate", selectedDate);
			}
		});

		$("#date_end").prop("readonly", true).datepicker({
			
			changeMonth: true,
			dateFormat: "yy-mm-dd",
			onClose: function(selectedDate) {
				
				$("#date_start").datepicker("option", "maxDate", selectedDate);
				$("#date_end").val($(this).val());
			}
		});

		$('#date_hhmmss').prop("readonly", true).timepicker({
			timeText: '时间',
			hourText: '小时',
			minuteText: '分钟',
			secondText: '秒',
			currentText: '现在',
			closeText: '完成',
			showSecond: true, //显示秒  
			timeFormat: 'HH:mm:ss' //格式化时间  
		});

		$.timepicker.dateRange(
			$("#date_start_1"),
			$("#date_end_1"), {
				minInterval: (1000 * 60 * 60 * 24 * 1), // 区间时间间隔时间
				maxInterval: (1000 * 60 * 60 * 24 * 1), // 1 days 区间时间间隔时间
				start: {}, // start picker options
				end: {} // end picker options});
			}
		);
	});
}(jQuery));
</script>
		<style type="text/css">
			.onError{
				color:red;
				font-size:13px;
			}
			html {
				height: 100%;
			}
			body {
				display: flex;
				flex-direction: column;
				height: 100%;
			}
			.date_selector .nav {
				width: 17.5em;
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
			.d1-right {
				float: right;
				margin-right: 80px;
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
			
			#footer {
				text-align: center;
			}
			
			.panel-body {
				font-size: 15px;
			}
		</style>
	</head>
	<body onload="getregion();judge()">
		<header>
			<div class="navbar navbar-default">
				<div class="navbar-header">
					<a href="#" class="navbar-brand" id="title-bule">中国电信安徽分公司维保管理系统 v1.0</a>
				</div>
				<div class="d1-right">
					<ul class="nav navbar-nav">
						<li style="width: 240px;">
							<marquee><br />
								<a href='<c:url value="/provider/ProviderMain"/>' style="color: red;font-size: 15px;">当前有<c:out value="${input.nonDealed}"/>条记录未点评</a>
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

		<!--
			标准头结束
        -->
		<br /><br />

		<div class="nav nav-pills" style="font-size: 19px;background-color:#EBEBEB ">
			<li>
			    <a href="#">请选择维保类型 :</a>
			</li>
			<li>
				<a href="#">驻点服务录入</a>
			</li>
			<li>
				<a href="#">巡检录入</a>
			</li>
			<li>
				<a href="#">培训录入</a>
			</li>
			<li>
				<a href='<c:url value="/provider/AddSER"/>'>服务支撑录入</a>
			</li>
			<li class="active">
				<a href='<c:url value="/provider/AddHM"/>'>硬件返修录入</a>
			</li>
			<li>
				<a href="#">紧急备件服务录入</a>
			</li>
		</div>
		<br /><br />
		<form method="post" class="form-horizontal" role="form" id="form" action='<c:url value="/provider/AddHM"/>'>
			<div class="container">
				<div class="col-lg-1">
					
				</div>
				<div class="col-lg-11">
					<div class="checkbox" >
				<label>
					<input type="checkbox" name="end" id="check" value="1" style="height:10px;width:10px" onchange="shutend()"> <span style="font-size:17px">已完结</span>
				</label>
			</div>
			<br/>
			<div align="left" style="font-size: 16px">
				<span>
				开始日期:
				<input type="text" id="date_start" name="launchTime" onchange="getendTime()" readonly="readonly" class="required"> &nbsp;</span>&nbsp;&nbsp;
				<span id="enddate" style="display:none" readonly="readonly">
				结束日期:
				<input type="text" id="date_end" name="completionTime" value=<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date())%> class="required"></span>
				
				<br /><br />
				<div style="font-size: 16px">
					区&nbsp;&nbsp;&nbsp;&nbsp;域:&nbsp;&nbsp;
					<select class="combsobox" name="region" onchange="getSubRegions()" id="region">
						<option>-选择区域-</option>
						<c:forEach var="region" items="${input.regionList}" varStatus="status">
							<option value=<c:out value="${region.regionId}"/>>
								<c:out value="${region.regionName}" />
							</option>
						</c:forEach>
					</select>
					&nbsp;子区域:&nbsp;
					<select class="combobox" name="subRegion" id="subregion" >
						<option>-选择子区域-</option>
					</select>
					&nbsp;专业:&nbsp;
					<select class="combobox" name="major" id="major" onchange="getMajorContact()" onblur="getNetWork()">
						<option>-选择专业-</option>
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
					&nbsp;接口人:&nbsp;
					<select class="combobox" name="majorContact" id="majorContact">
					</select>
	            <br /><br />
				</div>
			</div>
		</div>
	</div>
			<div align="center">			
				<div class="addh">
					<div class="panel panel-default" style="height: 200px;width: 700px;"line-height:50%">
						<div class="panel-heading">
							<h3 class="panel-title">硬件类型1</h3>	
						</div>
						<div class="panel-body" style="font-size: 16px;" align="left";"line-height:50%">
							&nbsp;&nbsp;网络:&nbsp;<select name="network" id="network1" onchange="getNE(1)">
							<option>--请选择网络--</option>
							</select>&nbsp;&nbsp;
							网元:&nbsp;<select name="ne" id="NE1" onchange="getNEModels(1)">
								<option>--请选择网元--</option>
							</select>&nbsp;&nbsp;
							型号:&nbsp;<select name="neModels" id="NEModels1">
							</select>
							<br /> <br />
							<div> 
							&nbsp;&nbsp;需修数量:&nbsp;<input type="text" name="totalNum" id="totalNum" value="" />
				            &nbsp;&nbsp;修复数量:&nbsp;<input type="text" name="repairNum" id="repairNum" value="" />
							</div> <br /> 
							&nbsp;&nbsp;<button type="button" class="btn btn-primary" onclick="addh()">
									<span class="glyphicon glyphicon-plus">添加硬件类型</span>
							</button>
						</div>
					</div>
				</div>
			
				<span id="2h" class="addh">					
				</span>
			</div>
			<div class="form-group" align="center" id="suggest">
				<button type="submit" class="btn btn-primary" id="tijiao">提交</button>
			</div>
		</form>
		<br />
		<!--
			标准尾
        -->
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
	<script type="text/javascript" src="../js/jquery.date_input.pack.js"></script>	
	<script type="text/javascript">
		var xmlHttp = null;
        	if(window.XMLHttpRequest){
        		xmlHttp = new XMLHttpRequest();
        	}else{
        		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        	}
        var url=null;
        var neType=1;
		$(function() {
			$('.date_picker').date_input();
		});


        function addh(){
        	neType++;
        	document.getElementById(neType+"h").innerHTML ='<div class="panel panel-default" style="height: 200px;width: 700px;" align="left"><div class="panel-heading"><h3 class="panel-title" align="center">硬件类型'+neType+'</h3></div><div class="panel-body" style="font-size: 16px;">&nbsp;&nbsp;网络:&nbsp;<select name="network" id="network'+neType+'" onchange="getNE('+neType+')"><option>--请选择网络--</option></select>&nbsp;&nbsp;&nbsp;网元:&nbsp;<select name="ne" id="NE'+neType+'" onchange="getNEModels('+neType+')"><option>--请选择网元--</option></select>&nbsp;&nbsp;&nbsp;型号:&nbsp;<select name="neModels" id="NEModels'+neType+'"></select><br /> <br /> &nbsp;&nbsp;需修数量:&nbsp;<input type="text" name="totalNum" id="" value="0" />&nbsp;&nbsp;&nbsp;修复数量:&nbsp;<input type="text" name="repairNum" id="" value="0" /><br /><br />&nbsp;&nbsp;<button type="button" class="btn btn-primary" onclick="addh()"><span class="glyphicon glyphicon-plus">添加硬件类型</span></button></div></div></span><span id="'+(neType+1)+'h" class="addh">';
        }
        

		function getSubRegions(){
			url = '<c:url value="/common/GetSubRegion"/>';
			xmlHttp.open("post",url,true);
        		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        		var regionId = document.getElementById("region").value;
        		xmlHttp.send("regionId=" + regionId);
        		xmlHttp.onreadystatechange=setSubRegions;
		}
		
		function setSubRegions(){
        		if(xmlHttp.readyState==4&&xmlHttp.status==200){
        			var subRegion = document.getElementById("subregion");
        			subRegion.options.length=1;
        			var subRegionText = xmlHttp.responseText;
        			var sr = eval("("+subRegionText+")");
        			for(var i=0;i<sr.length;i++){
        				var subRegionName = sr[i].subRegionName;
        				var subRegionId = sr[i].subRegionId;
        				subRegion.options.add(new Option(subRegionName,subRegionId))
        			}
        		}
        	}
		
		function getMajorContact(){
			url = '<c:url value="/common/GetContact"/>';
			xmlHttp.open("post",url,true);
        		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        		var subRegionId = document.getElementById("subregion").value;
        		var majorId = document.getElementById("major").value;
        		xmlHttp.send("subRegionId="+subRegionId+"&majorId=" + majorId);
        		xmlHttp.onreadystatechange=setMajorContact;
		}
		
		function setMajorContact(){
			if(xmlHttp.readyState==4&&xmlHttp.status==200){
        			var majorContact = document.getElementById("majorContact");
        			majorContact.options.length=0;
        			var majorContactText = xmlHttp.responseText;
        			var mc = eval("("+majorContactText+")");
        			for(var i=0;i<mc.length;i++){
        				var tpId = mc[i].tp.tpId;
        				var tpName = mc[i].tp.tpName;
        				majorContact.options.add(new Option(tpName ,tpId))
        			}
        		}
			}
		
		function getNetWork(){
			for(var n=1;n<=neType;n++){
    			var nemodel = document.getElementById("NEModels"+n);
    			nemodel.options.length=0;
			}
			url = '<c:url value="/common/GetNetwork"/>';
			xmlHttp.open("post",url,true);
        		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        		var majorId = document.getElementById("major").value;
        		xmlHttp.send("majorId="+majorId);
        		xmlHttp.onreadystatechange=setNetWork;
		}
		
		function setNetWork(){
			if(xmlHttp.readyState==4&&xmlHttp.status==200){
				var networkText = xmlHttp.responseText;
				var nw = eval("("+networkText+")");
				for(var n=1;n<=neType;n++){
        			var network = document.getElementById("network"+n);
        			network.options.length=1;        			        			
        			for(var i=0;i<nw.length;i++){
        				var networkId = nw[i].networkId;
        				var networkName = nw[i].networkName;
        				network.options.add(new Option(networkName,networkId))
        			}
        		}
			}
		}
		function getNE(n){
			url = '<c:url value="/common/GetNE"/>';
			xmlHttp.open("post",url,true);
        		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        		var network = document.getElementById("network"+n).value;
        		xmlHttp.send("networkId="+network);
        		xmlHttp.onreadystatechange=function(){setNE(n)};
		}
		function setNE(n){
			if(xmlHttp.readyState==4&&xmlHttp.status==200){
        			var ne = document.getElementById("NE"+n);
        			ne.options.length=1;
        			var neText = xmlHttp.responseText;
        			var n = eval("("+neText+")");
        			for(var i=0;i<n.length;i++){
        				var neId = n[i].neId;
        				var neName = n[i].neName;        				
        				ne.options.add(new Option(neName,neId))
        			}
        		}
		}
		function getNEModels(n){
			url = '<c:url value="/common/GetNEModel"/>';
			xmlHttp.open("post",url,true);
        		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        		var ne = document.getElementById("NE"+n).value;
        		xmlHttp.send("neId="+ne);
        		xmlHttp.onreadystatechange=function(){setNEModels(n)};
		}
		function setNEModels(n){
			if(xmlHttp.readyState==4&&xmlHttp.status==200){
				var neModels = document.getElementById("NEModels"+n);
        			neModels.options.length=0;
        			var neModelsText = xmlHttp.responseText;
        			var nm = eval("("+neModelsText+")");
        			for(var i=0;i<nm.length;i++){
        				var NEModel_id = nm[i].neModelId;
        				var NEModel_name = nm[i].neModelName;
        				neModels.options.add(new Option(NEModel_name,NEModel_id))
        		}
			}
		}
		function shutend(){
			var obj = document.getElementById("check");
			var value = obj.checked;
			if(!value){
				document.getElementById("enddate").style.display="none";
			}else{
				document.getElementById("enddate").style.display="inline";
			}
		}
		$("form :input").change(function(){
			var $parent = $(this).parent();
			$parent.find(".msg").remove();
			if($(this).is("#date_end")){
				if(!duibi($("#date_start").val(),this.value)){
					var errorMsg = " 开始日期不得晚于结束日期";
					$parent.append("<span class='msg onError'>" + errorMsg + "</span>");
				}
			}
		});
		$("form :input").blur(function(){
			var $parent = $(this).parent();
			$parent.find(".msg").remove();
			if($(this).is("#date_start")){
				$("#date_end").trigger("blur");
				if(this.value==""){
					var errorMsg = "请选择日期";
					$parent.append("<span class='msg onError'>" + errorMsg + "</span>");
				}
			}
			if($(this).is("#date_end")){
				if(!duibi($("#date_start").val(),this.value)){
					var errorMsg = " 开始日期不得晚于结束日期";
					$parent.append("<span class='msg onError'>" + errorMsg + "</span>");
				}
			}
			
		});
		
		
		$("#tijiao").click(function(){
			$("form .required:input").trigger("blur");
			var numError = $("form .onError").length;
			if(numError){
				return false;
			}else if($("#region").val()=="-选择区域-"){
				alert("请选择区域");
				return false;
				
			}else if($("#subregion").val()=="-选择子区域-"){
				alert("请选择子区域");
				return false;
			}else if($("#major").val()=="-选择专业-"){
				alert("请选择专业");
				return false;
			}else if($("#majorContact").val()==""){
				alert("请选择接口人");
				return false;
			}else if($("#network1").val()=="--请选择网络--"){
				alert("请选择网络");
				return false;
			}else if($("#NE1").val()=="--请选择网元--"){
				alert("请选择网元");
				return false;
			}else if($("#NEModels1").val()==""){
				alert("请选择型号");
				return false;
			}else if($("#totalNum").val()==""){
				alert("需修数量不能为0");
				return false;
			}else if($("#repairNum").val()==""){
				alert("修复数量不能为0");
				return false;
			}else if(($("#totalNum").val()-$("#repairNum").val())<0){
				alert("需修数量不能小于修复数量");
				return false;
			}
			
		});
		function duibi(a,b){
			
		var arr=a.split("-");
		var starttime=new Date(arr[0],arr[1],arr[2]);
		var starttimes=starttime.getTime();
		
		var arrs=b.split("-");
		var lktime=new Date(arrs[0],arrs[1],arrs[2]);
		var lktimes=lktime.getTime();
		
			if(starttimes>=lktimes)
		{
			return false;
		}
			else
			return true;
		}
	</script>
</html>