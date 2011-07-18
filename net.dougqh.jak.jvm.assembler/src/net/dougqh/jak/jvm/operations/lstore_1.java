package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;

public final class lstore_1 extends JvmOperation {
	public static final String ID = "lstore_1";
	public static final byte CODE = LSTORE_1;
	
	public static final lstore_1 instance() {
		return new lstore_1();
	}
	
	private lstore_1() {}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final Class< ? >[] getCodeOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		return new Class< ? >[] { long.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_RESULTS;
	}
	
	@Override
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.lstore_1();
	}
}