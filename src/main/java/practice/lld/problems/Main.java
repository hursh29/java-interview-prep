package practice.lld.problems;

import com.google.common.html.HtmlEscapers;

import java.util.HashMap;
import java.util.Map;

public class Main {
	private final static Map<String, String> fruitNameToValue = new HashMap<String, String>();

	public static void main(String... args) {
		fruitNameToValue.values().forEach(System.out::println);

		fruitNameToValue.put("Banana", "Bananas");
		System.out.println("Hello world!");
	}

	public static String useGuavaForSomeReason(String input) {
		return HtmlEscapers.htmlEscaper().escape(input);
	}
}
