package dic_ver2;

public class Translation {
	String name;
	String meaning;
	int likedNum;
	boolean selected;
	
	public Translation(String name) {
		this.name = name;
		meaning = "";
		likedNum = 0;
		selected = false;
	}
}
