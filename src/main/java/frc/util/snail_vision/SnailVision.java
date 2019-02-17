/*
Created by Adam Zamlynny
Contact: azamlynny@hotmail.com
*/

package frc.util.snail_vision;

import edu.wpi.first.networktables.*;

import com.kauailabs.navx.frc.*;

import java.util.*;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Timer;

public class SnailVision {
    public double ANGLE_CORRECT_P;
    public double ANGLE_CORRECT_F;
    public double ANGLE_CORRECT_MIN_ANGLE;

    public double GET_IN_DISTANCE_P;
    public double GET_IN_DISTANCE_ERROR;
    public String DISTANCE_ESTIMATION_METHOD; // Either "trig" or "area"

    public double CAMERA_HEIGHT;
    public double CAMERA_ANGLE;

    public double horizontalAngleFromTarget; // -180 to 0 to 180 degrees. Represents the last location the target was seen.

    public ArrayList<Double> storedTargetAreaValues;
    public ArrayList<Double> targetAreaValues;

    public ArrayList<Double> TargetX; // Target's angle on the x-axis 
    public ArrayList<Double> TargetY; // Target's angle on the y-axis 
    public ArrayList<Double> TargetA; // Target's area on the screen
    public ArrayList<Boolean> TargetV; // Target's Visibility on the screen 1.0 = true 0.0 = false
    public ArrayList<Double> TargetS; // Target's skew/rotation on the screen
    public ArrayList<Integer> Latency; // Latency of the camera in miliseconds
    public ArrayList<Double> TargetShort; // Sidelength of shortest side of the fitted bounding box (pixels)
    public ArrayList<Double> TargetLong; // Sidelength of longest side of the fitted bounding box (pixels)
    public ArrayList<Double> TargetHorizontal; // Horizontal length of the fitted bounding box
    public ArrayList<Double> TargetVertical; // Vertical length of the fitted bounding box
    public ArrayList<Byte> currentPipeline; // Array because it might be used when switching pipeline

    public  ArrayList<Target> TARGETS = new ArrayList<Target>();

    // Gyroscope Variables
    public boolean useGyro;
    public String rotationalAxis; // Yaw - navx is flat pointing forward Pitch - navx is vertical pointing forward Roll - navx is vertical pointing sideways
    public AHRS navx;
    public double resetAngle;
    public double currentAccelleration;
    public double pastAccelleration; // 1 iteration behind
    public Timer Timer;
    public double instantaneousJerk;
    public double JERK_COLLISION_THRESHOLD; // What the jerk has to be for it to be considered a collision
    public boolean printIterationTime;

    public SnailVision(boolean utilizeGyro){
        TargetX = new ArrayList<Double>(); // Target's angle on the x-axis 
        TargetY = new ArrayList<Double>(); // Target's angle on the y-axis 
        TargetA = new ArrayList<Double>(); // Target's area on the screen
        TargetV = new ArrayList<Boolean>(); // Target's visibility on the screen
        TargetS = new ArrayList<Double>(); // Target's skew/rotation on the screen
        Latency = new ArrayList<Integer>(); // Latency of the camera in miliseconds
        TargetShort = new ArrayList<Double>(); // Sidelength of shortest side of the fitted bounding box (pixels)
        TargetLong = new ArrayList<Double>(); // Sidelength of longest side of the fitted bounding box (pixels)
        TargetHorizontal = new ArrayList<Double>(); // Horizontal length of the fitted bounding box
        TargetVertical = new ArrayList<Double>(); // Vertical length of the fitted bounding box
        currentPipeline = new ArrayList<Byte>(); // Array because it might be used when switching pipeline. Byte saves RAM
        
        useGyro = utilizeGyro;
        if(useGyro == true){
            rotationalAxis = "yaw"; // Default is yaw
            navx = new AHRS(Port.kMXP);
            pastAccelleration = 0;
            currentAccelleration = 0;
            Timer = new Timer();
            Timer.start();
            printIterationTime = false;
        }
    }

