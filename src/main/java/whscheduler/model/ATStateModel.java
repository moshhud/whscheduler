package whscheduler.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "at_state")
public class ATStateModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "stID")
	private long ID;
	@Column(name = "stDurationInMillis")
	private long durationInMillis;
	
	public ATStateModel() {
	}
	public ATStateModel(int iD, long durationInMillis) {
		ID = iD;
		this.durationInMillis = durationInMillis;
	}
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public long getDurationInMillis() {
		return durationInMillis;
	}
	public void setDurationInMillis(long durationInMillis) {
		this.durationInMillis = durationInMillis;
	}
	@Override
	public String toString() {
		return "ATStateModel [ID=" + ID + ", durationInMillis=" + durationInMillis + "]";
	}
	
	
}
