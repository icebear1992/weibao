<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>中国电信安徽分公司维保管理系统</title>
<link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css"/>
<link rel="stylesheet" href='<c:url value="/css/bootstrap.min.css"/>' />
		<script src="../date/jquery.min.js"></script>
<script src="../date/jquery-ui.min.js"></script>
<script src="../date/jquery-ui-timepicker-addon.min.js"></script>
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
<link rel="stylesheet" type="text/css" href="../date/jquery-ui.min.css"/>
<link rel="stylesheet" href='<c:url value="/date/jquery-ui.min.css"/>' />
<script type="text/javascript" src="../date/jquery-ui-timepicker-zh-CN.js"></script>
<script type="text/ecmascript" src="../grid/jquery-1.11.0.min.js"></script>
<script type="text/ecmascript" src="../grid/grid.locale-cn.js"></script>
<script type="text/ecmascript" src="../grid/jquery.jqGrid.min.js"></script>
<link href="../date/jquery-ui-timepicker-addon.min.css" rel="stylesheet" />
<link href='<c:url value="/date/jquery-ui-timepicker-addon.min.css"/>' rel="stylesheet" />
<script>
		$.jgrid.defaults.width = 780;
		$.jgrid.defaults.styleUI = 'Bootstrap';
</script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" media="screen" href="../grid/ui.jqgrid-bootstrap.css" />
		<style type="text/css">
		.ui-jqgrid-sortable {font-size:12px;} 
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
			}
