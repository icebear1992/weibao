package com.telecom.weibao.dac;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.telecom.weibao.entity.HM;
import com.telecom.weibao.entity.HMInfo;
import com.telecom.weibao.entity.ManufacturerPersonnel;
import com.telecom.weibao.entity.TelecomPersonnel;
import com.telecom.weibao.statistics.HMModelStatistics;


public class HMDac {	
	//HM基本情况
	static class HMRowMapper implements RowMapper<HM> {
		@Override
		public HM mapRow(ResultSet rs, int rowNum) throws SQLException {
			HM hm = new HM();
			hm.setHmId(rs.getLong("hm_id"));
			hm.getSubRegion().setSubRegionId(rs.getLong("subregion_id"));
			hm.getSubRegion().setSubRegionName(rs.getString("subregion_name"));
			hm.getSubRegion().getRegion().setRegionId(rs.getLong("region_id"));
			hm.getSubRegion().getRegion().setRegionName(rs.getString("region_name"));
			hm.getMajor().setMajorId(rs.getLong("major_id"));
			hm.getMajor().setMajorName(rs.getString("major_name"));
			hm.setLaunchTime(rs.getDate("launch_time"));
			hm.setCompletionTime(rs.getDate("completion_time"));
			hm.getMp().setMpId(rs.getLong("mp_id"));
			hm.getMp().setMpName(rs.getString("mp_name"));
			hm.getMp().getManufacturer().setManufacturerId(rs.getLong("manufacturer_id"));
			hm.getMp().getManufacturer().setManufacturerName(rs.getString("manufacturer_name"));
			hm.getAuditor().setTpId(rs.getLong("auditor_id"));
			hm.getAuditor().setTpName(rs.getString("tp_name"));
			hm.setCreaterId(rs.getLong("creater_id"));
			hm.setCreatTime(rs.getTimestamp("creat_time"));
			hm.setCurrentProcess(rs.getInt("currentprocess"));
			hm.setTitle(rs.getString("title"));
			System.out.println(hm);
			return hm;
		}
	}

	static String sql1 = "SELECT h.title,h.hm_id,h.subregion_id,sr.subregion_name,sr.region_id,r.region_name,h.major_id,m.major_name,mp.manufacturer_id,mf.manufacturer_name,"
			+ "h.launch_time,h.completion_time,h.mp_id,mp.mp_name,h.auditor_id,tp.tp_name,h.creater_id,h.creat_time,h.CURRENTPROCESS  "
			+ "FROM subregions sr,regions r,majors m,hms h,manufacturers mf,manufacturerpersonnels mp,telecompersonnels tp "
			+ "WHERE h.subregion_id=sr.subregion_id AND sr.region_id=r.region_id AND h.major_id=m.major_id AND h.mp_id=mp.mp_id AND mp.manufacturer_id=mf.manufacturer_id AND h.auditor_id=tp.tp_id";

	/*
	 * 根据ID获取HM的详情
	 */
	public static HM getHMDetailById(long hmId) {
		String sql = sql1 + " AND h.hm_id=?";
		List<HM> hmList = Dac.getJdbcTemplate().query(sql, new Object[] { hmId }, new HMRowMapper());
		if (hmList.size() > 0) {
			System.out.println(0);
			hmList.get(0).setHmInfoList(HMInfoDac.getHMInfosByHMId(hmId));
			System.out.println(1);
			//hmList.get(0).setHmReviewList(HMReviewDac.getHMReviewsByHMId(hmId));
			System.out.println(2);
			return hmList.get(0);
		}
		return null;
	}

