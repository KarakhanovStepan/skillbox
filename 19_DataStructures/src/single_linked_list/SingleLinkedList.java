package single_linked_list;

public class SingleLinkedList
{
    private ListItem top;

    public void push(ListItem item)
    {
        if(top != null) {
            item.setNext(top);
        }
        top = item;
    }

    public ListItem pop()
    {
        ListItem item = top;
        if(top != null)
        {
            top = top.getNext();
            item.setNext(null);
        }
        return item;
    }

    public void removeTop()
    {
        if(top != null) {
            top = top.getNext();
        }
    }

    public void removeLast()
    {
        ListItem previous;
        ListItem item;

        if(top == null)
        {
            System.out.println("Empty list.");
            return;
        }
        else if(top.getNext() == null)
        {
            top = null;
        }
        else
        {
            previous = top;
            item = top.getNext();

            while(!(item.getNext() == null))
            {
                previous = item;
                item = item.getNext();
            }

            previous.setNext(null);
        }
    }
}