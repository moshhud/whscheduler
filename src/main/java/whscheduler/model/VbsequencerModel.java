package whscheduler.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vbSequencer")
public class VbsequencerModel implements Serializable{

	private static final long serialVersionUID = 3128571605562433050L;
	@Column(name = "next_id")
	private long nextId;
	@Id
	@Column(name = "table_name")
	private String tableName;
	@Column(name = "table_LastModificationTime")
	private long tableLastModificationTime;
	
	public VbsequencerModel() {
	}
	public VbsequencerModel(long nextId, String tableName, long tableLastModificationTime) {
		this.nextId = nextId;
		this.tableName = tableName;
		this.tableLastModificationTime = tableLastModificationTime;
	}
	public long getNextId() {
		return nextId;
	}
	public void setNextId(long nextId) {
		this.nextId = nextId;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public long getTableLastModificationTime() {
		return tableLastModificationTime;
	}
	public void setTableLastModificationTime(long tableLastModificationTime) {
		this.tableLastModificationTime = tableLastModificationTime;
	}
	@Override
	public String toString() {
		return "VbsequencerModel [nextId=" + nextId + ", tableName=" + tableName + ", tableLastModificationTime="
				+ tableLastModificationTime + "]";
	}
	
}
