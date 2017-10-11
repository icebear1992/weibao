package com.telecom.weibao.dac;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.telecom.weibao.entity.ManufacturerPersonnel;
import com.telecom.weibao.entity.SER;
import com.telecom.weibao.entity.SS;
import com.telecom.weibao.entity.SSInfo;
import com.telecom.weibao.entity.TelecomPersonnel;

public class SSDac {	
	
	//SS基本情况
	static class SSRowMapper implements RowMapper<SS>{
		@Override
		public SS mapRow(ResultSet rs, int rowNum) throws SQLException {
			SS ss=new SS();
			ss.setSsId(rs.getLong("ss_id"));
			ss.getSubRegion().setSubRegionId(rs.getLong("subregion_id"));
			ss.getSubRegion().setSubRegionName(rs.getString("subregion_name"));
			ss.getMajor().setMajorId(rs.getLong("major_id"));
			ss.getMajor().setMajorName(rs.getString("major_name"));
			ss.setStartDate(rs.getDate("start_time"));
			ss.setEndDate(rs.getDate("end_time"));
			ss.setTotalDuration(rs.getDouble("total_duration"));
			ss.getMp().setMpId(rs.getLong("mp_id"));
			ss.getMp().setMpName(rs.getString("mp_name"));
			ss.getAuditor().setTpId(rs.getLong("auditor_id"));
			ss.getAuditor().setTpName(rs.getString("tp_name"));
			ss.getMp().getManufacturer().setManufacturerId(rs.getLong("manufacturer_id"));
			ss.getMp().getManufacturer().setManufacturerName(rs.getString("manufacturer_name"));
			ss.setCreatTime(rs.getTimestamp("creat_time"));
			return ss;
		}
	}
	
	static String sql1="SELECT s.ss_id,s.subregion_id,sr.subregion_name,s.major_id,m.major_name,s.start_time,s.end_time,s.total_duration,s.mp_id,mp.mp_name,s.auditor_id,tp.tp_name,mp.manufacturer_id,mf.manufacturer_name,s.creat_time "
			+"FROM subregions sr,majors m,ss s,manufacturerpersonnels mp,telecompersonnels tp,manufacturers mf "
			+"WHERE s.subregion_id=sr.subregion_id AND s.major_id=m.major_id AND s.mp_id=mp.mp_id AND mp.manufacturer_id=mf.manufacturer_id AND s.auditor_id=tp.tp_id";
	
	/*
	 * 根据ssid获取ss详情
	 */
	public static SS getSSDetailById(long ssId){
		String sql=sql1 +"AND s.ss_id=?";
		List<SS> ssList=Dac.getJdbcTemplate().query(sql,new Object[]{ssId},new SSRowMapper());
		if(ssList.size()>0){
			ssList.get(0).setSsInfoList(SSInfoDac.getSSInfosBySSId(ssId));
			ssList.get(0).setSsReviewList(SSReviewDac.getSSReviewsBySSId(ssId));
			return ssList.get(0);
		}
		return null;
	}
	
