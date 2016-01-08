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
        Thread.currentThread().setName("WorkerThread");
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
        
        //processing 
        Thread.sleep(100);
        
        File x = new File("src/pipeline/receivedImg/"+nomeFile);
        if(!x.exists())
        {
            System.out.println("NON ESISTO");
        }
        else
        {
            javaxt.io.Image image = new javaxt.io.Image(x);
            double[] coordinate = image.getGPSCoordinate();
            if (coordinate==null)
                System.out.println("non ci sono coordinate gps");
            
            if(action.equals("count"))
            {
                Global.IncrementaNumeroRichiesteEffettive("police");
                
                String value=Count_Processing.CountObject(x);
                
                double halfVOI= Global.getApplicationPriority("police")*Global.getNumeroRichiesteEffettive("police")/Global.getStimaNumeroRichieste("police");
                java.util.Date date= new java.util.Date();
                SingleResult sr= new SingleResult(nomeFile, "count", value, new Timestamp(date.getTime()), coordinate[0], coordinate[1], halfVOI);
                
                //System.out.println(sr.toString());
                Results.addResult(sr);
            }
            else if(action.equals("ocr"))
            {
                Global.IncrementaNumeroRichiesteEffettive("participant");
                
                String value=TextRecognition.SplitFiles(x);
				
				if(value.indexOf("water")!=-1)  //verifico che sia almeno presente la scritta water
				{ 
					double halfVOI= Global.getApplicationPriority("participant")*Global.getNumeroRichiesteEffettive("participant")/Global.getStimaNumeroRichieste("participant");
					java.util.Date date= new java.util.Date();
					SingleResult sr= new SingleResult(nomeFile, "ocr", value, new Timestamp(date.getTime()), coordinate[0], coordinate[1], halfVOI);
					
					Results.addResult(sr);
				}
				else
					System.out.println("non presente scritta water");
            }
            //cancello il file temporaneo per non sprecare spazio
            x.delete();
        }
        
              
        
    }
}
