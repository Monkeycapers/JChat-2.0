import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;

/**
 * Created by Evan on 8/29/2016.
 */
public class JChat {
    ChatGui chatGui;
    Server server;
    StartUpGui startUpGui;
    String hostname;
    String nick;
    int port;
    public static void main (String[] args) {
        new JChat();
    }
    public JChat () {
        //hostname = JOptionPane.showInputDialog(null, "Hostname:");
        //port = Integer.parseInt(JOptionPane.showInputDialog(null, "Port:"));
        //port = 16000;

        //startup gui calls start() when the user presses the start button
        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
            //UIManager.setLookAndFeel(
                    //UIManager.getCrossPlatformLookAndFeelClassName());
            //UIManager.put("MenuBar.background", Color.RED);
            UIManager.put("Menu.background", new Color(43, 46, 57));
            UIManager.put("Menu.foreground", Color.WHITE);
            //UIManager.put("MenuItem.background", Color.MAGENTA);

        }
        catch (UnsupportedLookAndFeelException e) {
            // handle exception
        }
        catch (ClassNotFoundException e) {
            // handle exception
        }
        catch (InstantiationException e) {
            // handle exception
        }
        catch (IllegalAccessException e) {
            // handle exception
        }
        Settings.load();
        startUpGui = new StartUpGui(this);

        //chatGui = new ChatGui(this);
        //server = new Server(this, hostname, port);
        //nick = "Anon";
        //server.run();
    }
    public void start(String hostName, int portNumber, String nick, String user, String pass, boolean authType) {
        this.nick = nick;
        chatGui = new ChatGui(this);
        server = new Server(this, hostName, portNumber);
        new Thread(server).start();
        //Give some time for the server to connect
        try {Thread.sleep(100); } catch (Exception e) { }
        if (authType) {
            server.parseMessage("/signup " + user + " " + pass);
        }
        else {
            server.parseMessage("/signin " + user + " " + pass);
        }

    }
    public void start(String hostName, int portNumber, String nick) {

        this.nick = nick;
        chatGui = new ChatGui(this);
        server = new Server(this, hostName, portNumber);
        new Thread(server).start();

    }
    //This recievs messages from the gui's textbox, and sends to internal server
    public void sendMessage (String message) {

        if (message.startsWith("/")) {
            if (message.startsWith("/nick")) {
                nick = message.substring(6);
            }
            else if (message.startsWith("/clear")){
                chatGui.clearScreen();
            }
            else {
                //Command for server

                server.parseMessage(message);
            }
        }
        else {
            server.parseMessage(message);
        }

    }
    //This recievs messages from the server, and sends to gui to be displayed
    public void receiveMessage(String message) {
        //Todo: Use the message class to have formmated text
        StyleContext styleContext = new StyleContext();
        //StyleConstants styleConstants = new StyleConstants();
        Style style = styleContext.addStyle("", null);

        String[] split = message.split(",");
        for (int i = 0; i <= split.length - 2; i += 2) {
            Color c;
            c = Color.BLACK;
            int r = 0;
            int b = 0;
            int g = 0;
            if (split[i].startsWith("c")) {
                //c001001001

                r = Integer.parseInt(split[i].substring(1, 4));
                g = Integer.parseInt(split[i].substring(4, 7));
                b = Integer.parseInt(split[i].substring(7, 10));

                c = new Color(r, g, b);
            }
            StyleConstants.setForeground(style, c);

            //System.out.println(split[i].substring(1));
            //System.out.println(split[i + 1]);

            //StyleConstants.setForeground(style, Color.getColor(split[i].substring(1)));
            chatGui.append(split[i + 1], style);
        }
       // chatGui.append(message, style);
    }
}
