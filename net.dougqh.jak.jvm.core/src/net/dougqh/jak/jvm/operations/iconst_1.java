package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class iconst_1 extends FixedConstantOperation {
	public static final String ID = "iconst_1";
	public static final byte CODE = ICONST_1;
	
	public static final iconst_1 instance() {
		return new iconst_1();
	}
	
	private iconst_1() {}
	
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
		return int.class;
	}
	
	@Override
	public final <T> T value() {
		return ConstantOperation.<T>as( 1 );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.iconst_1();
	}
}