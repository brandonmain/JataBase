/**
 * Class that handles the instructions or commands that
 * are issued from file or from the command line.
 *
 * by: Brandon Main
 *
 * last edit: February 20, 2019
 */

import java.lang.*;
import java.io.*;
import java.util.*;

final class Instructions
{
    //A String object to switch between what database
    //instructions are being issued to.
    private static File currentDatabase;

    /**
     *				default constructor()
     *
     * Make constructor private so there cannot be
     * any instances of the class.
     */
    private Instructions(){}


    /**
     *				operate()
     *
     * Function that operates on an input instruction and determines
     * what to be done next.
     *
     * @param      instruction  The instruction input from file or CLI.
     */
    static void operate(String instruction)
    {
        //Get instruction
        Scanner scanString = new Scanner(instruction);
        scanString.useDelimiter(" |;");
        String next;

        //Scan and operate
        if(scanString.hasNext())
        {
            next = scanString.next();

            if (next.equalsIgnoreCase("CREATE"))
            {
                create(scanString);
            }
            else if (next.equalsIgnoreCase("DROP"))
            {
                drop(scanString);
            }
            else if (next.equalsIgnoreCase("USE"))
            {
                use(scanString);
            }
            else if (next.equalsIgnoreCase("SELECT"))
            {
                select(scanString);
            }
            else if (next.equalsIgnoreCase("ALTER"))
            {
                alter(scanString);
            }
            else if (next.equalsIgnoreCase(".EXIT"))
            {
                System.exit(0);
            }
        }

        scanString.close();
    }

    /**
     *					create()
     *
     * This function is called when the "CREATE" keyword
     * is input in an instruction. It determines what will be
     * created, e.g. a database or table.
     *
     * @param      scanString  A scanner object with the rest of
     * 						   the instruction to be parsed.
     */
    private static void create(Scanner scanString)
    {
        String next;

        if(scanString.hasNext())
        {
            next = scanString.next();

            if(next.equalsIgnoreCase("DATABASE"))
            {
                createDatabase(scanString);
            }
            else if(next.equalsIgnoreCase("TABLE"))
            {
                createTable(scanString);
            }
            else
            {
                System.out.println("JataBase~# Instruction " + next + " not recognized. Skipping instruction." );
            }
        }
    }

    /**
     *					createDatabase();
     *
     * This function determine whether a database folder is to be
     * created based on if the folder already exists or not.
     *
     * @param      scanString  A Scanner object that contains the
     * 						   rest of the instruction to be parsed.
     */
    private static void createDatabase(Scanner scanString)
    {
        File database;

        if(scanString.hasNext())
        {
            database = new File(scanString.next());

            if(!database.exists())
            {
                //Make a database folder.
                if(database.mkdir())
                {
                    System.out.println("JataBase~# Database " + database.toString() + " created.");
                }
            }
            else
            {
                //Database already exists: do nothing.
                System.out.println("JataBase~# !Failed to create database " + database.toString() +
                        " because it already exists.");
            }
        }
        else
        {
            System.out.println("JataBase~# Instruction not recognized. Skipping instruction." );
        }
    }


