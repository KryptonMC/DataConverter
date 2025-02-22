package ca.spottedleaf.dataconverter.types.nbt;

import ca.spottedleaf.dataconverter.types.ObjectType;
import ca.spottedleaf.dataconverter.types.ListType;
import ca.spottedleaf.dataconverter.types.MapType;
import ca.spottedleaf.dataconverter.types.TypeUtil;
import ca.spottedleaf.dataconverter.types.Types;
import java.util.ArrayList;
import org.kryptonmc.nbt.ByteArrayTag;
import org.kryptonmc.nbt.ByteTag;
import org.kryptonmc.nbt.CompoundTag;
import org.kryptonmc.nbt.DoubleTag;
import org.kryptonmc.nbt.FloatTag;
import org.kryptonmc.nbt.IntArrayTag;
import org.kryptonmc.nbt.IntTag;
import org.kryptonmc.nbt.ListTag;
import org.kryptonmc.nbt.LongArrayTag;
import org.kryptonmc.nbt.LongTag;
import org.kryptonmc.nbt.MutableListTag;
import org.kryptonmc.nbt.NumberTag;
import org.kryptonmc.nbt.ShortTag;
import org.kryptonmc.nbt.StringTag;
import org.kryptonmc.nbt.Tag;

public final class NBTListType implements ListType {

    private final ListTag list;

    public NBTListType() {
        this.list = MutableListTag.empty();
    }

    public NBTListType(final ListTag tag) {
        this.list = tag.asMutable();
    }

    @Override
    public TypeUtil getTypeUtil() {
        return Types.NBT;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != NBTListType.class) {
            return false;
        }

