package cgl.imr.samples.parallcap.ivy.a;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import cgl.imr.base.SerializationException;
import cgl.imr.base.TwisterMessage;
import cgl.imr.base.Value;

public class Node implements Value {
	private int id;
	/*
	 * three colors of tag:
	 * white: not yet expanded
	 * gray: need to be expanded next iteration
	 * black: has been expanded and will never be expanded again
	 * */
	private String tag;
	//how many paths going through the node
	private int pathCount;
	//how many nodes each path has
	private List<Integer> numOfTraceNodes;
	//nodes on each path
	private List<List<Integer>> traceHistrory;
	
	public Node() {
		id = -1;
		tag = "";
		numOfTraceNodes = null;
		pathCount = 0;
		traceHistrory = null;
		}


	public Node(int parseInt) {
		// TODO Auto-generated constructor stub
		id = parseInt;
		tag = CAPConstraints.Write;
		numOfTraceNodes = new ArrayList<Integer>();
		pathCount = 0;
		traceHistrory = new ArrayList<List<Integer>>();
	}
	
	public void incPathCnt() { this.pathCount += 1; }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public List<List<Integer>> getTraceHistrory() {
		return traceHistrory;
	}
	public void setTraceHistrory(List<List<Integer>> traceHistrory) {
		this.traceHistrory = traceHistrory;
	}
	
	public List<Integer> getNumOfTraceNodes() {
		return numOfTraceNodes;
	}
	public void setNumOfTraceNodes(List<Integer> numOfTraceNodes) {
		this.numOfTraceNodes = numOfTraceNodes;
	}
	
	public int getPathCount() {
		return pathCount;
	}


	public void setPathCount(int pathCount) {
		this.pathCount = pathCount;
	}


	@Override
	public void fromTwisterMessage(TwisterMessage msg)
			throws SerializationException {
		// TODO Auto-generated method stub
		id = msg.readInt();
		tag = msg.readString();	
		pathCount = msg.readInt();
		numOfTraceNodes = new ArrayList<Integer>();
		for (int i = 0; i < pathCount; i++)
			numOfTraceNodes.add(msg.readInt());		
		traceHistrory = new ArrayList<List<Integer>>();

		for (int i = 0; i < pathCount; i++) {
			traceHistrory.add(new ArrayList<Integer>());
			for (int j = 0; j < numOfTraceNodes.get(i); j++) {
				traceHistrory.get(i).add(msg.readInt());
			}
		}
	}
	
	@Override
	public void toTwisterMessage(TwisterMessage msg)
			throws SerializationException {
		// TODO Auto-generated method stub
		msg.writeInt(id);
		msg.writeString(tag);
		msg.writeInt(pathCount);
		for (Integer i : numOfTraceNodes) {
			msg.writeInt(i);
		}
		for (int i = 0; i < pathCount; i++) {
			for (int j = 0; j < numOfTraceNodes.get(i); j++) {
				msg.writeInt(traceHistrory.get(i).get(j));
			}
		}
	}
	@Override
	public void mergeInShuffle(Value msg) {
		// TODO Auto-generated method stub
		
	}
	
	
}
