package com.tonggn.mma;

import org.springframework.boot.SpringApplication;

public class TestMmaServerApplication {

  public static void main(String[] args) {
    SpringApplication.from(MmaServerApplication::main).with(TestcontainersConfiguration.class)
        .run(args);
  }

}
