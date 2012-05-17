package cn.com.carit.market.dao;

import java.util.List;

import cn.com.carit.market.bean.BaseField;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;

public interface BaseFieldDao {
	/**
	 * 增加
	 * @param field
	 * @return
	 */
	int add(BaseField field);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(int id);
	
	/**
	 * 更新
	 * @param field
	 * @return
	 */
	int update(BaseField field);
	
	/**
	 * 按Id查询
	 * @param id
	 * @return
	 */
	BaseField queryById(int id);
	
	/**
	 * 查询
	 * @return
	 */
	List<BaseField> query();
	
	/**
	 * 条件查询
	 * @param field
	 * @return
	 */
	List<BaseField> queryByExemple(BaseField field);
	
	/**
	 * 带分页的条件查询
	 * @param field
	 * @param dgm
	 * @return
	 */
	JsonPage queryByExemple(BaseField field, DataGridModel dgm);
	
	/**
	 * 按样例获取总数
	 * @param field
	 * @return
	 */
	int getCount(BaseField field);
}
