package binary_tree;

public class BinaryTree
{
    private Node root;

    public void addNode(String data)
    {
        if(root == null)
        {
            root = new Node();
            root.setData(data);
            root.setParent(null);
            root.setLeft(null);
            root.setRight(null);
        }
        else {
            Node node = root;

            while(node != null)
            {
                if(node.getData().compareTo(data) == 0)
                {
                    node = null;
                }
                else if(node.getData().compareTo(data) > 0)
                {
                    if (node.getRight() != null)
                    {
                        node = node.getRight();
                    }
                    else
                    {
                        Node newNode = new Node();

                        newNode.setData(data);
                        newNode.setParent(node);
                        newNode.setRight(null);
                        newNode.setLeft(null);

                        node.setRight(newNode);

                        node = null;
                    }
                }
                else
                {
                    if(node.getLeft() != null)
                    {
                        node = node.getLeft();
                    }
                    else
                    {
                        Node newNode = new Node();

                        newNode.setData(data);
                        newNode.setParent(node);
                        newNode.setRight(null);
                        newNode.setLeft(null);

                        node.setLeft(newNode);

                        node = null;
                    }
                }
            }
        }
    }

//    public List<Node> searchNodes(String data)
//    {
//        List<Node> nodeList = new ArrayList<>();
//
//        if(root == null)
//        {
//            return null;
//        }
//        else
//        {
//            Node node = root;
//
//            while(node != null)
//            {
//                if(node.getData().equals(data))
//                {
//                    nodeList.add(node);
//
//                    if(node.getRight() != null)
//                    {
//                        node = node.getRight();
//                    }
//                    else
//                    {
//                        node = null;
//                    }
//                }
//                else if(node.getData().compareTo(data) > 0 && node.getRight() != null)
//                {
//                    node = node.getRight();
//                }
//                else
//                {
//                    if(node.getLeft() != null)
//                    {
//                        node = node.getLeft();
//                    }
//                    else
//                    {
//                        node = null;
//                    }
//                }
//            }
//        }
//
//        return nodeList;
//    }

    public Node searchNode(String data)
    {
        if(root == null)
            return null;
        else
        {
            Node node = root;

            while (node != null)
            {
                if(node.getData().compareTo(data) == 0)
                    return node;
                else if(node.getData().compareTo(data) > 0)
                {
                    if(node.getRight() != null)
                        node = node.getRight();
                    else
                        node = null;
                }
                else
                {
                    if(node.getLeft() != null)
                        node = node.getLeft();
                    else
                        node = null;
                }
            }

            return null;
        }
    }
}