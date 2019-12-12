package com.clansoft.ingredient.database;

import com.zaxxer.hikari.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
	
  Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

  @Value("${spring.datasource.url}")
  private String dbUrl;
  
  @Value("${spring.datasource.username}")
  private String username;
  
  @Value("${spring.datasource.password}")
  private String password;

  @Bean
  public DataSource dataSource() {
      HikariConfig config = new HikariConfig();
      config.setJdbcUrl(dbUrl);
      logger.info("datasource url : {}", dbUrl);
      config.setUsername(username);
      logger.info("datasource user name : {}", username);
      config.setPassword(password);
      logger.info("datasource password : {}", password);
      return new HikariDataSource(config);
  }
}