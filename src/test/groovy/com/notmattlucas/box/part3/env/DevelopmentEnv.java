package com.notmattlucas.box.part3.env;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ContainerPort;
import org.jetbrains.annotations.NotNull;
import org.testcontainers.shaded.com.github.dockerjava.core.DefaultDockerClientConfig;
import org.testcontainers.shaded.com.github.dockerjava.core.DockerClientImpl;
import org.testcontainers.shaded.com.github.dockerjava.okhttp.OkDockerHttpClient;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DevelopmentEnv implements Environment {

    private static final String DOCKER_SOCK;

    private static final String PREFIX = "/blog-legacy-in-a-box_";

    private Map<String, Container> containers;

    static {
        String docker = System.getenv("DOCKER_HOST");
        if (docker == null) {
            DOCKER_SOCK = "unix:///var/run/docker.sock";
        } else {
            DOCKER_SOCK = docker;
        }

    }

    public void connect() {
        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .build();
        OkDockerHttpClient http = (new OkDockerHttpClient.Builder())
                .dockerHost(URI.create(DOCKER_SOCK))
                .build();
        DockerClient client = DockerClientImpl.getInstance(config, http);
        containers = client.listContainersCmd().exec()
                .stream()
                .flatMap(container -> Arrays.stream(container.getNames())
                        .map(name -> Map.entry(removePrefix(name), container)))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    @NotNull
    private static String removePrefix(String name) {
        return name.replace(PREFIX, "");
    }

    @Override
    public MySql mysql() {
        Optional<Integer> port = publicPortOf(MYSQL_ID, MYSQL_PORT);
        if (port.isEmpty()) {
            throw new IllegalStateException("Could not find mysql container - is it running?");
        }
        return new MySql("localhost", port.get());
    }

    @Override
    public PetClinic petclinic() {
        Optional<Integer> port = publicPortOf(PETCLINIC_ID, PETCLINIC_PORT);
        if (port.isEmpty()) {
            throw new IllegalStateException("Could not find petclinic container - is it running?");
        }
        return new PetClinic("localhost", port.get());
    }

    private Optional<Integer> publicPortOf(String id, int requiredPort) {
        Container container = containers.get(id);
        for (ContainerPort port: container.getPorts()) {
            if (port.getPrivatePort() != null && port.getPublicPort() != null && port.getPrivatePort().equals(requiredPort)) {
                return Optional.of(port.getPublicPort());
            }
        }
        return Optional.empty();
    }

}
