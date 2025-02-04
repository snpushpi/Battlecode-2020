package whatamidoing;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotType;

import java.util.ArrayList;

public class HQ extends RobotPlayer {

    static int numMinersCreated;
    static int numLandscapersCreated;
    static int numDronesCreated;

    static void initializeHQ() throws GameActionException{
        numMinersCreated = 0;
        numLandscapersCreated = 0;
        numDronesCreated = 0;
    }

    static void runHQ() throws GameActionException {
        if (turnCount == 1 || (rc.getTeamSoup() > 300 && numMinersCreated < 4)) { // Spawn Miners by Default if not doing anything else
            ArrayList<MapLocation> locations = getMapLocationsInRadius(RobotType.HQ);


            //TODO: check if it is possible to build north
            //TODO: build in best possible direction

            Direction buildDir = rc.getLocation().directionTo(nearestSoup());

            // prioritizing building towards soup
            if (rc.canBuildRobot(RobotType.MINER, buildDir)) {
                rc.buildRobot(RobotType.MINER, buildDir);
                numMinersCreated++;
            }
            for (Direction d : directions) {
                if (rc.canBuildRobot(RobotType.MINER, d)) {
                    rc.buildRobot(RobotType.MINER, d);
                    numMinersCreated++;
                }
            }

        }
        //TODO
    }
}