	/*
	 * 获取电信人员待评价的HM 即当前环节为0，且审核人是自己的
	 */
	public static List<HM> getNonReviewedHMs(TelecomPersonnel tp) {
		String sql = sql1 + " AND h.currentprocess=0 AND h.auditor_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{tp.getTpId()},new HMRowMapper());
	}
	public static int getNonReviewedHMCount(TelecomPersonnel tp){
		String sql="SELECT count(*) FROM hms WHERE currentprocess=0 AND auditor_id=?";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{tp.getTpId()},Integer.class);
	}
	/*
	 * 获取电信人员待审核的HM 即当前环节是1，且审核人为自己的下属
	 */
	public static List<HM> getNonAuditedHMs(TelecomPersonnel tp){
		String sql = sql1 + " AND h.currentprocess=1 AND h.auditor_id in (SELECT tp_id FROM telecompersonnels WHERE tpmanager_id=?)";
		return Dac.getJdbcTemplate().query(sql, new Object[]{tp.getTpId()},new HMRowMapper());
	}
	public static int getNonAuditedHMCount(TelecomPersonnel tp){
		String sql="SELECT count(*) FROM hms WHERE currentprocess=1 AND auditor_id in (SELECT tp_id FROM telecompersonnels WHERE tpmanager_id=?)";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{tp.getTpId()},Integer.class);
	}
	/*
	 * 与电信员工自身有关的，被退回的工单（其为点评人或审核人）
	 */
	public static List<HM> getRelateReturnedHMs(TelecomPersonnel tp){
		String sql=sql1+" AND h.currentprocess=-1 AND (h.creater_id=? OR h.auditor_id=?)";
		return Dac.getJdbcTemplate().query(sql, new Object[]{tp.getTpId(),tp.getTpId()},new HMRowMapper());
	}
	public static int getRelateReturnedHMcount(TelecomPersonnel tp){
		String sql="SELECT count(*) FROM hms WHERE currentprocess=-1 AND (creater_id=? OR auditor_id=?)";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{tp.getTpId(),tp.getTpId()},Integer.class);
	}
	/*
	 * 与电信员工自身有关的，未完成的工单（其创建的或其要点评的）
	 */
	public static List<HM> getRelateUnCompletedHMs(TelecomPersonnel tp){
		String sql=sql1+" AND h.currentprocess=-2 AND (h.creater_id=? OR h.auditor_id=?)";
		return Dac.getJdbcTemplate().query(sql, new Object[]{tp.getTpId(),tp.getTpId()},new HMRowMapper());
	}
	public static int getRelateUnCompletedHMcount(TelecomPersonnel tp){
		String sql="SELECT count(*) FROM hms WHERE currentprocess=-2 AND (creater_id=? OR auditor_id=?)";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{tp.getTpId(),tp.getTpId()},Integer.class);
	}
	
	/*
	 * 获取厂家人员被退回的HM，即当前环节为-1，且厂商人员是自己的
	 */
	public static List<HM> getNonDealedHMs(ManufacturerPersonnel mp){
		String sql = sql1 + " AND h.currentprocess=-1 AND h.mp_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{mp.getMpId()},new HMRowMapper());
	}
	public static int getNonDealedHMCount(ManufacturerPersonnel mp){
		String sql="SELECT count(*) FROM hms WHERE currentprocess=-1 AND mp_id=?";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{mp.getMpId()},Integer.class);
	}
	/*
	 * 获取厂家人员被退回的HM数量，即当前环节为-2，且厂商人员是自己的
	 */
	public static int getNonFinHMCount(ManufacturerPersonnel mp){
		String sql="SELECT count(*) FROM hms WHERE currentprocess=-2 AND mp_id=?";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{mp.getMpId()},Integer.class);
	}
	/*
	 * 获取厂家已提交待点评的HM 数量，即当前环节为0，且厂商人员是自己的
	 */
	public static int getNonReviewedHMCount(ManufacturerPersonnel mp){
		String sql="SELECT count(*) FROM hms WHERE currentprocess=0 AND mp_id=?";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{mp.getMpId()},Integer.class);
	}
	/*
	 * 获取厂家已提交待审核的HM 数量，即当前环节为0，且厂商人员是自己的
	 */
	public static int getNonAuditedHMCount(ManufacturerPersonnel mp){
		String sql="SELECT count(*) FROM hms WHERE currentprocess=1 AND mp_id=?";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{mp.getMpId()},Integer.class);
	}
	/*
	 * 获取厂家已提交待点评的HM list列表，即当前环节为0，且厂商人员是自己的
	 */
	public static List<HM> getNonReviewedHMs(ManufacturerPersonnel mp){
		String sql = sql1 + " AND h.currentprocess=0 AND h.mp_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{mp.getMpId()},new HMRowMapper());
	}
	/*
	 * 获取厂家已提交待审核的HM，即当前环节为1，且厂商人员是自己的
	 */
	public static List<HM> getNonAuditedHMs(ManufacturerPersonnel mp){
		String sql = sql1 + " AND h.currentprocess=1 AND h.mp_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{mp.getMpId()},new HMRowMapper());
	}
	/*
	 * 获取厂家未完成的HM，即当前环节为-2，且厂商人员是自己的
	 */
	public static List<HM> getUnfinishedHMs(ManufacturerPersonnel mp){
		String sql = sql1 + " AND h.currentprocess=-2 AND h.mp_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{mp.getMpId()},new HMRowMapper());
	}
	
	
		
	
	/*
	 * 插入一条HM记录，需进行事务处理
	 */
	public static void addHM(HM hm) {
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>(){
			@Override
			public Void doInTransaction(TransactionStatus ts) {
                try{
					String sql = "INSERT INTO hms (hm_id,subregion_id,major_id,launch_time,completion_time,mp_id,creater_id,auditor_id,creat_time,currentprocess) "
							+ "VALUES (recseq.nextval,?,?,?,?,?,?,?,?,?)";
                    //执行并获取hm_id
					Dac.getJdbcTemplate().execute(new PreparedStatementCreator() {
						public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
							return conn.prepareStatement(sql,new String[] { "hm_id" });
						}
					}, 
					new PreparedStatementCallback<Integer>() {
						public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
							ps.setLong(1, hm.getSubRegion().getSubRegionId());
							ps.setLong(2, hm.getMajor().getMajorId());
							ps.setDate(3, hm.getLaunchTime());
							ps.setDate(4, hm.getCompletionTime());
							ps.setLong(5,hm.getMp().getMpId());
							ps.setLong(6, hm.getCreaterId());
							ps.setLong(7,hm.getAuditor().getTpId());
							ps.setTimestamp(8, hm.getCreatTime());
							ps.setInt(9,hm.getCurrentProcess());							
							int result=ps.executeUpdate();
							ResultSet rs = ps.getGeneratedKeys();
							if (rs.next())
								hm.setHmId(rs.getLong(1));
							return result;
						}
					});	
					//批量添加HMInfo
					for(HMInfo hmInfo: hm.getHmInfoList()){
						HMInfoDac.addHMinfo(hm.getHmId(),hmInfo);
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
	 * 删除一条HM记录，需进行事务处理
	 */
	public static void delHM(long hmId){
		//先删除点评和维修详情，然后删除该记录
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try {
					HMReviewDac.delHMReviews(hmId);
					HMInfoDac.delHMinfos(hmId);
					String sql="DELETE hms WHERE hm_id=?";
					Dac.getJdbcTemplate().update(sql,new Object[]{hmId});
				} catch (Exception e) {
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}
	
	/*
	 * 修改一条HM记录，需进行事务处理
	 */
	public static void chgHM(HM hm){
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try {
					//先修改hm表
					String sql="UPDATE hms SET subregion_id=?,major_id=?,launch_time=?,completion_time=?,mp_id=?,auditor_id=?,currentprocess=? WHERE hm_id=?";
					System.out.println(sql);
					Dac.getJdbcTemplate().update(sql, new Object[]{
							hm.getSubRegion().getSubRegionId(),hm.getMajor().getMajorId(),
							hm.getLaunchTime(),hm.getCompletionTime(),hm.getMp().getMpId(),
							hm.getAuditor().getTpId(),hm.getCurrentProcess(),hm.getHmId()});
					//删除hminfo再添加
					HMInfoDac.delHMinfos(hm.getHmId());
					for(HMInfo hmInfo: hm.getHmInfoList()){
						HMInfoDac.addHMinfo(hm.getHmId(),hmInfo);
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
	 * 修改HM的当前环节
	 */
	public static boolean chgHMCurrentProcess(long hmId,int currentprocess){
		String sql="UPDATE hms SET currentprocess=? WHERE hm_id=?";
		if(Dac.getJdbcTemplate().update(sql,new Object[]{currentprocess,hmId})==1)
			return true;
		return false;
	}
	
	/*
	 * 查看一条HM记录是不是该厂家用户可以查看的，即厂商人员是他自己
	 */
	public static boolean hasShowAuthority(long hmId,long mpId){
		String sql="SELECT count(*) FROM hms WHERE hm_id=? AND mp_id=?";
		if(Dac.getJdbcTemplate().queryForObject(sql, new Object[]{hmId,mpId},Long.class)==0)
			return false;
		return true;
	}
	
	/*
	 * 查看一条HM记录是不是该厂家用户可以修改的，即厂商人员是他自己，且状态是-1的
	 */
	public static boolean hasChgAuthority(long hmId,long mpId){
		String sql="SELECT count(*) FROM hms WHERE currentprocess=-1 AND hm_id=? AND mp_id=?";
		if(Dac.getJdbcTemplate().queryForObject(sql,new Object[]{hmId,mpId},Long.class)==0)
			return false;
		return true;
	}
	
	/*
	 * 查看一条HM记录是不是厂家自己创建的，不是自己创建的不可删除，部分属性无法修改
	 */
	public static boolean isCreater(long hmId,long mpId){
		String sql="SELECT count(*) FROM hms WHERE hm_id=? AND creater_id=?";
		if(Dac.getJdbcTemplate().queryForObject(sql,new Object[]{hmId,mpId},Long.class)==0)
			return false;
		return true;
	}
	
	/*
	 * 查看一条HM记录是不是该电信用户可以查看的，即电信人员是他自己或者他下属的
	 */
	public static boolean hasShowAuthority(long hmId,TelecomPersonnel tp){
		String sql="SELECT count(*) FROM hms WHERE hmid=? AND (tp_id=? OR tp_id IN (SELECT tp_id FROM telecompersonnels WHERE manager_id=?))";
		if(Dac.getJdbcTemplate().queryForObject(sql,new Object[]{hmId,tp.getTpId(),tp.getTpId()},Long.class)==0)
			return false;
		return true;
	}
	
	/*
	 * 查看一条HM记录是不是该电信用户可以修改的，即电信人员是他自己，且状态是0的
	 * 以及电信人员是他下属，且状态是1的
	 */
	public static boolean hasChgAuthority(long hmId,TelecomPersonnel tp){
		String sql="SELECT count(*) FROM hms WHERE hmid=? AND ((currentprocess=0 AND tp_id=?) OR (currentprocess=1 AND tp_id IN (SELECT tp_id FROM telecompersonnels WHERE manager_id=?)))";
		if(Dac.getJdbcTemplate().queryForObject(sql,new Object[]{hmId,tp.getTpId(),tp.getTpId()},Long.class)==0)
			return false;
		return true;
	}
	
	/*
	 * 查看一条HM记录是不是厂家自己应该处理的的，
	 */
	public static boolean isMpdo(long HMId,long mpId){
		String sql="SELECT count(*) FROM HMS WHERE HM_id=? AND mp_id=?";
		if(Dac.getJdbcTemplate().queryForObject(sql,new Object[]{HMId,mpId},Long.class)==0){
			return false;
		}
		return true;
	}
	
	
	
	/*
	 * 获取未完成的返修事件的数量，即结束时间小于开始时间的
	 */
	public static int getNonCompletedHMCount(){
		String sql="SELECT count(*) FROM hms WHERE completion_time<launch_time";
		return Dac.getJdbcTemplate().queryForObject(sql,Integer.class);
	}
	/*
	 * 获取未完成的返修事件的清单，即结束事件小于开始事件的
	 */
	public static List<HM> getNonCompletedHMs(){
		String sql=sql1+ " AND h.launch_time>h.completion_time";
		return Dac.getJdbcTemplate().query(sql, new HMRowMapper());
	}
	
	
	
	
	
	
	/*
	 * 根据区域、专业、厂家等获取HM点评及时性
	 * months参数意义：代表距离当前日期的月份数，0代表当前月份，1代表上个月，如二者都是0，代表当前月份的情况
	 */
	public static double getHMTimeliness(long regionId,long majorId,long manufacturerId,int beginMonth,int endMonth){
		String sql = "SELECT NVL(avg(timeliness),0) FROM hms h,subregions sr,manufacturerpersonnels mp,"
				+ "(SELECT h1.hm_id,timeliness FROM hmreviews h1,"
				+ "(SELECT hm_id,max(review_time) review_time FROM hmreviews GROUP BY hm_id) h2 "
				+ "WHERE h1.hm_id=h2.hm_id AND h1.review_time=h2.review_time) hs "
				+ "WHERE h.hm_id=hs.hm_id AND h.subregion_id=sr.subregion_id AND h.mp_id=mp.mp_id ";
		if (regionId > 0) {// 代表选择的不是省公司全部区域
			sql += " AND sr.region_id=?";
		} else {
			sql += " AND sr.region_id>?";
		}
		if (majorId > 0) {// 代表选择的是单个专业
			sql += " AND h.major_id=?";
		} else {
			sql += " AND h.major_id>?";
		}
		if (manufacturerId > 0) {// 代表选择的是单个厂家
			sql += " AND mp.manufacturer_id=?";
		} else {
			sql += " AND mp.manufacturer_id>?";
		}
		sql += " AND h.completion_time<(select add_months(last_day(sysdate)+1,?) from dual)"
				+ " AND h.completion_time>=(select add_months(last_day(sysdate)+1,?) from dual)";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{regionId,majorId,manufacturerId,0-endMonth,-1-beginMonth} ,Double.class);
	}
	
	/*
	 * 根据区域、专业、厂家等获取HM点评满意度
	 * months参数意义：代表距离当前日期的月份数，0代表当前月份，1代表上个月，如二者都是0，代表当前月份的情况
	 */
	public static double getHMSatisfaction(long regionId,long majorId,long manufacturerId,int beginMonth,int endMonth){
		String sql = "SELECT NVL(avg(satisfaction),0) FROM hms h,subregions sr,manufacturerpersonnels mp,"
				+ "(SELECT h1.hm_id,satisfaction FROM hmreviews h1,"
				+ "(SELECT hm_id,max(review_time) review_time FROM hmreviews GROUP BY hm_id) h2 "
				+ "WHERE h1.hm_id=h2.hm_id AND h1.review_time=h2.review_time) hs "
				+ "WHERE h.hm_id=hs.hm_id AND h.subregion_id=sr.subregion_id AND h.mp_id=mp.mp_id ";
		if (regionId > 0) {// 代表选择的不是省公司全部区域
			sql += " AND sr.region_id=?";
		} else {
			sql += " AND sr.region_id>?";
		}
		if (majorId > 0) {// 代表选择的是单个专业
			sql += " AND h.major_id=?";
		} else {
			sql += " AND h.major_id>?";
		}
		if (manufacturerId > 0) {// 代表选择的是单个厂家
			sql += " AND mp.manufacturer_id=?";
		} else {
			sql += " AND mp.manufacturer_id>?";
		}
		sql += " AND h.completion_time<(select add_months(last_day(sysdate)+1,?) from dual)"
				+ " AND h.completion_time>=(select add_months(last_day(sysdate)+1,?) from dual)";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{regionId,majorId,manufacturerId,0-endMonth,-1-beginMonth} ,Double.class);
	}
	
	
	/*
	 * 根据区域、专业、厂家、月份获取HM返修次数
	 */
	public static int getHMCount(long regionId,long majorId,long manufacturerId,int beginMonth,int endMonth){
		String sql="SELECT count(*) FROM hms h,subregions sr,manufacturerpersonnels mp "
				+"WHERE h.subregion_id=sr.subregion_id AND h.mp_id=mp.mp_id";
		if (regionId > 0) {// 代表选择的不是省公司全部区域
			sql += " AND sr.region_id=?";
		} else {
			sql += " AND sr.region_id>?";
		}
		if (majorId > 0) {// 代表选择的是单个专业
			sql += " AND h.major_id=?";
		} else {
			sql += " AND h.major_id>?";
		}
		if (manufacturerId > 0) {// 代表选择的是单个厂家
			sql += " AND mp.manufacturer_id=?";
		} else {
			sql += " AND mp.manufacturer_id>?";
		}
		sql += " AND h.completion_time<(select add_months(last_day(sysdate)+1,?) from dual)"
				+ " AND h.completion_time>=(select add_months(last_day(sysdate)+1,?) from dual)";
		int count= Dac.getJdbcTemplate().queryForObject(sql, new Object[]{regionId,majorId,manufacturerId,0-endMonth,-1-beginMonth},Integer.class);
		return count;
	}
	
	/*
	 * 根据区域、专业、厂家获取HM平均历时
	 */
	public static double getHMAvgTime(long regionId,long majorId,long manufacturerId,int beginMonth,int endMonth){
		String sql="SELECT avg(extract(day from (completion_time-launch_time))) FROM hms h,subregions sr,manufacturerpersonnels mp "
				+"WHERE h.subregion_id=sr.subregion_id AND h.mp_id=mp.mp_id";
		if (regionId > 0) {// 代表选择的不是省公司全部区域
			sql += " AND sr.region_id=?";
		} else {
			sql += " AND sr.region_id>?";
		}
		if (majorId > 0) {// 代表选择的是单个专业
			sql += " AND h.major_id=?";
		} else {
			sql += " AND h.major_id>?";
		}
		if (manufacturerId > 0) {// 代表选择的是单个厂家
			sql += " AND mp.manufacturer_id=?";
		} else {
			sql += " AND mp.manufacturer_id>?";
		}
		sql += " AND h.completion_time<(select add_months(last_day(sysdate)+1,?) from dual)"
				+ " AND h.completion_time>=(select add_months(last_day(sysdate)+1,?) from dual)";
		double time=Dac.getJdbcTemplate().queryForObject(sql,new Object[]{regionId,majorId,manufacturerId,0-endMonth,-1-beginMonth},Double.class);
		return time;
	}
	
	/*
	 * 根据区域、专业、厂家获取HM返修情况，按照设备类型进行分组
	 */
	public static List<HMModelStatistics> getHMModelCount(long regionId,long majorId,long manufacturerId){
		String sql = "SELECT ht.nemodel_id nemodelId,nm.nemodel_name nemodelName,sum(ht.totalnum) total,sum(ht.badnum) bad,sum(ht.repairnum) repair "
				+ "FROM nemodels nm,subregions sr,nes ne,networks nw,"
				+ "(SELECT n.subregion_id,n.nemodel_id,n.ne_num totalnum,nvl(h.badnum,0) badnum,nvl(h.repairnum,0) repairnum FROM nenumber n,"
				+ "(SELECT hm.subregion_id,hi.nemodel_id,sum(hi.total_num) badnum,sum(hi.repair_num) repairnum "
				+ "FROM hms hm,hminfos hi WHERE hi.hm_id=hm.hm_id AND hm.currentprocess>0 GROUP BY hm.subregion_id,hi.nemodel_id) h "
				+ "WHERE n.subregion_id=h.subregion_id(+) AND n.nemodel_id=h.nemodel_id(+)) ht "
				+ "WHERE ht.nemodel_id=nm.nemodel_id AND nm.ne_id=ne.ne_id AND ne.network_id=nw.network_id AND ht.subregion_id=sr.subregion_id";
				
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
			sql += " AND nm.manufacturer_id=?";
		} else {
			sql += " AND nm.manufacturer_id>?";
		}
		sql+=" GROUP BY ht.nemodel_id,nm.nemodel_name ORDER BY bad/total DESC";
		return Dac.getJdbcTemplate().query(sql, new Object[]{regionId,majorId,manufacturerId},new BeanPropertyRowMapper<HMModelStatistics>(HMModelStatistics.class));
	}
	
	
	/*
	 * 根据区域、专业、厂家等信息获取返修总览信息,不包含设备信息
	 */
	public static List<Map<String,Object>> getHMPandect(long regionId,long majorId,long manufacturerId){
		String sql = "SELECT h.hm_id,r.region_name,sr.subregion_name,m.major_name,mf.manufacturer_name,"
				+ "launch_time,completion_time,hn.total,hn.repair,p.name,creat_time,mp.mp_name,tp.tp_name,"
				+ "(case currentprocess when -1 then '未完成' when 0 then '已提交,待点评' when 1 then '已点评' else '已审核' end) currentprocess,"
				+ "hs.timeliness,hs.satisfaction"
				+ "FROM hms h,subregions sr,regions r,majors m,telecompersonnels tp,manufacturerpersonnels mp,manufacturers mf,"
				+ "(SELECT hm_id,count(total_num) total,count(repair_num) repair FROM hminfos GROUP BY hm_id) hn,"
				+ "(SELECT h1.hm_id,timeliness,satisfaction FROM hmreviews h1,"
				+ "(SELECT hm_id,max(review_time) review_time FROM hmreviews GROUP BY hm_id) h2 "
				+ "WHERE h1.hm_id=h2.hm_id AND h1.review_time=h2.review_time) hs,"
				+ "(SELECT tp_id pid,tp_name name FROM telecompersonnels union SELECT mp_id pid,mp_name name FROM manufacturerpersonnels) p "
				+ "WHERE h.subregion_id=sr.subregion_id AND sr.region_id=r.region_id AND h.major_id=m.major_id AND h.mp_id=mp.mp_id "
				+ "AND mp.manufacturer_id=mf.manufacturer_id AND h.auditor_id=tp.tp_id AND h.creater_id=p.pid AND h.hm_id=hn.hm_id AND h.hm_id=hs.hm_id";
		if (regionId > 0) {// 代表选择的不是省公司全部区域
			sql += " AND r.region_id=?";
		} else {
			sql += " AND r.region_id>?";
		}
		if (majorId > 0) {// 代表选择的是单个专业
			sql += " AND m.major_id=?";
		} else {
			sql += " AND m.major_id>?";
		}
		if (manufacturerId > 0) {// 代表选择的是单个厂家
			sql += " AND mf.manufacturer_id=?";
		} else {
			sql += " AND mf.manufacturer_id>?";
		}
		return Dac.getJdbcTemplate().queryForList(sql);
	}
	
	/*
	 * 根据区域、专业、厂家等信息获取返修总览，包含设备详情
	 */
	public static List<Map<String,Object>> getHMAllPandect(long regionId,long subregionId,long majorId,long manufacturerId,Date launchTime,Date completionTime,String comquery,String person){
		String sql = "SELECT hd.hm_id,region_name,subregion_name,major_name,manufacturer_name,launch_time,completion_time,name,creat_time,mp_name,tp_name,currentprocess,"
				+ "timeliness,satisfaction,network_name,ne_name,nemodel_name,total_num,repair_num "
				+ "FROM (SELECT h.hm_id,r.region_name,sr.subregion_name,m.major_name,mf.manufacturer_name,"
				+ "launch_time,completion_time,p.name,creat_time,mp.mp_name,tp.tp_name,(case currentprocess when -1 then '未完成' when 0 then '已提交,待点评' when 1 then '已点评' else '已审核' end) currentprocess,"
				+ "NVL(hs.timeliness,0) timeliness,NVL(hs.satisfaction,0) satisfaction "
				+ "FROM hms h,subregions sr,regions r,majors m,telecompersonnels tp,manufacturerpersonnels mp,manufacturers mf,"
				+ "(SELECT h1.hm_id,timeliness,satisfaction FROM hmreviews h1,"
				+ "(SELECT hm_id,max(review_time) review_time FROM hmreviews GROUP BY hm_id) h2 "
				+ "WHERE h1.hm_id=h2.hm_id AND h1.review_time=h2.review_time) hs,"
				+ "(SELECT tp_id pid,tp_name name FROM telecompersonnels union SELECT mp_id pid,mp_name name FROM manufacturerpersonnels) p "
				+ "WHERE h.subregion_id=sr.subregion_id AND sr.region_id=r.region_id AND h.major_id=m.major_id AND h.mp_id=mp.mp_id "
				+ "AND mp.manufacturer_id=mf.manufacturer_id AND h.auditor_id=tp.tp_id AND h.creater_id=p.pid AND h.hm_id=hs.hm_id(+)";
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
		sql +=" AND launch_time>=? AND completion_time<=?";
		sql +=") hd,hminfos hi,nemodels nm,nes ne,networks nw WHERE hd.hm_id=hi.hm_id AND hi.nemodel_id=nm.nemodel_id AND nm.ne_id=ne.ne_id AND ne.network_id=nw.network_id";
		sql +=" AND (nemodel_name LIKE '%' || ? || '%'"
				+ " OR ne_name LIKE '%' || ? || '%'"
				+ " OR network_name LIKE '%' || ? || '%')";
		sql+=" AND (name LIKE '%' || ? || '%'"
				+ " OR tp_name LIKE '%' || ? || '%'"
				+ " OR mp_name LIKE '%' || ? || '%')";
		return Dac.getJdbcTemplate().queryForList(sql,new Object[]{
				regionId,subregionId,majorId,manufacturerId,launchTime,completionTime,
				comquery,comquery,comquery,person,person,person
		});
	}
	
	/*
	 * 根据区域、专业、厂家等信息获取未完成返修总览，包含设备详情
	 */
	public static List<Map<String,Object>> getNonFinishedPandect(){
		String sql = "SELECT hd.hm_id,region_name,subregion_name,major_name,manufacturer_name,launch_time,completion_time,name,creat_time,mp_name,tp_name,currentprocess,"
				+ "timeliness,satisfaction,network_name,ne_name,nemodel_name,total_num,repair_num "
				+ "FROM (SELECT h.hm_id,r.region_name,sr.subregion_name,m.major_name,mf.manufacturer_name,"
				+ "launch_time,completion_time,p.name,creat_time,mp.mp_name,tp.tp_name,(case currentprocess when -1 then '未完成' when 0 then '已提交,待点评' when 1 then '已点评' else '已审核' end) currentprocess,"
				+ "NVL(hs.timeliness,0) timeliness,NVL(hs.satisfaction,0) satisfaction "
				+ "FROM hms h,subregions sr,regions r,majors m,telecompersonnels tp,manufacturerpersonnels mp,manufacturers mf,"
				+ "(SELECT h1.hm_id,timeliness,satisfaction FROM hmreviews h1,"
				+ "(SELECT hm_id,max(review_time) review_time FROM hmreviews GROUP BY hm_id) h2 "
				+ "WHERE h1.hm_id=h2.hm_id AND h1.review_time=h2.review_time) hs,"
				+ "(SELECT tp_id pid,tp_name name FROM telecompersonnels union SELECT mp_id pid,mp_name name FROM manufacturerpersonnels) p "
				+ "WHERE h.subregion_id=sr.subregion_id AND sr.region_id=r.region_id AND h.major_id=m.major_id AND h.mp_id=mp.mp_id "
				+ "AND mp.manufacturer_id=mf.manufacturer_id AND h.auditor_id=tp.tp_id AND h.creater_id=p.pid AND h.hm_id=hs.hm_id(+)";
		sql+=" AND launch_time>completion_time) hd,hminfos hi,nemodels nm,nes ne,networks nw WHERE hd.hm_id=hi.hm_id AND hi.nemodel_id=nm.nemodel_id AND nm.ne_id=ne.ne_id AND ne.network_id=nw.network_id";
		return Dac.getJdbcTemplate().queryForList(sql);
	}
	
	/*
	 * 厂家竣工hm工单
	 */
    public static void comHMtran(long hmId,Timestamp comTime){
		//先补上hm的endtime，然后更改hm的currentprosess
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try {
					HMDac.comHM(hmId,comTime);
					HMDac.chgHMCurrentProcess(hmId,0);
				} catch (Exception e) {
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}
    
	public static void comHM(long hmId, Timestamp comTime) {
		String sql="UPDATE hms SET end_time=? WHERE hm_Id=?";
		Dac.getJdbcTemplate().update(sql,new Object[]{comTime,hmId});
	}
}
