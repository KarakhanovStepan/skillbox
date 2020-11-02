package double_linked_list;

public class ListItemD
{
    private String data;
    private ListItemD prev;
    private ListItemD next;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ListItemD getPrev() {
        return prev;
    }

    public void setPrev(ListItemD prev) {
        this.prev = prev;
    }

    public ListItemD getNext() {
        return next;
    }

    public void setNext(ListItemD next) {
        this.next = next;
    }
}