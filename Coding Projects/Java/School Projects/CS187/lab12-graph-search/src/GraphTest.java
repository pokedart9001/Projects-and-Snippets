import java.util.ArrayList;

public class GraphTest {

	private static Graph<String> graphS;
	private final static int nverts = 8;	// number of vertices in the graph
	
	private static void testCompleteGraph() {
		ArrayList<Vertex<Character>> verts = new ArrayList<Vertex<Character>>();
		// create vertices
		for(int i=0;i<nverts;i++) {
			verts.add(new Vertex<Character>((char)('A'+i)));
		}
		boolean[][] mat = new boolean[nverts][nverts];
		for(int i=0;i<nverts;i++) {
			for(int j=0;j<nverts;j++) {
				mat[i][j] = false;
			}
		}
		Graph<Character> graphC = new Graph<Character>(verts, mat);
		graphC.completeGraph();
		graphC.print();
	}
	
	private static void makeCycleGraph(boolean reverse, boolean double_edges) {
		ArrayList<Vertex<String>> verts = new ArrayList<Vertex<String>>();
		// create vertices
		for(int i=0;i<nverts;i++) {
			verts.add(new Vertex<String>("V"+(char)('a'+i)));
		}
		boolean[][] mat = new boolean[nverts][nverts];
		for(int i=0;i<nverts;i++) {
			for(int j=0;j<nverts;j++) {
				mat[i][j] = false;
			}
			if(!reverse) {
				mat[i][(i+2)%nverts] = true;
				if(double_edges) mat[i][(i+3)%nverts] = true;
			}
			else {
				mat[i][(i-2+nverts)%nverts] = true;
				if(double_edges) mat[i][(i-3+nverts)%nverts] = true;
			}
		}	
		graphS = new Graph<String>(verts, mat);
	}
	
	public static void testDFS() {
		makeCycleGraph(false, true);
		System.out.print("DFS in a cycle graph: "); 
		graphS.DFS(0);
		System.out.println("\n(correct answer is:   Va Vc Ve Vg Vb Vd Vf Vh)");
		
		System.out.print("DFS in a reverse cycle graph: ");
		makeCycleGraph(true, true);
		graphS.DFS(1);
		System.out.println("\n(correct answer is:           Vb Vg Vd Va Vf Vc Vh Ve)");
	}

	public static void testBFS() {
		System.out.print("BFS path finding in a cycle graph: ");
		makeCycleGraph(false, true);
		ArrayList<Integer> path = graphS.findPathBFS(0, 1);
		if(path != null) {
			for(Integer x:path) {
				System.out.print(x+" ");
			}			
		} else System.out.print("doesn't exist");
		System.out.println("\n(correct answer is: 0 3 6 1)");
		
		System.out.print("BFS path finding in a reverse cycle graph: ");
		makeCycleGraph(true, false);
		path = graphS.findPathBFS(0, 1);
		if(path != null) {
			for(Integer x:path) {
				System.out.print(x+" ");
			}			
		} else System.out.print("doesn't exist");
		System.out.println("\n(correct answer is: doesn't exist)");

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testCompleteGraph();
		testDFS();
		testBFS();
	}

}
