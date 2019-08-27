package whscheduler.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="at_webhosting")
public class ATWebhostingModel implements Serializable{

	private static final long serialVersionUID = 5878913852678411622L;
	@Id
	@Column(name="whID")
	private long ID;
	@Column(name="whClientID")
	long clientID;
	@Column(name="whClientType")
	int clientType;
	@Column(name="whDomain")
	String domain;
	@Column(name="whEmail")
	String email;
	@Column(name="whUserName")
	String userName;
	@Column(name="whUserPass")
	String userPass;
	@Column(name="whPackageID")
	long packageID;
	@Column(name="whUpgradePackageID")
	long upgradePackageID;
	@Column(name="whExpiryDate")
	long expiryDate;
	@Column(name="whActivationDate")	
	long activationDate;
	@Column(name="whLastModificationDate")
	long lastModificationTime;
	@Column(name="whIsBlocked")
	int isBlocked;
	@Column(name="whIsPrivileged")
	int isPrivileged;
	@Column(name="whCurrentStatus")
	int currentStatus;
	@Column(name="whLatestStatus")
	int latestStatus;
	@Column(name="whServerID")
	long serverID;
	@Column(name="whIsDeleted")
	boolean isDeleted;	
	@Column(name="whWriteCPanel")
	int cpanelWrittingStatus;
	
	public ATWebhostingModel() {
		
	}
    public ATWebhostingModel(long ID,long clientID,int clientType,String domain,String email,
    		String userName,String userPass,long packageID,long upgradePackageID,long expiryDate,
    		long activationDate,long lastModificationTime,int isBlocked,int isPrivileged,
    		int currentStatus,int latestStatus,long serverID,int cpanelWrittingStatus) {
		this.ID = ID;
		this.clientID = clientID;
		this.clientType = clientType;
		this.domain = domain;
		this.email = email;
		this.userName = userName;
		this.userPass = userPass;
		this.packageID = packageID;
		this.upgradePackageID = upgradePackageID;
		this.expiryDate =  expiryDate;
		this.activationDate =  activationDate;
		this.lastModificationTime = lastModificationTime;
		this.isBlocked =  isBlocked;
		this.isPrivileged =  isPrivileged;
		this.currentStatus =  currentStatus;
		this.latestStatus =  latestStatus;
		this.serverID =  serverID;
		this.cpanelWrittingStatus = cpanelWrittingStatus;
		
		
	}
	
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
	}
	public int getClientType() {
		return clientType;
	}
	public void setClientType(int clientType) {
		this.clientType = clientType;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPass() {
		return userPass;
	}
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	public long getPackageID() {
		return packageID;
	}
	public void setPackageID(long packageID) {
		this.packageID = packageID;
	}
	public long getUpgradePackageID() {
		return upgradePackageID;
	}
	public void setUpgradePackageID(long upgradePackageID) {
		this.upgradePackageID = upgradePackageID;
	}
	public long getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(long expiryDate) {
		this.expiryDate = expiryDate;
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
	public int getIsBlocked() {
		return isBlocked;
	}
	public void setIsBlocked(int isBlocked) {
		this.isBlocked = isBlocked;
	}
	public int getIsPrivileged() {
		return isPrivileged;
	}
	public void setIsPrivileged(int isPrivileged) {
		this.isPrivileged = isPrivileged;
	}
	public int getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(int currentStatus) {
		this.currentStatus = currentStatus;
	}
	public int getLatestStatus() {
		return latestStatus;
	}
	public void setLatestStatus(int latestStatus) {
		this.latestStatus = latestStatus;
	}
	public long getServerID() {
		return serverID;
	}
	public void setServerID(long serverID) {
		this.serverID = serverID;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public int getCpanelWrittingStatus() {
		return cpanelWrittingStatus;
	}
	public void setCpanelWrittingStatus(int cpanelWrittingStatus) {
		this.cpanelWrittingStatus = cpanelWrittingStatus;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "ATWebhostingModel [ ID="+ID
				+",clientID="+clientID
				+",clientType="+clientType
				+",domain="+domain
				+",email="+email
				+",userName="+userName
				+",userPass="+userPass
				+",packageID="+packageID
				+",upgradePackageID="+upgradePackageID
				+",expiryDate="+expiryDate
				+",activationDate="+activationDate
				+",lastModificationTime="+lastModificationTime
				+",isBlocked="+isBlocked
				+",isPrivileged="+isPrivileged
				+",currentStatus="+currentStatus
				+",serverID="+serverID
				+",isDeleted="+isDeleted
				+",cpanelWrittingStatus="+cpanelWrittingStatus
				+ "]";
	}

}
