package com.hospital.mediflow.Common.Config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.hospital.mediflow",
        entityManagerFactoryRef = "appEntityManagerFactory",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.REGEX,
                pattern = "com\\.hospital\\.mediflow\\.Audit\\..*"
        ),
        transactionManagerRef = "appTransactionManager"
)
public class AppDataSourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties appDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource appDataSource() {
        return appDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean appEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("appDataSource") DataSource dataSource) {

        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.hbm2ddl.auto", "validate");

        return builder
                .dataSource(dataSource)
                .packages(
                        "com.hospital.mediflow.Appointment",
                        "com.hospital.mediflow.Billing",
                        "com.hospital.mediflow.Department",
                        "com.hospital.mediflow.Doctor",
                        "com.hospital.mediflow.DoctorDepartments",
                        "com.hospital.mediflow.MedicalRecords",
                        "com.hospital.mediflow.Patient",
                        "com.hospital.mediflow.Specialty",
                        "com.hospital.mediflow.Security"
                )
                .persistenceUnit("app")
                .properties(props)
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager appTransactionManager(
            @Qualifier("appEntityManagerFactory")
            EntityManagerFactory emf) {

        return new JpaTransactionManager(emf);
    }
}