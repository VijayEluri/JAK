package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;

public final class Ishl extends Operation {
	public static final String ID = "ishl";
	public static final byte CODE = ISHL;
	
	public static final Ishl instance() {
		return new Ishl();
	}
	
	private Ishl() {}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final String getOperator() {
		return LEFT_SHIFT;
	}
	
	@Override
	public final Class< ? >[] getCodeOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		return new Class< ? >[] { int.class, int.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { int.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.ishl();
	}
}