package ca.spottedleaf.dataconverter.minecraft.versions;

import ca.spottedleaf.dataconverter.converters.DataConverter;
import ca.spottedleaf.dataconverter.converters.datatypes.DataHook;
import ca.spottedleaf.dataconverter.minecraft.MCVersions;
import ca.spottedleaf.dataconverter.minecraft.converters.chunk.ConverterFlattenChunk;
import ca.spottedleaf.dataconverter.minecraft.converters.helpers.HelperBlockFlatteningV1450;
import ca.spottedleaf.dataconverter.minecraft.converters.helpers.HelperItemNameV102;
import ca.spottedleaf.dataconverter.minecraft.converters.itemstack.ConverterFlattenItemStack;
import ca.spottedleaf.dataconverter.minecraft.converters.itemstack.ConverterFlattenSpawnEgg;
import ca.spottedleaf.dataconverter.minecraft.converters.stats.ConverterFlattenStats;
import ca.spottedleaf.dataconverter.minecraft.converters.entity.ConverterFlattenEntity;
import ca.spottedleaf.dataconverter.minecraft.datatypes.MCTypeRegistry;
import ca.spottedleaf.dataconverter.minecraft.walkers.generic.DataWalkerTypePaths;
import ca.spottedleaf.dataconverter.minecraft.walkers.itemstack.DataWalkerItemLists;
import ca.spottedleaf.dataconverter.minecraft.walkers.generic.WalkerUtils;
import ca.spottedleaf.dataconverter.minecraft.walkers.itemstack.DataWalkerItems;
import ca.spottedleaf.dataconverter.minecraft.walkers.tile_entity.DataWalkerTileEntities;
import ca.spottedleaf.dataconverter.types.ListType;
import ca.spottedleaf.dataconverter.types.MapType;
import ca.spottedleaf.dataconverter.types.ObjectType;
import ca.spottedleaf.dataconverter.types.Types;
import com.google.common.base.Splitter;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.kyori.adventure.key.InvalidKeyException;
import net.kyori.adventure.key.Key;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Iterator;
import java.util.List;

public final class V1451 {

