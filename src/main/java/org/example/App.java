package org.example;

import java.io.IOException;
import org.example.grpc.GrpcServer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, InterruptedException
    {
      GrpcServer server = new GrpcServer();
      server.start();
    }
}
