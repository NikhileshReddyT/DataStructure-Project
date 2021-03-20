class RedBlackNode<T extends Comparable<T>> 
{

    public static final int BLACK = 0;
    public static final int RED = 1; //Color could be either red or black but nothing else.
    public T key;	//Key for each node
    RedBlackNode<T> parent;	// Parent node
    RedBlackNode<T> left;	//Left Child
    RedBlackNode<T> right;	//Right Child
    
    public int Number_of_left_Nodes = 0;	//Number of nodes to the left of each node
    public int Number_of_Right_Nodes = 0;	//Number of nodes to the Right of each node
    public int color;						//Color of the node. Either 0 or 1 as mentioned above.

    RedBlackNode()
    {
        color = BLACK;
        Number_of_left_Nodes = 0;
        Number_of_Right_Nodes = 0;
        parent = null;
        left = null;
        right = null;
    }

    RedBlackNode(T key)
    {
        this();
        this.key = key;
    }	//This constructor initializes the node and inserts the key into the node.
}