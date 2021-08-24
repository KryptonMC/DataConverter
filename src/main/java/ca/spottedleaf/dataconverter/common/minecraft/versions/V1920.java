package ca.spottedleaf.dataconverter.common.minecraft.versions;

import ca.spottedleaf.dataconverter.common.converters.DataConverter;
import ca.spottedleaf.dataconverter.common.minecraft.MCVersions;
import ca.spottedleaf.dataconverter.common.minecraft.datatypes.MCTypeRegistry;
import ca.spottedleaf.dataconverter.common.minecraft.walkers.itemstack.DataWalkerItemLists;
import ca.spottedleaf.dataconverter.common.types.MapType;
import ca.spottedleaf.dataconverter.common.util.NamespaceUtil;

public final class V1920 {

    protected static final int VERSION = MCVersions.V18W50A + 1;

    private V1920() {}

    public static void register() {
        MCTypeRegistry.CHUNK.addStructureConverter(new DataConverter<>(VERSION) {
            @Override
            public MapType<String> convert(final MapType<String> data, final long sourceVersion, final long toVersion) {
                final MapType<String> level = data.getMap("Level");
                if (level == null) {
                    return null;
                }

                final MapType<String> structures = level.getMap("Structures");
                if (structures == null) {
                    return null;
                }

                final MapType<String> starts = structures.getMap("Starts");
                if (starts != null) {
                    final MapType<String> village = starts.getMap("New_Village");
                    if (village != null) {
                        starts.remove("New_Village");
                        starts.setMap("Village", village);
                    } else {
                        starts.remove("Village");
                    }
                }

                final MapType<String> references = structures.getMap("References");
                if (references != null) {
                    final MapType<String> newVillage = references.getMap("New_Village");
                    // I believe Mojang had a typo here, removing Village from references only made sense
                    // if the new village didn't exist. DFU removes it whether or not it exists, but still relocates
                    // New_Village to Village first. It doesn't make sense to me to relocate it just to remove it, so it
                    // must be a typo.
                    if (newVillage == null) {
                        references.remove("Village");
                    } else {
                        references.remove("New_Village");
                        references.setMap("Village", newVillage);
                    }
                }

                return null;
            }
        });

        MCTypeRegistry.STRUCTURE_FEATURE.addStructureConverter(new DataConverter<>(VERSION) {
            @Override
            public MapType<String> convert(final MapType<String> data, final long sourceVersion, final long toVersion) {
                final String id = data.getString("id");

                if ("minecraft:new_village".equals(NamespaceUtil.correctNamespace(id))) {
                    data.setString("id", "minecraft:village");
                }

                return null;
            }
        });

        MCTypeRegistry.ENTITY.addWalker(VERSION, "minecraft:campfire", new DataWalkerItemLists("Items"));
    }
}
