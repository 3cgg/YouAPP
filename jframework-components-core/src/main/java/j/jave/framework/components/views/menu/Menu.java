package j.jave.framework.components.views.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * menu . 
 * @author J
 */
public class Menu {
	

	/**
	 * url
	 */
	private String url;
	
	/**
	 * param
	 */
	private String param;
	
	
	/**
	 * label
	 */
	private String label;
	
	
	/**
	 * name
	 */
	private String name;
	
	
	/**
	 * parent.
	 */
	private String menu;
	
	/**
	 * children of menus 
	 */
	private List<Menu> menus=new ArrayList<Menu>();
	
	/**
	 * children of items.
	 */
	private List<Item> items=new ArrayList<Item>();

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	
}
