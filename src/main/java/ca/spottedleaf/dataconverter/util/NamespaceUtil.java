package ca.spottedleaf.dataconverter.util;

import org.apache.commons.lang3.StringUtils;

public final class NamespaceUtil {

    private NamespaceUtil() {}

    public static String correctNamespace(final String value) {
        if (value == null) {
            return null;
        }
        final String resourceLocation = tryParse(value);
        return resourceLocation != null ? resourceLocation : value;
    }

    public static String correctNamespaceOrNull(final String value) {
        if (value == null) {
            return null;
        }
        final String correct = correctNamespace(value);
        return correct.equals(value) ? null : correct;
    }

    private static String tryParse(final String value) {
        try {
            return construct(value);
        } catch (final IllegalArgumentException exception) {
            return null;
        }
    }

    private static String construct(final String text) {
        final String[] split = new String[]{"minecraft", text};
        int separatorIndex = text.indexOf(':');
        if (separatorIndex >= 0) {
            split[1] = text.substring(separatorIndex + 1);
            if (separatorIndex >= 1) {
                split[0] = text.substring(0, separatorIndex);
            }
        }
        final String namespace = StringUtils.isEmpty(split[0]) ? "minecraft" : split[0];
        final String path = split[1];
        if (!isValidNamespace(namespace)) {
            throw new IllegalArgumentException("Non [a-z0-9_.-] character in namespace of location: " + namespace + ":" + path);
        } else if (!isValidPath(path)) {
            throw new IllegalArgumentException("Non [a-z0-9/._-] character in path of location: " + namespace + ":" + path);
        }
        return namespace + ':' + path;
    }

    private static boolean isValidPath(String string) {
        for(int i = 0; i < string.length(); ++i) {
            if (!validPathChar(string.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    private static boolean isValidNamespace(String string) {
        for(int i = 0; i < string.length(); ++i) {
            if (!validNamespaceChar(string.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean validPathChar(char c) {
        return c == '_' || c == '-' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '/' || c == '.';
    }

    private static boolean validNamespaceChar(char c) {
        return c == '_' || c == '-' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '.';
    }
}
