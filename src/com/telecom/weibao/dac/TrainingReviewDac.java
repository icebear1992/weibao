package com.telecom.weibao.dac;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.telecom.weibao.entity.TrainingReview;

public class TrainingReviewDac {
	
	static class TrainingReviewRowMapper implements RowMapper<TrainingReview>{
		@Override
		public TrainingReview mapRow(ResultSet rs, int rowNum) throws SQLException {
			TrainingReview tr=new TrainingReview();
			tr.getReviewer().setTpName(rs.getString("tp_name"));
			tr.setSatisfaction(rs.getInt("satisfaction"));
			tr.setReviewContent(rs.getString("review_content"));
			tr.setReviewTime(rs.getTimestamp("review_time"));
			return tr;
		}		
	}
	
	//根据tId获取TrainingReview详情
	public static List<TrainingReview> getTrainingReviewsByTId(long tId){
		String sql="SELECT tp_name,satisfaction,review_content,review_time FROM trainingreviews t,telecompersonnels tp WHERE t.reviewer_id=tp.tp_id"
				+" AND t.t_id=? ORDER BY review_time";
		return Dac.getJdbcTemplate().query(sql, new Object[]{tId},new TrainingReviewRowMapper());
	}
	
	//根据tId删除TrainingReview
	public static void delTrainingReviews(long tId) {
		String sql = "DELETE trainingreviews WHERE t_id=?";
		Dac.getJdbcTemplate().update(sql, new Object[] { tId });
	}
	
	
	
	// 点评Training，除了在TrainingReview表中插入外，还需要将Training的当前环节字段加以修改
	public static void ReviewTraining(TrainingReview tr) {
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try {
					String sql = "INSERT INTO trainingreviews (review_id,t_id,reviewer_id,satisfaction,review_content,review_time) "
							+ "VALUES (rewseq.nextval,?,?,?,?,?)";
					Dac.getJdbcTemplate().update(sql,
							new Object[] { tr.getTraining().gettId(), tr.getReviewer().getTpId(),
									tr.getSatisfaction(), tr.getReviewContent(),tr.getReviewTime() });
					// 将Cruising的当前环节字段加以修改
					TrainingDac.chgTrainingCurrentProcess(tr.getTraining().gettId(), 1);
				} catch (Exception e) {
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}

	// 回退Training，除了在TrainingReview表中插入外，还需要将Training的当前环节字段加以修改
	public static void FallBackTraining(TrainingReview tr) {
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try {
					String sql = "INSERT INTO trainingreviews (review_id,t_id,reviewer_id,satisfaction,review_content,review_time) "
							+ "VALUES (rewseq.nextval,?,?,0,?,?)";
					Dac.getJdbcTemplate().update(sql, new Object[] { tr.getTraining().gettId(), tr.getReviewer().getTpId(),
							tr.getReviewContent(), tr.getReviewTime() });
					// 将Cruising的当前环节字段加以修改
					TrainingDac.chgTrainingCurrentProcess(tr.getTraining().gettId(), -1);
				} catch (Exception e) {
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}

	// 审核通过Training，除了在TrainingReview表中插入外，还需要将Training的当前环节字段加以修改
	public static void VerifyTraining(TrainingReview tr) {
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try {
					String sql = "INSERT INTO trainingreviews (review_id,t_id,reviewer_id,satisfaction,review_content,review_time) "
							+ "VALUES (rewseq.nextval,?,?,?,?,?)";
					Dac.getJdbcTemplate().update(sql,
							new Object[] { tr.getTraining().gettId(), tr.getReviewer().getTpId(),
									tr.getSatisfaction(), tr.getReviewContent(),tr.getReviewTime() });
					// 将Cruising的当前环节字段加以修改
					TrainingDac.chgTrainingCurrentProcess(tr.getTraining().gettId(),1);
				} catch (Exception e) {
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}
	
	
}
