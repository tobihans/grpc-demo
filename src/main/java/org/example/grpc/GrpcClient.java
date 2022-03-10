package org.example.todo;

import java.util.logging.Logger;

public class GrpcClient {
  private static Logger logger = Logger.getLogger(GrpcClient.class.getName());


  private final String host;
  private final int port;
  public GrpcClient() {
    host = "0.0.0.0";
    port = 9000;
  }
}

