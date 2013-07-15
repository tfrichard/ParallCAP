package cgl.imr.samples.parallcap.ivy.a;

import cgl.imr.base.SerializationException;
import cgl.imr.base.TwisterMessage;
import cgl.imr.base.Value;

public class IntVectorValue implements Value {
	int num;
	int[] array;
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int[] getArray() {
		return array;
	}

	public void setArray(int[] array) {
		this.array = array;
	}

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
