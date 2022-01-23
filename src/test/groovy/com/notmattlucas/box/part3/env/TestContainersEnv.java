package com.notmattlucas.box.part3.env;

import org.testcontainers.containers.DockerComposeContainer;

import java.io.File;

class TestContainersEnv implements Environment {

    private DockerComposeContainer environment = new DockerComposeContainer(new File("./docker-compose.yml"))
            // waits for mapped port on 9966 to become available
            .withExposedService(PETCLINIC_ID, 9966)
            // waits for mapped port on 3306 to become available
            .withExposedService(MYSQL_ID, 3306);

    public void start() {
        environment.start();
    }

    @Override
    public MySql mysql() {
        String host = environment.getServiceHost(MYSQL_ID, MYSQL_PORT);
        int port = environment.getServicePort(MYSQL_ID, MYSQL_PORT);
        return new MySql(host, port);
    }

    @Override
    public PetClinic petclinic() {
        String host = environment.getServiceHost(PETCLINIC_ID, PETCLINIC_PORT);
        int port = environment.getServicePort(PETCLINIC_ID, PETCLINIC_PORT);
        return new PetClinic(host, port);
    }

}
