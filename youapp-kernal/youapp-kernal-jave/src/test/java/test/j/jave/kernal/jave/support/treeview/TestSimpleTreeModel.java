package test.j.jave.kernal.jave.support.treeview;

import j.jave.kernal.jave.random.JObjectPopulate;
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
		JObjectPopulate objectPopulate1=new JObjectPopulate(testModel1);
		objectPopulate1.populate();
		testModel1.setParentId(null);
		testModels.add(testModel1);
		
		//2
		TestSimpleTreeModel testModel2=new TestSimpleTreeModel();
		JObjectPopulate objectPopulate2=new JObjectPopulate(testModel2);
		objectPopulate2.populate();
		testModels.add(testModel2);
		testModel2.setParentId(null);
		
		//3
		TestSimpleTreeModel testModel3=new TestSimpleTreeModel();
		JObjectPopulate objectPopulate3=new JObjectPopulate(testModel3);
		objectPopulate3.populate();
		testModels.add(testModel3);
		testModel3.setParentId(null);
		
		//1.1
		TestSimpleTreeModel testModel11=new TestSimpleTreeModel();
		JObjectPopulate objectPopulate11=new JObjectPopulate(testModel11);
		objectPopulate11.populate();
		testModel11.setParentId(testModel1.getId());
		testModels.add(testModel11);
		
		//2.1
		TestSimpleTreeModel testModel21=new TestSimpleTreeModel();
		JObjectPopulate objectPopulate21=new JObjectPopulate(testModel21);
		objectPopulate21.populate();
		testModel21.setParentId(testModel2.getId());
		testModels.add(testModel21);
		
		//1.1.1
		TestSimpleTreeModel testModel111=new TestSimpleTreeModel();
		JObjectPopulate objectPopulate111=new JObjectPopulate(testModel111);
		objectPopulate111.populate();
		testModel111.setParentId(testModel11.getId());
		testModels.add(testModel111);
		
		//2.2
		TestSimpleTreeModel testModel22=new TestSimpleTreeModel();
		JObjectPopulate objectPopulate22=new JObjectPopulate(testModel22);
		objectPopulate22.populate();
		testModel22.setParentId(testModel2.getId());
		testModels.add(testModel22);
		
		//1.2
		TestSimpleTreeModel testModel12=new TestSimpleTreeModel();
		JObjectPopulate objectPopulate12=new JObjectPopulate(testModel12);
		objectPopulate12.populate();
		testModel12.setParentId(testModel1.getId());
		testModels.add(testModel12);
		
		//4
		TestSimpleTreeModel testModel4=new TestSimpleTreeModel();
		JObjectPopulate objectPopulate4=new JObjectPopulate(testModel4);
		objectPopulate4.populate();
		testModels.add(testModel4);
		testModel4.setParentId(null);
		
		//5-5
		TestSimpleTreeModel testModel5_5=new TestSimpleTreeModel();
		JObjectPopulate objectPopulate5_5=new JObjectPopulate(testModel5_5);
		objectPopulate5_5.populate();
		testModels.add(testModel5_5);
		testModel5_5.setParentId(null);
		
		//6_3_6
		TestSimpleTreeModel testModel6_3_6=new TestSimpleTreeModel();
		JObjectPopulate objectPopulate6_3_6=new JObjectPopulate(testModel6_3_6);
		objectPopulate6_3_6.populate();
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