    /**
     *                  createTable()
     *
     *  Function that creates a table in the currently used database.
     *
     * @param scanString Scanner object containing rest of instruction.
     */
    private static void createTable(Scanner scanString)
    {
        File tableFile;
        String table;

        if(scanString.hasNext() && currentDatabase != null)
        {
            table = scanString.next();
            tableFile = new File(currentDatabase + "/" + table);

            //Check if tableFile exists in the database.
            if(!tableFile.exists())
            {
                try
                {
                    boolean created = tableFile.createNewFile();
                    scanString.useDelimiter(";");

                    if(scanString.hasNext() && created)
                    {
                        String writeToTable = scanString.next();
                        writeToTable = writeToTable.replaceAll("[()]", "");
                        writeToTable = writeToTable.replaceAll(",", " |");

                        //Write table to table file.
                        BufferedWriter writer = new BufferedWriter(new FileWriter(tableFile));
                        writer.write(writeToTable);

                        writer.close();
                    }

                    System.out.println("JataBase~# Table " + table + " created.");
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                //Table already exists: do nothing.
                System.out.println("JataBase~# !Failed to create table " + table +
                        " because it already exists.");
            }
        }
        else
        {
            System.out.println("JataBase~# !Failed to create table because database not specified.");
        }
    }

    /**
     *                      alter()
     *
     * Function that alters a table in the currently used database.
     *
     * @param scanString - Scanner object containing instruction.
     *
     * @implNote This function calls addToTable() | removeFromTable()
     */
    private static void alter(Scanner scanString)
    {
        File table;

        if(scanString.hasNext())
        {
            //Get TABLE word and ignore
            scanString.next();

            if(scanString.hasNext())
            {
                table = new File(currentDatabase + "/" + scanString.next());

                //Check if table exists
                if(table.exists())
                {
                    //Get alter operation
                    if(scanString.hasNext())
                    {
                        String operation = scanString.next();

                        if(operation.equalsIgnoreCase("ADD"))
                        {
                            addToTable(scanString, table);
                        }
                    }
                }
            }
        }
    }

    /**
     *                      addToTable()
     *
     * Function that adds a new member to a table in a database.
     *
     * @param scanString - Scanner object containing instruction to add to table.
     * @param table - the path to the table file.
     */
    private static void addToTable(Scanner scanString, File table)
    {
        scanString.useDelimiter(";");

        if(scanString.hasNext())
        {
            try
            {
                String writeToTable;

                writeToTable = scanString.next();

                //Write table to table file.
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(table, true))) {
                    writer.append(" | ").append(writeToTable);
                }

                System.out.println("JataBase~# Table " + table + " modified.");
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     *					drop()
     *
     * This function is called when the "DROP" instruction is
     * issued. It determines if a database or table should be
     * dropped.
     *
     * @param      scanString  The scan string
     */
    private static void drop(Scanner scanString)
    {
        String next;

        if(scanString.hasNext())
        {
            next = scanString.next();

            if(next.equalsIgnoreCase("DATABASE"))
            {
                dropDatabase(scanString);
            }
            else if(next.equalsIgnoreCase("TABLE"))
            {
                dropTable(scanString);
            }
            else
            {
                System.out.println("JataBase~# Instruction " + next + " not recognized. Skipping instruction." );
            }
        }
    }

    /**
     *					dropDatabase()
     *
     * Function that drops a database of the database exists.
     *
     * @param      scanString  The scan string
     */
    private static void dropDatabase(Scanner scanString)
    {

        File database;

        if(scanString.hasNext())
        {
            database = new File(scanString.next());

            //Determine if database folder exists
            if(database.exists())
            {
                //If it exists we need to delete all files in the
                //folder to delete the folder.
                String[]entries = database.list();

                assert entries != null;
                for(String s: entries)
                {
                    File currentFile = new File(database.getPath(),s);
                    currentFile.delete();
                }

                //Delete database after removing all files.
                database.delete();
                System.out.println("JataBase~# Database " + database + " deleted.");
            }
            else
            {
                //Database does not exist: do nothing.
                System.out.println("JataBase~# !Failed to drop database " + database.toString() +
                        " because it does not exist.");
            }
        }
        else
        {
            System.out.println("JataBase~# Instruction not recognized. Skipping instruction." );
        }
    }

    /**
     *                  dropTable()
     *
     * Function that removes a table from a database.
     *
     * @param scanString - Scanner object that contains table to be dropped.
     */
    private static void dropTable(Scanner scanString)
    {
        File table;

        if(scanString.hasNext())
        {
            String tableValue = scanString.next();
            table = new File(currentDatabase + "/" + tableValue);
            if(table.exists())
            {
                table.delete();
                System.out.println("JataBase~# Table deleted.");
            }
            else
            {
                //Table does not exist: do nothing.
                System.out.println("JataBase~# !Failed to drop table " + tableValue +
                        " because it does not exist.");
            }
        }
    }


    /**
     *                          use()
     *
     * Function that is called when the "USE" keyword is read. This
     * function determines what database is to be used if it exists, else
     * it defaults to null.
     *
     * @param scanString - Scanner object that contains rest of instruction.
     */
    private static void use(Scanner scanString)
    {
        if(scanString.hasNext())
        {
            currentDatabase = new File(scanString.next());

            //Check if database exists
            if(currentDatabase.exists())
            {
                //Return from function to get next instruction.
                System.out.println("JataBase~# Using database " + currentDatabase +".");
            }
            else
            {
                System.out.println("JataBase~# Database " + currentDatabase + " does not exist.");
                currentDatabase = null;
            }
        }
    }


    /**
     *                  select()
     *
     * Function that finds the selected data from a table and prints it.
     *
     * @param scanString - Scanner object containing rest of instruction.
     */
    private static void select(Scanner scanString)
    {
        if(scanString.hasNext())
        {
            scanString.next();

            //Take care of intermediary FROM statement
            if(scanString.hasNext())
            {
                scanString.next();
            }

            //Get table
            if(scanString.hasNext())
            {
                String tableVaue = scanString.next();
                File table = new File(currentDatabase + "/" + tableVaue);
                try
                {
                    Scanner scanFile = new Scanner(table);
                    if(scanFile.hasNext())
                    {
                        System.out.println(scanFile.nextLine());
                    }
                }
                catch(FileNotFoundException e)
                {
                    System.out.println("JataBase~# !Failed to query table " + tableVaue + " because it does not exist.");
                }
            }
        }
    }
}