    protected static final int VERSION = MCVersions.V17W47A;
    private static final Object2IntMap<String> REMAPPED_IDS = new Object2IntOpenHashMap<>();
    static {
        REMAPPED_IDS.put("minecraft:air", 0);
        REMAPPED_IDS.put("minecraft:stone", 1);
        REMAPPED_IDS.put("minecraft:grass", 2);
        REMAPPED_IDS.put("minecraft:dirt", 3);
        REMAPPED_IDS.put("minecraft:cobblestone", 4);
        REMAPPED_IDS.put("minecraft:planks", 5);
        REMAPPED_IDS.put("minecraft:sapling", 6);
        REMAPPED_IDS.put("minecraft:bedrock", 7);
        REMAPPED_IDS.put("minecraft:flowing_water", 8);
        REMAPPED_IDS.put("minecraft:water", 9);
        REMAPPED_IDS.put("minecraft:flowing_lava", 10);
        REMAPPED_IDS.put("minecraft:lava", 11);
        REMAPPED_IDS.put("minecraft:sand", 12);
        REMAPPED_IDS.put("minecraft:gravel", 13);
        REMAPPED_IDS.put("minecraft:gold_ore", 14);
        REMAPPED_IDS.put("minecraft:iron_ore", 15);
        REMAPPED_IDS.put("minecraft:coal_ore", 16);
        REMAPPED_IDS.put("minecraft:log", 17);
        REMAPPED_IDS.put("minecraft:leaves", 18);
        REMAPPED_IDS.put("minecraft:sponge", 19);
        REMAPPED_IDS.put("minecraft:glass", 20);
        REMAPPED_IDS.put("minecraft:lapis_ore", 21);
        REMAPPED_IDS.put("minecraft:lapis_block", 22);
        REMAPPED_IDS.put("minecraft:dispenser", 23);
        REMAPPED_IDS.put("minecraft:sandstone", 24);
        REMAPPED_IDS.put("minecraft:noteblock", 25);
        REMAPPED_IDS.put("minecraft:bed", 26);
        REMAPPED_IDS.put("minecraft:golden_rail", 27);
        REMAPPED_IDS.put("minecraft:detector_rail", 28);
        REMAPPED_IDS.put("minecraft:sticky_piston", 29);
        REMAPPED_IDS.put("minecraft:web", 30);
        REMAPPED_IDS.put("minecraft:tallgrass", 31);
        REMAPPED_IDS.put("minecraft:deadbush", 32);
        REMAPPED_IDS.put("minecraft:piston", 33);
        REMAPPED_IDS.put("minecraft:piston_head", 34);
        REMAPPED_IDS.put("minecraft:wool", 35);
        REMAPPED_IDS.put("minecraft:piston_extension", 36);
        REMAPPED_IDS.put("minecraft:yellow_flower", 37);
        REMAPPED_IDS.put("minecraft:red_flower", 38);
        REMAPPED_IDS.put("minecraft:brown_mushroom", 39);
        REMAPPED_IDS.put("minecraft:red_mushroom", 40);
        REMAPPED_IDS.put("minecraft:gold_block", 41);
        REMAPPED_IDS.put("minecraft:iron_block", 42);
        REMAPPED_IDS.put("minecraft:double_stone_slab", 43);
        REMAPPED_IDS.put("minecraft:stone_slab", 44);
        REMAPPED_IDS.put("minecraft:brick_block", 45);
        REMAPPED_IDS.put("minecraft:tnt", 46);
        REMAPPED_IDS.put("minecraft:bookshelf", 47);
        REMAPPED_IDS.put("minecraft:mossy_cobblestone", 48);
        REMAPPED_IDS.put("minecraft:obsidian", 49);
        REMAPPED_IDS.put("minecraft:torch", 50);
        REMAPPED_IDS.put("minecraft:fire", 51);
        REMAPPED_IDS.put("minecraft:mob_spawner", 52);
        REMAPPED_IDS.put("minecraft:oak_stairs", 53);
        REMAPPED_IDS.put("minecraft:chest", 54);
        REMAPPED_IDS.put("minecraft:redstone_wire", 55);
        REMAPPED_IDS.put("minecraft:diamond_ore", 56);
        REMAPPED_IDS.put("minecraft:diamond_block", 57);
        REMAPPED_IDS.put("minecraft:crafting_table", 58);
        REMAPPED_IDS.put("minecraft:wheat", 59);
        REMAPPED_IDS.put("minecraft:farmland", 60);
        REMAPPED_IDS.put("minecraft:furnace", 61);
        REMAPPED_IDS.put("minecraft:lit_furnace", 62);
        REMAPPED_IDS.put("minecraft:standing_sign", 63);
        REMAPPED_IDS.put("minecraft:wooden_door", 64);
        REMAPPED_IDS.put("minecraft:ladder", 65);
        REMAPPED_IDS.put("minecraft:rail", 66);
        REMAPPED_IDS.put("minecraft:stone_stairs", 67);
        REMAPPED_IDS.put("minecraft:wall_sign", 68);
        REMAPPED_IDS.put("minecraft:lever", 69);
        REMAPPED_IDS.put("minecraft:stone_pressure_plate", 70);
        REMAPPED_IDS.put("minecraft:iron_door", 71);
        REMAPPED_IDS.put("minecraft:wooden_pressure_plate", 72);
        REMAPPED_IDS.put("minecraft:redstone_ore", 73);
        REMAPPED_IDS.put("minecraft:lit_redstone_ore", 74);
        REMAPPED_IDS.put("minecraft:unlit_redstone_torch", 75);
        REMAPPED_IDS.put("minecraft:redstone_torch", 76);
        REMAPPED_IDS.put("minecraft:stone_button", 77);
        REMAPPED_IDS.put("minecraft:snow_layer", 78);
        REMAPPED_IDS.put("minecraft:ice", 79);
        REMAPPED_IDS.put("minecraft:snow", 80);
        REMAPPED_IDS.put("minecraft:cactus", 81);
        REMAPPED_IDS.put("minecraft:clay", 82);
        REMAPPED_IDS.put("minecraft:reeds", 83);
        REMAPPED_IDS.put("minecraft:jukebox", 84);
        REMAPPED_IDS.put("minecraft:fence", 85);
        REMAPPED_IDS.put("minecraft:pumpkin", 86);
        REMAPPED_IDS.put("minecraft:netherrack", 87);
        REMAPPED_IDS.put("minecraft:soul_sand", 88);
        REMAPPED_IDS.put("minecraft:glowstone", 89);
        REMAPPED_IDS.put("minecraft:portal", 90);
        REMAPPED_IDS.put("minecraft:lit_pumpkin", 91);
        REMAPPED_IDS.put("minecraft:cake", 92);
        REMAPPED_IDS.put("minecraft:unpowered_repeater", 93);
        REMAPPED_IDS.put("minecraft:powered_repeater", 94);
        REMAPPED_IDS.put("minecraft:stained_glass", 95);
        REMAPPED_IDS.put("minecraft:trapdoor", 96);
        REMAPPED_IDS.put("minecraft:monster_egg", 97);
        REMAPPED_IDS.put("minecraft:stonebrick", 98);
        REMAPPED_IDS.put("minecraft:brown_mushroom_block", 99);
        REMAPPED_IDS.put("minecraft:red_mushroom_block", 100);
        REMAPPED_IDS.put("minecraft:iron_bars", 101);
        REMAPPED_IDS.put("minecraft:glass_pane", 102);
        REMAPPED_IDS.put("minecraft:melon_block", 103);
        REMAPPED_IDS.put("minecraft:pumpkin_stem", 104);
        REMAPPED_IDS.put("minecraft:melon_stem", 105);
        REMAPPED_IDS.put("minecraft:vine", 106);
        REMAPPED_IDS.put("minecraft:fence_gate", 107);
        REMAPPED_IDS.put("minecraft:brick_stairs", 108);
        REMAPPED_IDS.put("minecraft:stone_brick_stairs", 109);
        REMAPPED_IDS.put("minecraft:mycelium", 110);
        REMAPPED_IDS.put("minecraft:waterlily", 111);
        REMAPPED_IDS.put("minecraft:nether_brick", 112);
        REMAPPED_IDS.put("minecraft:nether_brick_fence", 113);
        REMAPPED_IDS.put("minecraft:nether_brick_stairs", 114);
        REMAPPED_IDS.put("minecraft:nether_wart", 115);
        REMAPPED_IDS.put("minecraft:enchanting_table", 116);
        REMAPPED_IDS.put("minecraft:brewing_stand", 117);
        REMAPPED_IDS.put("minecraft:cauldron", 118);
        REMAPPED_IDS.put("minecraft:end_portal", 119);
        REMAPPED_IDS.put("minecraft:end_portal_frame", 120);
        REMAPPED_IDS.put("minecraft:end_stone", 121);
        REMAPPED_IDS.put("minecraft:dragon_egg", 122);
        REMAPPED_IDS.put("minecraft:redstone_lamp", 123);
        REMAPPED_IDS.put("minecraft:lit_redstone_lamp", 124);
        REMAPPED_IDS.put("minecraft:double_wooden_slab", 125);
        REMAPPED_IDS.put("minecraft:wooden_slab", 126);
        REMAPPED_IDS.put("minecraft:cocoa", 127);
        REMAPPED_IDS.put("minecraft:sandstone_stairs", 128);
        REMAPPED_IDS.put("minecraft:emerald_ore", 129);
        REMAPPED_IDS.put("minecraft:ender_chest", 130);
        REMAPPED_IDS.put("minecraft:tripwire_hook", 131);
        REMAPPED_IDS.put("minecraft:tripwire", 132);
        REMAPPED_IDS.put("minecraft:emerald_block", 133);
        REMAPPED_IDS.put("minecraft:spruce_stairs", 134);
        REMAPPED_IDS.put("minecraft:birch_stairs", 135);
        REMAPPED_IDS.put("minecraft:jungle_stairs", 136);
        REMAPPED_IDS.put("minecraft:command_block", 137);
        REMAPPED_IDS.put("minecraft:beacon", 138);
        REMAPPED_IDS.put("minecraft:cobblestone_wall", 139);
        REMAPPED_IDS.put("minecraft:flower_pot", 140);
        REMAPPED_IDS.put("minecraft:carrots", 141);
        REMAPPED_IDS.put("minecraft:potatoes", 142);
        REMAPPED_IDS.put("minecraft:wooden_button", 143);
        REMAPPED_IDS.put("minecraft:skull", 144);
        REMAPPED_IDS.put("minecraft:anvil", 145);
        REMAPPED_IDS.put("minecraft:trapped_chest", 146);
        REMAPPED_IDS.put("minecraft:light_weighted_pressure_plate", 147);
        REMAPPED_IDS.put("minecraft:heavy_weighted_pressure_plate", 148);
        REMAPPED_IDS.put("minecraft:unpowered_comparator", 149);
        REMAPPED_IDS.put("minecraft:powered_comparator", 150);
        REMAPPED_IDS.put("minecraft:daylight_detector", 151);
        REMAPPED_IDS.put("minecraft:redstone_block", 152);
        REMAPPED_IDS.put("minecraft:quartz_ore", 153);
        REMAPPED_IDS.put("minecraft:hopper", 154);
        REMAPPED_IDS.put("minecraft:quartz_block", 155);
        REMAPPED_IDS.put("minecraft:quartz_stairs", 156);
        REMAPPED_IDS.put("minecraft:activator_rail", 157);
        REMAPPED_IDS.put("minecraft:dropper", 158);
        REMAPPED_IDS.put("minecraft:stained_hardened_clay", 159);
        REMAPPED_IDS.put("minecraft:stained_glass_pane", 160);
        REMAPPED_IDS.put("minecraft:leaves2", 161);
        REMAPPED_IDS.put("minecraft:log2", 162);
        REMAPPED_IDS.put("minecraft:acacia_stairs", 163);
        REMAPPED_IDS.put("minecraft:dark_oak_stairs", 164);
        REMAPPED_IDS.put("minecraft:slime", 165);
        REMAPPED_IDS.put("minecraft:barrier", 166);
        REMAPPED_IDS.put("minecraft:iron_trapdoor", 167);
        REMAPPED_IDS.put("minecraft:prismarine", 168);
        REMAPPED_IDS.put("minecraft:sea_lantern", 169);
        REMAPPED_IDS.put("minecraft:hay_block", 170);
        REMAPPED_IDS.put("minecraft:carpet", 171);
        REMAPPED_IDS.put("minecraft:hardened_clay", 172);
        REMAPPED_IDS.put("minecraft:coal_block", 173);
        REMAPPED_IDS.put("minecraft:packed_ice", 174);
        REMAPPED_IDS.put("minecraft:double_plant", 175);
        REMAPPED_IDS.put("minecraft:standing_banner", 176);
        REMAPPED_IDS.put("minecraft:wall_banner", 177);
        REMAPPED_IDS.put("minecraft:daylight_detector_inverted", 178);
        REMAPPED_IDS.put("minecraft:red_sandstone", 179);
        REMAPPED_IDS.put("minecraft:red_sandstone_stairs", 180);
        REMAPPED_IDS.put("minecraft:double_stone_slab2", 181);
        REMAPPED_IDS.put("minecraft:stone_slab2", 182);
        REMAPPED_IDS.put("minecraft:spruce_fence_gate", 183);
        REMAPPED_IDS.put("minecraft:birch_fence_gate", 184);
        REMAPPED_IDS.put("minecraft:jungle_fence_gate", 185);
        REMAPPED_IDS.put("minecraft:dark_oak_fence_gate", 186);
        REMAPPED_IDS.put("minecraft:acacia_fence_gate", 187);
        REMAPPED_IDS.put("minecraft:spruce_fence", 188);
        REMAPPED_IDS.put("minecraft:birch_fence", 189);
        REMAPPED_IDS.put("minecraft:jungle_fence", 190);
        REMAPPED_IDS.put("minecraft:dark_oak_fence", 191);
        REMAPPED_IDS.put("minecraft:acacia_fence", 192);
        REMAPPED_IDS.put("minecraft:spruce_door", 193);
        REMAPPED_IDS.put("minecraft:birch_door", 194);
        REMAPPED_IDS.put("minecraft:jungle_door", 195);
        REMAPPED_IDS.put("minecraft:acacia_door", 196);
        REMAPPED_IDS.put("minecraft:dark_oak_door", 197);
        REMAPPED_IDS.put("minecraft:end_rod", 198);
        REMAPPED_IDS.put("minecraft:chorus_plant", 199);
        REMAPPED_IDS.put("minecraft:chorus_flower", 200);
        REMAPPED_IDS.put("minecraft:purpur_block", 201);
        REMAPPED_IDS.put("minecraft:purpur_pillar", 202);
        REMAPPED_IDS.put("minecraft:purpur_stairs", 203);
        REMAPPED_IDS.put("minecraft:purpur_double_slab", 204);
        REMAPPED_IDS.put("minecraft:purpur_slab", 205);
        REMAPPED_IDS.put("minecraft:end_bricks", 206);
        REMAPPED_IDS.put("minecraft:beetroots", 207);
        REMAPPED_IDS.put("minecraft:grass_path", 208);
        REMAPPED_IDS.put("minecraft:end_gateway", 209);
        REMAPPED_IDS.put("minecraft:repeating_command_block", 210);
        REMAPPED_IDS.put("minecraft:chain_command_block", 211);
        REMAPPED_IDS.put("minecraft:frosted_ice", 212);
        REMAPPED_IDS.put("minecraft:magma", 213);
        REMAPPED_IDS.put("minecraft:nether_wart_block", 214);
        REMAPPED_IDS.put("minecraft:red_nether_brick", 215);
        REMAPPED_IDS.put("minecraft:bone_block", 216);
        REMAPPED_IDS.put("minecraft:structure_void", 217);
        REMAPPED_IDS.put("minecraft:observer", 218);
        REMAPPED_IDS.put("minecraft:white_shulker_box", 219);
        REMAPPED_IDS.put("minecraft:orange_shulker_box", 220);
        REMAPPED_IDS.put("minecraft:magenta_shulker_box", 221);
        REMAPPED_IDS.put("minecraft:light_blue_shulker_box", 222);
        REMAPPED_IDS.put("minecraft:yellow_shulker_box", 223);
        REMAPPED_IDS.put("minecraft:lime_shulker_box", 224);
        REMAPPED_IDS.put("minecraft:pink_shulker_box", 225);
        REMAPPED_IDS.put("minecraft:gray_shulker_box", 226);
        REMAPPED_IDS.put("minecraft:silver_shulker_box", 227);
        REMAPPED_IDS.put("minecraft:cyan_shulker_box", 228);
        REMAPPED_IDS.put("minecraft:purple_shulker_box", 229);
        REMAPPED_IDS.put("minecraft:blue_shulker_box", 230);
        REMAPPED_IDS.put("minecraft:brown_shulker_box", 231);
        REMAPPED_IDS.put("minecraft:green_shulker_box", 232);
        REMAPPED_IDS.put("minecraft:red_shulker_box", 233);
        REMAPPED_IDS.put("minecraft:black_shulker_box", 234);
        REMAPPED_IDS.put("minecraft:white_glazed_terracotta", 235);
        REMAPPED_IDS.put("minecraft:orange_glazed_terracotta", 236);
        REMAPPED_IDS.put("minecraft:magenta_glazed_terracotta", 237);
        REMAPPED_IDS.put("minecraft:light_blue_glazed_terracotta", 238);
        REMAPPED_IDS.put("minecraft:yellow_glazed_terracotta", 239);
        REMAPPED_IDS.put("minecraft:lime_glazed_terracotta", 240);
        REMAPPED_IDS.put("minecraft:pink_glazed_terracotta", 241);
        REMAPPED_IDS.put("minecraft:gray_glazed_terracotta", 242);
        REMAPPED_IDS.put("minecraft:silver_glazed_terracotta", 243);
        REMAPPED_IDS.put("minecraft:cyan_glazed_terracotta", 244);
        REMAPPED_IDS.put("minecraft:purple_glazed_terracotta", 245);
        REMAPPED_IDS.put("minecraft:blue_glazed_terracotta", 246);
        REMAPPED_IDS.put("minecraft:brown_glazed_terracotta", 247);
        REMAPPED_IDS.put("minecraft:green_glazed_terracotta", 248);
        REMAPPED_IDS.put("minecraft:red_glazed_terracotta", 249);
        REMAPPED_IDS.put("minecraft:black_glazed_terracotta", 250);
        REMAPPED_IDS.put("minecraft:concrete", 251);
        REMAPPED_IDS.put("minecraft:concrete_powder", 252);
        REMAPPED_IDS.put("minecraft:structure_block", 255);
    }

