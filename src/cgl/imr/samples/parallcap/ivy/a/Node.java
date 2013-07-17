package cgl.imr.samples.parallcap.ivy.a;

import java.util.List;
import java.util.ArrayList;

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
	//traceHistory records the path along which query nodes come to the node
	private int numOfTraceNodes;
	private List<Integer> traceHistrory;
	
	public Node() {
		id = -1;
		tag = "";
		numOfTraceNodes = 0;
		traceHistrory = null;
		}


	public Node(int parseInt) {
		// TODO Auto-generated constructor stub
		id = parseInt;
		tag = CAPConstraints.Write;
		numOfTraceNodes = 0;
		traceHistrory = new ArrayList<Integer>();
	}
	public Node(int id2, String tag2) {
		// TODO Auto-generated constructor stub
		id = id2;
		tag = tag2;
	}
	public Node(int _id, String _tag, int _num) {
		id = _id;
		tag = _tag;
		numOfTraceNodes = _num;
	}
	
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
	
	public List<Integer> getTraceHistrory() {
		return traceHistrory;
	}
	public void setTraceHistrory(List<Integer> traceHistrory) {
		this.traceHistrory = traceHistrory;
	}
	
	public int getNumOfTraceNodes() {
		return numOfTraceNodes;
	}
	public void setNumOfTraceNodes(int numOfTraceNodes) {
		this.numOfTraceNodes = numOfTraceNodes;
	}
	
	@Override
	public void fromTwisterMessage(TwisterMessage msg)
			throws SerializationException {
		// TODO Auto-generated method stub
		id = msg.readInt();
		tag = msg.readString();		
		numOfTraceNodes = msg.readInt();

		traceHistrory = new ArrayList<Integer>();
		int tmpId = -1;	
		for (int i = 0; i < numOfTraceNodes; i++) {
			tmpId = msg.readInt();
			this.getTraceHistrory().add(tmpId);
		}
	}
	
	@Override
	public void toTwisterMessage(TwisterMessage msg)
			throws SerializationException {
		// TODO Auto-generated method stub
		msg.writeInt(id);
		msg.writeString(tag);
		msg.writeInt(numOfTraceNodes);
		
		for (int i = 0; i < numOfTraceNodes; i++) {
			msg.writeInt(traceHistrory.get(i));
		}
	}
	@Override
	public void mergeInShuffle(Value msg) {
		// TODO Auto-generated method stub
		
	}
	
	
}
