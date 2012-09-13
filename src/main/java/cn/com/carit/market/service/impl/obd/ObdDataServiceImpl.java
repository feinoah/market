package cn.com.carit.market.service.impl.obd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.carit.market.bean.obd.ObdData;
import cn.com.carit.market.bean.obd.ObdDataResponse;
import cn.com.carit.market.bean.obd.PostData;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.common.utils.JsonUtil;
import cn.com.carit.market.dao.obd.ObdDataDao;
import cn.com.carit.market.service.obd.ObdDataService;
@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class ObdDataServiceImpl implements ObdDataService {
	private final Logger logger = Logger.getLogger(getClass());
	@Resource
	private ObdDataDao dao;
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public int savePostData(PostData postData) throws Exception {
		// 构造Data
		ObdData t=new ObdData();
		t.setDate(postData.getDate());
		t.setDeviceId(postData.getDeviceID());
		t.setLocation(postData.getLocation());
		t.setError(JsonUtil.arrayToStr(postData.getError()));
		int index=1;
		for (Entry<Integer,Integer> e: postData.getData().entrySet()) {
			logger.info("第 "+index+" 个数据:"+e.getKey()+":"+e.getValue());
			if (e.getKey()!=null) {
				t.getValues()[e.getKey()-1]=e.getValue();//传过来的key 1~16
			}
			index++;
		}
		// 保存data
		return dao.add(t);
//		int dataId=dao.add(t);
//		if (postData.getError()!=null) {
//			for (int i = 0; i < postData.getError().length; i++) {
//				logger.info("第 "+(i+1)+" 个错误:"+postData.getError()[i]);
//				// 构造 error
//				ObdError error=new ObdError();
//				error.setDataId(dataId);
//				error.setDate(postData.getDate());
//				error.setDeviceId(postData.getDeviceID());
//				error.setLocation(postData.getLocation());
//				error.setErrorId(postData.getError()[i]);
//				obdErrorDao.add(error);
//			}
//		}
//		return index;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int add(ObdData t) {
		if (t==null) {
			logger.error("ObdData object is null...", new NullPointerException());
			return 0;
		}
		return dao.add(t);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int delete(int id) {
		if (id<=0) {
			logger.error("id must be bigger than 0...", new IllegalArgumentException());
			return 0;
		}
		return dao.delete(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int batchDelete(String ids) {
		if (StringUtils.hasText(ids)) {
			return dao.batchDelete(ids);
		}
		return 0;
	}

	@Override
	public ObdData queryById(int id) {
		if (id<=0) {
			logger.error("id must be bigger than 0...", new IllegalArgumentException());
			return null;
		}
		return dao.queryById(id);
	}

	
	@Override
	public ObdDataResponse queryLastByDeviceId(String deviceId) throws Exception {
		if (!StringUtils.hasText(deviceId)) {
			logger.error("deviceId is emtpy...", new NullPointerException());
			return null;
		} 
		ObdDataResponse res=new ObdDataResponse();
		ObdData data=dao.queryLastByDeviceId(deviceId);
		if (data!=null) {
			res.setDeviceID(data.getDeviceId());
			res.setLocation(data.getLocation());
			res.setDate(data.getDate());
			Map<Integer,Integer> map=new HashMap<Integer,Integer>();
			if (data.getValues()!=null) {
				for (int i = 0; i < data.getValues().length; i++) {
					map.put(i+1, data.getValues()[i]);
				}
			}
			res.setData(map);
			res.setError(JsonUtil.jsonToStrArray(data.getError()));
//			ObdError error=obdErrorDao.queryLastByDeviceId(deviceId);
//			if (error!=null) {
//				res.setError(error.getErrorId());
//			}
//			List<ObdError> errorList=obdErrorDao.queryByDataId(data.getId());
//			List<String> errors=new ArrayList<String>();
//			if (errorList!=null&&errorList.size()>0) {
//				for (ObdError temp : errorList) {
//					errors.add(temp.getErrorId());
//				}
//			}
//			res.setError(errors.toArray());
		}
		return res;
	}

	@Override
	public List<ObdData> query() {
		return dao.query();
	}

	@Override
	public List<ObdData> queryByExemple(ObdData t) {
		if (t==null) {
			logger.error("must be given an exemple...", new NullPointerException());
			return null;
		}
		return dao.queryByExemple(t);
	}

	@Override
	public JsonPage<ObdData> queryByExemple(ObdData t, DataGridModel dgm) {
		if (t==null) {
			logger.error("must be given an exemple...", new NullPointerException());
			return null;
		}
		return dao.queryByExemple(t, dgm);
	}

}
