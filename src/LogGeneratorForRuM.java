import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.Scanner;


//make sure RuM is already opened on the log generator page when trying to run this

public class LogGeneratorForRuM {
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
        Robot robot = new Robot();

        Scanner scan = new Scanner(System.in);

        System.out.println("!!!Do not forget to open RuM!!!");

        System.out.println("The absolute path to the directory where the declare models are stored:");
        String path = scan.nextLine();
        path = path.replace("/", "\\");

        System.out.println("The minimum length of the logs");
        Integer min = scan.nextInt();

        System.out.println("The maximum length of the logs");
        Integer max = scan.nextInt();

        System.out.println("The amount of cases er log");
        Integer amountOfPaths = scan.nextInt();

        //File dir = new File("C:\\Users\\peete\\OneDrive\\Documenten\\School\\2e master BI\\Masterproef\\Coding\\Logs for testing\\Declare Startpunt\\Level1\\Logs\\ResultsDeclarative");
        File dir = new File(path);

        File[] files = dir.listFiles((d, name) -> name.endsWith(".decl"));

        new File(dir + "\\Logs").mkdirs();


        if (files != null){
            int count = 0;
            for (File file: files){
                count+=1;
                OpenModel(robot, file);
                SetGenerationMode(robot);
                SetGeneralParameters(robot, min, max, amountOfPaths);
                GenerateAndSave(robot, count, dir.getAbsolutePath());
            }
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

    private static void OpenModel(Robot robot, File file) throws Exception{
        robot.mouseMove(580, 50);
        Thread.sleep(500);
        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(1000);
        copyToClipboard(file.getAbsolutePath());
        paste(robot);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(500);

    }

    private static void SetGenerationMode(Robot robot) throws Exception {
        robot.mouseMove(439, 150);
        Thread.sleep(500);
        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);

        robot.mouseMove(439, 184);
        Thread.sleep(500);
        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);

    }

    private static void SetGeneralParameters(Robot robot, int min, int max, int amountOfTraces) throws Exception{
        // Set Minimum Events Per Trace
        robot.mouseMove(439, 280);
        Thread.sleep(500);
        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        Thread.sleep(500);
        copyToClipboard("" + min);
        paste(robot);

        // Set Maximum Events Per Trace
        robot.mouseMove(439, 306);
        Thread.sleep(500);
        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(500);
        copyToClipboard("" + max);
        paste(robot);


        // Set Amount of Traces
        robot.mouseMove(439, 335);
        Thread.sleep(500);
        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(500);
        copyToClipboard("" + amountOfTraces);
        paste(robot);

    }

    private static void GenerateAndSave(Robot robot, int count, String dict) throws Exception{

        // close model constraints so that the generate button is always in the same spot
        robot.mouseMove(277, 441);
        Thread.sleep(500);
        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);

        robot.mouseMove(509, 509);
        Thread.sleep(500);
        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(10000);

        // save
        robot.mouseMove(1860, 160);
        Thread.sleep(500);
        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(15000);

        copyToClipboard(dict + "\\Logs\\" + count + ".xes");
        paste(robot);

        Thread.sleep(1500);
        robot.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(1500);

        robot.mouseMove(1091, 450);
        Thread.sleep(5000);
        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
    }
}
