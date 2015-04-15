package j.jave.framework.components.views.chart;

import java.io.Serializable;

public class SimpleLineChart implements Serializable{

	private String title;
	
	private String xlabel;
	
	private String ylabel;
	
	private String  xvalue;
	
	private double yvalue;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getXlabel() {
		return xlabel;
	}

	public void setXlabel(String xlabel) {
		this.xlabel = xlabel;
	}

	public String getYlabel() {
		return ylabel;
	}

	public void setYlabel(String ylabel) {
		this.ylabel = ylabel;
	}

	public String getXvalue() {
		return xvalue;
	}

	public void setXvalue(String xvalue) {
		this.xvalue = xvalue;
	}

	public double getYvalue() {
		return yvalue;
	}

	public void setYvalue(double yvalue) {
		this.yvalue = yvalue;
	}


	
	
	
}
