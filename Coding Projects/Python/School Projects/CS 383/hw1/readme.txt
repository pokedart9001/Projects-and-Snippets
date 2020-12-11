Name: Noah Levitt

Alright, first off: I've always thought a solid candidate for my best meal ever was these fried balls of mozzarella cheese I had for dinner once when my family and I stayed in Umbria, Italy a pretty long time ago (I think I was in middle school at the time).

Now... assuming I've set up weighted and unweighted costs correctly, I've found an example for which Uniform Cost, Greedy Best-First, and A* searches all find a longer path with weighted costs than without: 024365718.


Here's the results for A* with weights:

> python solver.py astar 024365718
0       start   024365718
1       up      324065718
2       left    324605718
3       up      324615708
4       right   324615078
5       down    324015678
6       down    024315678
7       left    204315678
8       left    240315678
9       up      245310678
10      right   245301678
11      down    205341678
12      right   025341678
13      up      325041678
14      left    325401678
15      left    325410678
16      down    320415678
17      right   302415678
18      up      312405678
19      right   312045678
20      down    012345678
path cost: 287
frontier: 285
expanded: 144

And here's the results for without weights:

> python solver.py astar 024365718 --noweight
0       start   024365718
1       up      324065718
2       left    324605718
3       up      324615708
4       left    324615780
5       down    324610785
6       down    320614785
7       right   302614785
8       up      312604785
9       left    312640785
10      up      312645780
11      right   312645708
12      right   312645078
13      down    312045678
14      down    012345678
path cost: 14
frontier: 222
expanded: 131


To corroborate this claim, here are the results for BFS with weights:

> python solver.py bfs 024365718
0       start   024365718
1       up      324065718
2       left    324605718
3       up      324615708
4       left    324615780
5       down    324610785
6       down    320614785
7       right   302614785
8       up      312604785
9       left    312640785
10      up      312645780
11      right   312645708
12      right   312645078
13      down    312045678
14      down    012345678
path cost: 355
frontier: 6113
expanded: 3247

And without:

> python solver.py bfs 024365718 --noweight
0       start   024365718
1       up      324065718
2       left    324605718
3       up      324615708
4       left    324615780
5       down    324610785
6       down    320614785
7       right   302614785
8       up      312604785
9       left    312640785
10      up      312645780
11      right   312645708
12      right   312645078
13      down    312045678
14      down    012345678
path cost: 14
frontier: 6113
expanded: 3247

Since the weighted cost of BFS exceeds that of A* (and of UCost and Greedy, though that's not shown here), I think we can reasonably conclude that taking weighted cost into account *can* affect the length of the solution path.


I am fairly confident that I've set up everything correctly — in particular, I've ensured each queue element's priority to be the following for each search:
- BFS: Always 0 (I have strong evidence to believe that the priority queue behaves like a regular, FIFO queue with every element at priority 0)
- UCost: Cost (i**2 if weighted else 1, where 'i' is the number on the tile moved)
- Greedy: Heuristic (Manhattan Sum * (i**2 if weighted else 1), where 'i' is each number on the board)
- A*: Cost + Heuristic

(as one more side note, LaTeX is the BEST — you'll see when you look at plots.pdf)