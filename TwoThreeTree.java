package cpsc331.assignment5;

import java.util.NoSuchElementException;
import cpsc331.collections.ElementFoundException;
import java.util.ArrayList;

//Connor Swartz (UCID: 30055899)
//Liam Sarjeant (UCID: 30150737)
//Mohammad Aaraiz (UCID: 30092994)

//Reference(s): 
//1.	Eberly, W. M. (n.d.). CPSC 331 — Assignment #4
//		Height-Balanced Binary Search Trees (Part Two) [PDF].
//
//2.	Eberly, W. M. (n.d.). CPSC 331 — Assignment #5
//		Height-Balanced Binary Search Trees (Part Three) [PDF].

/**
*
* Provides a 2-3 Tree Storing Values from an Ordered Type E.
*
*/

// 2-3 Tree Invariant: A rooted tree T is represented, so that the
// following 2-3 Tree Properties are satisfied:
//
// a) Each leaf in T stores an element of type E, and the elements
//    stored at the leaves are distinct.
// b) Each internal node in T has either (exactly) two or three
//    children - which are either leaves or internal nodes of T.
// c) If an internal node x of T has exactly two children - a first
//    child and a second child, then every element of E stored at a
//    leaf in the subtree whose root is the first child is less than
//    every element of E stored at a leaf in the subtree whose root
//    is the second child.
// d) If an internal node x of T has exactly three children - a first
//    child, second child and third child, then every element of E
//    stored at a leaf in the subtree whose root is the first child
//    is less than every element of E stored at a leaf in the subtree
//    whose root is the second child, and every element of E stored at
//    a leaf in the subtree whose root is the second child is less than
//    every element of E stored at a leaf in the subtree whose root
//    is the third child.
// e) If an internal node x has exactly two children then the largest
//    elements stored in each of the subtrees whose roots are its
//    children are also stored at x (and are called firstMax
//    and secondMax).
// f) If an internal node x has exactly three children then the largest
//    elements stored in each of the subtrees whose roots are its
//    children are also stored at x (and are called firstMax, secondMax
//    and thirdMax).
// g) Every leaf in T is at the same level, that is, has the same
//    distance from the root of T.
// h) Each node in T is the root of a 2-3 tree as well. That is, the
//    subtree of T with root x also satisfies properties (a)-(g).

public class TwoThreeTree<E extends Comparable<E>> {

  // Provides a node in this 2-3 Tree
  
  class TwoThreeNode {
  
    // Data Fields
    
    int numberChildren;         // Number of children of this node; an
                                // integer between 0 and 4
                                
    E element;                  // Element stored at this node; null
                                // if this is not a leaf
                                
    TwoThreeNode firstChild;    // First child
    E firstBound;               // Largest element stored in first subtree
                                // Both are null if this node is a leaf
                                
    TwoThreeNode secondChild;   // Second child
    E secondBound;              // Largest element stored in second subtree
                                // Both are null if this node has at most
                                // one child
                                
    TwoThreeNode thirdChild;    // Third child
    E thirdBound;               // Largest element stored in third subtree
                                // Both are null if this node has at most
                                // two children
                                
    TwoThreeNode fourthChild;   // Fourth child (Needed for Assignment #4)
    E fourthBound;              // Largest element stored in fourth subtree
                                // Both are null if this node has at most
                                // three children
                                
    TwoThreeNode parent;        // Parent; null if this node is the root
                                // of this tree
    
    
    // Constructor; constructs a TwoTreeNode with no children or parent,
    // storing null
    
    TwoThreeNode(){
    
      numberChildren = 0;
    
      element = null;
      
      firstChild = null;
      firstBound = null;
      
      secondChild = null;
      secondBound = null;
      
      thirdChild = null;
      thirdBound = null;
      
      fourthChild = null;
      fourthBound = null;
      
      parent = null;
    
    }
            
  }
  
  
  // Data Fields
  
  TwoThreeNode root;
  
  /**
  *
  * Constructs an empty 2-3 Tree.
  *
  */
  
  // Precondition: None
  // Postcondition: An empty 2-3 Tree (satisfying the above
  //                2-3 Tree Invariant) has been created.
  
  public TwoThreeTree() {
  
    root = null;
    
  }
  
  // *****************************************************************
  //
  //   Searching in a 2-3 Tree
  //
  // *****************************************************************
  
  
  /**
  *
  * Returns a TwoThreeNode with a given key<br>
  *
  * @param key the element to be searched for
  * @return the TwoThreeNode in this 2-3 tree storing the input key
  * @throws NoSuchElementException if the key is not in this tree
  * 
  */
   
  // Precondition::
  // a) This 2-3 Tree satisfies the above 2-3 Tree Properties.
  // b) A non-null key with type E is given as input.
  //
  // Postcondition:
  // a) If the key is stored in this 2-3 tree then the node storing it is
  //    returned as output. A NoSuchElementException is thrown, otherwise.
  // b) This 2-3 Tree has not been changed, so it still satisfies
  //    the 2-3 Tree Properties.
  
  public TwoThreeNode search(E key) throws NoSuchElementException {
  
    if (root == null) {
    
      throw new NoSuchElementException(key.toString() + " not found.");
    
    } else {
    
      return get(key, root);
    
    }
  
  }
  
