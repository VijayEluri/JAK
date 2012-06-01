package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

public abstract class BinaryOperation implements JvmOperation {
	public abstract Type lhsType();
	
	public abstract Type rhsType();
	
	public abstract Type resultType();
	
	public abstract <T> T fold(final Object lhs, final Object rhs);
	
	@Override
	public final boolean isPolymorphic() {
		return false;
	}
	
	@Override
	public final Type[] getCodeOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Type[] getStackOperandTypes() {
		return new Type[] { this.lhsType(), this.rhsType() };
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		return new Type[] { this.resultType() };
	}
	
	protected static final <T> T as(final Object value) {
		@SuppressWarnings("unchecked")
		T castedValue = (T)value;
		return castedValue;
	}
}