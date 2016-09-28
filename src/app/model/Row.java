package app.model;

/**
 * @author baltzarmattsson
 * 2016-09-28
 * This class is designed to hold row-values from a result-set, for easier handling of the data
 */

public class Row {
	
	private String attribute1;
	private String attribute2;
	private String attribute3;
	private String attribute4;
	private String attribute5;
	private String attribute6;
	private String attribute7;
	private String attribute8;
	private String attribute9;
	private String attribute10;
	
	
	/** 
	 *  Basic constructors, this takes in strings and puts them on attribute1 -> 5, and the rest
	 *  i.e. attribute6 - 10 is put as an empty string, "". Look up Constructor overloading if unsure
	 * **/
	public Row(String att1, String att2, String att3, String att4, String att5) {
		this(att1, att2, att3, att4, att5, "", "", "", "", "");
	}
	
	/** 
	 * Constructor no. 2.
	 * If you want to set, for example, 6 attributes at once, create a new Constructor	
	 * **/
	public Row(String att1, String att2, String att3, String att4, String att5, String att6, String att7, String att8, String att9, String att10) {
		this.attribute1 = att1;
		this.attribute2 = att2;
		this.attribute3 = att3;
		this.attribute4 = att4;
		this.attribute5 = att5;
		this.attribute6 = att6;
		this.attribute7 = att7;
		this.attribute8 = att8;
		this.attribute9 = att9;
		this.attribute10 = att10;	
	}

	public String getAttribute1() {
		return attribute1;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public String getAttribute4() {
		return attribute4;
	}

	public String getAttribute5() {
		return attribute5;
	}

	public String getAttribute6() {
		return attribute6;
	}

	public String getAttribute7() {
		return attribute7;
	}

	public String getAttribute8() {
		return attribute8;
	}

	public String getAttribute9() {
		return attribute9;
	}

	public String getAttribute10() {
		return attribute10;
	}
	

	
	
}
