import java.util.concurrent.CountDownLatch;


public class ThreadExample
{
    public static void main(String[] args)
    {
        CountDownLatch latch = new CountDownLatch(1);
        MyThread t1 = new MyThread(latch, "C:\\Users\\Jeffrey Li\\drum-machine\\src\\clap.mp3");
        MyThread t2 = new MyThread(latch, "C:\\Users\\Jeffrey Li\\drum-machine\\src\\snare2.mp3");
        new Thread(t1).start();
        new Thread(t2).start();
        //Do whatever you want
        latch.countDown();          //This will inform all the threads to start
        //Continue to do whatever
    }
}

class MyThread implements Runnable
{
    CountDownLatch latch;
    String pathname;
    public MyThread(CountDownLatch latch, String pathname)
    {
        this.latch = latch;
        this.pathname = pathname;
    }
    @Override
    public void run()
    {
        try
        {
            latch.await();          //The thread keeps waiting till it is informed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Do the actual thing
    }
}