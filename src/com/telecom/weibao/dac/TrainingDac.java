package com.telecom.weibao.dac;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.telecom.weibao.entity.ManufacturerPersonnel;
import com.telecom.weibao.entity.TelecomPersonnel;
import com.telecom.weibao.entity.Training;

public class TrainingDac {
	
	static class TrainingRowMapper implements RowMapper<Training>{
		@Override
		public Training mapRow(ResultSet rs, int rowNum) throws SQLException {
			Training tr=new Training();
			tr.settId(rs.getLong("t_id"));
			tr.getSubRegion().setSubRegionId(rs.getLong("subregion_id"));
			tr.getSubRegion().setSubRegionName(rs.getString("subregion_name"));
			tr.getMajor().setMajorId(rs.getLong("major_id"));
			tr.getMajor().setMajorName(rs.getString("major_name"));
			tr.setTrainingName(rs.getString("training_name"));
			tr.setStartTime(rs.getDate("start_time"));
			tr.setEndTime(rs.getDate("end_time"));
			tr.setTotalDuration(rs.getDouble("total_duration"));
			tr.setTrainingFormat(rs.getString("training_format"));
			tr.setTrainingContent(rs.getString("training_content"));
			tr.setTrainingPlace(rs.getString("training_place"));
			tr.setTraineesnum(rs.getInt("traineesum"));
			tr.setCreaterId(rs.getLong("creater_id"));
			tr.setCreatTime(rs.getTimestamp("creat_time"));
			tr.getAuditor().setTpId(rs.getLong("auditor_id"));
			tr.getAuditor().setTpName(rs.getString("tp_name"));
			tr.getMp().setMpId(rs.getLong("mp_id"));
			tr.getMp().setMpName(rs.getString("mp_name"));
			tr.getMp().getManufacturer().setManufacturerId(rs.getLong("manufacturer_id"));
			tr.getMp().getManufacturer().setManufacturerName(rs.getString("manufacturer_name"));
			tr.setCurrentProcess(rs.getInt("currentprocess"));
			return tr;
		}
		
	}
	
	
	static String sql1="SELECT t.t_id,t.subregion_id,sr.subregion_name,t.major_id,m.major_name,t.training_name,t.start_time,t.end_time,t.total_duration,t.training_format,t.training_content,"
			+"t.training_place,t.traineesum,t.creater_id,t.creat_time,t.auditor_id,tp.tp_name,t.mp_id,mp.mp_name,mp.manufacturer_id,mf.manufacturer_name,t.currentprocess "
			+"FROM training t,subregions sr,majors m,telecompersonnels tp,manufacturerpersonnel mp,manufacturers mf "
			+"WHERE t.subregion_id=sr.subregion_id AND t.major_id=m.major_id AND t.auditor_id=tp.tp_id AND t.mp_id=mp.mp_id AND mp.manufacturer_id=mf.manufacturer_id";
	
	
	
