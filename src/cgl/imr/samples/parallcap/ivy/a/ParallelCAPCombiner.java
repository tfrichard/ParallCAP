package cgl.imr.samples.parallcap.ivy.a;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import cgl.imr.base.Combiner;
import cgl.imr.base.Key;
import cgl.imr.base.TwisterException;
import cgl.imr.base.Value;
import cgl.imr.base.impl.JobConf;
import cgl.imr.types.IntKey;

public class ParallelCAPCombiner implements Combiner {

	SortedMap<Key, Value> results;
	NodeVectorValue nodeVecVal;

	private class KeyComparator implements Comparator<Key> {
		@Override
		public int compare(Key key1, Key key2) {
			int k1 = ((IntKey) key1).getKey();
			int k2 = ((IntKey) key2).getKey();

			if (k1 < k2) {
				return -1;
			} else if (k1 == k2) {
				return 0;
			} else {
				return 1;
			}
		}
	}

	public ParallelCAPCombiner() {
		results = Collections.synchronizedSortedMap(new TreeMap<Key, Value>(
				new KeyComparator()));
	}

	public void close() throws TwisterException {
		// TODO Auto-generated method stub
	}

	/**
	 * Combines the reduce outputs to a single value.
	 */
	public void combine(Map<Key, Value> keyValues) throws TwisterException {
		// synchronized (this) {
		this.results.clear();
		System.out.println("value size is: " + keyValues.values().size());
		for (Key key : keyValues.keySet()) {
			System.out.println("key is:" + ((IntKey)key).getKey());
			this.results.put(key, keyValues.get(key));
		}
		// }
	}

	public void configure(JobConf jobConf) throws TwisterException {
		// TODO Auto-generated method stub

	}

	public List<Value> getResults() {
		List<Value> valueRes = new ArrayList<Value>();
		NodeVectorValue res = new NodeVectorValue();
		int cnt = 0;
		for (Value val : results.values()) {
			//res.getGrayNodeList().addAll(((NodeVectorValue)val).getGrayNodeList());
			res.getGrayNodeList().add((Node)val);
			cnt += 1;
			//cnt += ((NodeVectorValue)val).getNumOfGrayNodes();
		}
		res.setNumOfGrayNodes(cnt);
		
		System.out.println("all nodes in combiner: ");
		for (Node node : res.getGrayNodeList()) {
			System.out.println(node.getId());
		}
		valueRes.add(res);
		results.clear();
		return valueRes;
	}
}