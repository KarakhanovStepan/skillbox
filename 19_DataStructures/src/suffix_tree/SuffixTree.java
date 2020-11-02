package suffix_tree;

import java.util.ArrayList;
import java.util.List;

public class SuffixTree
{
    private String text;
    private ArrayList<Node> nodes;
    private Node root;

    public static void main(String[] args) {
        SuffixTree suffixTree = new SuffixTree("Hello World! How are you? A big apple under the tree! Airplane are append me!");

        suffixTree.build();

        List<Integer> list = suffixTree.search("are");
        System.out.println(list);
    }

    public SuffixTree(String text)
    {
        root = new Node(null);
        this.text = text;
        nodes = new ArrayList<>();
    }

    private void build()
    {
        int position = 0;
        StringBuilder word = new StringBuilder();
        char[] arrayText = text.toCharArray();

        while (position < arrayText.length)
        {
            while(Character.isLetter(arrayText[position]))
            {
                word.append(arrayText[position]);
                position++;
            }

            if(word.length() != 0)
            {
                addWord(word.toString(), root, position - word.length());
            }

            word = new StringBuilder();
            position++;
        }

    }

    private void addWord(String word, Node node, int position)
    {
        if(node.getNextNodes().size() != 0) {

            Node suffix = null;

            for(Integer i: node.getNextNodes()){
                if(nodes.get(i).getFragment().charAt(0) == word.charAt(0))
                    suffix = nodes.get(i);
            }

            if (suffix != null) {

                if (word.equals(suffix.getFragment())) {
                    suffix.getPositions().add(position);
                    return;
                }

                int sameCharsCount = 0;

                // Получаем количество одинаковых символов
                while (sameCharsCount < suffix.getFragment().length()
                        && suffix.getFragment().charAt(sameCharsCount) == word.charAt(sameCharsCount)) {
                    sameCharsCount++;
                }

                if (sameCharsCount == suffix.getFragment().length()) {
                    if (suffix.getNextNodes().size() == 0) {
                        nodes.add(new Node(word.substring(sameCharsCount), position));
                        suffix.getNextNodes().add(nodes.size() - 1);
                    } else {
                        addWord(word.substring(sameCharsCount), suffix, position);
                    }
                }
                else {
                    Node newSuffix = new Node(suffix.getFragment().substring(0, sameCharsCount));
                    nodes.add(newSuffix);

                    int suffixIndex = nodes.indexOf(suffix);

                    node.getNextNodes().remove(node.getNextNodes().indexOf(suffixIndex));
                    node.getNextNodes().add(nodes.size() - 1);
                    suffix.setFragment(suffix.getFragment().substring(sameCharsCount));
                    newSuffix.getNextNodes().add(suffixIndex);

                    Node newLeaf = new Node(word.substring(sameCharsCount), position);
                    nodes.add(newLeaf);
                    newSuffix.getNextNodes().add(nodes.size() - 1);
                }
            }
            else
            {
                nodes.add(new Node(word, position));
                node.getNextNodes().add(nodes.size() - 1);
            }
        }
        else {
            nodes.add(new Node(word, position));
            node.getNextNodes().add(nodes.size() - 1);
        }
    }

    private List<Integer> search(String query)
    {
        ArrayList<Integer> positions = null;

        int position = 0;
        Node node = root;
        Node nextNode = null;

        while (position < query.length())
        {
            for(Integer i: node.getNextNodes())
            {
                if(nodes.get(i).getFragment().charAt(0) == query.charAt(0))
                {
                    nextNode = nodes.get(i);
                    while(position < nextNode.getFragment().length() && nextNode.getFragment().charAt(position) == query.charAt(position))
                    {
                        position++;
                    }
                }
            }

            if(nextNode != null) {
                if ((position) == query.length()) {
                    positions = nextNode.getPositions();
                    return positions;
                } else {
                    node = nextNode;
                    query = query.substring(position);
                    position = 0;
                    nextNode = null;
                }
            }
            else
            {
                System.out.println("Query not found.");
                return null;
            }
        }

        return positions;
    }
}