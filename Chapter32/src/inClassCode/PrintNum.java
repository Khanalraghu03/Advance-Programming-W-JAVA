package inClassCode;

// The task class for printing number from 1 to n for a given n
class PrintNum implements Runnable
{
    private int lastNum;

    /** Construct a task for printing 1, 2, ... i */
    public PrintNum(int n)
    {
        lastNum = n;
    }

    @Override
    /** Tell the thread how to run */
    public void run()
    {
        for (int i = 1; i <= lastNum; i++)
        {
            System.out.print(" " + i);
        }
    }
}
