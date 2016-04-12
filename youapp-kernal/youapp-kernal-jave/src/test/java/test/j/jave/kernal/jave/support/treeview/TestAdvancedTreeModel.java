package test.j.jave.kernal.jave.support.treeview;

import j.jave.kernal.jave.random.JSimpleObjectPopulate;
import j.jave.kernal.jave.support.treeview.JAdvancedTreeStrcture;
import j.jave.kernal.jave.support.treeview.JDefaultTreeView;
import j.jave.kernal.jave.support.treeview.JHierarchyTreeView;
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
		JSimpleObjectPopulate objectPopulate1=new JSimpleObjectPopulate();
		objectPopulate1.populate(testModel1);
		testModel1.setText(false);
		testModel1.setParentId(null);
		testModels.add(testModel1);
		
		//2
		TestAdvancedTreeModel testModel2=new TestAdvancedTreeModel();
		JSimpleObjectPopulate objectPopulate2=new JSimpleObjectPopulate();
		objectPopulate2.populate(testModel2);
		testModels.add(testModel2);
		testModel2.setText(false);
		testModel2.setParentId(null);
		
		//3
		TestAdvancedTreeModel testModel3=new TestAdvancedTreeModel();
		JSimpleObjectPopulate objectPopulate3=new JSimpleObjectPopulate();
		objectPopulate3.populate(testModel3);
		testModels.add(testModel3);
		testModel3.setText(false);
		testModel3.setParentId(null);
		
		//1.1
		TestAdvancedTreeModel testModel11=new TestAdvancedTreeModel();
		JSimpleObjectPopulate objectPopulate11=new JSimpleObjectPopulate();
		objectPopulate11.populate(testModel11);
		testModel11.setParentId(testModel1.getId());
		testModels.add(testModel11);
		testModel11.setText(false);
		
		//2.1
		TestAdvancedTreeModel testModel21=new TestAdvancedTreeModel();
		JSimpleObjectPopulate objectPopulate21=new JSimpleObjectPopulate();
		objectPopulate21.populate(testModel21);
		testModel21.setParentId(testModel2.getId());
		testModels.add(testModel21);
		testModel21.setText(false);
		
		//1.1.1
		TestAdvancedTreeModel testModel111=new TestAdvancedTreeModel();
		JSimpleObjectPopulate objectPopulate111=new JSimpleObjectPopulate();
		objectPopulate111.populate(testModel111);
		testModel111.setParentId(testModel11.getId());
		testModels.add(testModel111);
		
		//2.2
		TestAdvancedTreeModel testModel22=new TestAdvancedTreeModel();
		JSimpleObjectPopulate objectPopulate22=new JSimpleObjectPopulate();
		objectPopulate22.populate(testModel22);
		testModel22.setParentId(testModel2.getId());
		testModels.add(testModel22);
		testModel22.setText(false);
		
		//1.2
		TestAdvancedTreeModel testModel12=new TestAdvancedTreeModel();
		JSimpleObjectPopulate objectPopulate12=new JSimpleObjectPopulate();
		objectPopulate12.populate(testModel12);
		testModel12.setParentId(testModel1.getId());
		testModels.add(testModel12);
		testModel12.setText(false);
		
		//4
		TestAdvancedTreeModel testModel4=new TestAdvancedTreeModel();
		JSimpleObjectPopulate objectPopulate4=new JSimpleObjectPopulate();
		objectPopulate4.populate(testModel4);
		testModels.add(testModel4);
		testModel4.setText(false);
		testModel4.setParentId(null);
		
		//5-5
		TestAdvancedTreeModel testModel5_5=new TestAdvancedTreeModel();
		JSimpleObjectPopulate objectPopulate5_5=new JSimpleObjectPopulate();
		objectPopulate5_5.populate(testModel5_5);
		testModels.add(testModel5_5);
		testModel5_5.setText(true);
		testModel5_5.setParentId(null);
		
		//6_3_6
		TestAdvancedTreeModel testModel6_3_6=new TestAdvancedTreeModel();
		JSimpleObjectPopulate objectPopulate6_3_6=new JSimpleObjectPopulate();
		objectPopulate6_3_6.populate(testModel6_3_6);
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
		
		JDefaultTreeView defaultTreeRepresent=new JDefaultTreeView(tree);
		System.out.println(defaultTreeRepresent.view());
		
		JHierarchyTreeView hierarchyTreeRepresent=new JHierarchyTreeView(tree);
		System.out.println(hierarchyTreeRepresent.view());
		
//		String treeString=JJSON.get().formatObject(tree.getTreeNodes());
//		System.out.println(treeString);
	}

@Override
public boolean isText() {
	return isText;
}
	
}
