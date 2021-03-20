public class RedBlackTree<T extends Comparable<T>>
{
    private RedBlackNode<T> nil = new RedBlackNode<T>();
    private RedBlackNode<T> root = nil;	// Root initialized to nil.

    public RedBlackTree()
    {
        root.left = nil;
        root.right = nil;
        root.parent = nil;
    }

    private void leftRotate(RedBlackNode<T> NeedLeftRotate)
    {
        leftRotateFixup(NeedLeftRotate);
        RedBlackNode<T> y;
        
        y = NeedLeftRotate.right;
        NeedLeftRotate.right = y.left;
        
        if(!isNil(y.left))
            y.left.parent = NeedLeftRotate;
        
        y.parent = NeedLeftRotate.parent;
        
        //if parent is nil then y will be new root
        if(isNil(NeedLeftRotate.parent))
            root = y;
        
        //Node is the left child of it's parent
        else if(NeedLeftRotate.parent.left == NeedLeftRotate)
            NeedLeftRotate.parent.left = y;
        
        //Node is the right child of it's parent.
        else
            NeedLeftRotate.parent.right = y;
        
        y.left = NeedLeftRotate;
        NeedLeftRotate.parent = y;
    }

    private void leftRotateFixup(RedBlackNode LeftRotateFix)
    {
        //Case : Only LeftRotateFix, LeftRotateFix.right and LeftRotateFix.right.right always are not nil.
        if(isNil(LeftRotateFix.left) && isNil(LeftRotateFix.right.left))
        {
            LeftRotateFix.Number_of_left_Nodes = 0;
            LeftRotateFix.Number_of_Right_Nodes = 0;
            LeftRotateFix.right.Number_of_left_Nodes = 1;
        }
        // Case : LeftRotateFix.right.left also exists in addition to Case 1
        else if(isNil(LeftRotateFix.left) && !isNil(LeftRotateFix.right.left))
        {
            LeftRotateFix.Number_of_left_Nodes = 0;
            LeftRotateFix.Number_of_Right_Nodes = 1 + LeftRotateFix.right.left.Number_of_left_Nodes + LeftRotateFix.right.left.Number_of_Right_Nodes;
            LeftRotateFix.right.Number_of_left_Nodes = 2 + LeftRotateFix.right.left.Number_of_left_Nodes + LeftRotateFix.right.left.Number_of_Right_Nodes;
        }
        //Case : LeftRotateFix.left also exists in addition to Case 1
        else if(!isNil(LeftRotateFix.left) && isNil(LeftRotateFix.right.left))
        {
            LeftRotateFix.Number_of_Right_Nodes = 0;
            LeftRotateFix.right.Number_of_left_Nodes = 2 + LeftRotateFix.left.Number_of_left_Nodes + LeftRotateFix.left.Number_of_Right_Nodes;
        }
        //Case : LeftRotateFix.left and LeftRotateFix.right.left both exist in addition to Case 1
        else
        {
            LeftRotateFix.Number_of_Right_Nodes = 1 + LeftRotateFix.right.left.Number_of_left_Nodes + LeftRotateFix.right.left.Number_of_Right_Nodes;
            LeftRotateFix.right.Number_of_left_Nodes = 3 + LeftRotateFix.left.Number_of_left_Nodes + LeftRotateFix.left.Number_of_Right_Nodes + LeftRotateFix.right.left.Number_of_left_Nodes + LeftRotateFix.right.left.Number_of_Right_Nodes;
        }
    }

    private void rightRotate(RedBlackNode<T> NeedRightRotate)
    {
        //Invoke rightRotateFixup to fix Number_of_Right_Nodes and Number_of_left_Nodes values
        rightRotateFixup(NeedRightRotate);
        RedBlackNode<T> x = NeedRightRotate.left;
        NeedRightRotate.left = x.right;
        
        //x is left child of node and Check for existence of x.right
        if(!isNil(x.right))
            x.right.parent = NeedRightRotate;
        
        x.parent = NeedRightRotate.parent;
        
        // nodes parent is nil
        if(isNil(NeedRightRotate.parent))
            root = x;
        
            //This node is  a right child of it's parent.
        else if(NeedRightRotate.parent.right == NeedRightRotate)
            NeedRightRotate.parent.right = x;
        
            //This node is a left child of it's parent.
        else
            NeedRightRotate.parent.left = x;
        
        x.right = NeedRightRotate;
        NeedRightRotate.parent = x;
    }

    private void rightRotateFixup(RedBlackNode RightRotateFix)
    {
        //Case : Only RightRotateFix, RightRotateFix.left and RightRotateFix.left.left exists.
        if(isNil(RightRotateFix.right) && isNil(RightRotateFix.left.right))
        {
            RightRotateFix.Number_of_Right_Nodes = 0;
            RightRotateFix.Number_of_left_Nodes = 0;
            RightRotateFix.left.Number_of_Right_Nodes = 1;
        }
        //Case : RightRotateFix.left.right also exists in addition to Case 1
        else if(isNil(RightRotateFix.right) && !isNil(RightRotateFix.left.right))
        {
            RightRotateFix.Number_of_Right_Nodes = 0;
            RightRotateFix.Number_of_left_Nodes = 1 + RightRotateFix.left.right.Number_of_Right_Nodes + RightRotateFix.left.right.Number_of_left_Nodes;
            RightRotateFix.left.Number_of_Right_Nodes = 2 + RightRotateFix.left.right.Number_of_Right_Nodes + RightRotateFix.left.right.Number_of_left_Nodes;
        }
        //Case : RightRotateFix.right also exists in addition to Case 1
        else if(!isNil(RightRotateFix.right) && isNil(RightRotateFix.left.right))
        {
            RightRotateFix.Number_of_left_Nodes = 0;
            RightRotateFix.left.Number_of_Right_Nodes = 2 + RightRotateFix.right.Number_of_Right_Nodes +RightRotateFix.right.Number_of_left_Nodes;

        }
        //Case : RightRotateFix.right & RightRotateFix.left.right exist in addition to Case 1
        else
        {
            RightRotateFix.Number_of_left_Nodes = 1 + RightRotateFix.left.right.Number_of_Right_Nodes + RightRotateFix.left.right.Number_of_left_Nodes;
            RightRotateFix.left.Number_of_Right_Nodes = 3 + RightRotateFix.right.Number_of_Right_Nodes + RightRotateFix.right.Number_of_left_Nodes + RightRotateFix.left.right.Number_of_Right_Nodes + RightRotateFix.left.right.Number_of_left_Nodes;
        }
    }

    public void insert(T InsertKey)
    {
        insert(new RedBlackNode<T>(InsertKey));
    }

    private void insert(RedBlackNode<T> nodeTobeInserted)
    {
        RedBlackNode<T> y = nil;
        RedBlackNode<T> x = root;
        
        //Compare and find the optimal position to insert
        while(!isNil(x))
        {
            y = x;
            if(nodeTobeInserted.key.compareTo(x.key) < 0)
            {
                x.Number_of_left_Nodes++;
                x = x.left;
            }
            else
            {
                x.Number_of_Right_Nodes++;
                x = x.right;
            }
        }
        
        nodeTobeInserted.parent = y;
        
        if(isNil(y))
            root = nodeTobeInserted;
        
        else if (nodeTobeInserted.key.compareTo(y.key) < 0)
            y.left = nodeTobeInserted;
        
        else
            y.right = nodeTobeInserted;
        
        nodeTobeInserted.left = nil;
        nodeTobeInserted.right = nil;
        nodeTobeInserted.color = RedBlackNode.RED;
        insertFixup(nodeTobeInserted);
    }
    //Insert may have violated red-black tree property, need to fix it up
    private void insertFixup(RedBlackNode<T> insertedNode)
    {
        RedBlackNode<T> b = nil;
        while(insertedNode.parent.color == RedBlackNode.RED)
        {
            if(insertedNode.parent == insertedNode.parent.parent.left)
            {
                b = insertedNode.parent.parent.right;
                if(b.color == RedBlackNode.RED)
                {
                    insertedNode.parent.color = RedBlackNode.BLACK;
                    b.color = RedBlackNode.BLACK;
                    insertedNode.parent.parent.color = RedBlackNode.RED;
                    insertedNode = insertedNode.parent.parent;
                }
                else if(insertedNode == insertedNode.parent.right)
                {
                    insertedNode = insertedNode.parent;
                    leftRotate(insertedNode);
                }
                else
                {
                    insertedNode.parent.color = RedBlackNode.BLACK;
                    insertedNode.parent.parent.color = RedBlackNode.RED;
                    rightRotate(insertedNode.parent.parent);
                }
            }
            else
            {
                b = insertedNode.parent.parent.left;
                if(b.color == RedBlackNode.RED)
                {
                    insertedNode.parent.color = RedBlackNode.BLACK;
                    b.color = RedBlackNode.BLACK;
                    
                    insertedNode.parent.parent.color = RedBlackNode.RED;
                    insertedNode = insertedNode.parent.parent;
                }
                else if(insertedNode == insertedNode.parent.left)
                {
                    insertedNode = insertedNode.parent;
                    rightRotate(insertedNode);
                }
                else
                {
                    insertedNode.parent.color = RedBlackNode.BLACK;
                    insertedNode.parent.parent.color = RedBlackNode.RED;
                    leftRotate(insertedNode.parent.parent);
                }
            }
        }
        //Root node color should always be black.
        root.color = RedBlackNode.BLACK;
    }

    public RedBlackNode<T> treeMinimum(RedBlackNode<T> node)
    {
        while(!isNil(node.left))
            node = node.left;
        return node;
    }

    public RedBlackNode<T> treeSuccessor(RedBlackNode<T> a)
    {

        if(!isNil(a.left) )
            return treeMinimum(a.right);
        RedBlackNode<T> b = a.parent;
        
        while(!isNil(b) && a == b.right)
        {
            //Keep moving up in the tree
            a = b;
            b = b.parent;
        }
        return b;
    }

    public void remove(RedBlackNode<T> nodeToBeRemoved)
    {
        RedBlackNode<T> c = searchNode(nodeToBeRemoved.key);
        RedBlackNode<T> a = nil;
        RedBlackNode<T> b = nil;
        
        //If either node's children is nil, then we must remove c
        if(isNil(c.left) || isNil(c.right))
            b = c;
            //Else we remove successor of c
        else b = treeSuccessor(c);
        //Let a be the left or right child of b (b can only have one child)
        if(!isNil(b.left))
            a = b.left;
        else
            a = b.right;
        
        //Link a's parent to b's parent
        a.parent = b.parent;
        
        //If b's parent is nil, then a is the root
        if(isNil(b.parent))
            root = a;
        
            //Else if b is a left child, set a to be b's left sibling
        else if(!isNil(b.parent.left) && b.parent.left == b)
            b.parent.left = a;
        
            //Else if b is a right child, set a to be b's right sibling
        else if(!isNil(b.parent.right) && b.parent.right == b)
            b.parent.right = a;
        
        //If b != c, transfer b's data into c.
        if(b != c)
            c.key = b.key;
        
        //Update the Number_of_left_Nodes and Number_of_Right_Nodes numbers which might need
        fixNodeData(a,b);
        
        //If b's color is black, it is a violation
        if(b.color == RedBlackNode.BLACK)
            removeFixup(a);
    }

    private void fixNodeData(RedBlackNode<T> a, RedBlackNode<T> b)
    {

        RedBlackNode<T> current = nil;
        RedBlackNode<T> track = nil;
        if(isNil(a))
        {
            current = b.parent;
            track = b;
        }
        else
        {
            current = a.parent;
            track = a;
        }
        while(!isNil(current)){
            if(b.key != current.key)
            {
                if(b.key.compareTo(current.key) > 0)
                    current.Number_of_Right_Nodes--;
                
                if(b.key.compareTo(current.key) < 0)
                    current.Number_of_left_Nodes--;
            }
            else
            {
                if(isNil(current.left))
                    current.Number_of_left_Nodes--;
                
                else if(isNil(current.right))
                    current.Number_of_Right_Nodes--;
                
                else if(track == current.right)
                    current.Number_of_Right_Nodes--;
                
                else if(track == current.left)
                    current.Number_of_left_Nodes--;
            }
            track = current;
            current = current.parent;
        }
    }

    private void removeFixup(RedBlackNode<T> a)
    {
        RedBlackNode<T> w;
        while(a != root && a.color == RedBlackNode.BLACK)
        {
            //If a is it's parent's left child
            if(a == a.parent.left)
            {
                //Set w = a's sibling
                w = a.parent.right;
                
                //If w's color is red.
                if(w.color == RedBlackNode.RED)
                {
                    w.color = RedBlackNode.BLACK;
                    a.parent.color = RedBlackNode.RED;
                    
                    leftRotate(a.parent);
                    w = a.parent.right;
                }
                //If both of w's children are black
                if(w.left.color == RedBlackNode.BLACK && w.right.color == RedBlackNode.BLACK)
                {
                    w.color = RedBlackNode.RED;
                    a = a.parent;
                }
                else
                {
                    //If w's right child is black
                    if(w.right.color == RedBlackNode.BLACK)
                    {
                        w.left.color = RedBlackNode.BLACK;
                        w.color = RedBlackNode.RED;
                        
                        rightRotate(w);
                        w = a.parent.right;
                    }
                    //If w = black, w.right = red
                    w.color = a.parent.color;
                    a.parent.color = RedBlackNode.BLACK;
                    
                    w.right.color = RedBlackNode.BLACK;
                    leftRotate(a.parent);
                    a = root;
                }
            }
            //If a is it's parent's right child
            else
            {
                //Set w to a's sibling
                w = a.parent.left;
                //If w's color is red
                if(w.color == RedBlackNode.RED)
                {
                    w.color = RedBlackNode.BLACK;
                    a.parent.color = RedBlackNode.RED;
                    
                    rightRotate(a.parent);
                    w = a.parent.left;
                }
                //If both of w's children are black
                if(w.right.color == RedBlackNode.BLACK && w.left.color == RedBlackNode.BLACK)
                {
                    w.color = RedBlackNode.RED;
                    a = a.parent;
                }
                else
                {
                    //If w's left child is black
                    if(w.left.color == RedBlackNode.BLACK)
                    {
                        w.right.color = RedBlackNode.BLACK;
                        w.color = RedBlackNode.RED;
                        
                        leftRotate(w);
                        w = a.parent.left;
                    }
                    //If w = black, and w.left = red
                    w.color = a.parent.color;
                    a.parent.color = RedBlackNode.BLACK;
                    
                    w.left.color = RedBlackNode.BLACK;
                    rightRotate(a.parent);
                    a = root;
                }
            }
        }
        a.color = RedBlackNode.BLACK;
    }

    public RedBlackNode<T> searchNode(T keyToBeSearched)	//This method searches a particular node from the tree based on the key and returns that node.
    {
        RedBlackNode<T> current = root;
        while(!isNil(current))
        {
            if(current.key.equals(keyToBeSearched))
                return current;
            
            else if(current.key.compareTo(keyToBeSearched) < 0)
                current = current.right;
            
            else
                current = current.left;
        }
        return null;
    }

    private boolean isNil(RedBlackNode node) //Return true of false depending on whether the node given is same as nil.
    {
        return node == nil;
    }

    public int size() //Returns the size of the tree
    {
        return root.Number_of_left_Nodes + root.Number_of_Right_Nodes + 1;
    }
}