package whscheduler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="at_webhosting_package")
public class WebHostingPackageInfoModel {
	
	@Id
	@Column(name="webPackID")
	long ID;
	@Column(name="webPackName")
	String packageName;
	@Column(name="webPackPrice")
	double price;
	@Column(name="webPackIsVisible")
	int isDisplayed;
	@Column(name="webPackIsDeleted")
	int isDeleted;
	@Column(name="webPackActivationDate")
	long activationDate;
	@Column(name="webPackLastModificationTime")
	long lastModificationTime;
	@Column(name="wpdPackageType")
	int packageType;
	@Column(name="webPackWriteCPanel")
	int cpanelWrittingStatus;
	
	public WebHostingPackageInfoModel() {
		
	}
	
    public WebHostingPackageInfoModel(long ID,String packageName,double price,int isDisplayed,
    		int isDeleted,long activationDate,long lastModificationTime,int packageType,int cpanelWrittingStatus) {
    	this.ID = ID;
    	this.packageName = packageName;
    	this.price = price;
    	this.isDisplayed = isDisplayed;
    	this.isDeleted = isDeleted;
    	this.activationDate = activationDate;
    	this.lastModificationTime = lastModificationTime;
    	this.packageType = packageType;
    	this.cpanelWrittingStatus =cpanelWrittingStatus;
    	
    	
	}

	
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getIsDisplayed() {
		return isDisplayed;
	}
	public void setIsDisplayed(int isDisplayed) {
		this.isDisplayed = isDisplayed;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	public long getActivationDate() {
		return activationDate;
	}
	public void setActivationDate(long activationDate) {
		this.activationDate = activationDate;
	}
	public long getLastModificationTime() {
		return lastModificationTime;
	}
	public void setLastModificationTime(long lastModificationTime) {
		this.lastModificationTime = lastModificationTime;
	}
	public int getPackageType() {
		return packageType;
	}
	public void setPackageType(int packageType) {
		this.packageType = packageType;
	}
	public int getCpanelWrittingStatus() {
		return cpanelWrittingStatus;
	}
	public void setCpanelWrittingStatus(int cpanelWrittingStatus) {
		this.cpanelWrittingStatus = cpanelWrittingStatus;
	}
	
	@Override
	public String toString() {
		return "WebHostingPackageInfoModel [ ID="+ID
				+",packageName="+packageName
				+",price="+price
				+",isDisplayed="+isDisplayed
				+",isDeleted="+isDeleted
				+",activationDate="+activationDate
				+",lastModificationTime="+lastModificationTime
				+",packageType="+packageType
				+",cpanelWrittingStatus="+cpanelWrittingStatus
				+ "]";
	}

}
