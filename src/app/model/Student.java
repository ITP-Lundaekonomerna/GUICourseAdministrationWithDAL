package app.model;

/**
 * @author baltzarmattsson
 * 2016-09-28
 *
 */

public class Student {

	private String pnbr;
	private String name;
	private String address;
	private String phoneNbr;
	
	public Student(String pnbr, String name, String address, String phoneNbr) {
		this.pnbr = pnbr;
		this.name = name;
		this.address = address;
		this.phoneNbr = phoneNbr;
	}
	
	
	public String getPnbr() {
		return pnbr;
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public String getPhoneNbr() {
		return phoneNbr;
	}
	
	
	// Denna är bra för att du kan skriva System.out.println(s) där s är en student, och då får du ut pnbr name address phonenbr direkt. Bra för testninig
	@Override
	public String toString() {
		return pnbr + " " + name + " " + address + " " + phoneNbr;
	}
	
	
}
