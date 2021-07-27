package org.geektimes.configuration.microprofile.config.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.junit.Test;

public class ConfigProviderTest {

    @Test
    public void testConfigProvider() {
        Config config = ConfigProvider.getConfig();
        config.getPropertyNames().forEach(x -> {
            System.out.println(x + ":" + config.getConfigValue(x).getValue());
        });
    }
}
