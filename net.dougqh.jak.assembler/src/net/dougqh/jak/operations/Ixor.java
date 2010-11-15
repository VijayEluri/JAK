package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;

public final class Ixor extends Operation {
	public static final String ID = "ixor";
	public static final byte CODE = IXOR;
	
	public static final Ixor instance() {
		return new Ixor();
	}
	
	private Ixor() {}
	
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
		return XOR;
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
		writer.ixor();
	}
}