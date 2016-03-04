package j.jave.platform.basicwebcomp.spirngjpa.query;

import java.util.Calendar;

import javax.persistence.TemporalType;

public class JpaCalendarParam {

	private Calendar calendar;
	
	private TemporalType temporalType;

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public TemporalType getTemporalType() {
		return temporalType;
	}

	public void setTemporalType(TemporalType temporalType) {
		this.temporalType = temporalType;
	}
}
