package org.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import org.example.grpc.GrpcServer;
import org.example.grpc.GrpcClient;
import org.example.grpc.todo.TodoModel;

public class App 
{
    public static void main( String[] args ) throws IOException, InterruptedException
    {
      if (args.length > 0) {
        if("--server".equals(args[0])) {
          int port = Integer.parseInt(args[1]); // Assuming it will always be passed and valid
          GrpcServer server = new GrpcServer(port);
          server.start();
        }

        if ("--client".equals(args[0])) {
          String target = args[1];
          ManagedChannel channel = ManagedChannelBuilder
            .forTarget(target)
            .usePlaintext()
            .build();
          try {
            GrpcClient client = new GrpcClient(channel);

            System.out.println("List of todos:");
            List<TodoModel> todos = client.getAll(1, 10);
            for (TodoModel todo: todos) {
              System.out.println(todo.toString());
            }

            System.out.println("Adding new todo item");
            TodoModel todo = client.createTodo("Commit this file", "Remember");
            System.out.printf("Create todo. Id: %s\n", todo.getId());

            System.out.println("List of todos:");
            todos = client.getAll(1, 10);
            for (TodoModel item: todos) {
              System.out.println(item.toString());
            }
          }
          finally {
            System.out.println("*** Closing connection to server...");
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
          }
        }
      }
    }
}
