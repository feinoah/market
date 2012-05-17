package cn.com.carit.market.dao.app;
import java.util.List;

import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import  cn.com.carit.market.bean.app.AppAttachment;

/**
 * AppAttachmentDao
 * Auto generated Code
 */
public interface AppAttachmentDao {
	
	/**
	 * 增加
	 * @param AppAttachment
	 * @return
	 */
	int add(final AppAttachment AppAttachment);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(int id);
	
	/**
	 * 更新
	 * @param AppAttachment
	 * @return
	 */
	int update(AppAttachment appAttachment);
	
	/**
	 * 按Id查询
	 * @param id
	 * @return
	 */
	AppAttachment queryById(int id);
	
	/**
	 * 查询
	 * @return
	 */
	List<AppAttachment> query();
	
	/**
	 * 条件查询
	 * @param appAttachment
	 * @return
	 */
	List<AppAttachment> queryByExemple(AppAttachment appAttachment);
	
	/**
	 * 带分页的条件查询
	 * @param appAttachment
	 * @param dgm
	 * @return
	 */
	JsonPage queryByExemple(AppAttachment appAttachment, DataGridModel dgm);
	
	/**
	 * 按样例获取总数
	 * @param appAttachment
	 * @return
	 */
	int getCount(AppAttachment appAttachment);
	
}
