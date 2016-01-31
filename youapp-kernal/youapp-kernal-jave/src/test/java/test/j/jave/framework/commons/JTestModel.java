package test.j.jave.framework.commons;

import j.jave.kernal.jave.random.JObjectPopulate;
import j.jave.kernal.jave.support.treeview.JTree;
import j.jave.kernal.jave.support.treeview.JTreeStrcture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JTestModel implements JTreeStrcture {

	private String id;
	
	private String parentId;
	
	private boolean isText;
	
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
		
		List<JTestModel> testModels=new ArrayList<JTestModel>();
		
		//1
		JTestModel testModel1=new JTestModel();
		JObjectPopulate objectPopulate1=new JObjectPopulate(testModel1);
		objectPopulate1.populate();
		testModel1.setText(false);
		testModel1.setParentId(null);
		testModels.add(testModel1);
		
		//2
		JTestModel testModel2=new JTestModel();
		JObjectPopulate objectPopulate2=new JObjectPopulate(testModel2);
		objectPopulate2.populate();
		testModels.add(testModel2);
		testModel2.setText(false);
		testModel2.setParentId(null);
		
		//3
		JTestModel testModel3=new JTestModel();
		JObjectPopulate objectPopulate3=new JObjectPopulate(testModel3);
		objectPopulate3.populate();
		testModels.add(testModel3);
		testModel3.setText(false);
		testModel3.setParentId(null);
		
		//1.1
		JTestModel testModel11=new JTestModel();
		JObjectPopulate objectPopulate11=new JObjectPopulate(testModel11);
		objectPopulate11.populate();
		testModel11.setParentId(testModel1.getId());
		testModels.add(testModel11);
		testModel11.setText(false);
		
		//2.1
		JTestModel testModel21=new JTestModel();
		JObjectPopulate objectPopulate21=new JObjectPopulate(testModel21);
		objectPopulate21.populate();
		testModel21.setParentId(testModel2.getId());
		testModels.add(testModel21);
		testModel21.setText(false);
		
		//1.1.1
		JTestModel testModel111=new JTestModel();
		JObjectPopulate objectPopulate111=new JObjectPopulate(testModel111);
		objectPopulate111.populate();
		testModel111.setParentId(testModel11.getId());
		testModels.add(testModel111);
		
		//2.2
		JTestModel testModel22=new JTestModel();
		JObjectPopulate objectPopulate22=new JObjectPopulate(testModel22);
		objectPopulate22.populate();
		testModel22.setParentId(testModel2.getId());
		testModels.add(testModel22);
		testModel22.setText(false);
		
		//1.2
		JTestModel testModel12=new JTestModel();
		JObjectPopulate objectPopulate12=new JObjectPopulate(testModel12);
		objectPopulate12.populate();
		testModel12.setParentId(testModel1.getId());
		testModels.add(testModel12);
		testModel12.setText(false);
		
		//4
		JTestModel testModel4=new JTestModel();
		JObjectPopulate objectPopulate4=new JObjectPopulate(testModel4);
		objectPopulate4.populate();
		testModels.add(testModel4);
		testModel4.setText(false);
		testModel4.setParentId(null);
		
		//5-5
		JTestModel testModel5_5=new JTestModel();
		JObjectPopulate objectPopulate5_5=new JObjectPopulate(testModel5_5);
		objectPopulate5_5.populate();
		testModels.add(testModel5_5);
		testModel5_5.setText(true);
		testModel5_5.setParentId(null);
		
		//6_3_6
		JTestModel testModel6_3_6=new JTestModel();
		JObjectPopulate objectPopulate6_3_6=new JObjectPopulate(testModel6_3_6);
		objectPopulate6_3_6.populate();
		testModel6_3_6.setParentId(testModel3.getId());
		testModels.add(testModel6_3_6);
		testModel6_3_6.setText(false);
		
//		testModel3.setParentId(testModel6_3_6.getId());
		
		
		for (Iterator<JTestModel> iterator = testModels.iterator(); iterator.hasNext();) {
			JTestModel jTestModel =  iterator.next();
			
			System.out.println(jTestModel.getId()+"----------"+jTestModel.getParentId()+"--------------"+jTestModel.isText());
		}
		
		
		System.out.println(new JTree(testModels).get().represent());
	}
	
}
