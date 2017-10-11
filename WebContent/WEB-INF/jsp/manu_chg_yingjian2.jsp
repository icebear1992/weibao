<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width, initial-sacale=1" />
		<title>中国电信安徽分公司维保管理系统</title>
		<link rel="stylesheet" href='../css/bootstrap.min.css' />
		<link rel="stylesheet" href='<c:url value="/css/bootstrap.min.css"/>' />
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
		html {
				height: 100%;
			}
		body {
				display: flex;
				flex-direction: column;
				height: 100%;
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
			select {
    width:130px;height:27px;
    border: 1px solid #ccc; 
    border-radius: 3px;
    box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
    /*将默认的select选择框样式清除*/
    appearance:none;
    -moz-appearance:none;
    -webkit-appearance:none;
    /*在选择框的最右侧中间显示小箭头图片*/
    background: url("http://ourjs.github.io/static/2015/arrow.png") no-repeat scroll right center transparent;
    /*为下拉小箭头留出一点位置，避免被文字覆盖*/
                padding-right: 10px;
                font-size: 12px;
                }
		</style>
	</head>
	<script type="text/javascript" src="../js/myjquery.js"></script>

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
		<!--
			标准头结束
        -->
		<br />

		<div class="alert alert-warning"><h3 align="center"><span class="glyphicon glyphicon-cog"></span> 硬件返修修改</h3></div>
		<br />
		<form method="post" class="form-horizontal" role="form" id="form" action='<c:url value="/provider/ChgHM.do"/>'>
		
		
		<div align="center">
				开始日期:<input type="text" id="date_start" name="launchTime" value="${input.hm.launchTime}" readonly="readonly"> &nbsp;&nbsp;&nbsp;结束日期:
				<input type="text" name="completionTime" id="date_end" value="${input.hm.completionTime}" readonly="readonly"><br /><br />
		<div style="font-size: 16px;font-family: '黑体';font-weight:bold">
					区域:
					<select class="combsobox" name="region" onchange="getSubRegions()" id="region">						
						<c:forEach var="region" items="${input.regionList}" varStatus="status">
							<option value=<c:out value="${region.regionId}"/>>
								<c:out value="${region.regionName}" />
							</option>
						</c:forEach>
					</select>
					&nbsp;子区域:
					<select class="combobox" name="subRegion" id="subregion" >
					</select>
					&nbsp;专业:
					<select class="combobox" name="major" id="major" onchange="getMajorContact()" onblur="getNetWork()">
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
					&nbsp;接口人:
					<select class="combobox" name="majorContact" id="majorContact">
					</select>
				</div>	
		</div>
		<input type="hidden" name="hmId" value="${input.hm.hmId}">
		<br />
			<div class="col-lg-3">
			</div>			
			<div class="col-lg-6">
			<c:forEach var="hmInfo" items="${input.hm.hmInfoList}" varStatus="status">
				<div class="panel panel-primary" >
					<div class="panel-heading">
						<h3 class="panel-title">硬件类型${status.count}</h3>
					</div>
					<div class="panel-body" style="font-size: 16px;">
						网络:<select name="network" id="network${status.count}" onchange="getNE(${status.count})">
						<option value="${hmInfo.neModel.getNe().network.networkId }"><c:out value="${hmInfo.neModel.getNe().network.networkName }" /></option>
						</select>&nbsp;&nbsp;
						网元:<select name="ne" id="NE${status.count}" onchange="getNEModels(${status.count})">
						<option value="${hmInfo.neModel.getNe().neId }"><c:out value="${hmInfo.neModel.getNe().neName }" /></option>
						</select>&nbsp;&nbsp;
						型号:<select name="neModels" id="NEModels${status.count}">
						<option value="${hmInfo.neModel.neModelId}"><c:out value="${hmInfo.neModel.neModelName}" /></option>
						</select>
						<br /> <br /> 需修数量:
						<input type="text" style="height:27px;width:80px" name="totalNum" id="" value="${hmInfo.totalNumber}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						修复数量:
						<input type="text" style="height:27px;width:80px" name="repairNum" id="" value="${hmInfo.repairNumber}" />
						<br /><br />
						<button type="button" class="btn btn-primary" onclick="addh()">
									<span class="glyphicon glyphicon-plus">添加硬件类型</span>
						</button>
					</div>
				</div>
				</c:forEach>
				<div class="form-group" align="center">
				
			</div>
			<div id="${input.hm.hmInfoList.size()+1}h" class="addh">					
			</div>
			<div align="center">
			<button type="submit" class="btn btn-primary" id="tijiao">提交</button></div>
			</div>
			
			<div class="col-lg-3">
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
var neType=${input.hm.hmInfoList.size()};
$(function() {
	$('.date_picker').date_input();
});


function addh(){
	neType++;
	document.getElementById(neType+"h").innerHTML ='<div class="panel panel-success"  align="left"><div class="panel-heading"><h3 class="panel-title">硬件类型'+neType+'</h3></div><div class="panel-body" style="font-size: 16px;">&nbsp;&nbsp;网络:<select name="network" id="network'+neType+'" onchange="getNE('+neType+')"><option>--请选择网络--</option></select>&nbsp;&nbsp;网元:<select name="ne" id="NE'+neType+'" onchange="getNEModels('+neType+')"><option>--请选择网元--</option></select>&nbsp;&nbsp;型号:<select name="neModels" id="NEModels'+neType+'"></select><br /> <br /> 需修数量:<input type="text" style="height:27px;width:80px" name="totalNum" id="" value="0" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 修复数量:<input type="text" style="height:27px;width:80px" name="repairNum" id="" value="0" /><br /><br /><button type="button" class="btn btn-primary" onclick="addh()"><span class="glyphicon glyphicon-plus">添加硬件类型</span></button></div></div></span><span id="'+(neType+1)+'h" class="addh">';            
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
			subRegion.options.length=0;
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
	alert("被调用");
	for(var n=1;n<=neType;n++){
		var ne=document.getElementById("NE"+n);
		ne.options.length=0;
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
			network.options.length=0;
			network.options.add(new Option("请选择网络",""));
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
			ne.options.length=0;
			ne.options.add(new Option("请选择网元",""));
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
function judge(){
	if(<c:out value="${input.nonDealed}"/>==0){
		document.getElementById("redspan").style.display="none";
	}
}

$("form :input").change(function(){
	var $parent = $(this).parent();
	$parent.find(".msg").remove();
	if($(this).is("#date_end")){
		if(!duibi($("#date_start").val(),this.value)){
			var errorMsg = " 开始日期不得晚于结束日期";
			$parent.append("<span class='msg onError' style='color:red'>" + errorMsg + "</span>");
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
			$parent.append("<span class='msg onError' style='color:red'>" + errorMsg + "</span>");
		}
	}
	if($(this).is("#date_end")){
		if(!duibi($("#date_start").val(),this.value)){
			var errorMsg = " 开始日期不得晚于结束日期";
			$parent.append("<span class='msg onError' style='color:red'>" + errorMsg + "</span>");
		}
	}
	
});


$("#tijiao").click(function(){
	$("form .required:input").trigger("blur");
	var numError = $("form .onError").length;
	if(numError){
		return false;
	}else if(!duibi($("#date_start").val(),$("#date_end").val())){
		alert("开始日期不得晚于结束日期");
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
	}else if($("#date_end").val()==""){
		alert("请选择结束时间");
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


window.onload=function(){ 
	//设置区域
	document.getElementById("region").value=${input.hm.subRegion.region.regionId};
	document.getElementById("major").value=${input.hm.major.majorId}; 
    //设置子区域并选中
    url = '<c:url value="/common/GetSubRegion"/>';
	xmlHttp.open("post",url,true);
	xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	var regionId = document.getElementById("region").value;
	xmlHttp.send("regionId=" + regionId);
	xmlHttp.onreadystatechange=function(){		
		if(xmlHttp.readyState==4&&xmlHttp.status==200){
			var subRegion = document.getElementById("subregion");
			subRegion.options.length=0;
			var subRegionText = xmlHttp.responseText;
			var sr = eval("("+subRegionText+")");
			for(var i=0;i<sr.length;i++){
				var subRegionName = sr[i].subRegionName;
				var subRegionId = sr[i].subRegionId;
				subRegion.options.add(new Option(subRegionName,subRegionId))
			}
			document.getElementById("subregion").value=${input.hm.subRegion.subRegionId};
			//接着在设置接口人
			url = '<c:url value="/common/GetContact"/>';
			xmlHttp.open("post",url,true);
			xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			var subRegionId = document.getElementById("subregion").value;
			var majorId = document.getElementById("major").value;
			xmlHttp.send("subRegionId="+subRegionId+"&majorId=" + majorId);
			xmlHttp.onreadystatechange=function(){
				if(xmlHttp.readyState==4&&xmlHttp.status==200){
					var majorContact = document.getElementById("majorContact");
					majorContact.options.length=0;
					var majorContactText = xmlHttp.responseText;
					var mc = eval("("+majorContactText+")");
					for(var i=0;i<mc.length;i++){
						var tpId = mc[i].tp.tpId;
						var tpName = mc[i].tp.tpName;
						majorContact.options.add(new Option(tpName,tpId))
					}
					document.getElementById("majorContact").value=${input.hm.auditor.tpId};
					//接着设置网络
					url = '<c:url value="/common/GetNetwork"/>';
					xmlHttp.open("post",url,true);
					xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
					majorId = document.getElementById("major").value;
					xmlHttp.send("majorId="+majorId);
					xmlHttp.onreadystatechange=function(){
						if(xmlHttp.readyState==4&&xmlHttp.status==200){
							var networkText = xmlHttp.responseText;
							var nw = eval("("+networkText+")");
							for(var n=1;n<=neType;n++){
								var network = document.getElementById("network"+n);
								network.options.length=1;        			        			
								for(var i=0;i<nw.length;i++){
									var networkId = nw[i].networkId;
									var networkName = nw[i].networkName;
									network.options.add(new Option(networkName,networkId));
								}																																																																																																																								
							}
						}
					}										
				}		
			}
		}
	}
}





	</script>
</html>