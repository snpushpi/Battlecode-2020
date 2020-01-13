package whatamidoing;

import battlecode.common.GameActionException;
import battlecode.common.RobotType;

public class DesignSchool extends RobotPlayer {

    static void runDesignSchool() throws GameActionException {
        if(rc.getTeamSoup() > 350){
            mustBuild(RobotType.LANDSCAPER);
        }
    }

}
