package net.thisptr.jackson.jq.internal.functions;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.TextNode;

import net.thisptr.jackson.jq.Function;
import net.thisptr.jackson.jq.JsonQuery;
import net.thisptr.jackson.jq.Scope;
import net.thisptr.jackson.jq.exception.JsonQueryException;
import net.thisptr.jackson.jq.internal.BuiltinFunction;
import net.thisptr.jackson.jq.internal.misc.Preconditions;
import net.thisptr.jackson.jq.internal.misc.Strings;

@BuiltinFunction("split/1")
public class SplitFunction implements Function {
	@Override
	public List<JsonNode> apply(final Scope scope, final List<JsonQuery> args, final JsonNode in) throws JsonQueryException {
		Preconditions.checkInputType("split", in, JsonNodeType.STRING);

		final List<JsonNode> out = new ArrayList<>();
		for (final JsonNode sep : args.get(0).apply(scope, in)) {
			if (!sep.isTextual())
				throw new JsonQueryException("1st argument of split() must evaluate to string, not " + sep.getNodeType());

			final ArrayNode row = scope.getObjectMapper().createArrayNode();
			for (final String seg : split(in.asText(), sep.asText()))
				row.add(new TextNode(seg));
			out.add(row);
		}
		return out;
	}

	protected String[] split(final String in, final String sep) {
		final List<String> result;
		if (sep.isEmpty()) {
			result = new ArrayList<>();
			final int length = in.length();
			for (int offset = 0; offset < length;) {
				final int codepoint = in.codePointAt(offset);
				result.add(new String(Character.toChars(codepoint)));
				offset += Character.charCount(codepoint);
			}
		} else {
			return Strings.splitToArray(in, sep);
		}
		return result.toArray(new String[result.size()]);
	}
}
