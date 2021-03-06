package net.dougqh.jak.repl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public final class NumericLiteralCommand extends ReplCommand {
	static final NumericLiteralCommand INSTANCE = new NumericLiteralCommand();
	
	private NumericLiteralCommand() {}
	
	@Override
	final boolean matches( final String command ) {
		char firstChar = command.charAt( 0 );
		return ( firstChar == '-' ) ||
			( firstChar == ReplArgument.CHAR_QUOTE ) ||
			Character.isDigit( firstChar ) ||
			isBooleanLiteral( command );
	}
	
	private static final boolean isBooleanLiteral( final String command ) {
		return ReplArgument.TRUE.equals( command ) ||
			ReplArgument.FALSE.equals( command );
	}
	
	@Override
	final boolean runProgramAfterCommand() {
		return true;
	}
	
	@Override
	final boolean run(
		final JakRepl repl,
		final String command,
		final List< String > args )
		throws IOException
	{
		try {
			char firstChar = command.charAt( 0 );
			if ( firstChar == ReplArgument.CHAR_QUOTE ) {
				Character literal = (Character)ReplArgument.CHAR.parse( repl, command );
				repl.codeWriter().iconst( literal );
			} else if ( isBooleanLiteral( command ) ) {
				Boolean literal = (Boolean)ReplArgument.BOOLEAN.parse( repl, command );
				repl.codeWriter().iconst( literal );
			} else {
				Class< ? > type = ReplArgument.typeQualifier( command );
				if ( type == null ) {
					Integer value = (Integer)ReplArgument.INT.parse( repl, command );
					repl.codeWriter().iconst( value );
				} else if ( type.equals( float.class ) ) {
					Float value = (Float)ReplArgument.FLOAT.parse( repl, command );
					repl.codeWriter().fconst( value );
				} else if ( type.equals( long.class ) ) {
					Long value = (Long)ReplArgument.LONG.parse( repl, command );
					repl.codeWriter().lconst( value );
				} else if ( type.equals( double.class ) ) {
					Double value = (Double)ReplArgument.DOUBLE.parse( repl, command );
					repl.codeWriter().dconst( value );
				} else {
					throw new IllegalStateException();
				}
			}
			return true;
		} catch ( IllegalArgumentException e ) {
			repl.console().printError( "Invalid literal" );
			return false;
		}
	}
}
