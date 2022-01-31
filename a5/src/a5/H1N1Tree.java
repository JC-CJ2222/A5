package a5;
/* NetId(s): yz548, jc3448

 * Name(s): Nancy Zhang, Jessie Chen
 * What I thought about this assignment:
 * I think this assignment is a great practice for us to learn about recursion.
 *
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/** Important notes 1. Remember that a variable declared as: H1N1Tree ct; <br>
 * can be viewed in two ways. (1) It is a node of a tree (2) It is the whole tree! <br>
 * More specifically, the root of the tree.
 *
 * At any point, we may talk about "node ct" or "tree ct" or "the tree with root ct". <br>
 * It all depends on what is important at this point.
 *
 * 2. For this particular class and assignment, we also use the following terminology. <br>
 * Let p be a Person. When we talk of
 *
 * . . . . "H1N1Tree p",
 *
 * we mean the H1N1Tree node that has p in field person. <br>
 * This saves a lot of verbiage; it shortens explanations and specs. <br>
 * It's OK to use this abbreviation because, in this project, no Person <br>
 * can appear more than once in a H1N1Tree.
 *
 * An instance of H1N1Tree represents the spread of H1N1 among a Network of people. <br>
 * In this model, each person can "catch" H1N1 from only a single person. <br>
 * The root of the H1N1Tree is the person who first got H1N1. <br>
 * From there, each person in the H1N1Tree is the child of the person who gave <br>
 * them H1N1. For example, for the tree:
 *
 * <pre>
 *       A
 *      / \
 *     B   C
 *        / \
 *       D   E
 * </pre>
 *
 * Person A originally got H1N1, B and C caught H1N1 from A, and<br>
 * D and E caught H1N1 from C.
 *
 * Important note: Each person in the H1N1 tree is unique. <br>
 * Check whether two Person's are the same by calling method equals. */
public class H1N1Tree {

    /** Replace "-1" by the time you spent on A4 in hours.<br>
     * Example: for 3 hours 15 minutes, use 3.25<br>
     * Example: for 4 hours 30 minutes, use 4.50<br>
     * Example: for 5 hours, use 5 or 5.0 */
    public static double timeSpent= 5;

    /** The String to be used as a separator in toString() */
    public static final String SEPARATOR= " - ";

    /** The String that marks the start of children in toString() */
    public static final String START_CHILDREN_DELIMITER= "[";

    /** The String that divides children in toString() */
    public static final String DELIMITER= ", ";

    /** The String that marks the end of children in toString() */
    public static final String END_CHILDREN_DELIMITER= "]";

    /** The String that is the space increment in toStringVerbose() */
    public static final String VERBOSE_SPACE_INCREMENT= "\t";

    /** The Person at the root of this H1N1Tree. <br>
     * i.e. the person in this node of this H1N1Tree. <br>
     * This field is never null.<br>
     * This is the H1N1 ancestor of everyone in this H1N1Tree, that is,<br>
     * the person who got ill first and indirectly caused everyone in it to get ill. <br>
     * All nodes of a H1N1Tree have different Persons in them. There are no duplicates */
    private Person person;

    /** The children of this H1N1Tree node. <br>
     * Each element of this set got H1N1 from the person at this node. <br>
     * It is the empty set if this node is a leaf. For each child,<br>
     * its field person is not null. */
    private Set<H1N1Tree> children;

    /** Constructor: a new H1N1Tree with root Person p and no children. <br>
     * Throw an IllegalArgumentException if p is null. */
    public H1N1Tree(Person p) throws IllegalArgumentException {
        if (p == null)
            throw new IllegalArgumentException("Can't construct H1N1Tree without a Person");
        person= p;
        children= new HashSet<>();
    }

    /** Constructor: a new H1N1Tree that is a copy of tree t. <br>
     * Tree t and its copy have no node in common (but nodes can share a Person). <br>
     * Throw an IllegalArgumentException if t is null. */
    public H1N1Tree(H1N1Tree t) throws IllegalArgumentException {
        if (t == null)
            throw new IllegalArgumentException("Can't construct copy of null H1N1Tree");
        person= t.person;
        children= new HashSet<>();

        for (H1N1Tree dt : t.children) {
            children.add(new H1N1Tree(dt));
        }
    }

