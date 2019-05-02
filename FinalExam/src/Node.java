class Node
{
	public Comparable data;
	public Node left;
	public Node right;

	/**
         Inserts a new node as a descendant of this node.
         @param newNode the node to insert
	 */
	public void addNode(Node newNode)
	{
		int comp = newNode.data.compareTo(data);
		if (comp < 0)
		{
			if (left == null) left = newNode;
			else left.addNode(newNode);
		}
		else if (comp > 0)
		{
			if (right == null) right = newNode;
			else right.addNode(newNode);
		}
	}
	public void Left(Node newNode)
	{
		left = newNode; 
	}
	public void addRightNode(Node newNode)
	{
		right = newNode;
	}

	/**
         Prints this node and all of its descendants
         in sorted order.
	 */
	public void printInNodes()
	{
		if (left != null)
			left.printInNodes();
		System.out.print(data + " ");
		System.out.println();
		if (right != null)
			right.printInNodes();
	}
	public void printPostNodes()
	{
		if (left != null)
			left.printPostNodes();		
		if (right != null)
			right.printPostNodes();
		System.out.print(data + " ");
	}
}