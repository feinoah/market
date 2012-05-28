package cn.com.carit.market.bean.portal;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * AppCatalog
 * Auto generated Code
 */
public class PortalAppCatalog  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2364638845600437095L;
	@JsonIgnore
	private String local;
	/**
	 * id
	 */
	private int id;
	/**
	 * name
	 */
	private String name;
	/**
	 * description
	 */
	private String description;

	public void setId(int value) {
		this.id = value;
	}
	public int getId() {
		return this.id;
	}
	public void setName(String value) {
		this.name = value;
	}
	public String getName() {
		return this.name;
	}
	public void setDescription(String value) {
		this.description = value;
	}
	public String getDescription() {
		return this.description;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	
}
