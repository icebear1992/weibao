package com.telecom.weibao.maintanceTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.telecom.weibao.dac.*;
import com.telecom.weibao.entity.*;


public class SerImportDac {

//从列表生成维保信息：
//列表内容：子区域，网元，开始时间，结束时间，服务有效时长，处理人，联系方式，服务支撑方式，远程服务时长，现场服务时长，需求类别，解决方案，问题及需求等级，备注信息，实际创建人，厂家录入人，审核人，创建时间，流程状态，
//列表示例："省NOC","ONU_型号b","2015/01/02 04:17:27","2015/01/04 02:05:52","16","王烈","133805501131","现场","8","8","故障查修","补丁升级","3","无","阮绍祺","殷子昂","阮绍祺","2015/01/02 09:17:27","待复审"
	public static void main(String[] arg){
		
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("com/telecom/weibao/maintanceTool/applicationContext.xml");
		JdbcTemplate jdbcTemplate = (JdbcTemplate) ac.getBean("jdbcTemplate");
		Dac.setJdbcTemplate(jdbcTemplate); 
		
/**	此处为增加服务支撑响应信息的测试字段
 * public static SER ListToSER(List<String> list)
 * 输入数据序列中无需包括事件id，适用于单件维保事务导入

		List<String> inpList = Arrays.asList(new String[]{
		"省NOC","ONU_型号b","2015/01/02 04:17:27","2015/01/04 02:05:52","16",
		"王烈","133805501131","现场","8","8","故障查修","补丁升级","3","无","阮绍祺",
		"殷子昂","阮绍祺","2015/01/02 09:17:27","待复审"});
		
		SER ser = ListToSER(inpList);
		SERDac.addSER(ser);

 * 
 * */
/**	自己定义序列号的导入示例，存在问题，影响数据库序列的增加
		List<String> inpList = new ArrayList(Arrays.asList(new String[]{"40001",
		"省NOC","ONU_型号b","2015/01/02 04:17:27","2015/01/04 02:05:52","16",
		"王烈","133805501131","现场","8","8","故障查修","补丁升级","3","无","阮绍祺",
		"殷子昂","阮绍祺","2015/01/02 09:17:27","待复审"}));
		
		SER ser = ListToSER_withId(inpList);
		SERDac.addSER(ser);
 */		
		
/**
 * 批量导入评价信息	,导入信息需根据sers表中的ser_id
 * 输入的示例"10001","方英达","5","5","无","2016/10/11 12:00:00"
 */
		List<String> inpList = new ArrayList(Arrays.asList(new String[]{"10001",
				"方英达","5","5","无","2016/10/11 12:00:00"}));
		
		SERReview Sr = listToSr(inpList);
		insertSERR(Sr);
	
	}

//输入数据序列中包括事件id，适用于批量维保事务导入，ID可用于关联评价信息的录入	
	public static SER ListToSER_withId(List<String> list){
		if (list.size()!= 20) return null;
		String serIdStr = list.get(0);
		System.out.println(serIdStr);
		list.remove(0);
		
		SER SEROut = ListToSER(list);
		
		SEROut.setSerId(Long.valueOf(serIdStr));
		
		return SEROut;
	}
	
	
//输入数据序列中无需包括事件id，适用于单件维保事务导入
	public static SER ListToSER(List<String> list){
		
		if (list.size()!= 19) return null;
		
		String subregionName = list.get(0);
		String nemodelName = list.get(1);
		String startTimeStr = list.get(2);
		String endTimeStr = list.get(3);
		String totalDuration = list.get(4);
		String MHName = list.get(5);//主要处理人可能为无系统权限的人
		String MHphone = list.get(6);
		String supportMethod = list.get(7);
		String TelDur = list.get(8);
		String FedDur = list.get(9);
		String demandInfo = list.get(10);
		String solution = list.get(11);
		String demandDegree = list.get(12);
		String remarkInfo = list.get(13);
		String createrName = list.get(14);
		String auditorName = list.get(16);
		String mpName = list.get(15);
		String createTime = list.get(17);
		String curProcessStr = list.get(18);
		
		Integer currentProcess = -1;
		if (curProcessStr.equals("待审核")) currentProcess =1;
		if (curProcessStr.equals("待复审")) currentProcess =2;
		if (curProcessStr.equals("归档")) currentProcess =3;

		
		SER SEROutput = new SER();
		//System.out.println(getNeModelByName("ONU_型号b").getNeModelName());
				
		if (createrName.equals(mpName)){
			SEROutput.setCreaterId(MpImportDac.GetMpByName(createrName).getMpId());
		}
		if (!createrName.equals(mpName)){
			SEROutput.setCreaterId(TpImportDac.GetTelecomPersonnelByName(createrName).getTpId());
		}
			
		SEROutput.setSerId(0);
		SEROutput.setSubRegion(TpImportDac.GetSubRegionByName(subregionName));
		SEROutput.setNeModel(getNeModelByName(nemodelName));
		SEROutput.setStartTime(dateStr2TimeStamp(startTimeStr,"yyyy/MM/dd HH:mm:ss"));
		SEROutput.setEndTime(dateStr2TimeStamp(endTimeStr,"yyyy/MM/dd HH:mm:ss"));
		SEROutput.setTotalDuration(Double.valueOf(totalDuration));
		SEROutput.setSupportMethod(supportMethod);
		SEROutput.setContactinfo(MHphone);
		SEROutput.setMajorHandler(MHName);
		SEROutput.setTelnetServDuration(Double.valueOf(TelDur));
		SEROutput.setFieldServDuration(Double.valueOf(FedDur));
		SEROutput.setCategory(getDemandCategoryByName(demandInfo));
		SEROutput.setMp(MpImportDac.GetMpByName(mpName));
		SEROutput.setCurrentProcess(currentProcess);
		SEROutput.setDemandInfo(demandInfo);
		SEROutput.setSolution(solution);
		SEROutput.setDemandDegree(Integer.valueOf(demandDegree));
		SEROutput.setRemarksInfo(remarkInfo);
		SEROutput.setAuditor(TpImportDac.GetTelecomPersonnelByName(auditorName));
		SEROutput.setCreatTime(dateStr2TimeStamp(createTime,"yyyy/MM/dd HH:mm:ss"));
		
		return SEROutput;
	}
	