	/*
	 * 根据tId获取Training详情
	 */
	public static Training getTrainingDetailById(long tId){
		String sql = sql1+" AND t.t_id=?";
		List<Training> tList=Dac.getJdbcTemplate().query(sql, new Object[]{tId},new TrainingRowMapper());
		if(tList.size()>0){
			tList.get(0).setTrainingReviewList(TrainingReviewDac.getTrainingReviewsByTId(tId));
			return tList.get(0);
		}
		return null;
	}
	
	
	/*
	 * 获取电信人员待评价的Training 即当前环节为0，且审核人是自己的
	 */
	public static List<Training> getNonReviewedTrainings(TelecomPersonnel tp) {
		String sql = sql1 + " AND t.currentprocess=0 AND t.auditor_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{tp.getTpId()},new TrainingRowMapper());
	}
	public static int getNonReviewedTrainingCount(TelecomPersonnel tp){
		String sql="SELECT count(*) FROM training WHERE currentprocess=0 AND auditor_id=?";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{tp.getTpId()},Integer.class);
	}
	/*
	 * 获取电信人员待审核的Training 即当前环节是1，且审核人为自己的下属
	 */
	public static List<Training> getNonVerifyTrainings(TelecomPersonnel tp){
		String sql = sql1 + " AND t.currentprocess=1 AND t.auditor_id in (SELECT tp_id FROM telecompersonnels WHERE tpmanager_id=?)";
		return Dac.getJdbcTemplate().query(sql, new Object[]{tp.getTpId()},new TrainingRowMapper());
	}
	public static int getNonVerifyTrainingCount(TelecomPersonnel tp){
		String sql="SELECT count(*) FROM training WHERE currentprocess=1 AND auditor_id in (SELECT tp_id FROM telecompersonnels WHERE tpmanager_id=?)";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{tp.getTpId()},Integer.class);
	}
	/*
	 * 获取自身创建、退回、以及厂家创建尚未提交的
	 */
	public static List<Training> getRelatedTrainings(TelecomPersonnel tp){
		String sql=sql1+" AND t.currentprocess=-1 AND (t.creater_id=? OR t.auditor_id=?)";
		return Dac.getJdbcTemplate().query(sql, new Object[]{tp.getTpId(),tp.getTpId()},new TrainingRowMapper());
	}
	
	/*
	 * 获取厂家人员待处理的Training，即当前环节为-1，且厂商人员是自己的
	 */
	public static List<Training> getNonDealedTrainings(ManufacturerPersonnel mp){
		String sql = sql1 + " AND t.currentprocess=-1 AND t.mp_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{mp.getMpId()},new TrainingRowMapper());
	}
	public static int getNonDealedTrainingCount(ManufacturerPersonnel mp){
		String sql="SELECT count(*) FROM training WHERE currentprocess=-1 AND mp_id=?";
		return Dac.getJdbcTemplate().queryForObject(sql, new Object[]{mp.getMpId()},Integer.class);
	}
	/*
	 * 获取厂家已提交待点评的Training，即当前环节为0，且厂商人员是自己的
	 */
	public static List<Training> getNonReviewedTrainings(ManufacturerPersonnel mp){
		String sql = sql1 + " AND t.currentprocess=0 AND t.mp_id=?";
		return Dac.getJdbcTemplate().query(sql, new Object[]{mp.getMpId()},new TrainingRowMapper());
	}
	
	
	/*
	 * 插入一条Training
	 */
	public static void addTraining(Training t) {
		String sql = "INSERT INTO training (t_id,subregion_id,major_id,training_name,start_time,end_time,total_duration,"
				+ "training_format,training_content,training_place,traineesnum,creater_id,auditor_id,creat_time,mp_id,currentprocess) "
				+ "VALUES (recseq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Dac.getJdbcTemplate().update(sql,new Object[]{
				t.getSubRegion().getSubRegionId(),t.getMajor().getMajorId(),
				t.getTrainingName(),t.getStartTime(),t.getEndTime(),t.getTotalDuration(),
				t.getTrainingFormat(),t.getTrainingContent(),t.getTrainingPlace(),
				t.getTraineesnum(),t.getCreaterId(),t.getAuditor().getTpId(),
				t.getCreatTime(),t.getMp().getMpId(),t.getCurrentProcess()
		});		
	}
	
	/*
	 * 修改一条Training
	 */
	public static void chgTraining(Training t){
		String sql = "UPDATE training SET subregion_id=?,major_id=?,training_name=?,start_time=?,end_time=?,total_duration=?,"
				+ "training_format=?,training_content=?,training_place=?,traineesnum=?,auditor_id=?,mp_id=?,currentprocess=? "
				+ "WHERE t_id=?";
		Dac.getJdbcTemplate().update(sql,new Object[]{
				t.getSubRegion().getSubRegionId(),t.getMajor().getMajorId(),
				t.getTrainingName(),t.getStartTime(),t.getEndTime(),t.getTotalDuration(),
				t.getTrainingFormat(),t.getTrainingContent(),t.getTrainingPlace(),
				t.getTraineesnum(),t.getAuditor().getTpId(),t.getMp().getMpId(),
				t.getCurrentProcess(),t.gettId()
		});		
	}
	
	/*
	 * 删除一条Training，需进行事务处理
	 */
	public static void delTraining(long tId){
		//先删除点评，然后删除该记录
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try {
					TrainingReviewDac.delTrainingReviews(tId);
					String sql="DELETE training WHERE t_id=?";
					Dac.getJdbcTemplate().update(sql,new Object[]{tId});
				} catch (Exception e) {
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}
	/*
	 * 修改Training的当前环节,increment代表增量，值为1或者-1
	 */
	public static boolean chgTrainingCurrentProcess(long tId,int increment){
		String sql="UPDATE training SET currentprocess=currentprocess+? WHERE t_id=?";
		if(Dac.getJdbcTemplate().update(sql,new Object[]{increment,tId})==1)
			return true;
		return false;
	}
	
	/*
	 * 查看一条Training记录是不是该厂家用户可以查看的，即厂商人员是他自己
	 */
	public static boolean hasShowAuthority(long tId,long mpId){
		String sql="SELECT count(*) FROM training WHERE t_id=? AND mp_id=?";
		if(Dac.getJdbcTemplate().queryForObject(sql, new Object[]{tId,mpId},Long.class)==0)
			return false;
		return true;
	}
	
	/*
	 * 查看一条Training记录是不是该厂家用户可以修改的，即厂商人员是他自己，且状态是-1的
	 */
	public static boolean hasChgAuthority(long tId,long mpId){
		String sql="SELECT count(*) FROM training WHERE currentprocess=-1 AND t_id=? AND mp_id=?";
		if(Dac.getJdbcTemplate().queryForObject(sql,new Object[]{tId,mpId},Long.class)==0)
			return false;
		return true;
	}
		
	
	
	

	/*
	 * 根据区域、专业、厂家、月份获取培训次数
	 */
	public static int getTrainingCount(long regionId,long majorId,long manufacturerId,int beginMonth,int endMonth){
		String sql="SELECT count(*) FROM training t,subregions sr,manufacturerpersonnels mp "
				+"WHERE t.subregion_id=sr.subregion_id AND t.mp_id=mp.mp_id";
		if (regionId > 0) {// 代表选择的不是省公司全部区域
			sql += " AND sr.region_id=?";
		} else {
			sql += " AND sr.region_id>?";
		}
		if (majorId > 0) {// 代表选择的是单个专业
			sql += " AND t.major_id=?";
		} else {
			sql += " AND t.major_id>?";
		}
		if (manufacturerId > 0) {// 代表选择的是单个厂家
			sql += " AND mp.manufacturer_id=?";
		} else {
			sql += " AND mp.manufacturer_id>?";
		}
		sql += " AND t.end_time<(select add_months(last_day(sysdate)+1,?) from dual)"
				+ " AND t.end_time>=(select add_months(last_day(sysdate)+1,?) from dual)";
		int count= Dac.getJdbcTemplate().queryForObject(sql, new Object[]{regionId,majorId,manufacturerId,0-endMonth,-1-beginMonth},Integer.class);
		return count;
	}
}
