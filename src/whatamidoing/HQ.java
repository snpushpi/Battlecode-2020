package whatamidoing;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotType;

public class HQ extends RobotPlayer {

    static void runHQ() throws GameActionException {
        if (turnCount == 1) {
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
