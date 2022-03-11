package org.example.grpc;

import io.grpc.Channel;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.example.grpc.todo.TodoServiceGrpc;
import org.example.grpc.todo.TodoModel;
import org.example.grpc.todo.TodoCreate;
import org.example.grpc.todo.TodoCreateResponse;
import org.example.grpc.todo.TodosGet;
import org.example.grpc.todo.TodosGetResponse;
import org.example.grpc.todo.TodoServiceGrpc.TodoServiceStub;
import org.example.grpc.todo.TodoServiceGrpc.TodoServiceBlockingStub;

public class GrpcClient {
  private static Logger logger = Logger.getLogger(GrpcClient.class.getName());

  private final TodoServiceBlockingStub todoBlockingStub;
  private final TodoServiceStub todoAsyncStub;

  public GrpcClient(Channel channel) {
   todoBlockingStub = TodoServiceGrpc.newBlockingStub(channel);
   todoAsyncStub = TodoServiceGrpc.newStub(channel);
  }
  
  public TodoModel createTodo(String title, String desc) {
    TodoCreate req = TodoCreate
      .newBuilder()
      .setTitle(title)
      .setDescription(desc)
      .build();

    TodoCreateResponse res = todoBlockingStub.createTodo(req);
    return res.getTodo();
  }

  public List<TodoModel> getAll(int page, int perPage) {
    TodosGet req = TodosGet
      .newBuilder()
      .setPage(page)
      .setPerPage(perPage)
      .build();

    TodosGetResponse res = todoBlockingStub.getAllTodos(req);
    return res.getItemsList();
  }
}
