package com.telecom.weibao.dac;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import com.telecom.weibao.entity.SERReview;

public class SERReviewDac {
	
	static class SERReviewRowMapper implements RowMapper<SERReview>{
		@Override
		public SERReview mapRow(ResultSet rs, int rowNum) throws SQLException {
			SERReview serReview=new SERReview();
				serReview.getReviewer().setTpName(rs.getString("tp_name"));
				serReview.setServiceSatisfaction(rs.getInt("servicesatisfaction"));
				serReview.setResultSatisfaction(rs.getInt("resultsatisfaction"));
				serReview.setReviewContent(rs.getString("review_content"));
				serReview.setReviewType(rs.getInt("review_type"));
				serReview.setReviewTime(rs.getTimestamp("review_time"));
			return serReview;
		}		
	}
	
	//根据serId获取点评，审核详情
	public static List<SERReview> getSERReviewsBySERId(long serId){
		String sql = "SELECT tp_name,servicesatisfaction,resultsatisfaction,review_content,review_time,review_type "
				+ "FROM serreviews s,telecompersonnels t WHERE s.reviewer_id=t.tp_id AND ser_id=? "
				+"ORDER BY review_time";
		return Dac.getJdbcTemplate().query(sql, new Object[]{serId},new SERReviewRowMapper());
	}
	
	//根据serId获取点评，审核+修改详情
		public static List<SERReview> getSERReviewsAllBySERId(long serId){
			System.out.println("oracle"+serId);
			String sql = "SELECT tp_name,servicesatisfaction,resultsatisfaction,review_content,review_time,review_type FROM serreviews s,telecompersonnels t WHERE s.reviewer_id=t.tp_id  AND ser_id=? union select mp_name,servicesatisfaction,resultsatisfaction,review_content,review_time,review_type FROM serreviews s,MANUFACTURERPERSONNELS m WHERE s.CHANGER_ID=m.mp_id  AND ser_id=? ORDER BY review_time DESC";
			return Dac.getJdbcTemplate().query(sql, new Object[]{serId,serId},new SERReviewRowMapper());
		}
	
	
	//根据serId删除SERReview
	public static void delSERReviews(long serId){
		String sql="DELETE serreviews WHERE ser_id=?";
		Dac.getJdbcTemplate().update(sql,new Object[]{serId});
	}
	
	// 点评SER，除了在SERReview表中插入外，还需要将SER的当前环节字段加以修改
	public static void ReviewSER(SERReview sr,int flag) {
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try {
					String sql = "INSERT INTO serreviews (review_id,ser_id,reviewer_id,servicesatisfaction,resultsatisfaction,review_content,review_time,review_type) "
							+ "VALUES (rewseq.nextval,?,?,?,?,?,?,?)";
					Dac.getJdbcTemplate().update(sql, new Object[] { sr.getSer().getSerId(), sr.getReviewer().getTpId(),
							sr.getServiceSatisfaction(), sr.getResultSatisfaction(), sr.getReviewContent(), sr.getReviewTime(),sr.getReviewType() });
					// 将HM的当前环节字段加以修改
					if(flag==0){
						SERDac.chgSERCurrentProcess(sr.getSer().getSerId(), -1);
					}else{
						SERDac.chgSERCurrentProcess(sr.getSer().getSerId(), 1);
					}
				} catch (Exception e) {
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}

	// 回退SER，除了在SERReview表中插入外，还需要将SER的当前环节字段加以修改
	public static void FallBackSER(SERReview sr) {
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try {
					String sql = "INSERT INTO serreviews (review_id,ser_id,reviewer_id,servicesatisfaction,resultsatisfaction,review_content,review_time) "
							+ "VALUES (rewseq.nextval,?,?,0,0,?,?)";
					Dac.getJdbcTemplate().update(sql, new Object[] { sr.getSer().getSerId(), sr.getReviewer().getTpId(),sr.getReviewContent(), sr.getReviewTime() });
					// 将HM的当前环节字段加以修改
					SERDac.chgSERCurrentProcess(sr.getSer().getSerId(), -1);
				} catch (Exception e) {
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}
	
	// 审核通过SER,除了在SERReview表中插入外，还需要将SER的当前环节字段加以修改
	public static void VerifySER(SERReview sr,int flag) {
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try {
					String sql = "INSERT INTO serreviews (review_id,ser_id,reviewer_id,servicesatisfaction,resultsatisfaction,review_content,review_time,review_type) "
							+ "VALUES (rewseq.nextval,?,?,?,?,?,?,?)";
					Dac.getJdbcTemplate().update(sql, new Object[] { sr.getSer().getSerId(), sr.getReviewer().getTpId(),sr.getServiceSatisfaction(),sr.getResultSatisfaction(),sr.getReviewContent(), sr.getReviewTime(),sr.getReviewType() });
					// 将HM的当前环节字段加以修改
					if(flag==0){
						SERDac.chgSERCurrentProcess(sr.getSer().getSerId(), -1);
					}else{
						SERDac.chgSERCurrentProcess(sr.getSer().getSerId(), 2);
					}
				} catch (Exception e) {
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}

}
