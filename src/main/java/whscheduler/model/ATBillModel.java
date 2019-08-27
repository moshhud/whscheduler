package whscheduler.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "at_bill")
public class ATBillModel implements Serializable{

	private static final long serialVersionUID = -5411067299361080279L;
	@Id
	@Column(name = "blID")
	private long ID;
	@Column(name = "blClientID")
	private long clientID;
	@Column(name = "blGrandTotal")
	private double grandTotal;
	@Column(name = "blLateFee")
	private double lateFee;
	@Column(name = "blAdjustmentAmount")
	private double adjustmentAmount;
	@Column(name = "blTotalPayable")
	private double totalPayable;
	@Column(name = "blVAT")
	private double VAT;
	@Column(name = "blNetPayable")
	private double netPayable;
	@Column(name = "blEntityID")
	private long blEntityID;
	@Column(name = "blEntityTypeID")
	private int blEntityTypeID;
	@Column(name = "blDiscount")
	private double discount;
	@Column(name = "blMonth",nullable = true)
	private int blMonth;
	@Column(name = "blYear",nullable = true)
	private int blYear;
	@Column(name = "blActivationTimeFrom",nullable = true)
	private long activationTimeFrom;
	@Column(name = "blActivationTimeTo",nullable = true)
	private Long activationTimeTo;
	@Column(name = "blClassName")
	private String blClassName;
	@Column(name = "blLastPaymentDate")
	private long lastPaymentDate;
	@Column(name = "blDuePaymentDate")
	private long duePaymentDate;
	@Column(name = "blExtraChargeAfterDueDate")
	private double extraChargeAfterDueDate;
	@Column(name = "blExtraVATAfterDueDate")
	private double extraVATAfterDueDate;
	@Column(name = "blNetPayableAfterDueDate")
	private double netPayableAfterDueDate;
	@Column(name = "blGenerationTime")
	private long generationTime;
	@Column(name = "blType")
	private int type;
	@Column(name = "blRequestType")
	private int requestType;
	@Column(name = "blIsDeleted",nullable = true)
	private int isDeleted;
	@Column(name = "blLastModificationTime",nullable = true)
	private long lastModificationTime;
	@Column(name = "blReqID")
	private long reqID;
	@Column(name = "blPaymentID")
	private long paymentID;
	@Column(name = "blDescription")
	private String description;
	@Column(name = "blBillFilePath")
	private String billFilePath;
	@Column(name = "blPaymentStatus")
	private int paymentStatus;
	@Column(name = "blPaymentGatewayType")
	private Integer paymentGatewayType;
	
	
	public ATBillModel() {
	}
	public ATBillModel(long id, long clientID, double grandTotal, double lateFee, double adjustmentAmount,
			double totalPayable, double vAT, double netPayable, long blEntityID, int blEntityTypeID, double blDiscount,
			int blMonth, int blYear, long activationTimeFrom, String blClassName, long lastPaymentDate,
			long generationTime, int type, int requestType, int isDeleted, long lastModificationTime, long reqID,
			long paymentID, String description, String billFilePath, int paymentStatus, int paymentGatewayType) {
		this.ID = id;
		this.clientID = clientID;
		this.grandTotal = grandTotal;
		this.lateFee = lateFee;
		this.adjustmentAmount = adjustmentAmount;
		this.totalPayable = totalPayable;
		this.VAT = vAT;
		this.netPayable = netPayable;
		this.blEntityID = blEntityID;
		this.blEntityTypeID = blEntityTypeID;
		this.discount = blDiscount;
		this.blMonth = blMonth;
		this.blYear = blYear;
		this.activationTimeFrom = activationTimeFrom;
		this.blClassName = blClassName;
		this.lastPaymentDate = lastPaymentDate;
		this.generationTime = generationTime;
		this.type = type;
		this.requestType = requestType;
		this.isDeleted = isDeleted;
		this.lastModificationTime = lastModificationTime;
		this.reqID = reqID;
		this.paymentID = paymentID;
		this.description = description;
		this.billFilePath = billFilePath;
		this.paymentStatus = paymentStatus;
		this.paymentGatewayType = paymentGatewayType;
	}
	public long getID() {
		return ID;
	}
	public void setID(long id) {
		ID = id;
	}
	public long getClientID() {
		return clientID;
	}
	public void setClientID(long clientID) {
		this.clientID = clientID;
	}
	public double getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}
	public double getLateFee() {
		return lateFee;
	}
	public void setLateFee(double lateFee) {
		this.lateFee = lateFee;
	}
	public double getAdjustmentAmount() {
		return adjustmentAmount;
	}
	public void setAdjustmentAmount(double adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}
	public double getTotalPayable() {
		return totalPayable;
	}
	public void setTotalPayable(double totalPayable) {
		this.totalPayable = totalPayable;
	}
	public double getVAT() {
		return VAT;
	}
	public void setVAT(double vAT) {
		VAT = vAT;
	}
	public double getNetPayable() {
		return netPayable;
	}
	public void setNetPayable(double netPayable) {
		this.netPayable = netPayable;
	}
	public long getBlEntityID() {
		return blEntityID;
	}
	public void setBlEntityID(long blEntityID) {
		this.blEntityID = blEntityID;
	}
	public int getBlEntityTypeID() {
		return blEntityTypeID;
	}
	public void setBlEntityTypeID(int blEntityTypeID) {
		this.blEntityTypeID = blEntityTypeID;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public int getBlMonth() {
		return blMonth;
	}
	public void setBlMonth(int blMonth) {
		this.blMonth = blMonth;
	}
	public int getBlYear() {
		return blYear;
	}
	public void setBlYear(int blYear) {
		this.blYear = blYear;
	}
	public long getActivationTimeFrom() {
		return activationTimeFrom;
	}
	public void setActivationTimeFrom(long activationTimeFrom) {
		this.activationTimeFrom = activationTimeFrom;
	}
	public long getActivationTimeTo() {
		return activationTimeTo;
	}
	public void setActivationTimeTo(long activationTimeTo) {
		this.activationTimeTo = activationTimeTo;
	}
	public String getBlClassName() {
		return blClassName;
	}
	public void setBlClassName(String blClassName) {
		this.blClassName = blClassName;
	}
	public long getLastPaymentDate() {
		return lastPaymentDate;
	}
	public void setLastPaymentDate(long lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}
	public long getGenerationTime() {
		return generationTime;
	}
	public void setGenerationTime(long generationTime) {
		this.generationTime = generationTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getRequestType() {
		return requestType;
	}
	public void setRequestType(int requestType) {
		this.requestType = requestType;
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
	public long getReqID() {
		return reqID;
	}
	public void setReqID(long reqID) {
		this.reqID = reqID;
	}
	public long getPaymentID() {
		return paymentID;
	}
	public void setPaymentID(long paymentID) {
		this.paymentID = paymentID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBillFilePath() {
		return billFilePath;
	}
	public void setBillFilePath(String billFilePath) {
		this.billFilePath = billFilePath;
	}
	public int getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public int getPaymentGatewayType() {
		return paymentGatewayType;
	}
	public void setPaymentGatewayType(Integer paymentGatewayType) {
		this.paymentGatewayType = paymentGatewayType;
	}
	
	public long getDuePaymentDate() {
		return duePaymentDate;
	}
	public void setDuePaymentDate(long duePaymentDate) {
		this.duePaymentDate = duePaymentDate;
	}
	public double getExtraChargeAfterDueDate() {
		return extraChargeAfterDueDate;
	}
	public void setExtraChargeAfterDueDate(double extraChargeAfterDueDate) {
		this.extraChargeAfterDueDate = extraChargeAfterDueDate;
	}
	public double getExtraVATAfterDueDate() {
		return extraVATAfterDueDate;
	}
	public void setExtraVATAfterDueDate(double extraVATAfterDueDate) {
		this.extraVATAfterDueDate = extraVATAfterDueDate;
	}
	public double getNetPayableAfterDueDate() {
		return netPayableAfterDueDate;
	}
	public void setNetPayableAfterDueDate(double netPayableAfterDueDate) {
		this.netPayableAfterDueDate = netPayableAfterDueDate;
	}
	@Override
	public String toString() {
		return "ATBillDTO [Id=" + ID + ", clientID=" + clientID + ", grandTotal=" + grandTotal + ", lateFee=" + lateFee
				+ ", adjustmentAmount=" + adjustmentAmount + ", totalPayable=" + totalPayable + ", VAT=" + VAT
				+ ", netPayable=" + netPayable + ", blEntityID=" + blEntityID + ", blEntityTypeID=" + blEntityTypeID
				+ ", blDiscount=" + discount + ", blMonth=" + blMonth + ", blYear=" + blYear + ", activationTimeFrom="
				+ activationTimeFrom + ", blClassName=" + blClassName + ", lastPaymentDate=" + lastPaymentDate
				+ ", generationTime=" + generationTime + ", type=" + type + ", requestType=" + requestType
				+ ", isDeleted=" + isDeleted + ", lastModificationTime=" + lastModificationTime + ", reqID=" + reqID
				+ ", paymentID=" + paymentID + ", description=" + description + ", billFilePath=" + billFilePath
				+ ", paymentStatus=" + paymentStatus + ", paymentGatewayType=" + paymentGatewayType + "]";
	}
	
	
}
