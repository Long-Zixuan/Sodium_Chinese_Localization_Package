package me.loongly.mods.sclp.client;

import com.google.common.collect.EvictingQueue;

import me.loongly.mods.sclp.SCLPMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Queue;
import java.util.stream.IntStream;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT, modid = SCLPMod.MOD_ID)
public class ClientTickHandler {

    @SubscribeEvent
    public static void onTick(final TickEvent.ClientTickEvent event)
    {

    }

}
