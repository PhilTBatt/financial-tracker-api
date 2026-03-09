package io.github.philtbatt.financialtrackerapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;

@Configuration
public class BedrockConfig {
    @Bean
    public BedrockRuntimeClient bedrockClient () {
        return BedrockRuntimeClient.builder()
                .region(Region.EU_WEST_1)
                .build();
    }
}
