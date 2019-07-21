package me.randomhashtags.randomprisons.utils.supported;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;

public class WorldGuardAPI {
    private static WorldGuardAPI instance;
    public byte version;
    public static WorldGuardAPI getWorldGuardAPI() {
        if(instance == null) {
            instance = new WorldGuardAPI();
            final Plugin p = Bukkit.getPluginManager().getPlugin("WorldGuard");
            final String version = p != null && p.isEnabled() ? p.getDescription().getVersion() : null;
            instance.version = (byte) (version != null ? version.startsWith("6") ? 6 : 7 : -1);
        }
        return instance;
    }

    public boolean allows(Player player, Location l, com.sk89q.worldguard.protection.flags.StateFlag...flags) {
        return version == -1 || version == 6 ? allows_wg6(player, l, flags) : allows_wg7(player, l, flags);
    }

    public boolean hasBypass(Player player, Location l) {
        final World w = l.getWorld();
        if(version == 6) {
            return com.sk89q.worldguard.bukkit.WorldGuardPlugin.inst().getSessionManager().hasBypass(player, w);
        } else {
            final com.sk89q.worldedit.world.World wew = com.sk89q.worldedit.bukkit.BukkitAdapter.adapt(w);
            final com.sk89q.worldguard.session.SessionManager m = com.sk89q.worldguard.WorldGuard.getInstance().getPlatform().getSessionManager();
            try {
                final Method method = m.getClass().getDeclaredMethod("hasBypass", com.sk89q.worldguard.LocalPlayer.class, com.sk89q.worldedit.world.World.class);
                return (Boolean) method.invoke(m, getLocalPlayer(player), wew);
            } catch(Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }
    private com.sk89q.worldguard.LocalPlayer getLocalPlayer(Player player) {
        return player != null ? com.sk89q.worldguard.bukkit.WorldGuardPlugin.inst().wrapPlayer(player) : null;
    }

    private boolean allows_wg6(Player player, Location l, com.sk89q.worldguard.protection.flags.StateFlag...flags) {
        if(hasBypass(player, l)) return true;
        final com.sk89q.worldguard.LocalPlayer p = player != null ? com.sk89q.worldguard.bukkit.WorldGuardPlugin.inst().wrapPlayer(player) : null;
        final com.sk89q.worldguard.protection.ApplicableRegionSet s = com.sk89q.worldguard.bukkit.WGBukkit.getPlugin().getRegionManager(l.getWorld()).getApplicableRegions(l);
        final com.sk89q.worldguard.protection.flags.StateFlag.State deny = com.sk89q.worldguard.protection.flags.StateFlag.State.DENY;
        for(com.sk89q.worldguard.protection.flags.StateFlag flag : flags) {
            if(s.queryState(p, flag) == deny) {
                return false;
            }
        }
        return true;
    }
    private boolean allows_wg7(Player player, Location l, com.sk89q.worldguard.protection.flags.StateFlag...flags) {
        if(hasBypass(player, l)) return true;
        final com.sk89q.worldguard.protection.regions.RegionContainer c = com.sk89q.worldguard.WorldGuard.getInstance().getPlatform().getRegionContainer();
        final com.sk89q.worldguard.protection.regions.RegionQuery q = c.createQuery();
        final com.sk89q.worldguard.protection.ApplicableRegionSet s = q.getApplicableRegions(com.sk89q.worldedit.bukkit.BukkitAdapter.adapt(l));
        return s.testState(getLocalPlayer(player), flags);
    }
}

