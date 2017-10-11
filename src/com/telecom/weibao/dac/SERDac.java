package com.telecom.weibao.dac;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.telecom.weibao.entity.ManufacturerPersonnel;
import com.telecom.weibao.entity.SER;
import com.telecom.weibao.entity.TelecomPersonnel;
import com.telecom.weibao.statistics.SERTypeStatistics;

public class SERDac {
	
	// SER基本情况
	static class SERRowMapper implements RowMapper<SER>{
		@Override
		public SER mapRow(ResultSet rs, int rowNum) throws SQLException {
			SER ser=new SER();
			ser.setSerId(rs.getLong("ser_id"));
			ser.getSubRegion().setSubRegionId(rs.getLong("subregion_id"));
			ser.getSubRegion().setSubRegionName(rs.getString("subregion_name"));
			ser.getNeModel().getNe().getNetwork().getMajor().setMajorId(rs.getLong("major_id"));
			ser.getNeModel().getNe().getNetwork().getMajor().setMajorName(rs.getString("major_name"));
			ser.getNeModel().setNeModelId(rs.getLong("nemodel_id"));
			ser.getNeModel().setNeModelName(rs.getString("nemodel_name"));
			ser.setStartTime(rs.getTimestamp("start_time"));
			ser.setEndTime(rs.getTimestamp("end_time"));
			ser.setTotalDuration(rs.getDouble("total_duration"));
			ser.setMajorHandler(rs.getString("majorhandler"));
			ser.setSupportMethod(rs.getString("supportmethod"));
			ser.setContactinfo(rs.getString("contactinfo"));
			ser.setTelnetServDuration(rs.getDouble("telnetserv_duration"));
			ser.setFieldServDuration(rs.getDouble("fieldserv_duration"));
			ser.getCategory().setDemandId(rs.getLong("demand_id"));
			ser.getCategory().setDemandName(rs.getString("demand_name"));
			ser.setDemandInfo(rs.getString("demand_info"));
			ser.setSolution(rs.getString("solution"));
			ser.setDemandDegree(rs.getInt("demand_degree"));
			ser.setRemarksInfo(rs.getString("remarksinfo"));
			ser.getAuditor().setTpId(rs.getLong("auditor_id"));
			ser.getAuditor().setTpName(rs.getString("tp_name"));
			ser.getMp().setMpId(rs.getLong("mp_id"));
			ser.getMp().setMpName(rs.getString("mp_name"));
			ser.getMp().getManufacturer().setManufacturerId(rs.getLong("manufacturer_id"));
			ser.getMp().getManufacturer().setManufacturerName(rs.getString("manufacturer_name"));
			ser.setCreatTime(rs.getTimestamp("creat_time"));
			ser.setCreaterId(rs.getLong("creater_id"));
			ser.setCurrentProcess(rs.getInt("currentprocess"));
			ser.setTitle(rs.getString("title"));
			ser.getNeModel().getNe().setNeId(rs.getLong("ne_id"));
			ser.getNeModel().getNe().setNeName(rs.getString("ne_name"));
			ser.getNeModel().getNe().getNetwork().setNetworkId(rs.getLong("network_id"));
			ser.getNeModel().getNe().getNetwork().setNetworkName(rs.getString("network_name"));
			
			return ser;
		}		
	}
	
	static String sql1 = "SELECT s.title,s.ser_id,s.subregion_id,sr.subregion_name,m.major_id,m.major_name,s.nemodel_id,nm.nemodel_name,s.start_time,s.end_time,s.total_duration,s.majorhandler,"
			+ "s.supportmethod,s.contactinfo,s.telnetserv_duration,s.fieldserv_duration,s.demand_id,d.demand_name,s.demand_info,s.solution,"
			+ "s.demand_degree,s.remarksinfo,s.auditor_id,tp.tp_name,s.mp_id,mp.mp_name,mp.manufacturer_id,mf.manufacturer_name,s.creat_time,s.creater_id,s.currentprocess,ne.ne_Id,ne.ne_Name,nw.NETWORK_ID,nw.NETWORK_name "
			+ "FROM sers s,subregions sr,majors m,nemodels nm,nes ne,networks nw,demandcategories d,telecompersonnels tp,manufacturerpersonnels mp,manufacturers mf "
			+ "WHERE s.subregion_id=sr.subregion_id AND s.nemodel_id=nm.nemodel_id AND nm.ne_id=ne.ne_id AND ne.network_id=nw.network_id AND nw.major_id=m.major_id "
			+ "AND s.demand_id=d.demand_id AND s.auditor_id=tp.tp_id AND s.mp_id=mp.mp_id AND mp.manufacturer_id=mf.manufacturer_id";
	
