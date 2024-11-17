package com.jackbusters.epicadditions.packets;

import com.jackbusters.epicadditions.capabilities.pocketcells.PocketCellProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CSyncPocketData {
    public final int POCKET_CELL_LEVEL;

    public S2CSyncPocketData(int POCKET_CELL_LEVEL){
        this.POCKET_CELL_LEVEL = POCKET_CELL_LEVEL;
    }

    public S2CSyncPocketData(FriendlyByteBuf buffer){
        this(buffer.readInt());
    }

    public void encode(FriendlyByteBuf buffer){
        buffer.writeInt(this.POCKET_CELL_LEVEL);
    }

    public static void handle(Object msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                // Make sure it's only executed on the physical client
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlerClass.handlePacket(msg))
        );
        ctx.get().setPacketHandled(true);
    }
}

/**
 * Per <a href=https://docs.minecraftforge.net/en/1.20.1/networking/simpleimpl/>Forge Documentation</a>, packets sent from server-to-client should be executed in another class.
 */
class ClientPacketHandlerClass {
    public static void handlePacket(Object msg) {
        LocalPlayer player = Minecraft.getInstance().player;

        if(player != null && msg instanceof S2CSyncPocketData s2CSyncPocketData){
            player.getCapability(PocketCellProvider.POCKET_CELL_DATA).ifPresent(data -> data.setPocketCellLevel(s2CSyncPocketData.POCKET_CELL_LEVEL));
        }
    }
}
