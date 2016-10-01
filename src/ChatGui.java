

import sun.font.FontFamily;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by Evan on 8/29/2016.
 */
public class ChatGui extends JFrame {
    private JTextPane jTextPane;
    private JScrollPane jScrollPane;
    private JTextField jTextField;
    private JMenuBar jMenuBar;

    Style style;
    StyleContext styleContext;
    StyleConstants styleConstants;

    JChat jChat;
    public ChatGui (JChat jChat) {
        this.jChat = jChat;
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jChat.sendMessage(jTextField.getText().substring(jChat.nick.length() + 3).replaceAll(",", ""));
                jTextField.setText("<" + jChat.nick + "> ");
            }

        };
        //jScrollPane = new JScrollPane();
        styleContext = new StyleContext();
        style = styleContext.addStyle("", null);
        styleConstants.setForeground(style, Color.BLUE);
        DefaultStyledDocument document = new DefaultStyledDocument();
        jTextPane = new JTextPane(document);
        jTextPane.setMargin(new Insets(25, 25, 25, 25));
        jTextField = new JTextField();
        jTextField.setMargin(new Insets(25, 25, 25, 25));

        jTextField.addActionListener(action);

        jTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE); {
                    if (jTextField.getText().length() <= jChat.nick.length() + 3) {
                        jTextField.setText("<" + jChat.nick + "> ");
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        //jTextPane.setText("Test");
        jTextPane.setEditable(false);
        jTextField.setBackground(new Color(43, 46, 57));
        jTextField.setForeground(Color.WHITE);

        jTextPane.setBackground(new Color(43,46,57));

        try {
            //create the font to use. Specify the size!
            String dirty = "C:\\Users\\Evan\\Google Drive\\dev\\JChat-2.0\\out\\production\\JChat Client 2.0\\Fonts\\OpenSans-Regular.ttf";
            Font roboto = Font.createFont(Font.TRUETYPE_FONT, new File(dirty)).deriveFont(15f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(dirty)));
            jTextPane.setFont(roboto);
            jTextField.setFont(roboto);
        } catch (IOException e) {
            e.printStackTrace();

        } catch(FontFormatException e) {
            e.printStackTrace();
        }

        jScrollPane = new JScrollPane(jTextPane);


        add(jScrollPane);
        add (jTextField, BorderLayout.SOUTH);
        jMenuBar = new JMenuBar();
        jMenuBar.setBackground(new Color(43, 46, 57));
        jMenuBar.setForeground(new Color(43, 46, 57));
        JMenu jMenu = new JMenu("Test");
        jMenu.setBackground(new Color(43, 46, 57));
        jMenu.add("Test");
        jMenuBar.add(jMenu);
        //Todo: Make menu bar work
        //add(jMenuBar, BorderLayout.NORTH);
        //setLayout( new ScrollPaneLayout());

        pack();
        jTextField.setText("<" + jChat.nick + "> ");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setState(MAXIMIZED_BOTH);
        setExtendedState( getExtendedState()|MAXIMIZED_BOTH );
        //setBounds(100, 100, 100, 100);
        setResizable(true);
        setBackground(new Color(43, 46, 57));
        //append("Test", style);
        //styleConstants.setForeground(style, Color.RED);
        //append("Test", style);
       // styleConstants.setForeground(style, Color.GREEN);
        //append("Test", style);
    }
    public void append(String s, Style style) {
        try {
            Document doc = jTextPane.getDocument();
            doc.insertString(doc.getLength(), s, style);
            jTextPane.setCaretPosition(jTextPane.getDocument().getLength());
        } catch(BadLocationException exc) {
            exc.printStackTrace();
        }
    }
    public void clearScreen() {
        jTextPane.setText("");
    }
    public void addText(String message) {

    }
}
