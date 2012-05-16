package net.dougqh.jak.jvm.assembler.api;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;
import net.dougqh.java.meta.types.type;

import org.junit.Test;

import static net.dougqh.jak.Jak.*;
import static org.junit.Assert.*;

public final class GenericsTest {
	@Test
	public final void specificReturnType() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "SpecificReturnType" ) );
		
		classWriter.defineDefaultConstructor();
		
		Type List_String = type( List.class ).of( String.class ).make();
		
		classWriter.define( public_().final_().method( List_String, "getStrings" ) ).
			aconst_null().
			areturn();
		
		Class< ? > generatedClass = classWriter.load();
		Method method = getMethod( generatedClass, "getStrings" );
		
		assertEquals(
			List.class,
			method.getReturnType() );
		assertEquals(
			type( List.class ).of( String.class ).make(),
			method.getGenericReturnType() );
	}
	
	@Test
	public final void wildcardExtendsType() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "WilcardExtends" ) );
		
		classWriter.defineDefaultConstructor();
		
		Type List_extends_String = 
			type( List.class ).of(
				type().extends_( String.class ) ).make();
		
		classWriter.define( public_().final_().method( List_extends_String, "getStrings" ) ).
			aconst_null().
			areturn();
		
		Class< ? > generatedClass = classWriter.load();
		Method method = getMethod( generatedClass, "getStrings" );
		
		assertEquals(
			type( List.class ).of( type().extends_( String.class ) ).make(),
			method.getGenericReturnType() );
	}
	
	@Test
	public final void specificParameterType() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "SpecificReturnType" ) );
		
		classWriter.defineDefaultConstructor();
		
		Type List_String = type( List.class ).of( String.class ).make();
		
		classWriter.define( public_().final_().method( void.class, "setStrings", List_String ) ).
			aconst_null().
			return_();
		
		Class< ? > generatedClass = classWriter.load();
		Method method = getMethod( generatedClass, "setStrings" );
		
		Class< ? >[] paramClasses = method.getParameterTypes();
		Type[] paramTypes = method.getGenericParameterTypes();
		
		assertEquals( 1, paramClasses.length );
		assertEquals( 1, paramTypes.length );
		
		assertEquals( List.class, paramClasses[ 0 ] );
		assertEquals(
			type( List.class ).of( String.class ).make(),
			paramTypes[ 0 ] );
	}
	
	@Test
	public final void specificCode() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "SpecificCode" ).implements_( Creator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		Type ArrayList_String = type( ArrayList.class ).of( String.class ).make();
		
		classWriter.define( public_().final_().method( Object.class, "create" ) ).
			new_( ArrayList_String ).
			dup().
			invokespecial( ArrayList_String, init() ).
			areturn();
		
		Creator creator = classWriter.< Creator >newInstance();
		assertTrue( creator.create() instanceof List );
	}
	
	@Test
	public final void specificField() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "SpecificField" ) );

		Type List_String = new type< List< String > >() {};
		
		classWriter.define(
			public_().static_().field( List_String, "strings" ) );
		
		Field field = getField( classWriter.load(), "strings" );
		
		assertEquals(
			type( List.class ).of( String.class ).make(),
			field.getGenericType() );
	}
	
	@Test
	public final void specificExtension() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().abstract_().class_( "SpecificExtension" ).
				extends_( type( AbstractList.class ).of( String.class ) ).
				implements_( type( List.class ).of( String.class ) ) );
		
		Class< ? > aClass = classWriter.load();
		
		assertEquals(
			type( AbstractList.class ).of( String.class ).make(),
			aClass.getGenericSuperclass() );
		
		Type[] interfaceTypes = aClass.getGenericInterfaces();
		assertEquals( 1, interfaceTypes.length );
		assertEquals(
			type( List.class ).of( String.class ).make(),
			interfaceTypes[ 0 ] );
	}
	
	private static final Field getField(
		final Class< ? > aClass,
		final String fieldName )
	{
		try {
			return aClass.getField( fieldName );
		} catch ( SecurityException e ) {
			throw new IllegalStateException( e );
		} catch ( NoSuchFieldException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	private static final Method getMethod(
		final Class< ? > aClass,
		final String methodName )
	{
		for ( Method method: aClass.getDeclaredMethods() ) {
			if ( method.getName().equals( methodName ) ) {
				return method;
			}
		}
		throw new IllegalStateException( "Method " + methodName + " not found" );
	}
	
	public interface Creator {
		public abstract Object create();
	}
}
