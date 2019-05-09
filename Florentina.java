package maria;

import java.awt.Color;

import robocode.*;
import robocode.util.Utils;



public class Florentina extends Robot {
	
	int moveCounter;
	String sentry;
	String target;
	
	public void run() {
		
		setAdjustRadarForRobotTurn(true);
		getReady();
		
		while(true) {
			
			turnRadarRight(Double.POSITIVE_INFINITY);
			
		}//end WHILE TRUE
		
	}//end RUN METHOD
	
	
	public void onScannedRobot(ScannedRobotEvent e) {
		
		if((e.isSentryRobot()==true)||(sentry==null)) {
			
			sentry=e.getName();
			
		}//end if is sentry
		
		else{
			
			shoot(e);
			movement();
			
		}//end if this is the target
		
	}//end ON SCANNED ROBOT
	
	
	public void movement() {
		
		if(moveCounter<2) {
	  		turnRight(20);
			ahead(150);
			moveCounter++;
	  	}
	  	else if(moveCounter<4) {
	  		turnLeft(20);
			  back(150);
			  moveCounter++;
	  	}//end else
	  	else {
	  		turnRight(20);
			ahead(150);
			moveCounter=1;
	  	}//end else (right again)
		
	}//end MOVEMENT
	
	public void strangerDanger(ScannedRobotEvent e) {
		
		if(e.getBearing()>90&&e.getBearing()<=-90) {
			turnRight(e.getBearing()-90);
			ahead(150-e.getDistance());
		}//end reverse
		else {
			turnLeft(e.getBearing()-90);
			back(150-e.getDistance());
		}//end else
		scan();
		
	}//end DISTANCE
	
	public void shoot(ScannedRobotEvent e) {
		
		scan();
		
		turnRight(e.getBearing()-90);
		//Wilde.java, Alex McPheterson (line 144, https://github.com/alexjamesmacpherson/robocode/blob/master/Wilde.java)
		double power=Math.min(3, Math.max(600/e.getDistance(), 1));
				
		//Non-Iterative Linear targeting (http://robowiki.net/wiki/Linear_Targeting#Example_of_Noniterative_Linear_Targeting)
		double absoluteBearing = Math.toRadians(getHeading()) + e.getBearingRadians();
		double bulletSpeed=20-(3*power);
		double gunTurn=(Utils.normalRelativeAngle(absoluteBearing-Math.toRadians(getGunHeading())+(e.getVelocity()*Math.sin(e.getHeadingRadians()-absoluteBearing)/bulletSpeed)));
		
		if(e.getVelocity()!=0||e.getEnergy()>1) {
			turnGunRight(gunTurn);
			fire(power);
		}//end if moving
		else {fire(0.1);}
				
	}//end SHOOT
	
	public void getReady() {
		
		setBodyColor(new Color(242, 186, 204));
		setGunColor(new Color(242, 196, 186));
		setRadarColor(new Color(249,217,224));
		setScanColor(new Color(252, 20, 82));
		setBulletColor(new Color(32,223,252));
		
		turnGunRight(90);
		sentry=null;
		moveCounter=0;
		
	}//end GET READY

}//end MAIN CLASS