/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipeline;

import java.io.File;
import java.util.UUID;


public class Global 
{
    static String count="count";
    static String ocr="ocr";
    
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
