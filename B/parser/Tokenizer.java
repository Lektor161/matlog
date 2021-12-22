package parser;

public class Tokenizer {
    private final String s;
    private int pos = 0;
    private int savePos = 0;

    public Tokenizer(String s) {
        this.s = s;
    }

    public boolean isEnd() {
        return pos == s.length();
    }

    public void save() {
        savePos = pos;
    }

    public void comeBack() {
        pos = savePos;
    }

    public boolean test(String test) {
        if (pos + test.length() >= s.length()) {
            return false;
        }
        for (int i = 0; i < test.length(); i++) {
            if (s.charAt(pos + i) != test.charAt(i)) {
                return false;
            }
        }
        pos += test.length();
        return true;
    }

    public String getVal() {
        if (isEnd()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (s.charAt(pos) >= 'A' && s.charAt(pos) <= 'Z') {
            sb.append(s.charAt(pos++));
        } else {
            return "";
        }
        if (!isEnd()) {
            char c = s.charAt(pos);
            while (!isEnd() && (c >= 'A' && c <= 'Z' || Character.isDigit(c) || c == '\'')) {
                sb.append(c);
                pos++;
                if (isEnd()) {
                    break;
                }
                c = s.charAt(pos);
            }
        }
        return sb.toString();
    }
}
