package cn.com.carit.market.service.app;
import java.util.List;
import java.util.Map;

import cn.com.carit.market.bean.app.AppComment;
import cn.com.carit.market.bean.portal.PortalAppComment;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;

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
	JsonPage<AppComment> queryByExemple(AppComment appComment, DataGridModel dgm);
	
	/**
	 * 查询评论
	 * @param appId
	 * @param dgm
	 * @return
	 */
	JsonPage<PortalAppComment> queryComment(int appId, DataGridModel dgm);
	
	/**
	 * 统计评论
	 * @param appId
	 * @return
	 */
	Map<String,Object> statComment(int appId);
	
	/**
	 * 统计评论级别
	 * @param appId
	 * @return
	 */
	List<Map<String, Object>>  statCommentGrade(int appId);
}
