package ca.spottedleaf.dataconverter.types.nbt;

import ca.spottedleaf.dataconverter.types.ListType;
import ca.spottedleaf.dataconverter.types.MapType;
import ca.spottedleaf.dataconverter.types.ObjectType;
import ca.spottedleaf.dataconverter.types.TypeUtil;
import ca.spottedleaf.dataconverter.types.Types;
import java.util.HashMap;
import java.util.Set;
import org.kryptonmc.nbt.ByteArrayTag;
import org.kryptonmc.nbt.CompoundTag;
import org.kryptonmc.nbt.IntArrayTag;
import org.kryptonmc.nbt.ListTag;
import org.kryptonmc.nbt.LongArrayTag;
import org.kryptonmc.nbt.MutableCompoundTag;
import org.kryptonmc.nbt.NumberTag;
import org.kryptonmc.nbt.StringTag;
import org.kryptonmc.nbt.Tag;

public final class NBTMapType implements MapType<String> {

    private final CompoundTag map;

    public NBTMapType() {
        this.map = CompoundTag.mutable(new HashMap<>());
    }

    public NBTMapType(final CompoundTag tag) {
        this.map = tag instanceof MutableCompoundTag ? tag : tag.mutable();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != NBTMapType.class) {
            return false;
        }

        return this.map.equals(((NBTMapType)obj).map);
    }

    @Override
    public TypeUtil getTypeUtil() {
        return Types.NBT;
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }

    @Override
    public String toString() {
        return "NBTMapType{" +
                "map=" + this.map +
                '}';
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public Set<String> keys() {
        return this.map.keySet();
    }

    public CompoundTag getTag() {
        return this.map;
    }

    @Override
    public MapType<String> copy() {
        return new NBTMapType((CompoundTag) this.map.copy());
    }

    @Override
    public boolean hasKey(final String key) {
        return this.map.get(key) != null;
    }

    @Override
    public boolean hasKey(final String key, final ObjectType type) {
        final Tag tag = this.map.get(key);
        if (tag == null) {
            return false;
        }

        final ObjectType valueType = NBTListType.getType((byte) tag.getId());

        return valueType == type || (type == ObjectType.NUMBER && valueType.isNumber());
    }

    @Override
    public void remove(final String key) {
        this.map.remove(key);
    }

    @Override
    public Object getGeneric(final String key) {
        final Tag tag = this.map.get(key);
        if (tag == null) {
            return null;
        }

        switch (NBTListType.getType((byte) tag.getId())) {
            case BYTE:
            case SHORT:
            case INT:
            case LONG:
            case FLOAT:
            case DOUBLE:
                return ((NumberTag)tag).getValue();
            case MAP:
                return new NBTMapType((CompoundTag)tag);
            case LIST:
                return new NBTListType((ListTag)tag);
            case STRING:
                return ((StringTag)tag).getValue();
            case BYTE_ARRAY:
                return ((ByteArrayTag)tag).getData();
            // Note: No short array tag!
            case INT_ARRAY:
                return ((IntArrayTag)tag).getData();
            case LONG_ARRAY:
                return ((LongArrayTag)tag).getData();
        }

        throw new IllegalStateException("Unrecognized type " + tag);
    }

    @Override
    public Number getNumber(final String key) {
        return this.getNumber(key, null);
    }

    @Override
    public Number getNumber(final String key, final Number dfl) {
        final Tag tag = this.map.get(key);
        if (tag instanceof NumberTag) {
            return ((NumberTag)tag).getValue();
        }
        return dfl;
    }

    @Override
    public boolean getBoolean(final String key) {
        return this.getByte(key) != 0;
    }

    @Override
    public boolean getBoolean(final String key, final boolean dfl) {
        return this.getByte(key, dfl ? (byte)1 : (byte)0) != 0;
    }

    @Override
    public void setBoolean(final String key, final boolean val) {
        this.setByte(key, val ? (byte)1 : (byte)0);
    }

    @Override
    public byte getByte(final String key) {
        final Tag tag = this.map.get(key);
        if (tag instanceof NumberTag) {
            return ((NumberTag)tag).toByte();
        }
        return 0;
    }

    @Override
    public byte getByte(final String key, final byte dfl) {
        final Tag tag = this.map.get(key);
        if (tag instanceof NumberTag) {
            return ((NumberTag)tag).toByte();
        }
        return dfl;
    }

    @Override
    public void setByte(final String key, final byte val) {
        this.map.putByte(key, val);
    }

    @Override
    public short getShort(final String key) {
        final Tag tag = this.map.get(key);
        if (tag instanceof NumberTag) {
            return ((NumberTag)tag).toShort();
        }
        return 0;
    }

    @Override
    public short getShort(final String key, final short dfl) {
        final Tag tag = this.map.get(key);
        if (tag instanceof NumberTag) {
            return ((NumberTag)tag).toShort();
        }
        return dfl;
    }

    @Override
    public void setShort(final String key, final short val) {
        this.map.putShort(key, val);
    }

    @Override
    public int getInt(final String key) {
        final Tag tag = this.map.get(key);
        if (tag instanceof NumberTag) {
            return ((NumberTag)tag).toInt();
        }
        return 0;
    }

    @Override
    public int getInt(final String key, final int dfl) {
        final Tag tag = this.map.get(key);
        if (tag instanceof NumberTag) {
            return ((NumberTag)tag).toInt();
        }
        return dfl;
    }

    @Override
    public void setInt(final String key, final int val) {
        this.map.putInt(key, val);
    }

    @Override
    public long getLong(final String key) {
        final Tag tag = this.map.get(key);
        if (tag instanceof NumberTag) {
            return ((NumberTag)tag).toLong();
        }
        return 0;
    }

    @Override
    public long getLong(final String key, final long dfl) {
        final Tag tag = this.map.get(key);
        if (tag instanceof NumberTag) {
            return ((NumberTag)tag).toLong();
        }
        return dfl;
    }

    @Override
    public void setLong(final String key, final long val) {
        this.map.putLong(key, val);
    }

    @Override
    public float getFloat(final String key) {
        final Tag tag = this.map.get(key);
        if (tag instanceof NumberTag) {
            return ((NumberTag)tag).toFloat();
        }
        return 0;
    }

    @Override
    public float getFloat(final String key, final float dfl) {
        final Tag tag = this.map.get(key);
        if (tag instanceof NumberTag) {
            return ((NumberTag)tag).toFloat();
        }
        return dfl;
    }

    @Override
    public void setFloat(final String key, final float val) {
        this.map.putFloat(key, val);
    }

    @Override
    public double getDouble(final String key) {
        final Tag tag = this.map.get(key);
        if (tag instanceof NumberTag) {
            return ((NumberTag)tag).toDouble();
        }
        return 0;
    }

    @Override
    public double getDouble(final String key, final double dfl) {
        final Tag tag = this.map.get(key);
        if (tag instanceof NumberTag) {
            return ((NumberTag)tag).toDouble();
        }
        return dfl;
    }

    @Override
    public void setDouble(final String key, final double val) {
        this.map.putDouble(key, val);
    }

    @Override
    public byte[] getBytes(final String key) {
        return this.getBytes(key, null);
    }

    @Override
    public byte[] getBytes(final String key, final byte[] dfl) {
        final Tag tag = this.map.get(key);
        if (tag instanceof ByteArrayTag) {
            return ((ByteArrayTag)tag).getData();
        }
        return dfl;
    }

    @Override
    public void setBytes(final String key, final byte[] val) {
        this.map.putByteArray(key, val);
    }

    @Override
    public short[] getShorts(final String key) {
        return this.getShorts(key, null);
    }

    @Override
    public short[] getShorts(final String key, final short[] dfl) {
        // NBT does not support short array
        return dfl;
    }

    @Override
    public void setShorts(final String key, final short[] val) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int[] getInts(final String key) {
        return this.getInts(key, null);
    }

    @Override
    public int[] getInts(final String key, final int[] dfl) {
        final Tag tag = this.map.get(key);
        if (tag instanceof IntArrayTag) {
            return ((IntArrayTag)tag).getData();
        }
        return dfl;
    }

    @Override
    public void setInts(final String key, final int[] val) {
        this.map.putIntArray(key, val);
    }

    @Override
    public long[] getLongs(final String key) {
        return this.getLongs(key, null);
    }

    @Override
    public long[] getLongs(final String key, final long[] dfl) {
        final Tag tag = this.map.get(key);
        if (tag instanceof LongArrayTag) {
            return ((LongArrayTag)tag).getData();
        }
        return dfl;
    }

    @Override
    public void setLongs(final String key, final long[] val) {
        this.map.putLongArray(key, val);
    }

    @Override
    public ListType getListUnchecked(final String key) {
        return this.getListUnchecked(key, null);
    }

    @Override
    public ListType getListUnchecked(final String key, final ListType dfl) {
        final Tag tag = this.map.get(key);
        if (tag instanceof ListTag) {
            return new NBTListType((ListTag)tag);
        }
        return dfl;
    }

    @Override
    public void setList(final String key, final ListType val) {
        this.map.put(key, ((NBTListType)val).getTag());
    }

    @Override
    public MapType<String> getMap(final String key) {
        return this.getMap(key, null);
    }

    @Override
    public MapType<String> getMap(final String key, final MapType dfl) {
        final Tag tag = this.map.get(key);
        if (tag instanceof CompoundTag) {
            return new NBTMapType((CompoundTag)tag);
        }
        return dfl;
    }

    @Override
    public void setMap(final String key, final MapType<?> val) {
        this.map.put(key, ((NBTMapType)val).getTag());
    }

    @Override
    public String getString(final String key) {
        return this.getString(key, null);
    }

    @Override
    public String getString(final String key, final String dfl) {
        final Tag tag = this.map.get(key);
        if (tag instanceof StringTag) {
            return ((StringTag)tag).getValue();
        }
        return dfl;
    }

    @Override
    public void setString(final String key, final String val) {
        this.map.putString(key, val);
    }
}
