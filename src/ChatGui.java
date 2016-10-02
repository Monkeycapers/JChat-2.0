

import sun.font.FontFamily;

import javax.swing.*;
import javax.swing.plaf.basic.BasicMenuBarUI;
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
    private JList jList;

    String[] users;

    Color backGroundColor = new Color(43, 46, 57);
    //Color backGroundColor = Color.BLACK;
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
        setBackground(backGroundColor);
        //setUndecorated(true);
        getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));

//        ComponentResizer cr = new ComponentResizer();
//        cr.registerComponent(this);
//        cr.setSnapSize(new Dimension(10, 10));
//
//        ComponentMover cm = new ComponentMover();
//        cm.registerComponent(this);
//        cm.setDragInsets( cr.getDragInsets() );
//        cm.setSnapSize(new Dimension(10, 10));
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
        jTextField.setBackground(backGroundColor);
        jTextField.setForeground(Color.WHITE);

        jTextPane.setBackground(backGroundColor);
        //String[] test = new String[] {"User List:    ", "test2   ", "Test3   "};
        users = new String[] {"User List:   ", jChat.nick};
        jList = new JList(users);
        jList.setBackground(backGroundColor);
        jList.setForeground(Color.white);
        //jList.setSize(100, 100);

        jMenuBar = new JMenuBar();
        //jMenuBar.setLayout(new GridLayout());

        //jMenuBar.setBackground(backGroundColor);
        //jMenuBar.setForeground(backGroundColor);
        JMenu jMenu = new JMenu("Test");
        jMenu.setOpaque(true);
        jMenu.setForeground(Color.WHITE);
        //jMenu.setBackground(backGroundColor);
        jMenu.add("Test");
        jMenuBar.add(jMenu);
        jMenuBar.setOpaque(true);
        jMenuBar.setUI ( new BasicMenuBarUI()
        {
            public void paint ( Graphics g, JComponent c )
            {
                g.setColor ( backGroundColor );
                g.fillRect ( 0, 0, c.getWidth (), c.getHeight () );
            }
        } );
        jMenuBar.setForeground(Color.WHITE);

        try {
            String dirty = "C:\\Users\\Evan\\Google Drive\\dev\\JChat-2.0\\out\\production\\JChat Client 2.0\\Fonts\\OpenSans-Regular.ttf";
            Font roboto = Font.createFont(Font.TRUETYPE_FONT, new File(dirty)).deriveFont(15f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(dirty)));
            jTextPane.setFont(roboto);
            jTextField.setFont(roboto);
            jList.setFont(roboto);
            jMenu.setFont(roboto);
            jMenuBar.setFont(roboto);
        } catch (IOException e) {
            e.printStackTrace();

        } catch(FontFormatException e) {
            e.printStackTrace();
        }

        jScrollPane = new JScrollPane(jTextPane);


        add(jScrollPane);
        add (jTextField, BorderLayout.SOUTH);
        add(jList, BorderLayout.EAST);

        //Todo: Make menu bar work
        add(jMenuBar, BorderLayout.NORTH);
        //setLayout( new ScrollPaneLayout());

        pack();
        jTextField.setText("<" + jChat.nick + "> ");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setState(MAXIMIZED_BOTH);
        setExtendedState( getExtendedState()|MAXIMIZED_BOTH );
        //setBounds(100, 100, 100, 100);
        setResizable(true);
        setBackground(backGroundColor);
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
