package pipeline;


public class MainTest 
{
    public static void main(String[] args) throws InterruptedException 
    {
        MainPipeline cp= new MainPipeline(Global.ocr, 50001);
        
        cp.startMainThread();
        Thread.sleep(500);
        System.out.println("---------------------------------------------THREAD: "+cp.getNumberOfWorkingThread());
        Thread.sleep(15000);
        cp.stopMainThread();
        
        Thread.sleep(5000);
        cp.startMainThread(); 
        
        Thread.sleep(2000);
        cp.setMaxNumberOfWorkingThread(20);
        System.out.println("---------------------------------------------THREAD: "+cp.getNumberOfWorkingThread());
        Thread.sleep(15000);
        
        cp.stopMainThread();
       
    }
    
}
