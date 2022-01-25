package com.notmattlucas.box.part4.env;

public abstract class Svc {

    private final String host;

    private final int port;

    public Svc(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

}
