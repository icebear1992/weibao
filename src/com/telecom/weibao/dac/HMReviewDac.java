package com.telecom.weibao.dac;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.telecom.weibao.entity.HMReview;

public class HMReviewDac {
	static class HMReviewRowMapper implements RowMapper<HMReview> {
		@Override
		public HMReview mapRow(ResultSet rs, int rowNum) throws SQLException {
			HMReview hmReview=new HMReview();
			hmReview.getReviewer().setTpName(rs.getString("tp_name"));
			hmReview.setReviewTime(rs.getTimestamp("review_time"));
			hmReview.setTimeliness(rs.getInt("timeliness"));
			hmReview.setSatisfaction(rs.getInt("satisfaction"));
			hmReview.setReviewContent(rs.getString("review_content"));
			hmReview.setReviewType(rs.getInt("review_type"));
			System.out.println(hmReview);
			return hmReview;
		}
	}
	//根据hmId获取HMReview审核点评详情
	public static List<HMReview> getHMReviewsByHMId(long hmId){
		String sql="SELECT tp_name,review_time,timeliness,satisfaction,review_content "
				+"FROM hmreviews h,telecompersonnels tp WHERE h.reviewer_id=tp.tp_id AND h.hm_id=? "
				+"ORDER BY review_time";
		return Dac.getJdbcTemplate().query(sql, new Object[]{hmId},new HMReviewRowMapper());
	}
	//根据hmId获取HMReview审核点评修改详情
	public static List<HMReview> getHMReviewsAllByHMId(long hmId){
		System.out.println("sql");
		String sql="SELECT tp_name,review_time,timeliness,satisfaction,review_content,review_type FROM hmreviews h,telecompersonnels tp WHERE h.reviewer_id=tp.tp_id AND h.hm_id=? union select mp_name,review_time,timeliness,satisfaction,review_content,review_type from hmreviews h,MANUFACTURERPERSONNELS m WHERE h.CHANGER_ID=m.MP_ID and hm_id=? ORDER BY review_time desc";
		return Dac.getJdbcTemplate().query(sql, new Object[]{hmId,hmId},new HMReviewRowMapper());
	}
	
	//根据hmId删除HMReview
	public static void delHMReviews(long hmId){
		String sql="DELETE hmreviews WHERE hm_id=?";
		Dac.getJdbcTemplate().update(sql,new Object[]{hmId});
	}
	
	
	//点评HM，除了在HMReview表中插入外，还需要将HM的当前环节字段加以修改
	public static void ReviewHM(HMReview hr,int flag){
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>(){
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try{
					String sql = "INSERT INTO hmreviews (review_id,hm_id,reviewer_id,timeliness,satisfaction,review_content,review_time,review_type) "
							+ "VALUES (rewseq.nextval,?,?,?,?,?,?,?)";
					Dac.getJdbcTemplate().update(sql,new Object[]{hr.getHm().getHmId(),hr.getReviewer().getTpId(),hr.getTimeliness(),hr.getSatisfaction(),hr.getReviewContent(),hr.getReviewTime(),hr.getReviewType()});
					//将HM的当前环节字段加以修改
					if(flag==0){
						HMDac.chgHMCurrentProcess(hr.getHm().getHmId(), -1);					
					}else{
						HMDac.chgHMCurrentProcess(hr.getHm().getHmId(), 1);					
					}
				}catch(Exception e){
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}
	//回退HM，除了在HMReview表中插入外，还需要将HM的当前环节字段加以修改
	public static void FallBackHM(HMReview hr){
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>(){
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try{
					String sql = "INSERT INTO hmreviews (review_id,hm_id,reviewer_id,timeliness,satisfaction,review_content,review_time) "
							+ "VALUES (rewseq.nextval,?,?,0,0,?,?)";
					Dac.getJdbcTemplate().update(sql,new Object[]{hr.getHm().getHmId(),hr.getReviewer().getTpId(),hr.getReviewContent(),hr.getReviewTime()});
					//将HM的当前环节字段加以修改
					HMDac.chgHMCurrentProcess(hr.getHm().getHmId(), -1);					
				}catch(Exception e){
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}
	//审核通过HM，除了在HMReview表中插入外，还需要将HM的当前环节字段加以修改
	public static void VerifyHM(HMReview hr,int flag){
		Dac.getTransactionTemplate().execute(new TransactionCallback<Void>(){
			@Override
			public Void doInTransaction(TransactionStatus ts) {
				try{
					String sql = "INSERT INTO hmreviews (review_id,hm_id,reviewer_id,timeliness,satisfaction,review_content,review_time,review_type) "
							+ "VALUES (rewseq.nextval,?,?,?,?,?,?)";
					Dac.getJdbcTemplate().update(sql,new Object[]{hr.getHm().getHmId(),hr.getReviewer().getTpId(),hr.getTimeliness(),hr.getSatisfaction(),hr.getReviewContent(),hr.getReviewTime(),hr.getReviewType()});
					//将HM的当前环节字段加以修改
					if(flag==0){
						HMDac.chgHMCurrentProcess(hr.getHm().getHmId(), -1);
					}else{
						HMDac.chgHMCurrentProcess(hr.getHm().getHmId(), 2);
					}
				}catch(Exception e){
					e.printStackTrace();
					ts.setRollbackOnly();
				}
				return null;
			}
		});
	}

}
