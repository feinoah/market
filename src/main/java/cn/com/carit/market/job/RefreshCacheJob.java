package cn.com.carit.market.job;

import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.com.carit.market.web.CacheManager;

/**
 * <p>
 * <b>功能说明：</b>缓存更新Job，每50分钟执行
 * </p>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * @date 2012-12-12
 */
@Service
public class RefreshCacheJob   {

	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Scheduled(cron="0 0/50 * * * ?")
	public void work()
			throws JobExecutionException {
		logger.info("refresh cache start...");
		CacheManager.getInstance().refreshCache();
		logger.info("refresh cache end...");
	}

}
