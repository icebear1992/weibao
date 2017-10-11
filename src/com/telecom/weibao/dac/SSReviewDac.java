package com.telecom.weibao.dac;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.telecom.weibao.entity.SSReview;

public class SSReviewDac {
	static class SSReviewRowMapper implements RowMapper<SSReview>{
		@Override
		public SSReview mapRow(ResultSet rs, int rowNum) throws SQLException {
			SSReview ssReview=new SSReview();
			ssReview.getReviewer().setTpName(rs.getString("tp_name"));
			ssReview.setSatisfaction(rs.getInt("satisfaction"));
			ssReview.setReviewContent(rs.getString("review_content"));
			ssReview.setReviewTime(rs.getTimestamp("review_time"));
			ssReview.setCurrentProcess(rs.getInt("currentprocess"));
			return ssReview;
		}		
	}
	
	//根据ssId获取SSReview详情
	public static List<SSReview> getSSReviewsBySSId(long ssId){
		String sql="SELECT tp_name,satisfaction,review_content,review_time,currentprocess "
				+"FROM ssreviews s,telecompersonnels t WHERE s.reviewer_id=t.tp_id AND s.ss_id=? "
				+"ORDER BY review_time";
		return Dac.getJdbcTemplate().query(sql, new Object[]{ssId},new SSReviewRowMapper());
	}
	
	//根据ssId删除SSReview
	public static void delSSReviews(long ssId){
		String sql="DELETE ssreviews WHERE ss_id=?";
		Dac.getJdbcTemplate().update(sql,new Object[]{ssId});
	}
	
	// 点评SS，除了在SSReview表中插入外，还需要将SS的当前环节字段加以修改
	public static void ReviewSS(SSReview sr) {
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try {
					String sql = "INSERT INTO ssreviews (review_id,ss_id,reviewer_id,satisfaction,review_content,review_time) "
							+ "VALUES (rewseq.nextval,?,?,?,?,?)";
					Dac.getJdbcTemplate().update(sql, new Object[] {sr.getSs().getSsId(), sr.getReviewer().getTpId(),
							 sr.getSatisfaction(), sr.getReviewContent(), sr.getReviewTime()});
					// 将HM的当前环节字段加以修改
					SSDac.chgSSCurrentProcess(sr.getSs().getSsId(), 1);
				} catch (Exception e) {
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}

	// 回退SS，除了在SSReview表中插入外，还需要将SS的当前环节字段加以修改
	public static void FallBackSS(SSReview sr) {
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try {
					String sql = "INSERT INTO ssreviews (review_id,ss_id,reviewer_id,satisfaction,review_content,review_time) "
							+ "VALUES (rewseq.nextval,?,?,0,?,?)";
					Dac.getJdbcTemplate().update(sql, new Object[] { sr.getSs().getSsId(), sr.getReviewer().getTpId(),sr.getReviewContent(), sr.getReviewTime() });
					// 将HM的当前环节字段加以修改
					SSDac.chgSSCurrentProcess(sr.getSs().getSsId(), -1);
				} catch (Exception e) {
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}
	
	// 审核通过SS，除了在SSReview表中插入外，还需要将SS的当前环节字段加以修改
	public static void VerifySS(SSReview sr) {
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try {
					String sql = "INSERT INTO ssreviews (review_id,ss_id,reviewer_id,satisfaction,review_content,review_time) "
							+ "VALUES (rewseq.nextval,?,?,?,?,?)";
					Dac.getJdbcTemplate().update(sql, new Object[] { sr.getSs().getSsId(), sr.getReviewer().getTpId(),
							sr.getSatisfaction(),sr.getReviewContent(), sr.getReviewTime() });
					// 将HM的当前环节字段加以修改
					SSDac.chgSSCurrentProcess(sr.getSs().getSsId(), 1);
				} catch (Exception e) {
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}
}
