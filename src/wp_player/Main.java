// -*- Mode: java -*-
// Copyright(C) 2021 yamakatsu
// --
package wp_player;

import java.awt.Window;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import java.awt.*;


public final class Main extends Window implements MouseListener {

    private static final long  serialVersionUID = 1L;
    private PropertyWindow     property;
    private CommandManager     commander;
    private Dimension          screen;
    private BufferedImage      pic_image;
    private BufferedImage      trans_image;
    private BufferedImage      double_buffer;
    private int                last_w, last_h;
    private float              transparency;
    private Timer              trans_timer;


    public static void main(String args[]) {
	new Main();
    }

    Main() {
	super(null);

	property = new PropertyWindow(this);
	commander = new CommandManager(this, property);
	screen = getToolkit().getScreenSize();
	pic_image = trans_image = double_buffer = null;
	last_w = last_h = -1;

        addMouseListener(this);
	setBackground(Color.black);
	setVisible(true);
	toBack();

	commander.start();
	// ファイルエラー時はまだ大きさがなく repaint() されない為 paint()
	paint(getGraphics());
    }

    protected void setNextImage(BufferedImage img) {
	trans_image = img;
	transparency = (float)0.0;

	TimerTask task = new TimerTask() {
		public void run() {
		    transparency += (float)0.2;
		    if ( transparency >= (float)1.0 ) {
			transparency = (float)1.0;
			trans_timer.cancel();
			pic_image = trans_image;
			trans_image.flush();
			trans_image = null;
		    }
		    //repaint(); ではチラつく
		    paint(getGraphics());
		}
	    };
	trans_timer = new Timer(false);
	trans_timer.schedule(task, 0, 30);
    }

    public void paint(Graphics g) {
	int pw = 400;
	int ph = 40;
	if ( pic_image != null ) {
	    pw = pic_image.getWidth();
	    ph = pic_image.getHeight();
	}

	int um = property.getUMargin();
	int bm = property.getBMargin();
	int lm = property.getLMargin();
	int rm = property.getRMargin();
	int ix = 0;
	int iy = 0;
	int sx = ((int)screen.getWidth()-pw)/2;
	int sy = ((int)screen.getHeight()-ph)/2;
	if ( sx < lm ) {
	    ix = -(lm - sx);
	    sx = lm;
	}
	if ( sy < um) {
	    iy = -(um - sy);
	    sy = um;
	}
	int sw = pw;
	int sh = ph;
	if ( (lm + sw + rm) > (int)screen.getWidth() )
	    sw = (int)screen.getWidth() - lm - rm;
	if ( (um + sh + bm) > (int)screen.getHeight() )
	    sh = (int)screen.getHeight() - um - bm;

	if ( sw != last_w || sh != last_h ) {
	    last_w = sw;
	    last_h = sh;
	    g.setColor(Color.black);
	    g.fillRect( 0,  0, sw, sh ); // 効果なく一瞬チラつく
	    setBounds( sx, sy, sw, sh );
	    double_buffer =
		new BufferedImage(sw, sh, BufferedImage.TYPE_INT_ARGB);
	}

	Graphics2D gc = double_buffer.createGraphics();
	if ( pic_image != null ) {
	    gc.drawImage( pic_image, ix, iy, this );
	}
	if ( trans_image != null ) {
	    gc.setComposite(
		AlphaComposite.getInstance(
		    AlphaComposite.SRC_OVER, transparency));
	    gc.drawImage( trans_image, ix, iy, this );
	}
	gc.dispose();

	g.drawImage( double_buffer, 0, 0, this );
    }

    @Override
    public void toFront() {
	super.setAlwaysOnTop(true);
	super.toFront();
	super.requestFocus();
	super.setAlwaysOnTop(false);
    }

    // MouseListener interface.
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e)  {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e){}
    public void mousePressed(MouseEvent e) {
	if ( (e.getModifiers() & InputEvent.BUTTON3_MASK) != 0 ) {
            commander.show(e.getPoint());
	}
    }
}
