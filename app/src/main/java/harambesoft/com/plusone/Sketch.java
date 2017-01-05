package harambesoft.com.plusone;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;

public class Sketch extends PApplet {
    //SKETCH VARIABLES
    public static int viewWidth= 0,viewHeight=0;
    PShape backgroundImg;
    PImage image ;
    float rotationAngle = 0.0f;

    //URL BASED VARIABLES
    public int scaleTemp = 8;
    boolean loadingImage = false;
    String zoom = "&zoom="+scaleTemp;
    String size = "&size=";
    String scale = "&scale=1";
    String keyf = "&key=AIzaSyBL98hzfjEja36P5xTwzUnCjwEs6e23WYs";
    String urlbase = "https://maps.googleapis.com/maps/api/staticmap?";
    String url = "https://maps.googleapis.com/maps/api/staticmap?";
    String center = "center=";

    //USER VARIABLES
    ArrayList<RotatingCube> woods = new ArrayList<RotatingCube>();
    ArrayList<BillBoard> billboards = new ArrayList<BillBoard>();
    double userLatitude,userLongtitude;


    public void setup() {
        frameRate(60);
        textAlign(CENTER);
        textSize(viewWidth/10);

        //CREATE BACKGROUND SHAPE DISPLAY
        backgroundImg = createShape();
        backgroundImg.noFill();
        backgroundImg.strokeWeight(20);
        for (int i=0;i<viewWidth;i+=viewWidth/5){
            backgroundImg.beginShape();
            for (int j=0;j<viewHeight;j+=viewHeight /5){
                backgroundImg.vertex(i,j);
                backgroundImg.vertex(i+viewWidth/5,j);
                backgroundImg.vertex(i+viewWidth/5,j+viewHeight/5);
            }
            backgroundImg.endShape(CLOSE);
        }

        size += viewWidth+"x"+viewHeight;

        //GET THE USER COORDINATES FROM CURRENTUSER CLASS
        userLatitude = Double.parseDouble(CurrentUser.latitude());
        userLongtitude = Double.parseDouble(CurrentUser.longitude());
        url = urlbase + center +CurrentUser.latitude()+","+CurrentUser.longitude()+ zoom + size + scale+ keyf;
        println(viewWidth+" width" +viewHeight);
        image = loadImage(url);
        println(url);

    }

    public void draw() {
        background(52);

        //CHECK THE CHANGED GPS COORDINATES IF CHANGED LOAD IMAGE
        if(frameCount%(60*5) == 0){
            if(userLongtitude != Double.parseDouble(CurrentUser.longitude()) || userLatitude != Double.parseDouble(CurrentUser.latitude())) {
                userLatitude = Double.parseDouble(CurrentUser.latitude());
                userLongtitude = Double.parseDouble(CurrentUser.longitude());
                url = urlbase + center +CurrentUser.latitude()+","+CurrentUser.longitude()+ zoom + size + scale+ keyf;
                println("loading url ->" + url);
                image = loadImage(url);
            }

        }

        //DRAW BACKGROUND IMAGE
        pushMatrix();
        translate(0,0,-10);
        shape(backgroundImg,0,0,viewWidth,viewHeight);
        popMatrix();

        //DOWNLOAD FOR NEW IMAGE
        if (loadingImage) {
            image = null;
            zoom = "&zoom=" + scaleTemp;
            println(zoom + " zoom scale");
            url = urlbase + center + zoom + size + scale+ keyf;
            println(url);
            image = loadImage(urlbase + center +CurrentUser.latitude()+","+CurrentUser.longitude()+ zoom + size + scale+ keyf);
            loadingImage = false;
        }

        if(image!=null){
            image(image, 0,0);
        }

        //DRAW THE BUTTONS
        noStroke();
        fill(240,200);
        rectMode(CENTER);
        rect(viewWidth-(viewWidth/20), viewHeight/20,viewHeight/10,viewHeight/10);
        textSize(viewHeight/20);
        textAlign(CENTER);
        fill(0);
        text("+",viewWidth-(viewWidth/20) - (textWidth("+")/2), viewHeight/20 + (textWidth("+")/2 ));
        fill(40,200);
        rect(viewWidth-(viewWidth/20), viewHeight/20 + viewHeight/10,viewHeight/10,viewHeight/10);
        fill(255);
        text("-",viewWidth-(viewWidth/20) - (textWidth("+")/2),textWidth("-") + 3*viewHeight/20 );


        if(viewWidth==0) {
            fill(0);
            text("No Signal!", viewWidth / 2, viewHeight / 2);
        }


    }

    public void mousePressed() {
        if (mouseX >= viewWidth - (viewWidth / 10) && mouseY <= viewHeight / 10) {
            keyPressed();
            return;
        }
        if (mouseX >= viewWidth - (viewWidth / 10) && mouseY <= viewHeight / 5) {
            zoomOut();
            return;
        }
    }

    public void keyPressed(){ // ZOOM IN
        loadingImage = true;
        scaleTemp++;
        scaleTemp = constrain(scaleTemp,7,19);
        rotationAngle = 0.0f;
    }
    public void zoomOut(){ //ZOOM OUT
        loadingImage = true;
        scaleTemp--;
        scaleTemp = constrain(scaleTemp,7,19);
        rotationAngle = 0.0f;
    }


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
        private int centerX = viewWidth/2, centerY = viewHeight/2, centerZ = 0; //CENTER POINTS
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

            translate(centerX, centerY, centerZ+viewWidth/40);
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
            box(viewWidth/20);



            popMatrix();
        }
    }
    public void settings() {
        while(viewWidth == 0 && viewHeight == 0);
        size(viewWidth,viewHeight,P3D);
    }


    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[] { "Sketch" };
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }



}