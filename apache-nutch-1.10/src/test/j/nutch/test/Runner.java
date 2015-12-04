package j.nutch.test;

import java.io.File;
import java.util.Arrays;

import org.apache.hadoop.util.ToolRunner;
import org.apache.nutch.crawl.CrawlDb;
import org.apache.nutch.crawl.Generator;
import org.apache.nutch.crawl.Injector;
import org.apache.nutch.crawl.LinkDb;
import org.apache.nutch.fetcher.Fetcher;
import org.apache.nutch.parse.ParseSegment;
import org.apache.nutch.segment.SegmentReader;
import org.apache.nutch.util.NutchConfiguration;

public class Runner {

	public static void main(String[] args) {
		String[] injectArgs = { "data/crawldb", "urls/" };
		String[] generatorArgs = { "data/crawldb", "data/segments", "-noFilter" };
		String[] fetchArgs = { "data/segments/" };
		String[] readsegArgs = { "-dump", "data/segments/", "data/readseg",
				"-noparsetext", "-noparse", "-noparsedata" };
		
		String[] parseSegmentArgs = { "data/segments/" };
		
		String[] linkeDbArgs={"data/linkdb","-dir","data/segments/"};
		
		String[] udpateDbArgs = { "data/crawldb","data/segments/" };

		File dataFile = new File("data");
		if (dataFile.exists()) {
			print("delete");
			deleteDir(dataFile);
		}
		try {
			ToolRunner.run(NutchConfiguration.create(), new Injector(),
					injectArgs);
			ToolRunner.run(NutchConfiguration.create(), new Generator(),
					generatorArgs);
			File segPath = new File("data/segments");
			String[] list = segPath.list();
			print(Arrays.asList(list));
			fetchArgs[0] = fetchArgs[0] + list[0];
			ToolRunner.run(NutchConfiguration.create(), new Fetcher(),
					fetchArgs);

			readsegArgs[1] += list[0];
			
			parseSegmentArgs[0] = parseSegmentArgs[0] + list[0];
			ToolRunner.run(NutchConfiguration.create(), new ParseSegment(), parseSegmentArgs);
			
			//linkdb
			ToolRunner.run(NutchConfiguration.create(), new LinkDb(), linkeDbArgs);
			
			//update db
			udpateDbArgs[1] += list[0];
			ToolRunner.run(NutchConfiguration.create(), new CrawlDb(), udpateDbArgs);
			
			SegmentReader.main(readsegArgs);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	public static final void print(Object text) {
		System.out.println(text);
	}
}
