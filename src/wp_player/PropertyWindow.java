// -*- Mode: java -*-
// Copyright(C) 2021 yamakatsu
// --
package wp_player;

import java.util.Properties;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;
import java.awt.*;
import javax.swing.*;


public class PropertyWindow extends JDialog implements ActionListener {

    private static final long    serialVersionUID = 1L;    
    private static final String  PROPERTIES_FILE  = "wpp.properties";
    private Main                 main;
    private Properties           properties;

    // properties ïœêî
    private String dirPath;
    private int u_margin, b_margin, l_margin, r_margin;
    private int interval;
    private String prefix;

    // properties User Interface
    JTextField dirPathTF;
    JButton dirPathB;
    JTextField u_marginTF, b_marginTF, l_marginTF, r_marginTF;
    JButton okB, applyB, closeB;
    JTextField intervalTF;
    JTextField prefixTF;

    PropertyWindow(Main ma) {
	super();
	main = ma;
	setTitle("Wallpaper Player ver 1.0");

        properties = new Properties();
	try {
	    BufferedInputStream in =
		new BufferedInputStream(
		    new FileInputStream( PROPERTIES_FILE ));
	    properties.load(in);
	    in.close();
	} catch(FileNotFoundException ffx) {
	} catch(IOException iox) {}
	dirPath = properties.getProperty("dirPath","/");	
	u_margin = Integer.valueOf(properties.getProperty("u_margin","20"));
	b_margin = Integer.valueOf(properties.getProperty("b_margin","20"));
	l_margin = Integer.valueOf(properties.getProperty("l_margin","20"));
	r_margin = Integer.valueOf(properties.getProperty("r_margin","20"));
	interval = Integer.valueOf(properties.getProperty("interval","3"));
	prefix = properties.getProperty("prefix","");

	JLabel lbl;
	GridBagLayout gridbag = new GridBagLayout();
	GridBagConstraints c = new GridBagConstraints();
	setLayout(gridbag);
	//
	lbl = new JLabel("Picture Directory");
	lbl.setPreferredSize(new Dimension(200,25));
	c.insets = new Insets(1, 2, 1, 1);
	c.gridwidth = 2;
	gridbag.setConstraints(lbl,c);
	add(lbl);

	dirPathB = new JButton("Dir Select");
	dirPathB.setPreferredSize(new Dimension(100,30));
	dirPathB.setActionCommand("Dir Select");
	dirPathB.addActionListener(this);
	c.insets = new Insets(1, 1, 1, 2);
	c.gridwidth = GridBagConstraints.REMAINDER;
	gridbag.setConstraints(dirPathB, c);
	add(dirPathB);

	dirPathTF = new JTextField();
	dirPathTF.setPreferredSize(new Dimension(300,25));
	c.insets = new Insets(1, 2, 1, 2);
	c.gridwidth = GridBagConstraints.REMAINDER;
	gridbag.setConstraints(dirPathTF, c);
	add(dirPathTF);
	//
	lbl = new JLabel("Upper margin");
	lbl.setPreferredSize(new Dimension(200,25));
	c.insets = new Insets(1, 2, 1, 1);
	c.gridwidth = 2;
	gridbag.setConstraints(lbl,c);
	add(lbl);

	u_marginTF = new JTextField();
	u_marginTF.setPreferredSize(new Dimension(100,25));
	c.insets = new Insets(1, 1, 1, 2);
	c.gridwidth = GridBagConstraints.REMAINDER;
	gridbag.setConstraints(u_marginTF, c);
	add(u_marginTF);

	lbl = new JLabel("Bottom margin");
	lbl.setPreferredSize(new Dimension(200,25));
	c.insets = new Insets(1, 2, 1, 1);
	c.gridwidth = 2;
	gridbag.setConstraints(lbl,c);
	add(lbl);

	b_marginTF = new JTextField();
	b_marginTF.setPreferredSize(new Dimension(100,25));
	c.insets = new Insets(1, 1, 1, 2);
	c.gridwidth = GridBagConstraints.REMAINDER;
	gridbag.setConstraints(b_marginTF, c);
	add(b_marginTF);

	lbl = new JLabel("Left margin");
	lbl.setPreferredSize(new Dimension(200,25));
	c.insets = new Insets(1, 2, 1, 1);
	c.gridwidth = 2;
	gridbag.setConstraints(lbl,c);
	add(lbl);

	l_marginTF = new JTextField();
	l_marginTF.setPreferredSize(new Dimension(100,25));
	c.insets = new Insets(1, 1, 1, 2);
	c.gridwidth = GridBagConstraints.REMAINDER;
	gridbag.setConstraints(l_marginTF, c);
	add(l_marginTF);

	lbl = new JLabel("Right margin");
	lbl.setPreferredSize(new Dimension(200,25));
	c.insets = new Insets(1, 2, 1, 1);
	c.gridwidth = 2;
	gridbag.setConstraints(lbl,c);
	add(lbl);

	r_marginTF = new JTextField();
	r_marginTF.setPreferredSize(new Dimension(100,25));
	c.insets = new Insets(1, 1, 1, 2);
	c.gridwidth = GridBagConstraints.REMAINDER;
	gridbag.setConstraints(r_marginTF, c);
	add(r_marginTF);
	//
	lbl = new JLabel("Interval (sec)");
	lbl.setPreferredSize(new Dimension(200,25));
	c.insets = new Insets(1, 2, 1, 1);
	c.gridwidth = 2;
	gridbag.setConstraints(lbl,c);
	add(lbl);

	intervalTF = new JTextField();
	intervalTF.setPreferredSize(new Dimension(100,25));
	c.insets = new Insets(1, 1, 1, 2);
	c.gridwidth = GridBagConstraints.REMAINDER;
	gridbag.setConstraints(intervalTF, c);
	add(intervalTF);
	//
	lbl = new JLabel("Prefix");
	lbl.setPreferredSize(new Dimension(200,25));
	c.insets = new Insets(1, 2, 1, 1);
	c.gridwidth = 2;
	gridbag.setConstraints(lbl,c);
	add(lbl);

	prefixTF = new JTextField();
	prefixTF.setPreferredSize(new Dimension(100,25));
	c.insets = new Insets(1, 1, 1, 2);
	c.gridwidth = GridBagConstraints.REMAINDER;
	gridbag.setConstraints(prefixTF, c);
	add(prefixTF);
	//
	okB = new JButton("OK");
	okB.setPreferredSize(new Dimension(100,30));
	okB.setActionCommand("ok");
	okB.addActionListener(this);
	c.insets = new Insets(1, 2, 1, 1);
	c.gridwidth = 1;
	gridbag.setConstraints(okB, c);
	add(okB);

	applyB = new JButton("Apply");
	applyB.setPreferredSize(new Dimension(100,30));
	applyB.setActionCommand("apply");
	applyB.addActionListener(this);
	c.insets = new Insets(1, 1, 2, 1);
	c.gridwidth = 1;
	gridbag.setConstraints(applyB, c);
	add(applyB);

	closeB = new JButton("Close");
	closeB.setPreferredSize(new Dimension(100,30));
	closeB.setActionCommand("close");
	closeB.addActionListener(this);
	c.insets = new Insets(1, 1, 2, 2);
	c.gridwidth = GridBagConstraints.REMAINDER;
	gridbag.setConstraints(closeB, c);
	add(closeB);

	pack();
    }