  //
  // Searches for a given key in the subtree with a given node as root
  //
  // Precondition:
  // a) This 2-3 tree satisfies the above 2-3 Tree Properties.
  // b) key is a non-null input with type E.
  // c) x is a non-null TwoThreeNode in this 2-3 Tree, that is
  //    also given as input.
  //
  // Postcondition:
  // a) If the key is stored in the subtree with root x, then the node
  //    storing the key is returned as output. A NoSuchElementException
  //    is thrown otherwise.
  // b) This 2-3 Tree has not been changed, so it still satisfies
  //    the 2-3 Tree Properties.
  
  private TwoThreeNode get(E key, TwoThreeNode x)
                                       throws NoSuchElementException {
  
    // Bound Function: Depth of subtree with root x
  
    if (x.numberChildren == 0) {
    
      if (key.equals(x.element)) {
      
        return x;
      
      } else {
      
        throw new NoSuchElementException(key.toString() + " not found.");
      
      }
    
    } else if (x.numberChildren == 2) {
    
      if (key.compareTo(x.firstBound) <= 0) {
      
        return get(key, x.firstChild);
      
      } else {
      
        return get(key, x.secondChild);
      
      }
    
    } else {
    
      if (key.compareTo(x.firstBound) <= 0) {
      
        return get(key, x.firstChild);
      
      } else if (key.compareTo(x.secondBound) <= 0) {
      
        return get(key, x.secondChild);
      
      } else {
      
        return get(key, x.thirdChild);
      
      }
    
    }
  
  }
  
  // **********************************************************************
  //
  // Insertion in a 2-3 Tree
  //
  // **********************************************************************
  
  // The following "Modified Tree" properties are satisfied at the beginning
  // or end of private methods called by one or more public ones provided by
  // this 2-3 tree.
  //
  // a) This tree, T, satisfies 2-3 Tree properties (a), (c), (d), (e), (f)
  //    and (g) - but not, necessarily, 2-3 Tree properties (b) or (h).
  // b) Every internal node of T has (exactly) either two, three or four
  //    children - which are each either leaves or internal nodes of T.
  //    There is at most one internal node of T that has four children -
  //    all other internal nodes of T have either exactly two children
  //    or exactly three children.
  // c) If an internal node x of T has exactly four children - a first child,
  //    second child, third child and fourth children, which are the roots
  //    of the first subtree, second subtree, third subtree and fourth
  //    subtree of the subtree with root x, respectively - then each of the
  //    values stored at the leaves of the first subtree is less than each
  //    of the values stored at the leaves of the second subtree, each of
  //    the values stored at the leaves of the second subtree is less than
  //    each of the values stored at the leaves of the the third subtree,
  //    and each of the values stored at the leaves of the third subtree is
  //    less than each of the values stored at the leaves of the fourth
  //    subtree.
  // d) If an internal node x of T has exactly four children then x stores
  //    a 4-tuple (m1, m2, m3, m4) - where m1 is the largest value stored
  //    at any leaf of the first subtree, m2 is the largest value stored
  //    at any leaf of the second subtree, m3 is the largest value stored
  //    at any leaf of the third subtree, and m4 is the largest value
  //    stored at any leaf of the fourth subtree of the subtree with root x.
  // e) Each node x of T is the root of a rooted tree satisfying these
  //    properties as well. That is, the subtree with root x also satisfies
  //    the above properties (a)-(d), for every node of x.
  
  
  //
  // Inserts an input key into this 2-3 Tree
  //
  // Precondition:
  // a) This 2-3 Tree, T, satisfies the above 2-3 Tree Properties.
  // b) key is a non-null input of type E.
  //
  // Postcondition:
  // a) If the input key is not already included in the subset of E
  //    represented by T then it is added to this subset (with this subset
  //    being otherwise unchanged). An ElementFoundException is thrown and
  //    the set is not changed, if the key already belongs to this subset.
  // b) T satisfies the 2-3 Tree oroperties given above.
  
  public void insert (E key) throws ElementFoundException {
  
    if (root == null) {
    
      addFirstElement(key);
    
    } else if (root.numberChildren == 0) {
    
      addSecondElement(key);
    
    } else {
    
      insertIntoSubtree(key, root);
      if (root.numberChildren == 4) {
        fixRoot();
      }
    
    }
  
  }
  
  // Inserts an input key into this 2-3 Treee when it is Empty
  //
  // Precondition:
  // a) This 2-3 Tree, T, satisfies the above 2-3 tree properties - and
  //    is empty.
  // b) key is a non-null input with type E.
  //
  // Postcondition:
  // a) The input key has been added to the subset of E represented by T,
  //    which is otherwise unchanged.
  // b) T satisfies the 2-3 Tree properties given above.
  
  private void addFirstElement (E key) {
  
    TwoThreeNode z = new TwoThreeNode();
    z.numberChildren = 0;
    z.element = key;
    root = z;
  
  }
  