    /** = the person at the root of this H1N1Tree. */
    public Person person() {
        return person;
    }

    /** = the number of direct children of this H1N1Tree. */
    public int childrenSize() {
        return children.size();
    }

    /** = the H1N1Tree that has p in its root. <br>
     * (null if p is not in this tree). */
    public H1N1Tree node(Person p) {
        if (person == p) return this; // Base case

        // Recursive case - Return the node with Person p if a child contains it.
        for (H1N1Tree dt : children) {
            H1N1Tree node= dt.node(p);
            if (node != null) return node;
        }

        return null; // p is not in the tree
    }

    /** Give p a new child, a node with Person c. <br>
     * Return the newly created node. <br>
     * Throw an IllegalArgumentException if:<br>
     * -- p or c is null, or<br>
     * -- p is not in this H1N1Tree, or <br>
     * -- c is already in this H1N1Tree. */
    public H1N1Tree insert(Person p, Person c) throws IllegalArgumentException {
        // TODO 1
        // This method should not be recursive.
        // Use method node(), above, and use no methods that are below.
        // DO NOT traverse the tree twice looking for the same node
        // --don't duplicate work.

        if (p == null || c == null)
            throw new IllegalArgumentException("Can't insert a null child to a null parent");
        if (node(p) == null)
            throw new IllegalArgumentException("Parent is not in this H1N1tree");
        if (node(c) != null)
            throw new IllegalArgumentException("Child already in this H1N1tree");

        H1N1Tree newTree= new H1N1Tree(c);
        node(p).children.add(newTree);

        return newTree;

    }

    /** = the number of nodes in this H1N1Tree. <br>
     * Note: If this is a leaf, the size is 1 (just the root) */
    public int size() {
        // TODO 2. This method must be recursive.
        // State whether this is a searching or a counting method:

        if (children.size() == 0) return 1;

        int sum= 1;

        for (H1N1Tree dt : children) {
            sum= sum + dt.size();
        }

        return sum;
        // This is a counting method
    }

    /** = "this H1N1 tree contains a node with person p." */
    public boolean contains(Person p) {
        // TODO 3
        // Note: This may be the first method you write that may test whether two
        // Persons r and p are equal. For all such tests, always use function equals:
        //
        // r.equals(p)
        //
        // If you write this recursively,
        // state whether this is a searching or a counting method:
        //
        // Recursive definition: This H1N1Tree contains p iff the person in this
        // H1N1Tree node is p or if one of this node's children contains p.
        if (person == p) return true;

        for (H1N1Tree dt : children) {
            if (dt.contains(p)) return true;
        }

        return false;
        // This is a searching method
    }

    /** = the depth at which p occurs in this H1N1Tree, or <br>
     * -1 if p is not in the H1N1Tree. <br>
     * Note: depth(root) is 0. <br>
     * If p is a child of this H1N1Tree node, then depth(p) is 1. etc. <br>
     * Note: if p is null, it is obviously NOT in the tree, but DO NOT<br>
     * put in a special test for it; it would be unnecessary case analysis. */
    public int depth(Person p) {
        // TODO 4
        // Note: Do NOT call function contains(p) to find out whether p is in
        // the tree. Do not have unnecessary calls that traverse the tree.
        // Study the two tutorials on processing recursive calls and use the
        // correct pattern here.
        // State whether this is a searching or a counting method:

        // Here is recursive insight:
        // ... Let child c of the root contain p.
        // ... Then the depth of p in the root = (1 + depth of p in c)
        if (person == p) return 0;

        int count= 0;

        for (H1N1Tree dt : children) {
            count= count + 1 + dt.depth(p);
            if (count != 0)
                return count;
        }
        return -1;
        // This is a searching method
    }

