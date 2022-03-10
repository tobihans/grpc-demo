package org.example.grpc.todo;

import io.grpc.stub.StreamObserver;

public class Service extends TodoServiceGrpc.TodoServiceImplBase {
  @Override
  public void createTodo(TodoCreate req, StreamObserver<TodoCreateResponse> responseObserver) {
    responseObserver.onCompleted();
  }

  @Override
  public void getAllTodos(TodosGet req, StreamObserver<TodosGetResponse> responseObserver) {
    responseObserver.onCompleted();
  }

  @Override
  public void searchTodos(TodosSearch req, StreamObserver<TodosGetResponse> responseObserver) {
    responseObserver.onCompleted();
  }
}

