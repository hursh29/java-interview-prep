package practice.lld.problems;

import org.junit.Assert;
import org.junit.Test;

public class MainTest {
	@Test
	public void testUseGuavaForSomeReason() throws Exception {
		String actual = Main.useGuavaForSomeReason("hello!<a href>");
		Assert.assertEquals("uh oh, the tests failed!", "hello!&lt;a href&gt;", actual);
	}
}