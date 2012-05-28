package cn.com.carit.market.bean.portal;

import java.io.Serializable;

public class PortalAppComment  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5622036003053105842L;
	/**
	 * grade
	 */
	private Integer grade;
	/**
	 * comment
	 */
	private String comment;
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
