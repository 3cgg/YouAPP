package j.jave.platform.basicwebcomp.core.model;

import java.util.Date;

import javax.persistence.TemporalType;

public class JpaDateParam {

	private Date date;
	
	private TemporalType temporalType;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public TemporalType getTemporalType() {
		return temporalType;
	}

	public void setTemporalType(TemporalType temporalType) {
		this.temporalType = temporalType;
	}
}
