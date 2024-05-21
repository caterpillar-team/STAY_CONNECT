// package com.caterpillars.StayConnect.configuration;

// import java.util.HashMap;
// import java.util.Map;

// import javax.sql.DataSource;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
// import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

// @Configuration
// public class JpaConfig {

// @Bean
// public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource
// dataSource) {
// LocalContainerEntityManagerFactoryBean factoryBean = new
// LocalContainerEntityManagerFactoryBean();
// factoryBean.setDataSource(dataSource);
// factoryBean.setPackagesToScan("com.caterpillars.StayConnect.model");

// HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
// factoryBean.setJpaVendorAdapter(vendorAdapter);

// Map<String, Object> properties = new HashMap<>();
// properties.put("hibernate.hbm2ddl.auto", "create");
// factoryBean.setJpaPropertyMap(properties);

// factoryBean.setPersistenceUnitPostProcessors(persistenceUnit -> {
// persistenceUnit.getManagedClassNames()
// .removeIf(className ->
// className.equals("com.caterpillars.StayConnect.model.Accommodation"));
// });

// return factoryBean;
// }
// }
