import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

class ClientHandler extends Thread {

    private JDesktopPane desktop = null;
    private Socket cSocket = null;
    private JInternalFrame interFrame = new JInternalFrame("Client Screen", true, true, true);
    private JPanel cPanel = new JPanel();
    
    public ClientHandler(Socket cSocket, JDesktopPane desktop){
        this.cSocket = cSocket;
        this.desktop = desktop;
        start();
	try{
	    join();
	}catch(Exception ex){
	    ex.printStackTrace();
	}
    }
    public void drawGUI(){
        interFrame.setLayout(new BorderLayout());
        interFrame.getContentPane().add(cPanel,BorderLayout.CENTER);
        interFrame.setSize(100,100);
        desktop.add(interFrame);
        try {
            interFrame.setMaximum(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        cPanel.setFocusable(true);
        interFrame.setVisible(true);
    }

    public void run(){
        Rectangle clientScreenDim = null;
	InputStream in = null;
        ObjectInputStream ois = null;
        drawGUI();
	try{
	    in=cSocket.getInputStream();
            ois = new ObjectInputStream(in);
	    clientScreenDim =(Rectangle) ois.readObject();
        }catch(IOException ex){
            ex.printStackTrace();
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
        new ClientScreenReciever(in,cPanel);
        new ClientCommandsSender(cSocket,cPanel,clientScreenDim);
    }

}
