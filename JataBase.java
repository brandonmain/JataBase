/**
 *  Main class for the JataBase Database Management System.
 *  This is where the application first enters and begins
 *  databasing operations.
 *
 *  by: Brandon Main
 *
 *  last edit: February 20, 2019
 */

import java.lang.*;
import java.io.*;
import java.util.*;

public class JataBase
{
    /**
     *				JataBase()
     *
     * Constructs the database management program object.
     *
     * @param      args - A string of arguments.
     */
    private JataBase(String[] args)
    {
        System.out.println("\nJataBase version 1.0 2019-02-05");
        System.out.println("Written by: Brandon Main");
        System.out.println("Enter \".EXIT\" to exit the program.");

        //If args is empty, read from command line
        //else, read from file.
        if(args.length == 0)
        {
            String input = "";

            //Read from command line
            while(!input.equals(".EXIT"))
            {
                System.out.print("\nJataBase~# ");
                input = System.console().readLine();
                Instructions.operate(input);
            }
        }
        else if(args.length == 1)
        {
            //Read from file
            readFile(args[0]);
        }
        else
        {
            System.out.println("\nJataBase~# Improper usage. " +
                    "Usage is of the form:\n\n\t\tjava JataBase <NULL | <FILE>>\n");
        }
    }

    /**
     *				readFile()
     *
     * Reads input file of instructions.
     *
     * @param      arg - A String representing an input file.
     */
    private void readFile(String arg)
    {
        File file = new File(arg);

        System.out.println("\nReading from file " + file.toString() + " ...\n");

        try
        {
            Scanner scanFile = new Scanner(file);

            //Scan through whole file and operate on instructions.
            while(scanFile.hasNext())
            {
                //Scan through String from file and operate on
                //intructions.
                Instructions.operate(scanFile.nextLine());
            }

        }
        catch(FileNotFoundException e)
        {
            System.out.println("\nJataBase~# \tFile not Found! Please restart and try again.\n");
        }
    }

    /**
     *				main()
     *
     * The main entrance to the JataBase program.
     *
     * @param      args - Command line arguments.
     */
    public static void main(String[] args)
    {
        new JataBase(args);
    }
}
