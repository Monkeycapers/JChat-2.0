import javafx.scene.layout.StackPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Evan on 9/11/2016.
 */
public class StartUpGui extends JFrame {
    JChat jChat;
    JLabel hostnameLabel;
    JTextField hostnameField;
    JLabel portNumberLabel;
    JTextField portNumberField;
    JLabel nickLabel;
    JTextField nickField;
    JLabel enableLoginLabel;
    JToggleButton enableLoginToggle;
    ButtonGroup buttonGroup;
    JRadioButton enableLoginRadio;
    JRadioButton enableSignupRadio;
    JLabel userLabel;
    JTextField userField;
    JLabel passwordLabel;
    JPasswordField passwordField;
    JButton startButton;
    JButton quitButton;
    public StartUpGui(JChat jChat) {
        this.jChat = jChat;
        setUpGui();
    }

    public void setUpGui() {
        setTitle("JChat startup");
        JPanel p = new JPanel(new BorderLayout());
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(0, 2));
        //Hostname
        hostnameLabel = new JLabel("Hostname: ");
        add(hostnameLabel);
        hostnameField = new JTextField(Settings.getProperty("host"));
        add(hostnameField);
        //Portnumber
        portNumberLabel = new JLabel("Portnumber: ");
        add(portNumberLabel);
        portNumberField = new JTextField(Settings.getProperty("port"));
        add(portNumberField);
        //Nickname
        nickLabel = new JLabel("Nickname: ");
        add(nickLabel);
        nickField = new JTextField(Settings.getProperty("nick"));
        add(nickField);
        //Toggle logging in
        enableLoginLabel = new JLabel("Login or Signup right away: ");
        add(enableLoginLabel);
        enableLoginToggle = new JToggleButton("Enable");
        add(enableLoginToggle);
        //Login or Signup
        buttonGroup = new ButtonGroup();
        enableSignupRadio = new JRadioButton("Signup");

        buttonGroup.add(enableSignupRadio);
        add(enableSignupRadio);
        enableLoginRadio = new JRadioButton("Signin");
        buttonGroup.add(enableLoginRadio);

        add(enableLoginRadio);
        //Username
        userLabel = new JLabel("Username: ");
        //userLabel.setEnabled(false);
        add(userLabel);
        userField = new JTextField(Settings.getProperty("user"));
        //userField.setEnabled(false);
        add(userField);
        //password
        passwordLabel = new JLabel("Password: ");
        add(passwordLabel);
        passwordField = new JPasswordField();
        add(passwordField);
        //Start and quit buttons
        quitButton = new JButton("Quit");
        add(quitButton);
        startButton = new JButton("Start");
        add(startButton);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(150, 100, 100, 100);
        setResizable(false);
        pack();
        setVisible(true);
        //Button actions
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });
    }
    public void quit() {
        System.exit(1);
    }
    public void start() {
        try {
            String hostName = hostnameField.getText();
            int portNumber = Integer.parseInt(portNumberField.getText());
            String nick = nickField.getText();
            Settings.writePropertys(new String[] {"host", "port", "nick", "user"}, new String[] {hostName, String.valueOf(portNumber), nick, userField.getText()});
            if (enableLoginToggle.isSelected()) {
                if (enableLoginRadio.isSelected()) {
                    jChat.start(hostName, portNumber, nick, userField.getText(), String.valueOf(passwordField.getPassword()), false);
                    setVisible(false);
                    this.dispose();
                }
                else {
                    jChat.start(hostName, portNumber, nick, userField.getText(), String.valueOf(passwordField.getPassword()), true);
                    setVisible(false);
                    this.dispose();
                }
            }
            else {
                jChat.start(hostName, portNumber, nick);
                setVisible(false);
                this.dispose();
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input data");
        }
    }

}
