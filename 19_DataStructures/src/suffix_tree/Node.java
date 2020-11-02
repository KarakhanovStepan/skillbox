package suffix_tree;

import java.util.ArrayList;
import java.util.List;

public class Node
{
    private String fragment;
    private ArrayList<Integer> nextNodes;
    private ArrayList<Integer> positions;

    public Node(String fragment)
    {
        this.fragment = fragment;
        nextNodes = new ArrayList<>();
        positions = new ArrayList<>();
    }

    public Node(String fragment, int position)
    {
        this.fragment = fragment;
        nextNodes = new ArrayList<>();
        positions = new ArrayList<>();
        positions.add(position);
    }

    public String getFragment()
    {
        return fragment;
    }

    public void setFragment(String fragment) {
        this.fragment = fragment;
    }

    public void addPosition(int position)
    {
        positions.add(position);
    }

    public ArrayList<Integer> getPositions()
    {
        return positions;
    }

    public List<Integer> getNextNodes()
    {
        return nextNodes;
    }
}