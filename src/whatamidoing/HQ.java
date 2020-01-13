package whatamidoing;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotType;

import java.util.ArrayList;

public class HQ extends RobotPlayer {


    static void runHQ() throws GameActionException {
        if (turnCount == 1 || rc.getTeamSoup() > 500) { // Spawn Miners by Default if not doing anything else
            ArrayList<MapLocation> locations = getMapLocationsInRadius(RobotType.HQ);



            //TODO: check if it is possible to build north
            //TODO: build in best possible direction

            for (Direction d : directions) {
                if (rc.canBuildRobot(RobotType.MINER, d)) {
                    rc.buildRobot(RobotType.MINER, d);
                }
            }

        }
        //TODO
    }
}
