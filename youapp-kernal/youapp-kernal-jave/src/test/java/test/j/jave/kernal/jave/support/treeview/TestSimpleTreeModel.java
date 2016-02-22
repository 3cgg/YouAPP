package test.j.jave.kernal.jave.support.treeview;

import j.jave.kernal.jave.random.JSimpleObjectPopulate;
import j.jave.kernal.jave.support.treeview.JDefaultTreeRepresent;
import j.jave.kernal.jave.support.treeview.JHierarchyTreeRepresent;
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
		JSimpleObjectPopulate objectPopulate1=new JSimpleObjectPopulate();
		objectPopulate1.populate(testModel1);
		testModel1.setParentId(null);
		testModels.add(testModel1);
		
		//2
		TestSimpleTreeModel testModel2=new TestSimpleTreeModel();
		JSimpleObjectPopulate objectPopulate2=new JSimpleObjectPopulate();
		objectPopulate2.populate(testModel2);
		testModels.add(testModel2);
		testModel2.setParentId(null);
		
		//3
		TestSimpleTreeModel testModel3=new TestSimpleTreeModel();
		JSimpleObjectPopulate objectPopulate3=new JSimpleObjectPopulate();
		objectPopulate3.populate(testModel3);
		testModels.add(testModel3);
		testModel3.setParentId(null);
		
		//1.1
		TestSimpleTreeModel testModel11=new TestSimpleTreeModel();
		JSimpleObjectPopulate objectPopulate11=new JSimpleObjectPopulate();
		objectPopulate11.populate(testModel11);
		testModel11.setParentId(testModel1.getId());
		testModels.add(testModel11);
		
		//2.1
		TestSimpleTreeModel testModel21=new TestSimpleTreeModel();
		JSimpleObjectPopulate objectPopulate21=new JSimpleObjectPopulate();
		objectPopulate21.populate(testModel21);
		testModel21.setParentId(testModel2.getId());
		testModels.add(testModel21);
		
		//1.1.1
		TestSimpleTreeModel testModel111=new TestSimpleTreeModel();
		JSimpleObjectPopulate objectPopulate111=new JSimpleObjectPopulate();
		objectPopulate111.populate(testModel111);
		testModel111.setParentId(testModel11.getId());
		testModels.add(testModel111);
		
		//2.2
		TestSimpleTreeModel testModel22=new TestSimpleTreeModel();
		JSimpleObjectPopulate objectPopulate22=new JSimpleObjectPopulate();
		objectPopulate22.populate(testModel22);
		testModel22.setParentId(testModel2.getId());
		testModels.add(testModel22);
		
		//1.2
		TestSimpleTreeModel testModel12=new TestSimpleTreeModel();
		JSimpleObjectPopulate objectPopulate12=new JSimpleObjectPopulate();
		objectPopulate12.populate(testModel12);
		testModel12.setParentId(testModel1.getId());
		testModels.add(testModel12);
		
		//4
		TestSimpleTreeModel testModel4=new TestSimpleTreeModel();
		JSimpleObjectPopulate objectPopulate4=new JSimpleObjectPopulate();
		objectPopulate4.populate(testModel4);
		testModels.add(testModel4);
		testModel4.setParentId(null);
		
		//5-5
		TestSimpleTreeModel testModel5_5=new TestSimpleTreeModel();
		JSimpleObjectPopulate objectPopulate5_5=new JSimpleObjectPopulate();
		objectPopulate5_5.populate(testModel5_5);
		testModels.add(testModel5_5);
		testModel5_5.setParentId(null);
		
		//6_3_6
		TestSimpleTreeModel testModel6_3_6=new TestSimpleTreeModel();
		JSimpleObjectPopulate objectPopulate6_3_6=new JSimpleObjectPopulate();
		objectPopulate6_3_6.populate(testModel6_3_6);
		testModel6_3_6.setParentId(testModel3.getId());
		testModels.add(testModel6_3_6);
		
//		testModel3.setParentId(testModel6_3_6.getId());
		
		
		for (Iterator<TestSimpleTreeModel> iterator = testModels.iterator(); iterator.hasNext();) {
			TestSimpleTreeModel jTestModel =  iterator.next();
			String split="----------";
			System.out.println(split+jTestModel.getId()+split+jTestModel.getParentId()+split);
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
