package cn.com.carit.market.service.permission;

import java.util.List;
import java.util.Map;

import cn.com.carit.market.bean.BaseField;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;

public interface BaseFieldService {
	/**
	 * @param field
	 * @return
	 */
	int saveOrUpdate(BaseField field);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(int id);
	
	/**
	 * 按Id查询
	 * @param id
	 * @return
	 */
	BaseField queryById(int id);
	
	/**
	 * 按字段名字查询
	 * @param filed
	 * @return
	 */
	List<BaseField> queryByField(String filed);
	
	/**
	 * 查询已有字典集
	 * @return
	 */
	List<Map<String,Object>>  queryGroupByField();
	
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
	JsonPage<BaseField> queryByExemple(BaseField field, DataGridModel dgm);
	
	/**
	 * 检测字典名称是否存在
	 * @param field
	 * @return
	 */
	int checkField(String field);
	
	/**
	 * 按字段名返回limit条记录
	 * @param field
	 * @param limit
	 * @return
	 */
	List<BaseField> queryByField(String field, int limit);
}
