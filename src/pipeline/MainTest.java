package pipeline;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import javax.swing.Timer;


public class MainTest 
{
    static Timer timer;
    public static void main(String[] args) throws InterruptedException 
    {
        //gestione timer
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                Global.decrementaTotaleNumeroRichiesteEffettive();
                System.out.println(Global.getNumeroRichiesteEffettive("ems")+" "+Global.getNumeroRichiesteEffettive("police")+" "+Global.getNumeroRichiesteEffettive("participant"));
            }
        };
        timer = new Timer( Global.TimeWindows, actionListener );
        timer.start();
        Results.init();
        
        String operazioneCheSiVuoleFare=Global.ocr;
        
        MainPipeline pipeline= new MainPipeline(operazioneCheSiVuoleFare, 50000);
        
        pipeline.startMainThread();
        Thread.sleep(500);
        System.out.println("---------------------------------------------THREAD: "+pipeline.getNumberOfWorkingThread());
        Thread.sleep(15000);
        
        
       //simulo la richiesta del client
        System.out.println("simulooo....");
        java.util.Date date= new java.util.Date(); 
        Results.createFinalList(operazioneCheSiVuoleFare, 40.0 , 11.0 , new Timestamp(date.getTime()));
        Results.stampaArrayFinale();
      
        pipeline.stopMainThread();
        
        Thread.sleep(5000);
        pipeline.startMainThread(); 
        
        Thread.sleep(2000);
        pipeline.setMaxNumberOfWorkingThread(20);
        System.out.println("---------------------------------------------THREAD: "+pipeline.getNumberOfWorkingThread());
        Thread.sleep(15000);
        
        pipeline.stopMainThread();
        
          
     /*   
        System.out.println(Global.count);
        Global.count="prova";
        System.out.println(Global.count);
        
        Global.getApplicationPriority("ems");
      */
        /*
        //prova gps
        String nome="prova1.jpg";
        File f= new File(nome);
        if(f.exists())
        {
            System.out.println("File ce");
            javaxt.io.Image image = new javaxt.io.Image(nome);
            double[] coordinate = image.getGPSCoordinate();
            if (coordinate!=null)
                System.out.println("Coordinate GPS: " + coordinate[0] + ", " + coordinate[1]);
            
         
        }
         */     
        
       
    }
    
}
