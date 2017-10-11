package com.telecom.weibao.dac;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.telecom.weibao.entity.CruisingReview;

public class CruisingReviewDac {
	static class CruisingReviewRowMapper implements RowMapper<CruisingReview> {
		@Override
		public CruisingReview mapRow(ResultSet rs, int rowNum) throws SQLException {
			CruisingReview cReview=new CruisingReview();
			cReview.getReviewer().setTpName(rs.getString("tp_name"));
			cReview.setReviewTime(rs.getTimestamp("review_time"));
			cReview.setSatisfaction(rs.getInt("satisfaction"));
			cReview.setReviewContent(rs.getString("review_content"));
			return cReview;
		}
	}
	
	// 根据cId获取cReview详情
	public static List<CruisingReview> getCruisingReviewsByCruisingId(long cId) {
		String sql = "SELECT tp_name,review_time,satisfaction,review_content "
				+ "FROM cruisingreviews c,telecompersonnels tp WHERE c.reviewer_id=tp.tp_id AND c.c_id=? "
				+ "ORDER BY review_time";
		return Dac.getJdbcTemplate().query(sql, new Object[] { cId }, new CruisingReviewRowMapper());
	}

	// 根据cId删除cReview
	public static void delCruisingReviews(long cId) {
		String sql = "DELETE cruisingreviews WHERE c_id=?";
		Dac.getJdbcTemplate().update(sql, new Object[] { cId });
	}
	
	// 点评Cruising，除了在CruisingReview表中插入外，还需要将Cruising的当前环节字段加以修改
	public static void ReviewCruising(CruisingReview cr) {
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try {
					String sql = "INSERT INTO cruisingreviews (review_id,c_id,reviewer_id,satisfaction,review_content,review_time) "
							+ "VALUES (rewseq.nextval,?,?,?,?,?)";
					Dac.getJdbcTemplate().update(sql,
							new Object[] { cr.getCruising().getcId(), cr.getReviewer().getTpId(),
									cr.getSatisfaction(), cr.getReviewContent(),cr.getReviewTime() });
					// 将Cruising的当前环节字段加以修改
					CruisingDac.chgCruisingCurrentProcess(cr.getCruising().getcId(), 1);
				} catch (Exception e) {
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}

	// 回退Cruising，除了在CruisingReview表中插入外，还需要将Cruising的当前环节字段加以修改
	public static void FallBackCruising(CruisingReview cr) {
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try {
					String sql = "INSERT INTO cruisingreviews (review_id,c_id,reviewer_id,satisfaction,review_content,review_time) "
							+ "VALUES (rewseq.nextval,?,?,0,?,?)";
					Dac.getJdbcTemplate().update(sql, new Object[] { cr.getCruising().getcId(), cr.getReviewer().getTpId(),
							cr.getReviewContent(), cr.getReviewTime() });
					// 将Cruising的当前环节字段加以修改
					CruisingDac.chgCruisingCurrentProcess(cr.getCruising().getcId(), -1);
				} catch (Exception e) {
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}

	// 审核通过Cruising，除了在CruisingReview表中插入外，还需要将Cruising的当前环节字段加以修改
	public static void VerifyCruising(CruisingReview cr) {
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try {
					String sql = "INSERT INTO cruisingreviews (review_id,c_id,reviewer_id,satisfaction,review_content,review_time) "
							+ "VALUES (rewseq.nextval,?,?,?,?,?)";
					Dac.getJdbcTemplate().update(sql,
							new Object[] { cr.getCruising().getcId(), cr.getReviewer().getTpId(),
									cr.getSatisfaction(),cr.getReviewContent(),cr.getReviewTime() });
					// 将Cruising的当前环节字段加以修改
					CruisingDac.chgCruisingCurrentProcess(cr.getCruising().getcId(),1);
				} catch (Exception e) {
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}

}
