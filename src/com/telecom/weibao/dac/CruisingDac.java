package com.telecom.weibao.dac;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.telecom.weibao.entity.Cruising;
import com.telecom.weibao.entity.ManufacturerPersonnel;
import com.telecom.weibao.entity.TelecomPersonnel;

public class CruisingDac {
	static class CruisingRowMapper implements RowMapper<Cruising>{
		@Override
		public Cruising mapRow(ResultSet rs, int rowNum) throws SQLException {
			Cruising c=new Cruising();
			c.setcId(rs.getLong("c_id"));
			c.getSubRegion().setSubRegionId(rs.getLong("subregion_id"));
			c.getSubRegion().setSubRegionName(rs.getString("subregion_name"));
			c.getMajor().setMajorId(rs.getLong("major_id"));
			c.getMajor().setMajorName(rs.getString("major_name"));
			c.setStartTime(rs.getDate("start_time"));
			c.setEndTime(rs.getDate("end_time"));
			c.setTotalDuration(rs.getDouble("total_duration"));
			c.getMp().setMpId(rs.getLong("mp_id"));
			c.getMp().setMpName(rs.getString("mp_name"));
			c.getMp().getManufacturer().setManufacturerId(rs.getLong("manufacturer_id"));
			c.getMp().getManufacturer().setManufacturerName(rs.getString("manufacturer_name"));
			c.getAuditor().setTpId(rs.getLong("auditor_id"));
			c.getAuditor().setTpName(rs.getString("tp_name"));
			c.setCreaterId(rs.getLong("creater_id"));
			c.setCreatTime(rs.getTimestamp("creat_time"));
			c.setCurrentProcess(rs.getInt("currentProcess"));
			c.setCenginerroomNum(rs.getInt("cenginerroom_num"));
			c.setCequipmentNum(rs.getInt("cenginerroom_num"));
			c.setStaffing(rs.getInt("staffing"));
			c.setVehicleDeployment(rs.getInt("vehicle_deployment"));
			c.setcObject(rs.getString("c_object"));
			c.setcContent(rs.getString("c_content"));
			c.setcResult(rs.getString("c_result"));
			c.setcReport(rs.getString("c_report"));
			c.setRemarksinfo(rs.getString("remarksinfo"));
			return c;
		}		
	}
	
	static String sql1 = "SELECT c.c_id,c.subregion_id,sr.subregion_name,c.major_id,m.major_name,c.start_time,c.end_time,c.total_duration,"
			+ "c.mp_id,mp.mp_name,mp.manufacturer_id,mf.manufacturer_name,c.auditor_id,tp.tp_name,c.creater_id,c.creat_time,c.currentprocess,"
			+ "c.cenginerroom_num,c.cequipment_num,c.staffing,c.vehicle_deployment,c.c_object,c.c_content,c.c_result,c.c_report,c.remarksinfo "
			+ "FROM cruising c,subregions sr,majors m,manufacturerpersonnels mp,manufacturers mf,telecompersonnels tp "
			+ "WHERE c.subregion_id=sr.subregion_id AND c.major_id=m.major_id AND c.mp_id=mp.mp_id AND mp.manufacturer_id=mf.manufacturer_id AND c.auditor_id=tp.tp_id";
	
	
	/*
	 * 根据ID获取Cruising的详情
	 */
	public static Cruising getHMDetailById(long cId) {
		String sql = sql1 + " AND c.c_id=?";
		List<Cruising> cList = Dac.getJdbcTemplate().query(sql, new Object[] { cId }, new CruisingRowMapper());
		if (cList.size() > 0) {
			cList.get(0).setCruisingReviewList(CruisingReviewDac.getCruisingReviewsByCruisingId(cId));
			return cList.get(0);
		}
		return null;
	}
	
	/*
	 * 获取电信人员待评价的Cruising 即当前环节为0，且审核人是自己的
	 */
	public static List<Cruising> getNonReviewedCruisings(TelecomPersonnel tp) {
		String sql = sql1 + " AND c.currentprocess=0 AND c.auditor_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{tp.getTpId()},new CruisingRowMapper());
	}
	/*
	 * 获取电信人员待审核的Cruising 即当前环节是1，且审核人为自己的下属
	 */
	public static List<Cruising> getNonVerifyCruisings(TelecomPersonnel tp){
		String sql = sql1 + " AND c.currentprocess=1 AND c.auditor_id in (SELECT tp_id FROM telecompersonnels WHERE tpmanager_id=?)";
		return Dac.getJdbcTemplate().query(sql, new Object[]{tp.getTpId()},new CruisingRowMapper());
	}
	/*
	 * 获取厂家人员待处理的Cruising，即当前环节为-1，且厂商人员是自己的
	 */
	public static List<Cruising> getNonDealedCruisings(ManufacturerPersonnel mp){
		String sql = sql1 + " AND c.currentprocess=-1 AND c.mp_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{mp.getMpId()},new CruisingRowMapper());
	}
	/*
	 * 获取厂家已提交待点评的Cruising，即当前环节为0，且厂商人员是自己的
	 */
	public static List<Cruising> getNonReviewedHMs(ManufacturerPersonnel mp){
		String sql = sql1 + " AND c.currentprocess=0 AND c.mp_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{mp.getMpId()},new CruisingRowMapper());
	}
	