        return this.list.equals(((NBTListType)obj).list);
    }

    @Override
    public int hashCode() {
        return this.list.hashCode();
    }

    @Override
    public String toString() {
        return "NBTListType{" +
                "list=" + this.list +
                '}';
    }

    public ListTag getTag() {
        return this.list;
    }

    @Override
    public ListType copy() {
        return new NBTListType(this.list.copy());
    }

    protected static ObjectType getType(final byte id) {
        switch (id) {
            case 0: // END
                return ObjectType.NONE;
            case 1: // BYTE
                return ObjectType.BYTE;
            case 2: // SHORT
                return ObjectType.SHORT;
            case 3: // INT
                return ObjectType.INT;
            case 4: // LONG
                return ObjectType.LONG;
            case 5: // FLOAT
                return ObjectType.FLOAT;
            case 6: // DOUBLE
                return ObjectType.DOUBLE;
            case 7: // BYTE_ARRAY
                return ObjectType.BYTE_ARRAY;
            case 8: // STRING
                return ObjectType.STRING;
            case 9: // LIST
                return ObjectType.LIST;
            case 10: // COMPOUND
                return ObjectType.MAP;
            case 11: // INT_ARRAY
                return ObjectType.INT_ARRAY;
            case 12: // LONG_ARRAY
                return ObjectType.LONG_ARRAY;
            default:
                throw new IllegalStateException("Unknown type: " + id);
        }
    }

    @Override
    public ObjectType getType() {
        return getType((byte) this.list.elementType());
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public void remove(final int index) {
        this.list.remove(index);
    }

    @Override
    public Number getNumber(final int index) {
        final Tag tag = this.list.get(index); // does bound checking for us
        if (!(tag instanceof NumberTag)) {
            throw new IllegalStateException();
        }
        return ((NumberTag)tag).asNumber();
    }

    @Override
    public byte getByte(final int index) {
        final Tag tag = this.list.get(index); // does bound checking for us
        if (!(tag instanceof NumberTag)) {
            throw new IllegalStateException();
        }
        return ((NumberTag)tag).toByte();
    }

    @Override
    public void setByte(final int index, final byte to) {
        this.list.set(index, ByteTag.of(to));
    }

    @Override
    public short getShort(final int index) {
        final Tag tag = this.list.get(index); // does bound checking for us
        if (!(tag instanceof NumberTag)) {
            throw new IllegalStateException();
        }
        return ((NumberTag)tag).toShort();
    }

    @Override
    public void setShort(final int index, final short to) {
        this.list.set(index, ShortTag.of(to));
    }

    @Override
    public int getInt(final int index) {
        final Tag tag = this.list.get(index); // does bound checking for us
        if (!(tag instanceof NumberTag)) {
            throw new IllegalStateException();
        }
        return ((NumberTag)tag).toInt();
    }

    @Override
    public void setInt(final int index, final int to) {
        this.list.set(index, IntTag.of(to));
    }

    @Override
    public long getLong(final int index) {
        final Tag tag = this.list.get(index); // does bound checking for us
        if (!(tag instanceof NumberTag)) {
            throw new IllegalStateException();
        }
        return ((NumberTag)tag).toLong();
    }

    @Override
    public void setLong(final int index, final long to) {
        this.list.set(index, LongTag.of(to));
    }

    @Override
    public float getFloat(final int index) {
        final Tag tag = this.list.get(index); // does bound checking for us
        if (!(tag instanceof NumberTag)) {
            throw new IllegalStateException();
        }
        return ((NumberTag)tag).toFloat();
    }

    @Override
    public void setFloat(final int index, final float to) {
        this.list.set(index, FloatTag.of(to));
    }

    @Override
    public double getDouble(final int index) {
        final Tag tag = this.list.get(index); // does bound checking for us
        if (!(tag instanceof NumberTag)) {
            throw new IllegalStateException();
        }
        return ((NumberTag)tag).toDouble();
    }

    @Override
    public void setDouble(final int index, final double to) {
        this.list.set(index, DoubleTag.of(to));
    }

    @Override
    public byte[] getBytes(final int index) {
        final Tag tag = this.list.get(index); // does bound checking for us
        if (!(tag instanceof ByteArrayTag)) {
            throw new IllegalStateException();
        }
        return ((ByteArrayTag)tag).getData();
    }

    @Override
    public void setBytes(final int index, final byte[] to) {
        this.list.set(index, ByteArrayTag.of(to));
    }

    @Override
    public short[] getShorts(final int index) {
        // NBT does not support shorts
        throw new UnsupportedOperationException();
    }

    @Override
    public void setShorts(final int index, final short[] to) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int[] getInts(final int index) {
        final Tag tag = this.list.get(index); // does bound checking for us
        if (!(tag instanceof IntArrayTag)) {
            throw new IllegalStateException();
        }
        return ((IntArrayTag)tag).getData();
    }

    @Override
    public void setInts(final int index, final int[] to) {
        this.list.set(index, IntArrayTag.of(to));
    }

    @Override
    public long[] getLongs(final int index) {
        final Tag tag = this.list.get(index); // does bound checking for us
        if (!(tag instanceof LongArrayTag)) {
            throw new IllegalStateException();
        }
        return ((LongArrayTag)tag).getData();
    }

    @Override
    public void setLongs(final int index, final long[] to) {
        this.list.set(index, LongArrayTag.of(to));
    }

    @Override
    public ListType getList(final int index) {
        final Tag tag = this.list.get(index); // does bound checking for us
        if (!(tag instanceof ListTag)) {
            throw new IllegalStateException();
        }
        return new NBTListType((ListTag)tag);
    }

    @Override
    public void setList(final int index, final ListType list) {
        this.list.set(index, ((NBTListType)list).getTag());
    }

    @Override
    public MapType<String> getMap(final int index) {
        final Tag tag = this.list.get(index); // does bound checking for us
        if (!(tag instanceof CompoundTag)) {
            throw new IllegalStateException();
        }
        return new NBTMapType((CompoundTag)tag);
    }

    @Override
    public void setMap(final int index, final MapType<?> to) {
        this.list.set(index, ((NBTMapType)to).getTag());
    }

    @Override
    public String getString(final int index) {
        final Tag tag = this.list.get(index); // does bound checking for us
        if (!(tag instanceof StringTag)) {
            throw new IllegalStateException();
        }
        return ((StringTag)tag).value();
    }

    @Override
    public void setString(final int index, final String to) {
        this.list.set(index, StringTag.of(to));
    }

    @Override
    public void addByte(final byte b) {
        this.list.add(ByteTag.of(b));
    }

    @Override
    public void addByte(final int index, final byte b) {
        this.list.getData().add(index, ByteTag.of(b));
    }

    @Override
    public void addShort(final short s) {
        this.list.add(ShortTag.of(s));
    }

    @Override
    public void addShort(final int index, final short s) {
        this.list.getData().add(index, ShortTag.of(s));
    }

    @Override
    public void addInt(final int i) {
        this.list.add(IntTag.of(i));
    }

    @Override
    public void addInt(final int index, final int i) {
        this.list.getData().add(index, IntTag.of(i));
    }

    @Override
    public void addLong(final long l) {
        this.list.add(LongTag.of(l));
    }

    @Override
    public void addLong(final int index, final long l) {
        this.list.getData().add(index, LongTag.of(l));
    }

    @Override
    public void addFloat(final float f) {
        this.list.add(FloatTag.of(f));
    }

    @Override
    public void addFloat(final int index, final float f) {
        this.list.getData().add(index, FloatTag.of(f));
    }

    @Override
    public void addDouble(final double d) {
        this.list.add(DoubleTag.of(d));
    }

    @Override
    public void addDouble(final int index, final double d) {
        this.list.getData().add(index, DoubleTag.of(d));
    }

    @Override
    public void addByteArray(final byte[] arr) {
        this.list.add(ByteArrayTag.of(arr));
    }

    @Override
    public void addByteArray(final int index, final byte[] arr) {
        this.list.getData().add(index, ByteArrayTag.of(arr));
    }

    @Override
    public void addShortArray(final short[] arr) {
        // NBT does not support short[]
        throw new UnsupportedOperationException();
    }

    @Override
    public void addShortArray(final int index, final short[] arr) {
        // NBT does not support short[]
        throw new UnsupportedOperationException();
    }

    @Override
    public void addIntArray(final int[] arr) {
        this.list.add(IntArrayTag.of(arr));
    }

    @Override
    public void addIntArray(final int index, final int[] arr) {
        this.list.getData().add(index, IntArrayTag.of(arr));
    }

    @Override
    public void addLongArray(final long[] arr) {
        this.list.add(LongArrayTag.of(arr));
    }

    @Override
    public void addLongArray(final int index, final long[] arr) {
        this.list.getData().add(index, LongArrayTag.of(arr));
    }

    @Override
    public void addList(final ListType list) {
        this.list.add(((NBTListType)list).getTag());
    }

    @Override
    public void addList(final int index, final ListType list) {
        this.list.getData().add(index, ((NBTListType)list).getTag());
    }

    @Override
    public void addMap(final MapType<?> map) {
        this.list.add(((NBTMapType)map).getTag());
    }

    @Override
    public void addMap(final int index, final MapType<?> map) {
        this.list.getData().add(index, ((NBTMapType)map).getTag());
    }

    @Override
    public void addString(final String string) {
        this.list.add(StringTag.of(string));
    }

    @Override
    public void addString(final int index, final String string) {
        this.list.getData().add(index, StringTag.of(string));
    }
}
