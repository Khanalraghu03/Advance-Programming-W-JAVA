package inClassCode;

// The task for printing a specified character in specified times
class PrintChar implements Runnable
{
    private char charToPrint; // The character to print
    private int times; // The times to repeat

    /**
     * Construct a task with specified character and number of times to print
     * the character
     */
    public PrintChar(char c, int t)
    {
        charToPrint = c;
        times = t;
    }

    @Override
    /** Override the run() method to tell the system
     *  what the task to perform
     */
    public void run()
    {
        Thread thread4 = new Thread(new PrintChar('v', 5));
        for (int i = 0; i < times; i++)
        {
            System.out.print(charToPrint);
            if(i == 50) {
                try {
                    thread4.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