	/*
	 * 根据ID获取SER的详情
	 */
	public static SER getSERDetailById(long serId){
		String sql = sql1+" AND s.ser_id=?";
		List<SER> serList=Dac.getJdbcTemplate().query(sql, new Object[]{serId},new SERRowMapper());
		if(serList.size()>0){
			serList.get(0).setSerReviewList(SERReviewDac.getSERReviewsBySERId(serId));
			return serList.get(0);
		}
		return null;
	}
	
	/*
	 * 获取电信人员待评价的SER 即当前环节为0，且审核人是自己的
	 */
	public static List<SER> getNonReviewedSERs(TelecomPersonnel tp) {
		String sql = sql1 + " AND s.currentprocess=0 AND s.auditor_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{tp.getTpId()},new SERRowMapper());
	}
	public static int getNonReviewedSERCount(TelecomPersonnel tp){
		String sql="SELECT count(*) FROM sers WHERE currentprocess=0 AND auditor_id=?";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{tp.getTpId()},Integer.class);
	}
	/*
	 * 获取电信人员待审核的SER 即当前环节是1，且审核人为自己的下属
	 */
	public static List<SER> getNonAuditedSERs(TelecomPersonnel tp){
		String sql = sql1 + " AND s.currentprocess=1 AND s.auditor_id in (SELECT tp_id FROM telecompersonnels WHERE tpmanager_id=?)";
		return Dac.getJdbcTemplate().query(sql, new Object[]{tp.getTpId()},new SERRowMapper());
	}
	public static int getNonVerifySERCount(TelecomPersonnel tp){
		String sql="SELECT count(*) FROM sers WHERE currentprocess=1 AND auditor_id in (SELECT tp_id FROM telecompersonnels WHERE tpmanager_id=?)";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{tp.getTpId()},Integer.class);
	}
	/*
	 * 与电信员工自身有关的，被退回的工单（其为点评人或审核人）
	 */
	public static List<SER> getRelateReturnedSERs(TelecomPersonnel tp){
		String sql=sql1+" AND s.currentprocess=-1 AND (s.creater_id=? OR s.auditor_id=?)";
		return Dac.getJdbcTemplate().query(sql, new Object[]{tp.getTpId(),tp.getTpId()},new SERRowMapper());
	}
	public static int getRelateReturnedSERcount(TelecomPersonnel tp){
		String sql="SELECT count(*) FROM SERs WHERE currentprocess=-1 AND (creater_id=? OR auditor_id=?)";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{tp.getTpId(),tp.getTpId()},Integer.class);
	}
	/*
	 * 与电信员工自身有关的，未完成的工单（其创建的或其要点评的）
	 */
	public static List<SER> getRelateUnCompletedSERs(TelecomPersonnel tp){
		String sql=sql1+" AND s.currentprocess=-2 AND (s.creater_id=? OR s.auditor_id=?)";
		return Dac.getJdbcTemplate().query(sql, new Object[]{tp.getTpId(),tp.getTpId()},new SERRowMapper());
	}
	public static int getRelateUnCompletedSERcount(TelecomPersonnel tp){
		String sql="SELECT count(*) FROM SERs WHERE currentprocess=-2 AND (creater_id=? OR auditor_id=?)";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{tp.getTpId(),tp.getTpId()},Integer.class);
	}
	
	
	/*
	 * 获取厂家人员待处理的SER，即当前环节为-1，且厂商人员是自己的
	 */
	public static List<SER> getNonDealedSERs(ManufacturerPersonnel mp){
		String sql = sql1 + " AND s.currentprocess=-1 AND s.mp_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{mp.getMpId()},new SERRowMapper());
	}
	public static int getNonDealedSERCount(ManufacturerPersonnel mp){
		String sql="SELECT count(*) FROM sers WHERE currentprocess=-1 AND mp_id=?";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{mp.getMpId()},Integer.class);
	}	
	/*
	 * 获取厂家人员待处理的SER，即当前环节为-2，且厂商人员是自己的
	 */
	public static int getNonFinSERCount(ManufacturerPersonnel mp){
		String sql="SELECT count(*) FROM sers WHERE currentprocess=-2 AND mp_id=?";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{mp.getMpId()},Integer.class);
	}
	
	/*
	 * 获取厂家已提交待点评的SER，即当前环节为0，且厂商人员是自己的
	 */
	public static List<SER> getNonReviewedSERs(ManufacturerPersonnel mp){
		String sql = sql1 + " AND s.currentprocess=0 AND s.mp_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{mp.getMpId()},new SERRowMapper());
	}
	public static int getNonReviewedSERCount(ManufacturerPersonnel mp){
		String sql="SELECT count(*) FROM sers WHERE currentprocess=0 AND mp_id=?";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{mp.getMpId()},Integer.class);
	}	
	/*
	 * 获取厂家已提交待点评的SER，即当前环节为1，且厂商人员是自己的
	 */
	public static List<SER> getNonAuditedSERs(ManufacturerPersonnel mp){
		String sql = sql1 + " AND s.currentprocess=1 AND s.mp_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{mp.getMpId()},new SERRowMapper());
	}
	public static int getNonAuditedSERCount(ManufacturerPersonnel mp){
		String sql="SELECT count(*) FROM sers WHERE currentprocess=1 AND mp_id=?";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{mp.getMpId()},Integer.class);
	}	
	/*
	 * 获取厂家已提交待点评的SER，即当前环节为-2，且厂商人员是自己的
	 */
	public static List<SER> getUnfinishedSERs(ManufacturerPersonnel mp){
		String sql = sql1 + " AND s.currentprocess=-2 AND s.mp_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{mp.getMpId()},new SERRowMapper());
	}
	
	
	
	
	/*
	 * 插入一条SER
	 */
	public static void addSER(SER ser) {
		String sql = "INSERT INTO sers (ser_id,subregion_id,nemodel_id,start_time,end_time,total_duration,majorhandler,supportmethod,contactinfo,"
				+ "telnetserv_duration,fieldserv_duration,demand_id,demand_info,solution,demand_degree,remarksinfo,creater_id,auditor_id,creat_time,mp_id,currentprocess,title) "
				+ "VALUES (rewseq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Dac.getJdbcTemplate().update(sql,new Object[]{
				ser.getSubRegion().getSubRegionId(),
				ser.getNeModel().getNeModelId(),
				ser.getStartTime(),ser.getEndTime(),ser.getTotalDuration(),
				ser.getMajorHandler(),ser.getSupportMethod(),ser.getContactinfo(),
				ser.getTelnetServDuration(),ser.getFieldServDuration(),
				ser.getCategory().getDemandId(),ser.getDemandInfo(),
				ser.getSolution(),ser.getDemandDegree(),ser.getRemarksInfo(),
				ser.getCreaterId(),ser.getAuditor().getTpId(),
				ser.getCreatTime(),ser.getMp().getMpId(),ser.getCurrentProcess(),
				ser.getTitle()
		});		
	}
	
	/*
	 * 修改一条SER
	 */
	public static void chgSER(SER ser){
		String sql = "UPDATE sers SET subregion_id=?,nemodel_id=?,start_time=?,end_time=?,total_duration=?,majorhandler=?,supportmethod=?,contactinfo=?,"
				+ "telnetserv_duration=?,fieldserv_duration=?,demand_id=?,demand_info=?,solution=?,demand_degree=?,remarksinfo=?,auditor_id=?,mp_id=?,currentprocess=?"
				+ "WHERE ser_id=?";
		Dac.getJdbcTemplate().update(sql,new Object[]{
				ser.getSubRegion().getSubRegionId(),
				ser.getNeModel().getNeModelId(),
				ser.getStartTime(),ser.getEndTime(),ser.getTotalDuration(),
				ser.getMajorHandler(),ser.getSupportMethod(),ser.getContactinfo(),
				ser.getTelnetServDuration(),ser.getFieldServDuration(),
				ser.getCategory().getDemandId(),ser.getDemandInfo(),
				ser.getSolution(),ser.getDemandDegree(),ser.getRemarksInfo(),
                ser.getAuditor().getTpId(),ser.getMp().getMpId(),ser.getCurrentProcess(),
                ser.getSerId()
		});		
	}
	
	/*
	 * 删除一条SER，需进行事务处理
	 */
	public static void delSER(long serId){
		//先删除点评，然后删除该记录
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try {
					SERReviewDac.delSERReviews(serId);
					String sql="DELETE sers WHERE ser_id=?";
					Dac.getJdbcTemplate().update(sql,new Object[]{serId});
				} catch (Exception e) {
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}
	/*
	 * 修改SER的当前环节
	 */
	public static boolean chgSERCurrentProcess(long serId,int currentprocess){
		String sql="UPDATE sers SET currentprocess=? WHERE ser_id=?";
		if(Dac.getJdbcTemplate().update(sql,new Object[]{currentprocess,serId})==1)
			return true;
		return false;
	}
	/*
	 * 查看一条SER记录是不是该厂家用户可以查看的，即厂商人员是他自己
	 */
	public static boolean hasShowAuthority(long serId,long mpId){
		String sql="SELECT count(*) FROM sers WHERE ser_id=? AND mp_id=?";
		if(Dac.getJdbcTemplate().queryForObject(sql, new Object[]{serId,mpId},Long.class)==0)
			return false;
		return true;
	}
	
	/*
	 * 查看一条SER记录是不是该厂家用户可以修改的，即厂商人员是他自己，且状态是-1的
	 */
	public static boolean hasChgAuthority(long serId,long mpId){
		String sql="SELECT count(*) FROM sers WHERE currentprocess=-1 AND ser_id=? AND mp_id=?";
		if(Dac.getJdbcTemplate().queryForObject(sql,new Object[]{serId,mpId},Long.class)==0)
			return false;
		return true;
	}
	
	/*
	 * 查看一条SER记录是不是厂家自己创建的，不是自己创建的不可删除，部分属性无法修改
	 */
	public static boolean isCreater(long serId,long mpId){
		String sql="SELECT count(*) FROM sers WHERE ser_id=? AND creater_id=?";
		if(Dac.getJdbcTemplate().queryForObject(sql,new Object[]{serId,mpId},Long.class)==0)
			return false;
		return true;
	}
	
	/*
	 * 查看一条SER记录是不是厂家自己应该处理的的，
	 */
	public static boolean isMpdo(long serId,long mpId){
		String sql="SELECT count(*) FROM sers WHERE ser_id=? AND mp_id=?";
		if(Dac.getJdbcTemplate().queryForObject(sql,new Object[]{serId,mpId},Long.class)==0){
			return false;
		}
		return true;
	}
	
	
	
	
	/*
	 * 获取未完成的服务支撑事件的数量，即结束时间小于开始时间的
	 */
	public static int getNonCompletedSERCount(String serDemandName) {
		String sql = "SELECT count(*) FROM sers s,demandcategories d WHERE s.demand_id=d.demand_id AND s.end_time<s.start_time AND d.demand_name LIKE '%' || ? || '%'";
		return Dac.getJdbcTemplate().queryForObject(sql,new Object[]{serDemandName},Integer.class);
	}
	/*
	 * 根据服务支撑名称获取未完成的服务支撑事件
	 */
	public static List<SER> getNonCompletedSERs(String serDemandName){
		String sql=sql1+" AND s.end_time<s.start_time AND d.demand_name LIKE '%' || ? || '%'";
		return Dac.getJdbcTemplate().query(sql, new Object[]{serDemandName},new SERRowMapper());
	}
	
	
	
	
	
	/*
	 * 根据区域、专业、厂家等获取SER服务满意度
	 * months参数意义：代表距离当前日期的月份数，0代表当前月份，1代表上个月，如二者都是0，代表当前月份的情况
	 */
	public static double getSERServiceSatisfaction(long regionId,long majorId,long manufacturerId,int beginMonth,int endMonth){
		String sql = "SELECT NVL(avg(servicesatisfaction),0) FROM sers s,subregions sr,nemodels nm,nes ne,networks nw,manufacturerpersonnels mp,"
				+ "(SELECT s1.ser_id,servicesatisfaction FROM serreviews s1,"
				+ "(SELECT ser_id,max(review_time) review_time FROM serreviews GROUP BY ser_id) s2 "
				+ "WHERE s1.ser_id=s2.ser_id AND s1.review_time=s2.review_time) ss "
				+ "WHERE s.ser_id=ss.ser_id AND s.subregion_id=sr.subregion_id AND s.mp_id=mp.mp_id "
				+ "AND s.nemodel_id=nm.nemodel_id AND nm.ne_id=ne.ne_id AND ne.network_id=nw.network_id";
		if (regionId > 0) {// 代表选择的不是省公司全部区域
			sql += " AND sr.region_id=?";
		} else {
			sql += " AND sr.region_id>?";
		}
		if (majorId > 0) {// 代表选择的是单个专业
			sql += " AND nw.major_id=?";
		} else {
			sql += " AND nw.major_id>?";
		}
		if (manufacturerId > 0) {// 代表选择的是单个厂家
			sql += " AND mp.manufacturer_id=?";
		} else {
			sql += " AND mp.manufacturer_id>?";
		}
		sql += " AND s.end_time<(select add_months(last_day(sysdate)+1,?) from dual)"
				+ " AND s.end_time>=(select add_months(last_day(sysdate)+1,?) from dual)";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{regionId,majorId,manufacturerId,0-endMonth,-1-beginMonth} ,Double.class);
	}
	
	/*
	 * 根据区域、专业、厂家等获取SER结果满意度
	 * months参数意义：代表距离当前日期的月份数，0代表当前月份，1代表上个月，如二者都是0，代表当前月份的情况
	 */
	public static double getSERResultSatisfaction(long regionId,long majorId,long manufacturerId,int beginMonth,int endMonth){
		String sql = "SELECT NVL(avg(resultsatisfaction),0) FROM sers s,subregions sr,nemodels nm,nes ne,networks nw,manufacturerpersonnels mp,"
				+ "(SELECT s1.ser_id,resultsatisfaction FROM serreviews s1,"
				+ "(SELECT ser_id,max(review_time) review_time FROM serreviews GROUP BY ser_id) s2 "
				+ "WHERE s1.ser_id=s2.ser_id AND s1.review_time=s2.review_time) ss "
				+ "WHERE s.ser_id=ss.ser_id AND s.subregion_id=sr.subregion_id AND s.mp_id=mp.mp_id "
				+ "AND s.nemodel_id=nm.nemodel_id AND nm.ne_id=ne.ne_id AND ne.network_id=nw.network_id";
		if (regionId > 0) {// 代表选择的不是省公司全部区域
			sql += " AND sr.region_id=?";
		} else {
			sql += " AND sr.region_id>?";
		}
		if (majorId > 0) {// 代表选择的是单个专业
			sql += " AND nw.major_id=?";
		} else {
			sql += " AND nw.major_id>?";
		}
		if (manufacturerId > 0) {// 代表选择的是单个厂家
			sql += " AND mp.manufacturer_id=?";
		} else {
			sql += " AND mp.manufacturer_id>?";
		}
		sql += " AND s.end_time<(select add_months(last_day(sysdate)+1,?) from dual)"
				+ " AND s.end_time>=(select add_months(last_day(sysdate)+1,?) from dual)";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{regionId,majorId,manufacturerId,0-endMonth,-1-beginMonth} ,Double.class);
	}
	
	/*
	 * 根据区域、专业、厂家获取各类服务支撑的数量
	 */
	public static List<SERTypeStatistics> getSERTypeCount(long regionId,long majorId,long manufacturerId){
		String sql="SELECT s.demand_id demandId,d.demand_name demandName,count(*) total,avg(total_duration) time "
				+"FROM sers s,demandcategories d,subregions sr,nemodels nm,nes ne,networks nw,manufacturerpersonnels mp "
				+"WHERE s.demand_id=d.demand_id AND s.subregion_id=sr.subregion_id AND s.mp_id=mp.mp_id "
				+"AND s.nemodel_id=nm.nemodel_id AND nm.ne_id=ne.ne_id AND ne.network_id=nw.network_id";
		if (regionId > 0) {// 代表选择的不是省公司全部区域
			sql += " AND sr.region_id=?";
		} else {
			sql += " AND sr.region_id>?";
		}
		if (majorId > 0) {// 代表选择的是单个专业
			sql += " AND nw.major_id=?";
		} else {
			sql += " AND nw.major_id>?";
		}
		if (manufacturerId > 0) {// 代表选择的是单个厂家
			sql += " AND mp.manufacturer_id=?";
		} else {
			sql += " AND mp.manufacturer_id>?";
		}
		sql+=" GROUP BY s.demand_id,d.demand_name ORDER BY total DESC";
		return Dac.getJdbcTemplate().query(sql, new Object[]{regionId,majorId,manufacturerId},new BeanPropertyRowMapper<SERTypeStatistics>(SERTypeStatistics.class));
	}
	
	/*
	 * 根据区域、专业、厂家、月份获取各类服务支撑的数量
	 */
	public static List<SERTypeStatistics> getSERTypeCount(long regionId,long majorId,long manufacturerId,int beginMonth,int endMonth){
		String sql="SELECT s.demand_id demandId,d.demand_name demandName,count(*) total,avg(total_duration) time "
				+"FROM sers s,demandcategories d,subregions sr,nemodels nm,nes ne,networks nw,manufacturerpersonnels mp "
				+"WHERE s.demand_id(+)=d.demand_id AND s.subregion_id=sr.subregion_id AND s.mp_id=mp.mp_id "
				+"AND s.nemodel_id=nm.nemodel_id AND nm.ne_id=ne.ne_id AND ne.network_id=nw.network_id";
		if (regionId > 0) {// 代表选择的不是省公司全部区域
			sql += " AND sr.region_id=?";
		} else {
			sql += " AND sr.region_id>?";
		}
		if (majorId > 0) {// 代表选择的是单个专业
			sql += " AND nw.major_id=?";
		} else {
			sql += " AND nw.major_id>?";
		}
		if (manufacturerId > 0) {// 代表选择的是单个厂家
			sql += " AND mp.manufacturer_id=?";
		} else {
			sql += " AND mp.manufacturer_id>?";
		}
		sql += " AND s.end_time<(select add_months(last_day(sysdate)+1,?) from dual)"
				+ " AND s.end_time>=(select add_months(last_day(sysdate)+1,?) from dual)";
		sql+=" GROUP BY s.demand_id,d.demand_name";
		return Dac.getJdbcTemplate().query(sql, new Object[]{regionId,majorId,manufacturerId,0-endMonth,-1-beginMonth},new BeanPropertyRowMapper<SERTypeStatistics>(SERTypeStatistics.class));
	}
	
	/*
	 * 根据区域、专业、厂家、月份获取服务响应数量
	 */
	public static int getSERCount(long regionId,long majorId,long manufacturerId,int beginMonth,int endMonth){
		String sql="SELECT count(*) FROM sers s,subregions sr,nemodels nm,nes ne,networks nw,manufacturerpersonnels mp "
				+"WHERE s.subregion_id=sr.subregion_id AND s.mp_id=mp.mp_id "
				+"AND s.nemodel_id=nm.nemodel_id AND nm.ne_id=ne.ne_id AND ne.network_id=nw.network_id";
		if (regionId > 0) {// 代表选择的不是省公司全部区域
			sql += " AND sr.region_id=?";
		} else {
			sql += " AND sr.region_id>?";
		}
		if (majorId > 0) {// 代表选择的是单个专业
			sql += " AND nw.major_id=?";
		} else {
			sql += " AND nw.major_id>?";
		}
		if (manufacturerId > 0) {// 代表选择的是单个厂家
			sql += " AND mp.manufacturer_id=?";
		} else {
			sql += " AND mp.manufacturer_id>?";
		}
		sql += " AND s.end_time<(select add_months(last_day(sysdate)+1,?) from dual)"
				+ " AND s.end_time>=(select add_months(last_day(sysdate)+1,?) from dual)";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{regionId,majorId,manufacturerId,0-endMonth,-1-beginMonth},Integer.class);
	}
	
		
	
	/*
	 * 根据区域、专业、厂家获取服务支撑的总览
	 */
    public static List<Map<String,Object>> getSERPandect(long regionId,long subregionId,long majorId,long manufacturerId,Timestamp launchTime,Timestamp completionTime,String comquery,String person){
		String sql = "SELECT s.ser_id,r.region_name,sr.subregion_name,m.major_name,mf.manufacturer_name,nm.nemodel_name,"
				+ "ne.ne_name,nw.network_name,s.start_time,s.end_time,s.total_duration,s.majorhandler,s.supportmethod,s.contactinfo,"
				+ "s.telnetserv_duration,s.fieldserv_duration,d.demand_name,s.demand_info,s.solution,s.demand_degree,s.remarksinfo,"
				+ "p.name,s.creat_time,tp.tp_name,mp.mp_name,"
				+ "(case currentprocess when -1 then '未完成' when 0 then '已提交,待点评' when 1 then '已点评' else '已审核' end) currentprocess,"
				+ "NVL(ss.servicesatisfaction,0) servicesatisfaction,NVL(resultsatisfaction,0) resultsatisfaction "
				+ "FROM  sers s,subregions sr,regions r,majors m,telecompersonnels tp,manufacturerpersonnels mp,manufacturers mf,"
				+ "nemodels nm,nes ne,networks nw,demandcategories d,"
				+ "(SELECT tp_id pid,tp_name name FROM telecompersonnels union SELECT mp_id pid,mp_name name FROM manufacturerpersonnels) p,"
				+ "(SELECT s1.ser_id,servicesatisfaction,resultsatisfaction "
				+ "FROM serreviews s1,(SELECT ser_id,max(review_time) review_time "
				+ "FROM serreviews GROUP BY ser_id) s2 "
				+ "WHERE s1.ser_id=s2.ser_id AND s1.review_time=s2.review_time) ss "
				+ "WHERE s.subregion_id=sr.subregion_id AND sr.region_id=r.region_id  AND s.mp_id=mp.mp_id AND mp.manufacturer_id=mf.manufacturer_id AND s.demand_id=d.demand_id "
				+ "AND s.nemodel_id=nm.nemodel_id AND nm.ne_id=ne.ne_id AND ne.network_id=nw.network_id AND nw.major_id=m.major_id AND s.auditor_id=tp.tp_id AND s.creater_id=p.pid AND s.ser_id=ss.ser_id(+)";
		
		if(regionId==0){
			sql+= " AND r.region_id>?";
		}else{
			sql+=" AND r.region_id=?";
		}
		if(subregionId==0){
			sql+=" AND sr.subregion_id>?";
		}
		else{
			sql+=" AND sr.subregion_id=?";
		}
		if(majorId==0){
			sql+=" AND m.major_id>?";
		}
		else{
			sql+=" AND m.major_id=?";
		}
		if (manufacturerId == 0) {
			sql += " AND mf.manufacturer_id>?";
		} else {
			sql += " AND mf.manufacturer_id=?";
		}
		sql +=" AND s.start_time>=? AND s.end_time<=?";
		sql +=" AND (nm.nemodel_name LIKE '%' || ? || '%'"
				+ " OR ne.ne_name LIKE '%' || ? || '%'"
				+ " OR nw.network_name LIKE '%' || ? || '%'"
				+ " OR s.supportmethod LIKE '%' || ? || '%'"
				+ " OR d.demand_name LIKE '%' || ? || '%'"
				+ " OR s.demand_info LIKE '%' || ? || '%'"
				+ " OR s.solution LIKE '%' || ? || '%'"
				+ " OR s.remarksinfo LIKE '%' || ? || '%')";
		sql+=" AND (p.name LIKE '%' || ? || '%'"
				+ " OR tp.tp_name LIKE '%' || ? || '%'"
				+ " OR mp.mp_name LIKE '%' || ? || '%'"
				+ " OR s.majorhandler LIKE '%' || ? || '%')";
	
		return Dac.getJdbcTemplate().queryForList(sql,new Object[]{
				regionId,subregionId,majorId,manufacturerId,launchTime,completionTime,
				comquery,comquery,comquery,comquery,comquery,comquery,comquery,comquery,
				person,person,person,person
		});
    }

    /*
	 * 厂家竣工ser工单
	 */
    public static void comSERtran(long serId,Timestamp comTime){
		//先补上ser的endtime，然后更改ser的currentprosess
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try {
					SERDac.comSER(serId,comTime);
					SERDac.chgSERCurrentProcess(serId,0);
				} catch (Exception e) {
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}
    
	public static void comSER(long serId, Timestamp comTime) {
		String sql="UPDATE sers SET end_time=? WHERE ser_id=?";
		Dac.getJdbcTemplate().update(sql,new Object[]{comTime,serId});
	}
}
