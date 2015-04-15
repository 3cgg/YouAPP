package j.jave.framework.components.views.chart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SimpleBarChart implements Serializable{

	public static class Data{
		private String xvalue;
		LinkedHashMap<String, Double> items=new LinkedHashMap<String, Double>();
		
		public String getBarYData(){
			String yData="";
			//List<String> itemList=new ArrayList<String>();
			if(items!=null){
				for (Iterator<Entry<String, Double>> iterator = items.entrySet().iterator(); iterator
						.hasNext();) {
					Entry<String, Double> entry =  iterator.next();
					//itemList.add(entry.getKey()+":"+entry.getValue());
					yData=yData+","+entry.getKey()+":"+entry.getValue();
				}
			}
			/*
			Collections.sort(itemList, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return o1.compareTo(o2);
				}
			});
			for(int i=0;i<itemList.size();i++){
				yData=yData+","+itemList.get(i);
			}
			*/
			return yData.replaceFirst(",", "");
		}

		public String getXvalue() {
			return xvalue;
		}

		public void setXvalue(String xvalue) {
			this.xvalue = xvalue;
		}

		public LinkedHashMap<String, Double> getItems() {
			return items;
		}

		public void setItems(LinkedHashMap<String, Double> items) {
			this.items = items;
		}
	}
	
	private String title;
	
	private String xlabel;
	
	private String yLabel;
	
	private List<Data> datas=new ArrayList<SimpleBarChart.Data>();
	
	private List<String> ykeys=new ArrayList<String>();
	
	private Map<String,String> ylabels=new HashMap<String, String>();

	/**
	 * set value for bar chart 
	 * @param xvalue
	 * @param xGroup
	 * @param yGroup
	 * @param xGroupLabel
	 */
	public void put(String xvalue,String xGroup,Double yGroup,String xGroupLabel){
		
		xGroup=xGroup.replace("-", "");
		
		if(datas!=null){
			boolean exists=false;
			for (Iterator<Data> iterator = datas.iterator(); iterator.hasNext();) {
				Data data =  iterator.next();
				if(xvalue.equals(data.getXvalue())){
					Double value=data.items.get(xGroup);
					data.items.put(xGroup, yGroup+(value==null?0:value));
					exists=true; 
					break;  // exit for 'loop'
				}
			}
			
			if(!exists){
				Data data=new Data();
				data.setXvalue(xvalue);
				data.items.put(xGroup, yGroup);
				datas.add(data);
			}
			
			if(!ykeys.contains(xGroup)){
				ykeys.add(xGroup);
			}
			
			if(!ylabels.containsKey(xGroup)){
				ylabels.put(xGroup, xGroupLabel);
			}

		}
	}
	
	
	public String getBarLables(){
		String barLabels="";
		if(ykeys!=null){
			for(int i=0;i<ykeys.size();i++){
				barLabels=barLabels+",'"+ylabels.get(ykeys.get(i))+"'";
			}
		}
		return barLabels.replaceFirst(",", "");
	}
	
	public String getBarYKeys(){
		String barYKeys="";
		if(ykeys!=null){
			for(int i=0;i<ykeys.size();i++){
				barYKeys=barYKeys+",'"+ykeys.get(i)+"'";
			}
		}
		return barYKeys.replaceFirst(",", "");
	}


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


	public String getyLabel() {
		return yLabel;
	}


	public void setyLabel(String yLabel) {
		this.yLabel = yLabel;
	}


	public List<Data> getDatas() {
		return datas;
	}
	
	public void sort(){
		if(datas!=null){
			Collections.sort(datas, new Comparator<Data>() {
				@Override
				public int compare(Data o1, Data o2) {
					return o1.getXvalue().compareTo(o2.getXvalue());
				}
			});
		}
	}
	
	
}
