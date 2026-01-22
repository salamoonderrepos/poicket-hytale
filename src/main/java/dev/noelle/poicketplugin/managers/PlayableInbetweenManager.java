package dev.noelle.poicketplugin.managers;

import com.hypixel.hytale.builtin.hytalegenerator.plugin.HytaleGenerator;
import com.hypixel.hytale.common.plugin.PluginIdentifier;
import com.hypixel.hytale.server.core.plugin.PluginManager;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;

import javax.annotation.Nullable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public class PlayableInbetweenManager {
    private final static String WORLDNAME = "Inbetween";
    private final static String WORLDGEN = "InbetweenWorldGen";
    private static World world;
    private static HytaleGenerator hytaleGen;

    public PlayableInbetweenManager(World w){
        world = w;
    }
    public PlayableInbetweenManager(){
    }
    public static HytaleGenerator getHytaleGen(){
        return hytaleGen;
    }

    public static boolean initialize() {
        hytaleGen = (HytaleGenerator) PluginManager.get().getPlugin(new PluginIdentifier("Hytale", "HytaleGenerator"));
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
    public static void resetWorld(Universe universe){
        universe.removeWorld("Inbetween");
        initialize();
    }
    @Nullable
    private static World createNewWorld(Universe universe) {

        try {
            Path path = universe.getPath().resolve("worlds").resolve(WORLDNAME);
            Files.createDirectories(path);
            try {
                //world = universe.makeWorld(WORLDNAME, path, b).join();
                return universe.addWorld("Inbetween", "InbetweenWorldGen", null).join();

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
