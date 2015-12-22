/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipeline;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;



public class Global 
{
    //numero di secondi della finestra temporale per diminuire la priorità delle richieste
    static int TimeWindows = 3000;
    
    
    //operazioni consentite
    static String count="count";
    static String ocr="ocr";
    
    
    //settare priorità per le varie applicazioni ems, police, participant
    static HashMap<String, String > priority = new HashMap<String, String>(){{
        put("police","0.95");
        put("ems","1");
        put("participant","0.5");
    }};
    
    //stima numero utenti per ogni applicazione
    static HashMap<String, String > stimaNumeroRichieste = new HashMap<String, String>(){{
        put("police","10");
        put("ems","12");
        put("participant","200");
    }};
    
    //valori reali delle richieste servite con decremento
    static HashMap<String, String > NumeroRichiesteEffettive = new HashMap<String, String>(){{
        put("police","8");
        put("ems","8");
        put("participant","8");
    }};  
    
    
    public static void decrementaTotaleNumeroRichiesteEffettive()
    {
        decrementaNumeroRichiesteEffettive("police");
        decrementaNumeroRichiesteEffettive("ems");
        decrementaNumeroRichiesteEffettive("participant");
    }
    
    
    public static void decrementaNumeroRichiesteEffettive(String app)
    {
        int value= Integer.parseInt(NumeroRichiesteEffettive.get(app));
        NumeroRichiesteEffettive.remove(app);
        if (value >0)
            value--;
        NumeroRichiesteEffettive.put(app, ""+value);      
    }
    
    public static void IncrementaNumeroRichiesteEffettive(String app)
    {
        int value= Integer.parseInt(NumeroRichiesteEffettive.get(app));
        NumeroRichiesteEffettive.remove(app);
        if( value < getStimaNumeroRichieste(app))
            value++;
        NumeroRichiesteEffettive.put(app, ""+value);  
    }
    
    public static int getNumeroRichiesteEffettive(String app)
    {
        return Integer.parseInt(NumeroRichiesteEffettive.get(app));  
    }
    
    public static double getApplicationPriority(String app)
    {
        return Double.parseDouble(priority.get(app));  
    }
    
    public static int getStimaNumeroRichieste(String app)
    {
        return Integer.parseInt(stimaNumeroRichieste.get(app));  
    }
    
    
    public static String getJPGNameFile()
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
    }
       
}