  // Inserts an input key into this 2-3 tree when the root of this tree
  // is a leaf
  //
  // Precondition:
  // a) This 2-3 Tree, T, satisfies the 2-3 Tree properties - and the
  //    root of this tree is a leaf, so that T represents a subset of E
  //    with size one.
  // b) key is a non-null input with type E.
  //
  // Postcondition:
  // a) Id the input key does not belong to the subset of E represented
  //    by T then they key is added to this subset - which is otherwise
  //    unchanged. If the key does already belong to this subset and an
  //    ElementFoundException is thrown and T is not changed.
  // b) T satisfies the 2-3 Tree properties given above.
  
  
  private void addSecondElement (E key) throws ElementFoundException {
  
    E e = root.element;
    if (e.equals(key)) {
    
      throw new ElementFoundException(key.toString() + " already stored.");
    
    } else {
    
      TwoThreeNode u = new TwoThreeNode();
      u.numberChildren = 2;
      root.parent = u;
      
      TwoThreeNode v = new TwoThreeNode();
      v.numberChildren = 0;
      v.element = key;
      v.parent = u;
      
      if (e.compareTo(key) < 0) {
      
        u.firstChild = root;
        u.firstBound = e;
        u.secondChild = v;
        u.secondBound = key;
      
      } else {
      
        u.firstChild = v;
        u.firstBound = key;
        u.secondChild = root;
        u.secondBound = e;
      
      };
      
      root = u;
    
    }
  
  }
  
  // Inserts a given key into the subtree of T with a given node x
  // as root if x is an internal node; throws an exception to aid the
  // inclusion of the input key if x is a leaf
  //
  // Precondition:
  // a) This 2-3 tree, T, satisfies the 2-3 Tree properties given above.
  // b) key is a non-null input with type E.
  // c) x is a non-null node in T.
  // d) Either key is not stored at any leaf in T or it is stored in a
  //    leaf of the subtree of T with root x.
  //
  // Postcondition:
  // a) If the input key already belongs to the subset of E stored at
  //    the leaves in the subtree of T with root x, then an
  //    ElementFoundException is thrown and T is not changed.
  // b) If x is a leaf that stores an element of E that is not equal
  //    to the input key then a NoSuchElementException is thrown and
  //    T is not changed.
  // c) If x is an internal node and the input key does not (initially)
  //    belong to the subset of E stored at the leaves in the subtree
  //    of T with root x, then
  //    - the input key is added to the subset of E stored at the leaves
  //      of T - which is otherwise unchanged;
  //    - either T satisfies the 2-3 Tree properties, given above, or
  //      T satisfies the "Modified Tree" properties, given above, and
  //      x is now an internal node with four children.
  
  private void insertIntoSubtree ( E key, TwoThreeNode x ) 
           throws ElementFoundException, NoSuchElementException {
           
    // Bound Function: Depth of subtree with root x
  
    if (x.numberChildren == 0 ) {
    
      E e = x.element;
      if (e.equals(key)) {
        throw new ElementFoundException(key.toString()
                                                 + " already stored.");
      } else {
        throw new NoSuchElementException(key.toString() + " not found.");
      }
    
    } else {
    
      try {
      
        if (x.numberChildren == 2) {
        
         if ((x.firstBound).compareTo(key) >= 0) {
           insertIntoSubtree(key, x.firstChild);
         } else {
           insertIntoSubtree(key, x.secondChild);
           if ((x.secondBound).compareTo(key) < 0) {
        	   x.secondBound = key;
           }
         }
        
        } else {
        
          if ((x.firstBound).compareTo(key) >= 0) {
            insertIntoSubtree(key, x.firstChild);
          } else if ((x.secondBound).compareTo(key) >= 0) {
            insertIntoSubtree(key, x.secondChild);
          } else {
            insertIntoSubtree(key, x.thirdChild);
            if ((x.thirdBound).compareTo(key) < 0) {
            	x.thirdBound = key;
            }
          }
        
        };
        
        raiseSurplus(x);
      
      } catch (NoSuchElementException ex) {
      
        addLeaf(key, x);
      
      }
    
    }
  
  }
  
  // Brings a node with four children closer to the root, if one exists
  // in this modified tree
  //
  // Precondition:
  // a) This tree, T, satisfies the "Modified Tree" properties given above.
  // b) x is an internal node of T whose children are also internal nodes
  //    in T.
  // c) Either T is a 2-3 tree (that is, it satisfies the above "2-3 Tree"
  //    properties), or one of the children of x has four children.
  //
  // Postcondition:
  // a) The subset of E stored at the leaves of T has not been changed.
  // b) Either T is a 2-3 tree (that is, it satisfies the above "2-3 Tree"
  //    properties), or T satisfies the "Modified Tree" properties and x
  //    has four children.
  
  private void raiseSurplus ( TwoThreeNode x ) {
  
    ArrayList<TwoThreeNode> children = new ArrayList<TwoThreeNode>();
    ArrayList<E> largest = new ArrayList<E>();
    
    includeChild(x.firstChild, x.firstBound, children, largest);
    includeChild(x.secondChild, x.secondBound, children, largest);
    if (x.numberChildren == 3) {
      includeChild(x.thirdChild, x.thirdBound, children, largest);
    };
    
    x.firstChild = children.get(0);
    x.firstBound = largest.get(0);
    x.secondChild = children.get(1);
    x.secondBound = largest.get(1);
    if (children.size() >= 3) {
      x.thirdChild = children.get(2);
      x.thirdBound = largest.get(2);
      if (children.size() == 4) {
        x.fourthChild = children.get(3);
        x.fourthBound = largest.get(3);
        x.numberChildren = 4;
      } else {
        x.numberChildren = 3;
      };
    } else {
      x.numberChildren = 2;
    }
  
  }
  
