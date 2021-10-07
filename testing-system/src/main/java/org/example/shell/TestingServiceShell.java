package org.example.shell;

import org.example.service.TestingService;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class TestingServiceShell {
    private final TestingService testingService;

    public TestingServiceShell(TestingService testingService) {
        this.testingService = testingService;
    }

    @ShellMethod(value = "Start", key = {"s", "start"})
    public String start() {
        testingService.start();
        return "Now you can start testing";
    }

    @ShellMethod(value = "Testing", key = {"t", "test"})
    @ShellMethodAvailability("testAvailable")
    public String test() {
        testingService.test();
        return "The result is now available";
    }

    @ShellMethod(value = "Result", key = {"r", "result"})
    @ShellMethodAvailability("resultAvailable")
    public void result() {
        testingService.printStats();
    }

    public Availability testAvailable() {
        return testingService.testAvailable()
                ? Availability.available()
                : Availability.unavailable("you must introduce yourself or get a test result");
    }

    public Availability resultAvailable() {
        return testingService.resultAvailable()
                ? Availability.available()
                : Availability.unavailable("you must run the test first");
    }
}
