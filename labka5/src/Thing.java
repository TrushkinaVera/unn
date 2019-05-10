public class Thing implements Comparable<Thing>{
    private String name;
    private int size;

    public Thing(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int compareTo(Thing o) {
        return size - o.getSize();
    }

    @Override
    public String toString() {
        return "["+name+", "+ Integer.toString(size) + "]";
    }
    @Override
    public int hashCode() {
        int p = 239;
        return (name.hashCode() + size * p);
    }

    public boolean equals(Object Universe) {
        Thing thing = (Thing) Universe;
        return this.name.equals(thing.name)&& this.size == thing.size;
    }
}

