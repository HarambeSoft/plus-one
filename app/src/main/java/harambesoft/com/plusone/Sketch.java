package harambesoft.com.plusone;

import processing.core.*;

import java.util.ArrayList;

public class Sketch extends PApplet {



    double longitude, latitude, altitude;
    String tmp  = "https://maps.googleapis.com/maps/api/staticmap?&center=39.78492138772224,30.509143963437293&zoom=16&size=480x800&key=AIzaSyAc356Rv5G_qfLWokpLdTt5apP8ToSiiV8";
    boolean loaded = false;
    PImage mapImage;
    String base_url = "https://maps.googleapis.com/maps/api/staticmap?&center=";
    String url = "";
    ArrayList<RotatingCube> woods = new ArrayList<RotatingCube>();
    ArrayList<BillBoard> billboards = new ArrayList<BillBoard>();

    public int unloadedTimeStart=0;
    boolean value = false;

    int time=0;
    public void setup() {

        textAlign(CENTER, CENTER);
        textSize(36);
    }

    public void draw() {
        background(255);



        if(loop){
            if (mapImage!=null) {
                image(mapImage, 0, 0, width, height);

                fill(255, 0, 0, 255);
                strokeWeight(2);
                stroke(255, 255, 0, 255);
                ellipseMode(CENTER);
                ellipse(width/2, height/2, 30, 30);
            } else {
                fill(0);
                text("No Signal!", width/2, height/2);

            }

            for(int i=0;i<woods.size();i++){
                woods.get(i).rotateCube();
            }
            for(int i=0;i<billboards.size();i++){
                billboards.get(i).drawBillBoard();
            }
        }
        else{
            loadPixels();
            for(int i=0;i<width*height;i++){
                pixels[i] = color(0,0,0);
            }
            updatePixels();
        }



        if(time==0) time = millis();
        else if(millis()-time >=10000){
            time = millis();
        }

        translate(width-50,50);
        rectMode(CENTER);
        fill(0);
        noStroke();
        rect(0,0,40,40);
        fill(255);
        rect(0,0,30,30);


  /* text("Latitude: " + latitude + "\n" +
   "Longitude: " + longitude + "\n" +
   "Altitude: " + altitude + "\n" +
   "Provider: " + location.getProvider(),  0, 0, width, height);
   // getProvider() returns "gps" if GPS is available
   // otherwise "network" (cell network) or "passive" (WiFi MACID)*/
    }

    public void onLocationEvent(double _latitude, double _longitude, double _altitude)
    {
        longitude = _longitude;
        latitude = _latitude;
        altitude = _altitude;
        url = base_url +_latitude+","+_longitude+"&zoom=17"+"&size="+width+"x"+height+"&key=AIzaSyAc356Rv5G_qfLWokpLdTt5apP8ToSiiV8";
        if (_latitude!=0.0d && _longitude!=0.0d && _altitude!=0.0d)
            mapImage = loadImage(url);
        else {
            unloadedTimeStart = millis();
            mapImage = null;
        }

  /*println("\n\n"+url+"\n---------------------------------------\n");
   println("lat/lon/alt: " + latitude + "/" + longitude + "/" + altitude);
   println(width+" - "+height+"\n"+mapImage.width+" - "+mapImage.height);*/
    }
    boolean loop = true;
    public void mousePressed(){

            if(loop) {

                RotatingCube cube = new RotatingCube(mouseX,mouseY,0,false,false,true);
                woods.add(cube);
            }

    }


/*if( loop){

  loadPixels();
  for(int i=0;x<width*height;x++){
  pixels[i] = color(0,0,0);
  }
  updatePixels();*/

    public class BillBoard{

        int centerX=0,centerY=0,centerZ=0;
        public int x = color(200,200,200,255);
        public PImage ads = null;
        float angle = 0;
        public BillBoard(){

        }
        public BillBoard(PImage image){
            ads = image;
        }
        public BillBoard(int x,int y, int z, PImage image){
            centerX = x;
            centerY = y;
            centerZ = z;
            ads = image;
        }
        public BillBoard(int x,int y, int z){
            centerX = x;
            centerY = y;
            centerZ = z;
        }

        public void drawBillBoard(){//DEMO PURPOSE CODE
            pushMatrix();
            stroke(51);
            fill(x);
            translate(centerX-15,centerY-15,40);
            box(5,5,80);
            translate(30,30,0);
            box(5,5,80);

            translate(-15,-15,40);
            rotate(radians(315));
            box(5,45,5);

            translate(0,0,-60);
            box(5,45,5);

            rectMode(CENTER);
            stroke(255,255,0,255);
            fill(0);
            translate(0,0,+30);
            rotateY(radians(90));
            rect(0,0,30,30);

            popMatrix();
        }


    }

    public class RotatingCube {
        public int roughSize = 0;
        public int cubeColor = color(139, 69, 19,255);
        public float axisSpeed = 0.05f; //##########DEMO PURPOSES##############
        public String texts = "a";

        public int waitTime = 1000; // MILLISECONDS

        private boolean axisX = false, axisY = false, axisZ = false; //ROTATING TO WHICH ANGLE
        private int centerX = width/2, centerY = height/2, centerZ = 0; //CENTER POINTS
        private float axisSpeedX = 0.0f, axisSpeedY = 0.0f, axisSpeedZ = 0.0f; //AXIS'S SPEED RATES
        private int edgeLenght = 50;  //CUBES ONE EDGE LENGHT VARIES ON EVERY PHONE SO MAKE RESIZEBLE WITH SCREEN WIDTG AND HEIGHT
        private boolean opaque = false;


        public RotatingCube() { //EMPTY CONSTRUCTOR
        }

        public RotatingCube(boolean x, boolean y, boolean z) { //WHICH WAYS TO ROTATE
            axisX = x;
            axisY = y;
            axisZ = z;
        }

        public RotatingCube(int pointX, int pointY, int pointZ) { //WHICH WAYS TO ROTATE AND CUSTOMIZED CENTER POINTS
            centerX = pointX;
            centerY = pointY;
            centerZ = pointZ;
        }

        public RotatingCube(int pointX, int pointY, int pointZ, boolean x, boolean y, boolean z) { //WHICH WAYS TO ROTATE AND CUSTOMIZED CENTER POINTS
            centerX = pointX;
            centerY = pointY;
            centerZ = pointZ;
            axisX = x;
            axisY = y;
            axisZ = z;
        }

        public RotatingCube(int pointX, int pointY, int pointZ, int edge, boolean x, boolean y, boolean z) { //WHICH WAYS TO ROTATE AND CUSTOMIZED CENTER POINTS && EDGE LENGHT
            edgeLenght = edge;
            centerX = pointX;
            centerY = pointY;
            centerZ = pointZ;
            axisX = x;
            axisY = y;
            axisZ = z;
        }

        public void rotateCube() {
            pushMatrix();

            translate(centerX, centerY, centerZ+width/40);
            if (axisX) {
                axisSpeedX += axisSpeed;
                rotateX(axisSpeedX);
            }
            if (axisY) {
                axisSpeedY += axisSpeed;
                rotateY(axisSpeedY);
            }
            if (axisZ) {
                axisSpeedZ += axisSpeed;
                rotateZ(axisSpeedZ);
            }

            stroke(0);
            fill(cubeColor);
            box(width/20);



            popMatrix();
        }
    }
    public void settings() {  fullScreen(P3D,1); }
    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[] { "Sketch" };
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}