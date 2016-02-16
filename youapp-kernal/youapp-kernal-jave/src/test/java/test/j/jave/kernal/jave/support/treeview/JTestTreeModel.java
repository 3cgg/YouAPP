package test.j.jave.kernal.jave.support.treeview;

import j.jave.kernal.jave.random.JObjectPopulate;
import j.jave.kernal.jave.support.treeview.JDefaultTreeRepresent;
import j.jave.kernal.jave.support.treeview.JHierarchyTreeRepresent;
import j.jave.kernal.jave.support.treeview.JTree;
import j.jave.kernal.jave.support.treeview.JTreeStrcture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JTestTreeModel implements JTreeStrcture {

	private String id;
	
	private String parentId;
	
	private boolean isText;
	
	@Override
	public String getName() {
		return id;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setText(boolean isText) {
		this.isText = isText;
	}

	@Override
	public String getParentId() {
		return parentId;
	}

	@Override
	public boolean isText() {
		return isText;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
public static void main(String[] args) throws Exception {
		
		List<JTestTreeModel> testModels=new ArrayList<JTestTreeModel>();
		
		//1
		JTestTreeModel testModel1=new JTestTreeModel();
		JObjectPopulate objectPopulate1=new JObjectPopulate(testModel1);
		objectPopulate1.populate();
		testModel1.setText(false);
		testModel1.setParentId(null);
		testModels.add(testModel1);
		
		//2
		JTestTreeModel testModel2=new JTestTreeModel();
		JObjectPopulate objectPopulate2=new JObjectPopulate(testModel2);
		objectPopulate2.populate();
		testModels.add(testModel2);
		testModel2.setText(false);
		testModel2.setParentId(null);
		
		//3
		JTestTreeModel testModel3=new JTestTreeModel();
		JObjectPopulate objectPopulate3=new JObjectPopulate(testModel3);
		objectPopulate3.populate();
		testModels.add(testModel3);
		testModel3.setText(false);
		testModel3.setParentId(null);
		
		//1.1
		JTestTreeModel testModel11=new JTestTreeModel();
		JObjectPopulate objectPopulate11=new JObjectPopulate(testModel11);
		objectPopulate11.populate();
		testModel11.setParentId(testModel1.getId());
		testModels.add(testModel11);
		testModel11.setText(false);
		
		//2.1
		JTestTreeModel testModel21=new JTestTreeModel();
		JObjectPopulate objectPopulate21=new JObjectPopulate(testModel21);
		objectPopulate21.populate();
		testModel21.setParentId(testModel2.getId());
		testModels.add(testModel21);
		testModel21.setText(false);
		
		//1.1.1
		JTestTreeModel testModel111=new JTestTreeModel();
		JObjectPopulate objectPopulate111=new JObjectPopulate(testModel111);
		objectPopulate111.populate();
		testModel111.setParentId(testModel11.getId());
		testModels.add(testModel111);
		
		//2.2
		JTestTreeModel testModel22=new JTestTreeModel();
		JObjectPopulate objectPopulate22=new JObjectPopulate(testModel22);
		objectPopulate22.populate();
		testModel22.setParentId(testModel2.getId());
		testModels.add(testModel22);
		testModel22.setText(false);
		
		//1.2
		JTestTreeModel testModel12=new JTestTreeModel();
		JObjectPopulate objectPopulate12=new JObjectPopulate(testModel12);
		objectPopulate12.populate();
		testModel12.setParentId(testModel1.getId());
		testModels.add(testModel12);
		testModel12.setText(false);
		
		//4
		JTestTreeModel testModel4=new JTestTreeModel();
		JObjectPopulate objectPopulate4=new JObjectPopulate(testModel4);
		objectPopulate4.populate();
		testModels.add(testModel4);
		testModel4.setText(false);
		testModel4.setParentId(null);
		
		//5-5
		JTestTreeModel testModel5_5=new JTestTreeModel();
		JObjectPopulate objectPopulate5_5=new JObjectPopulate(testModel5_5);
		objectPopulate5_5.populate();
		testModels.add(testModel5_5);
		testModel5_5.setText(true);
		testModel5_5.setParentId(null);
		
		//6_3_6
		JTestTreeModel testModel6_3_6=new JTestTreeModel();
		JObjectPopulate objectPopulate6_3_6=new JObjectPopulate(testModel6_3_6);
		objectPopulate6_3_6.populate();
		testModel6_3_6.setParentId(testModel3.getId());
		testModels.add(testModel6_3_6);
		testModel6_3_6.setText(false);
		
//		testModel3.setParentId(testModel6_3_6.getId());
		
		
		for (Iterator<JTestTreeModel> iterator = testModels.iterator(); iterator.hasNext();) {
			JTestTreeModel jTestModel =  iterator.next();
			
			System.out.println(jTestModel.getId()+"----------"+jTestModel.getParentId()+"--------------"+jTestModel.isText());
		}
		JTree tree=new JTree(testModels).get();
		
		JDefaultTreeRepresent defaultTreeRepresent=new JDefaultTreeRepresent(tree);
		System.out.println(defaultTreeRepresent.represent());
		
		JHierarchyTreeRepresent hierarchyTreeRepresent=new JHierarchyTreeRepresent(tree);
		System.out.println(hierarchyTreeRepresent.represent());
		
//		String treeString=JJSON.get().formatObject(tree.getTreeNodes());
//		System.out.println(treeString);
	}
	
}
