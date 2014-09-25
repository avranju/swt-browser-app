package in.nerdworks.browserapp;

import in.nerdworks.browserutils.types.Browser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.ExecutionException;

public class Program extends JFrame {
    private JPanel contentPane;
    private JButton signInButton;
    private Browser browser;

    public Program() throws
            MalformedURLException,
            ExecutionException,
            ClassNotFoundException,
            IllegalAccessException,
            InstantiationException {

        URLClassLoader loader = new URLClassLoader(new URL[] {
                new URL("file:/" + SWTLoader.loadJar().getPath()),
                new URL("file:/F:/code/swt-browser-app/out/artifacts/browser-util.jar")
        }, Program.class.getClassLoader());

        for(URL u : loader.getURLs()) {
            System.out.println(u.toString());
        }

        Class browserImplClass = loader.loadClass("in.nerdworks.browserutils.BrowserImpl");
        browser = (Browser)browserImplClass.newInstance();

        setContentPane(contentPane);
        getRootPane().setDefaultButton(signInButton);

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser.show(contentPane, "https://account.live.com/");
            }
        });
    }

    public static void main(String[] args) {
        try {
            Program dialog = new Program();
            dialog.setPreferredSize(new Dimension(320, 240));
            dialog.pack();
            dialog.setVisible(true);

            dialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    System.exit(0);
                }
            });
        }
        catch (Exception e) {
            System.out.println("ERROR:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