    public void networkTableFunctionality(NetworkTable Table){ // Works with limelight!
        // NetworkTable Table = NetworkTableInstance.getDefault().getTable("limelight");
        
        // Creates objects which retrieve data from the limelight. The limelight MUST have these entries. Fill with 0 if they do not exist
        NetworkTableEntry txE = Table.getEntry("tx");
        NetworkTableEntry tyE = Table.getEntry("ty");
        NetworkTableEntry taE = Table.getEntry("ta");
        NetworkTableEntry tvE = Table.getEntry("tv");
        NetworkTableEntry tsE = Table.getEntry("ts");
        NetworkTableEntry tlE = Table.getEntry("tl");
        NetworkTableEntry tshortE = Table.getEntry("tshort");
        NetworkTableEntry tlongE = Table.getEntry("tlong");
        NetworkTableEntry thorE = Table.getEntry("thor");
        NetworkTableEntry tvertE = Table.getEntry("tvert");
        NetworkTableEntry tgetpipeE = Table.getEntry("getpipe");

        TargetX.add(0, txE.getDouble(0)); // Target's angle on the x-axis 
        TargetY.add(0, tyE.getDouble(0)); // Target's angle on the y-axis 
        TargetA.add(0, taE.getDouble(0)); // Target's area on the screen
        double targetVisible = tvE.getDouble(0); // Converts double 1.0 or 0.0 for visibility to a boolean to save RAM
        if(targetVisible == 1.0){
            TargetV.add(0, true);
        }
        else if(targetVisible == 0.0){
            TargetV.add(0, false);
        }
        TargetS.add(0, tsE.getDouble(0)); // Target's skew/rotation on the screen
        Latency.add(0, (int) tlE.getDouble(0)); // Latency of the camera in miliseconds
        TargetShort.add(0, tshortE.getDouble(0)); // Sidelength of shortest side of the fitted bounding box (pixels)
        TargetLong.add(0, tlongE.getDouble(0)); // Sidelength of longest side of the fitted bounding box (pixels)
        TargetHorizontal.add(0, thorE.getDouble(0)); // Horizontal length of the fitted bounding box
        TargetVertical.add(0, tvertE.getDouble(0)); // Vertical length of the fitted bounding box
        currentPipeline.add(0, (byte) tgetpipeE.getDouble(0));
            
        if(TargetX.size() > 60){ // Removes the last entry in the arraylist and shifts over the rest
            TargetX.remove(60); // Target's angle on the x-axis 
            TargetY.remove(60); // Target's angle on the y-axis 
            TargetA.remove(60); // Target's area on the screen
            TargetV.remove(60); // Target's visibility on the screen 
            TargetS.remove(60); // Target's skew/rotation on the screen
            Latency.remove(60); // Latency of the camera in miliseconds
            TargetShort.remove(60); // Sidelength of shortest side of the fitted bounding box (pixels)
            TargetLong.remove(60); // Sidelength of longest side of the fitted bounding box (pixels)
            TargetHorizontal.remove(60); // Horizontal length of the fitted bounding box
            TargetVertical.remove(60); // Vertical length of the fitted bounding box
            currentPipeline.remove(60); // Array because it might be used when switching pipeline
        }
    }

    public double angleCorrect(){
        double tx = TargetX.get(0); // Gets the angle of how far away from the corsshair the object is

        double Kf = ANGLE_CORRECT_F;  // Minimum motor input to move robot in case P can't do it 
        double Kp = ANGLE_CORRECT_P; // for PID
        double heading_error = tx; 
        double steering_adjust = 0.0;

        if (tx > ANGLE_CORRECT_MIN_ANGLE){ // If the angle of the target is farther than n degrees do normal pid
            steering_adjust = Kp * heading_error;
        } 
        else if (tx < ANGLE_CORRECT_MIN_ANGLE) // If angle fo the target is less than n degrees, the motor will not be able to move the robot due to friction, so Kf is added to give it the minimum speed to move
        {
            steering_adjust = Kp * heading_error + Kf;
        }

        return(-steering_adjust); // return motor output
    }

    public double getInDistance(Target Target){ // targetHeightLevel is used for if there are multiple levels of targets 
        double currentDistance = 0;

        if(DISTANCE_ESTIMATION_METHOD == "area"){
            currentDistance = areaDistance(Target);
        }
        else if(DISTANCE_ESTIMATION_METHOD == "trig"){
            currentDistance = trigDistance(Target);
        }
        else{
            return(0); // No distance estimation method was selected
        }
        
        double distanceError = currentDistance - Target.DESIRED_DISTANCE;
        double driving_adjust = 0;

        if(distanceError > GET_IN_DISTANCE_ERROR){ // 3 inches of error space for PID
            driving_adjust = GET_IN_DISTANCE_P * distanceError;
        }

        return(driving_adjust); // return motor output
    }
    
