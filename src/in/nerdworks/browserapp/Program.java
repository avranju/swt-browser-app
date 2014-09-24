package in.nerdworks.browserapp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.ExecutionException;

public class Program extends JFrame {
    private JPanel contentPane;
    private JButton signInButton;

    public Program() {
        setContentPane(contentPane);
        //setModal(true);
        getRootPane().setDefaultButton(signInButton);

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Display display = new Display();
                Shell shell = new Shell(display);
                Browser browser = null;

                shell.setLayout(new FillLayout());
                Monitor monitor = display.getPrimaryMonitor();
                Rectangle bounds = monitor.getBounds();
                shell.setSize((int) (bounds.width * 0.25), (int) (bounds.height * 0.55));

                try {
                    browser = new Browser(shell, SWT.NONE);
                } catch (SWTError err) {
                    JOptionPane.showMessageDialog(contentPane, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

                if (browser != null) {
                    browser.setUrl("http://bit.ly/avranju");

                    shell.open();
                    while (!shell.isDisposed()) {
                        if (!display.readAndDispatch()) {
                            display.sleep();
                        }
                    }
                }

                display.dispose();
            }
        });
    }

    public static void main(String[] args) {
        try {
            SWTLoader swtLoader = new SWTLoader();
            File jarFile = swtLoader.loadJar();
            URLClassLoader loader = new URLClassLoader(new URL[] {
                    new URL("file://" + jarFile.getPath())
            }, Thread.currentThread().getContextClassLoader());
            Thread.currentThread().setContextClassLoader(loader);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (ExecutionException e) {
            e.printStackTrace();
            System.exit(1);
        }

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
}
