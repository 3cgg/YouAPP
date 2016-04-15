package test.j.jave.kernal.jave.support.treeview;

import j.jave.kernal.jave.support.random.JSimpleObjectRandomBinder;
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
		JSimpleObjectRandomBinder objectPopulate1=new JSimpleObjectRandomBinder();
		objectPopulate1.bind(testModel1);
		testModel1.setText(false);
		testModel1.setParentId(null);
		testModels.add(testModel1);
		
		//2
		TestAdvancedTreeModel testModel2=new TestAdvancedTreeModel();
		JSimpleObjectRandomBinder objectPopulate2=new JSimpleObjectRandomBinder();
		objectPopulate2.bind(testModel2);
		testModels.add(testModel2);
		testModel2.setText(false);
		testModel2.setParentId(null);
		
		//3
		TestAdvancedTreeModel testModel3=new TestAdvancedTreeModel();
		JSimpleObjectRandomBinder objectPopulate3=new JSimpleObjectRandomBinder();
		objectPopulate3.bind(testModel3);
		testModels.add(testModel3);
		testModel3.setText(false);
		testModel3.setParentId(null);
		
		//1.1
		TestAdvancedTreeModel testModel11=new TestAdvancedTreeModel();
		JSimpleObjectRandomBinder objectPopulate11=new JSimpleObjectRandomBinder();
		objectPopulate11.bind(testModel11);
		testModel11.setParentId(testModel1.getId());
		testModels.add(testModel11);
		testModel11.setText(false);
		
		//2.1
		TestAdvancedTreeModel testModel21=new TestAdvancedTreeModel();
		JSimpleObjectRandomBinder objectPopulate21=new JSimpleObjectRandomBinder();
		objectPopulate21.bind(testModel21);
		testModel21.setParentId(testModel2.getId());
		testModels.add(testModel21);
		testModel21.setText(false);
		
		//1.1.1
		TestAdvancedTreeModel testModel111=new TestAdvancedTreeModel();
		JSimpleObjectRandomBinder objectPopulate111=new JSimpleObjectRandomBinder();
		objectPopulate111.bind(testModel111);
		testModel111.setParentId(testModel11.getId());
		testModels.add(testModel111);
		
		//2.2
		TestAdvancedTreeModel testModel22=new TestAdvancedTreeModel();
		JSimpleObjectRandomBinder objectPopulate22=new JSimpleObjectRandomBinder();
		objectPopulate22.bind(testModel22);
		testModel22.setParentId(testModel2.getId());
		testModels.add(testModel22);
		testModel22.setText(false);
		
		//1.2
		TestAdvancedTreeModel testModel12=new TestAdvancedTreeModel();
		JSimpleObjectRandomBinder objectPopulate12=new JSimpleObjectRandomBinder();
		objectPopulate12.bind(testModel12);
		testModel12.setParentId(testModel1.getId());
		testModels.add(testModel12);
		testModel12.setText(false);
		
		//4
		TestAdvancedTreeModel testModel4=new TestAdvancedTreeModel();
		JSimpleObjectRandomBinder objectPopulate4=new JSimpleObjectRandomBinder();
		objectPopulate4.bind(testModel4);
		testModels.add(testModel4);
		testModel4.setText(false);
		testModel4.setParentId(null);
		
		//5-5
		TestAdvancedTreeModel testModel5_5=new TestAdvancedTreeModel();
		JSimpleObjectRandomBinder objectPopulate5_5=new JSimpleObjectRandomBinder();
		objectPopulate5_5.bind(testModel5_5);
		testModels.add(testModel5_5);
		testModel5_5.setText(true);
		testModel5_5.setParentId(null);
		
		//6_3_6
		TestAdvancedTreeModel testModel6_3_6=new TestAdvancedTreeModel();
		JSimpleObjectRandomBinder objectPopulate6_3_6=new JSimpleObjectRandomBinder();
		objectPopulate6_3_6.bind(testModel6_3_6);
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
