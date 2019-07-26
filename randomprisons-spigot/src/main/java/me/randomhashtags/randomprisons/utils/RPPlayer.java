package me.randomhashtags.randomprisons.utils;

import me.randomhashtags.randomprisons.addons.Cell;
import me.randomhashtags.randomprisons.addons.KillTag;
import me.randomhashtags.randomprisons.addons.MiningLevel;
import me.randomhashtags.randomprisons.addons.objects.PlayerOptions;

import java.math.BigDecimal;

public class RPPlayer {
    private boolean isLoaded = false;
    private long playtime;

    private Cell cell;
    private PlayerOptions options;
    private MiningLevel levelcap, mininglevel;
    private BigDecimal blocksMined, raidsCompleted, miningxp;
    private KillTag killtag;

    public boolean isLoaded() { return isLoaded; }
    public void load() {
        if(!isLoaded) {
            isLoaded = true;
        }
    }
    public void unload() {
        if(isLoaded) {
            isLoaded = false;
        }
    }


    public long getPlayTime() { return playtime; }
    public Cell getCell() {
        if(cell == null) {
        }
        return cell;
    }
    public PlayerOptions getOptions() {
        if(options == null) {
        }
        return options;
    }
    public MiningLevel getLevelCap() {
        if(levelcap == null) {
        }
        return levelcap;
    }
    public KillTag getKillTag() {
        if(killtag == null) {
        }
        return killtag;
    }
}
