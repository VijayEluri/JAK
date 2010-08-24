package net.dougqh.jak;

import java.lang.reflect.Type;

import net.dougqh.java.meta.types.JavaTypeBuilder;
import net.dougqh.java.meta.types.JavaTypes;

public final class JavaClassDescriptor {
	private final int flags;
	private final String name;
	private Type parentType = Object.class;
	private Type[] interfaceTypes = new Type[] {};
	
	JavaClassDescriptor(
		final JavaFlagsBuilder flagsBuilder,
		final String name )
	{
		this.flags = flagsBuilder.flags();
		this.name = name;
	}
		
	public final JavaClassDescriptor extends_( final String name ) {
		return this.extends_( JavaTypes.objectTypeName( name ) );
	}
	
	public final JavaClassDescriptor extends_( final JavaTypeBuilder typeBuilder ) {
		return this.extends_( typeBuilder.make() );
	}
	
	public final JavaClassDescriptor extends_( final Type type ) {
		this.parentType = JavaTypes.resolve( type );
		return this;
	}
	
	public final JavaClassDescriptor implements_( final JavaTypeBuilder... typeBuilders ) {
		return this.implements_( JavaTypeBuilder.make( typeBuilders ) );
	}
	
	public final JavaClassDescriptor implements_( final Type... types ) {
		this.interfaceTypes = JavaTypes.resolve( types );
		return this;
	}
	
	protected final TypeDescriptor typeDescriptor() {
		return new TypeDescriptor(
			this.flags,
			this.name,
			this.parentType,
			this.interfaceTypes );
	}
}
