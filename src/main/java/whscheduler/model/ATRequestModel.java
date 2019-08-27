package whscheduler.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "at_req")
public class ATRequestModel implements Serializable{

	private static final long serialVersionUID = 9069463242543422640L;
	@Id
	@Column(name = "arID")
	private long arID;
	@Column(name = "arRequestTypeID")
	private int arRequestTypeID;
	@Column(name = "arRootRequestID")
	private Long arRootRequestID;
	@Column(name = "arEntityTypeID")
	private int arEntityTypeID;
	@Column(name = "arEntityID")
	private long arEntityID;
	@Column(name = "arClientID")
	private long arClientID;
	@Column(name = "arReqTime")
	private long arReqTime;
	@Column(name = "arRequestedByAccountID")
	private long arRequestedByAccountID;
	@Column(name = "arIP")
	private String arIP;
	@Column(name = "arRequestedToAccountID",nullable = true,columnDefinition = "bigint(20) default null")
	private Long arRequestedToAccountID;
	@Column(name = "arPriority")
	private boolean arPriority;
	@Column(name = "arExpireTime")
	private long arExpireTime;
	@Column(name = "arDescription")
	private String arDescription;
	@Column(name = "arLastModificationTime")
	private long arLastModificationTime;
	@Column(name = "arIsDeleted")
	private boolean arIsDeleted;
	@Column(name = "arCompletionStatus")
	private boolean arCompletionStatus;
	@Column(name = "arSourceRequestID")
	private String arSourceRequestID;
	@Column(name = "arActionTypeID")
	private int arActionTypeID;
	
	public ATRequestModel() {
	}
	
	public long getArID() {
		return arID;
	}
	public void setArID(long arID) {
		this.arID = arID;
	}
	public int getArRequestTypeID() {
		return arRequestTypeID;
	}
	public void setArRequestTypeID(int arRequestTypeID) {
		this.arRequestTypeID = arRequestTypeID;
	}
	public Long getArRootRequestID() {
		return arRootRequestID;
	}
	public void setArRootRequestID(Long arRootRequestID) {
		this.arRootRequestID = arRootRequestID;
	}
	public void setArRequestedToAccountID(Long arRequestedToAccountID) {
		this.arRequestedToAccountID = arRequestedToAccountID;
	}
	public int getArEntityTypeID() {
		return arEntityTypeID;
	}
	public void setArEntityTypeID(int arEntityTypeID) {
		this.arEntityTypeID = arEntityTypeID;
	}
	public long getArEntityID() {
		return arEntityID;
	}
	public void setArEntityID(long arEntityID) {
		this.arEntityID = arEntityID;
	}
	public long getArClientID() {
		return arClientID;
	}
	public void setArClientID(long arClientID) {
		this.arClientID = arClientID;
	}
	public long getArReqTime() {
		return arReqTime;
	}
	public void setArReqTime(long arReqTime) {
		this.arReqTime = arReqTime;
	}
	public long getArRequestedByAccountID() {
		return arRequestedByAccountID;
	}
	public void setArRequestedByAccountID(long arRequestedByAccountID) {
		this.arRequestedByAccountID = arRequestedByAccountID;
	}
	public String getArIP() {
		return arIP;
	}
	public void setArIP(String arIP) {
		this.arIP = arIP;
	}
	public boolean isArPriority() {
		return arPriority;
	}
	public void setArPriority(boolean arPriority) {
		this.arPriority = arPriority;
	}
	public long getArExpireTime() {
		return arExpireTime;
	}
	public void setArExpireTime(long arExpireTime) {
		this.arExpireTime = arExpireTime;
	}
	public String getArDescription() {
		return arDescription;
	}
	public void setArDescription(String arDescription) {
		this.arDescription = arDescription;
	}
	public long getArLastModificationTime() {
		return arLastModificationTime;
	}
	public void setArLastModificationTime(long arLastModificationTime) {
		this.arLastModificationTime = arLastModificationTime;
	}
	public boolean isArIsDeleted() {
		return arIsDeleted;
	}
	public void setArIsDeleted(boolean arIsDeleted) {
		this.arIsDeleted = arIsDeleted;
	}
	public boolean isArCompletionStatus() {
		return arCompletionStatus;
	}
	public void setArCompletionStatus(boolean arCompletionStatus) {
		this.arCompletionStatus = arCompletionStatus;
	}
	public String getArSourceRequestID() {
		return arSourceRequestID;
	}
	public void setArSourceRequestID(String arSourceRequestID) {
		this.arSourceRequestID = arSourceRequestID;
	}
	public int getArActionTypeID() {
		return arActionTypeID;
	}
	public void setArActionTypeID(int arActionTypeID) {
		this.arActionTypeID = arActionTypeID;
	}
	@Override
	public String toString() {
		return "ATRequestModel [arID=" + arID + ", arRequestTypeID=" + arRequestTypeID + ", arEntityTypeID="
				+ arEntityTypeID + ", arEntityID=" + arEntityID + ", arClientID=" + arClientID + ", arReqTime="
				+ arReqTime + ", arRequestedByAccountID=" + arRequestedByAccountID + ", arIP=" + arIP
				+ ", arRequestedToAccountID=" + arRequestedToAccountID + ", arPriority=" + arPriority
				+ ", arExpireTime=" + arExpireTime + ", arDescription=" + arDescription + ", arLastModificationTime="
				+ arLastModificationTime + ", arIsDeleted=" + arIsDeleted + ", arCompletionStatus=" + arCompletionStatus
				+ ", arSourceRequestID=" + arSourceRequestID + ", arActionTypeID=" + arActionTypeID + "]";
	}
	
}
