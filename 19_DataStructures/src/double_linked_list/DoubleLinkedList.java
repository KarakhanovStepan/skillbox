package double_linked_list;

public class DoubleLinkedList
{
    private ListItemD head;
    private ListItemD tail;

    public ListItemD getHeadElement()
    {
        if(head != null)
            return head;
        else
            return null;
    }

    public ListItemD getTailElement()
    {
        if(tail != null)
            return tail;
        else
            return null;
    }

    public ListItemD popHeadElement()
    {
        if(head == null)
            return null;

        ListItemD item = head;

        if(head.getNext() != null)
        {
            head.getNext().setPrev(null);
            head = head.getNext();
            item.setNext(null);
        }
        else
            head = null;

        return item;
    }

    public ListItemD popTailElement()
    {
        if(tail == null)
            return null;

        ListItemD item = tail;

        if(tail.getPrev() != null)
        {
            tail.getPrev().setNext(null);
            tail = tail.getPrev();
            item.setPrev(null);
        }
        else
            tail = null;

        return item;
    }

    public void removeHeadElement()
    {
        if(head == null)
            return;

        if(head.getNext() != null)
        {
            head.getNext().setPrev(null);
            head = head.getNext();
        }
        else
            head = null;
    }

    public void removeTailElement()
    {
        if(tail == null)
            return;

        if(tail.getPrev() != null)
        {
            tail.getPrev().setNext(null);
            tail = tail.getPrev();
        }
        else
            tail = null;
    }

    public void addToHead(ListItemD item)
    {
        if(head == null)
        {
            head = item;
            head.setPrev(null);

            if(tail != null)
                head.setNext(tail);
            else
                head.setNext(null);
        }
        else if(tail == null)           // Голова становится хвостом
        {
            tail = head;
            head = item;

            tail.setPrev(head);
            tail.setNext(null);
            head.setNext(tail);
            head.setPrev(null);
        }
        else {
            head.setPrev(item);
            item.setNext(head);
            item.setPrev(null);
            head = item;
        }

    }

    public void addToTail(ListItemD item)
    {
        if(tail == null)
        {
            tail = item;
            tail.setNext(null);

            if(head != null)
                tail.setPrev(head);
            else
                tail.setPrev(null);
        }
        else if(head == null)           // Хвост становится головой
        {
            head = tail;
            tail = item;

            head.setNext(tail);
            head.setPrev(null);
            tail.setPrev(head);
            tail.setNext(null);
        }
        else {
            tail.setNext(item);
            item.setPrev(tail);
            item.setNext(null);
            tail = item;
        }
    }
}