  // Adds a node and the value stored at the largest leaf of its subtree
  // to ArrayLists maintaining this information - or adds a pair of nodes
  // and corresponding values to these ArrayLists if c has four children
  //
  // Precondition:
  // a) T satisfies the "Modified Tree" properties given above
  // b) c is a non-null node in T, which is not the root of T, and cMax is
  //    the largest value (in E) stored at any leaf in the subtree of T
  //    with root c.
  // c) If x is the parent of c then the input ArrayList "children" stores
  //    all of the earlier children of x (that is, children whose subtrees
  //    store smaller values than the values stored in the subtree with
  //    root c) - or nodes that should replace these children - while the
  //    ArrayList "largest" stores the largest values in the subtrees with
  //    these nodes as root.
  //
  // Postcondition:
  // a) T has not been changed.
  // b) If c has at most three children then c is added to the ArrayMax
  //    "children" and cMax is added to largest. Otherwise (when c has four
  //    children) a pair if nodes that each have two of the children of c,
  //    and that should replace c in T, are added to "children" and the
  //    largest values stored at leaves of the subtrees with these nodes as
  //    root are added to "largest"
  
  private void includeChild(TwoThreeNode c, E cMax,
                                    ArrayList<TwoThreeNode> children,
                                                ArrayList<E> largest) {
                                                               
    if (c.numberChildren <= 3) {
    
      children.add(c);
      largest.add(cMax);
    
    } else {
    
      TwoThreeNode cLow = new TwoThreeNode();
      cLow.numberChildren = 2;
      cLow.firstChild = c.firstChild;
      (cLow.firstChild).parent = cLow;
      cLow.firstBound = c.firstBound;
      cLow.secondChild = c.secondChild;
      (cLow.secondChild).parent = cLow;
      cLow.secondBound = c.secondBound;
      cLow.parent = c.parent;
      children.add(cLow);
      largest.add(cLow.secondBound);
      
      TwoThreeNode cHigh = new TwoThreeNode();
      cHigh.numberChildren = 2;
      cHigh.firstChild = c.thirdChild;
      (cHigh.firstChild).parent = cHigh;
      cHigh.firstBound = c.thirdBound;
      cHigh.secondChild = c.fourthChild;
      (cHigh.secondChild).parent = cHigh;
      cHigh.secondBound = c.fourthBound;
      cHigh.parent = c.parent;
      children.add(cHigh);
      largest.add(cHigh.secondBound);
    
    };
     
  }
  
  // Adds a leaf storing a given value as a child of a given
  // internal node
  //
  // Precondition:
  // a) This tree, T,is a 2-3 tree (that is,it satisfies the 2-3 Tree
  //    properties given above).
  // b) x is an input internal node in T whose children are leaves.
  // c) key is a non-null input element of E that is not in the set of
  //    elements of E stored at leaves of T.
  // d) It is possible to produce a tree satisfying the "Modified Tree"
  //    properties, given above, by adding a leaf storing the input key
  //    as a child of x.
  //
  // Postcondition:
  // a) The input key has been added to the set of elements stored at the
  //    leaves of T, which is otherwise unchanged.
  // b) Either T is a 2-3 Tree or T satisfies the above "Modified Tree"
  //    properties and x has four children.
  
  private void addLeaf ( E key, TwoThreeNode x) {
  
    ArrayList<E> leaves = new ArrayList<E>();
    
    leaves.add(x.firstBound);
    leaves.add(x.secondBound);
    if (x.numberChildren == 3) {
      leaves.add(x.thirdBound);
    };
    leaves.add(key);
    
    // Ensure that values at leaves are sorted in increasing order
    
    int i = leaves.size() - 1;
    while ((i >= 1) && ((leaves.get(i-1)).compareTo(leaves.get(i)) > 0)) {
      E tmp = leaves.get(i);
      leaves.set(i, leaves.get(i-1));
      leaves.set(i-1, tmp);
      i = i-1;
    }
    
    TwoThreeNode u0 = new TwoThreeNode();
    u0.numberChildren = 0;
    u0.element = leaves.get(0);
    u0.parent = x;
    x.firstChild = u0;
    x.firstBound = u0.element;
    
    TwoThreeNode u1 = new TwoThreeNode();
    u1.numberChildren = 0;
    u1.element = leaves.get(1);
    u1.parent = x;
    x.secondChild = u1;
    x.secondBound = u1.element;
    
    if (leaves.size() >= 3) {
    
      TwoThreeNode u2 = new TwoThreeNode();
      u2.numberChildren = 0;
      u2.element = leaves.get(2);
      u2.parent = x;
      x.thirdChild = u2;
      x.thirdBound = u2.element;
      
      if (leaves.size() == 4) {
      
        TwoThreeNode u3 = new TwoThreeNode();
        u3.numberChildren = 0;
        u3.element = leaves.get(3);
        u3.parent = x;
        x.fourthChild = u3;
        x.fourthBound = u3.element;
        
        x.numberChildren = 4;
      
      } else {
      
        x.numberChildren = 3;
      
      };
    
    } else {
    
      x.numberChildren = 2;
    
    };
  
  }
  
  // Completes the restoration of a 2-3 tree after the
  // "insertIntoSubtree" method has applied and the root has four
  // children
  //
  // Precondition:
  // a) T is a rooted tree, satisfying the above "Modified Tree"
  //    properties, whose root is an internal node with four children.
  //
  // Postcondition:
  // a) The subset of E represented by T has not been changed.
  // b) T now satisfies the "2-3 Tree" properties given above.
  