    /** Return the width of this tree at depth d <br>
     * (i.e. the number of nodes that occur at depth d).<br>
     * Throw an IllegalArgumentException if d < 0.<br>
     * Thus, for the following tree:<br>
     *
     * <pre>
      * Depth  level:
     *  0         A
     *           / \
     *  1       B   C
     *         /   / \
     *  2     D   E   F
     *             \
     *  3           G
     * </pre>
     *
     * A.widthAtDepth(0) = 1, A.widthAtDepth(1) = 2,<br>
     * A.widthAtDepth(2) = 3, A.widthAtDepth(3) = 1, A.widthAtDepth(4) = 0.<br>
     * C.widthAtDepth(0) = 1, C.widthAtDepth(1) = 2. */
    public int widthAtDepth(int d) throws IllegalArgumentException {
        // TODO 5
        // It may help to read Ed Discussions note #487, Stepwise refinement, when
        // developing widthAtDepth. Do not have calls that unnecessarily traverse
        // the tree, causing the tree to be traversed more than once.

        // State whether this is a searching or a counting method:

        // Use this recursive definition:
        // ..... If d = 0, the answer is 1.
        // ..... If d > 0, the answer is: sum of widths of the children at depth d-1.
        if (d < 0)
            throw new IllegalArgumentException("Depth can't be a negative number.");
        if (d == 0) return 1;

        int sum= 0;

        for (H1N1Tree dt : children) {
            sum= sum + dt.widthAtDepth(d - 1);
        }
        return sum;
        // This is a counting method

    }

    /** Return the route H1N1 took to get from "here" (the root of <br>
     * this H1N1Tree) to descendent c. If there is no such route, return null.<br>
     * For example, for this tree:<br>
     *
     * <pre>
     * Depth:
     *    0           A
     *               / \
     *    1         B   C
     *             /   / \
     *    2       D   E   F
     *             \
     *    3         G
     * </pre>
     *
     * A.H1N1RouteTo(E) returns the list [A,C,E].<br>
     * A.H1N1RouteTo(A) returns the list [A]. <br>
     * A.H1N1RouteTo(X) returns null.<br>
     * B.H1N1RouteTo(C) returns null. <br>
     * B.H1N1RouteTo(D) returns the list [B,D] */
    public List<Person> H1N1RouteTo(Person c) {
        // TODO 6
        // 0. Using getParent will give you 0 for this method. It is too inefficient,
        // requiring possibly many traversals of the tree.
        //
        // 1. The method must return a List<Person> object. But List is an interface, so
        // use something that implements it. LinkedList<Person> is preferred to ArrayList<Person>,
        // because prepend (or its equivalent) may have to be used.

        // 2. The ONLY case in which this method must create a list object (using
        // a new expression new ArrayList<>() or new LinkedList<>()) is the base
        // case, when c is the root. Do NOT create a new list object using a new-expression
        // in the recursive case.

        // 3. If c is null, there is no route to it, so null should be returned. But do NOT
        // put in a special test for this case; it is unnecessary case analysis.

        // 4. Base Case: The root of this H1N1Tree is c; i.e. the Route is just [c].

        // State whether this is a searching or a counting method:
        if (person == c) {
            List<Person> list= new LinkedList<>();
            list.add(c);
            return list;
        }

        for (H1N1Tree dt : children) {
            List<Person> route= dt.H1N1RouteTo(c);
            if (route != null) {
                route.add(0, person());
                return route;
            }
        }

        return null;
        // This is a searching method
    }

