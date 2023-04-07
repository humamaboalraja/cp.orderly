import com.github.davidmc24.gradle.plugin.avro.GenerateAvroJavaTask
import com.github.davidmc24.gradle.plugin.avro.ResolveAvroDependenciesTask
import org.apache.avro.generic.GenericData.StringType

val avroVersion: String by project

plugins {
    id("kotlin-tests-conventions")
    id("spring-conventions")
    id("com.github.davidmc24.gradle.plugin.avro-base") version "1.6.0"
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

val generateAvroResolvedDependencies = tasks.register<ResolveAvroDependenciesTask>("resolveAvroDependencies") {
    source("src/main/resources/avro-schema")
    setOutputDir(file("src/main/java"))
}

tasks.getByName("build") {
    dependsOn(generateAvro)
}