  private void fixRoot() {
  
    TwoThreeNode u = new TwoThreeNode();
    u.numberChildren = 2;
    
    TwoThreeNode v = new TwoThreeNode();
    v.numberChildren = 2;
    v.parent = u;
    
    v.firstChild = root.firstChild;
    (v.firstChild).parent = v;
    v.firstBound = root.firstBound;
    v.secondChild = root.secondChild;
    (v.secondChild).parent = v;
    v.secondBound = root.secondBound;
    
    u.firstChild = v;
    u.firstBound = v.secondBound;
    
    TwoThreeNode w = new TwoThreeNode();
    w.numberChildren = 2;
    w.parent = u;
    
    w.firstChild = root.thirdChild;
    (w.firstChild).parent = w;
    w.firstBound = root.thirdBound;
    w.secondChild = root.fourthChild;
    (w.secondChild).parent = w;
    w.secondBound = root.fourthBound;
    
    u.secondChild = w;
    u.secondBound = w.secondBound;
    
    root = u;
  
  }
  
  // **************************************************************************
  //
  // Deletion in a 2-3 Tree
  //
  // **************************************************************************
  
  // Removes a single node containing input key from tree T
  //
  // Precondition:
  // a)	This 2-3 tree, T, satisfies the 2-3 Tree properties.
  // b)	key is a non-null input of type E.
  //
  // Postcondition:
  // a) If the input key belongs to the subset of E represented by T, then the
  //	key is removed from this set, which is otherwise unchanged. A 
  //	NoSuchElementException is thrown and T is not changed if the key does
  //	not belong to this subset of E.
  // b) T satisfies the 2-3 Tree properties.
  //
  
  public void delete (E key) throws NoSuchElementException {

	  search(key);
	  
	  if (root == null) {
		  throw new NoSuchElementException("this tree is empty");
	  } else {
		  if (root.numberChildren == 0) {
			  removeFirstElement(key);
		  } else if (root.numberChildren == 2 && root.firstChild.numberChildren == 0) {
			  removeSecondElement(key);
		  } else if (root.numberChildren == 3 && root.firstChild.numberChildren == 0) {
			  removeThirdElement(key);
		  } else {
			  removeFromSubtree(key, root);
			  
			  if (root.numberChildren == 1) {
				  fixRoot2();
			  }
		  }
	  }
    // FOR YOU TO COMPLETE
  }
  
  // Removes the input key from the 2-3 tree when it is of size 1 (root with 
  // no children)
  //
  // Precondition:
  // a)	This 2-3 tree T satisfies the 2-3 tree properties.
  // b)	The root node of tree T has no children.
  // c) The subset of E represented by tree T is of size one.
  // d)	key is a non-null input of type E.
  // 
  // Postcondition:
  // a)	The input key has been removed from the subset of E represented by 
  //	the tree T. 
  // b)	The tree T is empty, satisfying the 2-3 tree properties.

  private void removeFirstElement(E key) {
	  root = null;
  }
  
  // Removes the input key from the 2-3 tree when the root of the tree has 
  // two children which are leaf nodes. 
  //
  // Precondition:
  // a) This 2-3 tree T satisfies the 2-3 tree properties. 
  // b)	The root node has two children which are leaf nodes.
  // c)	The subset of E represented by tree T is of size two.
  // d)	key is a non-null input with type E.
  //
  // Postcondition:
  // a)	The input key has been removed from the subset of E represented by 
  //	the tree T . 
  // b)	The tree T satisfies the 2-3 tree properties.
  
  private void removeSecondElement(E key) {
	  if (key.compareTo(root.firstChild.element) == 0) {
		  root.element = root.secondChild.element;
	  } else {
		  root.element = root.firstChild.element;
	  }
	  
	  root.numberChildren = 0;
	  
	  root.firstChild = null;
	  root.firstBound = null;
	  
	  root.secondChild = null;
	  root.secondBound = null;
  }
  
  // Removes the input key from the 2-3 tree when the root of the tree has 
  // three children which are leaf nodes.
  // 
  // Precondition: 
  // a)	This 2-3 tree T satisfies the 2-3 tree properties.
  // b)	The root node has three children which are leaf nodes.
  // c)	The subset of E represented by tree T is of size three.
  // d)	key is a non-null input with type E.
  // 
  // Postcondition:
  // a)	The input key has been removed from the subset of E represented by 
  //	the tree T. 
  // b)	The tree T satisfies the 2-3 tree properties.

  private void removeThirdElement(E key) {
	  if (key.compareTo(root.firstChild.element) == 0) {
		  root.firstChild = root.secondChild;
		  root.firstBound = root.secondBound;
		  
		  root.secondChild = root.thirdChild;
		  root.secondBound = root.thirdBound;
	  } else if (key.compareTo(root.secondChild.element) == 0) {
		  root.secondChild = root.thirdChild;
		  root.secondBound = root.thirdBound;
	  }
	  
	  root.numberChildren = 2;
	  
	  root.thirdChild = null;
	  root.thirdBound = null;
  }
  
  // Removes a given key from the subtree of T with internal node x provided 
  // as input.
  //
  // Precondition: 
  // a)	This 2-3 tree satisfies the 2-3 tree properties.
  // b)	key is a non-null input of type E.
  // c)	x is a non-null node in the tree.
  // d)	key is stored in a leaf node of subtree with root x.
  //
  // Postcondition:
  // a)	Tree T either satisfies the 2-3 tree properties or the “Further Modified 
  //	Tree” properties.
  // b)	Leaf node x storing element key is removed from the tree T.

