package whscheduler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="at_webhosting_discount")
public class WebHostingDiscountModel {
	@Id
	@Column(name="wdID")
	long ID;
	@Column(name="wdDiscountType")
	int discountType;
	@Column(name="wdEventName")
	String eventName;
	@Column(name="wdMinDiscount")
	double minDiscount;
	@Column(name="wdMaxDiscount")
	double maxDiscount;
	@Column(name="wdDiscountPrcnt")
	double discountPrcnt;
	@Column(name="wdAmount")
	double amount;
	@Column(name="wdYear")
	int year;
	@Column(name="wdIsPercent")
	int isPercent;
	@Column(name="wdEffectiveFrom")
	long effectiveFrom;
	@Column(name="wdEffectiveTo")
	long effectiveTo;
	@Column(name="wdLastModificationTime")
	long lastModificationTime;
	@Column(name="wdIsDeleted")
	int isDeleted;
	@Column(name="wdIsActive")
	int isActive;
	@Column(name="wdRestrictMin")
	int restrictMin;
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public int getDiscountType() {
		return discountType;
	}
	public void setDiscountType(int discountType) {
		this.discountType = discountType;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public double getMinDiscount() {
		return minDiscount;
	}
	public void setMinDiscount(double minDiscount) {
		this.minDiscount = minDiscount;
	}
	public double getMaxDiscount() {
		return maxDiscount;
	}
	public void setMaxDiscount(double maxDiscount) {
		this.maxDiscount = maxDiscount;
	}
	public double getDiscountPrcnt() {
		return discountPrcnt;
	}
	public void setDiscountPrcnt(double discountPrcnt) {
		this.discountPrcnt = discountPrcnt;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getIsPercent() {
		return isPercent;
	}
	public void setIsPercent(int isPercent) {
		this.isPercent = isPercent;
	}
	public long getEffectiveFrom() {
		return effectiveFrom;
	}
	public void setEffectiveFrom(long effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}
	public long getEffectiveTo() {
		return effectiveTo;
	}
	public void setEffectiveTo(long effectiveTo) {
		this.effectiveTo = effectiveTo;
	}
	public long getLastModificationTime() {
		return lastModificationTime;
	}
	public void setLastModificationTime(long lastModificationTime) {
		this.lastModificationTime = lastModificationTime;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public int getRestrictMin() {
		return restrictMin;
	}
	public void setRestrictMin(int restrictMin) {
		this.restrictMin = restrictMin;
	}
	
	@Override
	public String toString() {
		return "WebHostingDiscountModel [ ID="+ID
				+",discountType="+discountType
				+",eventName="+eventName
				+",minDiscount="+minDiscount
				+",maxDiscount="+maxDiscount
				+",discountPrcnt="+discountPrcnt
				+",amount="+amount
				+",year="+year
				+",isPercent="+isPercent
				+",effectiveFrom="+effectiveFrom
				+",effectiveTo="+effectiveTo
				+",lastModificationTime="+lastModificationTime
				+",isDeleted="+isDeleted
				+",isActive="+isActive
				+ "]";
	}

}
