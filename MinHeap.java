public class MinHeap
{

    public Building[] Heap;
    public int size;
    private int capacity;

    private static final int BuildingCounter = 1;

    public MinHeap(int capacity)
    {
        this.capacity = capacity;
        this.size = 0;
        Heap = new Building[this.capacity + 1];
        Heap[0] = new Building(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE); //Storing the initial array index with a dummy building with values that won't affect our program.
    }

    private int parent(int location)
    {
        return location / 2;
    }

    private int leftChild(int location)
    {
        return (2 * location);
    }

    private int rightChild(int location)
    {
        return (2 * location) + 1;
    }

    private boolean ifLeaf(int location)
    {
        if((size == 3 && location == 1) || (location == 1 && size == 2))
        	return false;
        if (location >= (size / 2) && location <= size)
            return true;
        return false;
    }

    private void swap(int first, int second)	//Function to swap 2 nodes.
    {
        Building tempNode;
        tempNode = Heap[first];
        Heap[first] = Heap[second];
        Heap[second] = tempNode;
    }

    public void heapify(int location)	//Heapify method will check the min-heap constraints of the tree and if there is any violations, this method will do the necessary corrections.
    {
        if (!ifLeaf(location) && size == 2)
        {
            if (Heap[location].executed_Time > Heap[leftChild(location)].executed_Time)
            {
                swap(location, leftChild(location));
                heapify(leftChild(location));
            }
            if (Heap[location].executed_Time == Heap[leftChild(location)].executed_Time && Heap[location].buildingNum > Heap[leftChild(location)].buildingNum)
            {
                swap(location, leftChild(location));
                heapify(leftChild(location));
            }
        }
        if (!ifLeaf(location) && size > 2)
        {

            if (Heap[location].executed_Time > Heap[leftChild(location)].executed_Time || Heap[location].executed_Time > Heap[rightChild(location)].executed_Time)
            {
                if (Heap[leftChild(location)].executed_Time < Heap[rightChild(location)].executed_Time)
                {
                    swap(location, leftChild(location));
                    heapify(leftChild(location));
                }
                else
                {
                    swap(location, rightChild(location));
                    heapify(rightChild(location));
                }
            }
            if (Heap[location].executed_Time == Heap[leftChild(location)].executed_Time || Heap[location].executed_Time == Heap[rightChild(location)].executed_Time)
            {
                if (Heap[location].executed_Time == Heap[leftChild(location)].executed_Time && Heap[location].executed_Time == Heap[rightChild(location)].executed_Time)
                {
                    if(Heap[leftChild((location))].buildingNum < Heap[rightChild(location)].buildingNum && Heap[location].buildingNum > Heap[leftChild(location)].buildingNum)
                    {
                        swap(location, leftChild(location));
                        heapify(leftChild(location));
                    }
                    else if(Heap[location].buildingNum > Heap[rightChild(location)].buildingNum)
                    {
                        swap(location, rightChild(location));
                        heapify(rightChild(location));
                    }
                }
                else if(Heap[location].executed_Time == Heap[leftChild(location)].executed_Time)
                {
                    if(Heap[location].buildingNum > Heap[leftChild(location)].buildingNum)
                    {
                        swap(location, leftChild(location));
                        heapify(leftChild(location));
                    }
                }
                else if (Heap[location].buildingNum > Heap[rightChild(location)].buildingNum)
                {
                    swap(location, rightChild(location));
                    heapify(rightChild(location));
                }
            }
        }
    }	

    public void insert(Building element) //Insert method will add a new building into the heap and perform heapity operation on it. 
    {
        if (size >= capacity)
            return;

        Heap[++size] = element;
        int current = size;

        while (Heap[current].executed_Time <= Heap[parent(current)].executed_Time)
        {
            if(Heap[current].executed_Time == Heap[parent(current)].executed_Time)
            {
                if(Heap[current].buildingNum < Heap[parent(current)].buildingNum)
                {
                    swap(current, parent(current));
                    current = parent(current);
                }
                else 
                	break;
            }
            else
            {
                swap(current, parent(current));
                current = parent(current);
            }
        }
    }
    
    public Building remove()	//This will delete the root node building of the min-heap, updates the size of the tree and perform heapify operation.
    {
        Building toBeRemoved = Heap[BuildingCounter];
        Heap[BuildingCounter] = Heap[size--];
        heapify(BuildingCounter);
        return toBeRemoved;
    }
}