package com.isti.util.desktop;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.desktop.AboutEvent;
import java.awt.desktop.AboutHandler;
import java.awt.desktop.PreferencesEvent;
import java.awt.desktop.PreferencesHandler;
import java.awt.desktop.QuitEvent;
import java.awt.desktop.QuitHandler;
import java.awt.desktop.QuitResponse;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

public class DesktopUtilFns {
	/**
	 * Run the test.
	 * 
	 * @param args
	 *            the arguments.
	 */
	public static void main(String[] args) {
		// exit if desktop is not supported
		if (!Desktop.isDesktopSupported()) {
			System.out.println("Desktop is not supported");
			return;
		}
		final String aboutCommand = "About";
		final String quitCommand = "Quit";
		final String preferencesCommand = "Preferences";
		final ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final String actionCommand = e.getActionCommand();
				javax.swing.JOptionPane.showMessageDialog(null,
						actionCommand + " dialog");
				switch (actionCommand) {
				case aboutCommand:
					break;
				case quitCommand:
					System.exit(0);
					break;
				case preferencesCommand:
					break;
				}
			}
		};
		setHandler(actionListener, aboutCommand, quitCommand,
				preferencesCommand);
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("DesktopUtilFns");
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					javax.swing.JOptionPane.showMessageDialog(null,
							"windowClosing dialog");
					System.exit(0);
				}
			});
			frame.setSize(new Dimension(320, 240));
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}

	/**
	 * Set the default menu bar.
	 * 
	 * @param menuBar
	 *            the menu bar.
	 */
	public static void setDefaultMenuBar(JMenuBar menuBar) {
		// if desktop is supported
		if (Desktop.isDesktopSupported()) {
			try {
				final Desktop desktop = Desktop.getDesktop();
				desktop.setDefaultMenuBar(menuBar);
			} catch (Throwable t) {
			}
		}
	}

	/**
	 * Set the handler for the about, quit and preferences menu options.
	 * 
	 * @param actionListener
	 *            the action listener.
	 * @param aboutCommand
	 *            the about action command or null if none.
	 * @param quitCommand
	 *            the quit action command or null if none.
	 * @param preferencesCommand
	 *            the preferences action command or null if none.
	 * @return true if success, false otherwise.
	 */
	public static boolean setHandler(final ActionListener actionListener,
			final String aboutCommand, final String quitCommand,
			final String preferencesCommand) {
		// if desktop is supported
		if (Desktop.isDesktopSupported()) {
			try {
				final Desktop desktop = Desktop.getDesktop();
				if (aboutCommand != null) {
					desktop.setAboutHandler(new AboutHandler() {
						@Override
						public void handleAbout(AboutEvent e) {
							actionListener.actionPerformed(new ActionEvent(
									e.getSource(), ActionEvent.ACTION_PERFORMED,
									aboutCommand));
						}
					});
				}
				if (quitCommand != null) {
					desktop.setQuitHandler(new QuitHandler() {
						@Override
						public void handleQuitRequestWith(QuitEvent e,
								QuitResponse response) {
							actionListener.actionPerformed(new ActionEvent(
									e.getSource(), ActionEvent.ACTION_PERFORMED,
									quitCommand));
						}
					});
				}
				if (preferencesCommand != null) {
					desktop.setPreferencesHandler(new PreferencesHandler() {
						@Override
						public void handlePreferences(PreferencesEvent e) {
							actionListener.actionPerformed(new ActionEvent(
									e.getSource(), ActionEvent.ACTION_PERFORMED,
									preferencesCommand));
						}
					});
				}
				return true;
			} catch (Throwable t) {
			}
		}
		return false;
	}
}