	public static NEModel getNeModelByName(String nemodelName){
		String sql="SELECT nemodel_id as neModelId, nemodel_name as neModelName, manufacturer_id,ne_id FROM nemodels "
				+ "where nemodel_name=?";
		List<NEModel> neModelList = Dac.getJdbcTemplate().query(sql,new Object[]{nemodelName} ,new BeanPropertyRowMapper<NEModel>(NEModel.class));
		if (neModelList.size() > 0) return neModelList.get(0);
		return null;	
	}
	
	public static DemandCategory getDemandCategoryByName(String demandInfo){
		String sql="SELECT demand_id,demand_name,inputtimelimit FROM demandcategories "
				+ "where demand_name=?";
		List<DemandCategory> dclList = Dac.getJdbcTemplate().query(sql,new Object[]{demandInfo} ,new BeanPropertyRowMapper<DemandCategory>(DemandCategory.class));
		if (dclList.size() > 0) return dclList.get(0);
		return null;	
	}
	
    public static Timestamp dateStr2TimeStamp(String date_str,String format){  
        try {  
            SimpleDateFormat sdf = new SimpleDateFormat(format);  
            return new Timestamp(sdf.parse(date_str).getTime());  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return new Timestamp(0);  
    }  
    
    
   
    public static SERReview listToSr(List<String> list){
    	
    	//输入的示例"10001","方英达","5","5","无","2016/10/11 12:00:00"
    	
    	if(list.size()< 6) return null;
    	
    	Timestamp review_time = dateStr2TimeStamp(list.get(5),"YYYY/MM/dd hh:mm:ss");
    	TelecomPersonnel tp = TpImportDac.GetTelecomPersonnelByName(list.get(1));
    	
    	Long serId= Long.valueOf(list.get(0));
    	Integer servicesatisfaction= Integer.valueOf(list.get(2));
    	Integer resultsatisfaction= Integer.valueOf(list.get(3));
    	String review_content = list.get(5);
    	
		SERReview serReview=new SERReview();
		
		serReview.getSer().setSerId(serId);
		if(tp != null)serReview.setReviewer(tp);
		serReview.setServiceSatisfaction(servicesatisfaction);
		serReview.setResultSatisfaction(resultsatisfaction);
		serReview.setReviewContent(review_content);
		serReview.setReviewTime(review_time);
		return serReview;
    	
    	
    }

    
    
	public static void insertSERR(SERReview sr) {
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try {
					String sql = "INSERT INTO serreviews (review_id,ser_id,reviewer_id,servicesatisfaction,resultsatisfaction,review_content,review_time) "
							+ "VALUES (rewseq.nextval,?,?,?,?,?,?)";
					Dac.getJdbcTemplate().update(sql, new Object[] { sr.getSer().getSerId(), sr.getReviewer().getTpId(),
							sr.getServiceSatisfaction(), sr.getResultSatisfaction(), sr.getReviewContent(), sr.getReviewTime() });
				} catch (Exception e) {
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}
	
}
