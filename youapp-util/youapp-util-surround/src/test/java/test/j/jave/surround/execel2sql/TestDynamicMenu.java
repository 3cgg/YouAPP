package test.j.jave.surround.execel2sql;

import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class TestDynamicMenu extends TestBaseExecel2Sql {

	private static String prefix="D:\\java_\\project\\tec-mall-svn\\Doc\\3设计阶段\\数据库";
	
	private static String file="\\动态菜单字典表.xls";
	
	public TestDynamicMenu(String input, String output) {
		super(input, output);
	}

	public TestDynamicMenu() {
		super(prefix+file, prefix+"/t_dynamic_menu.sql");
	}
	
	@Override
	protected StringBuffer doParse(InputStream inputStream) throws Exception {

		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("TRUNCATE table t_dynamic_menu;");
		stringBuffer.append("\n");
		Workbook wb = null;
		wb = new HSSFWorkbook(inputStream);
		Iterator<Sheet> iterator=  wb.sheetIterator();
		while(iterator.hasNext()){
			Sheet sheet= iterator.next();
			
			logger.info("sheet:-----------> "+sheet.getSheetName());
			
			int lastRowNum=sheet.getLastRowNum();
			int codeIndex=3;
			String rootCode=null;
			while(codeIndex<=lastRowNum){
				
				// TREE
				Row codeRow=sheet.getRow(codeIndex);
				
				short lastColumnNum=codeRow.getLastCellNum();
				short columnIndex=0;
				boolean has=false;
				while(columnIndex<=lastColumnNum){
					Cell cell=codeRow.getCell(columnIndex);
					if(cell==null||"".equals(cell.getStringCellValue().trim())){
						columnIndex++;
						continue;
					}
					else{
						has=true;
						break;
					}
				}
				
				if(!has){
					break;
				}
				
				String code=codeRow.getCell(columnIndex).getStringCellValue().trim();
				String name=codeRow.getCell(5).getStringCellValue().trim();
				String available=codeRow.getCell(6).getStringCellValue().trim();
				
				logger.info("name---: "+name);
				
				stringBuffer.append("insert into t_dynamic_menu("
						+"[id],[creator_id] ,[create_date]  ,[modifier_id] ,[modify_date] ,[is_available],[version] ,"
						+"[code] ,[name] ,[sequence],[level],[path],[parent_id] ,[img_id] )"
						+" values(cast(NEWID() as varchar(36)),'SYS',getdate(),'SYS',getdate(),"+("Y".equals(available)?1:0)+",0,"
						+"'"+code+"','"+name+"',"+"0,"+columnIndex+",'"+getPath(code, rootCode)
						+"',(select a.id from t_dynamic_menu a where a.code='"+getParentPath(code, rootCode)+"' and a.is_available=1)"
						+",null);");
				stringBuffer.append("\n");
				codeIndex++;
			}
		}
		return stringBuffer;
	}
	
	private static String getPath(String code,String rootCode){
		String path="";
		int index=0;
		int n=1;
		while(index<code.length()){
			index=n*2;
			path=path+code.substring(0, index)+"/";
			n++;
		}
		return path;
	}
	
	private static String getParentPath(String code,String rootCode){
		
		
		
		
		
		return code.substring(0,code.length()-2);
	}
	
}
