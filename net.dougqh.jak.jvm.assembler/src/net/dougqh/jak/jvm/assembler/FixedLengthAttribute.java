package net.dougqh.jak.jvm.assembler;

abstract class FixedLengthAttribute extends Attribute {
	private final int length;
	
	FixedLengthAttribute(
		final ConstantPool constantPool,
		final String name,
		final int length )
	{
		super( constantPool, name );
		this.length = length;
	}
	
	@Override
	final int length() {
		return this.length;
	}
	
	@Override
	boolean isEmpty() {
		return false;
	}
}
