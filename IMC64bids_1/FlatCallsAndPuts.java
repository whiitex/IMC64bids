package IMC64bids_1;

import java.util.*;

public class FlatCallsAndPuts {
    public FlatCallsAndPuts() {}

    public void combination(HashMap<Integer,Integer> mappa, List<Integer> volumes, int total) {
        for (int i=0; i<total; i++) {
            String binary = Integer.toBinaryString(i);
            int sum = 0;
            for (int j=binary.length()-1; j>-1; j--)
                if (binary.charAt(j) =='1')
                    sum += volumes.get(binary.length()-j-1);
            mappa.put(i, sum);
        }
    }

    public int max(int[] vet) {
        if (vet.length >0) {
            int max = vet[0];
            for (int j : vet)
                if (j>max)
                    max = j;
            return max;
        } return 0;
    }


    public int calculateProfit(List<Integer> callStrikePrices, List<Integer> callVolumes, List<Integer> putStrikePrices, List<Integer> putVolumes) {
        int sizeCall = (int) Math.pow(2,callStrikePrices.size());
        int sizePut = (int) Math.pow(2,putStrikePrices.size());
        HashMap<Integer, Integer> callCombination = new HashMap<>();
        HashMap<Integer, Integer> putCombination = new HashMap<>();
        combination(callCombination,callVolumes,sizeCall);
        combination(putCombination,putVolumes,sizePut);
        int[][] elenco = new int[1024 * 1024][2]; /** [contatore][0=i=CALL ; 1=j=PUT]  */

        int contatore = 0;
        for (int i = 0; i<sizeCall; i++)
            for (int j = 0; j<sizePut; j++)
                if (callCombination.get(i).equals(putCombination.get(j))) {
                    elenco[contatore][0] = i;
                    elenco[contatore][1] = j;
                    contatore++;
                }

        if (contatore>0) {
            int[] profit = new int[++contatore];
            for (int i = 0; i<contatore; i++) {
                // CALL
                int callCash = 0;
                String call = Integer.toBinaryString(elenco[i][0]);
                for (int j = call.length()-1; j>-1; j--)
                    if (call.charAt(j) == '1')
                        callCash += callStrikePrices.get(call.length()-j-1) * callVolumes.get(call.length()-j-1);
                // PUT
                int putCash = 0;
                String put = Integer.toBinaryString(elenco[i][1]);
                for (int j = put.length()-1; j>-1; j--)
                    if (put.charAt(j) == '1')
                        putCash += putStrikePrices.get(put.length()-j-1) * putVolumes.get(put.length()-j-1);
                profit[i] = putCash - callCash;
            }

            int max = max(profit);
            if (max>0) return max;
        }
        return 0;
    }
}


/*
    public static void main(String[] args) {

        ArrayList<Integer> callStrikes = new ArrayList<Integer>();
        ArrayList<Integer> callVolumes = new ArrayList<Integer>();
        ArrayList<Integer> putStrikes = new ArrayList<Integer>();
        ArrayList<Integer> putVolumes = new ArrayList<Integer>();

        callStrikes.add(4); callStrikes.add(10); //callStrikes.add(7);
        callVolumes.add(6); callVolumes.add(4); //callVolumes.add(2);
        putStrikes.add(8); putStrikes.add(5);
        putVolumes.add(5); putVolumes.add(5);

        int profit = calculateProfit(callStrikes, callVolumes, putStrikes, putVolumes);
        System.out.println(profit);
    }
*/

                                            /*
callStrikes = [ 3, 9, 7 ]
callVolumes = [ 1, 3, 2 ]
putStrikes = [ 10, 10 ]
putVolumes = [ 2, 2 ]

callStrikes.add(4); callStrikes.add(10);
callVolumes.add(6); callVolumes.add(4);
putStrikes.add(8); putStrikes.add(5);
putVolumes.add(5); putVolumes.add(5);
                                            */
