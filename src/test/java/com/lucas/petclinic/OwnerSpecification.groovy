package com.lucas.petclinic

import org.junit.ClassRule
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.containers.wait.strategy.WaitStrategy
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

@Testcontainers
class OwnerSpecification extends Specification {

    @ClassRule
    public DockerComposeContainer environment = new DockerComposeContainer(new File("./docker-compose.yml"))
        .withExposedService("petclinic", 9966);

    def "creates an owner"() {

        when: "an owner is created"
        environment.getContainerByServiceName("petclinic").get().getMappedPort(9966)

        then: "that owner can be seen in the list of owners"

    }

}