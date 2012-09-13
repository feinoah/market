package cn.com.carit.market.dao.obd;

import java.util.List;

import cn.com.carit.market.bean.obd.ObdData;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;

public interface ObdDataDao {
	/**
	 * 增加
	 * @param ObdData
	 * @return
	 */
	int add(final ObdData t);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(int id);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int batchDelete(String ids);
	
	/*
	 * 更新
	 * @param ObdData
	 * @return
	int update(ObdData t);
	 */
	
	/**
	 * 按Id查询
	 * @param id
	 * @return
	 */
	ObdData queryById(int id);
	
	/**
	 * 按照设备Id查询最新记录
	 * @param deviceId
	 * @return
	 */
	ObdData queryLastByDeviceId(String deviceId);
	
	/**
	 * 查询
	 * @return
	 */
	List<ObdData> query();
	
	/**
	 * 条件查询
	 * @param t
	 * @return
	 */
	List<ObdData> queryByExemple(ObdData t);
	
	/**
	 * 带分页的条件查询
	 * @param t
	 * @param dgm
	 * @return
	 */
	JsonPage<ObdData> queryByExemple(ObdData t, DataGridModel dgm);
}
