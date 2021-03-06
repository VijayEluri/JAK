package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class lconst_1 extends FixedConstantOperation {
	public static final String ID = "lconst_1";
	public static final byte CODE = LCONST_1;
	
	public static final lconst_1 instance() {
		return new lconst_1();
	}
	
	private lconst_1() {}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final Type type() {
		return long.class;
	}
	
	@Override
	public final <T> T value() {
		return ConstantOperation.<T>as( 1L );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.lconst_1();
	}
}