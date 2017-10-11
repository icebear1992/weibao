<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<!--IE渲染        -->
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width, initial-sacale=1"/>
		<title>中国电信安徽分公司维保管理系统</title>
		<!--描述：载入Bootstrap-->
		<script src="../js/echart/echarts.js"></script>
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
			.d1-right{
				float: right;
				margin-right: 80px;
			}
			#title-bule{
				color:#00F;
				font-size: x-large;
				margin-left: 80px;
			}
			#grszbule{
				color: #204D74;
			}
			#redspan{
				background-color: red;
				color: white;
			}
			#footer{
				text-align: center;
			}
			#bottomdiv{
				position: relative;
				bottom: -20px;
			}
			.chart2{
			position: relative;
				bottom:110px;
			}
			.chart3{
			position: relative;
				bottom:110px;
			}
			.word{
			position: relative;
				bottom:35px;
			}
			#leftdown{
			position: relative;
				bottom:15px;
			}
		</style>
	</head>
	<body onload="getTrend();judge()">
		<header>
			<div class="navbar navbar-default">
				<div class="navbar-header">
					<a href="#" class="navbar-brand" id="title-bule">中国电信安徽分公司维保管理系统 v1.0</a>
				</div>
				<div class="d1-right">
					<ul class="nav navbar-nav">
						<li style="width: 240px;">
							<marquee><br />
								<a href="<c:url value="/user/Pendings"/>" style="color: red;font-size: 15px;">当前未点评记录有<c:out value="${input.nonDealed}"/>条</a>
							</marquee>
						</li>
						<li>
							<a href="#" style="">欢迎,<c:out value="${input.name}"/></a>
						</li>
						<li>
							<a href='<c:url value="/man/Main"/>' style="font-size: large;">首&nbsp;页</a>
						</li>
						<li>
							<a href="<c:url value="/user/Pendings"/>" style="font-size: large;">审&nbsp;核&nbsp;<span class="badge" id="redspan">${input.nonDealed}</span></a>
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
			
		<div class="container">
			<div class="col-lg-3">
			<br /><br />
				<div class="panel panel-default">
					<div class="panel-heading">
					
						<h4>维保综合统计表</h4>
					</div>
					<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
					<div class="panel-body" style="font-size: 16px;">
						<form action="" method="post">
							
						
						专&nbsp;业&nbsp;:&nbsp;<select name="network" id="network" onchange="getNE()">
						<option value="0">--请选择专业--</option>
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
						<br /><br />
						区&nbsp;域&nbsp;:&nbsp;<select name="region" id="region">
							<option value="0">--请选择区域--</option>
							<c:forEach var="region" items="${input.regionList}" varStatus="status">
									<option value=<c:out value="${region.regionId}"/>>
										<c:out value="${region.regionName}" />
									</option>
								</c:forEach>
						</select>
						<br /><br />
						厂&nbsp;家&nbsp;:&nbsp;<select name="manu" id="manu">
							<option value="0">--请选择厂家--</option>
							<c:forEach var="mf" items="${input.mfList}" varStatus="status">
					         <option value=<c:out value="${mf.manufacturerId}"/>>
					          <c:out value="${mf.manufacturerName}" />
					         </option>
					        </c:forEach>
						</select>
						<br /><br />
						
						
						<button type="button" class="btn btn-primary" onclick="getTrend()">
								<span class="glyphicon glyphicon-search">展示图表</span>
							</button>
						</form>
					</div>
				</div>
			</div>
			<div class="col-lg-9">
						<div id="main" style="height:350px;width: 900px;"></div>
					</div>
				
			</div>
			
		
		<!--
        	作者：offline
        	时间：2017-06-03
        	描述：下行分三
        -->
			<div class="container" id="bottomdiv">
					<div class="col-lg-3 col-md-6">
                    <div class="panel panel-default" id="leftdown">
                        <div class="panel-heading">
                            <h4>未完成情况</h4>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                        
                            <div class="panel-footer">
                                
                                <div class="row">
                               <div class="col-xs-4">
                                     <a href="#"><h1><c:out value="${input.nonCompletedFault}"/></h1>
                                    <span style="font-size:15px">处理故障</span></a>
                                </div>
                                 <div class="col-xs-4 ">
                                   <a href='<c:url value="/man/NonFinishHM?finish=0"/>'><h1><c:out value="${input.nonCompletedHm}"/></h1>
                                    <span style="font-size:15px">返修备件</span></a>
                                </div>
                                <div class="col-xs-4 ">
                                     <a href="#"><h1><c:out value="${input.nonCompletedDanger}"/></h1>
                                    <span style="font-size:15px">隐患整治</span></a>
                                </div>
                            </div>
                            </div>
                        </a>
                    </div>
                </div>
					</ul>
				</div>
				<div class="col-lg-1" >
				</div>
				<div class="col-lg-4">
					<h4 class="word">当月维保类型分布图</h4>
						<div id="main1" style="height:330px;width: 385px;" class="chart2"></div>
				</div>
				<div class="col-lg-4">
					<h4 class="word">当月维保事件分布图</h4>
						<div id="main2" style="height:330px;width: 375px;" class="chart3"></div>
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
		<!--底部通式 -->
	</body>
