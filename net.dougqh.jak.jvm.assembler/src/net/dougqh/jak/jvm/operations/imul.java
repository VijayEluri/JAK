package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;

public final class imul extends JvmOperation {
	public static final String ID = "imul";
	public static final byte CODE = IMUL;
	
	public static final imul instance() {
		return new imul();
	}
	
	private imul() {}
	
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
		return MUL;
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
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.imul();
	}
}