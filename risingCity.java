import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class risingCity
{

    public static void main(String[] args)
    {
    	
    	HashMap<Integer, ConvertingInputCommand> commandMap = convertInputCommands(args[0]); // Storing the commands into the HashMap.
    	
        //this counter is to keep a track on how many insertions are remaining.
        int InsertCommandCounter = 0;
        
        RedBlackTree rbt = new RedBlackTree();
        MinHeap minHeap = new MinHeap(2000);
        
        HashMap<Integer, Building> waitList = new HashMap<>(); // Storing the buildings with their building numbers as key.
        //In the span of 5 days, if we are construction some building and in that time we encounter new buildings, then we store those buildings here and dump them all into min-heap at once.
        
        List<Building> List_of_Under_Constructed_Buildings = new ArrayList<>(); //This is to temporarily store the buildings that needs to be added to the trees.

        //Initiating the Global Counter to -1, so that when the process starts, it'll start from 0.
        int GLOBAL_COUNTER = -1;
        
        try 
        {
        	PrintStream out = new PrintStream(new FileOutputStream("output_file.txt"));
            System.setOut(out); //Operation to make the output appear in a file, instead of appearing on console.
        } 
        
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        
        int No_of_Days_Since_previous_Building_began = 0; //Counter to know how many days it has been, since the construction of the building started.
        Building buildingUnderConstruction = null; //To store the current building under construction.
        
        for(HashMap.Entry<Integer, ConvertingInputCommand> entry :commandMap.entrySet()) 
            if(entry.getValue().inputCommand.equals("Insert")) 
            	InsertCommandCounter +=1;
            //Updating the counter to a value, equal to the number of insert commands given to us. 

        boolean FlagVariable1 = false; //Flag variable.
        while(InsertCommandCounter != 0)
        {
            //Check if input contains this counter for input
            GLOBAL_COUNTER = GLOBAL_COUNTER + 1;

            if(buildingUnderConstruction != null)
            {
                buildingUnderConstruction.executed_Time = buildingUnderConstruction.executed_Time + 1 ;
                waitList.put(buildingUnderConstruction.buildingNum, buildingUnderConstruction); //If we have a building that we are currently working on, then we update its execution time on every iteration.
                No_of_Days_Since_previous_Building_began = No_of_Days_Since_previous_Building_began +1;
            }

            if(commandMap.containsKey(GLOBAL_COUNTER)) //checking if any inputCommand has the key equal to our global counter.
            {
                ConvertingInputCommand input = commandMap.get(GLOBAL_COUNTER); //Sending the inputCommand to the ConvertingInputCommand.
                switch (input.inputCommand)
                {
                    case "PrintBuilding":			//If the inputCommand is to print, then we execute the following steps.
                        int start = input.firstArgument; //Stores the starting building number.
                        int sec = input.secondArgument;		//Stores the ending building number.
                        int end = sec == 0 ? start : sec; 	//Checking if the starting and ending building numbers are same or not.
                        int buildingCount = 0;
                        for(int i = start; i <= end; i++) //We begin the process from the starting building number till the ending number.
                        {									//If the they both are same then we do the process only for the former.		
                            RedBlackNode resultNode = rbt.searchNode(i); //We search the building number from the red-black tree and store that building in this temporary variable.
                            if(resultNode != null || waitList.containsKey(i)) //If we didn't find the building in red-black tree but we have it in the temporary list of 
                            {
                                int buildingNumber = i;
                                buildingCount++;
                                Building building = waitList.get(buildingNumber);
                                if(buildingCount == 1)
                                {
                                    if(FlagVariable1)
                                    {
                                        System.out.print("\n(" + buildingNumber + "," +
                                        building.executed_Time + "," + building.total_Time + ")");
                                    }
                                    else 
                                    {
                                        System.out.print("(" + buildingNumber + "," +
                                        building.executed_Time + "," + building.total_Time + ")");
                                        FlagVariable1 = true;
                                    }
                                }
                                else
                                {
                                    System.out.print(",(" + buildingNumber + "," +
                                            building.executed_Time + "," + building.total_Time + ")");
                                }
                            }

                        } // FlagVariable1 and count are flag variables which are used for printing all the details of each building in one line without a comma at the end.
                        
                        if(buildingCount == 0)
                            System.out.print("\n(0,0,0)");
                        
                        break;

                    case "Insert": 	//If the inputCommand is insert, then following steps are executed.
                        int inputBuildingNumber = input.firstArgument; //We store the building number and total time in temporary variables.
                        int total_Time = input.secondArgument;
                        Building latestBuilding = new Building(inputBuildingNumber, 0, total_Time);
                        waitList.put(inputBuildingNumber, latestBuilding);//We create a new building with the above given details and store in the temporary arraylist.
                        if(minHeap.size == 0)
                            minHeap.insert(latestBuilding); 
        
                        else List_of_Under_Constructed_Buildings.add(latestBuilding); //Adding the new building to the temporary list until its construction is completed. 
                        //Insert building in RBT
                        rbt.insert(inputBuildingNumber); // Inserting only the building number into the red-black tree instead of the whole building.
                }
            }

            if(No_of_Days_Since_previous_Building_began == 0 && minHeap.size == 1 && buildingUnderConstruction == null)
                buildingUnderConstruction = minHeap.Heap[1]; //Taking the root of the min heap for construction, if there is no current building already under construction.
            
            if(buildingUnderConstruction != null && buildingUnderConstruction.total_Time == buildingUnderConstruction.executed_Time)
            {
                Building eliminated;
                eliminated = minHeap.remove();
                for(Building b: List_of_Under_Constructed_Buildings)
                    minHeap.insert(b);
                if(minHeap.size == 0)
                    buildingUnderConstruction = null; //If the min heap size is one and there is a building under construction whose execution time = total time, then we need to remove it. Then we dump all the buildings from temporary storage into the min-heap.
                else
                { 
                    List_of_Under_Constructed_Buildings.clear();//Is size of min-heap is not 1, then dump all the buildings from the temporary list into the min-heap and clear that list.
                    minHeap.heapify(1); //Heapify the tree and take the root node building and start constructing that building.
                    buildingUnderConstruction = minHeap.Heap[1];
                }

                rbt.remove(new RedBlackNode(eliminated.buildingNum)); // Remove the corresponding building number from the red-black tree as well.
                
                waitList.remove(eliminated.buildingNum, eliminated); //We also remove it from the hash-map and print the removed building's details. 
                InsertCommandCounter--;

                if(FlagVariable1)
                    System.out.print("\n("+eliminated.buildingNum+ ","+ GLOBAL_COUNTER + ")");
                else
                {
                    System.out.print("("+eliminated.buildingNum+ ","+ GLOBAL_COUNTER + ")");
                    FlagVariable1 = true;
                }

                No_of_Days_Since_previous_Building_began = 0;	//Setting this counter back to 0.
            }
            else if(No_of_Days_Since_previous_Building_began == 5)
            { //If 5 days have passed, then insert all the buildings that we kept in store while some other building was under construction, into the min-heap.
                minHeap = heapInsert(waitList);
                No_of_Days_Since_previous_Building_began = 0; //Setting this counter back to 0.
                List_of_Under_Constructed_Buildings.clear(); //Emptying this list and taking the root node building of the updated min-heap under construction.
                buildingUnderConstruction = minHeap.Heap[1];
            }

        }
        System.out.println();
    }

    private static MinHeap heapInsert(HashMap<Integer, Building> waitList)
    {
        MinHeap heap = new MinHeap(2000);
        for(HashMap.Entry<Integer, Building> eachEntry :waitList.entrySet())
            heap.insert(eachEntry.getValue());
        return heap;
    } //This function dumps all the buildings from the hash-map which temporarily store all the buildings into the min-heap. 

    public static HashMap<Integer, ConvertingInputCommand>  convertInputCommands(String inputFileName)
    {

        HashMap<Integer, ConvertingInputCommand> map = new HashMap<>();

        URL path = risingCity.class.getResource(inputFileName);
        File file = new File(path.getFile());
        String Str = "";
        String DayNumber = "";
        String commandInput = "";
        String firstArgument = "";
        String secondArgument = "0";
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while(true)
            {
                try
                {
                    if(!((Str = reader.readLine()) != null))
                    	break;
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                DayNumber = Str.split(":")[0].trim();
                String tempString = Str.split(":")[1].trim();
                commandInput = tempString.split("\\(")[0].trim();
                String[] tempArray = tempString.split("\\(")[1].trim().split("\\)")[0].trim().split(",");
                firstArgument = tempArray[0];
                if(tempArray.length == 2)
                    secondArgument = tempArray[1];
                else secondArgument = "0";
                map.put(Integer.parseInt(DayNumber), new ConvertingInputCommand(commandInput, Integer.parseInt(firstArgument), Integer.parseInt(secondArgument)));
            }
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return map;
    }
}