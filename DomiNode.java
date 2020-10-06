package search;

public class DomiNode {
	private String path;
	private String top;
	private String bottom;
	private int size = 0;

	public void setInfo(String t, String b) {
		top = t;
		bottom = b;
	}

	public void setPath(String p) {
		path = p;
		size++;
	}

	public String getPath() {
		return path;
	}

	public String getTop() {
		return top;
	}

	public String getBottom() {
		return bottom;
	}

	public boolean isSolution() {
		return (top.equals(bottom));
	}

	public void setSize(int i) {
		size = i;
	}

	public int getSize() {
		return size;
	}

}