  private void removeFromSubtree(E key, TwoThreeNode x) {
	  if (x.numberChildren == 0) {
		  removeLeaf(x);
	  } else {
		  if (x.numberChildren == 2) {
			  if (key.compareTo(x.firstBound) <= 0) {
				  removeFromSubtree(key, x.firstChild);
			  } else {
				  removeFromSubtree(key, x.secondChild);
			  }
		  } else {
			  if (key.compareTo(x.firstBound) <= 0) {
				  removeFromSubtree(key, x.firstChild);
			  } else if (key.compareTo(x.secondBound) <= 0) {
				  removeFromSubtree(key, x.secondChild);
			  } else {
				  removeFromSubtree(key, x.thirdChild);
			  }
		  }
		  raiseDeficit(x);
	  }
  }
  
  // If x has only one child, either move x's child to one of x's siblings, or 
  // moves one of x's sibling's children to x. 
  // 
  // Precondition:
  // a)	This tree satisfies the “Further Modified Tree” properties or the 2-3 
  //	tree properties.
  // b)	x is an internal, non-root node of tree T.
  // c)	Either x has only a single child that is a leaf node or there is no 
  //	internal node of T with one or four children so that T is a 2-3 tree.
  // 
  // Postcondition:
  // a)	The subset of E stored at the leaves of T remains unchanged.
  // b)	The depth of the tree T is not changed.
  // c)	Tree T satisfies either 2-3 tree properties or “Further Modified Tree” 
  //	properties.
  
