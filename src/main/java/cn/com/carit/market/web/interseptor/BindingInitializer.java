package cn.com.carit.market.web.interseptor;

import java.util.Date;

import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import cn.com.carit.market.common.springmvc.DateConvertEditor;
import cn.com.carit.market.common.springmvc.IntEditor;
import cn.com.carit.market.common.springmvc.LongEditor;

/**
 * 自定义类型绑定器
 * @author <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a>
 *
 */
public class BindingInitializer implements WebBindingInitializer {

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
//		dateFormat.setLenient(false);
		binder.registerCustomEditor(String.class,new StringTrimmerEditor(false));
		binder.registerCustomEditor(Integer.class,new CustomNumberEditor(Integer.class, null, true));
		binder.registerCustomEditor(Long.class, new CustomNumberEditor(Long.class,null, true));
		binder.registerCustomEditor(Double.class,  new CustomNumberEditor(Double.class, null, true));
		binder.registerCustomEditor(Byte.class, new CustomNumberEditor(Byte.class, null, true));
		binder.registerCustomEditor(Float.class, new CustomNumberEditor(Float.class, null, true));
		binder.registerCustomEditor(int.class,new IntEditor());
		binder.registerCustomEditor(long.class, new LongEditor());
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(Date.class,new DateConvertEditor());
	}

}