    /** If either child1 or child2 is null or is not in this H1N1Tree, return null.<br>
     * Otherwise, return the person at the root of the smallest subtree of this<br>
     * H1N1Tree that contains child1 and child2.<br>
     *
     * Examples. For the following tree (which does not contain H):
     *
     * <pre>
     * Depth:
     *    0      A
     *          / \
     *    1    B   C
     *        /   / \
     *    2  D   E   F
     *        \
     *    3    G
     * </pre>
     *
     * A.commonAncestor(B, A) is A<br>
     * A.commonAncestor(B, B) is B<br>
     * A.commonAncestor(B, C) is A<br>
     * A.commonAncestor(A, C) is A<br>
     * A.commonAncestor(E, F) is C<br>
     * A.commonAncestor(G, F) is A<br>
     * B.commonAncestor(B, E) is null<br>
     * B.commonAncestor(B, A) is null<br>
     * B.commonAncestor(D, F) is null<br>
     * B.commonAncestor(D, H) is null<br>
     * A.commonAncestor(null, C) is null */
    public Person commonAncestor(Person child1, Person child2) {
        // TODO 7
        /* Using getParent will give you 0 for this method. Its use over and over
         * is inefficient. This method itself will not be recursive.
         *
         * Instead, find the H1N1 routes l1 and l2 to the two children.
         * If both are not null, then two things are known:
         * (1) l1[0] = l2[0]
         * (2) the answer l1[i] is the largest i such that l1[0..i] = l2[0..i].
         * If this is not clear, draw an example.
         * The answer, then, can be found using a loop. No recursion is needed.
         *
         * You have a problem of writing this loop efficiently. You can't use a foreach loop
         * on both lists simultaneously. The simplest thing to do is to use List's
         * function toArray and work with the array representations of the lists. */
        if (child1 != null && child2 != null && H1N1RouteTo(child1) != null &&
            H1N1RouteTo(child2) != null) {
            List<Person> l1= H1N1RouteTo(child1);
            List<Person> l2= H1N1RouteTo(child2);
            Person[] l1Array= l1.toArray(new Person[l1.size()]);
            Person[] l2Array= l2.toArray(new Person[l2.size()]);

            Person ca= l1Array[0];
            for (int i= 1; i < l1Array.length && i < l2Array.length; i= i + 1) {

                if (l1Array[i] == l2Array[i]) { ca= l1Array[i]; }

            }
            return ca;
        }

        return null;
    }

    /** Return true iff this is equal to ob.<br>
     * 1. If this and ob are not of the same class, they are not equal, so return false.<br>
     * 2. Two H1N1Trees are equal if<br>
     * -- (1) they have the same Person in their root object (use ==) AND<br>
     * -- (2) their children sets are the same size AND<br>
     * -- (3) their children sets are equal.<br>
     * ------ Let the two children sets be s1 and s2. <br>
     * ------ Since their sizes are equal, this requires:<br>
     * -------- for every H1N1Tree t1 in s1 there is a H1N1Tree<br>
     * -------- t2 in s2 for which t1.equals(t2) is true.<br>
     *
     * -- Otherwise the two H1N1Trees are not equal.<br>
     * Do not use any of the toString functions to write equals(). <br>
     * Do not use Set's functions equals, contains, or containsAll. */
    @Override
    public boolean equals(Object ob) {
        // TODO 8
        // The specification outlines what must be done in detail. Two points to discuss:
        // 1. Stay away from nested ifs as much as possible! Instead, as soon as it is
        // determined that a property for equality is not met (or is met), return
        // false (or true)! So the structure could be:
        //
        // if (property 1 not met) return false;
        // if (property 2 not met) return false;
        // etc.
        //
        // 2. The difficult part is step (3). You have to test that sets
        // s1 and s2 are equal, knowing that their sizes are the same.
        // Example: Suppose we have these two trees:
        //
        // s1: {t1, t2, t3, t4, t5}
        // s2: {u1, u2, u3, u4, u5}
        //
        // To test whether they are equal, test whether each ti is in set s2;
        // stop immediately and return false if a ti is not in s2!
        //
        // To help you with this, we have stubbed in method isIn, below.
        // In implementing this part, you MUST call method isIn.
        // Debug equals and isIN together, using testing method test8equals.
        //
        // We force you to use isIN because it is the simplest and easiest way
        // to implement testing whether s1 = s2. In previous semesters on a
        // similar question, those who did not use such a method but had
        // all the code in function equals usually made a mess of it, and many
        // got it wrong.
        //
        // Note that isIn is going to call equals, like this: s2.equals(...).
        // That is a call to the method you are trying to write! We have
        // MUTUAL RECURSION: equals calls isIn, which calls equals, which call
        // isIn ... But when thinking about what a call does, USE THE
        // SPECIFICATION to understand what it does.
        if (ob == null || this.getClass() != ob.getClass()) { return false; }
        H1N1Tree p= (H1N1Tree) ob;
        if (size() != p.size() || person() != p.person()) { return false; }
        for (H1N1Tree dt : children) {
            if (!isIn(dt, p.children)) { return false; }
        }

        return true;

    }

