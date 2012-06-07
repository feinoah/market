package cn.com.carit.market.dao.app;
import java.util.List;

import cn.com.carit.market.bean.app.AppComment;
import cn.com.carit.market.bean.portal.PortalAppComment;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;

/**
 * AppCommentDao
 * Auto generated Code
 */
public interface AppCommentDao {
	
	/**
	 * 增加
	 * @param AppComment
	 * @return
	 */
	int add(final AppComment AppComment);
	
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
	 * 更新
	 * @param AppComment
	 * @return
	 */
	int update(AppComment appComment);
	
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
	JsonPage<AppComment> queryByExemple(AppComment appComment, DataGridModel dgm);
	
	/**
	 * 查询评论
	 * @param appId
	 * @param dgm
	 * @return
	 */
	JsonPage<PortalAppComment> queryComment(int appId, DataGridModel dgm);
	
	double queryAvgGrade(int appId);
}
