package me.bunny.app._c.jpa.springjpa.query;

import java.util.Date;

import javax.persistence.TemporalType;

public class JJpaDateParam {

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
