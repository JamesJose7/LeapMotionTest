import java.io.IOException;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;

class LeapListener extends Listener {
	
	//Controller initialized
	public void onInit(Controller controller) {
		System.out.println("Initialized");
	}
	
	//Controller connected
	public void onConnect(Controller controller) {
		System.out.println("Connected to motion Sensor");
		
		//Declare Gestures
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
	}
	
	//Controller disconnect
	public void onDisconnect(Controller controller) {
		System.out.println("Motion sensor disconnected");
	}
	
	//Controller exit
	public void onExit(Controller controller) {
		System.out.println("Exited");
	}
	
	//Frame method
	public void onFrame(Controller controller) {
		
		Frame frame = controller.frame();
		
		//Frame Data
		/*System.out.println("Frame id: " + frame.id()
							+ ", Timestamp: " + frame.timestamp()
							+ ", Hands: " + frame.hands().count()
							+ ", Fingers: " + frame.fingers().count()
							+ ", Tools: " + frame.tools().count()
							+ ", Gestures: " + frame.gestures().count());
		
		for (Hand hand : frame.hands()) {
			String handType = hand.isLeft() ? "Left Hand" : "Right Hand";
			System.out.println(handType + " " + ", id: " + hand.id()
								+ ", Palm Position: " + hand.palmPosition());
			
			Vector normal = hand.palmNormal();
			Vector direction = hand.direction();
			
			System.out.println("Pitch: " + Math.toDegrees(direction.pitch())
								+ "Roll: " + Math.toDegrees(normal.roll())
								+ " Yaw: " + Math.toDegrees(direction.yaw()));
				
		}
		
		for (Finger finger : frame.fingers()) {
			System.out.println("//Finger Type: " + finger.type()
								+ "  ID: " + finger.id()
								+ "  Finger length (mm): " + finger.length()
								+ "  Finger width (mm): " + finger.width());
		
		
			for (Bone.Type boneType : Bone.Type.values()) {
				Bone bone = finger.bone(boneType);
				System.out.println("Bone Type: " + bone.type()
									+ " Start: " + bone.prevJoint()
									+ " End: " + bone.nextJoint()
									+ " Direction: " + bone.direction());
				
			}
			
		}*/
		
		for (Tool tool : frame.tools()) {
			System.out.println("Tool ID: " + tool.id()
								+ " Tip position: " + tool.tipPosition()
								+ " Direction: " + tool.direction()
								+ " Width: " + tool.width()
								+ " Touch Distance (mm) " + tool.touchDistance());
		}
	}
}

public class LeapController {

	public static void main(String[] args) {
		LeapListener listener = new LeapListener();
		Controller controller = new Controller();
		
		controller.addListener(listener);
		
		System.out.println("Press enter to quit");
		
		
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		controller.removeListener(listener);
		
	}

}
