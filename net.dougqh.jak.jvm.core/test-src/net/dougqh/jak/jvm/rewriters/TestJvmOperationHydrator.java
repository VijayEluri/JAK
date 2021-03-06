package net.dougqh.jak.jvm.rewriters;

import java.util.LinkedList;

import net.dougqh.jak.jvm.JvmOperationHydrator;
import net.dougqh.jak.jvm.operations.JvmOperation;

public final class TestJvmOperationHydrator extends JvmOperationHydrator {
	private final LinkedList<JvmOperation> opQueue = new LinkedList<JvmOperation>();
	
	@Override
	protected final void process(final JvmOperation operation) {
		this.opQueue.addLast(operation);
	}
	
	public final JvmOperation get() {
		return this.opQueue.removeFirst();
	}
}
