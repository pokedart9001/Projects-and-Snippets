package filesystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.NoSuchElementException;

import structures.Queue;

/**
 * An iterator to perform a level order traversal of part of a filesystem. A
 * level-order traversal is equivalent to a breadth- first search.
 */
public class LevelOrderIterator extends FileIterator<File> {

	Queue<File> files;

	/**
	 * Instantiate a new LevelOrderIterator, rooted at the rootNode.
	 * 
	 * @param rootNode
	 * @throws FileNotFoundException if the rootNode does not exist
	 */
	public LevelOrderIterator(File rootNode) throws FileNotFoundException {
		if (!rootNode.exists())
			throw new FileNotFoundException();
		files = new Queue<File>();
		files.enqueue(rootNode);
	}

	@Override
	public boolean hasNext() {
		return !files.isEmpty();
	}

	@Override
	public File next() throws NoSuchElementException {
		if (!hasNext())
			throw new NoSuchElementException();
		File root = files.dequeue();
		if (root.isDirectory()) {
			File[] list = root.listFiles();
			Arrays.sort(list);
			for (File file : list)
				files.enqueue(file);
		}
		return root;
	}

	@Override
	public void remove() {
		// Leave this one alone.
		throw new UnsupportedOperationException();
	}

}
