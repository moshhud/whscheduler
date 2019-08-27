package whscheduler.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "at_client")
public class ATClientModel implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "clID")
	private long clID;
	@Column(name = "clLoginName")
	private String loginName;
	@Column(name = "clIsDeleted")
	private int isDeleted;
	@Column(name = "clLastModificationTime")
	private long lastModificationTime;
	@OneToOne(fetch = FetchType.EAGER,mappedBy = "client")
	private ATClientDetailsModel clientDetails;
	
	
	
	public ATClientModel() {
	}
	public long getClID() {
		return clID;
	}
	public void setClID(long clID) {
		this.clID = clID;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	public long getLastModificationTime() {
		return lastModificationTime;
	}
	public void setLastModificationTime(long lastModificationTime) {
		this.lastModificationTime = lastModificationTime;
	}
	public ATClientDetailsModel getClientDetails() {
		return clientDetails;
	}
	public void setClientDetails(ATClientDetailsModel clientDetails) {
		this.clientDetails = clientDetails;
	}
	
	
	
	@Override
	public String toString() {
		return "ATClientModel [clID=" + clID + ", loginName=" + loginName + ", clientDetails=" + clientDetails
				+ ", contractDetails= ]";
	}
	
}
