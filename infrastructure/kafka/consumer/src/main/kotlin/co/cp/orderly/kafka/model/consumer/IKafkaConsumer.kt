package co.cp.orderly.kafka.model.consumer

import org.apache.avro.specific.SpecificRecordBase

interface IKafkaConsumer<T : SpecificRecordBase> {
    fun receive(messages: List<T>, keys: List<String>, partitions: List<Int>, offsets: List<Long>)
}
