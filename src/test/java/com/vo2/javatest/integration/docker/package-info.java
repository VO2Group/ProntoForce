/**
 * Created by VO2 on 24/01/2017.
 * You find here an example of docker tests.
 * In order to require running docker machine(s), the class of test must end with DockerIT (docker + IT = Integration Test)
 * To launch only Docker tests :
 *       mvnw clean verify -Docker
 * To launch only UT + IT that not require Docker (not ending with DockerIT) :
 *       mvnw clean verify
 * To launch only integration tests :
 *       mvnw clean verify -Dsurefire.skip=true
 */
package com.vo2.javatest.integration.docker;