package harambesoft.com.plusone;

/**
 * Created by asus on 08.01.2017.
 */

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import harambesoft.com.plusone.models.PollModel;
import harambesoft.com.plusone.services.ApiClient;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
            295828775.300000,
            147914387.600000,
            73957193.820000,
            36978596.910000,
            18489298.450000,
            9244649.227000,
            4622324.614000,
            2311162.307000,
            1155581.153000,
            577790.576700,
            288895.288400,
            144447.644200,
            72223.822090,
            36111.911040,
            18055.955520,
            9027.977761,
            4513.988880,
            2256.994440,
            1128.497220
    };
    List<Double[]> pools = new ArrayList<Double[]>();
    //URL BASED VARIABLES
    public int scaleTemp = 15;
    boolean loadingImage = false;
    String zoom = "&zoom="+scaleTemp;
    String size = "&size=";
    int scaleFactor = 1;
    String scale = "&scale"+scaleFactor;
    String keyf = "&key=AIzaSyBL98hzfjEja36P5xTwzUnCjwEs6e23WYs";
    String urlbase = "http://maps.googleapis.com/maps/api/staticmap?";
    String url = "http://maps.googleapis.com/maps/api/staticmap?";
    String center = "center=";
    //USER VARIABLES
    double userLatitude,userLongtitude;
    boolean userAtCenter = true;
    boolean movingAnimation = false;
    int movingPolls = 0;
    int movingPixels = 0;
    public void setup() {
        frameRate(60);
        smooth();
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
        addPolls();
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

    public void load(){
        image = loadImage(urlbase + center +userLatitude+","+userLongtitude+ zoom + size + scale+ keyf);
        System.out.println("downloading thread");
        reorganizePixels();
    }
    boolean safe = true;
    public void draw() {

        if(sphere == false){

            background(52);
            //DRAW BACKGROUND IMAGE
            pushMatrix();
            translate(0,0,-10);
            backgroundImg.beginShape();
            backgroundImg.tint((255 / (frameCount+1)),(255 / (frameCount+1)),(255 / (frameCount+1)));
            backgroundImg.endShape();
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
            }else{

                // IF GPS VALUES ARE VALID THEN DRAW SOME COOL! INDICATOR AT THE CENTER
                //CHECK THE CHANGED GPS COORDINATES IF CHANGED LOAD IMAGE

                //DOWNLOAD FOR NEW IMAGE
                if (loadingImage) {
                    image = null;
                    zoom = "&zoom=" + scaleTemp;
                    println(zoom + " zoom scale");
                    if(CurrentUser.latitude().length()!=0)
                        thread("load");

                    println(urlbase + center +userLatitude+","+userLongtitude+ zoom + size + scale+ keyf);
                    loadingImage = false;
                }
                //ROTATE IMAGE
                if(mousePressed){
                    //movingPolls += mouseX - pmouseX;
                    //movingPixels += mouseY - pmouseY;
                }
                //PUT THE IMAGE
                if(image!=null){
                    pushMatrix();
                    translate(viewWidth/2,viewHeight/2);
                    rotateZ(radians(rotationAngle/10));
                    image(image, -viewWidth + movingPolls, -viewHeight + movingPixels,viewWidth*2,viewHeight*2);
                    if(safe)
                        drawRects();
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
                    translate(0,0,20);


                    if(userAtCenter){

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
        }else{
            dra();
        }

    }
    public void mousePressed() {
           // MOVE IN THE MAPS

            for (int i=0;i<pixelCoords.size();i++) {
                if ((mouseX-viewWidth/2)+(viewWidth/40) +(viewWidth/80)>= pixelCoords.get(i)[0] && (mouseX-viewWidth/2)-(viewWidth/40) +(viewWidth/80)<=pixelCoords.get(i)[0] ){
                    if ((mouseY-viewHeight/2)+(viewWidth/40) +(viewWidth/80) >= pixelCoords.get(i)[1] &&(mouseY-viewHeight/2)-(viewWidth/40) +(viewWidth/80)<=pixelCoords.get(i)[1] ) {
                        App.showPoll(pools.get(i)[2].intValue());
                        break;
                    }
                }
                userAtCenter = false;
            }
            userAtCenter = false;

        if (mouseX >= viewWidth - (viewWidth / 10) && mouseY <= viewHeight / 10) {
            keyPressed();

            return;
        }
        else if (mouseX >= viewWidth - (viewWidth / 10) && mouseY <= viewHeight / 5) {
            zoomOut();
            return;
        }else {
            System.out.println("3th");
            sphere = !sphere;
            setu();
        }


    }
    public void keyPressed(){ // ZOOM IN
        if(scaleTemp ==19) {loadingImage = false;return;}
        scaleTemp++;
        println("Zooming");
        scaleTemp = constrain(scaleTemp,7,19);
        rotationAngle = 0.0f;
        loadingImage = true;
        thread("load");


    }
    public void zoomOut(){ //ZOOM OUT
        if(scaleTemp ==7) {loadingImage = false;return;}
        scaleTemp--;
        println("Outing");
        scaleTemp = constrain(scaleTemp,7,19);
        rotationAngle = 0.0f;
        loadingImage = true;

        thread("load");
    }

    public synchronized void reorganizePixels(){
        safe = false;
        System.out.println("Reorganizing!!");
        pixelCoords.clear();
        Double[] tmp;
        for (int i = 0; i< pools.size();i++){
            tmp = toMap(pools.get(i)[0],pools.get(i)[1]);
            pixelCoords.add(tmp);
        }
        safe = true;
    }

    public synchronized void drawRects(){
        stroke(0);
        fill(52);
        rectMode(CENTER);
        for (int i=0;i<pools.size();i++){
            pushMatrix();
            if (pixelCoords!=null)
                if(i<=pixelCoords.size()-1)
                    translate((pixelCoords.get(i)[0].floatValue())+movingPolls,(pixelCoords.get(i)[1].floatValue())+movingPixels,8);
            box(viewWidth/40);
            popMatrix();
        }
    }

    public Double distance(double x1, double y1, double x2, double y2) {
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
    public void pixelToCoordinate(int x,int y){
        double R = 6371000.0d;
        double lat = 110.574d;
        double lon =  111.320d * Math.cos(lat);

        double x_km_distance = -x * (137912.554668d) / ( scaleArray[scaleArray.length -1 - scaleTemp] * 2.0d) ;
        double y_km_distance = -y * (137912.554668d) / ( scaleArray[scaleArray.length -1 - scaleTemp] * 2.0d) ;

        double distance = Math.sqrt((x_km_distance*x_km_distance) + (y_km_distance*y_km_distance));
        loadingImage= true;
        userLatitude += y_km_distance/lat;
        userLongtitude +=x_km_distance/lon;
        movingAnimation = true;
        movingPolls = -x;
        movingPixels = -y;
    }

    public Double[] toMap(double x2, double y2) {
        //FROM COORDINATES CAlCULATE THE DISTANCE THEN GET THE APPROXIMATE PIXEL LENGHT
        //AT LAST STEP FROM THE COORDINATES AND THE PIXEL LENGHT GET THE REAL PIXEL COORDINATES
        Double meter_distanceY = distance(userLatitude, userLongtitude, x2, userLongtitude);
        Double meter_distanceX = distance(userLatitude, userLongtitude, userLatitude, y2);
        double pixel_distanceX = 2.0d*Math.signum(-userLongtitude+y2)*scaleArray[scaleArray.length -1 - scaleTemp] * meter_distanceX /(1000d*(137912.554668d));
        double pixel_distanceY = 2.0d*Math.signum(-x2+userLatitude)*scaleArray[scaleArray.length -1 - scaleTemp] * meter_distanceY /(1000d*(137912.554668d)) ;

        Double[] tmp = {pixel_distanceX,pixel_distanceY};
        return tmp;
    }
    ArrayList<Double[]> pixelCoords = new ArrayList<Double[]>();
    public void addPolls(){

        ApiClient.apiService().getNearPolls(CurrentUser.latitude(), CurrentUser.longitude(), "1000", CurrentUser.apiToken()).enqueue(new Callback<List<PollModel>>() {
            @Override
            public void onResponse(Call<List<PollModel>> call, Response<List<PollModel>> response) {
                if(response.body()!=null)
                    if(!response.body().isEmpty()){
                        Double[] tmp ;
                        pools.clear();
                        pixelCoords.clear();
                        for (PollModel pollModel: response.body()) {
                            Log.d("near polls: ", pollModel.getQuestion());
                            tmp = new Double[3];
                            tmp[0] = Double.parseDouble(pollModel.getLatitude());
                            tmp[1] = Double.parseDouble(pollModel.getLongitude());
                            tmp[2] = pollModel.getId().doubleValue();
                            pools.add(tmp);
                            Double[] val = toMap(tmp[0],tmp[1]);
                            pixelCoords.add(val);
                        }
                    }
            }

            @Override
            public void onFailure(Call<List<PollModel>> call, Throwable t) {

            }
        });


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
            translate((float)(centerX -(viewWidth/2)*Math.pow(2,scaleTemp-15)), (float)((centerY -(viewHeight/2)*Math.pow(2,scaleTemp-15))), centerZ);
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
            box(25);
            popMatrix();
        }
    }
    public void settings() {
        while(viewWidth == 0 && viewHeight == 0);
        size(viewWidth,viewHeight,OPENGL);
    }
    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[] { "Sketch" };
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }

    PImage img;

    int numPointsW;
    int numPointsH_2pi;
    int numPointsH;
    int ptsW, ptsH;
    float[] coorX;
    float[] coorY;
    float[] coorZ;
    float[] multXZ;
    public void initializeSphere(int numPtsW, int numPtsH_2pi) {

        // The number of points around the width and height
        numPointsW=numPtsW+1;
        numPointsH_2pi=numPtsH_2pi;  // How many actual pts around the sphere (not just from top to bottom)
        numPointsH=ceil((float)numPointsH_2pi/2)+1;  // How many pts from top to bottom (abs(....) b/c of the possibility of an odd numPointsH_2pi)

        coorX=new float[numPointsW];   // All the x-coor in a horizontal circle radius 1
        coorY=new float[numPointsH];   // All the y-coor in a vertical circle radius 1
        coorZ=new float[numPointsW];   // All the z-coor in a horizontal circle radius 1
        multXZ=new float[numPointsH];  // The radius of each horizontal circle (that you will multiply with coorX and coorZ)

        for (int i=0; i<numPointsW ;i++) {  // For all the points around the width
            float thetaW=i*2*PI/(numPointsW-1);
            coorX[i]=sin(thetaW);
            coorZ[i]=cos(thetaW);
        }

        for (int i=0; i<numPointsH; i++) {  // For all points from top to bottom
            if (PApplet.parseInt(numPointsH_2pi/2) != (float)numPointsH_2pi/2 && i==numPointsH-1) {  // If the numPointsH_2pi is odd and it is at the last pt
                float thetaH=(i-1)*2*PI/(numPointsH_2pi);
                coorY[i]=cos(PI+thetaH);
                multXZ[i]=0;
            }
            else {
                //The numPointsH_2pi and 2 below allows there to be a flat bottom if the numPointsH is odd
                float thetaH=i*2*PI/(numPointsH_2pi);

                //PI+ below makes the top always the point instead of the bottom.
                coorY[i]=cos(PI+thetaH);
                multXZ[i]=sin(thetaH);
            }
        }
    }
    public void textureSphere(float rx, float ry, float rz, PImage t) {
        // These are so we can map certain parts of the image on to the shape
        float changeU=4.0f*t.width/(float)(numPointsW-1);
        float changeV=t.height/(float)(numPointsH-1);
        float u=0;  // Width variable for the texture
        float v=0;  // Height variable for the texture

        beginShape(TRIANGLE_STRIP);
        texture(t);
        for (int i=0; i<(numPointsH-1); i++) {  // For all the rings but top and bottom
            // Goes into the array here instead of loop to save time
            float coory=coorY[i];
            float cooryPlus=coorY[i+1];

            float multxz=multXZ[i];
            float multxzPlus=multXZ[i+1];

            for (int j=0; j<numPointsW; j++) { // For all the pts in the ring
                normal(-coorX[j]*multxz, -coory, -coorZ[j]*multxz);
                vertex(coorX[j]*multxz*rx, coory*ry, coorZ[j]*multxz*rz, u, v);
                normal(-coorX[j]*multxzPlus, -cooryPlus, -coorZ[j]*multxzPlus);
                vertex(coorX[j]*multxzPlus*rx, cooryPlus*ry, coorZ[j]*multxzPlus*rz, u, v+changeV);
                u+=changeU;
            }
            v+=changeV;
            u=0;
        }
        endShape();
    }
    boolean sphere = false;
    public void setu(){
        img=loadImage(url);
        background(0);
        noStroke();
        ptsW=30;
        ptsH=30;
        initializeSphere(100,100);
    }
    public void dra(){
        background(52);
        pushMatrix();
        translate(viewWidth/2,viewHeight/2,-mouseX*4 + 500);
        rotateX(radians(mouseY/10));
        rotateY(radians(-45));
        textureSphere(100, 100, 100, img);
        popMatrix();
    }
}
