package net.thisptr.jackson.jq.internal.tree.binaryop;

import net.thisptr.jackson.jq.JsonQuery;
import net.thisptr.jackson.jq.internal.operators.ModuloOperator;

public class ModuloExpression extends SimpleBinaryOperatorExpression {
	public ModuloExpression(final JsonQuery lhs, final JsonQuery rhs) {
		super(lhs, rhs, new ModuloOperator());
	}
}
