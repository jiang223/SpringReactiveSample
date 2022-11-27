package webflux.config;


import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

/**
 * @author HelloWood
 */
@Configuration
@Deprecated
public class DatabaseConfig extends AbstractR2dbcConfiguration {

    @Value("${spring.datasource.host}")
    private String host;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.port}")
    private Integer port;

    @Value("${spring.datasource.database}")
    private String database;

    @Override
    public ConnectionFactory connectionFactory() {
        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(DRIVER, "mysql222")
                .option(HOST, "ubuntu.wsl")
                .option(USER, "root")
                .option(PORT, 3306)  // optional, default 3306
                .option(PASSWORD, "database-password-in-here") // optional, default null, null means has no password
                .option(DATABASE, "r2dbc") // optional, default null, null means not specifying the database
                .build();
        ConnectionFactory connectionFactory = ConnectionFactories.get(options);
        return  connectionFactory;

    }
}