    /** Return true iff t is in s. */
    public static boolean isIn(H1N1Tree t, Set<H1N1Tree> s) {
        // TODO 9. Equals must call this method. Test the two methods
        // equals and isIN together.
        //
        for (H1N1Tree dt : s) {
            if (t.equals(dt)) { return true; }
        }

        return false;
    }

    /* ========================================================================
     * ========================================================================
     * ========================================================================
     * Do not use the methods written below. They are used to calculate data
     * for the GUI and in the grading program. Feel free to read/study them. */

    /** = a COPY of the set of children of this H1N1Tree. */
    public Set<H1N1Tree> copyOfChildren() {
        return new HashSet<>(children);
    }

    /** Return the maximum depth of this H1N1Tree, <br>
     * i.e. the longest path from the root to a leaf.<br>
     * Example. If this H1N1Tree is a leaf, return 0. */
    public int maxDepth() {
        int maxDepth= 0;
        for (H1N1Tree dt : children) {
            maxDepth= Math.max(maxDepth, dt.maxDepth() + 1);
        }
        return maxDepth;
    }

    /** Return the immediate parent of c (null if c is not in this H1N1Tree).<br>
     * Thus, for the following tree:
     *
     * <pre>
     * Depth:
     *    0      A
     *          / \
     *    1    B   C
     *        /   / \
     *    2  D   E   F
     *        \
     *    3    G
     * </pre>
     *
     * A.getParent(E) returns C.<br>
     * C.getParent(E) returns C.<br>
     * A.getParent(B) returns A.<br>
     * E.getParent(F) returns null. */
    public Person getParent(Person c) {
        // Base case
        for (H1N1Tree dt : children) {
            if (dt.person == c) return person;
        }

        // Recursive case - ask children to look
        for (H1N1Tree dt : children) {
            Person parent= dt.getParent(c);
            if (parent != null) return parent;
        }

        return null; // Not found
    }

    /** Return the maximum width of all the widths in this tree, <br>
     * i.e. the maximum value that could be returned from widthAtDepth for this tree. */
    public int maxWidth() {
        return maxWidthImplementationTwo(this);
    }

    /** Simple implementation of maxWidth. <br>
     * Relies on widthAtDepth. <br>
     * Takes time proportional to the square of the size of the t. */
    static int maxWidthImplementationOne(H1N1Tree t) {
        int width= 0;
        int depth= t.maxDepth();
        for (int i= 0; i <= depth; i++ ) {
            width= Math.max(width, t.widthAtDepth(i));
        }
        return width;
    }

    /** Better implementation of maxWidth. Caches results in an array. <br>
     * Takes time proportional to the size of t. */
    static int maxWidthImplementationTwo(H1N1Tree t) {
        // For each integer d, 0 <= d <= maximum depth of t, store in
        // widths[d] the number of nodes at depth d in t.
        // The calculation is done by calling recursive procedure addToWidths.
        int[] widths= new int[t.maxDepth() + 1];   // initially, contains 0's
        t.addToWidths(0, widths);

        int max= 0;
        for (int width : widths) {
            max= Math.max(max, width);
        }
        return max;
    }

    /** For each node of this H1N1Tree, which is at some depth d in this H1N1Tree,<br>
     * add 1 to widths[depth + d]. */
    private void addToWidths(int depth, int[] widths) {
        widths[depth]++ ;        // the root of this H1N1Tree is at depth d = 0
        for (H1N1Tree dt : children) {
            dt.addToWidths(depth + 1, widths);
        }
    }

