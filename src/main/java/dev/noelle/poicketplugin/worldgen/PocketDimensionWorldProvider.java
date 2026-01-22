package dev.noelle.poicketplugin.worldgen;

import com.hypixel.hytale.builtin.hytalegenerator.chunkgenerator.ChunkRequest;
import com.hypixel.hytale.builtin.hytalegenerator.plugin.Handle;
import com.hypixel.hytale.builtin.hytalegenerator.plugin.HytaleGenerator;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.common.plugin.PluginIdentifier;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.server.core.plugin.PluginManager;
import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGen;
import com.hypixel.hytale.server.core.universe.world.worldgen.WorldGenLoadException;
import com.hypixel.hytale.server.core.universe.world.worldgen.provider.IWorldGenProvider;

public class PocketDimensionWorldProvider implements IWorldGenProvider {
    public static final BuilderCodec<PocketDimensionWorldProvider> CODEC = BuilderCodec.builder(PocketDimensionWorldProvider.class, PocketDimensionWorldProvider::new)
            .build();
    @Override
    public IWorldGen getGenerator() throws WorldGenLoadException {


        return new Handle(
                (HytaleGenerator) PluginManager.get().getPlugin(new PluginIdentifier("Hytale", "HytaleGenerator")),
                new ChunkRequest.GeneratorProfile(
                        "InbetweenWorldStructureAsset",
                        new Transform(0,300,0),0));
    }
}
