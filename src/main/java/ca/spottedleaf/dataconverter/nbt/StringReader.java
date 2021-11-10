package ca.spottedleaf.dataconverter.nbt;

import java.io.IOException;

public final class StringReader {
    private static final char SYNTAX_ESCAPE = '\\';
    private static final char SYNTAX_DOUBLE_QUOTE = '"';
    private static final char SYNTAX_SINGLE_QUOTE = '\'';

    private final String string;
    private int cursor;

    public StringReader(final StringReader other) {
        this.string = other.string;
        this.cursor = other.cursor;
    }

    public StringReader(final String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setCursor(final int cursor) {
        this.cursor = cursor;
    }

    public int getRemainingLength() {
        return string.length() - cursor;
    }

    public int getTotalLength() {
        return string.length();
    }

    public int getCursor() {
        return cursor;
    }

    public String getRead() {
        return string.substring(0, cursor);
    }

    public String getRemaining() {
        return string.substring(cursor);
    }

    public boolean canRead(final int length) {
        return cursor + length <= string.length();
    }

    public boolean canRead() {
        return canRead(1);
    }

    public char peek() {
        return string.charAt(cursor);
    }

    public char peek(final int offset) {
        return string.charAt(cursor + offset);
    }

    public char read() {
        return string.charAt(cursor++);
    }

    public void skip() {
        cursor++;
    }

    public static boolean isAllowedNumber(final char c) {
        return c >= '0' && c <= '9' || c == '.' || c == '-';
    }

    public static boolean isQuotedStringStart(char c) {
        return c == SYNTAX_DOUBLE_QUOTE || c == SYNTAX_SINGLE_QUOTE;
    }

    public void skipWhitespace() {
        while (canRead() && Character.isWhitespace(peek())) {
            skip();
        }
    }

    public int readInt() throws IOException {
        final int start = cursor;
        while (canRead() && isAllowedNumber(peek())) {
            skip();
        }
        final String number = string.substring(start, cursor);
        if (number.isEmpty()) {
            throw new IOException("Expected integer at " + cursor);
        }
        try {
            return Integer.parseInt(number);
        } catch (final NumberFormatException ex) {
            cursor = start;
            throw new IOException("Could not parse " + number + " to an integer at " + cursor);
        }
    }

    public long readLong() throws IOException {
        final int start = cursor;
        while (canRead() && isAllowedNumber(peek())) {
            skip();
        }
        final String number = string.substring(start, cursor);
        if (number.isEmpty()) {
            throw new IOException("Expected long at " + cursor);
        }
        try {
            return Long.parseLong(number);
        } catch (final NumberFormatException ex) {
            cursor = start;
            throw new IOException("Could not parse " + number + " to a long at " + cursor);
        }
    }

    public double readDouble() throws IOException {
        final int start = cursor;
        while (canRead() && isAllowedNumber(peek())) {
            skip();
        }
        final String number = string.substring(start, cursor);
        if (number.isEmpty()) {
            throw new IOException("Expected double at " + cursor);
        }
        try {
            return Double.parseDouble(number);
        } catch (final NumberFormatException ex) {
            cursor = start;
            throw new IOException("Could not parse " + number + " to a long at " + cursor);
        }
    }

    public float readFloat() throws IOException {
        final int start = cursor;
        while (canRead() && isAllowedNumber(peek())) {
            skip();
        }
        final String number = string.substring(start, cursor);
        if (number.isEmpty()) {
            throw new IOException("Expected float at " + cursor);
        }
        try {
            return Float.parseFloat(number);
        } catch (final NumberFormatException ex) {
            cursor = start;
            throw new IOException("Could not parse " + number + " to a float at " + cursor);
        }
    }

    public static boolean isAllowedInUnquotedString(final char c) {
        return c >= '0' && c <= '9'
                || c >= 'A' && c <= 'Z'
                || c >= 'a' && c <= 'z'
                || c == '_' || c == '-'
                || c == '.' || c == '+';
    }

    public String readUnquotedString() {
        final int start = cursor;
        while (canRead() && isAllowedInUnquotedString(peek())) {
            skip();
        }
        return string.substring(start, cursor);
    }

    public String readQuotedString() throws IOException {
        if (!canRead()) {
            return "";
        }
        final char next = peek();
        if (!isQuotedStringStart(next)) {
            throw new IOException("Expected start quote at " + cursor + "!");
        }
        skip();
        return readStringUntil(next);
    }

    public String readStringUntil(char terminator) throws IOException {
        final StringBuilder result = new StringBuilder();
        boolean escaped = false;
        while (canRead()) {
            final char c = read();
            if (escaped) {
                if (c == terminator || c == SYNTAX_ESCAPE) {
                    result.append(c);
                    escaped = false;
                } else {
                    setCursor(getCursor() - 1);
                    throw new IOException("Invalid escape! At " + cursor);
                }
            } else if (c == SYNTAX_ESCAPE) {
                escaped = true;
            } else if (c == terminator) {
                return result.toString();
            } else {
                result.append(c);
            }
        }

        throw new IOException("Expected end quote at " + cursor + "!");
    }

    public String readString() throws IOException {
        if (!canRead()) {
            return "";
        }
        final char next = peek();
        if (isQuotedStringStart(next)) {
            skip();
            return readStringUntil(next);
        }
        return readUnquotedString();
    }

    public boolean readBoolean() throws IOException {
        final int start = cursor;
        final String value = readString();
        if (value.isEmpty()) {
            throw new IOException("Expected boolean at " + cursor + ", was empty!");
        }

        if (value.equals("true")) {
            return true;
        } else if (value.equals("false")) {
            return false;
        } else {
            cursor = start;
            throw new IOException("Could not parse " + value + " to a boolean at " + cursor + "!");
        }
    }

    public void expect(final char c) throws IOException {
        if (!canRead() || peek() != c) {
            throw new IOException("Expected symbol at " + cursor + ", got " + peek() + "!");
        }
        skip();
    }
}
