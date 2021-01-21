package com.liquidforte.terra.server;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class TerraServer extends Application<TerraConfiguration> {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            new TerraServer().run("server", "terra.yml");
        } else {
            new TerraServer().run(args);
        }
    }

    @Override
    public void run(TerraConfiguration configuration, Environment environment) throws Exception {
        
    }
}
