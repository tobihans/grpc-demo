package org.example.grpc.todo;

import java.util.List;
import java.util.ArrayList;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

public class Service extends TodoServiceGrpc.TodoServiceImplBase {
  private static Logger logger = Logger.getLogger(Service.class.getName());

  private final List<TodoModel> todos;

  public Service() {
    todos = new ArrayList<TodoModel>();

    for (int i = 0; i < 20; i++) {
      Instant time = Instant.now();
      String id = NanoIdUtils.randomNanoId();

      todos.add(TodoModel
        .newBuilder()
        .setId(id)
        .setTitle(String.format("Todo %d", i))
        .setDescription(String.format("Description of todo %d", i))
        .setCompleted(false)
        .setCreatedAt(Timestamp
          .newBuilder()
          .setSeconds(time.getEpochSecond())
          .setNanos(time.getNano())
          .build())
        .build()
      );
    }
  }

  @Override
  public void createTodo(TodoCreate req, StreamObserver<TodoCreateResponse> responseObserver) {
    String id = NanoIdUtils.randomNanoId();

    Instant time = Instant.now();

    TodoModel todo = TodoModel
      .newBuilder()
      .setId(id)
      .setTitle(req.getTitle())
      .setDescription(req.getDescription())
      .setCompleted(false)
      .setCreatedAt(
        Timestamp
          .newBuilder()
          .setSeconds(time.getEpochSecond())
          .setNanos(time.getNano())
          .build()
      )
      .build();
    todos.add(todo);

    TodoCreateResponse res = TodoCreateResponse
      .newBuilder()
      .setMessage("Item successfully registered.")
      .setSuccess(true)
      .setTodo(todo)
      .build();

    responseObserver.onNext(res);
    responseObserver.onCompleted();
  }

  @Override
  public void getAllTodos(TodosGet req, StreamObserver<TodosGetResponse> responseObserver) {
    int page = req.getPage();
    int perPage = req.getPerPage();
    
    logger.info(String.format("Requested page %d with %d items", page, perPage));

    responseObserver.onNext(TodosGetResponse
      .newBuilder()
      .setPage(page)
      .setPerPage(perPage)
      .addAllItems(todos)
      .setHasNextPage(false)
      .build()
    );
    responseObserver.onCompleted();
  }

  @Override
  public void searchTodos(TodosSearch req, StreamObserver<TodosGetResponse> responseObserver) {
    responseObserver.onCompleted();
  }
}

