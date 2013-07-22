package cgl.imr.samples.parallcap.ivy.a;

import java.util.ArrayList;
import java.util.List;

import cgl.imr.base.Key;
import cgl.imr.base.ReduceOutputCollector;
import cgl.imr.base.ReduceTask;
import cgl.imr.base.TwisterException;
import cgl.imr.base.Value;
import cgl.imr.base.impl.JobConf;
import cgl.imr.base.impl.ReducerConf;
import cgl.imr.types.IntKey;
import cgl.imr.types.IntValue;

public class ParallelCAPReduceTask implements ReduceTask {
	
	JobConf jobConf;

	@Override
	public void close() throws TwisterException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configure(JobConf jobConf, ReducerConf reducerConf)
			throws TwisterException {
		// TODO Auto-generated method stub
		this.jobConf = jobConf;
	}

	@Override
	public void reduce(ReduceOutputCollector collector, Key key, List<Value> values)
			throws TwisterException {
		// TODO Auto-generated method stub
		System.out.println(" Key: " + ((IntKey)key).getKey() +
				" Value Size: " + values.size());
		
		if (values.size() != 1) {
			throw new TwisterException("Reduce input error: invalid values.");
		}
		
		System.out.println("all received gray node ids:");
		for (Value val : values) {
			Node node = (Node)val;
			System.out.println(node.getId());
			for (List<Integer> path : node.getTraceHistrory()) {
				if (path.size() < Integer.parseInt(jobConf.getProperty("PATHLIMIT"))) {
					//valid length
					
				}
			}
		}
		
		List<Node> res = new ArrayList<Node>();
		for (Value val : values) {
			res.add((Node)val);
		}
		
		//add matrix elements and eliminate invalid xe-fragment happens here
		
		NodeVectorValue nodeVecVal = new NodeVectorValue(values.size(), res);
		collector.collect(key, nodeVecVal);
	}
	
}




















