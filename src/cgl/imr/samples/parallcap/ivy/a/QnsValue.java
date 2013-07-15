package cgl.imr.samples.parallcap.ivy.a;

import java.util.List;

import cgl.imr.base.SerializationException;
import cgl.imr.base.TwisterMessage;
import cgl.imr.base.Value;

public class QnsValue implements Value {
	private int numOfQns;
	private List<Node> qnsList;

	@Override
	public void fromTwisterMessage(TwisterMessage msg)
			throws SerializationException {
		// TODO Auto-generated method stub
		numOfQns = msg.readInt();
		Node node = null;
		for (int i = 0; i < numOfQns; i++) {
			int id = msg.readInt();
			String tag = msg.readString();
			node = new Node(id, tag);
			qnsList.add(node);
		}
	}

	@Override
	public void toTwisterMessage(TwisterMessage msg)
			throws SerializationException {
		// TODO Auto-generated method stub
		msg.writeInt(numOfQns);
		for (Node node : qnsList) {
			msg.writeInt(node.getId());
			msg.writeString(node.getTag());
		}
	}

	public int getNumOfQns() {
		return numOfQns;
	}

	public void setNumOfQns(int numOfQns) {
		this.numOfQns = numOfQns;
	}

	public List<Node> getQnsList() {
		return qnsList;
	}

	public void setQnsList(List<Node> qnsList) {
		this.qnsList = qnsList;
	}

	@Override
	public void mergeInShuffle(Value arg0) {
		// TODO Auto-generated method stub
		
	}

}
