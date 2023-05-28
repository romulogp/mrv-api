package br.com.okfx.mrv.api;

import br.com.okfx.mrv.api.config.property.ApiProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApiProperty.class)
public class MRVApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MRVApiApplication.class, args);
    }

}
