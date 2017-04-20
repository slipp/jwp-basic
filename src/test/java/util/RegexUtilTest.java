package util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by johngrib on 2017. 4. 22..
 */
public class RegexUtilTest {


    @Test
    public void test_exec() {
        final String regex = "^(.+)\\/(.+)\\.(class)$";
        final String text = "src/test.class";

        final List<String> exec = RegexUtil.exec(regex, text).list();
        final List<String> expect = new ArrayList<>();
        expect.add(text);
        expect.add("src");
        expect.add("test");
        expect.add("class");

        assertThat(expect, is(exec));
    }
}