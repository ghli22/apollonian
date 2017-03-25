class Point {//implements Comparable<Point>{
	public double x,y;
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}
	public String toString(){
		return String.format("(%.2f,%.2f)",x,y);
	}/*
	public boolean compareTo(Point o){
		return x == p.x && y == p.y;
	}*/
}

