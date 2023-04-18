import com.github.davidmc24.gradle.plugin.avro.GenerateAvroJavaTask
import com.github.davidmc24.gradle.plugin.avro.ResolveAvroDependenciesTask
import org.apache.avro.generic.GenericData.StringType

val avroVersion: String by project

plugins {
    id("kotlin-tests-conventions")
    id("java-conventions")
    id("spring-conventions")

    id("com.github.davidmc24.gradle.plugin.avro") version "1.1.0"
}

dependencies {
    implementation("org.apache.avro:avro:$avroVersion")
}

avro {
    setEnableDecimalLogicalType(true)
    setStringType(StringType.String)
    setFieldVisibility("PUBLIC")
}

val generateAvro = tasks.register<GenerateAvroJavaTask>("generateAvro") {
    setOutputDir(file("src/main/java"))
    source("src/main/resources/avro-schema")
}

val generateAvroResolvedDependencies = tasks.register<ResolveAvroDependenciesTask>("resolveAvroDependencies") {
    source("src/main/resources/avro-schema")
    setOutputDir(file("src/main/java"))
}

tasks.getByName("build") {
    dependsOn(generateAvro)
}