    /** Better implementation of maxWidth. Caches results in a HashMap. <br>
     * Takes time proportional to the size of t. */
    static int maxWidthImplementationThree(H1N1Tree t) {
        // For each possible depth d >= 0 in tree t, widthMap will contain the
        // entry (d, number of nodes at depth d in t). The calculation is
        // done using recursive procedure addToWidthMap.

        // For each integer d, 0 <= d <= maximum depth of t, add to
        // widthMap an entry <d, 0>.
        HashMap<Integer, Integer> widthMap= new HashMap<>();
        for (int d= 0; d <= t.maxDepth() + 1; d++ ) {
            widthMap.put(d, 0);
        }

        t.addToWidthMap(0, widthMap);

        int max= 0;
        for (Integer w : widthMap.values()) {
            max= Math.max(max, w);
        }
        return max;
    }

    /** For each node of this H1N1Tree, which is at some depth d in this H1N1Tree,<br>
     * add 1 to the value part of entry <depth + d, ...> of widthMap. */
    private void addToWidthMap(int depth, HashMap<Integer, Integer> widthMap) {
        widthMap.put(depth, widthMap.get(depth) + 1);  // the root is at depth d = 0
        for (H1N1Tree dt : children) {
            dt.addToWidthMap(depth + 1, widthMap);
        }
    }

    /** Return a (single line) String representation of this H1N1Tree.<br>
     * If this H1N1Tree has no children (it is a leaf), return the root's substring.<br>
     * Otherwise, return<br>
     * ... root's substring + SEPARATOR + START_CHILDREN_DELIMITER + each child's<br>
     * ... toString, separated by DELIMITER, followed by END_CHILD_DELIMITER.<br>
     *
     * Make sure there is not an extra DELIMITER following the last child.<br>
     *
     * Finally, make sure to use the static final fields declared at the top of<br>
     * H1N1Tree.java.<br>
     *
     * Thus, for the following tree:
     *
     * <pre>
     * Depth level:
     *   0         A
     *            / \
     *   1        B  C
     *           /  / \
     *   2      D  E   F
     *           \
     *   3        G
     *
     * A.toString() should print:
     * (A) - HEALTHY - [(C) - HEALTHY - [(F) - HEALTHY, (E) - HEALTHY - [(G) - HEALTHY]], (B) - HEALTHY - [(D) - HEALTHY]]
     *
     * C.toString() should print:
     * (C) - HEALTHY - [(F) - HEALTHY, (E) - HEALTHY - [(G) - HEALTHY]]
     * </pre>
     */
    @Override
    public String toString() {
        if (children.isEmpty()) return person.toString();
        String s= person.toString() + SEPARATOR + START_CHILDREN_DELIMITER;
        for (H1N1Tree dt : children) {
            s= s + dt.toString() + DELIMITER;
        }
        return s.substring(0, s.length() - 2) + END_CHILDREN_DELIMITER;
    }

    /** Return a verbose (multi-line) string representing this H1N1Tree. */
    public String toStringVerbose() {
        return toStringVerbose(0);
    }

    /** Return a verbose (multi-line) string representing this H1N1Tree.<br>
     * Each human in the tree is on its own line, with indentation representing<br>
     * what each human is a child of.<br>
     * indent is the the amount of indentation to put before this line.<br>
     * Should increase on recursive calls to children to create the above pattern.<br>
     * Thus, for the following tree:
     *
     * <pre>
     * Depth level:
     *   0         A
     *            / \
     *   1       B   C
     *          /   / \
     *   2     D   E   F
     *          \
     *   3       G
     *
     * A.toStringVerbose(0) should return:
     * (A) - HEALTHY
     * (C) - HEALTHY
     * (F) - HEALTHY
     * (E) - HEALTHY
     * (G) - HEALTHY
     * (B) - HEALTHY
     * (D) - HEALTHY
     * </pre>
     *
     * Make sure to use VERBOSE_SPACE_INCREMENT for indentation. */
    private String toStringVerbose(int indent) {
        String s= "";
        for (int i= 0; i < indent; i++ ) {
            s= s + VERBOSE_SPACE_INCREMENT;
        }
        s= s + person.toString();

        if (children.isEmpty()) return s;

        for (H1N1Tree dt : children) {
            s= s + "\n" + dt.toStringVerbose(indent + 1);
        }
        return s;
    }

}
