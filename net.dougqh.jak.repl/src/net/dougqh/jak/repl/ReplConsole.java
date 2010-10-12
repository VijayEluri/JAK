package net.dougqh.jak.repl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

import jline.CandidateListCompletionHandler;
import jline.Completor;
import jline.ConsoleReader;
import jline.ConsoleReaderInputStream;
import jline.History;
import net.dougqh.jak.JavaCoreCodeWriter.Jump;
import net.dougqh.jak.annotations.Op;
import net.dougqh.jak.operations.Operation;
import net.dougqh.jak.operations.Operations;
import net.dougqh.java.meta.types.JavaTypes;

final class ReplConsole {
	private final ConsoleReader reader;
	
	ReplConsole() throws IOException {
		File tempFile = File.createTempFile( "JavaAssemblerRepl", ".history" );

		this.reader = new ConsoleReader();
		this.reader.setHistory( new History( tempFile ) );
		this.reader.setCompletionHandler( new CandidateListCompletionHandler() );
	}
	
	final ReplConsole addCompletor( final Completor completor ) {
		this.reader.addCompletor( completor );
		return this;
	}
	
	final ReplConsole install() {
		ConsoleReaderInputStream.setIn( this.reader );
		return this;
	}
	
	final ReplConsole uninstall() {
		ConsoleReaderInputStream.restoreIn();
		return this;
	}
	
	final void complete( final String buffer ) throws IOException {
		this.reader.getCursorBuffer().clearBuffer();
		this.reader.getCursorBuffer().write( buffer );
		try {
			Method completeMethod = ConsoleReader.class.getDeclaredMethod( "complete" );
			completeMethod.setAccessible( true );
			completeMethod.invoke( this.reader );
		} catch ( NoSuchMethodException e ) {
			throw new IllegalStateException( e );
		} catch ( SecurityException e ) {
			throw new IllegalStateException( e );
		} catch ( IllegalArgumentException e ) {
			throw new IllegalStateException( e );
		} catch ( IllegalAccessException e ) {
			throw new IllegalStateException( e );
		} catch ( InvocationTargetException e ) {
			throw new IllegalStateException( e );
		} finally {
			this.reader.getCursorBuffer().clearBuffer();
		}
	}

	final ReplConsole clear() {
		try {
			//DQH - 10-10-2010 - Not using ConsoleReader.clearScreen 
			//because it does not work as desired when the REPL is run 
			//inside of Eclipse.
			for ( int i = 0; i < 40; ++i ) {
				this.reader.printNewline();
			}
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
		return this;
	}
	
	final String readCommand() throws IOException {
		return this.reader.readLine( "=>", '\0' );
	}
	
	final ReplConsole print( final Method interfaceMethod, final Object[] args ) {
		this.op( getNameOf( interfaceMethod ) );
		for ( Object arg: args ) {
			this.operand( arg );
		}
		this.endl();
		
		return this;
	}
	
	final ReplConsole printColumns( final String... columns ) {
		try {
			this.reader.printColumns( Arrays.asList( columns ) );
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
		return this;
	}
	
	final void printError( final String error ) throws IOException {
		this.reader.printString( error );
		this.reader.printNewline();
	}
	

	final ReplConsole op( final String code ) {
		return this.append( code );
	}
	
	private final ReplConsole operand( final Object operand ) {
		if ( operand instanceof Byte ) {
			return this.operand( ((Byte)operand).byteValue() );
		} else if ( operand instanceof Short ) {
			return this.operand( ((Short)operand).shortValue() );
		} else if ( operand instanceof Integer ) {
			return this.operand( ((Integer)operand).intValue() );
		} else if ( operand instanceof Type ) {
			return this.operand( (Type)operand );
		} else if ( operand instanceof Jump ) {
			return this.operand( (Jump)operand );
		} else {
			return this.todo( operand.getClass() );
		}
	}
	
	final ReplConsole append( final char ch ) {
		try {
			this.reader.printString( Character.toString( ch ) );
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
		return this;
	}
	
	final ReplConsole append( final String str ) {
		try {
			this.reader.printString( str );
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
		return this;
	}
	
	final ReplConsole operand( final byte operand ) {
		//TODO: Verify correctness
		return this.append( ' ' ).append( Byte.toString( operand ) );
	}
	
	final ReplConsole operand( final short operand ) {
		return this.append( ' ' ).append( Short.toString( operand ) );
	}
	
	final ReplConsole operand( final int operand ) {
		return this.append( ' ' ).append( Integer.toString( operand ) );
	}
	
	final ReplConsole operand( final Type operand ) {
		return this.append( ' ' ).append( JavaTypes.getRawClassName( operand ) );
	}
	
	final ReplConsole operand( final Jump jump ) {
		//TODO: Implement better
		return this.append( ' ' ).append( jump.toString() );
	}
	
	final ReplConsole todo() {
		//TODO: Remove when all output is handled
		this.append( " TODO" );
		return this;
	}
	
	final ReplConsole todo( final Class< ? > aClass ) {
		//TODO: Replace this for field and method signatures
		this.append( " TODO " + aClass.getSimpleName() );
		return this;
	}
	
	final ReplConsole endl() {
		//TODO: Platform specific new line
		try {
			this.reader.printNewline();
			this.reader.flushConsole();
			return this;
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	private static final String getNameOf( final Method method ) {
		Op op = method.getAnnotation( Op.class );
		Operation operation = Operations.getPrototype( op.value() );
		return operation.getId();
	}
}