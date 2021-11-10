package ca.spottedleaf.dataconverter.minecraft.versions;

import ca.spottedleaf.dataconverter.converters.DataConverter;
import ca.spottedleaf.dataconverter.minecraft.MCVersions;
import ca.spottedleaf.dataconverter.minecraft.datatypes.MCTypeRegistry;
import ca.spottedleaf.dataconverter.types.MapType;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

public final class V101 {

    protected static final int VERSION = MCVersions.V15W32A + 1;
    public static final Gson GSON = GsonComponentSerializer.gson().serializer().newBuilder().setLenient().create();

    protected static void updateLine(final MapType<String> data, final String path) {
        final String textString = data.getString(path);
        if (textString == null || textString.isEmpty() || "null".equals(textString)) {
            data.setString(path, GsonComponentSerializer.gson().serialize(Component.empty()));
            return;
        }

        Component component;
        if (textString.charAt(0) == '"' && textString.charAt(textString.length() - 1) == '"'
                || textString.charAt(0) == '{' && textString.charAt(textString.length() - 1) == '}') {
            try {
                component = GSON.fromJson(textString, Component.class);
            } catch (final JsonParseException exception) {
                component = Component.empty();
            }
        } else {
            component = Component.text(textString);
        }
        data.setString(path, GSON.toJson(component));
    }

    public static void register() {
        MCTypeRegistry.TILE_ENTITY.addConverterForId("Sign", new DataConverter<>(VERSION) {

            @Override
            public MapType<String> convert(final MapType<String> data, final long sourceVersion, final long toVersion) {
                updateLine(data, "Text1");
                updateLine(data, "Text2");
                updateLine(data, "Text3");
                updateLine(data, "Text4");
                return null;
            }
        });
    }

    private V101() {}

}
