import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
class Circle extends JComponent implements Fractal,Comparable<Circle> {
	private Ellipse2D.Double c; 
	double r,x,y;
	Point p;
	int io = 1;
	private Rectangle bound;
	private Color co;
	private ArrayList<Fractal> tangentials;
	public void setIo(int i){
		io = i;
	}
	Circle(double x, double y, double r){
		this.x = x;
		this.y = y;
		this.r = r;
		p = new Point(x,y);
		co = new Color(0,0,0);
		bound = new Rectangle((int)x-safeCeil(r), (int)y-safeCeil(r), 2*safeCeil(r) +1, 2*safeCeil(r) +1);
		c = new Ellipse2D.Double(x - bound.x - r,y - bound.y - r,2*r,2*r);
		this.setBounds(bound);

	}
	public Circle(Point p, double r){
		this.p = p;
		x = p.x;
		y = p.y;
		this.r = r;
		co = new Color(0,0,0);
		bound = new Rectangle((int)x-safeCeil(r), (int)y-safeCeil(r), 2*safeCeil(r) +1, 2*safeCeil(r) +1);
		c = new Ellipse2D.Double(x - bound.x - r,y - bound.y - r,2*r,2*r);
		this.setBounds(bound);
	}
	Circle (Circle c, double r){
		x = c.x;
		y = c.y;
		this.r = c.r + c.io*r;
		co = c.co;
		p = c.p;
		bound = new Rectangle((int)x-safeCeil(this.r), (int)y-safeCeil(this.r), 2*safeCeil(this.r) +1, 2*safeCeil(this.r) +1);
		this.c = new Ellipse2D.Double(x - bound.x - this.r,y - bound.y - this.r,2*this.r,2*this.r);
		this.setBounds(bound);

	}
	private int safeCeil(double d){
		return (int) (((d > 0 ? Math.ceil(d) : Math.floor(d)) * 10 + 1)/10);

	}
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(co);
		g2.draw(c);
	}
	public void setX(double x){
		x = x;
		bound.x = (int) x - safeCeil(r);
		c = new Ellipse2D.Double(x - bound.x - r,y - bound.y - r,2*r,2*r);
		this.setBounds(bound);
	}
	public void setY(double y){
		y = y;
		bound.y = (int) y - safeCeil(r);
		c = new Ellipse2D.Double(x - bound.x - r,y - bound.y - r, 2*r, 2*r);
		this.setBounds(bound);
	}

	public void setR(double r){
		this.r = r;
		bound.width = 2* (int) r +1;
		bound.height = bound.width;
		c = new Ellipse2D.Double(x - bound.x - r ,y - bound.y - r, 2*r,2*r);
		this.setBounds(bound);
	}
	public void recurse() {}
	public static double descartes(Circle c1, Circle c2, Circle c3) {
		//Circle[] temp = {c1,c2,c3};
		//Arrays.sort(temp);
		double k1 = c1.io/c1.r;
		double k2 = c2.io/c2.r;
		double k3 = c3.io/c3.r;
		double k4 = k1 + k2 + k3 + 2*Math.sqrt(Math.abs(k1*k2 + k1*k3 + k2*k3));
		return 1/k4;
		
	}
	
	public int compareTo(Circle c){
		return (int) this.r - (int) c.r;

	}
	public static Point[] trilat(Circle c1, Circle c2, Circle c3) {
		ArrayList<Point> wp = new ArrayList<Point>();
		Point[] pts = intersect(c1,c2);
		for (Point p : pts){
			if (Math.abs(dist(c3.p,p) - c3.r) <= .001)
				wp.add(p);
		}
		pts = new Point[wp.size()];
		return wp.toArray(pts);

	}
	public static Point[] intersect(Circle c1, Circle c2){
		ArrayList<Point> pts = new ArrayList<Point>();
		if (dist(c1,c2) > c1.r + c2.r);
		else if(Math.abs(dist(c1,c2) - (c1.r + c2.r)) <= .001)
			pts.add(new Point(c1.x + c1.r*(c2.x - c1.x)/(c1.r + c2.r),
					  c1.y - c1.r*(c2.y - c1.y)/(c1.r + c2.r)));
		else{ 
			pts.add(new Point(.5*(c2.x+c1.x)+.5*(c2.x-c1.x)*(Math.pow(c1.r,2)-Math.pow(c2.r,2))/Math.pow(dist(c1,c2),2)+2*(c2.y-c1.y)*(.25*Math.sqrt((dist(c1,c2)+c1.r+c2.r)*(-dist(c1,c2)+c1.r+c2.r)*(dist(c1,c2)-c1.r+c2.r)*(dist(c1,c2)+c1.r-c2.r)))/Math.pow(dist(c1,c2),2),
					  .5*(c2.y+c1.y)+.5*(c2.y-c1.y)*(Math.pow(c1.r,2)-Math.pow(c2.r,2))/Math.pow(dist(c1,c2),2)-2*(c2.x-c1.x)*(.25*Math.sqrt((dist(c1,c2)+c1.r+c2.r)*(-dist(c1,c2)+c1.r+c2.r)*(dist(c1,c2)-c1.r+c2.r)*(dist(c1,c2)+c1.r-c2.r)))/Math.pow(dist(c1,c2),2)));
			pts.add(new Point(.5*(c2.x+c1.x)+.5*(c2.x-c1.x)*(Math.pow(c1.r,2)-Math.pow(c2.r,2))/Math.pow(dist(c1,c2),2)-2*(c2.y-c1.y)*(.25*Math.sqrt((dist(c1,c2)+c1.r+c2.r)*(-dist(c1,c2)+c1.r+c2.r)*(dist(c1,c2)-c1.r+c2.r)*(dist(c1,c2)+c1.r-c2.r)))/Math.pow(dist(c1,c2),2),
					  .5*(c2.y+c1.y)+.5*(c2.y-c1.y)*(Math.pow(c1.r,2)-Math.pow(c2.r,2))/Math.pow(dist(c1,c2),2)+2*(c2.x-c1.x)*(.25*Math.sqrt((dist(c1,c2)+c1.r+c2.r)*(-dist(c1,c2)+c1.r+c2.r)*(dist(c1,c2)-c1.r+c2.r)*(dist(c1,c2)+c1.r-c2.r)))/Math.pow(dist(c1,c2),2)));

		}
		Point[] pa = new Point[pts.size()];
		if (pts.lastIndexOf(pts.get(0)) != 0)
			pts.remove(0);
		return pts.toArray(pa);
	}
	public static double dist (Point p1, Point p2){
		return Math.sqrt(Math.pow(p1.x - p2.x,2) + Math.pow(p1.y - p2.y,2));
	}
	public static double dist (Circle c1, Circle c2){
		return dist(c1.p, c2.p);
	}

	public static void main(String[] args) throws Exception{

		JFrame test = new JFrame();
		test.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		test.setLayout(null);
		test.setSize(1920,1080);
		test.setVisible(true);
		JLayeredPane p = new JLayeredPane();
		p.setBounds(0,0,1920,1000);
		p.setLayout(null);
		test.add(p);
		test.setVisible(true);
		Circle c1 = new Circle(900,500,400);
		Circle c2 = new Circle(740,500,240);
		Circle c3 = new Circle(1140,500,160);
		c1.setIo(-1);
		double newR = descartes(c1,c2,c3);
		Point[] a = trilat(new Circle(c1,newR),new Circle(c2,newR),new Circle(c3,newR));
		Circle c6 = new Circle(a[1],descartes(c1,c2,c3));
		Circle c7 = new Circle(a[0],descartes(c1,c2,c3));
		JComponent pt = new JComponent(){
			public void paintComponent(Graphics g){
				g.setColor(new Color(0,0,0));
				g.fillOval(0,0,10,10);


			}
			{this.setBounds(300-5,300+133-5,10,10);}


		};
		p.add(c1);
		p.add(c2);
		p.add(c3);
		//p.add(c4);
		//p.add(c5);
		fp.add(c6);
		p.add(c7);


	}
}
