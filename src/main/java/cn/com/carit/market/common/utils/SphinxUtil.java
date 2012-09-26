package cn.com.carit.market.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.sphx.api.SphinxClient;
import org.sphx.api.SphinxException;
import org.sphx.api.SphinxMatch;
import org.sphx.api.SphinxResult;
import org.sphx.api.SphinxWordInfo;
import org.springframework.util.StringUtils;

public class SphinxUtil {
	protected final static Logger logger = Logger.getLogger(SphinxUtil.class);
	private static volatile SphinxUtil INSTANCE = null;

	private static SphinxClient sphinxClient;
	private static String SPHINX_INDEX;
	private static Properties properties = new Properties();

	private SphinxUtil() {
		try {
			logger.info("init SphinxUtil INSTANCE start...");
			InputStream inStream = getClass().getResourceAsStream(
					"/resources/dataSource.properties");
			properties.load(inStream);
			 sphinxClient = new SphinxClient(
			 (String) properties.get("sphinx.host"),
			 Integer.parseInt((String) properties.get("sphinx.port")));
//			sphinxClient = new SphinxClient("192.168.0.241", 9312);
			 SPHINX_INDEX = (String) properties.get("sphinx.index");
//			SPHINX_INDEX = "application_cn_idx";
			sphinxClient.SetWeights(new int[] { 100, 1 });
			sphinxClient.SetLimits(0, Integer.MAX_VALUE);
			sphinxClient.SetMatchMode(SphinxClient.SPH_MATCH_EXTENDED2);
			sphinxClient.SetSortMode(SphinxClient.SPH_SORT_RELEVANCE, "");
			logger.info("init SphinxUtil INSTANCE end...");
		} catch (IOException e) {
			logger.error("init SphinxUtil INSTANCE error...");
			logger.error(e.getMessage());
		} catch (SphinxException e) {
			logger.error(e.getMessage());
		}
	}
	
	public static void init() {
		if (INSTANCE == null) {
			synchronized (AttachmentUtil.class) {
				if (INSTANCE == null) {
					INSTANCE = new SphinxUtil();
				}
			}
		}
	}
	public static String getValue(String key) {
		return (String) properties.get(key);
	}
	public static SphinxMatch[] getMatchs(String keywords)
			throws SphinxException {
		if (!StringUtils.hasText(keywords)) {
			logger.error("criteria is null");
			return null;
		}
		SphinxResult result = sphinxClient.Query(buildSearchQuery(keywords), SPHINX_INDEX);
		if (result == null) {
			logger.error(sphinxClient.GetLastError());
			return null;
		}
		if (sphinxClient.GetLastWarning() != null
				&& sphinxClient.GetLastWarning().length() > 0){
			logger.error(sphinxClient.GetLastError());
			logger.warn(sphinxClient.GetLastWarning()+"\r\n");
		}
		// Debug msg
		if (logger.isDebugEnabled()) {
			logger.debug("Query '" + keywords + "' retrieved " + result.total + " of "
				+ result.totalFound + " matches in " + result.time + " sec.");
			logger.debug("Query stats:");
			for (int i = 0; i < result.words.length; i++) {
				SphinxWordInfo wordInfo = result.words[i];
				logger.debug("\t'" + wordInfo.word + "' found "
						+ wordInfo.hits + " times in " + wordInfo.docs
						+ " documents");
			}
			logger.debug("\nMatches:");
			for (int i = 0; i < result.matches.length; i++) {
				SphinxMatch info = result.matches[i];
				System.out.println((i + 1) + ". id=" + info.docId + ", weight="
						+ info.weight);
				logger.debug((i + 1) + ". id=" + info.docId + ", weight="
						+ info.weight);
			}
		}
		return result.matches;
	}

	public static List<String> getApplicationIdsAsList(String keywords)
			throws SphinxException {
		List<String> appIdsList = new ArrayList<String>();
		SphinxMatch[] matches = getMatchs(keywords);
		if (matches != null && matches.length > 0) {
			for (SphinxMatch match : matches) {
				appIdsList.add(String.valueOf(match.docId));
			}
		}
		return appIdsList;
	}

	public static String getApplicationIdsAsStr(String keywords)
			throws SphinxException {
		StringBuilder ids = new StringBuilder();
		SphinxMatch[] matches = getMatchs(keywords);
		if (matches != null && matches.length > 0) {
			int size = 0;
			for (SphinxMatch match : matches) {
				ids.append(match.docId).append(", ");
				size++;
			}
			// 删除最后一个
			if (size > 0) {
				ids.delete(ids.lastIndexOf(", "), ids.length());
			}
			logger.info("Total record(s):" + size);
		}
		return ids.toString();
	}

	private static String buildSearchQuery(String keywords) {
		String keywordsArray[] = keywords.split(" ");
		StringBuilder searchFor = new StringBuilder();
		for (String key : keywordsArray) {
			if (StringUtils.hasText(key)) {
				searchFor.append(key);
				if (searchFor.length() > 1) {
					searchFor.append("*|*");
				}
			}
		}
		if (searchFor.lastIndexOf("|*")!=-1) {
			searchFor.delete(searchFor.lastIndexOf("|*"), searchFor.length());
		}
//		StringBuilder queryBuilder = new StringBuilder();
//		String query = searchFor.toString();
//		queryBuilder.append("@app_name *" + query + "  | ");
//		queryBuilder.append("@en_name *" + query + "  | ");
//		queryBuilder.append("@developer *" + query + "  | ");
//		queryBuilder.append("@catalog_name *" + query + "  | ");
//		queryBuilder.append("@en_catalog_en_name *" + query + " | ");
//		queryBuilder.append("@platform *" + query + " | ");
//		queryBuilder.append("@support_languages *" + query + " | ");
//		queryBuilder.append("@description *" + query + " | ");
//		queryBuilder.append("@en_description *" + query + " | ");
//		queryBuilder.append("@permission_desc *" + query + " | ");
//		queryBuilder.append("@en_permission_des *" + query + " | ");
//		queryBuilder.append("@features *" + query + " | ");
//		queryBuilder.append("@en_features *" + query + " | ");

//		logger.info("Sphinx Query: " + queryBuilder.toString());
//		return queryBuilder.toString();
		return searchFor.toString();
	}

	public static void main(String[] args) throws SphinxException {
		init();
		System.out.println(getApplicationIdsAsStr("Android 新浪"));
	}
}
