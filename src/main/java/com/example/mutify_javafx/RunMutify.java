package com.example.mutify_javafx;

import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RunMutify {
    public static void main (String[] args) {
        log.info("Starting application");
        Application.launch(Main.class, args);
    }
}
