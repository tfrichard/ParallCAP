package cgl.imr.samples.parallcap.ivy.a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cgl.imr.base.SerializationException;
import cgl.imr.base.TwisterMessage;
import cgl.imr.base.Value;

public class NodeVectorValue implements Value {
	private int numOfGrayNodes;
	List<Node> grayNodeList;
	
	public NodeVectorValue() {
		numOfGrayNodes = 0;
		grayNodeList = new ArrayList<Node>();
	}
	
	public NodeVectorValue(int _num, List<Node> _grayNodes) {
		this.numOfGrayNodes = _num;
		this.grayNodeList = _grayNodes;
	}

	public int getNumOfGrayNodes() {
		return numOfGrayNodes;
	}

	public void setNumOfGrayNodes(int numOfGrayNodes) {
		this.numOfGrayNodes = numOfGrayNodes;
	}

	public List<Node> getGrayNodeList() {
		return grayNodeList;
	}

	public void setGrayNodeList(List<Node> grayNodeList) {
		this.grayNodeList = grayNodeList;
	}
	
	public void decNum(int k) { this.numOfGrayNodes -= k; }

	@Override
	public void fromTwisterMessage(TwisterMessage msg)
			throws SerializationException {
		// TODO Auto-generated method stub
		numOfGrayNodes = msg.readInt();
		for (int i = 0; i < numOfGrayNodes; i++) {
			Node node = new Node();
			node.fromTwisterMessage(msg);
			grayNodeList.add(node);
		}
	}
	
	@Override
	public void toTwisterMessage(TwisterMessage msg)
			throws SerializationException {
		// TODO Auto-generated method stub
		msg.writeInt(numOfGrayNodes);
		for (Node node : grayNodeList) {
			node.toTwisterMessage(msg);
		}
	}
	
	@Override
	public void mergeInShuffle(Value arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
