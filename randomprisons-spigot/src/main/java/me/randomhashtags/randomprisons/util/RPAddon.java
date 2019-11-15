package me.randomhashtags.randomprisons.util;

import me.randomhashtags.randomprisons.RandomPrisonsAPI;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Random;

public abstract class RPAddon extends RPStorage {
    protected static RandomPrisonsAPI api = RandomPrisonsAPI.api;
    protected Random random = new Random();

    protected File file;
    protected YamlConfiguration yml;
    public void load(File file) {
        if(file.exists()) {
            this.file = file;
            yml = YamlConfiguration.loadConfiguration(file);
        }
    }
    public File getFile() { return file; }
    public YamlConfiguration getYaml() { return yml; }
    public String getYamlName() { return file.getName().split("\\.yml")[0]; }
}
