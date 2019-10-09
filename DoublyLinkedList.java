import java.util.Iterator;

import javax.management.RuntimeErrorException;

public class DoublyLinkedList<T> implements Iterable<T> {

    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    private class Node<t> {
        T data;
        Node<T> next, prev;

        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        public String toString() {
            return data.toString();
        }
    }

    public void clear() {
        Node<T> trav = head;
        while (trav != null) {
            Node<T> next = trav.next;
            trav.prev = trav.next = null;
            trav.data = null;
            trav = next;
        }
        head = tail = trav = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public int isEmpty() {
        return size() == 0;
    }

    public void add(T elem) {
        addLast(elem);
    }

    public void addLast(T elem) {
        if (isEmpty()) {
            head = tail = new Node<T>(elem, null, null);
        } else {
            tail.next = new Node<T>(elem, tail, null);
            tail = tail.next;
        }
        size++;
    }

    public void addFirst(T elem) {
        if (isEmpty()) {
            head = tail = new Node<T>(elem, null, null);
        } else {
            head.prev = new Node<T>(elem, null, head);
            head = head.prev;
        }
        size++;
    }

    public T peekFirst() {
        if (isEmpty())
            throw new RuntimeErrorException("empty list");
        return head.data;
    }

    public T peekLast() {
        if (isEmpty())
            throw new RuntimeErrorException("empty list");
        return tail.data;
    }

    public T removeFirst() {
        if (isEmpty())
            throw new RuntimeErrorException("empty list");

        /* extract data and move head pointer */
        T data = head.data;
        head = head.next;
        --size;

        /* if list is empty after removal set tail as null */
        if (isEmpty())
            tail = null;

        /* memory clean */
        else
            head.prev = null;

        /* return deleted node data */
        return data;
    }

    public T removeLast() {
        if (isEmpty())
            throw new RuntimeErrorException("empty list");

        /* extract data and move head pointer */
        T data = tail.data;
        tail = tail.prev;
        --size;

        /* if list is empty after removal set tail as null */
        if (isEmpty())
            head = null;

        /* memory clean */
        else
            tail.next = null;

        /* return deleted node data */
        return data;
    }

    public T remove(Node<T> node) {
        if (node.prev == null)
            return removeFirst();
        if (node.next == null)
            return removeLast();

        /* make pointers of adjacent to current node skip over the node */
        node.next.prev = node.prev;
        node.prev.next = node.next;

        /* extract data */
        T data = node.data;

        /* clear up memory */
        node.data = null;
        node = node.prev = node.next = null;
        --size;

        /* return data */
        return data;
    }

    public T remove(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException();

        int i;
        Node<T> trav;

        if (index < size / 2) {
            for (i = 0, trav = head; i != index; i++) {
                trav = trav.next;
            }
        }

        else {
            for (i = 0, trav = tail; i != index; i--) {
                trav = trav.prev;
            }
        }

        if (node.prev == null)
            return removeFirst();
        if (node.next == null)
            return removeLast();

        return remove(trav);
    }

    public boolean remove(Object obj) {
        Node<T> trav = head;

        /* support null values */
        if (obj == null) {
            for (trav = head; trav != null; trav = trav.next()) {
                if (trav.data == null) {
                    remove(trav);
                    return true;
                }
            }
        } else {
            for (trav = head; trav != null; trav = trav.next()) {
                if (obj.equals(trav.data)) {
                    remove(trav);
                    return true;
                }
            }
        }
        return false;
    }

    public int indexOf(Object obj){
        Node<T> trav = head;
        int index = 0;

        /* support null values */
        if(obj == null){
            for (trav = head; trav != null; trav = trav.next(); index++){
                if(trav.data == null){
                    return index;
                }
            }
        }
        else {
            for (trav = head; trav != null; trav = trav.next(); index++){
                if(obj.equals(trav.data)){
                    return index;
                }
            }
        }
        return -1;
    }

    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> trav = head;

            @Override
            public boolean hasNext() {
                return trav != null;
            }

            @Override
            public T next() {
                T data = trav.data;
                trav = trav.next;
                return data;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        Node<T> trav = head;
        while (trav != null) {
            sb.append(trav + (", "));
            trav = trav.next;
        }
        sb.append(" ]");
        return sb.toString();
    }
}
