package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.EMPTY_LIST;

public class RegexUtil {

    /**
     * javascript 의 exec 처럼 작동하는 메서드
     */
    static public ExecResult exec(final String regex, final String text) {
        final List<String> result = new ArrayList<>();
        final Matcher m = Pattern.compile(regex).matcher(text);

        if(!m.find()) {
            return new ExecResult(result);
        }

        final int cnt = m.groupCount();
        for (int i = 0; i <= cnt; i++) {
            result.add(m.group(i));
        }
        return new ExecResult(result);
    }

    static public class ExecResult {

        List<String> result = EMPTY_LIST;

        public ExecResult(final List<String> result) {
            this.result = result;
        }

        public String get(final int capture) {
            if(result.isEmpty() || result.size() <= capture) {
                return null;
            }
            return result.get(capture);
        }

        public int size() {
            return result.size();
        }

        public List<String> list() {
            final List<String> newList = new ArrayList<>(result.size());
            newList.addAll(result);
            return Collections.unmodifiableList(newList);
        }
    }
}

