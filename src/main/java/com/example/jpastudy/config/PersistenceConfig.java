package study.jpa.tsoh.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class PersistenceConfig {
    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");  // h2 DataBase 설정

        ds.addDataSourceProperty("serverName", env.getProperty("database.server-name"));
        ds.addDataSourceProperty("databaseName", env.getProperty("database.database-name"));
        ds.addDataSourceProperty("user", env.getProperty("database.user"));
        ds.addDataSourceProperty("password", env.getProperty("database.password"));

        return ds;
    }

    @Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
        builder.scanPackages("com.example.jpastudy");
        builder.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

        builder.setProperty("hibernate.show_sql", "true");
        builder.setProperty("hibernate.format_sql", "true");

        return builder.buildSessionFactory();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactory());
    }
}
