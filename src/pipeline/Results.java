/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipeline;

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
                double distanza=( sqrt( pow((sr.gpsLat-gpsLat),2) + pow((sr.gpsLong-gpsLong),2) ) );
                
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

  
    
}
