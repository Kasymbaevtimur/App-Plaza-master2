package peaksoft.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

/**
 * @Configuration: Эта аннотация говорит Spring, что этот класс предоставляет конфигурацию бинов в контексте приложения.
 * Методы, помеченные @Bean, возвращают объекты, которые будут управляться Spring.
 */
@Configuration
/**
 * @EnableTransactionManagement(proxyTargetClass = true): Эта аннотация включает управление транзакциями в Spring.
 * proxyTargetClass = true указывает использовать CGLIB-прокси для управления транзакциями.
 */
@EnableTransactionManagement(proxyTargetClass = true)
/**
 * @PropertySource("classpath:application.properties"): Эта аннотация указывает местоположение файла свойств,
 * где содержатся настройки приложения. В данном случае, файл свойств называется application.properties.
 */
@PropertySource("classpath:application.properties")
public class JpaConfig {

    /**
     * Environment: Это интерфейс в Spring, предоставляющий методы для доступа к свойствам среды выполнения приложения.
     * Он может быть использован для чтения значений из файла свойств,
     * переменных среды, системных свойств и других источников конфигурации.
     */
    private final Environment environment;

    /**
     * @Autowired: Эта аннотация используется для автоматического внедрения зависимости.
     * Здесь она внедряет Environment в конструктор класса.
     */
    @Autowired
    public JpaConfig(Environment environment) {
        this.environment = environment;
    }

    /**
     * Этот метод создает и настраивает
     * LocalContainerEntityManagerFactoryBean. Этот бин отвечает за создание фабрики EntityManager,
     * которая в свою очередь управляет взаимодействием с базой данных через JPA.
     * Метод устанавливает различные параметры, такие как источник данных, адаптер JPA (в данном случае, Hibernate),
     * именование персистентности, и пакеты, которые должны быть отсканированы для поиска сущностей.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean managerFactoryBean() {
        LocalContainerEntityManagerFactoryBean local = new LocalContainerEntityManagerFactoryBean();
        local.setPackagesToScan("peaksoft.model");
        local.setJpaVendorAdapter(jpaVendorAdapter());
        local.setDataSource(dataSource());
        local.setJpaProperties(properties());
        return local;
    }

    /**
     * Этот метод создает и возвращает адаптер JPA, конкретно HibernateJpaVendorAdapter.
     * Адаптер JPA предоставляет информацию Hibernate для настройки JPA.
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    /**
     * dataSource(): Здесь создается и настраивается источник данных с использованием Apache Commons DBCP2.
     * Метод использует свойства, которые загружаются из файла application.properties,
     * чтобы определить параметры подключения к базе данных, такие как драйвер, URL, имя пользователя и пароль.
     */
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("db.driverClassName"));
        dataSource.setUrl(environment.getProperty("db.url"));
        dataSource.setUsername(environment.getProperty("db.username"));
        dataSource.setPassword(environment.getProperty("db.password"));
        return dataSource;
    }

    /**
     * properties(): Этот метод создает объект Properties для хранения свойств Hibernate.
     * В данном случае, устанавливаются различные параметры Hibernate,
     * такие как диалект базы данных, отображение SQL, форматирование SQL и автоматическое создание таблиц.
     */
    @Bean
    public Properties properties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getProperty("hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
        return properties;
    }

    /**
     * platformTransactionManager(): Этот метод создает и возвращает менеджер транзакций для управления транзакциями в приложении.
     * Он использует JpaTransactionManager и требует объект LocalContainerEntityManagerFactoryBean в качестве зависимости.
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(Objects.requireNonNull(managerFactoryBean().getObject()));
        ё
    }

}
