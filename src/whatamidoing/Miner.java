package whatamidoing;

import battlecode.common.*;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Miner extends RobotPlayer {


    static RobotInfo hq;
    static MapLocation hqloc;
    static int designsBuilt;

    static void initializeMiner() throws GameActionException {
        RobotInfo hq = rc.senseNearbyRobots(3, rc.getTeam())[0];
        MapLocation hqloc = hq.getLocation();
        designsBuilt = 0;
    }
    static void runMiner() throws GameActionException {
        MapLocation here = rc.getLocation();

        if(rc.getTeamSoup() > 400 && designsBuilt == 0){ // each miner builds at most 1 design schools
            for (Direction d : directions) {
                if (rc.canBuildRobot(RobotType.DESIGN_SCHOOL, d)) {
                    rc.buildRobot(RobotType.DESIGN_SCHOOL, d);
                    break;
                }
            }
        }
        if (rc.getSoupCarrying() > 0) {
            if (nearestRefinery() != null) { //stores soup to refinery if nearby refinery
                Direction moveDir = here.directionTo(nearestRefinery());
                if (here.isAdjacentTo(nearestRefinery())) {
                    if (rc.canDepositSoup(moveDir)) {
                        rc.depositSoup(moveDir, rc.getSoupCarrying());
                    }
                }
                tryMove(moveDir);
            } else if (rc.getTeamSoup() > 200) { //builds refinery if no nearby refinery
                for (Direction d : directions) {
                    if (rc.canBuildRobot(RobotType.REFINERY, d)) {
                        rc.buildRobot(RobotType.REFINERY, d);
                        break;
                    }
                }
            }
        } else {


            Direction moveDir = here.directionTo(nearestSoup());
               if (rc.canMineSoup(moveDir)){
                    rc.mineSoup(moveDir);
                }



            if (rc.canMove(moveDir)) {
                rc.move(moveDir);
            }

        }
    }
 //TODO Debug why miner don't move at first few rounds
    static MapLocation nearestRefinery() throws GameActionException {
        ArrayList<MapLocation> locs = getMapLocationsInRadius(rc.getType());
        for (MapLocation x : locs){
            RobotInfo atx = rc.senseRobotAtLocation(x);
            if (atx != null && atx.getTeam() == rc.getTeam() && atx.getType() == RobotType.REFINERY){
                return x;
            }
        }
        return null;
    }

    static MapLocation optRefineryLocation(int[][] soupLocation, MapLocation baseLocation, int numMiners) {
        // Computes optimal place of refinery given the location of soup, base and estimated number of miners will be used
        int[] baseW = {baseLocation.x, baseLocation.y, 5 * numMiners};
        int numSoup = soupLocation.length;
        int[][] sd = new int[numSoup + 1][3];

        for (int i = 0; i < numSoup; i++) {
            sd[i][0] = soupLocation[i][0] + soupLocation[i][1];
            sd[i][1] = soupLocation[i][0] - soupLocation[i][1];
            sd[i][2] = soupLocation[i][2];
        }
        sd[numSoup][0] = baseW[0] + baseW[1];
        sd[numSoup][1] = baseW[0] - baseW[1];
        sd[numSoup][2] = baseW[2];

        int[] opt = new int[2];
        for (int i : new int[]{0, 1}){
            Arrays.sort(sd, new Comparator<int[]>() {
                @Override
                public int compare(int[] a1, int[] a2) {
                    return a1[i] - a2[i];
                }
            });
            int tot = 0;
            for (int[] x : sd){
                tot += x[2];
            }
            int ltot = 0;
            for (int j = 0; j< sd.length;j++) {
                ltot += sd[j][2];
                if (ltot * 2 >= tot){
                    opt[i] = sd[j][i];
                    break;
                }
            }
        }
        return new MapLocation((int)(Math.round(0.5 * (opt[0] + opt[1]))), (int)(Math.round(0.5 * (opt[0] - opt[1]))));
    }

}