package web.config;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import web.model.User;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@Configuration
@EnableJpaRepositories("web")
@PropertySource("classpath:db.properties")
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(value = "web")
public class AppHibernateConfig {

   @Resource
   private Environment env;

   @Bean
   public DataSource getDataSource() {
      BasicDataSource ds = new BasicDataSource();
      ds.setUrl(env.getRequiredProperty("db.url"));
      ds.setDriverClassName(env.getRequiredProperty("db.driver"));
      ds.setUsername(env.getRequiredProperty("db.username"));
      ds.setPassword(env.getRequiredProperty("db.password"));

      ds.setInitialSize(Integer.valueOf(env.getRequiredProperty("db.initialSize")));
      ds.setMinIdle(Integer.valueOf(env.getRequiredProperty("db.minIdle")));
      ds.setMaxIdle(Integer.valueOf(env.getRequiredProperty("db.maxIdle")));
      ds.setTimeBetweenEvictionRunsMillis(Long.valueOf(env
              .getRequiredProperty("db.timeBetweenEvictionRunsMillis")));
      ds.setMinEvictableIdleTimeMillis(Long.valueOf(env.getRequiredProperty("db.minEvictableIdleTimeMillis")));
      ds.setTestOnBorrow(Boolean.valueOf(env.getRequiredProperty("db.testOnBorrow")));
      ds.setValidationQuery(env.getRequiredProperty("db.validationQuery"));
      return ds;
   }

   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactory () {
      LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(getDataSource());
      em.setPackagesToScan(env.getRequiredProperty("db.packagesToScan"));
      em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
      em.setJpaProperties(getHibenateProperties());
      return em;
   }
@Bean
public PlatformTransactionManager platformTransactionManager () {
   JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
   jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
   return jpaTransactionManager;
}
   private Properties getHibenateProperties() {
      try {
      Properties properties = new Properties();
      InputStream is = getClass().getClassLoader().getResourceAsStream("hibernate.properties");
         properties.load(is);
         return properties;
      } catch (IOException e) {
         throw new IllegalArgumentException("Can't find hibernate.properties", e);
      }

   }
}
