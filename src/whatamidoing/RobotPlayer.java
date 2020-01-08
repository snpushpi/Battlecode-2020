package whatamidoing;

import battlecode.common.*;

import java.util.ArrayList;
import java.util.Comparator;

public strictfp class RobotPlayer {
    static RobotController rc;

    static Direction[] directions = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
    static RobotType[] spawnedByMiner = {RobotType.REFINERY, RobotType.VAPORATOR, RobotType.DESIGN_SCHOOL,
            RobotType.FULFILLMENT_CENTER, RobotType.NET_GUN};

    static int turnCount;

    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * If this method returns, the robot dies!
     **/
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {

        // This is the RobotController object. You use it to perform actions from this robot,
        // and to get information on its current status.
        RobotPlayer.rc = rc;

        turnCount = 0;

        System.out.println("I'm a " + rc.getType() + " and I just got created!");
        while (true) {
            turnCount += 1;
            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {
                // Here, we've separated the controls into a different method for each RobotType.
                // You can add the missing ones or rewrite this into your own control structure.
                System.out.println("I'm a " + rc.getType() + "! Location " + rc.getLocation());
                switch (rc.getType()) {
                    case HQ:
                        HQ.runHQ();
                        break;
                    case MINER:
                        Miner.runMiner();
                        break;
                    case REFINERY:
                        Refinery.runRefinery();
                        break;
                    case VAPORATOR:
                        Vaporator.runVaporator();
                        break;
                    case DESIGN_SCHOOL:
                        DesignSchool.runDesignSchool();
                        break;
                    case FULFILLMENT_CENTER:
                        FullfillmentCenter.runFulfillmentCenter();
                        break;
                    case LANDSCAPER:
                        Landscaper.runLandscaper();
                        break;
                    case DELIVERY_DRONE:
                        DeliveryDrone.runDeliveryDrone();
                        break;
                    case NET_GUN:
                        NetGun.runNetGun();
                        break;
                }

                // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
                Clock.yield();

            } catch (Exception e) {
                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();
            }
        }
    }


    //TODO: can be more efficient (no
    static ArrayList<MapLocation> getMapLocationsInRadius(RobotType robotType, RobotController rc) {
        ArrayList<MapLocation> locations = new ArrayList<MapLocation>();

        int max_dist = (int) Math.sqrt(robotType.HQ.sensorRadiusSquared);

        for (int x = -max_dist; x <= max_dist; ++x) {
            for (int y = -max_dist; y <= max_dist; ++y) {
                MapLocation ml = rc.getLocation().translate(x, y);

                if (rc.canSenseLocation(ml)) {
                    locations.add(ml);
                }
            }
        }

        locations.sort(Comparator.comparingInt((MapLocation m) -> rc.getLocation().distanceSquaredTo(m)));

        return locations;
    }


}
