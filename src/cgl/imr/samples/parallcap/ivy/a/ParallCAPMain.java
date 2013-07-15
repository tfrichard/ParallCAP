package cgl.imr.samples.parallcap.ivy.a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.safehaus.uuid.UUIDGenerator;

import cgl.imr.base.TwisterMonitor;
import cgl.imr.base.Value;
import cgl.imr.base.impl.JobConf;
import cgl.imr.client.TwisterDriver;
import cgl.imr.types.MemCacheAddress;

public class ParallCAPMain {
	private static Map<Integer, Map<Integer, List<Integer>>> queryIdMatrix;
	private static Qns qns;
	
	public ParallCAPMain() {
		qns = new Qns();
		queryIdMatrix = new HashMap<Integer, Map<Integer, List<Integer>>>();
		final List<Node> queryList = qns.getQueryNodes();
		for (int i = 0; i < queryList.size() - 1; i++) {
			queryIdMatrix.put(queryList.get(i).getId(), 
					new HashMap<Integer, List<Integer>>());
		}
		
		for (Integer integer : queryIdMatrix.keySet()) {
			for (int i = 1; i < queryList.size(); i++) {
				//initialize the matrix so as each cell is empty
				queryIdMatrix.get(integer).put(queryList.get(i).getId(), new ArrayList<Integer>());
			}
		}
		
		for (Integer m : queryIdMatrix.keySet()) {
			for (Integer n: queryIdMatrix.get(m).keySet()) {
				System.out.println("( " + m + ", " + n + " )");
			}
			System.out.println("\n");
		}
	}
	
	//mark query matrix based on the current gray node list 
	//and query nodes
	private static void markQueryMatrix(List<Value> grayNodes) {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		/*
		 * Main program to run parallel cap MapReduce job
		 * @param args
		 * @throws Exception
		 * */
		ParallCAPMain pcapMain = new ParallCAPMain();	

	}
	
	public static List<Value> driveMapReduce(int numMapTasks, int numReduceTasks, 
			String partitionFile, int numLoop) throws Exception {
		//JobConfigurations
		JobConf jobConf = new JobConf("ParallCap-map-reduce"
				+ UUIDGenerator.getInstance().generateTimeBasedUUID());
		jobConf.setMapperClass(ParallCAPMapTask.class);
		jobConf.setReducerClass(ParallelCAPReduceTask.class);
		jobConf.setCombinerClass(ParallelCAPCombiner.class);
		jobConf.setNumMapTasks(numMapTasks);
		jobConf.setNumReduceTasks(numReduceTasks);
		jobConf.setFaultTolerance();
		TwisterDriver driver = new TwisterDriver(jobConf);
		driver.configureMaps(partitionFile);
		
		List <Value> grayNodes = new ArrayList<Value>();
		for (Node node : qns.getQueryNodes()) {
			grayNodes.add(node);
		}
		
		int bfsIterCnt = 0;
		for (; bfsIterCnt < numLoop; bfsIterCnt++) {
			MemCacheAddress memCacheKey = driver.addToMemCache(grayNodes);
			TwisterMonitor monitor = driver.runMapReduceBCast(memCacheKey);
			monitor.monitorTillCompletion();
			driver.cleanMemCache();
			grayNodes = ((ParallelCAPCombiner) driver.getCurrentCombiner())
					.getResults();
			markQueryMatrix(grayNodes);
			System.out.println("Search step " + bfsIterCnt);
		}
		driver.close();
		return grayNodes;
		
	}

}











