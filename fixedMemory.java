
public class fixedMemory {
	
	   private String Pvalue = "null"; // ID is null if partition free, PN if allocated => (P1, P2..etc)
	   private String Mstatus = "free"; // free or allocated
	   private int Psize;
	   private int Saddress; 
	   private int Eaddress;
	   private int Fsize = -1; // size of partition - process size if status allocated or -1 if free
	   
	public String getPvalue() {
		return Pvalue;
	}
	public void setPvalue(String pvalue) {
		Pvalue = pvalue;
	}
	public String getMstatus() {
		return Mstatus;
	}
	public void setMstatus(String mstatus) {
		Mstatus = mstatus;
	}
	public int getPsize() {
		return Psize;
	}
	public void setPsize(int psize) {
		Psize = psize;
	}
	public int getSaddress() {
		return Saddress;
	}
	public void setSaddress(int saddress) {
		Saddress = saddress;
	}
	public int getEaddress() {
		return Eaddress;
	}
	public void setEaddress(int eaddress) {
		Eaddress = eaddress;
	}
	public int getFsize() {
		return Fsize;
	}
	public void setFsize(int fsize) {
		Fsize = fsize;
	}

}
