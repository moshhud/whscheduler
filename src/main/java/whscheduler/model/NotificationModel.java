package whscheduler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "at_sms_and_mail_log")
public class NotificationModel {

	@Id
	@Column(name = "id")
	private long id;
	@Column(name = "sent_type")
	private String sentType;
	@Column(name = "sent_from")
	private String sentFrom;
	@Column(name = "sent_to")
	private String sentTo;
	@Column(name = "sent_cc")
	private String sentCC;
	@Column(name = "sent_subject")
	private String sentSubject;
	@Column(name = "sent_body")
	private String sentBody;
	@Column(name = "attachment")
	private String attachment;
	@Column(name = "aditional_info")
	private String aditionalInfo;
	@Column(name = "request_generated_from",columnDefinition = "default 'webhosting jar' ")
	private String requestGeneratedFrom;
	@Column(name = "status")
	private String status;
	
	public NotificationModel() {
	}
	public NotificationModel(long id, String sentType, String sentFrom, String sentTo, String sentCC, String sentSubject,
			String sentBody, String status,String attachment,String additionalInfo) {
		this.id = id;
		this.sentType = sentType;
		this.sentFrom = sentFrom;
		this.sentTo = sentTo;
		this.sentCC = sentCC;
		this.sentSubject = sentSubject;
		this.sentBody = sentBody;
		this.status = status;
		this.attachment = attachment;
		this.aditionalInfo = additionalInfo;
		this.requestGeneratedFrom = "automation jar";
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSentType() {
		return sentType;
	}
	public void setSentType(String sentType) {
		this.sentType = sentType;
	}
	public String getSentFrom() {
		return sentFrom;
	}
	public void setSentFrom(String sentFrom) {
		this.sentFrom = sentFrom;
	}
	public String getSentTo() {
		return sentTo;
	}
	public void setSentTo(String sentTo) {
		this.sentTo = sentTo;
	}
	public String getSentCC() {
		return sentCC;
	}
	public void setSentCC(String sentCC) {
		this.sentCC = sentCC;
	}
	public String getSentSubject() {
		return sentSubject;
	}
	public void setSentSubject(String sentSubject) {
		this.sentSubject = sentSubject;
	}
	public String getSentBody() {
		return sentBody;
	}
	public void setSentBody(String sentBody) {
		this.sentBody = sentBody;
	}
	public String getAditionalInfo() {
		return aditionalInfo;
	}
	public void setAditionalInfo(String aditionalInfo) {
		this.aditionalInfo = aditionalInfo;
	}
	public String getRequestGeneratedFrom() {
		return requestGeneratedFrom;
	}
	public void setRequestGeneratedFrom(String requestGeneratedFrom) {
		this.requestGeneratedFrom = requestGeneratedFrom;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	@Override
	public String toString() {
		return "MailServiceModel [id=" + id + ", sentType=" + sentType + ", sentFrom=" + sentFrom + ", sentTo=" + sentTo
				+ ", sentCC=" + sentCC + ", sentSubject=" + sentSubject + ", sentBody=" + sentBody + ", status="
				+ status + "]";
	}
	
	
}
