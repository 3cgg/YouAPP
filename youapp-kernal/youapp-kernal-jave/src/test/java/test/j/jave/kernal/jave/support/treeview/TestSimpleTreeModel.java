package test.j.jave.kernal.jave.support.treeview;

import j.jave.kernal.jave.support.random.JSimpleObjectRandomBinder;
import j.jave.kernal.jave.support.treeview.JDefaultTreeView;
import j.jave.kernal.jave.support.treeview.JHierarchyTreeView;
import j.jave.kernal.jave.support.treeview.JSimpleTreeStrcture;
import j.jave.kernal.jave.support.treeview.JTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestSimpleTreeModel implements JSimpleTreeStrcture {

	private String id;
	
	private String parentId;
	
	@Override
	public String getName() {
		return id;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
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
		
		List<TestSimpleTreeModel> testModels=new ArrayList<TestSimpleTreeModel>();
		
		//1
		TestSimpleTreeModel testModel1=new TestSimpleTreeModel();
		JSimpleObjectRandomBinder objectPopulate1=new JSimpleObjectRandomBinder();
		objectPopulate1.bind(testModel1);
		testModel1.setParentId(null);
		testModels.add(testModel1);
		
		//2
		TestSimpleTreeModel testModel2=new TestSimpleTreeModel();
		JSimpleObjectRandomBinder objectPopulate2=new JSimpleObjectRandomBinder();
		objectPopulate2.bind(testModel2);
		testModels.add(testModel2);
		testModel2.setParentId(null);
		
		//3
		TestSimpleTreeModel testModel3=new TestSimpleTreeModel();
		JSimpleObjectRandomBinder objectPopulate3=new JSimpleObjectRandomBinder();
		objectPopulate3.bind(testModel3);
		testModels.add(testModel3);
		testModel3.setParentId(null);
		
		//1.1
		TestSimpleTreeModel testModel11=new TestSimpleTreeModel();
		JSimpleObjectRandomBinder objectPopulate11=new JSimpleObjectRandomBinder();
		objectPopulate11.bind(testModel11);
		testModel11.setParentId(testModel1.getId());
		testModels.add(testModel11);
		
		//2.1
		TestSimpleTreeModel testModel21=new TestSimpleTreeModel();
		JSimpleObjectRandomBinder objectPopulate21=new JSimpleObjectRandomBinder();
		objectPopulate21.bind(testModel21);
		testModel21.setParentId(testModel2.getId());
		testModels.add(testModel21);
		
		//1.1.1
		TestSimpleTreeModel testModel111=new TestSimpleTreeModel();
		JSimpleObjectRandomBinder objectPopulate111=new JSimpleObjectRandomBinder();
		objectPopulate111.bind(testModel111);
		testModel111.setParentId(testModel11.getId());
		testModels.add(testModel111);
		
		//2.2
		TestSimpleTreeModel testModel22=new TestSimpleTreeModel();
		JSimpleObjectRandomBinder objectPopulate22=new JSimpleObjectRandomBinder();
		objectPopulate22.bind(testModel22);
		testModel22.setParentId(testModel2.getId());
		testModels.add(testModel22);
		
		//1.2
		TestSimpleTreeModel testModel12=new TestSimpleTreeModel();
		JSimpleObjectRandomBinder objectPopulate12=new JSimpleObjectRandomBinder();
		objectPopulate12.bind(testModel12);
		testModel12.setParentId(testModel1.getId());
		testModels.add(testModel12);
		
		//4
		TestSimpleTreeModel testModel4=new TestSimpleTreeModel();
		JSimpleObjectRandomBinder objectPopulate4=new JSimpleObjectRandomBinder();
		objectPopulate4.bind(testModel4);
		testModels.add(testModel4);
		testModel4.setParentId(null);
		
		//5-5
		TestSimpleTreeModel testModel5_5=new TestSimpleTreeModel();
		JSimpleObjectRandomBinder objectPopulate5_5=new JSimpleObjectRandomBinder();
		objectPopulate5_5.bind(testModel5_5);
		testModels.add(testModel5_5);
		testModel5_5.setParentId(null);
		
		//6_3_6
		TestSimpleTreeModel testModel6_3_6=new TestSimpleTreeModel();
		JSimpleObjectRandomBinder objectPopulate6_3_6=new JSimpleObjectRandomBinder();
		objectPopulate6_3_6.bind(testModel6_3_6);
		testModel6_3_6.setParentId(testModel3.getId());
		testModels.add(testModel6_3_6);
		
//		testModel3.setParentId(testModel6_3_6.getId());
		
		
		for (Iterator<TestSimpleTreeModel> iterator = testModels.iterator(); iterator.hasNext();) {
			TestSimpleTreeModel jTestModel =  iterator.next();
			String split="----------";
			System.out.println(split+jTestModel.getId()+split+jTestModel.getParentId()+split);
		}
		JTree tree=new JTree(testModels).get();
		
		JDefaultTreeView defaultTreeRepresent=new JDefaultTreeView(tree);
		System.out.println(defaultTreeRepresent.view());
		
		JHierarchyTreeView hierarchyTreeRepresent=new JHierarchyTreeView(tree);
		System.out.println(hierarchyTreeRepresent.view());
		
//		String treeString=JJSON.get().formatObject(tree.getTreeNodes());
//		System.out.println(treeString);
	}
	
}
