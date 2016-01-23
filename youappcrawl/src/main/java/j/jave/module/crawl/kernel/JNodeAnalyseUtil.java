package j.jave.module.crawl.kernel;

import j.jave.module.crawl.def.JWebModel;
import j.jave.module.crawl.def.JWebModelDefProperties;
import j.jave.module.crawl.def.JWebNodeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JNodeAnalyseUtil {
	
	private static Map<String, JNodeAnalyse> customers=new HashMap<String, JNodeAnalyse>();
	
	public static List<JNodeAnalyse> get(Class<? extends JWebModel> webModelClass){
		try{
			JWebNodeModel webNodeModel= webModelClass.getAnnotation(JWebNodeModel.class);
			List<JNodeAnalyse> nodeAnalyses=new ArrayList<JNodeAnalyse>();
			
			if(webNodeModel!=null){
				String[] analysers=webNodeModel.analyse();
				for (int i = 0; i < analysers.length; i++) {
					String analyser=analysers[i];
					if(JWebModelDefProperties.NODE_ANALYSE_TABLE.equals(analyser)){
						JTableAnalyse tableAnalyse=new JTableAnalyse();
						nodeAnalyses.add(tableAnalyse);
					}
					else{
						JNodeAnalyse analyse=null;
						if((analyse=customers.get(analyser))!=null){
							nodeAnalyses.add(analyse); 
						}
						else{
							analyse=(JNodeAnalyse) Class.forName(analyser).newInstance();
							customers.put(analyser, analyse);
						}
						nodeAnalyses.add(analyse);
					}
				}
			}
			return nodeAnalyses;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}
	
}
