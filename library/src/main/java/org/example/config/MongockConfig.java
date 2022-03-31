package org.example.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import io.mongock.driver.api.driver.ConnectionDriver;
import io.mongock.driver.mongodb.reactive.driver.MongoReactiveDriver;
import io.mongock.runner.springboot.EnableMongock;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Configuration
@EnableMongock
public class MongockConfig {
    private final String connectionString;
    private final String database;

    public MongockConfig(@Value("${spring.data.mongodb.uri}") String connectionString,
                         @Value("${spring.data.mongodb.database}") String database) {
        this.connectionString = connectionString;
        this.database = database;
    }

//    @Bean
//    public MongockInitializingBeanRunner getBuilder(MongoClient reactiveMongoClient,
//                                                    ApplicationContext context) {
//        return MongockSpringboot.builder()
//                .setDriver(MongoReactiveDriver.withDefaultLock(reactiveMongoClient, database))
//                .addMigrationScanPackage(migrationScanPackage)
//                .setSpringContext(context)
//                .setTransactionEnabled(transactionEnabled)
//                .buildInitializingBeanRunner();
//    }

    @Bean
    public ConnectionDriver connectionDriver(MongoClient mongoClient) {
        return MongoReactiveDriver.withDefaultLock(mongoClient, database);
    }

    @Bean
    MongoClient mongoClient() {
        CodecRegistry codecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        return MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .codecRegistry(codecRegistry)
                .build());
    }
}
