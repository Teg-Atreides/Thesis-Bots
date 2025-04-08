import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RoboLauncher {
    public static void main(String[] args) {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "java", "-cp", "C:\\Users\\peete\\IdeaProjects\\AttemptAtRobot\\src", "Robo");
            pb.inheritIO(); // Optional: lets you see console output from Robo
            pb.start();

            System.out.println("Launched Robo in a separate JVM. Exiting launcher.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

