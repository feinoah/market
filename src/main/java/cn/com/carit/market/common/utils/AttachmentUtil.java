package cn.com.carit.market.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class AttachmentUtil {
	private static final Logger logger = Logger.getLogger(AttachmentUtil.class);
	
	private Properties p;
	
	public static class Holder{
		private static AttachmentUtil INSTANCE=new AttachmentUtil();
	}
	
	public static AttachmentUtil getInstance(){
		return Holder.INSTANCE;
	}
	private AttachmentUtil(){
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(
				"resources"+File.separator+"attachment.properties");   
		p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			logger.error("init attachment.properties error..."+e.getMessage());
		}
	}

	public static void mkDir(String path){
		File file=new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
	}
	
	public static File newFile(String parentPath, String fileName){
		mkDir(parentPath);
		return new File(parentPath+File.separator+fileName);
	}
	
	public static Object getValue(String key) {
		return getInstance().p.get(key);
	}
	
	public static File getIconFile(String fileName){
		return newFile((String)getValue("attachment.icons"), fileName);
	}
	
	public static File getImageFile(String fileName){
		return newFile((String)getValue("attachment.images"), fileName);
	}
	
	public static File getApkFile(String fileName){
		return newFile((String)getValue("attachment.apks"), fileName);
	}
	
	public static File getPhotoFile(String fileName){
		return newFile((String)getValue("attachment.photos"), fileName);
	}
	
	public static String getIconPath(String fileName){
		return (String)getValue("attachment.icons")+(File.separator+fileName);
	}
	
	public static String getImagePath(String fileName){
		return (String)getValue("attachment.images")+(File.separator+fileName);
	}
	
	public static String getApkPath(String fileName){
		return (String)getValue("attachment.apks")+(File.separator+fileName);
	}
	
	public static String getPhotoPath(String fileName){
		return (String)getValue("attachment.photos")+(File.separator+fileName);
	}
	
	public static void main(String[] args) {
		System.out.println(getIconFile("test"));
		System.out.println(getIconPath("test"));
	}
}