select::-ms-expand { display: none; }
		</style>
	</head>

	<body onload="judge();getunfinish()">
		<header>
			<div class="navbar navbar-default">
				<div class="navbar-header">
					<a href="#" class="navbar-brand" id="title-bule">中国电信安徽分公司维保管理系统 v1.0</a>
				</div>
				<div class="d1-right">
					<ul class="nav navbar-nav">
						<li style="width: 240px;">
							<marquee><br />
								<a href="<c:url value="/user/Pendings"/>" style="color: red;font-size: 15px;">当前有<c:out value="${input.nonDealed}"/>条记录未点评</a>
							</marquee>
						</li>
						<li>
							<a href="#" style="">欢迎,<c:out value="${input.name}"/></a>
						</li>
						<li>
							<a href="<c:url value="/man/Main"/>" style="font-size: large;">首&nbsp;页</a>
						</li>
						<li>
							<a href="<c:url value="/user/Pendings"/>" style="font-size: large;">审&nbsp;核&nbsp;<span class="badge" id="redspan">6</span></a>
						</li>
						<li>
							<a href="<c:url value="/man/SERPandect"/>" style="font-size: large;">查&nbsp;询</a>
						</li>
						<li>
							<a href="<c:url value="/man/MonthRegion"/>" style="font-size: large;">报&nbsp;表</a>
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
				<div class="col-lg-2">
					<br />
					<div class="list-group" style="font-size: 15px;">
							<a href="#" class="list-group-item active">
								<h5 class="list-group-item-heading" align="center">
								<span class="glyphicon glyphicon-edit"></span>
									请选择维保类型
								</h5>
							</a>
							<a href="<c:url value="/man/SERPandect"/>" class="list-group-item">
								<p class="list-group-item-text" align="center" >
									服务相应支撑记录
								</p>
							</a>
							<a href="#" class="list-group-item">
								<p class="list-group-item-text" align="center" style="font-size: 17px;font-weight: bold;">
									备件返修记录
								</p>
							</a>
							<a href="#" class="list-group-item">
								<p class="list-group-item-text" align="center">
									紧急备件替换
								</p>
							</a>
							<a href="#" class="list-group-item">
								<p class="list-group-item-text" align="center">
									驻点服务
								</p>
							</a>
							<a href='<c:url value="/provider/AddHM"/>' class="list-group-item">
								<p class="list-group-item-text" align="center">
									巡检
								</p>
							</a>
							<a href="#" class="list-group-item">
								<p class="list-group-item-text" align="center">
									培训
								</p>
							</a>
						</div>
				</div>
				<div class="col-lg-10">
				<form action='<c:url value="/man/GetHMPandect"/>' method="post">
				<div style="text-align: left;line-height:89%;margin:12px 0 5px 0" class="chose">
			<br />
				综合查询: <input type="text" name="comquery" id="comquery" value="" size="50"/>
				<br/><br/>
							分&nbsp;公&nbsp;司&nbsp;:
							<select class="combsobox" name="region" onchange="getSubRegions()" id="region">
								<option value="0">-选择分公司-</option>
								<c:forEach var="region" items="${input.regionList}" varStatus="status">
									<option value=<c:out value="${region.regionId}"/>>
										<c:out value="${region.regionName}" />
									</option>
								</c:forEach>
							</select>
							&nbsp;&nbsp;区域:&nbsp;<select class="combobox" name="subregion" id="subregion" >
						<option value="0">-选择区域-</option>
					</select>
							&nbsp;&nbsp;专业:&nbsp;<select class="combobox" name="major" id="major" style="align-content: center;">
								  <option value="0">-选择专业-</option>
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
							&nbsp;&nbsp;厂家:&nbsp;<select class="combobox" name="manufacturer" id="manufacturer">
								<option value="0">-选择厂家-</option>
								<c:forEach var="mf" items="${input.mfList}" varStatus="status">
					         <option value=<c:out value="${mf.manufacturerId}"/>>
					          <c:out value="${mf.manufacturerName}" />
					         </option>
					        </c:forEach>
							</select>
							<br /><br />
							开始时间:  <input id="defult1" name="launchTime" class="launchTime" />&nbsp;&nbsp;
							结束时间:  <input id="defult2" name="completionTime" class="completionTime" />&nbsp;&nbsp;
							相关人员：<input type="text" name="person" id="person"/>
				  <button onclick="" id="query_btn" class="btn btn-primary"  type="button" style="width:65px;height:30px;text-align:center;font-size: 12.5px" >查&nbsp;&nbsp;询</button>
				  <button onclick="" id="sheet_btn" class="btn btn-primary"  type="submit" style="width:80px;height:30px;text-align:center;font-size: 12.5px">导出Excel</button>
						</div>
						<br />
			</form>
			
					<table id="jqGrid"></table>
   					 <div id="jqGridPager"></div>
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
	<script type="text/javascript">
		var xmlHttp = null;
        	if(window.XMLHttpRequest){
        		xmlHttp = new XMLHttpRequest();
        	}else{
        		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        	}
        var url=null;
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
/*         function getunfinish(){
			url = '<c:url value="/man/GetNonFinishHM"/>';
			xmlHttp.open("post",url,true);
			var value = 1;
        		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        		xmlHttp.send("var=" + value);
        		xmlHttp.onreadystatechange=setunfinish;
		}
        
        function setunfinish(){
        		if(xmlHttp.readyState==4&&xmlHttp.status==200){
        			var unfinishText = xmlHttp.responseText;
        			var un = eval("("+unfinishText+")");
        			for(var i=0;i<un.length;i++){
        				var subRegionName = sr[i].subRegionName;
        				var subRegionId = sr[i].subRegionId;
        				subRegion.options.add(new Option(subRegionName,subRegionId))
        			}
        		}
        	} */	
        	$(function(){
                $("#jqGrid").jqGrid({
                  	url:'<c:url value="/man/GetNonFinishHM"/>',
                    mtype: "GET",
                    datatype: "json",
                    colModel: [
                    	{
                    	label : '区域',
						name : 'REGION_NAME',
						key : true,
						width : 90
					}, {
						label : '子区域',
						name : 'SUBREGION_NAME',
						width : 90
					}, {
						label : '专业',
						name : 'MAJOR_NAME',
						width : 90
					}, {
						label : '厂家',
						name : 'MANUFACTURER_NAME',
						width : 75
					},{
						label : '网元名称',
						name : 'NE_NAME',
						width : 170
					},{
						label : '网元型号',
						name : 'NEMODEL_NAME',
						width : 230
					}
					, {
						label : '发起时间',
						name : 'LAUNCH_TIME',
						width : 200
					}, {
						label : '完成时间',
						name : 'COMPLETION_TIME',
						width : 200
					},  {
						label : '主要处理人',
						name : 'MP_NAME',
						width : 110
					}, {
						label : '审核人',
						name : 'TP_NAME',
						width : 85
					}, {
						label : '总数量',
						name : 'TOTAL_NUM',
						width : 80
					},{
						label : '修复数量',
						name : 'REPAIR_NUM',
						width : 90
					},{
						label : '满意度',
						name : 'SATISFACTION',
						width : 80
					},
                    ],
                    altRows:true,
        			viewrecords: true,
        			width: 1210,
                    height: 370,
                    rowNum: 10,
                    loadonce: true,
                    pager: "#jqGridPager"
                });
            });
        
			$("#query_btn").click(function() {
				var comquery = $("#comquery").val();
				var region = $("#region").val();
				var subregion = $("#subregion").val();
				var major = $("#major").val();
				var manufacturer = $("#manufacturer").val();
				var launchTime = $(".launchTime").val();
				var completionTime = $(".completionTime").val();
				var person = $("#person").val();
				var data = {
					'comquery' : comquery,
					'region' : region,
					'subregion' : subregion,
					'major' : major,
					'manufacturer' : manufacturer,
					'launchTime' : launchTime,
					'completionTime' : completionTime,
					'person' : person
				};
				
				$("#jqGrid").jqGrid({
					postData : data,
					url : '<c:url value="/man/GetHMPandect"/>',
					mtype: "GET",
					datatype : "json",
					colModel : [ {
						label : '区域',
						name : 'REGION_NAME',
						key : true,
						width : 90
					}, {
						label : '子区域',
						name : 'SUBREGION_NAME',
						width : 90
					}, {
						label : '专业',
						name : 'MAJOR_NAME',
						width : 90
					}, {
						label : '厂家',
						name : 'MANUFACTURER_NAME',
						width : 75
					},{
						label : '网元名称',
						name : 'NE_NAME',
						width : 170
					},{
						label : '网元型号',
						name : 'NEMODEL_NAME',
						width : 230
					}
					, {
						label : '发起时间',
						name : 'LAUNCH_TIME',
						width : 200
					}, {
						label : '完成时间',
						name : 'COMPLETION_TIME',
						width : 200
					},  {
						label : '主要处理人',
						name : 'MP_NAME',
						width : 110
					}, {
						label : '审核人',
						name : 'TP_NAME',
						width : 85
					}, {
						label : '总数量',
						name : 'TOTAL_NUM',
						width : 80
					},{
						label : '修复数量',
						name : 'REPAIR_NUM',
						width : 90
					},{
						label : '满意度',
						name : 'SATISFACTION',
						width : 80
					},
					],
					altRows:true,
					viewrecords : true,
					width : 1190,
					height : 300,
					rowNum : 10,
					loadonce : true,
					pager : "#jqGridPager"
				});
				$("#jqGrid").jqGrid('setGridParam',{
					postData : data,
					url : '<c:url value="/man/GetHMPandect"/>',
					mtype: "GET",
					datatype : "json",
				}).trigger('reloadGrid');
			});
			function judge(){
	    		if(<c:out value="${input.nonDealed}"/>==0){
	    			document.getElementById("redspan").style.display="none";
	    		}
	    	}
	</script>
</html>