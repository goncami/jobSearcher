package com.main;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.exception.RevisionDiariaException;

public class StartRevision {
  static Properties prop;
  
  public static void main(String[] args) {
    try {
      loadProperties();
      comboBox();
    } catch (RevisionDiariaException ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage());
    } 
  }
  
  private static void loadProperties() throws RevisionDiariaException {
    System.out.println("current dir = " + System.getProperty("user.dir"));
    try (InputStream input = new FileInputStream("config.properties")) {
      prop = new Properties();
      prop.load(input);
    } catch (IOException ex) {
      ex.printStackTrace();
      throw new RevisionDiariaException(ex.getMessage());
    } 
  }
  
  private static void comboBox() {
    JFrame frame = new JFrame("Revisor laboral");
    JLabel label = new JLabel();
    label.setText("Selecciona la web a revisar");
    label.setHorizontalAlignment(0);
    label.setSize(350, 100);
    JButton jButton = new JButton("Ejecutar");
    jButton.setBounds(200, 100, 85, 20);
    String[] urls = { "Infojobs", "Linkedin" };
    final JComboBox<String> jComboBox = new JComboBox<>(urls);
    jComboBox.setBounds(50, 100, 120, 20);
    frame.add(jComboBox);
    frame.add(label);
    frame.add(jButton);
    frame.setLayout((LayoutManager)null);
    frame.setSize(350, 350);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(3);
    jButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            String data = jComboBox.getItemAt(jComboBox.getSelectedIndex());
            System.out.println("Seleccionado " + data);
            try {
              StartRevision.ejecutarRevision(StartRevision.getUrl(data));
            } catch (RevisionDiariaException ex) {
              JOptionPane.showMessageDialog(null, ex.getMessage());
            } 
          }
        });
  }
  
  private static void ejecutarRevision(String web) throws RevisionDiariaException {
    try {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
      System.out.println("START " + formatter.format(new Date(System.currentTimeMillis())));
      for (String patron : readCsv("listado.csv")) {
        TimeUnit.SECONDS.sleep(3L);
        System.out.println("Patron a buscar: " + patron);
        String formattedUrl = MessageFormat.format(web, new Object[] { patron });
        System.out.println(formattedUrl);
        openUrl(formattedUrl);
        TimeUnit.SECONDS.sleep(3L);
      } 
      System.out.println("END " + formatter.format(new Date(System.currentTimeMillis())));
    } catch (InterruptedException e) {
      System.out.println("Error sleeping " + e.getMessage());
      e.printStackTrace();
      throw new RevisionDiariaException(e.getMessage());
    } catch (IOException e) {
      System.out.println("Error al abrir la url " + e.getMessage());
      e.printStackTrace();
      throw new RevisionDiariaException(e.getMessage());
    } 
  }
  
  private static String getUrl(String input) {
    String url = null;
    switch (input) {
      case "Infojobs":
        url = prop.getProperty("url.infojobs");
        System.out.println(input);
        break;
      case "Linkedin":
        url = prop.getProperty("url.linkedin");
        System.out.println(input);
        break;
    } 
    return url;
  }
  
  private static ArrayList<String> readCsv(String csvFile) throws RevisionDiariaException {
    String line = "";
    ArrayList<String> palabrasListado = new ArrayList<>();
    System.out.println("current dir = " + System.getProperty("user.dir"));
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
      while ((line = br.readLine()) != null)
        palabrasListado.add(line); 
    } catch (IOException e) {
      e.printStackTrace();
      throw new RevisionDiariaException(e.getMessage());
    } 
    return palabrasListado;
  }
  
  private static void openUrl(String strUrl) throws IOException {
    String osName = System.getProperty("os.name");
    try {
      if (osName.startsWith("Win")) {
        Process process = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + strUrl);
      } else if (osName.startsWith("Mac")) {
    	  //TODO: revisar causa no funciona en mac.
//    	  Runtime.getRuntime().exec("open " + strUrl);
    	  ProcessBuilder pb = new ProcessBuilder("open", strUrl);
    	  Process p = pb.start();
      } else {
        System.out.println("Please open a browser and go to " + strUrl);
      }
    
    } catch (IOException e) {
      System.out.println("Failed to start a browser to open the url " + strUrl);
      throw new IOException("Failed to start a browser to open the url " + strUrl, e);
    }
  }
}
