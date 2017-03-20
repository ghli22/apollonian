import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
class Circle extends JComponent implements Fractal,Comparable {
	private Ellipse2D.Double c;
	private double cX, cY, r;
	private Rectangle bound;
	private Color co;
	private int io;
	private ArrayList<Fractal> tangentials;
	Circle(double x, double y, double r){
		cX = x;
		cY = y;
		this.r = r;
		co = new Color(0,0,0);
		bound = new Rectangle((int)cX-safeCeil(r), (int)cY-safeCeil(r), 2*safeCeil(r) +1, 2*safeCeil(r) +1);
		c = new Ellipse2D.Double(cX - bound.x - r,cY - bound.y - r,2*r,2*r);
		this.setBounds(bound);
		io = 1;

	}
	Circle (Circle c, double r){
		cX = c.cX;
		cY = c.cY;
		this.r = c.r + r;
		co = c.co;
		bound = new Rectangle((int)cX-safeCeil(this.r), (int)cY-safeCeil(this.r), 2*safeCeil(this.r) +1, 2*safeCeil(this.r) +1);
		this.c = new Ellipse2D.Double(cX - bound.x - this.r,cY - bound.y - this.r,2*this.r,2*this.r);
		this.setBounds(bound);
		io = 1;

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
		cX = x;
		bound.x = (int) x - safeCeil(r);
		c = new Ellipse2D.Double(cX - bound.x - r,cY - bound.y - r,2*r,2*r);
		this.setBounds(bound);
	}
	public void setY(double y){
		cY = y;
		bound.y = (int) y - safeCeil(r);
		c = new Ellipse2D.Double(cX - bound.x - r,cY - bound.y - r, 2*r, 2*r);
		this.setBounds(bound);
	}

	public void setR(double r){
		this.r = r;
		bound.width = 2* (int) r +1;
		bound.height = bound.width;
		c = new Ellipse2D.Double(cX - bound.x - r ,cY - bound.y - r, 2*r,2*r);
		this.setBounds(bound);
	}
	public void setIo(int io){
		this.io = io;
	}
	public void recurse() {}
	public static double descartes(Circle c1, Circle c2, Circle c3) {
		Circle[] temp = {c1,c2,c3};
		Arrays.sort(temp);
		double k1 = c1.io/temp[0].r;
		double k2 = c2.io/temp[1].r;
		double k3 = c3.io/temp[2].r;
		double k4 = k1 + k2 + k3 + 2*Math.sqrt(Math.abs(k1*k2 + k1*k3 + k2*k3));
		return 1/k4;

	}
	public static double[][] intersect(Circle c1, Circle c2) throws Exception{
		double[][] dd;
		System.out.printf("C1:(%f,%f)%f\n",c1.cX,c1.cY,c1.r);
		double theta1 = Math.acos(sDist(c1,c2)/c1.r);
		double theta2 = Math.atan((c2.cY - c1.cY)/(c2.cX - c1.cX));
		if (dist(c1,c2) > c1.r + c2.r){
			throw new Exception();
		}
		else if(Math.abs(dist(c1,c2) - (c1.r + c2.r)) <= .01){
			dd = new double[][] {{c1.cX + c1.r*(c2.cX - c1.cX )/(c1.r + c2.r), c1.cY-c1.r*(c2.cY-c1.cY)/(c1.r+c2.r)}};
		}
		else {
			dd = new double[][] {ptRotation(c1.cX+c1.r*Math.cos(theta1), c1.cY+c1.r*Math.sin(theta2),c1.cX+c1.r*Math.cos(theta1),c1.cY+c1.r*Math.sin(theta2)+c1.r*Math.sin(theta1),theta2),
					     ptRotation(c1.cX+c1.r*Math.cos(theta1), c1.cY+c1.r*Math.sin(theta2),c1.cX+c1.r*Math.cos(theta1),c1.cY+c1.r*Math.sin(theta2)+c1.r*Math.sin(theta1),theta2+Math.PI)
				};
		}
		return dd;


	}
	public String toString(){
		return String.format("(%.2f,%.2f),%.2f"
	}
	public static double[][] intersect(Circle c1, Circle c2, Circle c3) throws Exception {
		ArrayList<Circle> cs = new ArrayList<Circle>(){{
			this.add(c1);
			this.add(c2);
			this.add(c3);
		}};
		for (int i = 0; i < 3; ++i)
			if (cs.get(i).io < 0)
				cs.set(i,cs.set(0,cs.get(i)));

		double[][] dd = intersect(cs.get(1),cs.get(2));
		if (dd.length == 2)
			if (Math.abs(cPtDist(dd[0][0],dd[0][1],cs.get(0)) - cs.get(0).r) <= .001)
				if( Math.abs(cPtDist(dd[0][0],dd[0][1],cs.get(0)) - cs.get(0).r) <= .001)
					return dd;
				else
					return new double[][] {dd[0]};
			else
				return new double[][] {dd[1]};
		else //(dd.length)
			return dd;


	}
	public static double sDist(Circle c1, Circle c2){
		double temp = (Math.pow(c1.r,2) - Math.pow(c2.r,2) + Math.pow(dist(c1,c2),2) )/ (2*(dist(c1,c2)));
		return temp;

	}
	public static double[] ptRotation(double xo ,double yo ,double xp ,double yp, double theta){
		return new double[] {Math.cos(theta) * (xp - xo) - Math.sin(theta) * (yp - yo) + xo, Math.sin(theta) * (xp - xo) + Math.cos(theta) * (yp - yo) + yo};

	}
	public int compareTo(Object o){
		Circle c = (Circle) o;
		return (int) this.r - (int) c.r;

	}
	public static double dist(Circle c1, Circle c2){
		return Math.sqrt(Math.pow(c1.cX - c2.cX,2) + Math.pow(c1.cY - c2.cY,2));
	}
	public static double dist(Point p1, Point p2){
		return Math.sqrt(Math.pow(p1.x - p2.x,2) + Math.pow(p1.y - p2.y,2));
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
		Circle c4 = new Circle(c2,descartes(c1,c2,c3));
		Circle c5 = new Circle(c3,descartes(c1,c2,c3));
		Circle c6 = new Circle(a[0][0],a[0][1],descartes(c1,c2,c3));
		//Circle c7 = new Circle(a[1][0],a[1][1],descartes(c1,c2,c3));
		//Circle c7 = new Circle(
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
		p.add(c6);
		//p.add(c7);


	}
}
