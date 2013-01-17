package cn.com.carit.market.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.xmlpull.v1.XmlPullParser;

import android.util.TypedValue;
import brut.androlib.res.decoder.AXmlResourceParser;
/**
 * <p>
 * <b>功能说明：</b>APK文件解析帮助类
 * </p>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * @date 2012-12-25
 */
public class AnalysisApkUtils {
	private static final float RADIX_MULTS[] = { 0.00390625F, 3.051758E-005F,
			1.192093E-007F, 4.656613E-010F };
	private static final String DIMENSION_UNITS[] = { "px", "dip", "sp", "pt", "in",
			"mm", "", "" };
	private static final String FRACTION_UNITS[] = { "%", "%p", "", "", "", "", "", "" };

	private static final Map<String, String> apkInfo = new HashMap<String, String>();

	public static void analysis(File apkFile) throws Exception {
		if (apkFile == null) {
			throw new NullPointerException();
		}
		if (!apkFile.exists()) {
			throw new FileNotFoundException();
		}
		apkInfo.clear();
		ZipFile zipFile = new ZipFile(apkFile);
		Enumeration<?> enumeration = zipFile.entries();
		ZipEntry zipEntry = null;
		apkInfo.clear();
		while (enumeration.hasMoreElements()) {
			zipEntry = (ZipEntry) enumeration.nextElement();
			if (zipEntry.isDirectory()) {

			} else {
				if ("AndroidManifest.xml".equals(zipEntry.getName())) {
					try {
						AXmlResourceParser parser = new AXmlResourceParser();
						parser.open(zipFile.getInputStream(zipEntry));
						while (true) {
							int type = parser.next();
							if (type == XmlPullParser.END_DOCUMENT) {
								break;
							}
							switch (type) {
							case XmlPullParser.START_TAG: {
								for (int i = 0; i != parser.getAttributeCount(); ++i) {
									if ("versionName".equals(parser
											.getAttributeName(i))) {
										apkInfo.put("versionName",
												getAttributeValue(parser, i));
									} else if ("package".equals(parser
											.getAttributeName(i))) {
										apkInfo.put("package",
												getAttributeValue(parser, i));
									}
//									apkInfo.put(parser.getAttributeName(i),
//											getAttributeValue(parser, i));
								}
							}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		}
	}
	
	public static void analysis(String apkUrl) throws Exception {
		analysis(new File(apkUrl));
	}

	private static String getAttributeValue(AXmlResourceParser parser, int index) {
		int type = parser.getAttributeValueType(index);
		int data = parser.getAttributeValueData(index);
		if (type == TypedValue.TYPE_STRING) {
			return parser.getAttributeValue(index);
		}
		if (type == TypedValue.TYPE_ATTRIBUTE) {
			return String.format("?%sX", getPackage(data), data);
		}
		if (type == TypedValue.TYPE_REFERENCE) {
			return String.format("@%sX", getPackage(data), data);
		}
		if (type == TypedValue.TYPE_FLOAT) {
			return String.valueOf(Float.intBitsToFloat(data));
		}
		if (type == TypedValue.TYPE_INT_HEX) {
			return String.format("0xX", data);
		}
		if (type == TypedValue.TYPE_INT_BOOLEAN) {
			return data != 0 ? "true" : "false";
		}
		if (type == TypedValue.TYPE_DIMENSION) {
			return Float.toString(complexToFloat(data))
					+ DIMENSION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
		}
		if (type == TypedValue.TYPE_FRACTION) {
			return Float.toString(complexToFloat(data))
					+ FRACTION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
		}
		if (type >= TypedValue.TYPE_FIRST_COLOR_INT
				&& type <= TypedValue.TYPE_LAST_COLOR_INT) {
			return String.format("#X", data);
		}
		if (type >= TypedValue.TYPE_FIRST_INT
				&& type <= TypedValue.TYPE_LAST_INT) {
			return String.valueOf(data);
		}
		return String.format("<0x%X, type 0xX>", data, type);
	}

	private static String getPackage(int id) {
		if (id >>> 24 == 1) {
			return "android:";
		}
		return "";
	}

	// ///////////////////////////////// ILLEGAL STUFF, DONT LOOK :)
	public static float complexToFloat(int complex) {
		return (complex & 0xFFFFFF00) * RADIX_MULTS[(complex >> 4) & 3];
	}

	public static String getVersionName() {
		return apkInfo.get("versionName") == null ? "1.0" : apkInfo
				.get("versionName");
	}

	public static String getPackage() {
		return apkInfo.get("package") == null ? "1.0" : apkInfo.get("package");
	}
	
	public static void main(String[] args) throws Exception {
		AnalysisApkUtils.analysis("D:/Users/ivan/Downloads/微信.apk");
		System.out.println(AnalysisApkUtils.getVersionName());
		System.out.println(AnalysisApkUtils.getPackage());
	}
}