  private void raiseDeficit(TwoThreeNode x) {
	  if (x != root && x.numberChildren == 1) {		  
		  if (x.parent.numberChildren == 2) {
			  // check if x is the first child
			  if (x.parent.firstChild.numberChildren == 1) {
				  if (x.parent.secondChild.numberChildren == 2) {
					  x.parent.secondChild.numberChildren = 3;
					  
					  x.parent.secondChild.thirdChild = x.parent.secondChild.secondChild;
					  x.parent.secondChild.thirdBound = x.parent.secondChild.secondBound;
					  
					  x.parent.secondChild.secondChild = x.parent.secondChild.firstChild;
					  x.parent.secondChild.secondBound = x.parent.secondChild.firstBound;
					  
					  x.parent.secondChild.firstChild = x.firstChild;
					  x.parent.secondChild.firstBound = x.firstBound;
					  x.parent.secondChild.firstChild.parent = x.parent.secondChild;
					  
					  x.parent.numberChildren = 1;
					  
					  x.parent.firstChild = x.parent.secondChild;
					  x.parent.firstBound = x.parent.secondBound;
					  
					  x.parent.secondChild = null;
					  x.parent.secondBound = null;
				  } else { // if secondSib.numberChildren == 3
					  x.numberChildren = 2;
					  
					  x.secondChild = x.parent.secondChild.firstChild;
					  x.secondBound = x.parent.secondChild.firstBound;
					  
					  x.secondChild.parent = x;
					  
					  x.parent.secondChild.numberChildren = 2;
					  
					  x.parent.secondChild.firstChild = x.parent.secondChild.secondChild;
					  x.parent.secondChild.firstBound = x.parent.secondChild.secondBound;
					  
					  x.parent.secondChild.secondChild = x.parent.secondChild.thirdChild;
					  x.parent.secondChild.secondBound = x.parent.secondChild.thirdBound;
					  
					  x.parent.secondChild.thirdChild = null;
					  x.parent.secondChild.thirdBound = null;
					  
					  x.parent.firstBound = x.secondBound;
				  }
			  } else { // if secondSib.numberChildren == 1 // checks if x is second child
				  if (x.parent.firstChild.numberChildren == 2) {
					  x.parent.firstChild.numberChildren = 3;
					  
					  x.parent.firstChild.thirdChild = x.firstChild;
					  x.parent.firstChild.thirdBound = x.firstBound;
					  
					  x.parent.firstChild.thirdChild.parent = x.parent.firstChild;
					  
					  x.parent.numberChildren = 1;
					  x.parent.firstBound = x.parent.firstChild.thirdBound;
					  
					  x.parent.secondChild = null;
					  x.parent.secondBound = null;
				  } else { // if firstSib.numberChildren == 3
					  x.numberChildren = 2;
					  
					  x.secondChild = x.firstChild;
					  x.secondBound = x.firstBound;
					  
					  x.firstChild = x.parent.firstChild.thirdChild;
					  x.firstBound = x.parent.firstChild.thirdBound;
					  
					  x.firstChild.parent = x;
					  
					  x.parent.firstBound = x.parent.firstChild.secondBound;
					  
					  x.parent.firstChild.numberChildren = 2;
					  
					  x.parent.firstChild.thirdChild = null;
					  x.parent.firstChild.thirdBound = null;
				  }
			  }
		  } else { // if x.parent.numberChildren == 3
			  // checks if x is first child
			  if (x.parent.firstChild.numberChildren == 1) {
				  if (x.parent.secondChild.numberChildren == 2) {
					  if (x.parent.thirdChild.numberChildren == 2) {
						  x.parent.secondChild.numberChildren = 3;
						  
						  x.parent.secondChild.thirdChild = x.parent.secondChild.secondChild;
						  x.parent.secondChild.thirdBound = x.parent.secondChild.secondBound;
						  
						  x.parent.secondChild.secondChild = x.parent.secondChild.firstChild;
						  x.parent.secondChild.secondBound = x.parent.secondChild.firstBound;
						  
						  x.parent.secondChild.firstChild = x.firstChild;
						  x.parent.secondChild.firstBound = x.firstBound;
						  x.parent.secondChild.firstChild.parent = x.parent.secondChild;
						  
						  x.parent.numberChildren = 2;
						  
						  x.parent.firstChild = x.parent.secondChild;
						  x.parent.firstBound = x.parent.secondBound;
						  
						  x.parent.secondChild = x.parent.thirdChild;
						  x.parent.secondBound = x.parent.thirdBound;
						  
						  x.parent.thirdChild = null;
						  x.parent.thirdBound = null;
					  } else { // if thirdSib.numberChildren == 3
						  x.numberChildren = 2;
						  
						  x.secondChild = x.parent.secondChild.firstChild;
						  x.secondBound = x.parent.secondChild.firstBound;
						  
						  x.secondChild.parent = x;
						  
						  x.parent.firstBound = x.secondBound;
						  
						  x.parent.secondChild.firstChild = x.parent.secondChild.secondChild;
						  x.parent.secondChild.firstBound = x.parent.secondChild.secondBound;
						  
						  x.parent.secondChild.secondChild = x.parent.thirdChild.firstChild;
						  x.parent.secondChild.secondBound = x.parent.thirdChild.firstBound;
						  
						  x.parent.secondChild.secondChild.parent = x.parent.secondChild;
						  
						  x.parent.secondBound = x.parent.secondChild.secondBound;
						  
						  x.parent.thirdChild.numberChildren = 2;
						  
						  x.parent.thirdChild.firstChild = x.parent.thirdChild.secondChild;
						  x.parent.thirdChild.firstBound = x.parent.thirdChild.secondBound;
						  
						  x.parent.thirdChild.secondChild = x.parent.thirdChild.thirdChild;
						  x.parent.thirdChild.secondBound = x.parent.thirdChild.thirdBound;
						  
						  x.parent.thirdChild.thirdChild = null;
						  x.parent.thirdChild.thirdBound = null;
					  }
				  } else { // if secondSib.numberChildren == 3
					  x.numberChildren = 2;
					  
					  x.secondChild = x.parent.secondChild.firstChild;
					  x.secondBound = x.parent.secondChild.firstBound;
					  
					  x.secondChild.parent = x;
					  
					  x.parent.firstBound = x.secondBound;
					  
					  x.parent.secondChild.firstChild = x.parent.secondChild.secondChild;
					  x.parent.secondChild.firstBound = x.parent.secondChild.secondBound;
					  
					  x.parent.secondChild.secondChild = x.parent.secondChild.thirdChild;
					  x.parent.secondChild.secondBound = x.parent.secondChild.thirdBound;
					  
					  x.parent.secondChild.thirdChild = null;
					  x.parent.secondChild.thirdBound = null;
				  }
			  } else if (x.parent.secondChild.numberChildren == 1) { // checks if x is second child
				  if (x.parent.firstChild.numberChildren == 2) {
					  if (x.parent.thirdChild.numberChildren == 2) {
						  x.parent.thirdChild.numberChildren = 3;
						  
						  x.parent.thirdChild.thirdChild = x.parent.thirdChild.secondChild;
						  x.parent.thirdChild.thirdBound = x.parent.thirdChild.secondBound;
						  
						  x.parent.thirdChild.secondChild = x.parent.thirdChild.firstChild;
						  x.parent.thirdChild.secondBound = x.parent.thirdChild.firstBound;
						  
						  x.parent.thirdChild.firstChild = x.firstChild;
						  x.parent.thirdChild.firstBound = x.firstBound;
						  
						  x.parent.thirdChild.firstChild.parent = x.parent.thirdChild;
						  
						  x.parent.secondChild = x.parent.thirdChild;
						  x.parent.secondBound = x.parent.thirdBound;
						  
						  x.parent.thirdChild = null;
						  x.parent.thirdBound = null;
						  
						  x.parent.numberChildren = 2;
					  } else { // if thirdSib.numberChildren == 3
						  x.numberChildren = 2;
						  
						  x.secondChild = x.parent.thirdChild.firstChild;
						  x.secondBound = x.parent.thirdChild.firstBound;
						  
						  x.secondChild.parent = x;
						  
						  x.parent.secondBound = x.secondBound;
						  
						  x.parent.thirdChild.numberChildren = 2;
						  
						  x.parent.thirdChild.firstChild = x.parent.thirdChild.secondChild;
						  x.parent.thirdChild.firstBound = x.parent.thirdChild.secondBound;
						  
						  x.parent.thirdChild.secondChild = x.parent.thirdChild.thirdChild;
						  x.parent.thirdChild.secondBound = x.parent.thirdChild.thirdBound;
						  
						  x.parent.thirdChild.thirdChild = null;
						  x.parent.thirdChild.thirdBound = null;
					  }
				  } else { // if firstSib.numberChildren == 3
					  x.numberChildren = 2;
					  
					  x.secondChild = x.firstChild;
					  x.secondBound = x.firstBound;
					  
					  x.firstChild = x.parent.firstChild.thirdChild;
					  x.firstBound = x.parent.firstChild.thirdBound;
					  
					  x.firstChild.parent = x;
					  
					  x.parent.firstBound = x.parent.firstChild.secondBound;
					  
					  x.parent.firstChild.numberChildren = 2;
					  
					  x.parent.firstChild.thirdChild = null;
					  x.parent.firstChild.thirdBound = null;
				  }
			  } else { // if thirdSib.numberChildren == 1 / checks if x is third child
				  if (x.parent.secondChild.numberChildren == 2) {
					  if (x.parent.firstChild.numberChildren == 2) {
						  x.parent.secondChild.numberChildren = 3;
						  
						  x.parent.secondChild.thirdChild = x.firstChild;
						  x.parent.secondChild.thirdBound = x.firstBound;
						  
						  x.parent.secondChild.thirdChild.parent = x.parent.secondChild;
						  
						  x.parent.secondBound = x.firstBound;
						  
						  x.parent.numberChildren = 2;
						  
						  x.parent.thirdChild = null;
						  x.parent.thirdBound = null;
					  } else { // if firstSib.numberChildren == 3
						  x.numberChildren = 2;
						  
						  x.secondChild = x.firstChild;
						  x.secondBound = x.firstBound;
						  
						  x.firstChild = x.parent.secondChild.secondChild;
						  x.firstBound = x.parent.secondChild.secondBound;
						  
						  x.firstChild.parent = x;
						  
						  x.parent.secondChild.secondChild = x.parent.secondChild.firstChild;
						  x.parent.secondChild.secondBound = x.parent.secondChild.firstBound;
						  
						  x.parent.secondBound = x.parent.secondChild.secondBound;
						  
						  x.parent.secondChild.firstChild = x.parent.firstChild.thirdChild;
						  x.parent.secondChild.firstBound = x.parent.firstChild.thirdBound;
						  
						  x.parent.secondChild.firstChild.parent = x.parent.secondChild;
						  
						  x.parent.firstBound = x.parent.firstChild.secondBound;
						  
						  x.parent.firstChild.numberChildren = 2;
						  
						  x.parent.firstChild.thirdChild = null;
						  x.parent.firstChild.thirdBound = null;
						  
						  x.parent.thirdBound = x.secondBound;
					  }
				  } else { // if secondSib.numberChildren == 3
					  x.numberChildren = 2;
					  
					  x.secondChild = x.firstChild;
					  x.secondBound = x.firstBound;
					  
					  x.firstChild = x.parent.secondChild.thirdChild;
					  x.firstBound = x.parent.secondChild.thirdBound;
					  x.firstChild.parent = x;
					  
					  x.parent.secondBound = x.parent.secondChild.secondBound;
					  
					  x.parent.secondChild.numberChildren = 2;
					  
					  x.parent.secondChild.thirdChild = null;
					  x.parent.secondChild.thirdBound = null;
					  
					  x.parent.thirdBound = x.secondBound;
				  }
			  }
		  }
	}
  }
  
