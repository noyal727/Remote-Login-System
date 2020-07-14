import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;	
import java.net.Socket;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

class ScreenSpyer extends Thread {

    /*Socket socket = null; 
    Robot robot = null; 
    Rectangle rectangle = null;
    boolean continueLoop = true; 
    
    public ScreenSpyer(Socket socket, Robot robot,Rectangle rect) {
        this.socket = socket;
        this.robot = robot;
        rectangle = rect;
        start();
    }

    public void run(){
        ObjectOutputStream oos = null; 


        try{
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(rectangle);
        }catch(IOException ex){
            ex.printStackTrace();
        }

       while(continueLoop){
            BufferedImage image = robot.createScreenCapture(rectangle);
            ImageIcon imageIcon = new ImageIcon(image);
            try {
                System.out.println("before sending image");
                oos.writeObject(imageIcon);
                oos.reset(); 
                System.out.println("New screenshot sent");
            } catch (IOException ex) {
               ex.printStackTrace();
            }
			
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }

    }*/
	Socket socket=null;
	Robot robot=null;
	Rectangle rectangle=null;
	boolean continueLoop=true;
	
	OutputStream oos=null;

	public ScreenSpyer(Socket socket,Robot robot,Rectangle rect) {
		this.socket=socket;
		this.robot=robot;
		rectangle=rect;
		start();
	}

	public void run(){
		ObjectOutputStream obs = null; 
	
		try{
			oos=socket.getOutputStream();
			obs = new ObjectOutputStream(oos);
            obs.writeObject(rectangle);
			
		}catch(IOException ex){
			ex.printStackTrace();
		}

		while(continueLoop){
			BufferedImage image=robot.createScreenCapture(rectangle);

			try{
				ImageIO.write(image,"jpeg",oos);
			}catch(IOException ex){
				ex.printStackTrace();
			}
	
			try{
				Thread.sleep(10);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

}
