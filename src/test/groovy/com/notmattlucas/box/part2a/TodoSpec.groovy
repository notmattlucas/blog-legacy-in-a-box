package com.notmattlucas.box.part2a


import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
class TodoSpec extends Specification {

    @Shared
    DockerComposeContainer environment = new DockerComposeContainer(new File("./docker-compose.yml"))
            // waits for mapped port on 9966 to become available
            .withExposedService("petclinic_1", 9966)
            // waits for mapped port on 3306 to become available
            .withExposedService("mysql_1", 3306)

    def "connects to container"() {

        when: "I request details about the started containers"
        def petclinic = environment.getContainerByServiceName("petclinic_1").get()
        def mysql = environment.getContainerByServiceName("mysql_1").get()

        then: "I see on which host/port each service is running"
        println("Petclinic => ${petclinic.getHost()}:${petclinic.getMappedPort(9966)}")
        println("MySQL => ${mysql.getHost()}:${mysql.getMappedPort(3306)}")

    }

}