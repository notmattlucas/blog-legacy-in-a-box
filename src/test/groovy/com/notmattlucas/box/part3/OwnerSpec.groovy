package com.notmattlucas.box.part3

import com.notmattlucas.box.part3.env.Environment
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
class OwnerSpec extends Specification {

    @Shared
    Environment environment = Environment.get()

    def "creates a new owner"() {

        when: "I make a request to create a new owner"
        def petclinic = environment.petclinic()


        then: "I see on which host/port each service is running"
        println("Petclinic => ${petclinic.getHost()}:${petclinic.getPort()}")
        println("MySQL => ${mysql.getHost()}:${mysql.getPort()}")

    }

}