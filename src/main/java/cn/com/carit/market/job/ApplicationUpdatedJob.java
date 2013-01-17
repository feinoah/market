package cn.com.carit.market.job;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.com.carit.market.service.app.SystemMessageService;
@Service
public class ApplicationUpdatedJob {
	private final Logger logger=Logger.getLogger(getClass());
	
	@Scheduled(cron="0 0 3 * * ?")
	protected void work() {
		logger.info(" start...");
		WebApplicationContext context=ContextLoader.getCurrentWebApplicationContext();
		SystemMessageService systemMessageService=(SystemMessageService) context.getBean("systemMessageServiceImpl");
		systemMessageService.addAppUpdateNotice();
		logger.info(" end ...");
	}
	
	
}
