package j.jave.framework.components.tablemanager.model;

import java.util.ArrayList;
import java.util.List;

public class Record {

	private List<Cell> cells=new ArrayList<Cell>();

	public List<Cell> getCells() {
		return cells;
	}

	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}
	
	
}
