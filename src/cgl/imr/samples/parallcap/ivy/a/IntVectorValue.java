package cgl.imr.samples.parallcap.ivy.a;

import cgl.imr.base.SerializationException;
import cgl.imr.base.TwisterMessage;
import cgl.imr.base.Value;

public class IntVectorValue implements Value {
	int num;
	int[] array;
	
	public IntVectorValue() {
		num = 0;
		array = null;
	}
	
	public IntVectorValue(int length, int[] ids) {
		// TODO Auto-generated constructor stub
		num = length;
		array = ids;
	}

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
	public void fromTwisterMessage(TwisterMessage msg)
			throws SerializationException {
		// TODO Auto-generated method stub
		num = msg.readInt();
		array = new int[num];
		for (int i = 0; i < num; i++) {
			array[i] = msg.readInt();
		}
	}

	@Override
	public void toTwisterMessage(TwisterMessage msg)
			throws SerializationException {
		// TODO Auto-generated method stub
		msg.writeInt(num);
		for (int i : array) {
			msg.writeInt(i);
		}
	}

	@Override
	public void mergeInShuffle(Value arg0) {
		// TODO Auto-generated method stub
		
	}

}
