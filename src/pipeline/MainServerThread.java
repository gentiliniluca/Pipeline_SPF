package pipeline;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainServerThread implements Runnable
{
    private ServerSocket server_sock;
    private boolean continium=true;
    private static ThreadPoolExecutor executor;
    String action;
    int port;
    
    public MainServerThread(String action, int port)
    {
        this.action=action;
        this.port=port;
    }
    @Override
    public void run() 
    {        
        continium=true;
        try {   
            executor = new ThreadPoolExecutor(2,// core thread pool size
                                          20, // maximum thread pool size anche numeri alti...
                                          0L, // time to wait before resizing pool
                                          TimeUnit.MILLISECONDS,
                                          new LinkedBlockingQueue<Runnable>());
            
            server_sock= new ServerSocket(port);
        
            while(continium)
            {
                Socket client_sock= server_sock.accept();
                
                ObjectWorkerThread workerObject = new ObjectWorkerThread(client_sock, action);
                executor.execute(workerObject);
            }
        
        } catch (IOException ex) {
            System.out.println("errore/interruzione IO server main thread: "+ex);
        }
        catch (Exception e) {
            System.out.println("errore server main thread: "+e);
        }
    }
    
    public void setMaxWorkingThread(int n)
    {
        executor.setCorePoolSize(n);
    }
    
    public int getNumberWorkingThread()
    {
        return executor.getCorePoolSize();
    }
    
    public void closeMainThread() 
    {
        try {
            continium=false;
            server_sock.close();
        } catch (IOException ex) {
            System.out.println("Errore chiusura serversocket: "+ex);      
        }
        
    }
    
}