	/*
	 * 获取电信人员待评价的SS 即当前环节为0，且审核人是自己的
	 */
	public static List<SS> getNonReviewedSSs(TelecomPersonnel tp) {
		String sql = sql1 + " AND s.currentprocess=0 AND s.auditor_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{tp.getTpId()},new SSRowMapper());
	}
	public static int getNonReviewedSSCount(TelecomPersonnel tp) {
		String sql="SELECT count(*) FROM ss WHERE currentprocess=0 AND auditor_id=?";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{tp.getTpId()},Integer.class);
	}
	/*
	 * 获取电信人员待审核的SS 即当前环节是1，且审核人为自己的下属
	 */
	public static List<SS> getNonVerifySSs(TelecomPersonnel tp){
		String sql=sql1 + " AND s.currentprocess=1 AND s.auditor_id in (SELECT tp_id FROM telecompersonnels WHERE tpmanager_id=?)";
		return Dac.getJdbcTemplate().query(sql, new Object[]{tp.getTpId()},new SSRowMapper());
	}
	public static int getNonVerifySSCount(TelecomPersonnel tp){
		String sql="SELECT count(*) FROM ss WHERE currentprocess=1 AND auditor_id in (SELECT tp_id FROM telecompersonnels WHERE tpmanager_id=?)";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{tp.getTpId()},Integer.class);
	}

	/*
	 * 与电信员工自身有关的，被退回的工单（其为点评人或审核人）
	 */
	public static List<SS> getRelateReturnedSSs(TelecomPersonnel tp){
		String sql=sql1+" AND s.currentprocess=-1 AND (s.creater_id=? OR s.auditor_id=?)";
		return Dac.getJdbcTemplate().query(sql, new Object[]{tp.getTpId(),tp.getTpId()},new SSRowMapper());
	}
	public static int getRelateReturnedSScount(TelecomPersonnel tp){
		String sql="SELECT count(*) FROM SS WHERE currentprocess=-1 AND (creater_id=? OR auditor_id=?)";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{tp.getTpId(),tp.getTpId()},Integer.class);
	}
	/*
	 * 与电信员工自身有关的，未完成的工单（其创建的或其要点评的）
	 */
	public static List<SS> getRelateUnCompletedSSs(TelecomPersonnel tp){
		String sql=sql1+" AND h.currentprocess=-2 AND (s.creater_id=? OR s.auditor_id=?)";
		return Dac.getJdbcTemplate().query(sql, new Object[]{tp.getTpId(),tp.getTpId()},new SSRowMapper());
	}
	public static int getRelateUnCompletedSScount(TelecomPersonnel tp){
		String sql="SELECT count(*) FROM SS WHERE currentprocess=-2 AND (creater_id=? OR auditor_id=?)";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{tp.getTpId(),tp.getTpId()},Integer.class);
	}
	
	
	/*
	 * 获取厂家人员待处理的SS，即当前环节为-1，且厂商人员是自己的
	 */
	public static List<SS> getNonDealedSSs(ManufacturerPersonnel mp){
		String sql = sql1 + " AND s.currentprocess=-1 AND s.mp_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{mp.getMpId()},new SSRowMapper());
	}
	public static int getNonDealedSSCount(ManufacturerPersonnel mp){
		String sql="SELECT count(*) FROM ss WHERE currentprocess=-1 AND mp_id=?";
		return Dac.getJdbcTemplate().queryForObject(sql,new Object[]{mp.getMpId()},Integer.class);
	}
	/*
	 * 获取厂家人员未完成的SS，即当前环节为-1，且厂商人员是自己的
	 */
	public static int getNonFinSSCount(ManufacturerPersonnel mp){
		String sql="SELECT count(*) FROM ss WHERE currentprocess=-2 AND mp_id=?";
		return Dac.getJdbcTemplate().queryForObject(sql,new Object[]{mp.getMpId()},Integer.class);
	}
	/*
	 * 获取厂家已提交待点评的SS，即当前环节为0，且厂商人员是自己的
	 */
	public static List<SS> getNonReviewedSSs(ManufacturerPersonnel mp){
		String sql = sql1 + " AND s.currentprocess=0 AND s.mp_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{mp.getMpId()},new SSRowMapper());
	}
	public static int getNonReviewedSSCount(ManufacturerPersonnel mp){
		String sql="SELECT count(*) FROM ss WHERE currentprocess=0 AND mp_id=?";
		return Dac.getJdbcTemplate().queryForObject(sql,new Object[]{mp.getMpId()},Integer.class);
	}
	/*
	 * 获取厂家已提交待审核的SS的数量，即当前环节为1，且厂商人员是自己的
	 */
	public static int getNonAduitedSSCount(ManufacturerPersonnel mp){
		String sql="SELECT count(*) FROM ss WHERE currentprocess=1 AND mp_id=?";
		return Dac.getJdbcTemplate().queryForObject(sql,new Object[]{mp.getMpId()},Integer.class);
	}
	/*
	 * 根据区域、专业、厂家获取SS服务总人数
	 */
	public static int getSSTotalServers(long regionId,long majorId,long manufacturerId){
		String sql="SELECT sum(total) FROM ss s,subregions sr,manufacturerpersonnels mp,"
				+"(SELECT count(*) total FROM ssinfos GROUP BY ss_id) si "
				+"WHERE s.ss_id=si.ss_id AND s.subregion_id=sr.subregion_id AND s.mp_id=mp.mp_id";
		if (regionId > 0) {// 代表选择的不是省公司全部区域
			sql += " AND sr.region_id=?";
		} else {
			sql += " AND sr.region_id>?";
		}
		if (majorId > 0) {// 代表选择的是单个专业
			sql += " AND s.major_id=?";
		} else {
			sql += " AND s.major_id>?";
		}
		if (manufacturerId > 0) {// 代表选择的是单个厂家
			sql += " AND mp.manufacturer_id=?";
		} else {
			sql += " AND mp.manufacturer_id>?";
		}
		int total=Dac.getJdbcTemplate().queryForObject(sql, new Object[]{regionId,majorId,manufacturerId},Integer.class);
		return total;
	}
	/*
	 * 根据区域、专业、厂家获取SS服务总人时
	 */
	public static int getSSTotalTimes(long regionId,long majorId,long manufacturerId){
		String sql="SELECT sum(duration) FROM ss s,subregions sr,manufacturerpersonnels mp,"
				+"(SELECT sum(effective_duration) duration FROM ssinfos GROUP BY ss_id) si "
				+"WHERE s.ss_id=si.ss_id AND s.subregion_id=sr.subregion_id AND s.mp_id=mp.mp_id";
		if (regionId > 0) {// 代表选择的不是省公司全部区域
			sql += " AND sr.region_id=?";
		} else {
			sql += " AND sr.region_id>?";
		}
		if (majorId > 0) {// 代表选择的是单个专业
			sql += " AND s.major_id=?";
		} else {
			sql += " AND s.major_id>?";
		}
		if (manufacturerId > 0) {// 代表选择的是单个厂家
			sql += " AND mp.manufacturer_id=?";
		} else {
			sql += " AND mp.manufacturer_id>?";
		}
		int time=Dac.getJdbcTemplate().queryForObject(sql, new Object[]{regionId,majorId,manufacturerId},Integer.class);
		return time;
	}

	/*
	 * 插入一条SS记录，需进行事务处理
	 */
	public static void addSS(SS ss) {
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try{
					//添加SS获取ssId
					String sql = "INSERT INTO ss (ss_id,subregion_id,major_id,start_date,end_date,total_duration,mp_id,creater_id,auditor_id,creat_time,currentprocess) "
							+ "VALUES (recseq.nextval,?,?,?,?,?,?,?,?,?,?)";
					Dac.getJdbcTemplate().execute(new PreparedStatementCreator() {
						public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
							return conn.prepareStatement(sql,new String[] { "ss_id" });
						}
					}, 
					new PreparedStatementCallback<Integer>() {
						public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
							ps.setLong(1, ss.getSubRegion().getSubRegionId());
							ps.setLong(2, ss.getMajor().getMajorId());
							ps.setDate(3, ss.getStartDate());
							ps.setDate(4, ss.getEndDate());
							ps.setDouble(5,ss.getTotalDuration());
							ps.setLong(6, ss.getMp().getMpId());
							ps.setLong(7, ss.getCreaterId());
							ps.setLong(8,ss.getAuditor().getTpId());
							ps.setTimestamp(9, ss.getCreatTime());
							ps.setInt(10,ss.getCurrentProcess());							
							int result=ps.executeUpdate();
							ResultSet rs = ps.getGeneratedKeys();
							if (rs.next())
								ss.setSsId(rs.getLong(1));
							return result;
						}
					});
					//批量添加SSInfo
					for(SSInfo ssInfo: ss.getSsInfoList()){
						SSInfoDac.addSSInfo(ss.getSsId(), ssInfo);
					}
					
				}catch(Exception e){
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}
	
	/*
	 * 删除一条SS记录，需进行事务处理
	 */
	public static void delSS(long ssId){
		//先删除点评和维修详情，然后删除该记录
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try {
					SSReviewDac.delSSReviews(ssId);
					SSInfoDac.delSSInfos(ssId);
					String sql="DELETE ss WHERE ss_id=?";
					Dac.getJdbcTemplate().update(sql,new Object[]{ssId});
				} catch (Exception e) {
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}
	
	/*
	 * 修改一条SS记录，需进行事务处理
	 */
	public static void chgSS(SS ss) {
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try {
					// 先修改hm表
					String sql = "UPDATE ss SET subregion_id=?,major_id=?,start_date=?,end_date=?,total_duration=?,mp_id=?,auditor_id=?,currentprocess=? WHERE ss_id=?";
					Dac.getJdbcTemplate().update(sql,
							new Object[] { ss.getSubRegion().getSubRegionId(), ss.getMajor().getMajorId(),ss.getStartDate(),ss.getEndDate(),
									ss.getTotalDuration(), ss.getMp().getMpId(), ss.getAuditor().getTpId(),
									ss.getCurrentProcess(), ss.getSsId() });
					// 删除hminfo再添加
					SSInfoDac.delSSInfos(ss.getSsId());
					for (SSInfo ssInfo : ss.getSsInfoList()) {
						SSInfoDac.addSSInfo(ss.getSsId(), ssInfo);
					}
				} catch (Exception e) {
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}
	
	/*
	 * 修改SS的当前环节,increment代表增量，值为1或者-1
	 */
	public static boolean chgSSCurrentProcess(long ssId,int increment){
		String sql="UPDATE ss SET currentprocess=currentprocess+? WHERE hm_id=?";
		if(Dac.getJdbcTemplate().update(sql,new Object[]{increment,ssId})==1)
			return true;
		return false;
	}
	
	/*
	 * 查看一条SS记录是不是该厂家用户可以查看的，即厂商人员是他自己
	 */
	public static boolean hasShowAuthority(long ssId,long mpId){
		String sql="SELECT count(*) FROM ss WHERE ss_id=? AND mp_id=?";
		if(Dac.getJdbcTemplate().queryForObject(sql, new Object[]{ssId,mpId},Long.class)==0)
			return false;
		return true;
	}
	
	/*
	 * 查看一条SS记录是不是该厂家用户可以修改的，即厂商人员是他自己，且状态是-1的
	 */
	public static boolean hasChgAuthority(long ssId,long mpId){
		String sql="SELECT count(*) FROM ss WHERE currentprocess=-1 AND ss_id=? AND mp_id=?";
		if(Dac.getJdbcTemplate().queryForObject(sql,new Object[]{ssId,mpId},Long.class)==0)
			return false;
		return true;
	}
	
	
	
	/*
	 * 根据区域、专业、厂家、月份获取驻点服务次数
	 */
	public static int getSSCount(long regionId,long majorId,long manufacturerId,int beginMonth,int endMonth){
		String sql="SELECT count(*) FROM ss s,subregions sr,manufacturerpersonnels mp "
				+"WHERE s.subregion_id=sr.subregion_id AND s.mp_id=mp.mp_id";
		if (regionId > 0) {// 代表选择的不是省公司全部区域
			sql += " AND sr.region_id=?";
		} else {
			sql += " AND sr.region_id>?";
		}
		if (majorId > 0) {// 代表选择的是单个专业
			sql += " AND s.major_id=?";
		} else {
			sql += " AND s.major_id>?";
		}
		if (manufacturerId > 0) {// 代表选择的是单个厂家
			sql += " AND mp.manufacturer_id=?";
		} else {
			sql += " AND mp.manufacturer_id>?";
		}
		sql += " AND s.end_time<(select add_months(last_day(sysdate)+1,?) from dual)"
				+ " AND s.end_time>=(select add_months(last_day(sysdate)+1,?) from dual)";
		int count= Dac.getJdbcTemplate().queryForObject(sql, new Object[]{regionId,majorId,manufacturerId,0-endMonth,-1-beginMonth},Integer.class);
		return count;
	}

}