    public double areaDistance(Target Target){ // Returns inches significant up to the tenths place
        double ta = TargetA.get(0);
        boolean tv0 = TargetV.get(0); // Looks back 3 frames to see if the target was on the screen just to make sure that the limelight glitched and did not see the target for a split second
        boolean tv1 = TargetV.get(1);
        boolean tv2 = TargetV.get(2);
        double minDifference = 10000; // Just so that it finds a smaller value
        int minIndex = Target.AREA_PERCENT_MEASUREMENTS - 1; // The indexes of the array of percentages is the distance that the robot is from the target in inches
         if(tv0 == true || tv1 == true || tv2 == true){ // If the target is on screen in the past 3 frames
            for(int i = 0; i < Target.AREA_PERCENT_MEASUREMENTS - 1; i++){
                if(Target.AREA_TO_DISTANCE[i] - ta < minDifference && Target.AREA_TO_DISTANCE[i] - ta > 0){ // Finds the smallest difference in areas to find the distance the robot is from the target
                    minDifference = Target.AREA_TO_DISTANCE[i] - ta;
                    minIndex = i; 
                }
            }

            // Finds the average of the area between the two recorded constant points to find the average inch measurement in between
            if(ta > Target.AREA_TO_DISTANCE[minIndex]){
                minIndex += (ta - Target.AREA_TO_DISTANCE[minIndex]) / (Target.AREA_TO_DISTANCE[minIndex + 1] - Target.AREA_TO_DISTANCE[minIndex]);
            }
            else if (ta < Target.AREA_TO_DISTANCE[minIndex]){
                minIndex += 1 - (Target.AREA_TO_DISTANCE[minIndex] - ta) / (Target.AREA_TO_DISTANCE[minIndex + 1] - Target.AREA_TO_DISTANCE[minIndex]);
            }

            return(minIndex);
        }
        else{ // If there is no target on the screen then return 0 as a distance so that the robot does not do something unexpected
            return(0);
        }
    }

    public double trigDistance(Target Target){ // More accurate than area distance but the target has to be high in the air above the camera
        // Distance from Target = (Target Height - Camera Height) / tan(Angle of the Camera + Angle of the target above the Crosshair)
        double distanceFromTarget = (Target.TARGET_HEIGHT - CAMERA_HEIGHT) / Math.tan(Math.toRadians(CAMERA_ANGLE + TargetY.get(0)));
        return(distanceFromTarget);
    }

    public double findCameraAngle(double currentDistance, Target Target){ // Give the distance from a known target in inches
        // Camera Angle = arctan((Target Height - Camera Height) / Distance from Target) - Angle of Target Above Camera's Crosshair
        double cameraAngle = Math.atan(Math.toRadians( ( (Target.TARGET_HEIGHT - CAMERA_HEIGHT) / currentDistance) - TargetY.get(0)));
        System.out.println("Camera Angle" + cameraAngle);
        return(cameraAngle);
    }

    public double findTarget(){
        boolean tv = TargetV.get(0);

        if(tv == true){ // If the target is not on the screen then spin towards it
            if(horizontalAngleFromTarget < 0){
                return(-0.5);
            }
            else if(horizontalAngleFromTarget > 0){
                return(0.5);
            }
        }
        else if (tv == true){ // If the target is on the screen then auto-aim towards it
            return(angleCorrect());
        }

        return(0);
    }

    public void trackTargetPosition(){
        if(useGyro == true){ // Track where the target is to turn towards it quicker
            horizontalAngleFromTarget = getRotationalAngle();
        }
        else if (useGyro == false){ // Track where the target last left the screen to turn towards there
            if(TargetV.get(0) == true){ // Once the target is off screen, the function saves the last seen side 
                horizontalAngleFromTarget = TargetX.get(0); 
            }
        }
    }

    // The next 3 functions are used to record the target area to distance for distance estimation using area
    public void recordTargetArea(){ // Pressing a button loads area for that distance and removes outliers
        double ta = TargetA.get(0);
        storedTargetAreaValues.add(ta);
        Collections.sort(storedTargetAreaValues); // Sorts the values so that the maximum and minimum could be found
        if(storedTargetAreaValues.size() > 50){ // Removes outliers on the very edges
            storedTargetAreaValues.remove(0);
            storedTargetAreaValues.remove(storedTargetAreaValues.size() - 1);
        }
    }

    public void clearTargetArea(){ // Releasing the button saves an average of the good data and clears a temporary array
        storedTargetAreaValues.remove(0);
        storedTargetAreaValues.remove(storedTargetAreaValues.size() - 1); // Removes final 2 possible outliers before averaging
        double total = 0;
        for(int i = 0; i < storedTargetAreaValues.size(); i++){ // Adds up all values stored to find the average
            total += storedTargetAreaValues.get(i);
        }
        double average = total / storedTargetAreaValues.size(); // Average is found so that outliers do not impact the data
        targetAreaValues.add(average);
        storedTargetAreaValues.clear();
    }  

    public void resetTargetArea(){ // Completely resets the target measurement data collection process so that code doesn't have to be redeployed to measure another target
        clearTargetArea();
        targetAreaValues.clear();
    }

