package org.example.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GrpcServer {
  private static Logger logger = Logger.getLogger(GrpcServer.class.getName());

  private final int port;
  private final Server server;

  public GrpcServer() {
    port = 9000;
    server = ServerBuilder
      .forPort(port)
      .addService(new org.example.grpc.todo.Service())
      .build();
  }

  public void start() throws IOException, InterruptedException {
    logger.info(String.format("GrpcServer starting on port %d...", port));
    server.start();
    logger.info(String.format("GrpcServer started.", port));
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        System.err.println("*** Shutting down server... ***");

        try {
          GrpcServer.this.stop();
        }
        catch (InterruptedException e) {
          System.err.println(e.getStackTrace());
        }
        System.err.println("*** Server shutdown");
      }
    });
    server.awaitTermination();
  }

  public void stop() throws InterruptedException {
    if (server != null) {
      server.awaitTermination(30, TimeUnit.SECONDS);
    }
  }
}
