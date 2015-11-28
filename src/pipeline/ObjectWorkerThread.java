package pipeline;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.UUID;


public class ObjectWorkerThread implements Runnable
{
    Socket sock;
    String action;
    
    public ObjectWorkerThread(Socket s, String action)
    {
        this.sock=s;
        this.action=action;
        Thread.currentThread().setName("CountObjectWorkerThread");
    }
    
    @Override
    public void run() 
    {
        try{
            java.util.Date date= new java.util.Date();
            System.out.println(Thread.currentThread().getName()+" Start. Time = "+ new Timestamp(date.getTime()));;
            
            processCommand();
            
            Thread.sleep(100);
            System.out.println(Thread.currentThread().getName()+" End.");
        }catch(Exception e)
        {
            System.out.println("exception in worker thread : "+e);
        }
        
    }
    
    private void processCommand() throws IOException, InterruptedException 
    {        
        DataInputStream in = new DataInputStream(sock.getInputStream());
        byte buffer[] = new byte[1024];
        int bytesRead;
                
        String nomeFile=Global.getJPGNameFile();
        FileOutputStream fileOut = new FileOutputStream("src/pipeline/receivedImg/"+nomeFile);
        while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
                    fileOut.write(buffer, 0, bytesRead);
        }
        fileOut.close();
        sock.close();
        
        //processing del count objects
        Thread.sleep(100);
        
        File x = new File("src/pipeline/receivedImg/"+nomeFile);
        if(!x.exists())
        {
            System.out.println("NON ESITOOOOOOOOOOOOOOOOOOOOO");
        }
        else
        {
            if(action.equals("count"))
            {
                System.out.println("oggetti rilevati per "+nomeFile+" sono "+ Count_Processing.CountObject(x));
            }
            else if(action.equals("ocr"))
            {
                TextRecognition.SplitFiles(x);
            }
            //cancello il file temporaneo per non sprecare spazio
            //x.delete();
        }
        
              
        
    }
    
         
    
    /*public String getNameFile()
    {
        String fileName;
        File file;
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();;
        
        fileName=(Thread.currentThread().getName())+randomUUIDString+".jpg";
        file= new File(fileName);
        //verifico che il fileName non sia gia presente
        while(file.exists())
        {   
             fileName=(Thread.currentThread().getName())+randomUUIDString+".jpg";
             file= new File(fileName);
        }
        
        return fileName;
    }*/
    
}