  // Removes a given leaf node x
  // 
  // Precondition:
  // a)	This 2-3 tree T satisfies the 2-3 tree properties.
  // b)	x is a leaf node in tree T.
  // c) x is a non-null leaf node in tree T.
  // 
  // Postcondition: 
  // a)	The input node x has been removed from the subset of E represented 
  //	tree T.
  // b)	Either the tree T satisfies the 2-3 tree properties or the Further 
  //	Modified Tree properties.
  
  private void removeLeaf(TwoThreeNode x) {
	  if (x.parent.numberChildren == 2) {
		  // checks if x is first child
		  if (x.element == x.parent.firstBound) {
			  x.parent.numberChildren = 1;
			  
			  x.parent.firstChild = x.parent.secondChild;
			  x.parent.firstBound = x.parent.secondBound;
			  
			  x.parent.secondChild = null;
			  x.parent.secondBound = null;
		  } else { // x.element == x.parent.secondBound / checks if x is second child
			  x.parent.numberChildren = 1;
			  
			  x.parent.secondChild = null;
			  x.parent.secondBound = null;
		  }
	  }
	  
	  else { // if x.parent.numberChildren == 3
		  // checks if x is first child
		  if(x.element == x.parent.firstBound) {
			  x.parent.numberChildren = 2;
			  
			  x.parent.firstChild = x.parent.secondChild;
			  x.parent.firstBound = x.parent.secondBound;
			  
			  x.parent.secondChild = x.parent.thirdChild;
			  x.parent.secondBound = x.parent.thirdBound;
			  
			  x.parent.thirdChild = null;
			  x.parent.thirdBound = null;
		  } else if (x.element == x.parent.secondBound) { // checks if x is second child
			  x.parent.numberChildren = 2;
			  
			  x.parent.secondChild = x.parent.thirdChild;
			  x.parent.secondBound = x.parent.thirdBound;
			  
			  x.parent.thirdChild = null;
			  x.parent.thirdBound = null;
		  } else { // if x.element == x.parent.thirdBound / checks if x is third child
			  x.parent.numberChildren = 2;
			  
			  x.parent.thirdChild = null;
			  x.parent.thirdBound = null;
		  }
	  }
  }
  
  // Restores root with only one child to satisfy the properties of a 2-3 tree.
  // 
  // Precondition:
  // a)	Tree T satisfies the Further Modified Tree properties.
  // a)	The root of tree T has only one child.
  // 
  // Postcondition:
  // a)	The subset of E represented by the tree, T, remains unchanged.
  // b)	Tree T now satisfies the 2-3 tree properties.
  // c)	The depth of the tree T is reduced by one.
  
  private void fixRoot2() { // SHOULD WE RENAME THIS MAYBE?
	  root = root.firstChild;
	  root.parent = null;
  }
}