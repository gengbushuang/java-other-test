package com.rpc.message;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface MsgCoder {

	void write(DataOutput out) throws IOException;
	
	void readFields(DataInput in) throws IOException;
}
