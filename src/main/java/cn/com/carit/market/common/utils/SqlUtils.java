package cn.com.carit.market.common.utils;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class SqlUtils {
	/**
     * <code>
     * getSelectSql("T_TABLE t", "t.C1, t.C2, t.C3") = "SELECT t.C1, t.C2, t.C3 FROM T_TABLE t";
     * <code>
     * @param table
     * @param columns
     * @return
     */
	public static String getSelectSql(String table, String columns) {
		if (StringUtils.isEmpty(table)  || StringUtils.isEmpty(columns)) {
            return null;
        }
		return String.format("SELECT %1$s FROM %2$s", columns, table);
	}
	
	/**
	 * <code>
	 * getSelectSql("T_TABLE t", "t.C1, t.C2, t.C3", "t.C1=1") = "SELECT t.C1, t.C2, t.C3 FROM T_TABLE t WHERE t.C1=1";
	 * </code>
	 * @param table
	 * @param columns
	 * @param where
	 * @return
	 */
	public static String getSelectSql(String table, String columns, String where) {
		if (StringUtils.isEmpty(where)) {
			return getSelectSql(table, columns);
		}
		return String.format("SELECT %1$s FROM %2$s WHERE %3$s", columns, table, where);
	}
	
	/**
     * <code>
     * getInsertSql("T_TABLE", "C1, C2, C3") = "INSERT INTO T_TABLE (C1, C2, C3) VALUES (?, ?, ?)";
     * <code>
     * @param table
     * @param columns
     * @return
     */
    public static String getInsertSql(String table, String columns) {
    	if (StringUtils.isEmpty(table)  || StringUtils.isEmpty(columns)) {
            return null;
        }
        String params = getInsertParams(columns);
        String sql = String.format("INSERT INTO %1$s (%2$s) VALUES (%3$s)",
                table, columns, params);
        return sql;
    }
    
    /**
     * <code>
     * getUpdateSql("T_TABLE", "C1, C2, C3") = "UPDATE T_TABLE SET C1 = ?, C2 = ?, C3 = ?";
     * </code>
     * @param table
     * @param columns
     * @return
     */
    public static String getUpdateSql(String table, String columns) {
        return getUpdateSql(table, columns, null);
    }
    
    /**
     * <code>
     * getUpdateSql("T_TABLE", "C1, C2, C3", "C1 = 1") = "UPDATE T_TABLE SET C1 = ?, C2 = ?, C3 = ? WHERE C1 = 1";
     * </code>
     * @param table
     * @param columns
     * @param where
     * @return
     */
    public static String getUpdateSql(String table, String columns, String where) {
    	if (StringUtils.isEmpty(table)  || StringUtils.isEmpty(columns)) {
            return null;
        }
        
        String params = getUpdateParams(columns);
        
        String sql = null;
        if (null == where) {
            sql = String.format("UPDATE %1$s SET %2$s", table, params);
            return getUpdateSql(table, columns);
        } else {
            sql = String.format("UPDATE %1$s SET %2$s WHERE %3$s",
                    table, params, where);
        }
        return sql;
    }
    
    /**
     * <code>
     * getDeleteSql("T_TABLE") = "DELETE FROM T_TABLE";
     * </code>
     * @param table
     * @return
     */
    public static String getDeleteSql(String table) {
        return getDeleteSql(table, null);
    }
    
    /**
     * <code>
     * getDeleteSql("T_TABLE", "C1 = 1 AND C2 = ?") = "DELETE FROM T_TABLE WHERE C1 = 1 AND C2 = ?";
     * </code>
     * @param table
     * @param where
     * @return
     */
    public static String getDeleteSql(String table, String where) {
        String sql = null;
        if (StringUtils.isEmpty(where)) {
            sql = String.format("DELETE FROM %1$s", table);
        } else {
            sql = String.format("DELETE FROM %1$s WHERE %2$s", table, where);
        }
        return sql;
    }
    
    /**
     * <code>
     * getInsertParams("c1, c2, c3") = "?, ?, ?";
     * </code>
     * @param columns
     * @return
     */
    public static String getInsertParams(String columns) {
        if (StringUtils.isEmpty(columns)) {
            return null;
        }
        String[] cs = columns.replaceAll("\\s", "").split(",");
        return getInsertParams(cs);
    }
    
    /**
     * <code>
     * getInsertParams(new String[]{"c1", "c2", "c3"}) = "?, ?, ?";
     * </code>
     * @param columns
     * @return
     */
    public static String getInsertParams(String[] columns) {
        if (null == columns) {
            return null;
        }
        String[] params = new String[columns.length];
        Arrays.fill(params, "?");
        return Arrays.toString(params).replaceAll("[\\[\\]]", "");
    }
    
    /**
     * <pre>
     * List = new ArrayList(0);
     * list.add("c1");
     * list.add("c2");
     * list.add("c3");
     * getInsertParams(list) = "?, ?, ?";
     * </pre>
     * @param columns
     * @return
     */
    public static String getInsertParams(List<String> columns) {
        if (null == columns) {
            return null;
        }
        return getInsertParams(columns.toArray(new String[columns.size()]));
    }
    
    /**
     * <code>
     * getUpdateParams("c1, c2, c3") = "c1 = ?, c2 = ?, c3 = ?";
     * </code>
     * @param columns
     * @return
     */
    public static String getUpdateParams(String columns) {
    	if (StringUtils.isEmpty(columns)) {
            return null;
        }
        String[] cs = columns.replaceAll("\\s", "").split(",");
        return getUpdateParams(cs);
    }
    
    /**
     * <code>
     * getUpdateParams(new String[]{"c1", "c2", "c3"}) = "c1 = ?, c2 = ?, c3 = ?";
     * </code>
     * @param columns
     * @return
     */
    public static String getUpdateParams(String[] columns) {
        String[] params = Arrays.copyOf(columns, columns.length);
        for (int i = 0; i < params.length; i++) {
            params[i] += " = ?";
        }
        return Arrays.toString(params).replaceAll("[\\[\\]]", "");
    }
    
    /**
     * <pre>
     * List = new ArrayList(0);
     * list.add("c1");
     * list.add("c2");
     * list.add("c3");
     * getUpdateParams(list) = "c1 = ?, c2 = ?, c3 = ?";
     * </pre>
     * @param columns
     * @return
     */
    public static String getUpdateParams(List<String> columns) {
        if (null == columns) {
            return null;
        }
        return getUpdateParams(columns.toArray(new String[columns.size()]));
    }
    
    public static void main(String[] args) {
        System.out.println(getInsertSql("T_TABLE t", "C1, C2, C3"));
        System.out.println(getUpdateSql("T_TABLE t", "C1, C2, C3", "C1 = 1"));
        System.out.println(getSelectSql("T_TABLE t", "C1, C2, C3", "C1 = 1"));
        System.out.println(getSelectSql("T_TABLE t", "COUNT(1)", "C1 = 1"));
    }
}
