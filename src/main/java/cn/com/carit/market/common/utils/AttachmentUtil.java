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
		int index=fileName.lastIndexOf(File.separator);
		if (index!=-1) {
			fileName=fileName.substring(index);
		}
		return (String)getValue("attachment.icons")+(File.separator+fileName);
	}
	
	public static String getImagePath(String fileName){
		int index=fileName.lastIndexOf(File.separator);
		if (index!=-1) {
			fileName=fileName.substring(index);
		}
		return (String)getValue("attachment.images")+(File.separator+fileName);
	}
	
	public static String getApkPath(String fileName){
		int index=fileName.lastIndexOf(File.separator);
		if (index!=-1) {
			fileName=fileName.substring(index);
		}
		return (String)getValue("attachment.apks")+(File.separator+fileName);
	}
	
	public static String getPhotoPath(String fileName){int index=fileName.lastIndexOf(File.separator);
	if (index!=-1) {
		fileName=fileName.substring(index);
	}
		return (String)getValue("attachment.photos")+(File.separator+fileName);
	}
	
	public static boolean deleteFile(String fileName){
		File file = new File(fileName);
		return file.delete();
	}
	
	public static boolean deleteIcon(String fileName){
		return deleteFile(getIconPath(fileName));
	}
	
	public static boolean deleteImage(String fileName){
		return deleteFile(getImagePath(fileName));
	}
	
	public static boolean deletePhoto(String fileName){
		return deleteFile(getPhotoPath(fileName));
	}
	
	public static boolean deleteApk(String fileName){
		return deleteFile(getApkPath(fileName));
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(getIconFile("test"));
		System.out.println(getIconPath("test"));
//		System.out.println(getImageFile("test"));
//		File image=getImageFile("Blue hills.jpg");
//		FileOutputStream os=new FileOutputStream(getImageFile("test.txt"));
//		FileImageOutputStream os=new FileImageOutputStream(getImageFile("test.jpg"));
//		FileImageInputStream is=new FileImageInputStream(image);
//		byte[] buffer = new byte[1024];
//		int i=-1;
//		while ((i = is.read(buffer)) != -1) {
//			os.write(buffer, 0, i);
//		   }
//		os.flush();
//		os.close();
//		is.close();
		System.out.println(deleteFile(getImagePath("test.jpg")));
	}
}
