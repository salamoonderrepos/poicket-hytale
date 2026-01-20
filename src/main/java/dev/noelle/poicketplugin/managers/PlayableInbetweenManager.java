package dev.noelle.poicketplugin.managers;

import com.hypixel.hytale.protocol.WorldEnvironment;
import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.WorldConfig;
import com.hypixel.hytale.server.core.universe.world.WorldConfigProvider;
import com.hypixel.hytale.server.core.universe.world.WorldProvider;
import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGen;
import com.hypixel.hytale.server.core.universe.world.worldgen.WorldGenLoadException;
import com.hypixel.hytale.server.core.universe.world.worldgen.provider.IWorldGenProvider;
import com.hypixel.hytale.server.core.universe.world.worldgen.provider.VoidWorldGenProvider;

import javax.annotation.Nullable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

import static com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil.getLogger;

public class PlayableInbetweenManager {
    private final static String WORLDNAME = "Inbetween";
    private final static String WORLDGEN = "Void";
    private static World world;

    public PlayableInbetweenManager(World w){
        world = w;
    }
    public PlayableInbetweenManager(){
    }

    public static boolean initialize() {
        Universe universe = Universe.get();

        // Check if the hub world already exists in memory
        world = universe.getWorld(WORLDNAME);
        if (world == null) {
            world = loadOrCreateWorld(universe);
            return world != null;
        }

        return true;
    }

    @Nullable
    private static World loadOrCreateWorld(Universe universe) {
        if (isWorldOnDisk(universe)) {
            return loadWorldFromDisk(universe);
        } else {
            return createNewWorld(universe);
        }
    }
    private static boolean isWorldOnDisk(Universe universe) {
        return universe.isWorldLoadable(WORLDNAME);
    }

    @Nullable
    private static World loadWorldFromDisk(Universe universe) {

        try {
            return universe.loadWorld(WORLDNAME).join();
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    private static World createNewWorld(Universe universe) {

        try {
            WorldConfig b = new WorldConfig();
            IWorldGenProvider provider = IWorldGenProvider.CODEC.getCodecFor(WORLDGEN).getDefaultValue();

            b.setWorldGenProvider(provider);
            EffectControllerComponent a;

            Path path = universe.getPath().resolve("worlds").resolve(WORLDNAME);
            Files.createDirectories(path);
            try {
                world = universe.makeWorld(WORLDNAME, path, b).join();

                return world;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        } catch (Exception e) {
            return null;
        }
    }


    public static World getWorld() {
        return world;
    }

    public static Collection<PlayerRef> getLivePlayerCount(){
        return world.getPlayerRefs();
    }

}
