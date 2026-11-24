package SwagLabs_Automation_Testing;

import org.testng.*;
import java.io.*;
import java.util.*;

public class Reports implements ITestListener {

    static Map<String, List<String>> results = new HashMap<>();
    static Map<String, List<String>> bugs = new HashMap<>();
    static long run = System.currentTimeMillis();

    static String cls(ITestResult r) {
        return r.getTestClass().getRealClass().getSimpleName();
    }

    @Override
    public void onTestSuccess(ITestResult r) {
        results.computeIfAbsent(cls(r), k -> new ArrayList<>()).add(r.getName() + ",PASS");
    }

    @Override
    public void onTestFailure(ITestResult r) {
        results.computeIfAbsent(cls(r), k -> new ArrayList<>()).add(r.getName() + ",FAIL");
        String msg = String.valueOf(r.getThrowable().getMessage()).replace("\"", "'").replace("\n", " ");
        bugs.computeIfAbsent(cls(r), k -> new ArrayList<>()).add(r.getName() + ",\"" + msg + "\"");
    }

    @Override
    public void onFinish(ITestContext context) {
        for (String c : results.keySet()) {
            File dir = new File("Reports/" + c + "_" + run);
            dir.mkdirs();
            write(dir + "/TestResults.csv", "Test Case,Status", results.get(c));
            write(dir + "/BugReport.csv", "Test Case,Bug Description", bugs.getOrDefault(c, new ArrayList<>()));
        }
    }

    static void write(String path, String header, List<String> lines) {
        try (PrintWriter w = new PrintWriter(path)) {
            w.println(header);
            lines.forEach(w::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}