package cgl.imr.samples.parallcap.ivy.a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cgl.imr.base.Key;
import cgl.imr.base.MapOutputCollector;
import cgl.imr.base.MapTask;
import cgl.imr.base.TwisterException;
import cgl.imr.base.Value;
import cgl.imr.base.impl.JobConf;
import cgl.imr.base.impl.MapperConf;
import cgl.imr.data.file.FileData;
import cgl.imr.types.IntKey;
import cgl.imr.types.MemCacheAddress;
import cgl.imr.worker.MemCache;

public class ParallCAPMapTask implements MapTask {
	
	private StaticGraphData graph;
	private Map<Integer, Node> headNodes;
	private JobConf jobConf;
	private Map<Integer, List<Node>> nodeMap;

	@Override
	public void close() throws TwisterException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configure(JobConf jobConf, MapperConf mapConf)
			throws TwisterException {
		// TODO Auto-generated method stub
		this.jobConf = jobConf;
		FileData fileData = (FileData) mapConf.getDataPartition();
		this.graph = new StaticGraphData();
		this.nodeMap = new HashMap<Integer, List<Node>>();
		this.headNodes = new HashMap<Integer, Node>();
		
		try {
			this.graph.loadDataFromTextFile(fileData.getFileName());
		} catch (Exception e) {
			throw new TwisterException(e);
		}
		
		for (List<Node> list : graph.getAdjlist()) {
			headNodes.put(list.get(0).getId(), list.get(0));
			nodeMap.put(list.get(0).getId(), list);
		}
	}

	/*
	 * map task in charge of the local bfs search, go one step further in each iteration
	 * */
	@Override
	public void map(MapOutputCollector mapOutputCollector, Key key, Value val)
			throws TwisterException {
		System.out.println("MapTask is started!");
		System.out.println("Job: " + jobConf.getJobId() + "\n" +
			"MapTask: " + jobConf.getMapClass());
		
		//load gray value data from memcache
		MemCacheAddress memCacheKey = (MemCacheAddress) val;
		if (memCacheKey.getRange() != 1) {
			throw new TwisterException("MemCache size is not 1.");
		}
		IntVectorValue grayNodes = (IntVectorValue) (MemCache
				.getInstance().get(this.jobConf.getJobId(),
				memCacheKey.getMemCacheKeyBase() + memCacheKey.getStart()));
		
		//mark nodes to gray according to grayNodes vector
		int grayIds[] = grayNodes.getArray();
		for (int id : grayIds) {
			headNodes.get(id).setTag(CAPConstraints.Gray);
		}
		
		Map<Key, Value> mapOutputKeyValues = new HashMap<Key, Value>();
		List<Node> curList = null;
		
		for (Node node : headNodes.values()) {
			System.out.println("cur node color is " + node.getTag());
			if (node.getTag().equalsIgnoreCase(CAPConstraints.Gray)) {
				curList = nodeMap.get(node.getId());
				for (Node curNode : curList) {
					curNode.setTag(CAPConstraints.Gray);
					curNode.getTraceHistrory().add(node.getId());
					mapOutputKeyValues.put(new IntKey(curNode.getId()), 
							curNode);
				}
				node.setTag(CAPConstraints.Black);
			}
		}
		
		// collect
		if (!mapOutputKeyValues.isEmpty())
			mapOutputCollector.collect(mapOutputKeyValues);
		
		mapOutputKeyValues.clear();
		System.out.println("Map task is completed!");
	}
	
}




























