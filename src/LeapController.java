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
			
		}
		
		for (Tool tool : frame.tools()) {
			System.out.println("Tool ID: " + tool.id()
								+ " Tip position: " + tool.tipPosition()
								+ " Direction: " + tool.direction()
								+ " Width: " + tool.width()
								+ " Touch Distance (mm) " + tool.touchDistance());
		}*/
		
		GestureList gestures = frame.gestures();
		for (int i = 0; i < gestures.count(); i++) {
			Gesture gesture = gestures.get(i);
			
			switch (gesture.type()) {
				case TYPE_CIRCLE:
					CircleGesture circle = new CircleGesture(gesture);
					
					String clockwise;
					if (circle.pointable().direction().angleTo(circle.normal()) <= Math.PI/4) {
						clockwise = "clockwise";
					} else {
						clockwise = " counter-clockwise";
					}
					
					double sweptAngle = 0;
					if (circle.state() != State.STATE_START) {
						CircleGesture previous = new CircleGesture(controller.frame(1).gesture(circle.id()));
						sweptAngle = (circle.progress() - previous.progress()) * 2 * Math.PI;
					}
					
					System.out.println("Circle ID: " + circle.id()
										+ "  State: " + circle.state()
										+ "  Progress: " + circle.progress()
										+ "  Radius: " + circle.radius()
										+ "  Angle: " + Math.toDegrees(sweptAngle)
										+ " " + clockwise);
					break;
					
				case TYPE_SWIPE:
					SwipeGesture swipe = new SwipeGesture(gesture);
					System.out.println("Swipe ID: " + swipe.id()
									+ "  State: " + swipe.state()
									+ "  Swipe position: " + swipe.position()
									+ "  Direction: " + swipe.direction()
									+ "  Speed: " + swipe.speed());
					break;
					
				case TYPE_SCREEN_TAP:
					ScreenTapGesture screenTap = new ScreenTapGesture(gesture);
					System.out.println("ScreenTap ID: " + screenTap.id()
										+ " State: " + screenTap.state()
										+ " Position: " + screenTap.position()
										+ " Direction: " + screenTap.direction());
					
					
					
			}
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
