import java.io.IOException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

public class PLG2Generator {

    public static void main(String[] args) throws IOException {
        new Thread(() -> {
            try {
                runAutomation();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // Let main thread exit so we don't block the other Java application
        System.out.println("Automation started in a separate thread. Exiting main thread.");
    }

    public static void runAutomation() throws Exception {
        Robot robot = new Robot();

        File dir = new File("C:\\Users\\peete\\OneDrive\\Documenten\\School\\2e master BI\\Masterproef\\Coding\\Logs for testing\\Declare Startpunt\\Level3\\Logs\\ResultsImperative");
        File[] files = dir.listFiles((d, name) -> name.endsWith(".bpmn"));

        if (files != null) {
            int count = 0;
            for (File file : files) {
                count += 1;
                OpenModel(robot, file);
                GenerateLog(robot);
                SaveLog(robot, count, dir.getAbsolutePath());
            }
        }


    }

    private static void OpenModel(Robot robot, File file) throws Exception{
        robot.mouseMove(102, 37);
        Thread.sleep(500);
        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(5000);
        copyToClipboard(file.getAbsolutePath());
        paste(robot);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(500);
    }

    private static void GenerateLog(Robot robot) throws Exception{
        robot.mouseMove(1774, 37);
        Thread.sleep(500);
        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(500);
        robot.keyRelease(KeyEvent.VK_ENTER);

    }

    private static void SaveLog(Robot robot, int count, String dict) throws Exception{
        copyToClipboard(dict + "\\Logs\\" + count);
        paste(robot);
        robot.mouseMove(940,703);
        Thread.sleep(500);
        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
        robot.mouseMove(940,743);
        Thread.sleep(500);
        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(500);
        robot.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(5000);
    }

    private static void copyToClipboard(String text) {
        StringSelection selection = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
    }

    private static void paste(Robot robot) throws InterruptedException {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(500);
    }
}
