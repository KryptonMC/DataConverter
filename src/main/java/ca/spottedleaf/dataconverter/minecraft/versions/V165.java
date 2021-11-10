package ca.spottedleaf.dataconverter.minecraft.versions;

import ca.spottedleaf.dataconverter.converters.DataConverter;
import ca.spottedleaf.dataconverter.minecraft.MCVersions;
import ca.spottedleaf.dataconverter.minecraft.datatypes.MCTypeRegistry;
import ca.spottedleaf.dataconverter.types.ObjectType;
import ca.spottedleaf.dataconverter.types.ListType;
import ca.spottedleaf.dataconverter.types.MapType;
import com.google.gson.JsonParseException;
import net.kyori.adventure.text.Component;

public final class V165 {

    protected static final int VERSION = MCVersions.V1_9_PRE2;

    public static void register() {
        MCTypeRegistry.ITEM_STACK.addStructureConverter(new DataConverter<>(VERSION) {
            @Override
            public MapType<String> convert(final MapType<String> data, final long sourceVersion, final long toVersion) {
                final MapType<String> tag = data.getMap("tag");
                if (tag == null) {
                    return null;
                }

                final ListType pages = tag.getList("pages", ObjectType.STRING);
                if (pages == null) {
                    return null;
                }

                for (int i = 0, len = pages.size(); i < len; ++i) {
                    final String page = pages.getString(i);
                    Component component;
                    if (page != "null" && !page.isEmpty()) {
                        if (page.charAt(0) == '"' && page.charAt(page.length() - 1) == '"') {
                            try {
                                component = V101.GSON.fromJson(page, Component.class);
                            } catch (final JsonParseException exception) {
                                component = Component.empty();
                            }
                        } else {
                            component = Component.empty();
                        }
                    } else {
                        component = Component.empty();
                    }
                    pages.setString(i, V101.GSON.toJson(component));
                }

                return null;
            }
        });
    }

    private V165() {}

}
