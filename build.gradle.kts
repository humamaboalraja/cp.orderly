plugins {
    // Quality metrics using SonarQube
    id("org.sonarqube") version "3.5.0.2730"
    id("jacoco")

}

 sonarqube {
     properties {
         property("sonar.projectKey", "humamaboalraja_cp.orderly")
         property("sonar.organization", "humamaboalraja")
         property("sonar.host.url", "https://sonarcloud.io")
     }
 }


