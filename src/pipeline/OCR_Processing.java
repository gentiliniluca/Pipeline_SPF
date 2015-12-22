
package pipeline;


import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.sourceforge.tess4j.*;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class OCR_Processing {

    public static String performOCR_String2Text(File x) throws IOException 
    {
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
         File imageFile = x;   //da  usare senza filtro in grigio
         
        // long startTime = System.currentTimeMillis();
         String result="";
         
         File input = x;      //da  usare CON filtro in grigio mettendo il risultato output
         BufferedImage image = ImageIO.read(input);	

         byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
         Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
         mat.put(0, 0, data);

         Mat mat1 = new Mat(image.getHeight(),image.getWidth(),CvType.CV_8UC1);
         Imgproc.cvtColor(mat, mat1, Imgproc.COLOR_RGB2GRAY);

         byte[] data1 = new byte[mat1.rows() * mat1.cols() * (int)(mat1.elemSize())];
         mat1.get(0, 0, data1);
         BufferedImage image1 = new BufferedImage(mat1.cols(),mat1.rows(), BufferedImage.TYPE_BYTE_GRAY);
         image1.getRaster().setDataElements(0, 0, mat1.cols(), mat1.rows(), data1);

         File output = new File("src/pipeline/outputImg/"+Global.getJPGNameFile()+"grayscale.jpg");
         ImageIO.write(image1, "jpg", output);
         
         
         if(output.exists()){
            try {
                Tesseract1 instance = new Tesseract1(); 
                result = instance.doOCR(output); ////file da elaborare

            } catch (TesseractException e) {
                System.err.println(e.getMessage());
            } 
         }
         else
         {
             System.out.println("File not found");
             result="file not found";
         }
 //        System.out.println("\t\t\t->"+"src/pipeline/outputImg/"+Global.getJPGNameFile()+"grayscale.jpg");
         //System.out.println( "\t\t\t->"+result); 
         output.delete();
         return result;
         
         /*long endTime   = System.currentTimeMillis();
long totalTime = endTime - startTime;
System.out.println(totalTime);*/
    }
    
    
}