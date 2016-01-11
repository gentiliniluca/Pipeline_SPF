/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipeline;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.sql.Timestamp;
import java.util.ArrayList;


public class Results 
{
    static ArrayList<SingleResult> list;
    static ArrayList<SingleResult> finalList;
    
    static void init()
    {
        finalList = new ArrayList();
        list = new ArrayList();
    }
    
    
    public static void addResult(SingleResult sr)
    {
        list.add(sr);
    }
    
    //questo metodo va invocato per ogni richiesta di un client secondo i dati presenti 
    //nell'array list frutto delle immagini inviate dai sensori
    //Bisognerà implementare un metodo di pulizia che toglie i Results vecchi dato il loro timestamp
    public static void createFinalList(String operation, double gpsLat, double gpsLong, Timestamp t)
    {
        for (SingleResult sr : list) 
        {
            if((sr.operation).equals(operation))
            {
                //double distanza=( sqrt( pow((sr.gpsLat-gpsLat),2) + pow((sr.gpsLong-gpsLong),2) ) );
                double distanza=distanzaGPS(sr.gpsLat,sr.gpsLong, gpsLat , gpsLong); //distanza in km
		//distanza=distanza*1000;
                
                // Calculates the difference in milliseconds.
                long millisDiff = t.getTime() - sr.TimeStamp.getTime();
                int secondi = (int) (millisDiff / 1000);
                
                //System.out.println("DEBUGGING CON FURORE: "+distanza+"  "+secondi +" "+sr.halfVOI);

                /*
                // Calculates days/hours/minutes/seconds.
                int seconds = (int) (millisDiff / 1000 % 60);
                int minutes = (int) (millisDiff / 60000 % 60);
                int hours = (int) (millisDiff / 3600000 % 24);
                int days = (int) (millisDiff / 86400000);
                */
                
                //modifico l'half voi calcolato prima
                double newhalfVOI= 1 - sr.halfVOI;
                
                //valvolo VOI finale
                double VOI= newhalfVOI*distanza*secondi;
                
                sr.finalVOI=VOI;
                finalList.add(sr);
            }
        }
    }
    
    public static void stampaArrayFinale()
    {
        if(list.size()>0)
            for(SingleResult sr : list)
            {
                System.out.println(sr.toString());
            }
        else
            System.out.println("lista finale vuota");
    }
    
    public static double distanzaGPS(double lat1, double longit1, double lat2, double longit2)
    {
        lat1=radianti(lat1);
        lat2=radianti(lat2);
        longit1=radianti(longit1);
        longit2=radianti(longit2);
        
        double distance = 0;
        
        double dist_long = longit2 - longit1;
        double dist_lat = lat2 - lat1;
        
        double pezzo1 = Math.cos(lat2)*Math.sin(dist_long);
        double pezzo11 = pezzo1*pezzo1;
        
        double pezzo2 = Math.cos(lat1)*Math.sin(lat2)-Math.sin(lat1)*Math.cos(lat2)*Math.cos(dist_long);
        double pezzo22 = pezzo2*pezzo2;
        
        double pezzo3 = Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(dist_long);
        
        double pezzo4 = Math.atan((Math.sqrt(pezzo11+pezzo22))/pezzo3);
        
        distance = pezzo4*6372;
       
        return abs(distance);
    }
    
    public static double radianti(double gradi_dec)
	{
		return gradi_dec * Math.PI / 180;
	}

  
    
}
