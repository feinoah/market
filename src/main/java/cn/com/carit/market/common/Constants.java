package cn.com.carit.market.common;

import java.io.File;

public class Constants {
	public static final int PAGE_SIZE=10;
	public static final int STATUS_VALID=1;
	public static final int STATUS_INVALID=0;
	public static final int STATUS_LOCKED=2;
	public static final String DATE_TIME_FORMATTER="yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMATTER="yyyy-MM-dd";
	public static final String ADMIN_USER="adminUser";
	public static final String PORTAL_USER="portalUser";
	public static final String PASSWORD_ERROR_COUNT="passwordErrorCount";
	/**密码最多错误5次限制登录*/
	public static final int MAX_PWD_ERROR_COUNT=5;
	public static final String USER_ALL_MOUDLE="userAllMoudle";
	public static final String ANSWER_CODE="answerCode";
	
	public static final String BASE_PATH_ICON=File.separator+"resources"+File.separator+"attachment"+File.separator+"icons";
	public static final String BASE_PATH_IMAGE=File.separator+"resources"+File.separator+"attachment"+File.separator+"images";
	public static final String BASE_PATH_APK=File.separator+"resources"+File.separator+"attachment"+File.separator+"apk";
	public static final String BASE_PATH_PHOTOS=File.separator+"resources"+File.separator+"attachment"+File.separator+"photos";
}
