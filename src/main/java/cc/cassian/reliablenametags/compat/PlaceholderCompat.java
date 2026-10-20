package cc.cassian.reliablenametags.compat;

import eu.pb4.placeholders.api.ParserContext;
import eu.pb4.placeholders.api.parsers.NodeParser;
import net.minecraft.network.chat.Component;

public class PlaceholderCompat {
	public static final NodeParser PARSER = NodeParser.builder().quickText().build();

	public static Component parse(String customName) {
		//? if >26 {
		return PARSER.parseComponent(customName, ParserContext.of());
		//?} else {
		/*return PARSER.parseText(customName, ParserContext.of());
		*///?}
	}
}
