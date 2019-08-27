package whscheduler.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "at_client_contact_details")
public class ATClientContactDetailsModel implements Serializable{

	private static final long serialVersionUID = -3144441355756295715L;

	@Id
	@Column(name = "vclcID")
	private long Id;
	@Column(name = "vclcVpnClientID",insertable = false,updatable = false)
	private long clientID;
	@Column(name = "vclcName")
	private String name;
	@Column(name = "vclcLastName")
	private String lastName;
	@Column(name = "vclcEmail")
	private String email;
	@Column(name = "vclcAddress")
	private String address;
	@Column(name = "vclcDetailsType")
	private int detailsType;
	
	@Column(name = "vclcPhoneNumber")
	private String phoneNumber;
	
	@ManyToOne(fetch = FetchType.EAGER,optional = false)
	@JoinColumn(name = "vclcVpnClientID",nullable = false)
	private ATClientDetailsModel client;
	
	public ATClientContactDetailsModel() {
	}
	
	public ATClientContactDetailsModel(long id, long clientID, String name, String lastName, String email,
			String address, String phoneNumber) {
		Id = id;
		this.clientID = clientID;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public long getId() {
		return Id;
	}
	public void setId(long id) {
		this.Id = id;
	}
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	public int getDetailsType() {
		return detailsType;
	}

	public void setDetailsType(int detailsType) {
		this.detailsType = detailsType;
	}

	@Override
	public String toString() {
		return "ATClientContractDetailsModel [Id=" + Id + ", clientID=" + clientID + ", name=" + name + ", lastName="
				+ lastName + ", email=" + email + ", address=" + address + ", detailsType=" + detailsType+ ", phoneNumber=" + phoneNumber + "]";
	}
	
}
