package cn.com.carit.market.common.utils;

import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import cn.com.carit.market.common.Constants;

@JsonAutoDetect
@JsonIgnoreProperties(value = { "currentPage", "pageSize", "startRow"})
public class JsonPage {

	// total row
	/** 当前页 */
	private int currentPage = 1;
	/** 每页显示数 */
	private int pageSize = Constants.PAGE_SIZE;
	/** 总行数 */
	private int total = 0;
	/*
		*//** 总页数 */
	/*
	 * private int totalPage;
	 */
	/** 当前页在数据库中的起始行 */
	private int startRow = 0;

	/** 查询参数保存 javabean的形式 */
	// private Object queryObject;

	/** 要显示的数据集 */
	private List<?> rows;

	public JsonPage() {
	}

	public JsonPage(int currentPage, int pageSize, int total) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.total = total;
	}

	public JsonPage(int currentPage, int pageSize) {
		super();
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public JsonPage(int pageSize) {
		this.pageSize = pageSize;
		this.currentPage = 1;
		this.total = 1;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		if (currentPage == 0) {
			return;
		}
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getEndRow() {
		return this.startRow + this.pageSize;
	}

	public int getStartRow() {
		if (this.currentPage > 1) {
			this.startRow = (this.currentPage - 1) * this.pageSize;
		} else {
			this.startRow = 0;
		}
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	/*
	 * public Object getQueryObject() { return queryObject; }
	 * 
	 * public void setQueryObject(Object queryObject) { this.queryObject =
	 * queryObject; }
	 */

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	/*
	 * public int getTotalPage() { this.totalPage = this.total / this.pageSize;
	 * if (this.total % this.pageSize != 0) { this.totalPage += 1; } return
	 * totalPage; }
	 * 
	 * public void setTotalPage(int totalPage) { this.totalPage = totalPage; }
	 */

	@Override
	public String toString() {
		return "JsonPage [currentPage=" + currentPage + ", pageSize="
				+ pageSize + ", totalRow=" + total + ", startRow="
				+ startRow + ", rows=" + rows + "]";
	}
	
}
