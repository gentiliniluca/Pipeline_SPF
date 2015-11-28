package pipeline;

public class MainPipeline 
{
    Thread mainThread;
    MainServerThread countObjMain;
    String action;
    int port;
    
    public MainPipeline(String action, int port)
    {
        this.action=action;
        this.port=port;
    }
    
    public void startMainThread()
    {

        countObjMain= new MainServerThread(action, port);
        mainThread= new Thread(countObjMain);
        mainThread.start(); 
        System.out.println("Avvio il thread principale  Main Server thread");
        
    }
    
    public void stopMainThread()
    {
        countObjMain.closeMainThread();
        System.out.println("Fermo il thread principale Count Object");
        countObjMain= null;
    }
    
    public void setMaxNumberOfWorkingThread(int n)
    {
        if(countObjMain!=null)
            countObjMain.setMaxWorkingThread(n);
        else
            System.out.println("CountObjMain non e istanziato!!");
            
    }
    
    public int getNumberOfWorkingThread()
    {
        if(countObjMain!=null)
            return countObjMain.getNumberWorkingThread();
        else
            return -1;
    }
    
    
    
}
