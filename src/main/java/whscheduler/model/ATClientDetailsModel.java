package whscheduler.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "at_client_details")
public class ATClientDetailsModel implements Serializable{

	private static final long serialVersionUID = -7406959591441958476L;
	@Id
	@Column(name = "vclID")
	private long Id;
	@Column(name = "vclClientID",insertable = false, updatable = false)
	private long clientID;
	@Column(name = "vclRegType")
	private int regType;
	@Column(name = "vclLatestStatus")
	private int latestStatus;
	@Column(name = "vclCurrentStatus")
	private int currentStatus;
	
	@OneToOne(fetch = FetchType.EAGER,optional = false)
	@JoinColumn(name = "vclClientID",nullable = false)
	private ATClientModel client;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "client")
	private List<ATClientContactDetailsModel> contactDetails;
	
	public ATClientDetailsModel() {
	}
	public ATClientDetailsModel(long id, long clientID, int regType) {
		this.Id = id;
		this.clientID = clientID;
		this.regType = regType;
	}
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
	}
	public int getRegType() {
		return regType;
	}
	public void setRegType(int regType) {
		this.regType = regType;
	}
	
	public int getLatestStatus() {
		return latestStatus;
	}
	public void setLatestStatus(int latestStatus) {
		this.latestStatus = latestStatus;
	}
	public int getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(int currentStatus) {
		this.currentStatus = currentStatus;
	}
	public List<ATClientContactDetailsModel> getContactDetails() {
		return contactDetails;
	}
	public void setContactDetails(List<ATClientContactDetailsModel> contactDetails) {
		this.contactDetails = contactDetails;
	}
	@Override
	public String toString() {
		return "ATClientDetailsModel [Id=" + Id + ", clientID=" + clientID + ", regType=" + regType + "]";
	}
	
	
	
}
