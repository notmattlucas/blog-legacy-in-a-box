package com.notmattlucas.box.part4.env;

import org.jetbrains.annotations.NotNull;

public interface Environment {

    String PETCLINIC_ID = "petclinic_1";

    String MYSQL_ID = "mysql_1";

    int MYSQL_PORT = 3306;

    int PETCLINIC_PORT = 9966;

    static Environment get() {
        if (System.getenv("DEVELOPMENT") != null) {
            return development();
        }
        return standard();
    }

    @NotNull
    private static Environment standard() {
        TestContainersEnv testContainersEnv = new TestContainersEnv();
        testContainersEnv.start();
        return testContainersEnv;
    }

    @NotNull
    private static Environment development() {
        DevelopmentEnv developmentEnv = new DevelopmentEnv();
        developmentEnv.connect();
        return developmentEnv;
    }

    MySqlSvc mysql();

    PetClinic petclinic();

    class PetClinic extends Svc {
        public PetClinic(String host, int port) {
            super(host, port);
        }
    }

}
