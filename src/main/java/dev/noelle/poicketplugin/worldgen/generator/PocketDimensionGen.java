package dev.noelle.poicketplugin.worldgen.generator;

import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedChunk;
import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGen;
import com.hypixel.hytale.server.core.universe.world.worldgen.WorldGenTimingsCollector;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.LongPredicate;

public class PocketDimensionGen implements IWorldGen {
    @Override
    public @Nullable WorldGenTimingsCollector getTimings() {
        return null;
    }

    @Override
    public CompletableFuture<GeneratedChunk> generate(int var1, long var2, int var4, int var5, LongPredicate var6) {
        return null;
    }

    @Override
    public Transform[] getSpawnPoints(int var1) {
        return new Transform[0];
    }
}
