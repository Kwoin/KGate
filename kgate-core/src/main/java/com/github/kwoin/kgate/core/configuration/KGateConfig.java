package com.github.kwoin.kgate.core.configuration;

import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;


/**
 * @author P. WILLEMET
 */
public class KGateConfig {


    private static final Logger logger = LoggerFactory.getLogger(KGateConfig.class);
    private static final Configurations CONFIG2 = new Configurations();
    private static CompositeConfiguration config = new CompositeConfiguration();


    public static CompositeConfiguration getConfig() {

        if(config == null)
            loadConfiguration();

        return config;

    }


    public static void loadConfiguration() {

        addSystemPropertyConfiguration(config);
        addActiveDirectoryConfiguration(config);
        addClasspathConfiguration(config);
        addDefaultConfigurations(config);

    }


    private static void addSystemPropertyConfiguration(CompositeConfiguration configuration) {

        String kgateConfigurationPath = System.getProperty("kgate.configuration");
        if(kgateConfigurationPath != null && !kgateConfigurationPath.equals("")) {
            try {
                configuration.addConfiguration(CONFIG2.properties(kgateConfigurationPath));
            } catch (ConfigurationException e) {
                logger.error("Cannot load configuration from System Property kgate.configuration value : " + kgateConfigurationPath, e);
            }
        }

    }


    private static void addActiveDirectoryConfiguration(CompositeConfiguration configuration) {

        File f = new File("kgate.properties");
        if(f.isFile()) {
            try {
                configuration.addConfiguration(CONFIG2.properties(f));
            } catch (ConfigurationException e) {
                logger.error("Cannot load configuration from active directory", e);
            }
        }

    }


    private static void addClasspathConfiguration(CompositeConfiguration configuration) {

        URL url = KGateConfig.class.getClassLoader().getResource("kgate.properties");
        if(url != null) {
            try {
                configuration.addConfiguration(CONFIG2.properties(url));
            } catch (ConfigurationException e) {
                logger.error("Cannot load configuration from classpath", e);
            }
        }

    }


    private static void addDefaultConfigurations(CompositeConfiguration configuration) {

        try {
            Enumeration<URL> urls = KGateConfig.class.getClassLoader().getResources("default.properties");
            while (urls.hasMoreElements()) {
                PropertiesConfiguration propertiesConfiguration = CONFIG2.properties(urls.nextElement());
                configuration.addConfiguration(propertiesConfiguration);
            }
        } catch (ConfigurationException | IOException e) {
            logger.error("Cannot load default configuration", e);
        }

    }


}
