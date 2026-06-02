package cc.cassian.reliablenametags.compat;

//? if fabric || >26.1 {
import eu.pb4.placeholders.api.ParserContext;
import eu.pb4.placeholders.api.parsers.NodeParser;
//?}
import net.minecraft.network.chat.Component;

public class PlaceholderCompat {
	//? fabric || >26.1 {
	public static final NodeParser PARSER = NodeParser.builder().quickText().build();

	public static Component parse(String customName) {
		return PARSER.parseComponent(customName, ParserContext.of());
	}
	//?}
}
