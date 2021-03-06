package net.dougqh.jak.jvm.assembler;

import java.util.ListIterator;

import net.dougqh.jak.FormalArguments;
import net.dougqh.jak.JavaVariable;

final class JvmCodeWriterImpl extends JvmCodeWriter {
	private final JvmCoreCodeWriter coreWriter;
	
	private final CodeWritingState sharedState = new CodeWritingState();

	JvmCodeWriterImpl(
		final boolean isStatic,
		final FormalArguments arguments,
		final JvmCoreCodeWriter coreWriter )
	{
		this.coreWriter = coreWriter;
		
		//DQH - Currently, parameter handling is a horrible mess.
		//TypeWriter.define handles reserving space for the locals, but 
		//cannot handle name associations.  That means this code cannot 
		//simply use declare because the space for each variable is already
		//reserved.  Instead this code must resort to forcibly computing the
		//slot and associating as needed.
		if ( ! isStatic ) {
			this.varScope( false ).set( "this", 0 );
		}

		int curArgPos = ( isStatic ? 0 : 1 );
		for ( ListIterator< JavaVariable > iter = arguments.iterator(); iter.hasNext(); ) {
			int index = iter.nextIndex();
			JavaVariable var = iter.next();
			
			String alias = "arg$" + index;
			String varName = var.getName();
			if ( varName != null ) {
				this.varScope( false ).set( varName, curArgPos );
				this.varScope( false ).alias( alias, varName );
			} else {
				this.varScope( false ).set( alias, curArgPos );
			}
			curArgPos += JvmCoreCodeWriterImpl.size( var.getType() );
		}
	}
	
	@Override
	protected final CodeWritingState sharedState() {
		return this.sharedState;
	}
	
	@Override
	public final JvmCoreCodeWriter coreWriter() {
		return this.coreWriter;
	}
}
