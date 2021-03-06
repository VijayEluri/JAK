package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Slot;

public final class dload extends VariableLoadOperation {
	public static final String ID = "dload";
	public static final byte CODE = DLOAD;
	
	public static final dload prototype() {
		return new dload( 0 );
	}
	
	public dload( final int slot ) {
		super( slot );
	}
	
	public dload( final Slot slot ) {
		super( slot );
	}
	
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
		return double.class;
	}
	
	@Override
	protected final void process( final JvmOperationProcessor processor, final int slot ) {
		processor.dload( slot );
	}
	
	@Override
	protected final void process( final JvmOperationProcessor processor, final Slot slot ) {
		processor.dload( slot );
	}
	
	@Override
	public final boolean canNormalize() {
		return ( this.slot() < 4 );
	}
	
	@Override
	public final void normalize( final JvmOperationProcessor processor ) {
		normalize( processor, this.slot() );
	}
	
	public static final void normalize(
		final JvmOperationProcessor processor,
		final int slot )
	{
		switch ( slot ) {
			case 0:
			processor.dload_0();
			break;
			
			case 1:
			processor.dload_1();
			break;
			
			case 2:
			processor.dload_2();
			break;
			
			case 3:
			processor.dload_3();
			break;
			
			default:
			processor.dload( slot );
			break;
		}
	}
}