# CPSC-331-TwoThreeTree
Provides a 2-3 Tree Storing Values from an Ordered Type E

2-3 Tree Invariant: A rooted tree T is represented, so that the

// following 2-3 Tree Properties are satisfied:

// a) Each leaf in T stores an element of type E, and the elements stored at the leaves are distinct.

// b) Each internal node in T has either (exactly) two or three children - which are either leaves or internal nodes of T.

// c) If an internal node x of T has exactly two children - a first child and a second child, then every element of E stored at a leaf in the subtree whose root is the first child is less than every element of E stored at a leaf in the subtree whose root is the second child.

// d) If an internal node x of T has exactly three children - a first child, second child and third child, then every element of E stored at a leaf in the subtree whose root is the first child is less than every element of E stored at a leaf in the subtree whose root is the second child, and every element of E stored at a leaf in the subtree whose root is the second child is less than every element of E stored at a leaf in the subtree whose root is the third child.

// e) If an internal node x has exactly two children then the largest elements stored in each of the subtrees whose roots are its children are also stored at x (and are called firstMax and secondMax).

// f) If an internal node x has exactly three children then the largest elements stored in each of the subtrees whose roots are its children are also stored at x (and are called firstMax, secondMax and thirdMax).

// g) Every leaf in T is at the same level, that is, has the same distance from the root of T.

// h) Each node in T is the root of a 2-3 tree as well. That is, the subtree of T with root x also satisfies properties (a)-(g).
