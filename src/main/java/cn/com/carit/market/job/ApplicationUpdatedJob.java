package cn.com.carit.market.job;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.com.carit.market.service.app.SystemMessageService;

public class ApplicationUpdatedJob extends QuartzJobBean {
	private final Logger logger=Logger.getLogger(getClass());
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.info(" start...");
		WebApplicationContext context=ContextLoader.getCurrentWebApplicationContext();
		SystemMessageService systemMessageService=(SystemMessageService) context.getBean("systemMessageServiceImpl");
		systemMessageService.addAppUpdateNotice();
		logger.info(" end ...");
	}
	
	
}
