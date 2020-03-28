package com.gui;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.action.ActionRevision;
import com.exception.RevisionLaboralException;
import com.util.UtilRevisionUrl;

/**
 * Provides structure and components to the interface.
 *  
 * @author gon
 * @since 1.1
 */

public class GuiRevision {

	/**
	 * Logger
	 */
	final static Logger logger = LogManager.getLogger(GuiRevision.class.getName());
	
	/**
	 * Initialize the principal interface frame.
	 */
	public static void initialFrame() {
		JFrame frame = new JFrame("Revisor laboral");

		// Urls to combo box
		String[] urls = { "Infojobs", "Linkedin" };
		final JComboBox<String> jComboBox = new JComboBox<>(urls);
		jComboBox.setBounds(50, 100, 120, 20);
		frame.add(jComboBox);

		//label to frame
		JLabel label = new JLabel();
		label.setText("Selecciona la web a revisar");
		label.setHorizontalAlignment(0);
		label.setSize(350, 100);
		frame.add(label);

		//Format, size and structure frame
		frame.setLayout((LayoutManager)null);
		frame.setSize(350, 350);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(3);

		//Button and functionality to the button
		JButton jButton = new JButton("Ejecutar");
		jButton.setBounds(200, 100, 85, 20);
		buttonAddComboBoxAction(jComboBox, jButton);
		frame.add(jButton);
	}

	/**
	 * Set action to the button by item selected form combo box.
	 * 
	 * @param jComboBox
	 * @param jButton
	 */
	private static void buttonAddComboBoxAction(final JComboBox<String> jComboBox, JButton jButton) {
		jButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String data = jComboBox.getItemAt(jComboBox.getSelectedIndex());
				if(logger.isDebugEnabled()){
					logger.debug("Seleccionado " + data);
				}
				try {
					ActionRevision.executeRevision(UtilRevisionUrl.getUrl(data));
				} catch (RevisionLaboralException ex) {
					logger.error("Error: " + ex.getMessage());
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
	}
}
