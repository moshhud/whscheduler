package whscheduler.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "at_webhosting_req")
public class ATWebhostingRequestModel implements Serializable{

	private static final long serialVersionUID = 459500075993031770L;
	@Id
	@Column(name="whrnwrqID")
	private long ID;
	@Column(name="whrnwrqReqID")
	private long reqID;
	@Column(name="whrnwrqWebHostingID")
	long webHostingID;
	@Column(name="whrnwrqPackageID")
	long packageID;
	@Column(name="whrnwrqRequestType")
	int buyType;	
	@Column(name="whrnwrqRenewYear")
	int year;
	@Column(name="whrnwrqPackageType")
	int packageType;	
	@Column(name="whrnwrqLastModificationTime")
	long lastModificationTime;
	
	public ATWebhostingRequestModel() {
		
	}
	
    public ATWebhostingRequestModel(long ID,long reqID,long webHostingID,long packageID,int buyType,
 		int year,int packageType,long lastModificationTime) {
		this.ID = ID;
		this.reqID = reqID;
		this.webHostingID = webHostingID;
		this.packageID =  packageID;
		this.buyType = buyType;
		this.year = year;
		this.packageType = packageType;
		this.lastModificationTime = lastModificationTime;
		
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public long getReqID() {
		return reqID;
	}

	public void setReqID(long reqID) {
		this.reqID = reqID;
	}

	public long getWebHostingID() {
		return webHostingID;
	}

	public void setWebHostingID(long webHostingID) {
		this.webHostingID = webHostingID;
	}

	public long getPackageID() {
		return packageID;
	}

	public void setPackageID(long packageID) {
		this.packageID = packageID;
	}

	public int getBuyType() {
		return buyType;
	}

	public void setBuyType(int buyType) {
		this.buyType = buyType;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getPackageType() {
		return packageType;
	}

	public void setPackageType(int packageType) {
		this.packageType = packageType;
	}

	public long getLastModificationTime() {
		return lastModificationTime;
	}

	public void setLastModificationTime(long lastModificationTime) {
		this.lastModificationTime = lastModificationTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
	@Override
	public String toString() {
		return "ATWebhostingRequestModel [ID=" + ID 
				+ ", webHostingID=" + webHostingID 
				+ ", packageID=" + packageID
				+ ", buyType=" + buyType 
				+ ", year=" + year 
				+ ", packageType=" + packageType				
				+ ", lastModificationTime=" + lastModificationTime 
				+ "]";
	}
	


}
