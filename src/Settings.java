import java.io.*;
import java.util.ArrayList;

/**
 * Created by Evan on 9/22/2016.
 */
public class Settings {

    static String settingsFile = System.getProperty("user.home") + "\\AppData\\Roaming\\JChat\\Client\\Settings";
    static ArrayList<String> lines = new ArrayList<String>();

    public static void load() {
        //Load the settings into lines
        try {
            File file = new File(settingsFile);
            file.getParentFile().mkdirs();
            if (file.createNewFile()) {
                //file was just created
                writeDefaults(file);
                //return;
            }
            FileInputStream fis = new FileInputStream(file);

            //Construct BufferedReader from InputStreamReader
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = null;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
            fis.close();
        }
        catch(Exception e){

        }
    }
    public static void writeDefaults(File file) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(file));
            pw.println("host:localhost");
            pw.println("port:16000");
            pw.println("enable:0");
            pw.println("title:JChat Client 2.0");
            pw.println("nick:guest");
            pw.println("user:username");
            pw.close();
        }
        catch (Exception e) {

        }
    }
    public static String getProperty(String p) {
        for (String s: lines) {
            if (s.startsWith(p)) {
                String[] split = s.split(":");
                return split[1];
            }
        }
        return "";
    }
    public static void writeProperty(String p, String value ) {
        try {
            File file = new File(settingsFile);
            FileInputStream fis = new FileInputStream(file);
            //Construct BufferedReader from InputStreamReader
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String total = "";
            String line = null;
            String text = "";
            while ((line = br.readLine()) != null) {
                String[] split = line.split(":");
                    if (p.equals(split[0])) {
                        total += p + ":" + value + "\n";
                    }
                    else {
                        total += line + "\n";
                    }
            }
            br.close();
            PrintWriter pw = new PrintWriter(new FileWriter(file));
            pw.print(text + total);
            pw.close();
        }
        catch (Exception e) {

        }
    }
    public static void writePropertys(String[] p, String[] value) {
        try {
            File file = new File(settingsFile);
            FileInputStream fis = new FileInputStream(file);
            //Construct BufferedReader from InputStreamReader
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String total = "";
            String line = null;
            String text = "";
            while ((line = br.readLine()) != null) {
                String[] split = line.split(":");
                boolean found = false;
                for (int i = 0; i <p.length; i ++) {
                    if (split[0].equals(p[i])) {
                        text += p[i] + ":" + value[i] + "\n";
                        i = p.length;
                        found = true;
                    }
                }
                if (!found) {
                    text += line + "\n";
                }
            }
            br.close();
            PrintWriter pw = new PrintWriter(new FileWriter(file));
            pw.print(text + total);
            pw.close();
        }
        catch (Exception e) {

        }
    }
}
