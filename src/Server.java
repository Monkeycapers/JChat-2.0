import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by Evan on 8/30/2016.
 */
public class Server implements Runnable {

    boolean isRunning = true;

    JChat jChat;

    String hostName;
    int portNumber;

    String pendingMessageStr;
    boolean pendingMessageBol;

    Command [] commands;

    int delay;

    String cString = "c255255255";

    ServerMessageSender serverMessageSender;

    public Server (JChat jChat, String host, int port) {
        this.jChat = jChat;
        hostName = host;
        portNumber = port;
        //jChat.receiveMessage("Test2");
        commands = new Command[] {new AuthenticateCommand(), new UserListCommand(), new StopCommand(), new SignInCommand(), new SignUpCommand(), new PromoteCommand(), new PrivateMessageCommand(), new SignOutCommand(), new LobbyJoinCommand(), new LobbyCreateCommand(), new LobbyListCommand() };
        portNumber = port;
        hostName = host;
    }

    public void run () {

        while (isRunning) {
            //do server stuff
            //Get a Socket connection to the host
            try (
                    Socket echoSocket = new Socket(hostName, portNumber);     //new InputStreamReader(System.in))
                    OutputStream outToServer = echoSocket.getOutputStream();
                    DataOutputStream out = new DataOutputStream(outToServer);
                    InputStream inFromServer = echoSocket.getInputStream();
                    DataInputStream in = new DataInputStream(inFromServer);
            ) {
                //Setup Code
                //echoSocket.setSoTimeout(10);
                jChat.chatGui.clearScreen();
                System.out.println("Connected to " + hostName + ":" + portNumber);
                jChat.receiveMessage(cString + ",Connected to " + hostName + ":" + portNumber + "\n");
                String strOld = "";
                serverMessageSender = new ServerMessageSender(out);
                //
                while (isRunning) {
                    //Read message in
                    String strIn = in.readUTF();
                    if (!strIn.equals(strOld)) {
                        jChat.receiveMessage(strIn + "\n");
                    }
                    strOld = strIn;
                }


        } catch (UnknownHostException e) {
            System.exit(-1);
        } catch (IOException e) {
            //This can happen if the server has an error or closes
                try {Thread.sleep(1000);} catch (Exception ex) { }
        } catch (Exception e) {
            System.exit(-1);
        }
            }
        }



    public void parseMessage (String message) {
        if (message.startsWith("/")) {
            String split[] = message.split(" ");
            String name = split[0];
            //Command
            if (message.startsWith("/help")) {
                if (split.length == 2) {
                    for (Command c: commands) {
                        if (c.name.equals(split[1])) {
                            jChat.receiveMessage("c255255255,Help: " + c.name + " " + c.help + "\n");
                        }
                    }
                }
                else {
                    String total = "c255255255,";
                    for (Command c: commands) {
                        total += "Help: " + c.name + " " + c.help + "\n";
                    }
                    jChat.receiveMessage(total);
                }
            }
            else {
                for (Command c : commands) {
                    if (c.name.equals(name)) {
                        String result = c.parse(message);
                        if (result.equals("invalid")) {
                            jChat.receiveMessage("c255255255,Invalid usage of: " + c.name + "\n" + c.help + "\n");
                            break;
                        }
                        else {
                            serverMessageSender.send(jChat.nick + "," + c.name + result);
                            break;
                        }
                    }
                }
            }
        }
        else {
            serverMessageSender.send(jChat.nick + ",Message," + message);
        }
    }
}
