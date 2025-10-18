package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:user.config"
})

public interface UserConfig extends Config {

    @Key("username")
    String getUsername();

    @Key("password")
    String getPassword();
}
