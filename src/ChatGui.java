

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

        //jTextPane.setBackground(Color.black);
        jScrollPane = new JScrollPane(jTextPane);

        add(jScrollPane);
        add (jTextField, BorderLayout.SOUTH);
        jMenuBar = new JMenuBar();

        JMenu jMenu = new JMenu("Test");
        jMenu.add("Test");
        jMenuBar.add(jMenu);
        add(jMenuBar, BorderLayout.NORTH);
        //setLayout( new ScrollPaneLayout());

        pack();
        jTextField.setText("<" + jChat.nick + "> ");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setState(MAXIMIZED_BOTH);
        setExtendedState( getExtendedState()|MAXIMIZED_BOTH );
        //setBounds(100, 100, 100, 100);
        setResizable(true);
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
    public void addText(String message) {

    }
}