	/*
	 * 插入一条Cruising
	 */
	public static void addCruising(Cruising c) {
		String sql = "INSERT INTO cruising (c_id,subregion_id,major_id,start_time,end_time,total_duration,mp_id,auditor_id,creater_id,creat_time,currentprocess,"
				+"cenginerroom_num,cequipment_num,staffing,vehicle_deployment,c_object,c_content,c_result,c_report,remarksinfo) "
				+"VALUES (recseq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Dac.getJdbcTemplate().update(sql,new Object[]{
				c.getSubRegion().getSubRegionId(),c.getMajor().getMajorId(),
				c.getStartTime(),c.getEndTime(),c.getTotalDuration(),
				c.getMp().getMpId(),c.getAuditor().getTpId(),c.getCreaterId(),
				c.getCreatTime(),c.getCurrentProcess(),
				c.getCenginerroomNum(),c.getCequipmentNum(),
				c.getStaffing(),c.getVehicleDeployment(),
				c.getcObject(),c.getcContent(),c.getcResult(),c.getcReport(),c.getRemarksinfo()
		});		
	}
	
	/*
	 * 修改一条Cruising
	 */
	public static void chgCruising(Cruising c) {
		String sql = "UPDATE cruising subregion_id=?,major_id=?,start_time=?,end_time=?,total_duration=?,mp_id=?,auditor_id=?,currentprocess=?,"
				+"cenginerroom_num=?,cequipment_num=?,staffing=?,vehicle_deployment=?,c_object=?,c_content=?,c_result=?,c_report=?,remarksinfo=?) "
				+"WHERE c_id=?";
		Dac.getJdbcTemplate().update(sql,new Object[]{
				c.getSubRegion().getSubRegionId(),c.getMajor().getMajorId(),
				c.getStartTime(),c.getEndTime(),c.getTotalDuration(),
				c.getMp().getMpId(),c.getAuditor().getTpId(),c.getCurrentProcess(),
				c.getCenginerroomNum(),c.getCequipmentNum(),c.getStaffing(),c.getVehicleDeployment(),
				c.getcObject(),c.getcContent(),c.getcResult(),c.getcReport(),c.getRemarksinfo(),
				c.getcId()
		});		
	}
	
	/*
	 * 删除一条Cruising，需进行事务处理
	 */
	public static void delCruising(long cId){
		//先删除点评，然后删除该记录
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try {
					CruisingReviewDac.delCruisingReviews(cId);
					String sql="DELETE cruising WHERE c_id=?";
					Dac.getJdbcTemplate().update(sql,new Object[]{cId});
				} catch (Exception e) {
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}
	
	
	/*
	 * 修改Cruising的当前环节,increment代表增量，值为1或者-1
	 */
	public static boolean chgCruisingCurrentProcess(long cId,int increment){
		String sql="UPDATE cruising SET currentprocess=currentprocess+? WHERE c_id=?";
		if(Dac.getJdbcTemplate().update(sql,new Object[]{increment,cId})==1)
			return true;
		return false;
	}
	/*
	 * 查看一条Cruising记录是不是该厂家用户可以查看的，即厂商人员是他自己
	 */
	public static boolean hasShowAuthority(long cId,long mpId){
		String sql="SELECT count(*) FROM cruising WHERE c_id=? AND mp_id=?";
		if(Dac.getJdbcTemplate().queryForObject(sql, new Object[]{cId,mpId},Long.class)==0)
			return false;
		return true;
	}
	
	/*
	 * 查看一条Cruising记录是不是该厂家用户可以修改的，即厂商人员是他自己，且状态是-1的
	 */
	public static boolean hasChgAuthority(long cId,long mpId){
		String sql="SELECT count(*) FROM cruising WHERE currentprocess=-1 AND c_id=? AND mp_id=?";
		if(Dac.getJdbcTemplate().queryForObject(sql,new Object[]{cId,mpId},Long.class)==0)
			return false;
		return true;
	}
	
	
	
	/*
	 * 根据区域、专业、厂家、月份获取巡检次数
	 */
	public static int getCruisingCount(long regionId,long majorId,long manufacturerId,int beginMonth,int endMonth){
		String sql="SELECT count(*) FROM cruising c,subregions sr,manufacturerpersonnels mp "
				+"WHERE c.subregion_id=sr.subregion_id AND c.mp_id=mp.mp_id";
		if (regionId > 0) {// 代表选择的不是省公司全部区域
			sql += " AND sr.region_id=?";
		} else {
			sql += " AND sr.region_id>?";
		}
		if (majorId > 0) {// 代表选择的是单个专业
			sql += " AND c.major_id=?";
		} else {
			sql += " AND c.major_id>?";
		}
		if (manufacturerId > 0) {// 代表选择的是单个厂家
			sql += " AND mp.manufacturer_id=?";
		} else {
			sql += " AND mp.manufacturer_id>?";
		}
		sql += " AND c.end_time<(select add_months(last_day(sysdate)+1,?) from dual)"
				+ " AND c.end_time>=(select add_months(last_day(sysdate)+1,?) from dual)";
		int count= Dac.getJdbcTemplate().queryForObject(sql, new Object[]{regionId,majorId,manufacturerId,0-endMonth,-1-beginMonth},Integer.class);
		return count;
	}
	
}
