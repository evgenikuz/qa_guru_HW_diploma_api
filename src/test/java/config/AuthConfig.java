package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:auth.properties"
})
public interface AuthConfig extends Config {

    @Key("selenoid.username")
    String getSelenoidUsername();

    @Key("selenoid.password")
    String getSelenoidPassword();

}