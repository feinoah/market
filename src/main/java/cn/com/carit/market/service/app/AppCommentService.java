package cn.com.carit.market.service.app;
import java.util.List;

import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import  cn.com.carit.market.bean.app.AppComment;

/**
 * AppCommentService
 * Auto generated Code
 */
public interface AppCommentService {
	/**
	 * 增加
	 * @param appComment
	 * @return
	 */
	void saveOrUpdate(AppComment appComment);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(int id);
	
	/**
	 * 按应用Id删除
	 * @param appId
	 * @return
	 */
	int deleteByAppId(int appId);
	
	/**
	 * 按Id查询
	 * @param id
	 * @return
	 */
	AppComment queryById(int id);
	
	/**
	 * 查询
	 * @return
	 */
	List<AppComment> query();
	
	/**
	 * 条件查询
	 * @param appComment
	 * @return
	 */
	List<AppComment> queryByExemple(AppComment appComment);
	
	/**
	 * 带分页的条件查询
	 * @param appComment
	 * @param dgm
	 * @return
	 */
	JsonPage queryByExemple(AppComment appComment, DataGridModel dgm);
	
	/**
	 * 按样例获取总数
	 * @param appComment
	 * @return
	 */
	int getCount(AppComment appComment);
	
	/**
	 * 查询评论
	 * @param appId
	 * @param dgm
	 * @return
	 */
	JsonPage queryComment(int appId, DataGridModel dgm);
}