<script>
		var xmlHttp = null;
        	if(window.XMLHttpRequest){
        		xmlHttp = new XMLHttpRequest();
        	}else{
        		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        	}
        var url=null;
        function getTrend(){
			url = '<c:url value="/man/GetTrend"/>';
			xmlHttp.open("post",url,true);
        		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        		var network = document.getElementById("network").value;
        		var region = document.getElementById("region").value;
        		var manu = document.getElementById("manu").value;
        		xmlHttp.send("network="+network+"&region=" + region+"&manu=" + manu);
        		xmlHttp.onreadystatechange=setTrend;
		}
        function setTrend(){
        	if(xmlHttp.readyState==4&&xmlHttp.status==200){
        		var trendContactText = xmlHttp.responseText;
    			var trend = eval("("+trendContactText+")");
    			var serResultSatisfaction = new Array();
    			var planCount = new Array();
    			var hmCount = new Array();
    			var hmSatisfaction = new Array();
    			var faultCount = new Array();
    			var time = new Array();
    			
    			for(var i=0;i<trend.length;i++){
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
         require([ 'echarts', 'echarts/chart/bar','echarts/chart/line','echarts/chart/pie','echarts/theme/macarons' // 使用柱状图就加载bar模块，按需加载
        	        ], 
        	function (ec,theme) {
                // 基于准备好的dom，初始化echarts图表
                
                var myChart = ec.init(document.getElementById('main'),'macarons'); 
                
                var option = {
                	    tooltip : {
                	        trigger: 'axis'
                	    },
                	    toolbox: {
                	        show : false,
                	        feature : {
                	            mark : {show: true},
                	            dataView : {show: true, readOnly: false},
                	            magicType: {show: true, type: ['line', 'bar']},
                	            restore : {show: true},
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    calculable : true,
                	    legend: {
                	        data:['服务满意度','方案支撑数','硬件返修数','故障查修数']
                	    },
                	    xAxis : [
                	        {
                	            type : 'category',
                	            data : [time[0],time[1],time[2],time[3],time[4],time[5]]
                	        }
                	    ],
                	    yAxis : [
                	        {
                	            type : 'value',
                	            name : '维保事件',
                	            axisLabel : {
                	                formatter: '{value} 次'
                	            }
                	        },
                	        {
                	            type : 'value',
                	            name : '满意度',
                	            axisLabel : {
                	                formatter: '{value} 分'
                	            }
                	        }
                	    ],
                	    series : [

                	        {
                	                            "name":"服务满意度",
                	                            "type":"line",
                	                            "yAxisIndex": 1,
                	                            "data":[serResultSatisfaction[0],serResultSatisfaction[1],serResultSatisfaction[2],serResultSatisfaction[3],serResultSatisfaction[4],serResultSatisfaction[5]]
                	                        },{
                	                            "name":"方案支撑数",
                	                            "type":"bar",
                	                            "data":[planCount[0],planCount[1],planCount[2],planCount[3],planCount[4],planCount[5]]
                	                        },{
                	                            "name":"硬件返修数",
                	                            "type":"bar",
                	                            "data":[hmCount[0],hmCount[1],hmCount[2],hmCount[3],hmCount[4],hmCount[5]]
                	                        },{
                	                            "name":"故障查修数",
                	                            "type":"bar",
                	                            "data":[faultCount[0],faultCount[1],faultCount[2],faultCount[3],faultCount[4],faultCount[5]]
                	                        }
                	    ]
                	};
                // 为echarts对象加载数据 
                myChart.setOption(option); 
                
                
                var myChart1 = ec.init(document.getElementById('main1'),'macarons');
                
                var data=[];
                var series=[];
                <c:forEach items="${input.serTypeList}" var="ser">
                	var map={};
                	map["value"]="${ser.total}";
                	map["name"]="${ser.demandName}${ser.total}";
                    series.push(map);
                </c:forEach>    	

                var option1 = {
                	    title : {
                	        text: '',
                	        subtext: '',
                	        x:'center'
                	    },
                	    tooltip : {
                	        trigger: 'item',
                	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                	    },
                	    legend: {
                	        orient : 'vertical',
                	        x : 'left',
                	        data: data
                	    },
                	    toolbox: {
                	        show : false,
                	        feature : {
                	            mark : {show: false},
                	            dataView : {show: true, readOnly: false},
                	            magicType : {
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
                	            restore : {show: true},
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    calculable : true,
                	    series : [
                	        {
                	            name:'维保事件',
                	            type:'pie',
                	            radius : '55%',
                	            center: ['50%', '60%'],
                	            data: series
                	        }
                	    ]
                	};
                	                    
                // 为echarts对象加载数据 
                myChart1.setOption(option1) ;
                
				var myChart2 = ec.init(document.getElementById('main2'),'macarons');
                
                var data=[];
                var series=[];
                <c:forEach items="${input.eventList}" var="eve">
            	data.push("${eve.get(eventName)}");
            	var map={};
            	map["value"]="${eve.eventCount}";
            	map["name"]="${eve.eventName}${eve.eventCount}";
                series.push(map);
          		</c:forEach>   	

                var option2 = {
                	    title : {
                	        text: '',
                	        subtext: '',
                	        x:'center'
                	    },
                	    tooltip : {
                	        trigger: 'item',
                	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                	    },
                	    legend: {
                	        orient : 'vertical',
                	        x : 'left',
                	        data: data
                	    },
                	    toolbox: {
                	        show : false,
                	        feature : {
                	            mark : {show: false},
                	            dataView : {show: false, readOnly: false},
                	            magicType : {
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
                	            restore : {show: true},
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    calculable : true,
                	    series : [
                	        {
                	            name:'维保事件',
                	            type:'pie',
                	            radius : '55%',
                	            center: ['50%', '60%'],
                	            data: series
                	        }
                	    ]
                	};
                myChart2.setOption(option2) ;	                    
            }
        );
       }
      }
        function judge(){
    		if(<c:out value="${input.nonDealed}"/>==0){
    			document.getElementById("redspan").style.display="none";
    		}
    	}
        	    </script>
        	</body>
		
	<script type="text/javascript" src="../js/jquery-3.2.1.min.js" ></script>
	<script type="text/javascript" src="../js/bootstrap.min.js">
		
	</script>
</html>
