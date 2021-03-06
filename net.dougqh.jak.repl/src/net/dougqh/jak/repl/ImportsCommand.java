package net.dougqh.jak.repl;

import java.io.IOException;
import java.util.List;

final class ImportsCommand extends FixedIdCommand {
	public static final String ID = "imports";
	public static final ImportsCommand INSTANCE = new ImportsCommand();
	
	private ImportsCommand() {
		super( ID );
	}
	
	@Override
	final boolean run(
		final JakRepl repl,
		final String command,
		final List< String > args )
		throws IOException
	{
		checkNoArguments( args );
		
		repl.imports().print( repl.console() );
		return true;
	}
}
