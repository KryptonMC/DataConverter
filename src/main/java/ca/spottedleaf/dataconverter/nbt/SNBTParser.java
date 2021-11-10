package ca.spottedleaf.dataconverter.nbt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.kryptonmc.nbt.ByteArrayTag;
import org.kryptonmc.nbt.ByteTag;
import org.kryptonmc.nbt.DoubleTag;
import org.kryptonmc.nbt.FloatTag;
import org.kryptonmc.nbt.IntArrayTag;
import org.kryptonmc.nbt.IntTag;
import org.kryptonmc.nbt.ListTag;
import org.kryptonmc.nbt.LongArrayTag;
import org.kryptonmc.nbt.LongTag;
import org.kryptonmc.nbt.NumberTag;
import org.kryptonmc.nbt.ShortTag;
import org.kryptonmc.nbt.CompoundTag;
import org.kryptonmc.nbt.StringTag;
import org.kryptonmc.nbt.Tag;
import org.kryptonmc.nbt.TagType;

public final class SNBTParser {
    public static final char ELEMENT_SEPARATOR = ',';
    public static final char NAME_VALUE_SEPARATOR = ':';
    private static final char LIST_OPEN = '[';
    private static final char LIST_CLOSE = ']';
    private static final char STRUCT_CLOSE = '}';
    private static final char STRUCT_OPEN = '{';
    private static final Pattern DOUBLE_PATTERN_NOSUFFIX = Pattern.compile("[-+]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?", 2);
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", 2);
    private static final Pattern FLOAT_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?f", 2);
    private static final Pattern BYTE_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)b", 2);
    private static final Pattern LONG_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)l", 2);
    private static final Pattern SHORT_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)s", 2);
    private static final Pattern INT_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)");
    private final StringReader reader;

    public static CompoundTag parseTag(String string) throws IOException {
        return (new SNBTParser(new StringReader(string))).readSingleStruct();
    }

    CompoundTag readSingleStruct() throws IOException {
        CompoundTag compoundTag = this.readStruct();
        this.reader.skipWhitespace();
        if (this.reader.canRead()) {
            throw new IOException("Found trailing data " + reader.getString());
        } else {
            return compoundTag;
        }
    }

    public SNBTParser(final StringReader stringReader) {
        this.reader = stringReader;
    }

    protected String readKey() throws IOException {
        this.reader.skipWhitespace();
        if (!this.reader.canRead()) {
            throw new IOException("Expected key at " + reader.getCursor());
        } else {
            return this.reader.readString();
        }
    }

    protected Tag readTypedValue() throws IOException {
        this.reader.skipWhitespace();
        int i = this.reader.getCursor();
        if (StringReader.isQuotedStringStart(this.reader.peek())) {
            return StringTag.of(this.reader.readQuotedString());
        } else {
            String string = this.reader.readUnquotedString();
            if (string.isEmpty()) {
                this.reader.setCursor(i);
                throw new IOException("Expected value at " + reader.getCursor());
            } else {
                return this.type(string);
            }
        }
    }

    private Tag type(String string) {
        try {
            if (FLOAT_PATTERN.matcher(string).matches()) {
                return FloatTag.of(Float.parseFloat(string.substring(0, string.length() - 1)));
            }

            if (BYTE_PATTERN.matcher(string).matches()) {
                return ByteTag.of(Byte.parseByte(string.substring(0, string.length() - 1)));
            }

            if (LONG_PATTERN.matcher(string).matches()) {
                return LongTag.of(Long.parseLong(string.substring(0, string.length() - 1)));
            }

            if (SHORT_PATTERN.matcher(string).matches()) {
                return ShortTag.of(Short.parseShort(string.substring(0, string.length() - 1)));
            }

            if (INT_PATTERN.matcher(string).matches()) {
                return IntTag.of(Integer.parseInt(string));
            }

            if (DOUBLE_PATTERN.matcher(string).matches()) {
                return DoubleTag.of(Double.parseDouble(string.substring(0, string.length() - 1)));
            }

            if (DOUBLE_PATTERN_NOSUFFIX.matcher(string).matches()) {
                return DoubleTag.of(Double.parseDouble(string));
            }

            if ("true".equalsIgnoreCase(string)) {
                return ByteTag.ONE;
            }

            if ("false".equalsIgnoreCase(string)) {
                return ByteTag.ZERO;
            }
        } catch (NumberFormatException var3) {
        }

        return StringTag.of(string);
    }

    public Tag readValue() throws IOException {
        this.reader.skipWhitespace();
        if (!this.reader.canRead()) {
            throw new IOException("Expected value at " + reader.getCursor());
        } else {
            char c = this.reader.peek();
            if (c == '{') {
                return this.readStruct();
            } else {
                return c == '[' ? this.readList() : this.readTypedValue();
            }
        }
    }

    protected Tag readList() throws IOException {
        return this.reader.canRead(3) && !StringReader.isQuotedStringStart(this.reader.peek(1)) && this.reader.peek(2) == ';' ? this.readArrayTag() : this.readListTag();
    }

    public CompoundTag readStruct() throws IOException {
        this.expect('{');
        final CompoundTag.Builder builder = CompoundTag.builder();
        this.reader.skipWhitespace();

        while(this.reader.canRead() && this.reader.peek() != '}') {
            int i = this.reader.getCursor();
            String string = this.readKey();
            if (string.isEmpty()) {
                this.reader.setCursor(i);
                throw new IOException("Expected key at " + reader.getCursor());
            }

            this.expect(':');
            builder.put(string, readValue());
            if (!this.hasElementSeparator()) {
                break;
            }
        }

        this.expect('}');
        return builder.build();
    }

    private Tag readListTag() throws IOException {
        this.expect('[');
        this.reader.skipWhitespace();
        if (!this.reader.canRead()) {
            throw new IOException("Expected value at " + reader.getCursor());
        } else {
            final ListTag.Builder builder = ListTag.builder();
            TagType tagType = null;

            while(this.reader.peek() != ']') {
                int i = this.reader.getCursor();
                Tag tag = this.readValue();
                TagType tagType2 = tag.getType();
                if (tagType == null) {
                    tagType = tagType2;
                } else if (tagType2 != tagType) {
                    this.reader.setCursor(i);
                    throw new IOException("Mixed list detected!");
                }

                builder.add(tag);
                if (!this.hasElementSeparator()) {
                    break;
                }
            }

            this.expect(']');
            return builder.build();
        }
    }

    private Tag readArrayTag() throws IOException {
        this.expect('[');
        int i = this.reader.getCursor();
        char c = this.reader.read();
        this.reader.read();
        this.reader.skipWhitespace();
        if (!this.reader.canRead()) {
            throw new IOException("Expected value at " + reader.getCursor());
        } else if (c == 'B') {
            return new ByteArrayTag(this.readArray(ByteArrayTag.TYPE, ByteTag.TYPE));
        } else if (c == 'L') {
            return new LongArrayTag(this.readArray(LongArrayTag.TYPE, LongTag.TYPE));
        } else if (c == 'I') {
            return new IntArrayTag(this.readArray(IntArrayTag.TYPE, IntTag.TYPE));
        } else {
            this.reader.setCursor(i);
            throw new IOException("Invalid array!");
        }
    }

    private <T extends Number> List<T> readArray(TagType tagType, TagType tagType2) throws IOException {
        ArrayList<T> list = new ArrayList<>();

        while(true) {
            if (this.reader.peek() != ']') {
                int i = this.reader.getCursor();
                Tag tag = this.readValue();
                TagType tagType3 = tag.getType();
                if (tagType3 != tagType2) {
                    this.reader.setCursor(i);
                    throw new IOException("Mixed array detected!");
                }

                if (tagType2 == ByteTag.TYPE) {
                    list.add((T) Byte.valueOf(((NumberTag)tag).toByte()));
                } else if (tagType2 == LongTag.TYPE) {
                    list.add((T) Long.valueOf(((NumberTag)tag).toLong()));
                } else {
                    list.add((T) Integer.valueOf(((NumberTag)tag).toInt()));
                }

                if (this.hasElementSeparator()) {
                    if (!this.reader.canRead()) {
                        throw new IOException("Expected value at " + reader.getCursor());
                    }
                    continue;
                }
            }

            this.expect(']');
            return list;
        }
    }

    private boolean hasElementSeparator() {
        this.reader.skipWhitespace();
        if (this.reader.canRead() && this.reader.peek() == ',') {
            this.reader.skip();
            this.reader.skipWhitespace();
            return true;
        } else {
            return false;
        }
    }

    private void expect(char c) throws IOException {
        this.reader.skipWhitespace();
        this.reader.expect(c);
    }
}
