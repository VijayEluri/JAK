package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;

public final class i2s extends JvmOperation {
	public static final String ID = "i2s";
	public static final byte CODE = I2S;
	
	public static final i2s instance() {
		return new i2s();
	}
	
	private i2s() {}
	
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
		return new Class< ? >[] { int.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { short.class };
	}
	
	@Override
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.i2s();
	}
}