package alten.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import alten.core.ApplicationProperties;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;
import java.time.Instant;

@SpringBootApplication(scanBasePackages = {
        "alten.api",
        "alten.core",
        "alten.datasource"
})

@EnableConfigurationProperties({LiquibaseProperties.class, ApplicationProperties.class})
@EntityScan(basePackages = "alten.datasource.entities")
@EnableJpaRepositories(basePackages = "alten.datasource.repositories")
public class AltenApp {

    public static void main(String[] args) {
        SpringApplication.run(AltenApp.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        javaTimeModule.addDeserializer(Instant.class, new JsonDeserializer<>() {
            @Override
            public Instant deserialize(JsonParser p, DeserializationContext context) throws IOException {
                return Instant.ofEpochMilli(p.getLongValue());
            }
        });

        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .registerModules(
                        new ParameterNamesModule(),
                        new Jdk8Module(),
                        javaTimeModule
                );
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
