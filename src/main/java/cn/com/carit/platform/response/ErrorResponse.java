package cn.com.carit.platform.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "error")
public class ErrorResponse {

	@XmlAttribute
	private String code;
	@XmlAttribute
	private String message;
	@XmlAttribute
	private String solution;
	
	@XmlElement
	private List<SubError> subErrors;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public List<SubError> getSubErrors() {
		return subErrors;
	}

	public void setSubErrors(List<SubError> subErrors) {
		this.subErrors = subErrors;
	}

	@Override
	public String toString() {
		return "ErrorResponse [code=" + code + ", message=" + message
				+ ", solution=" + solution + ", subErrors=" + subErrors + "]";
	}
	
}