    public static void register() {
        // V0
        MCTypeRegistry.TILE_ENTITY.addWalker(VERSION, 0, "minecraft:trapped_chest", new DataWalkerItemLists("Items"));

        // V1
        MCTypeRegistry.CHUNK.addStructureConverter(new ConverterFlattenChunk());

        MCTypeRegistry.CHUNK.addStructureWalker(VERSION, 1, (final MapType<String> data, final long fromVersion, final long toVersion) -> {
            final MapType<String> level = data.getMap("Level");
            if (level == null) {
                return null;
            }

            WalkerUtils.convertList(MCTypeRegistry.ENTITY, level, "Entities", fromVersion, toVersion);
            WalkerUtils.convertList(MCTypeRegistry.TILE_ENTITY, level, "TileEntities", fromVersion, toVersion);

            final ListType tileTicks = level.getList("TileTicks", ObjectType.MAP);
            if (tileTicks != null) {
                for (int i = 0, len = tileTicks.size(); i < len; ++i) {
                    final MapType<String> tileTick = tileTicks.getMap(i);
                    WalkerUtils.convert(MCTypeRegistry.BLOCK_NAME, tileTick, "i", fromVersion, toVersion);
                }
            }

            final ListType sections = level.getList("Sections", ObjectType.MAP);
            if (sections != null) {
                for (int i = 0, len = sections.size(); i < len; ++i) {
                    final MapType<String> section = sections.getMap(i);
                    WalkerUtils.convertList(MCTypeRegistry.BLOCK_STATE, section, "Palette", fromVersion, toVersion);
                }
            }

            return null;
        });

        // V2
        MCTypeRegistry.TILE_ENTITY.addConverterForId("minecraft:piston", new DataConverter<>(VERSION, 2) {
            @Override
            public MapType<String> convert(final MapType<String> data, final long sourceVersion, final long toVersion) {
                final int blockId = data.getInt("blockId");
                final int blockData = data.getInt("blockData") & 15;

                data.remove("blockId");
                data.remove("blockData");

                data.setMap("blockState", HelperBlockFlatteningV1450.getNBTForId((blockId << 4) | blockData).copy()); // copy to avoid problems with later state datafixers

                return null;
            }
        });

        MCTypeRegistry.TILE_ENTITY.addWalker(VERSION, 2, "minecraft:piston", new DataWalkerTypePaths<>(MCTypeRegistry.BLOCK_STATE, "blockState"));

        // V3
        ConverterFlattenEntity.register();
        MCTypeRegistry.ITEM_STACK.addConverterForId("minecraft:filled_map", new DataConverter<>(VERSION, 3) {
            @Override
            public MapType<String> convert(final MapType<String> data, final long sourceVersion, final long toVersion) {
                MapType<String> tag = data.getMap("tag");
                if (tag == null) {
                    tag = Types.NBT.createEmptyMap();
                    data.setMap("tag", tag);
                }

                if (!tag.hasKey("map", ObjectType.NUMBER)) { // This if is from CB. as usual, no documentation from CB. I'm guessing it just wants to avoid possibly overwriting it. seems fine.
                    tag.setInt("map", data.getInt("Damage"));
                }

                return null;
            }
        });

        MCTypeRegistry.ENTITY.addWalker(VERSION, 3, "minecraft:potion", new DataWalkerItems("Potion"));
        MCTypeRegistry.ENTITY.addWalker(VERSION, 3, "minecraft:arrow", new DataWalkerTypePaths<>(MCTypeRegistry.BLOCK_STATE, "inBlockState"));
        MCTypeRegistry.ENTITY.addWalker(VERSION, 3, "minecraft:enderman", new DataWalkerItemLists("ArmorItems", "HandItems"));
        MCTypeRegistry.ENTITY.addWalker(VERSION, 3, "minecraft:enderman", new DataWalkerTypePaths<>(MCTypeRegistry.BLOCK_STATE, "carriedBlockState"));
        MCTypeRegistry.ENTITY.addWalker(VERSION, 3, "minecraft:falling_block", new DataWalkerTypePaths<>(MCTypeRegistry.BLOCK_STATE, "BlockState"));
        MCTypeRegistry.ENTITY.addWalker(VERSION, 3, "minecraft:falling_block", new DataWalkerTileEntities("TileEntityData"));
        MCTypeRegistry.ENTITY.addWalker(VERSION, 3, "minecraft:spectral_arrow", new DataWalkerTypePaths<>(MCTypeRegistry.BLOCK_STATE, "inBlockState"));
        MCTypeRegistry.ENTITY.addWalker(VERSION, 3, "minecraft:chest_minecart", new DataWalkerTypePaths<>(MCTypeRegistry.BLOCK_STATE, "DisplayState"));
        MCTypeRegistry.ENTITY.addWalker(VERSION, 3, "minecraft:chest_minecart", new DataWalkerItemLists("Items"));
        MCTypeRegistry.ENTITY.addWalker(VERSION, 3, "minecraft:commandblock_minecart", new DataWalkerTypePaths<>(MCTypeRegistry.BLOCK_STATE, "DisplayState"));
        MCTypeRegistry.ENTITY.addWalker(VERSION, 3, "minecraft:furnace_minecart", new DataWalkerTypePaths<>(MCTypeRegistry.BLOCK_STATE, "DisplayState"));
        MCTypeRegistry.ENTITY.addWalker(VERSION, 3, "minecraft:hopper_minecart", new DataWalkerTypePaths<>(MCTypeRegistry.BLOCK_STATE, "DisplayState"));
        MCTypeRegistry.ENTITY.addWalker(VERSION, 3, "minecraft:hopper_minecart", new DataWalkerItemLists("Items"));
        MCTypeRegistry.ENTITY.addWalker(VERSION, 3, "minecraft:minecart", new DataWalkerTypePaths<>(MCTypeRegistry.BLOCK_STATE, "DisplayState"));
        MCTypeRegistry.ENTITY.addWalker(VERSION, 3, "minecraft:spawner_minecart", new DataWalkerTypePaths<>(MCTypeRegistry.BLOCK_STATE, "DisplayState"));
        MCTypeRegistry.ENTITY.addWalker(VERSION, 3, "minecraft:spawner_minecart", (final MapType<String> data, final long fromVersion, final long toVersion) -> {
            MCTypeRegistry.UNTAGGED_SPAWNER.convert(data, fromVersion, toVersion);
            return null;
        });
        MCTypeRegistry.ENTITY.addWalker(VERSION, 3, "minecraft:tnt_minecart", new DataWalkerTypePaths<>(MCTypeRegistry.BLOCK_STATE, "DisplayState"));

        // V4
        MCTypeRegistry.BLOCK_NAME.addConverter(new DataConverter<>(VERSION, 4) {
            @Override
            public Object convert(final Object data, final long sourceVersion, final long toVersion) {
                if (data instanceof Number) {
                    return HelperBlockFlatteningV1450.getNameForId(((Number)data).intValue());
                } else if (data instanceof String) {
                    return HelperBlockFlatteningV1450.getNewBlockName((String)data); // structure hook ensured data is namespaced
                }
                return null;
            }
        });
        MCTypeRegistry.ITEM_STACK.addStructureConverter(new ConverterFlattenItemStack());

        // V5
        MCTypeRegistry.ITEM_STACK.addConverterForId("minecraft:spawn_egg", new ConverterFlattenSpawnEgg());
        /* This datafixer has been disabled because the collar colour handler did not change from 1.12 -> 1.13 at all.
        // So clearly somebody fucked up. This fixes wolf colours incorrectly converting between versions
        MCTypeRegistry.ENTITY.addConverterForId("minecraft:wolf", new DataConverter<>(VERSION, 5) {
            @Override
            public MapType<String> convert(final MapType<String> data, final long sourceVersion, final long toVersion) {
                final Number colour = data.getNumber("CollarColor");

                if (colour != null) {
                    data.setByte("CollarColor", (byte)(15 - colour.intValue()));
                }

                return null;
            }
        });
         */
        MCTypeRegistry.TILE_ENTITY.addConverterForId("minecraft:banner", new DataConverter<>(VERSION, 5) {
            @Override
            public MapType<String> convert(final MapType<String> data, final long sourceVersion, final long toVersion) {
                final Number base = data.getNumber("Base");
                if (base != null) {
                    data.setInt("Base", 15 - base.intValue());
                }

                final ListType patterns = data.getList("Patterns", ObjectType.MAP);
                if (patterns != null) {
                    for (int i = 0, len = patterns.size(); i < len; ++i) {
                        final MapType<String> pattern = patterns.getMap(i);
                        final Number colour = pattern.getNumber("Color");
                        if (colour != null) {
                            pattern.setInt("Color", 15 - colour.intValue());
                        }
                    }
                }

                return null;
            }
        });
        MCTypeRegistry.LEVEL.addStructureConverter(new DataConverter<>(VERSION, 5) {

            private static final Splitter SPLITTER = Splitter.on(';').limit(5);
            private static final Splitter LAYER_SPLITTER = Splitter.on(',');
            private static final Splitter OLD_AMOUNT_SPLITTER = Splitter.on('x').limit(2);
            private static final Splitter AMOUNT_SPLITTER = Splitter.on('*').limit(2);
            private static final Splitter BLOCK_SPLITTER = Splitter.on(':').limit(3);

            public MapType<String> convert(final MapType<String> data, final long sourceVersion, final long toVersion) {
                if (!data.getString("generatorName").equals("flat")) return null;
                final String generatorOptions = data.getString("generatorOptions");
                if (generatorOptions == null) return null;
                data.setString("generatorOptions", fixGeneratorSettings(generatorOptions));
                return null;
            }

            // I rewrote this for my DFU fixes port, so this is what I'm using here
            private static String fixGeneratorSettings(final String generatorOptions) {
                if (generatorOptions.isEmpty()) {
                    return "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
                }
                final Iterator<String> iterator = SPLITTER.split(generatorOptions).iterator();
                final String first = iterator.next();
                final int version = iterator.hasNext() ? NumberUtils.toInt(first, 0) : 0;
                final String next = iterator.hasNext() ? iterator.next() : first;

                if (version < 0 || version > 3) {
                    return "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
                }
                final StringBuilder builder = new StringBuilder();
                final Splitter splitter = version < 3 ? OLD_AMOUNT_SPLITTER : AMOUNT_SPLITTER;

                builder.append(LAYER_SPLITTER.splitToList(next).stream().map(element -> {
                    final List<String> amounts = splitter.splitToList(element);
                    final int amount = amounts.size() == 2 ? NumberUtils.toInt(amounts.get(0), 0) : 1;
                    final String blocksString = amounts.size() == 2 ? amounts.get(1) : amounts.get(0);
                    final List<String> blocks = BLOCK_SPLITTER.splitToList(blocksString);
                    final int index = blocks.get(0).equals("minecraft") ? 1 : 0;
                    final String block = blocks.get(index);
                    final int blockId = getBlockId(block);
                    final int nextIndex = index + 1;
                    int id;
                    try {
                        id = NumberUtils.toInt(blocks.get(nextIndex), 0);
                    } catch (final ArrayIndexOutOfBoundsException exception) {
                        id = 0;
                    }
                    final String prefix = amount == 1 ? "" : amount + "*";
                    final MapType<String> nbt = HelperBlockFlatteningV1450.getNBTForIdRaw(blockId >> 4 | id);
                    return prefix + nbt.getString("Name", "");
                }));
                while (iterator.hasNext()) builder.append(";").append(iterator.next());
                return builder.toString();
            }
        });

        // V6
        MCTypeRegistry.STATS.addStructureConverter(new ConverterFlattenStats());
        MCTypeRegistry.TILE_ENTITY.addConverterForId("minecraft:jukebox", new DataConverter<>(VERSION, 6) {
            @Override
            public MapType<String> convert(final MapType<String> data, final long sourceVersion, final long toVersion) {
                final int record = data.getInt("Record");
                if (record <= 0) {
                    return null;
                }

                data.remove("Record");

                final String newItemId = ConverterFlattenItemStack.flattenItem(HelperItemNameV102.getNameFromId(record), 0);
                if (newItemId == null) {
                    return null;
                }

                final MapType<String> recordItem = Types.NBT.createEmptyMap();
                data.setMap("RecordItem", recordItem);

                recordItem.setString("id", newItemId);
                recordItem.setByte("Count", (byte)1);

                return null;
            }
        });

        MCTypeRegistry.STATS.addStructureWalker(VERSION, 6, (final MapType<String> data, final long fromVersion, final long toVersion) -> {
            final MapType<String> stats = data.getMap("stats");
            if (stats == null) {
                return null;
            }

            WalkerUtils.convertKeys(MCTypeRegistry.BLOCK_NAME, stats, "minecraft:mined", fromVersion, toVersion);

            WalkerUtils.convertKeys(MCTypeRegistry.ITEM_NAME, stats, "minecraft:crafted", fromVersion, toVersion);
            WalkerUtils.convertKeys(MCTypeRegistry.ITEM_NAME, stats, "minecraft:used", fromVersion, toVersion);
            WalkerUtils.convertKeys(MCTypeRegistry.ITEM_NAME, stats, "minecraft:broken", fromVersion, toVersion);
            WalkerUtils.convertKeys(MCTypeRegistry.ITEM_NAME, stats, "minecraft:picked_up", fromVersion, toVersion);
            WalkerUtils.convertKeys(MCTypeRegistry.ITEM_NAME, stats, "minecraft:dropped", fromVersion, toVersion);

            WalkerUtils.convertKeys(MCTypeRegistry.ENTITY_NAME, stats, "minecraft:killed", fromVersion, toVersion);
            WalkerUtils.convertKeys(MCTypeRegistry.ENTITY_NAME, stats, "minecraft:killed_by", fromVersion, toVersion);

            return null;
        });

        MCTypeRegistry.OBJECTIVE.addStructureHook(VERSION, 6, new DataHook<>() {
            private static String packWithDot(final String string) {
                Key key;
                try {
                    key = Key.key(string);
                } catch (final InvalidKeyException exception) {
                    key = null;
                }
                return key != null ? key.namespace() + "." + key.value() : string;
            }

            @Override
            public MapType<String> preHook(final MapType<String> data, final long fromVersion, final long toVersion) {
                // unpack
                final String criteriaName = data.getString("CriteriaName");
                String type;
                String id;

                if (criteriaName != null) {
                    final int index = criteriaName.indexOf(':');
                    if (index < 0) {
                        type = "_special";
                        id = criteriaName;
                    } else {
                        try {
                            type = Key.key(criteriaName.substring(0, index), '.').asString();
                            id = Key.key(criteriaName.substring(index + 1), '.').asString();
                        } catch (final Exception ex) {
                            type = "_special";
                            id = criteriaName;
                        }
                    }
                } else {
                    type = null;
                    id = null;
                }

                if (type != null && id != null) {
                    final MapType<String> criteriaType = Types.NBT.createEmptyMap();
                    data.setMap("CriteriaType", criteriaType);

                    criteriaType.setString("type", type);
                    criteriaType.setString("id", id);
                }

                return null;
            }

            @Override
            public MapType<String> postHook(final MapType<String> data, final long fromVersion, final long toVersion) {
                // repack
                final MapType<String> criteriaType = data.getMap("CriteriaType");

                final String newName;
                if (criteriaType == null) {
                    newName = null;
                } else {
                    final String type = criteriaType.getString("type");
                    final String id = criteriaType.getString("id");
                    if (type != null && id != null) {
                        if ("_special".equals(type)) {
                            newName = id;
                        } else {
                            newName = packWithDot(type) + ":" + packWithDot(id);
                        }
                    } else {
                        newName = null;
                    }
                }

                if (newName != null) {
                    data.remove("CriteriaType");
                    data.setString("CriteriaName", newName);
                }

                return null;
            }
        });

        MCTypeRegistry.OBJECTIVE.addStructureWalker(VERSION, 6, (final MapType<String> data, final long fromVersion, final long toVersion) -> {
            final MapType<String> criteriaType = data.getMap("CriteriaType");
            if (criteriaType == null) {
                return null;
            }

            final String type = criteriaType.getString("type");

            if (type == null) {
                return null;
            }

            switch (type) {
                case "minecraft:mined": {
                    WalkerUtils.convert(MCTypeRegistry.BLOCK_NAME, criteriaType, "id", fromVersion, toVersion);
                    break;
                }

                case "minecraft:crafted":
                case "minecraft:used":
                case "minecraft:broken":
                case "minecraft:picked_up":
                case "minecraft:dropped": {
                    WalkerUtils.convert(MCTypeRegistry.ITEM_NAME, criteriaType, "id", fromVersion, toVersion);
                    break;
                }

                case "minecraft:killed":
                case "minecraft:killed_by": {
                    WalkerUtils.convert(MCTypeRegistry.ENTITY_NAME, criteriaType, "id", fromVersion, toVersion);
                    break;
                }
            }

            return null;
        });


        // V7
        MCTypeRegistry.STRUCTURE_FEATURE.addStructureConverter(new DataConverter<>(VERSION, 7) {
            private static void convertToBlockState(final MapType<String> data, final String path) {
                final Number number = data.getNumber(path);
                if (number == null) {
                    return;
                }

                data.setMap(path, HelperBlockFlatteningV1450.getNBTForId(number.intValue() << 4).copy()); // copy to avoid problems with later state datafixers
            }

            @Override
            public MapType<String> convert(final MapType<String> data, final long sourceVersion, final long toVersion) {
                final ListType children = data.getList("Children", ObjectType.MAP);
                if (children == null) {
                    return null;
                }

                for (int i = 0, len = children.size(); i < len; ++i) {
                    final MapType<String> child = children.getMap(i);

                    final String id = child.getString("id");

                    switch (id) {
                        case "ViF":
                            convertToBlockState(child, "CA");
                            convertToBlockState(child, "CB");
                            break;
                        case "ViDF":
                            convertToBlockState(child, "CA");
                            convertToBlockState(child, "CB");
                            convertToBlockState(child, "CC");
                            convertToBlockState(child, "CD");
                            break;
                    }
                }

                return null;
            }
        });

        // convert villagers to trade with pumpkins and not the carved pumpkin
        MCTypeRegistry.ENTITY.addConverterForId("minecraft:villager", new DataConverter<>(VERSION, 7) {
            private static void convertPumpkin(final MapType<String> data, final String path) {
                final MapType<String> item = data.getMap(path);
                if (item == null) {
                    return;
                }

                final String id = item.getString("id");

                if (id.equals("minecraft:carved_pumpkin")) {
                    item.setString("id", "minecraft:pumpkin");
                }
            }

            @Override
            public MapType<String> convert(final MapType<String> data, final long sourceVersion, final long toVersion) {
                final MapType<String> offers = data.getMap("Offers");
                if (offers != null) {
                    final ListType recipes = offers.getList("Recipes", ObjectType.MAP);
                    if (recipes != null) {
                        for (int i = 0, len = recipes.size(); i < len; ++i) {
                            final MapType<String> recipe = recipes.getMap(i);

                            convertPumpkin(recipe, "buy");
                            convertPumpkin(recipe, "buyB");
                            convertPumpkin(recipe, "sell");
                        }
                    }
                }
                return null;
            }
        });

        MCTypeRegistry.STRUCTURE_FEATURE.addStructureWalker(VERSION, 7, (final MapType<String> data, final long fromVersion, final long toVersion) -> {
            final ListType list = data.getList("Children", ObjectType.MAP);
            if (list == null) {
                return null;
            }

            for (int i = 0, len = list.size(); i < len; ++i) {
                final MapType<String> child = list.getMap(i);
                WalkerUtils.convert(MCTypeRegistry.BLOCK_STATE, child, "CA", fromVersion, toVersion);
                WalkerUtils.convert(MCTypeRegistry.BLOCK_STATE, child, "CB", fromVersion, toVersion);
                WalkerUtils.convert(MCTypeRegistry.BLOCK_STATE, child, "CC", fromVersion, toVersion);
                WalkerUtils.convert(MCTypeRegistry.BLOCK_STATE, child, "CD", fromVersion, toVersion);
            }

            return null;
        });
    }

    private static int getBlockId(final String block) {
        return REMAPPED_IDS.getOrDefault(block, 0);
    }

    private V1451() {}
}
