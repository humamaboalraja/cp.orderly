import com.github.davidmc24.gradle.plugin.avro.GenerateAvroJavaTask
import org.apache.avro.generic.GenericData.StringType
val avroVersion: String by project

plugins {
    id("kotlin-tests-conventions")
    id("spring-conventions")
    id("com.github.davidmc24.gradle.plugin.avro") version "1.2.0"
}

dependencies {
    implementation("org.apache.avro:avro:$avroVersion")
}

avro {
    setEnableDecimalLogicalType(true)
    setStringType(StringType.String)
}

val generateAvro = tasks.register<GenerateAvroJavaTask>("generateAvro") {
    source("src/main/resources/avro-schema")
    setOutputDir(file("src/main/java"))
}

tasks.getByName("build") {
    finalizedBy(generateAvro)
}
