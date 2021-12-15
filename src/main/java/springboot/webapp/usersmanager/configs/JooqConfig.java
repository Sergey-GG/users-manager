package springboot.webapp.usersmanager.configs;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class JooqConfig {

    @Bean
    public DSLContext dslContext(DataSource operationDataSource) {
        return DSL.using(operationDataSource, SQLDialect.POSTGRES);
    }
}