    public void printTargetArea(){ // So that the user can copy and paste the data and make it a constant
        for(int i = 0; i < targetAreaValues.size() - 1; i++){
            System.out.print(targetAreaValues.get(i) + ", ");
        }
        if(targetAreaValues.size() > 0){ // So that if a user presses the button without first saving any valeus the program does not crash
            System.out.println(targetAreaValues.get(targetAreaValues.size() - 1)); // Just used to print out data so that it could easily be copy and pasted into array format
        }
    }

    // NetworkTable functions are below

    public static void changePipeline(NetworkTable Table, int pipeline){
        if(pipeline < 0 || pipeline > 9){
            pipeline = 0; // In case that someone tries to switch to an impossible pipeline then set it to default.
        }
        Table.getEntry("pipeline").setNumber(pipeline);
    }

    // Limelight ONLY functions

    public static void toggleLimelightScreenshot(NetworkTable Table){ // Takes 2 screenshots per second
        if(Table.getEntry("snapshot").getDouble(0) == 0){ // If the camera is not taking screenshots currently
            Table.getEntry("snapshot").setNumber(1);
        }
        else if(Table.getEntry("snapshot").getDouble(0) == 1){ // If the camera is taking screenshots currently
            Table.getEntry("snapshot").setNumber(0);
        }
    }

    public static void turnOffLimelight(NetworkTable Table){
        if(Table.getEntry("ledMode").getDouble(0) != 1){
            Table.getEntry("ledMode").setNumber(1);
        }
    }

    public static void turnOnLimelight(NetworkTable Table){
        if(Table.getEntry("ledMode").getDouble(0) != 3){
            Table.getEntry("ledMode").setNumber(3);
        }
    }

    public static void blinkLimelight(NetworkTable Table){
        if(Table.getEntry("ledMode").getDouble(0) != 2){
            Table.getEntry("ledMode").setNumber(2);
        }
    }

    public static void toggleLimelightMode(NetworkTable Table){ // 0 = vision processing 1 = drive camera
        if(Table.getEntry("camMode").getDouble(0) == 0){
            Table.getEntry("camMode").setNumber(1);
        }
        else if(Table.getEntry("camMode").getDouble(0) == 1){
            Table.getEntry("camMode").setNumber(0);
        }
    }

    // Gyroscope NavX functionality - Included in SnailVision so that gyro works even if it is nowhere else in the project
    public void gyroFunctionality(){
        // Used for tracking the target offscreen
        if(TargetV.get(0) == true){
            resetRotationalAngle(); // Make the front of robot's current position 0
            resetAngle -= TargetX.get(0); // Changes the robot's current position to the center of the target
        }

        // Allows the user to see how long the RoboRIO delays between iterations if printIterationTime = true
        printIterationTime();

        // Used for jerk and collision detection
        instantaneousJerk = calculateJerk();
    }
    
    public double getRotationalAngle(){ // Angle of the robot as it rotates
        if(rotationalAxis == "yaw"){
            return getYawAngle(); // Even though yaw has a reset funciton it is used with resetAngle so that an origin could be set
        }
        else if(rotationalAxis == "roll"){
            return getRollAngle(); // resetAngle is here to act as a reset function
        }
        else if(rotationalAxis == "pitch"){
            return getPitchAngle(); // resetAngle is here to act as a reset function
        }
        else{
            return(0); // Just in case something breaks
        }
    }

    public void resetRotationalAngle(){
        if(rotationalAxis == "yaw"){
            resetAngle = navx.getYaw(); // Even though yaw has a reset funciton it is used like this so that an origin could be set
        }
        else if(rotationalAxis == "roll"){
            resetAngle = navx.getRoll();
        }
        else if(rotationalAxis == "pitch"){
            resetAngle = navx.getPitch();
        }
    }

    public double getYawAngle() {
        return (navx.getYaw() - resetAngle); // Even though yaw has a reset funciton it is used like this so that an origin could be set
    }

    public double getRollAngle() {
        return (navx.getRoll() - resetAngle);
    }
    
    public double getPitchAngle() {
        return (navx.getPitch() - resetAngle);
    }

    public double getAccelleration() {
        if(rotationalAxis == "yaw"){
           return(navx.getRawAccelX());
        }
        else if(rotationalAxis == "roll"){
            return(navx.getRawAccelY());
        }
        else if(rotationalAxis == "pitch"){
            return(navx.getRawAccelZ());
        }   
        else{
            return(0); // Just in case something breaks
        }
    }

    public double calculateJerk(){
        pastAccelleration = currentAccelleration;
        currentAccelleration = getAccelleration();
        double jerk = (pastAccelleration - currentAccelleration) / Timer.get(); // Change in accelleration = jerk
        Timer.reset();
        return(jerk);
    }

    public void printIterationTime(){
        if(printIterationTime == true){
            System.out.print(Timer.get() + ", ");
        }
    }
}