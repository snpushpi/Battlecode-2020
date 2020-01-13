package whatamidoing;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import java.lang.Math;
import java.util.Arrays;
import java.util.Comparator;

public class Miner extends RobotPlayer {


    static void runMiner() throws GameActionException {
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