    protected void open(Point p) {
	setLocation( p.x + 5, p.y + 5 );
	set();
	setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
	String cmd = e.getActionCommand();

	if ( cmd.equals("Dir Select") ) {
	    JFileChooser fc = new JFileChooser(dirPath);
	    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    int selected = fc.showOpenDialog(main);
	    if ( selected == JFileChooser.APPROVE_OPTION ) {
		File dir = fc.getSelectedFile();
		if ( dir.isDirectory() ) {
		    dirPathTF.setText(dir.getPath());
		}
	    }

	} else if ( cmd.equals( "ok") ) {
	    get();
	    setVisible(false);
	} else if ( cmd.equals( "apply") ) {
	    get();
	} else if ( cmd.equals( "close") ) {
	    setVisible(false);
	} else {
	    if ( e.getSource() instanceof JTextField )
		get();
	}

	main.repaint();
    }

    // ïœêîílÇÇtÇhÇ…ê›íËÇ∑ÇÈ
    void set() {
	dirPathTF.setText(dirPath);
	u_marginTF.setText(String.valueOf(u_margin));
	b_marginTF.setText(String.valueOf(b_margin));
	l_marginTF.setText(String.valueOf(l_margin));
	r_marginTF.setText(String.valueOf(r_margin));
	intervalTF.setText(String.valueOf(interval));
	prefixTF.setText(prefix);
    }

    // ÇtÇhê›íËÉfÅ[É^ÇïœêîÇ…ñﬂÇ∑
    void get() {
	dirPath = dirPathTF.getText();
	u_margin = Integer.valueOf(u_marginTF.getText());
	b_margin = Integer.valueOf(b_marginTF.getText());
	l_margin = Integer.valueOf(l_marginTF.getText());
	r_margin = Integer.valueOf(r_marginTF.getText());
	interval = Integer.valueOf(intervalTF.getText());
	prefix = prefixTF.getText();

	// Ç±Ç±Ç≈ê›íËÇ Property Ç…ï€ë∂ÇµÉZÅ[ÉuÇµÇƒÇ®Ç≠
	properties.setProperty("dirPath",dirPath);
	properties.setProperty("u_margin",String.valueOf(u_margin));
	properties.setProperty("b_margin",String.valueOf(b_margin));
	properties.setProperty("l_margin",String.valueOf(l_margin));
	properties.setProperty("r_margin",String.valueOf(r_margin));
	properties.setProperty("interval",String.valueOf(interval));
	properties.setProperty("prefix",prefix);
        try {
            BufferedOutputStream out =
                new BufferedOutputStream(
                    new FileOutputStream(PROPERTIES_FILE));
            properties.store(out,"Wallpaper Player Properties File");
            out.close();
        } catch(IOException iox){}	
    }

    // getter
    protected String getDirPath() { return dirPath; }
    protected int getUMargin() { return u_margin; }
    protected int getBMargin() { return b_margin; }
    protected int getLMargin() { return l_margin; }
    protected int getRMargin() { return r_margin; }
    protected int getInterval() { return interval*1000; }
    protected String getPrefix() { return prefix; }
}
