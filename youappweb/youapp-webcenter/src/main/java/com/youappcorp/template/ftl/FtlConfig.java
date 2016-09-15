package com.youappcorp.template.ftl;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public class FtlConfig {

	private static final FtlConfig INSTANCE=new FtlConfig();
	
	private Configuration cfg;
	
	private Object sync=new Object();
	
	private void init(){
		
		synchronized (sync) {
			if(cfg==null){
				try{
					/* ------------------------------------------------------------------------ */
			        /* You should do this ONLY ONCE in the whole application life-cycle:        */

			        /* Create and adjust the configuration singleton */
			        cfg = new Configuration(Configuration.VERSION_2_3_23);
			        cfg.setClassForTemplateLoading(FtlConfig.class, "");
			        cfg.setDefaultEncoding("UTF-8");
			        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			        cfg.setLogTemplateExceptions(true);

			        /* ------------------------------------------------------------------------ */
			        /* You usually do these for MULTIPLE TIMES in the application life-cycle:   */
				}catch(Exception e){
					cfg=null;
					throw new RuntimeException(e);
				}
			}
		}
		
		
		
	}
	
	public static final FtlConfig get(){
		return INSTANCE;
	}
	
	public Configuration getCfg(){
		if(cfg==null){
			init();
		}
		return cfg;
	}
	
}
