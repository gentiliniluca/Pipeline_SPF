/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipeline;

import java.sql.Timestamp;


public class SingleResult 
{
    String source;
    String operation;
    String value;
    Timestamp TimeStamp;
    double gpsLat;
    double gpsLong;
    double halfVOI;
    double finalVOI;
    
    public SingleResult(String source,String operation, String value, Timestamp Timestamp, double gpsLat, double gpsLong, double halfVOI)
    {
        this.source=source;
        this.operation=operation;
        this.value=value;
        this.TimeStamp=Timestamp;
        this.gpsLat=gpsLat;
        this.gpsLong=gpsLong;
        this.halfVOI=halfVOI;
        this.finalVOI=0;
    }
    
    @Override
    public String toString()
    {
        return "source: "+source+", operation: "+operation+", value: "+value+", voi: "+finalVOI;
    }
}
