package com.notmattlucas.box.part3

import com.notmattlucas.box.part3.env.Environment
import groovyx.net.http.RESTClient
import net.sf.json.groovy.JsonSlurper
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

import static groovyx.net.http.ContentType.JSON

@Testcontainers
class OwnerSpec extends Specification {

    @Shared
    Environment environment = Environment.get()

    @Shared
    def petclinic = environment.petclinic()

    @Shared
    def json = new JsonSlurper()

    @Shared
    def client = new RESTClient( "http://${petclinic.getHost()}:${petclinic.getPort()}")

    def "creates a new owner"() {

        when: "I make a request to create a new owner"

            def owner = json.parseText('''{
                "address": "101 Bachmann St",
                "city": "Silent Hill",
                "firstName": "Harry",
                "id": 0,
                "lastName": "Mason",
                "telephone": "01234567890",
                "pets": []
            }''')

            def response = client.post(
                    path: '/petclinic/api/owners',
                    body: owner,
                    requestContentType: JSON)


        then: "I should get a successful response (201)"
            assert response.status == 201


        and: "The petclinic should have 1 active owner"
            def owners = client.get(path: "/petclinic/api/owners")
            assert owners.data.size() == 1

    }

}