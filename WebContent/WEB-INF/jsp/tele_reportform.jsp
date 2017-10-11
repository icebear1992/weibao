<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>中国电信安徽分公司维保管理系统</title>

<link rel="stylesheet" href='<c:url value="/css/bootstrap.min.css"/>' />
<script type="text/ecmascript" src="../grid/jquery-1.11.0.min.js"></script>
<script type="text/ecmascript" src="../grid/grid.locale-cn.js"></script>
<script type="text/ecmascript" src="../grid/jquery.jqGrid.min.js"></script>
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
		</style>
	</head>

	<body onload="judge()">
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
							<a href='<c:url value="/man/Main"/>' style="font-size: large;">首&nbsp;页</a>
						</li>
						<li>
							<a href="<c:url value="/user/Pendings"/>" style="font-size: large;">审&nbsp;核&nbsp;<span class="badge" id="redspan"><c:out value="${input.nonDealed}"/></span></a>
						</li>
						<li>
							<a href="<c:url value="/man/SERPandect"/>" style="font-size: large;">查&nbsp;询</a>
						</li>
						<li>
							<a href="<c:url value="/man/SERPandect"/>" style="font-size: large;">报&nbsp;表</a>
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
				<div class="list-group" style="font-size: 14px;">
							<a href="#" class="list-group-item active">
								<h5 class="list-group-item-heading" align="center">
								<span class="glyphicon glyphicon-edit"></span>
									请选择报表类型
								</h5>
							</a>
							<a href='<c:url value="/man/MonthRegion"/>' class="list-group-item">
								<p class="list-group-item-text" align="center"  style="font-size: 18px;font-weight: bold;">
									分公司维保项目月报表
								</p>
							</a>
							<a href='<c:url value="/man/MonthManu"/>' class="list-group-item">
								<p class="list-group-item-text" align="center">
									厂家维保项目月报表
								</p>
							</a>
							<a href='<c:url value="/man/MonthMajor"/>' class="list-group-item">
								<p class="list-group-item-text" align="center">
									专业维保项目月报表
								</p>
							</a>
							<a href="#" class="list-group-item">
								<p class="list-group-item-text" align="center">
									百网元返修率报表
								</p>
							</a>
							<a href='<c:url value="/provider/AddHM"/>' class="list-group-item">
								<p class="list-group-item-text" align="center">
									百网元响应支撑报表
								</p>
							</a>
							<a href="#" class="list-group-item">
								<p class="list-group-item-text" align="center">
									厂家硬件返修月报表
								</p>
							</a>
						</div>
				</div>
				<div class="col-lg-10">
				<br/>
				<form action='<c:url value="/man/GetMonthRegion" />' method="post">
				开始年份:<select id="beginYear" name="beginYear">
			<option>==请选择开始年份==</option>
			<option value="2015">2015年</option>
			<option value="2016">2016年</option>
			<option value="2017">2017年</option>
			<option value="2018">2018年</option>
			<option value="2019">2019年</option>
			<option value="2020">2020年</option>
			<option value="2021">2021年</option>
			</select>  &nbsp;&nbsp;&nbsp;
			开始月份:<select id="beginMonth" name="beginMonth">
			<option>==请选择开始月份==</option>
			<option value="01">1月份</option>
			<option value="02">2月份</option>
			<option value="03">3月份</option>
			<option value="04">4月份</option>
			<option value="05">5月份</option>
			<option value="06">6月份</option>
			<option value="07">7月份</option>
			<option value="08">8月份</option>
			<option value="09">9月份</option>
			<option value="10">10月份</option>
			<option value="11">11月份</option>
			<option value="12">12月份</option>
			</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			结束年份:<select id="endYear" name="endYear">
			<option>==请选择结束年份==</option>
			<option value="2015">2015年</option>
			<option value="2016">2016年</option>
			<option value="2017">2017年</option>
			<option value="2018">2018年</option>
			<option value="2019">2019年</option>
			<option value="2020">2020年</option>
			<option value="2021">2021年</option>
			</select>  &nbsp;&nbsp;&nbsp;
			结束月份:<select id="endMonth" name="endMonth">
			<option>==请选择结束月份==</option>
			<option value="01">1月份</option>
			<option value="02">2月份</option>
			<option value="03">3月份</option>
			<option value="04">4月份</option>
			<option value="05">5月份</option>
			<option value="06">6月份</option>
			<option value="07">7月份</option>
			<option value="08">8月份</option>
			<option value="09">9月份</option>
			<option value="10">10月份</option>
			<option value="11">11月份</option>
			<option value="12">12月份</option>
			</select>&nbsp;&nbsp;
			<button onclick="" id="query_btn" class="btn btn-primary"  type="button" style="width:65px;height:30px;text-align:center;font-size: 12.5px" >查&nbsp;&nbsp;询</button>
				  <button onclick="" id="sheet_btn" class="btn btn-primary"  type="submit" style="width:80px;height:30px;text-align:center;font-size: 12.5px">导出Excel</button>
				 </form>
			<br/>
		
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
	$(function(){
        $("#jqGrid").jqGrid({
          	url:'<c:url value="/man/GetMonthRegion"/>',
            mtype: "GET",
            datatype: "json",
            colModel: [
            	{ label:'月份', name: 'TIME', key: true, width: 70 },
                { label: '区域', name: 'REGION_NAME', width: 60 },
                { label: '驻点服务人数', name: 'SERPERSONNEL', width: 90 },
                { label: '巡检覆盖设备', name: 'TOTALEQUIPMENT', width: 90 },
                { label:'巡检覆盖业务节点', name: 'TOTALENGINERROOM', width: 120 },
                { label:'培训时长*人数', name: 'TRAININGDURATION', width: 95 },
                { label:'方案咨询数', name: 'CONSULTATION', width: 80 },
                { label:'完结处理故障', name: 'COMPLETION', width: 90 },
                { label:'未完结故障', name: 'INCOMPLETION', width: 80 },
                { label:'硬件返修批次(增)', name: 'SSUM', width: 114 },
                { label:'硬件返修批次(存)', name: 'INSSUM', width: 114 },
                { label:'硬件返修平均历时', name: 'AVGTIME', width: 115 },
                { label:'紧急备件服务', name: 'SPRCOUNT', width: 95	 },
            ],
            altRows:true,
			viewrecords: true,
			width: 1245,
            height: 370,
            rowNum: 10,
            loadonce: true,
            pager: "#jqGridPager"
        });
    });
	
	$("#query_btn").click(function() {
		var beginYear = $("#beginYear").val();
		var beginMonth = $("#beginMonth").val();
		var endYear = $("#endYear").val();
		var endMonth = $("#endMonth").val();
		var data = {
			'beginYear' : beginYear,
			'beginMonth' : beginMonth,
			'endYear' : endYear,
			'endMonth' : endMonth,
		};
		
		$("#jqGrid").jqGrid({
			postData : data,
			url : '<c:url value="/man/GetMonthRegion"/>',
			mtype: "GET",
			datatype : "json",
			colModel : [ 
				{ label:'月份', name: 'TIME', key: true, width: 60 },
                { label: '区域', name: 'REGION_NAME', width: 60 },
	                { label: '驻点服务人数', name: 'SERPERSONNEL', width: 90 },
	                { label: '巡检覆盖设备', name: 'TOTALEQUIPMENT', width: 90 },
	                { label:'巡检覆盖业务节点', name: 'TOTALENGINERROOM', width: 110 },
	                { label:'培训时长*人数', name: 'TRAININGDURATION', width: 100 },
	                { label:'方案咨询数', name: 'CONSULTATION', width: 90 },
	                { label:'完结处理故障', name: 'COMPLETION', width: 90 },
	                { label:'未完结故障', name: 'INCOMPLETION', width: 80 },
	                { label:'硬件返修批次(增)', name: 'SSUM', width: 114 },
	                { label:'硬件返修批次(存)', name: 'INSSUM', width: 114 },
	                { label:'硬件返修平均历时', name: 'AVGTIME', width: 110 },
	                { label:'紧急备件服务', name: 'SPRCOUNT', width: 85	 },
			],
			altRows:true,
			viewrecords: true,
			width: 1210,
            height: 370,
            rowNum: 10,
            loadonce: true,
            pager: "#jqGridPager"
		});
		jQuery('#jqGrid').jqGrid('clearGridData');
		$("#jqGrid").jqGrid('setGridParam',{
			postData : data,
			url : '<c:url value="/man/GetMonthRegion"/>',
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