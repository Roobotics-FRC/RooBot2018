package org.usfirst.frc.team4373.robot.subsystems;

import edu.wpi.first.wpilibj.CameraServer;

/**
 * Created by thefangbear on 1/21/18.
 */
public class RooCamera {

    private static final String CAM_ADDR = "";
    private static RooCamera rooCamera = null;
    private CameraServer server;

    private RooCamera() {
        this.server = CameraServer.getInstance();
        this.server.addAxisCamera(CAM_ADDR);
        this.server.startAutomaticCapture();
    }

    public CameraServer getCameraServer() {
        return this.server;
    }

    public static RooCamera getRooCamera() {
        synchronized (RooCamera.class) {
            if (rooCamera == null) {
                rooCamera = new RooCamera();
            }
            return rooCamera;
        }
    }

}
