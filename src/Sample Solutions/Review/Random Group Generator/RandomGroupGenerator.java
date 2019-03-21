import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class: RandomGroupGenerator.java
 *
 * @author Ryan Cunico
 * @version 1.1
 * Course : ITEC 3150 Spring 2019
 * Written: January 19, 2019
 *
 * This class holds the main method and works in conjunction with the Student, StudentAL, and StudentALAL classes to
 * divide Student objects into random subgroups.  The user is asked to enter the number of groups that they would like
 * to create, and then they are asked to begin entering Student names.  If the user enters an "x" as the Student's name,
 * this signals that they would like to stop entering names.  The Students are then randomly divided into groups and
 * those groups are then printed to the console.  The Students are divided uniformly, unless there are an odd number
 * of Students, in which case one group may have one less or one more Student than the others.
 */
public class RandomGroupGenerator
    {

        public static void main (String[] args)
            {
                //Create StudentAL and StudentALAL objects
                StudentAL testStudents = new StudentAL();
                StudentALAL studentALAL = new StudentALAL();
                //Scanner to read user's input
                Scanner input = new Scanner(System.in);
                //Flag that keeps the user in the loop until they make a valid choice
                boolean valid = false;
                //First while loop keeps user inside the entire loop (containing three smaller while loops)
                while (!valid)
                    {
                        //First while loop to ensure a valid number of groups is entered
                        while (!valid)
                            {
                                try
                                    {
                                        //Sets the number of groups, throws an exception and loops if invalid
                                        System.out.print("Enter the number of groups: ");
                                        studentALAL.setNumGroups(input.nextInt());
                                        valid = true;
                                    }
                                    //Catches < 0 groups
                                catch (IOException e)
                                    {
                                        System.out.println(e.getMessage());
                                        input.nextLine();
                                    }
                                    //Catches InputMismatchException
                                catch (InputMismatchException ex)
                                    {
                                        System.out.println("Invalid input");
                                        input.nextLine();
                                    }
                            }

                        //Flag to keep the user entering Student names until they choose to stop
                        boolean exit = false;
                        input.nextLine();
                        while (!exit)
                            {
                                try
                                    {
                                        //Attempts to add the Students by name, throws Exception if "x" or blank
                                        System.out.print("Enter the student name (or x to exit): ");
                                        testStudents.addStudent(new Student(input.nextLine()));
                                    }
                                    //Catches the "x" signaling user wants to finish entering names
                                catch (IOException e)
                                    {
                                        System.out.println(e.getMessage() + "\n");
                                        exit = true;
                                    }
                                    //Catches blank names but continues looping user
                                catch (InputMismatchException ex)
                                    {
                                        System.out.println(ex.getMessage() + "\n");
                                    }
                            }

                        //Reset the flag to false for the next loop
                        valid = false;

                        {
                            try
                                {
                                    //Attempts to create the subgroups using the Students and selected number of groups
                                    studentALAL.createGroups(testStudents);
                                    valid = true;
                                }
                            catch (IOException e)
                                {
                                    testStudents.clear();
                                    System.out.println(e.getMessage());
                                    input.nextLine();
                                }
                        }
                    }

                //After Students are subdivided, print the subgroups to the console
                System.out.println(studentALAL.toString());
                System.out.println("\nThank you for using the Random Group Generator!");
                System.exit(0);


            }
    }
