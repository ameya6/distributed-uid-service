package org.duid.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@Log4j2
public class TimescaleConfig {

    @Value("${datasource.driver}")
    private String driver;

    @Value("${datasource.url}")
    private String url;

    @Value("${datasource.username}")
    private String username;

    @Value("${datasource.password}")
    private String password;

    @Value("${datasource.max-pool-size}")
    private Integer maxPoolSize;

    @Value("${datasource.connection-test-query}")
    private String connectionTestQuery;

    @Value("${datasource.pool-name}")
    private String poolName;


    /*@Bean(name="entityManagerFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("org.duid.model");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        log.info("Config : {}, {}, {}, {}, {}, {}, {}", driver, url, username,
                password, maxPoolSize, connectionTestQuery, poolName);
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setDriverClassName(driver);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);

        hikariConfig.setMaximumPoolSize(maxPoolSize);
        hikariConfig.setConnectionTestQuery(connectionTestQuery);
        hikariConfig.setPoolName(poolName);

        return new HikariDataSource(hikariConfig);
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "none");
        return hibernateProperties;
    }*/
}
