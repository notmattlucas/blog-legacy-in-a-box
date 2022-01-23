package com.notmattlucas.box.part2b

import com.notmattlucas.box.part2b.env.Environment
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
class TodoSpec extends Specification {

    @Shared
    Environment environment = Environment.get()

    def "connects to container"() {

        when: "I request details about the started containers"
        def petclinic = environment.petclinic()
        def mysql = environment.mysql()

        then: "I see on which host/port each service is running"
        println("Petclinic => ${petclinic.getHost()}:${petclinic.getPort()}")
        println("MySQL => ${mysql.getHost()}:${mysql.getPort()}")

    }

}