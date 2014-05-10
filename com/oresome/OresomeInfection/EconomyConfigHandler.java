package com.oresome.OresomeInfection;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class EconomyConfigHandler
{
  private FileConfiguration econConfig = null;
  private Infection plugin;
  private File econFile = null;
  public static Map<String, String> userMap = new HashMap();

  public void reloadUserConfig() { if (!configExists()) {
      if (this.econFile == null) this.econFile = new File(this.plugin.getPlugin().getDataFolder(), "config.yml");
      this.econConfig = YamlConfiguration.loadConfiguration(this.plugin.getPlugin().getResource("config.yml"));
      saveUserConfig();
    }
    if (this.econFile == null) this.econFile = new File(this.plugin.getPlugin().getDataFolder(), "config.yml");
    this.econConfig = YamlConfiguration.loadConfiguration(this.econFile);
    InputStream defaultUserStream = this.plugin.getPlugin().getResource("config.yml");
    if (defaultUserStream != null) {
      YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(defaultUserStream);
      this.econConfig.setDefaults(defaultConfig);
    } }

  public FileConfiguration getUserConfig()
  {
    if (this.econConfig == null) reloadUserConfig();
    return this.econConfig;
  }

  public void saveUserConfig() {
    if ((this.econConfig == null) || (this.econFile == null)) return; try
    {
      getUserConfig().save(this.econFile);
    } catch (IOException e) {
      this.plugin.getPlugin().getLogger().log(Level.SEVERE, "Could not save users to " + this.econConfig, e);
    }
  }

  public void reloadUserMap() {
    Set Users = this.econConfig.getKeys(false);
    for (String User : Users)
      userMap.put(User, this.econConfig.getString(User));
  }

  private boolean configExists()
  {
    return new File(this.plugin.getPlugin().getDataFolder(), "config.yml").exists();
  }
}