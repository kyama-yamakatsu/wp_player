// -*- Mode: java -*-
// Copyright(C) 2021 yamakatsu
// --
package wp_player;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.awt.*;
import javax.swing.*;


public class CommandManager implements ActionListener {

    private final String    FileExtension = "jpg";

    private Main            main;
    private PropertyWindow  property;
    private Point           event_p;

    private String[]        pic_list;
    private int             process_pic;

    private BufferedImage   pic_image;
    private Timer           pic_timer;

    public CommandManager(Main pa, PropertyWindow prop) {
	main = pa;
	property = prop;
	event_p = new Point();

	pic_list = null;
	process_pic = 0;
	pic_image = null;
	pic_timer = null;
    }

    public void show(Point point) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem;

 	menuItem = new JMenuItem("Property");
        menuItem.addActionListener(this);
        popupMenu.add(menuItem);

	menuItem = new JMenuItem("Start");
        menuItem.addActionListener(this);
        popupMenu.add(menuItem);

	menuItem = new JMenuItem("Pause");
        menuItem.addActionListener(this);
        popupMenu.add(menuItem);

	menuItem = new JMenuItem("Play");
        menuItem.addActionListener(this);
        popupMenu.add(menuItem);

 	menuItem = new JMenuItem("to Front");
        menuItem.addActionListener(this);
        popupMenu.add(menuItem);

 	menuItem = new JMenuItem("to Back");
        menuItem.addActionListener(this);
        popupMenu.add(menuItem);

 	menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(this);
        popupMenu.add(menuItem);

        popupMenu.show( main, point.x - 5, point.y - 5 );

	// screen 座標を得る
	Point pp = main.getLocation(null);
	Point mp = popupMenu.getLocation(null);
	event_p.x = point.x + pp.x + mp.x;
	event_p.y = point.y + pp.y + mp.y;
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

	if ( cmd.startsWith("Prop") ) {
	    property.open(event_p);

	} else if ( cmd.startsWith("Start") ) {
	    start();

	} else if ( cmd.startsWith("Pause") ) {
	    if ( pic_timer != null )
		pic_timer.cancel();
	    pic_timer = null;

	} else if ( cmd.startsWith("Play") ) {
	    if ( pic_timer != null )
		pic_timer.cancel();
	    timer_start();

	} else if ( cmd.startsWith("to F") ) {
	    main.toFront();

	} else if ( cmd.startsWith("to B") ) {
	    main.toBack();

	} else if ( cmd.startsWith("Exit") ) {
	    System.exit(0);
	}
    }

    protected void start() {
	if ( pic_timer != null )
	    pic_timer.cancel();
	pic_list = null;
	process_pic = 0;
	get_pictures();
	timer_start();
    }

    private void get_pictures() {
	String currentDirectory = property.getDirPath();
	File dir = new File(currentDirectory);
	File[] file_list = dir.listFiles();
	if ( file_list == null ) {
	    System.err.println("Error! no file in " + currentDirectory);
	    return;
	}

	int t = 0;
	for( int i=0; i<file_list.length; i++ )
	    if ( file_list[i].isFile() ) {
		String file_pn = file_list[i].toString();
		if ( !file_pn.toLowerCase().endsWith(FileExtension) )
		    continue;
		String file_n = file_pn.substring(
		    file_pn.lastIndexOf(File.separator)+1,file_pn.length());
		if ( property.getPrefix().length() != 0 ) {
		    if ( !file_n.startsWith(property.getPrefix()) )
			continue;
		}
		t++;
	    }
	if ( t == 0 ) {
	    System.err.println("Error! no jpg in " + currentDirectory);
	    return;
	}

	pic_list = new String[t];
	t = 0;
	for( int i=0; i<file_list.length; i++ )
	    if ( file_list[i].isFile() ) {
		String file_pn = file_list[i].toString();
		if ( !file_pn.toLowerCase().endsWith(FileExtension) )
		    continue;
		String file_n = file_pn.substring(
		    file_pn.lastIndexOf(File.separator)+1,file_pn.length());
		if ( property.getPrefix().length() != 0 ) {
		    if ( !file_n.startsWith(property.getPrefix()) )
			continue;
		}
		pic_list[t++] = file_list[i].toString();
	    }
    }

    private void timer_start() {
	if ( pic_list == null )
	    return;

	TimerTask task = new TimerTask() {
		public void run() {
		    next();
		    main.setNextImage(pic_image);
		}
	    };
	pic_timer = new Timer(false);
	pic_timer.schedule(task, 0, property.getInterval());	
    }

    private void next() {
	if ( process_pic == pic_list.length )
	    process_pic = 0;

	try {
	    File file = new File( pic_list[process_pic] );
	    pic_image = ImageIO.read( file );

	} catch(IOException iox) {
	    System.out.println("Error! can't read picture.");
	}
	process_pic++;
    }
}
