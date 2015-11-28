package pipeline;

import java.io.File;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

public class Count_Processing 
{
    static String CountObject( File x) //String FileName,
    {
        try{
            //carico la libreria che mi serve per eseguire il count
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

            //l'oggetto CascadecClassifier mi permette di prelevare l'xml che contiene 
            //il training di quello che voglio estrapolare
            CascadeClassifier objDetector = new CascadeClassifier(Count_Processing.class.getResource("cars3.xml").getPath());

            //carico l'immagine che voglio elaborare
            //Mat image = Highgui.imread(Core_Processing.class.getResource(completeName).getPath());
            Mat image = Highgui.imread(x.getPath());
            
            //faccio la ricerca in image degli elementi descritti dall'xml
            MatOfRect objDetections = new MatOfRect();
            objDetector.detectMultiScale(image, objDetections);

            //System.out.println(String.format("Trovati %s obj", objDetections.toArray().length));

            //disegno i rettangoli negli elementi trovati
            for (Rect rect : objDetections.toArray()) {
                Core.rectangle(image, new Point(rect.x, rect.y), 
                        new Point(rect.x + rect.width, rect.y + rect.height),
                        new Scalar(0, 255, 0));
            }

            //scrivo il file risultato
            /*String filenameOut = "/home/luca/Desktop/Pipeline/src/pipeline/outputImg/"+x.getName();
            //System.out.println(String.format("Scrivo %s", filenameOut));
            Highgui.imwrite(filenameOut, image);*/
            
            return ""+objDetections.toArray().length;
        }catch(Exception e)
        {
            System.out.println("Eccezione Core Processing: "+e);
        }
        
        return ":-(";
    }

      
}
