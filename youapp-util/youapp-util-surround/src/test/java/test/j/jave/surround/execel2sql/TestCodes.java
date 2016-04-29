package test.j.jave.surround.execel2sql;

import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class TestCodes extends TestBaseExecel2Sql {

	public TestCodes(String input, String output) {
		super(input, output);
	}
	
	public TestCodes() {
		super(prefix+file, prefix+"/dictionary.sql");
	}

	private static String prefix="D:\\java_\\project\\tec-mall-svn\\Doc\\3设计阶段\\数据库";
	
	private static String file="\\科技超市数据字典.xls";
	
	@Override
	protected StringBuffer doParse(InputStream inputStream) throws Exception {

		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("TRUNCATE TABLE t_dictionary;");
		stringBuffer.append("\n");
		stringBuffer.append("TRUNCATE TABLE t_dictionary_data;");
		stringBuffer.append("\n");
		Workbook wb = null;
		wb = new HSSFWorkbook(inputStream);
		Iterator<Sheet> iterator=  wb.sheetIterator();
		while(iterator.hasNext()){
			Sheet sheet= iterator.next();
			
			logger.info("sheet:-----------> "+sheet.getSheetName());
			// codetype
			Row codeTypeRow=sheet.getRow(2);
			String codeTypeCode=codeTypeRow.getCell(0).getStringCellValue();
			String codeTypeName=codeTypeRow.getCell(1).getStringCellValue();
			String available=codeTypeRow.getCell(2).getStringCellValue();
			
			String codeTypeFinalString=""
					+ "insert into t_dictionary(id,dict_code,dict_name,is_available,version,creator_id,create_date,modifier_id,modify_date)"
					+"values(cast(NEWID() as varchar(36)),'"+codeTypeCode+"','"+codeTypeName+"',"+("Y".equals(available)?1:0)+",0,'SYS',getdate(),'SYS',getdate());";
			stringBuffer.append(codeTypeFinalString+"\n");
			
			int lastRowNum=sheet.getLastRowNum();
			int codeIndex=5;
			while(codeIndex<=lastRowNum){
				
				// code
				Row codeRow=sheet.getRow(codeIndex);
				
				Cell cell=codeRow.getCell(0);
				if(cell==null||"".equals(codeRow.getCell(0).getStringCellValue().trim())){
					break;
				}
				
				String code=codeRow.getCell(0).getStringCellValue();
				logger.info("sheet:-----------> "+sheet.getSheetName()
						+"code-------------->"+code);
				
				String name=codeRow.getCell(1).getStringCellValue();
				logger.info("sheet:-----------> "+sheet.getSheetName()
						+"name-------------->"+name);
				
				String codeFinalString=""
						+"insert into t_dictionary_data(id,dict_id,dictdata_value,dictdata_name,is_available,version,creator_id,create_date,modifier_id,modify_date)"
						+"values(cast(NEWID() as varchar(36))," 
						+"(select a.id from t_dictionary a where a.dict_code='"+codeTypeCode+"'),'"+code+"','"+name+"',1,0,'SYS',getdate(),'SYS',getdate())";
				stringBuffer.append(codeFinalString+"\n");
				codeIndex++;
			}
		}
		return stringBuffer;
	}
	
}
