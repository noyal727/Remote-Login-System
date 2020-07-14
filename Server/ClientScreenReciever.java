import java.awt.Graphics;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


class ClientScreenReciever extends Thread {

    private ObjectInputStream cObjectInputStream = null;
    private JPanel cPanel = null;
    private boolean continueLoop = true;
	InputStream oin = null;
	Image image1 = null;
    public ClientScreenReciever(InputStream ois, JPanel p) {
        //cObjectInputStream = ois;
		oin = ois;
        cPanel = p;
        //start the thread and thus call the run method
        start();
    }

    public void run(){
        
            /*try {
                
                //Read screenshots of the client then draw them
                while(continueLoop){
                    //Recieve client screenshot and resize it to the current panel size
                    ImageIcon imageIcon = (ImageIcon) cObjectInputStream.readObject();
                    System.out.println("New image recieved");
                    Image image = imageIcon.getImage();
                    image = image.getScaledInstance(cPanel.getWidth(),cPanel.getHeight()
                                                        ,Image.SCALE_FAST);
                    //Draw the recieved screenshot
                    Graphics graphics = cPanel.getGraphics();
                    graphics.drawImage(image, 0, 0, cPanel.getWidth(),cPanel.getHeight(),cPanel);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
          } catch(ClassNotFoundException ex){
              ex.printStackTrace();
          }*/
		  try{
			//Read screenshots of the client and then draw them
			while(continueLoop){
				byte[] bytes = new byte[1024*1024];
				int count = 0;
				do{
					count+=oin.read(bytes,count,bytes.length-count);
				}while(!(count>4 && bytes[count-2]==(byte)-1 && bytes[count-1]==(byte)-39));

				image1 = ImageIO.read(new ByteArrayInputStream(bytes));
				image1 = image1.getScaledInstance(cPanel.getWidth(),cPanel.getHeight(),Image.SCALE_FAST);

				Graphics graphics = cPanel.getGraphics();
				graphics.drawImage(image1, 0, 0, cPanel.getWidth(), cPanel.getHeight(), cPanel);
			}

		} catch(IOException ex) {
			ex.printStackTrace();
		}
     }
}
