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
    float meScale=0.0f;         //VALUES FOR INDICATOR
    float meScale1 = 0.0f;      //VALUES FOR INDICATOR
    float meScale2 = 0.0f;      //VALUES FOR INDICATOR
    double[] scaleArray = { 591657550.500000f,
            295828775.300000f,
            147914387.600000f,
            73957193.820000f,
            36978596.910000f,
            18489298.450000f,
            9244649.227000f,
            4622324.614000f,
            2311162.307000f,
            1155581.153000f,
            577790.576700f,
            288895.288400f,
            144447.644200f,
            72223.822090f,
            36111.911040f,
            18055.955520f,
            9027.977761f,
            4513.988880f,
            2256.994440f,
            1128.497220f
    };
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
        for (int i=0;i<viewWidth;i+=viewWidth/5){
            for (int j=0;j<viewHeight;j+=viewHeight /5){
                backgroundImg.vertex(i,j);
                backgroundImg.vertex(i+viewWidth/5,j);
                backgroundImg.vertex(i+viewWidth/5,j+viewHeight/5);
            }
            backgroundImg.endShape(CLOSE);
        }
        size += viewWidth+"x"+viewHeight;
        //GET THE USER COORDINATES FROM CURRENTUSER CLASS
        if(CurrentUser.latitude().length()!=0) {
            userLatitude = Double.parseDouble(CurrentUser.latitude());
            userLongtitude = Double.parseDouble(CurrentUser.longitude());
            url = urlbase + center + CurrentUser.latitude() + "," + CurrentUser.longitude() + zoom + size + scale + keyf;
            println(viewWidth + " width" + viewHeight);
            image = loadImage(url);
            println(url);
        }else{
            userLatitude = 0;userLongtitude=0;
        }
    }
    public void draw() {
        background(52);
        //DRAW BACKGROUND IMAGE
        pushMatrix();
        translate(0,0,-10);
        backgroundImg.tint((255 / (frameCount+1)),(255 / (frameCount+1)),(255 / (frameCount+1)));
        shape(backgroundImg,0,0,viewWidth,viewHeight);
        popMatrix();
        if(userLongtitude==0 && userLatitude==0) {
            fill(0);
            text("No Signal!", viewWidth / 2, viewHeight / 2);
            if(CurrentUser.latitude().length()!=0){
                userLatitude = Double.parseDouble(CurrentUser.latitude());
                userLongtitude = Double.parseDouble(CurrentUser.longitude());
                loadingImage = true;
            }
        }else{ // IF GPS VALUES ARE VALID THEN DRAW SOME COOL! INDICATOR AT THE CENTER
            //CHECK THE CHANGED GPS COORDINATES IF CHANGED LOAD IMAGE
            if(frameCount%(60*5) == 0){
                if(CurrentUser.latitude().length()!=0)
                    if(userLongtitude != Double.parseDouble(CurrentUser.longitude()) || userLatitude != Double.parseDouble(CurrentUser.latitude())) {
                        userLatitude = Double.parseDouble(CurrentUser.latitude());
                        userLongtitude = Double.parseDouble(CurrentUser.longitude());
                        url = urlbase + center +CurrentUser.latitude()+","+CurrentUser.longitude()+ zoom + size + scale+ keyf;
                        println("loading url ->" + url);
                        image = loadImage(url);
                    }
            }
            //DOWNLOAD FOR NEW IMAGE
            if (loadingImage) {
                image = null;
                zoom = "&zoom=" + scaleTemp;
                println(zoom + " zoom scale");
                url = urlbase + center + zoom + size + scale+ keyf;
                println(url);
                if(CurrentUser.latitude().length()!=0)
                    image = loadImage(urlbase + center +userLatitude+","+userLongtitude+ zoom + size + scale+ keyf);
                loadingImage = false;
            }
            //ROTATE IMAGE
            if(mousePressed){
                if(mouseX-pmouseX < viewWidth/5 && pmouseX-mouseX < viewWidth/5 &&pmouseX != mouseX)rotationAngle += mouseX - pmouseX;
                if(mouseY-pmouseY < viewHeight/5 && pmouseY-mouseY < viewWidth/5 &&pmouseY != mouseY)rotationAngle -= -mouseY + pmouseY;
            }
            //PUT THE IMAGE
            if(image!=null){
                pushMatrix();
                translate(viewWidth/2,viewHeight/2);
                rotateZ(radians(rotationAngle/10));
                image(image, -image.width/2, -image.height/2);
                popMatrix();
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
            if(image != null){
                noStroke();
                pushMatrix();
                translate(0,0,viewWidth/25);
                pushMatrix();
                fill(0, 255,0,255);
                translate(viewWidth/2,viewHeight/2);
                rotateZ(meScale);rotateY(meScale1);rotateX(meScale2);
                translate(noise(meScale)*3, noise(meScale1)*3);
                ellipse(0,0,  noise(meScale)*viewWidth/50,noise(meScale)*viewWidth/50);
                popMatrix();
                pushMatrix();
                fill(0,0,255,255);
                translate(viewWidth/2,viewHeight/2);
                rotateZ(meScale2);rotateY(meScale);rotateX(meScale1);
                translate(noise(meScale1)*3, noise(meScale2)*3);
                ellipse(0,0, noise(meScale2)*viewWidth/50, noise(meScale2)*viewWidth/50);
                popMatrix();
                pushMatrix();
                fill(255,0,0,255);
                translate(viewWidth/2,viewHeight/2);
                rotateZ(meScale1);rotateY(meScale2);rotateX(meScale);
                translate(noise(meScale2)*3, noise(meScale)*3);
                ellipse(0,0, noise(meScale1)*viewWidth/50,noise(meScale1)*viewWidth/50);
                popMatrix();
                popMatrix();
                meScale1+=0.015;
                meScale2+=0.01;
                meScale+=0.02;
            }
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
    public Double distance(double x1, double y1, double x2, double y2) {
        //CALCULATE DISTANCE BETWEEN 2 COORDINATES
        //IF WANTED SEND THE USER COORDINATES
        Double R = 6371000.0d;
        Double v1 = Math.toRadians(x1);
        Double v2 = Math.toRadians(x2);
        Double v3 = Math.toRadians(x2 -x1);
        Double v4 = Math.toRadians(y2-y1);
        Double a = Math.sin(v3/2) * Math.sin(v3/2) +
                Math.cos(v1) * Math.cos(v2) *
                        Math.sin(v4/2) * Math.sin(v4/2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        Double distance = R * c;
        return distance;
    }
    public void toMap(double x2, double y2) {
        //FROM COORDINATES CAlCULATE THE DISTANCE THEN GET THE APPROXIMATE PIXEL LENGHT
        //AT LAST STEP FROM THE COORDINATES AND THE PIXEL LENGHT GET THE REAL PIXEL COORDINATES
        Double meter_distance = distance(userLatitude, userLongtitude, x2, y2);
        Double pixel_distance = scaleArray[scaleArray.length -1 - scaleTemp] * meter_distance /(1000*(137912.554668f)) ;
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
