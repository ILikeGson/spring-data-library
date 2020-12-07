package com.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class ConsoleIOService implements com.example.service.IOService {

    private final InputStream inputStream;
    private final PrintStream printStream;

    public ConsoleIOService(@Value("#{ T(java.lang.System).in}") InputStream inputStream,
                            @Value("#{ T(java.lang.System).out}") PrintStream printStream) {
        this.inputStream = inputStream;
        this.printStream = printStream;
    }

    @Override
    public String readString() {
        Scanner scanner = new Scanner(inputStream);
        return scanner.nextLine().trim();
    }

    @Override
    public void out(String str) {
        printStream.print(str);
    }
}
