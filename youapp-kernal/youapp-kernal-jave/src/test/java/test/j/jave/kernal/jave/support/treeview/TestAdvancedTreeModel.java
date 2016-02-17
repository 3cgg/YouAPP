package test.j.jave.kernal.jave.support.treeview;

import j.jave.kernal.jave.random.JObjectPopulate;
import j.jave.kernal.jave.support.treeview.JAdvancedTreeStrcture;
import j.jave.kernal.jave.support.treeview.JDefaultTreeRepresent;
import j.jave.kernal.jave.support.treeview.JHierarchyTreeRepresent;
import j.jave.kernal.jave.support.treeview.JTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestAdvancedTreeModel implements JAdvancedTreeStrcture {

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
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
public static void main(String[] args) throws Exception {
		
		List<TestAdvancedTreeModel> testModels=new ArrayList<TestAdvancedTreeModel>();
		
		//1
		TestAdvancedTreeModel testModel1=new TestAdvancedTreeModel();
		JObjectPopulate objectPopulate1=new JObjectPopulate(testModel1);
		objectPopulate1.populate();
		testModel1.setText(false);
		testModel1.setParentId(null);
		testModels.add(testModel1);
		
		//2
		TestAdvancedTreeModel testModel2=new TestAdvancedTreeModel();
		JObjectPopulate objectPopulate2=new JObjectPopulate(testModel2);
		objectPopulate2.populate();
		testModels.add(testModel2);
		testModel2.setText(false);
		testModel2.setParentId(null);
		
		//3
		TestAdvancedTreeModel testModel3=new TestAdvancedTreeModel();
		JObjectPopulate objectPopulate3=new JObjectPopulate(testModel3);
		objectPopulate3.populate();
		testModels.add(testModel3);
		testModel3.setText(false);
		testModel3.setParentId(null);
		
		//1.1
		TestAdvancedTreeModel testModel11=new TestAdvancedTreeModel();
		JObjectPopulate objectPopulate11=new JObjectPopulate(testModel11);
		objectPopulate11.populate();
		testModel11.setParentId(testModel1.getId());
		testModels.add(testModel11);
		testModel11.setText(false);
		
		//2.1
		TestAdvancedTreeModel testModel21=new TestAdvancedTreeModel();
		JObjectPopulate objectPopulate21=new JObjectPopulate(testModel21);
		objectPopulate21.populate();
		testModel21.setParentId(testModel2.getId());
		testModels.add(testModel21);
		testModel21.setText(false);
		
		//1.1.1
		TestAdvancedTreeModel testModel111=new TestAdvancedTreeModel();
		JObjectPopulate objectPopulate111=new JObjectPopulate(testModel111);
		objectPopulate111.populate();
		testModel111.setParentId(testModel11.getId());
		testModels.add(testModel111);
		
		//2.2
		TestAdvancedTreeModel testModel22=new TestAdvancedTreeModel();
		JObjectPopulate objectPopulate22=new JObjectPopulate(testModel22);
		objectPopulate22.populate();
		testModel22.setParentId(testModel2.getId());
		testModels.add(testModel22);
		testModel22.setText(false);
		
		//1.2
		TestAdvancedTreeModel testModel12=new TestAdvancedTreeModel();
		JObjectPopulate objectPopulate12=new JObjectPopulate(testModel12);
		objectPopulate12.populate();
		testModel12.setParentId(testModel1.getId());
		testModels.add(testModel12);
		testModel12.setText(false);
		
		//4
		TestAdvancedTreeModel testModel4=new TestAdvancedTreeModel();
		JObjectPopulate objectPopulate4=new JObjectPopulate(testModel4);
		objectPopulate4.populate();
		testModels.add(testModel4);
		testModel4.setText(false);
		testModel4.setParentId(null);
		
		//5-5
		TestAdvancedTreeModel testModel5_5=new TestAdvancedTreeModel();
		JObjectPopulate objectPopulate5_5=new JObjectPopulate(testModel5_5);
		objectPopulate5_5.populate();
		testModels.add(testModel5_5);
		testModel5_5.setText(true);
		testModel5_5.setParentId(null);
		
		//6_3_6
		TestAdvancedTreeModel testModel6_3_6=new TestAdvancedTreeModel();
		JObjectPopulate objectPopulate6_3_6=new JObjectPopulate(testModel6_3_6);
		objectPopulate6_3_6.populate();
		testModel6_3_6.setParentId(testModel3.getId());
		testModels.add(testModel6_3_6);
		testModel6_3_6.setText(false);
		
//		testModel3.setParentId(testModel6_3_6.getId());
		
		
		for (Iterator<TestAdvancedTreeModel> iterator = testModels.iterator(); iterator.hasNext();) {
			TestAdvancedTreeModel jTestModel =  iterator.next();
			String split="----------";
			System.out.println(split+jTestModel.getId()+split+jTestModel.getParentId()+split+jTestModel.isText+split);
		}
		JTree tree=new JTree(testModels).get();
		
		JDefaultTreeRepresent defaultTreeRepresent=new JDefaultTreeRepresent(tree);
		System.out.println(defaultTreeRepresent.represent());
		
		JHierarchyTreeRepresent hierarchyTreeRepresent=new JHierarchyTreeRepresent(tree);
		System.out.println(hierarchyTreeRepresent.represent());
		
//		String treeString=JJSON.get().formatObject(tree.getTreeNodes());
//		System.out.println(treeString);
	}

@Override
public boolean isText() {
	return isText;
}
	
}
