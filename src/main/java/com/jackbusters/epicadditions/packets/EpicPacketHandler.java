package com.jackbusters.epicadditions.packets;

import com.jackbusters.epicadditions.EpicAdditions;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class EpicPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry
            .newSimpleChannel(new ResourceLocation(EpicAdditions.MOD_ID, "main"), () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);

    public static void registerPackets() {
        int id = 0;
        INSTANCE.registerMessage(id++, S2CSyncPocketData.class, S2CSyncPocketData::encode, S2CSyncPocketData::new, S2CSyncPocketData::handle);
    }

//    public static void sendToServer(Object information){
//        INSTANCE.send(PacketDistributor.SERVER.noArg(), information);
//    }
//
//    public static void sendToAllClients(Object information){
//        INSTANCE.send(PacketDistributor.ALL.noArg(), information);
//    }
//
//    public static void sendToPlayer(Object information, ServerPlayer serverPlayer){
//        INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), information);
//    }
}
