import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.Scanner;


//make sure RuM is already opened when trying to run this


public class ModelGeneratorForRum { //this one takes a directory of xes files and generates and saves models with them

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
    private static void runAutomation() throws Exception {


        Thread.sleep(6000);


        Robot robot = new Robot();

        Scanner scan = new Scanner(System.in);

        System.out.println("!!!Do not forget to open RUM!!!");
        System.out.println("The absolute path to the directory where the event logs are stored:");
        String path = scan.nextLine();
        path = path.replace("/", "\\");

        // Open Log Generation
        robot.mouseMove(600, 375);
        Thread.sleep(1000);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(1000);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        int x = 500;


        //File dir = new File("C:\\Users\\peete\\OneDrive\\Documenten\\School\\2e master BI\\Masterproef\\Coding\\Logs for testing\\Declare Startpunt\\Level1\\Logs");

        File dir = new File(path);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".xes"));

        new File(dir + "\\Decl-models").mkdirs();

        if (files != null) {
            int count = 0;
            for (File file : files) {
                copyToClipboard(file.getAbsolutePath());
                Thread.sleep(6000);
                //open log
                robot.mouseMove(500, 50);
                Thread.sleep(500);
                robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(500);
                robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(2000);
                //open the right log in the dialog screen
                paste(robot);
                Thread.sleep(500);
                robot.keyPress(KeyEvent.VK_ENTER);
                Thread.sleep(1000); //20 second wait because it takes a while
                //press cancel so we can go to minerful quicker
                robot.mouseMove(1219, 433);
                Thread.sleep(1000);
                robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(500);
                robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(500);
                //change from declare miner to mp minerful
                robot.mouseMove(x, 150);
                Thread.sleep(1000);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(1000);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(1000);
                robot.mouseMove(x, 240);
                Thread.sleep(1000);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(1000);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(1000);
                //Discover again
                count += 1;
                robot.mouseMove(100, 10);
                Thread.sleep(5000);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(1000);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseMove(x, 690);
                Thread.sleep(5000);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(1000);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(15000);
                robot.mouseMove(1823, 150);
                robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(500);
                robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(500);
                String name = dir + "\\Decl-models\\model" + count + ".decl";
                copyToClipboard(name);
                paste(robot);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                Thread.sleep(5000);//Alternative for Thread.sleep. Thread.sleep blocked the thread that was also needed for RuM to work, this unblocks that thread
                robot.mouseMove(1091, 450);
                Thread.sleep(500);
                robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(500);
                robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
                Thread.sleep(500);
                }
            System.out.print("Generated " + count + " model(s)");
            } else{
                System.out.println("No files found or directory doesn't exist.");
            }
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
