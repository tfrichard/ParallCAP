package cgl.imr.samples.parallcap.ivy.a;

import java.util.List;

import cgl.imr.base.SerializationException;
import cgl.imr.base.TwisterMessage;
import cgl.imr.base.Value;

public class NodeValue implements Value {
	public Node curNode;
	public List<List<Node>> traceList;
	@Override
	public void fromTwisterMessage(TwisterMessage arg0)
			throws SerializationException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void toTwisterMessage(TwisterMessage arg0)
			throws SerializationException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mergeInShuffle(Value arg0) {
		// TODO Auto-generated method stub
		
	}
}
