package com.mercedes.fileuploader.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
@EnableSwagger2
public class AppConfig {

    @Bean
    public Statement statement() throws SQLException, ClassNotFoundException {
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        return DriverManager.getConnection("",
                        "", "").createStatement();
    }

}
