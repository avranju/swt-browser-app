package in.nerdworks.browserutils;

import in.nerdworks.browserutils.types.Browser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import javax.swing.*;
import java.awt.*;

public class BrowserImpl implements Browser {
    @Override
    public void show(Component parent, String url) {
        Display display = new Display();
        Shell shell = new Shell(display);
        org.eclipse.swt.browser.Browser browser = null;

        shell.setLayout(new FillLayout());
        Monitor monitor = display.getPrimaryMonitor();
        org.eclipse.swt.graphics.Rectangle bounds = monitor.getBounds();
        shell.setSize((int) (bounds.width * 0.25), (int) (bounds.height * 0.55));

        try {
            browser = new org.eclipse.swt.browser.Browser(shell, SWT.NONE);
        } catch (SWTError err) {
            JOptionPane.showMessageDialog(parent, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (browser != null) {
            browser.setUrl(url);

            shell.open();
            while (!shell.isDisposed()) {
                if (!display.readAndDispatch()) {
                    display.sleep();
                }
            }
        }

        display.dispose();
    }
}
