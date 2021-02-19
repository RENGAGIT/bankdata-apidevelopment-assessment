package rivers.bankdata.demo.bankdataaccountprofilews.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import rivers.bankdata.demo.bankdataaccountprofilews.models.Balance;
import rivers.bankdata.demo.bankdataaccountprofilews.models.Transaction;
import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    private String bootstrapAddress = "127.0.0.1:9092";
    private String groupId = "bank-transactions-grp";


    @Bean
    public ConsumerFactory<String, Transaction>  transactionConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapAddress);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<String, Transaction>(config, new StringDeserializer(), new JsonDeserializer<>(Transaction.class
        ));
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Transaction> kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, Transaction> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(transactionConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Balance>  balanceConsumerFactory() {
        Map<String, Object> balConfig = new HashMap<>();
        balConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapAddress);
        balConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "bank-balance-grp");
        balConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        balConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<String, Balance>(balConfig, new StringDeserializer(),
                new JsonDeserializer<>(Balance.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Balance> balanceListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, Balance> factory = new
                ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(balanceConsumerFactory());
        return factory;
    }

}
