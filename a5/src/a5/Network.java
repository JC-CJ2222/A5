package a5;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import graph.Graph;
import io.TextIO;

/** An instance represents a network of people, used with an H1N1Tree.<br>
 * The names of all Person's will be distinct --no duplicates. <br>
 *
 * @author Mshnik, revised by Gries. */
public class Network extends Graph<Person, PersonConnection> {
    /** The maximum health a person can have. */
    private int maxHealth;

    /** Names of all people. */
    protected static String[] names;

    /** Read in the array of names from text file names. */
    static {
        try {
            names= TextIO.read(new File("data/names.txt"));
        } catch (IOException e) {
            System.err.println("Error reading names file, should be located at data/names.txt");
            throw new RuntimeException(e.getMessage());
        }
    }

    /** Constructor: an instance with no people and no connections. */
    public Network() {
        super();
    }

    /** Constructor: a graph of size people of health mh with edges generated <br>
     * randomly based on connectionProbability cp. <br>
     * Preconditions: 0 <= size, 1 <= mh, 0 <= cp <= 1. */
    public Network(int size, int mh, double cp) {
        super();
        assert 0 <= size && 0 <= cp && cp <= 1 && 1 <= mh;
        maxHealth= mh;
        for (int i= 0; i < size; i++ ) {
            // Add itself to this as part of construction
            new Person(names[i], mh, this);
        }

        for (Person p1 : vertexSet()) {
            for (Person p2 : vertexSet()) {
                if (p1 != p2 && Math.random() < cp) {
                    addEdge(p1, p2, new PersonConnection());
                }
            }
        }
    }

    /** Constructor: an instance generated for the people in dt. <br>
     * There is an edge from each parent to each of its children. */
    public Network(H1N1Tree dt) {
        super();
        addVertex(dt.person());
        recCreate(dt);
    }

    /** Add to this Network the people in children trees of dt, <br>
     * adding edges from each root to its children. <br>
     * Precondition: dt.getRoot is already in the graph. */
    private void recCreate(H1N1Tree dt) {
        Person dtRoot= dt.person();
        for (H1N1Tree child : dt.copyOfChildren()) {
            addVertex(child.person());
            addEdge(dtRoot, child.person(), new PersonConnection());
            recCreate(child);
        }
    }

    /** Return a list of people in state s in this Network. */
    public List<Person> getPeopleOfType(Person.State s) {
        ArrayList<Person> lst= new ArrayList<>();
        for (Person p : vertexSet()) {
            if (p.state() == s) {
                lst.add(p);
            }
        }
        return lst;
    }

}
