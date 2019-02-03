package review;

import java.util.Random;

public class Stuff {
    private int myInt; //numOfGroup
    private String myString; //name user input

    public Stuff() {
        Random ran = new Random();
        myInt = ran.nextInt(100); //generate random num from 0 - 99
        myString = "raghu"; //my string is "raghu"
    }

    public Stuff(int myInt, String myString) { //with int and string defined by parameter
        this.myInt = myInt;
        this.myString = myString;
    }

    public int getMyInt() {
        return myInt;
    }

    public void setMyInt(int myInt) {
        this.myInt = myInt;
    }

    public String getMyString() {
        return myString;
    }

    public void setMyString(String myString) {
        this.myString = myString;
    }

    @Override
    public String toString() {
        return "Stuff{" +
                "myInt=" + myInt +
                ", myString='" + myString + '\'' +
                '}';
    }






}
