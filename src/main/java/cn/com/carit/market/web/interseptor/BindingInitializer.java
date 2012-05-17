package cn.com.carit.market.web.interseptor;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import cn.com.carit.market.common.Constants;
import cn.com.carit.market.common.springmvc.DateConvertEditor;

/**
 * 自定义类型绑定器
 * @author <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a>
 *
 */
public class BindingInitializer implements WebBindingInitializer {

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMATTER);
		dateFormat.setLenient(false);
		binder.registerCustomEditor(String.class,new StringTrimmerEditor(false));
		binder.registerCustomEditor(Date.class,new DateConvertEditor());

	}

}
