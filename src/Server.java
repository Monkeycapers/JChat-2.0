import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

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



    //Socket echoSocket;

    public Server (JChat jChat, String host, int port) {
        this.jChat = jChat;
        hostName = host;
        portNumber = port;
        //jChat.receiveMessage("Test2");
        commands = new Command[] {new AuthenticateCommand(), new UserListCommand(), new StopCommand(), new SignInCommand(), new SignUpCommand(), new PromoteCommand(), new PrivateMessageCommand(), new SignOutCommand() };
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
                //Code
                System.out.println("Connected to " + hostName + ":" + portNumber);
                jChat.receiveMessage("c000000000,Connected to " + hostName + ":" + portNumber + "\n");
                String strOld = "";
                while (isRunning) {
                    String strIn = in.readUTF();
                    if (!strIn.equals(strOld)) {
                        if (!strIn.equals("Alive")) {
                            jChat.receiveMessage(strIn + "\n");
                        }
                    }
                    strOld = strIn;

                    if (pendingMessageBol) {
                        out.writeUTF(pendingMessageStr);
                        pendingMessageBol = false;
                    }
                    else {
                        out.writeUTF(jChat.nick + ",Alive");
                    }
                    try {Thread.sleep(delay);} catch (Exception ex) { }
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
            for (Command c : commands) {
                if (c.name.equals(name)) {
                    String result = c.parse(message);
                    if (result.equals("invalid")) {
                        jChat.receiveMessage("c000000000,Invalid usage of: " + c.name + "\n" + c.help);
                        break;
                    }
                    else {
                        pendingMessageStr = jChat.nick + "," + c.name + result;
                        pendingMessageBol = true;
                        break;
                    }
                }
            }
        }
        else {
            pendingMessageStr = jChat.nick + ",Message," + message;
            pendingMessageBol = true;
        }

    }




}
