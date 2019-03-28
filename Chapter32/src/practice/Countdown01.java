package practice;

public class Countdown01 implements Runnable{
    private int startingNum;

    public Countdown01(int startingNum) {
        this.startingNum = startingNum;
    }

    public static void main(String[] args) {
        Runnable run = new Countdown01(50);
        Runnable run1 = new Countdown01(25);

        Thread thread0 = new Thread(run);
        Thread thread1 = new Thread(run1);

        //Thread thread0 = new Thread(new Countdown01(50));
        //Thread thread1 = new Thread(new Countdown01(25));

        thread0.start();
        thread1.start();
    }

    @Override
    public void run() {
        for(int i = startingNum; i > startingNum - 10; i--) {
            System.out.println(i);
            try {
                    Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                    e.printStackTrace();
            }

        }
    }
}
