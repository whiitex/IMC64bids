package IMC64bids_1;

import java.util.*;

//S13 R2 S8 R5 S9 R1 S9 R1 S8 R4
//S13 R2 S8 R5 S9 R1 S9 R1 S8 R4
//{"timestamp":0,"book":"AAPL","bids":[],"asks":[{"price":600,"volume":10}]}

//S13 R3 S8 R5 S9 R1 S8 R4 S9 R4 S9 R4 S9 R5 S9 R1
//S13 R3 S8 R5 S9 R1 S8 R4 S9 R4 S9 R4 S9 R5 S9 R1
//{"timestamp":15,"book":"TSLA","bids"[{"price":800,"volume":50},{"price":850,"volume":110}],"asks":[]}

// JSON LIKE FORMAT
                                                                                                                            /**
{
  "timestamp": 0,
  "book":"GOOG",
  "bids":[
    {
      "price":800,
      "volume":10
    }
  ],
  "asks": [
    {
      "price":800,
      "volume":10
    }
  ]
}
                                                                                                                            */

public class FastRead {
    public FastRead() {}

    String appending(char[] sr, int[] result, int i) {
        StringBuilder returning = new StringBuilder();
        for (int j=0; j<i+1; j++) {
            returning.append(sr[j]);
            returning.append(result[j]);
            returning.append(' ');
        }
        return returning.toString();
    }

    public String fastRead(double maxBuyPrice, double minSellPrice, String marketState) {
        StringBuilder ms = new StringBuilder(marketState);

        // DECLARATIONS
        int BID, ASK;
        int i =0;
        int timestampIndex=0, bookIndex=0;
        int[] result = new int[200];
        char[] sr = new char[200];

        // TIMESTAMP
        sr[i] = 'S';
        result[i] = 13;
        ms.delete(0,13);

        while (ms.charAt(0) != ',') {
            timestampIndex++;
            ms.delete(0,1);
        }
        timestampIndex++;
        ms.delete(0,1);
        sr[++i] = 'R';
        result[i] = timestampIndex;


        //BOOK
        sr[++i] = 'S';
        result[i] = 8;
        ms.delete(0,8);

        StringBuilder bookSB = new StringBuilder();
        while (ms.charAt(0) !='\"') {
            bookSB.append(ms.charAt(0));
            ms.delete(0,1);
            bookIndex++;
        }
        ms.delete(0,1);
        bookIndex++;
        String bookString = bookSB.toString();
        sr[++i] = 'R';
        result[i] = bookIndex;


        // BID
        sr[++i] = 'S';
        result[i] = 9;
        ms.delete(0,9);

        int flag=0;
        while (ms.charAt(0) != ']') {
            sr[++i] = 'R';
            result[i] = 1;
            ms.delete(0,1);

            if (flag>0) {
                sr[++i] = 'S';
                result[i] = 9;
                ms.delete(0,9);
            }
            else {
                sr[++i] = 'S';
                result[i] = 8;
                ms.delete(0,8);
            } flag++;

            Stack<Integer> temp = new Stack<>();
            int counter = 1;
            while (ms.charAt(0) != ',') {
                temp.push(Character.getNumericValue(ms.charAt(0)));
                counter++;
                ms.delete(0,1);
            }
            ms.delete(0,1);
            sr[++i] = 'R';
            result[i] = counter;
            BID = 0;
            for (int j =0; j<counter-1; j++)
                BID += temp.pop() * Math.pow(10, j);

            if (minSellPrice <= BID)
                return appending(sr, result, i);

            sr[++i] = 'S';
            result[i] = 9;
            ms.delete(0,9);

            counter = 1;
            while (ms.charAt(0) != '}') {
                counter++;
                ms.delete(0,1);
            }
            ms.delete(0,1);
            sr[++i] = 'R';
            result[i] = counter;
        }
        sr[++i] = 'R';
        result[i] = 1;
        ms.delete(0,1);


        // ASK
        sr[++i] = 'S';
        result[i] = 9;
        ms.delete(0,9);

        flag=0;
        while (ms.charAt(0) != ']') {
            sr[++i] = 'R';
            result[i] = 1;
            ms.delete(0,1);

            if (flag>0) {
                sr[++i] = 'S';
                result[i] = 9;
                ms.delete(0,9);
            }
            else {
                sr[++i] = 'S';
                result[i] = 8;
                ms.delete(0,8);
            }flag++;

            Stack<Integer> temp = new Stack<>();
            int counter = 1;
            while (ms.charAt(0) != ',') {
                temp.push(Character.getNumericValue(ms.charAt(0)));
                counter++;
                ms.delete(0,1);
            }
            ms.delete(0,1);
            sr[++i] = 'R';
            result[i] = counter;

            ASK = 0;
            for (int j =0; j<counter-1; j++)
                ASK += temp.pop() * Math.pow(10, j);

            if (maxBuyPrice>= ASK)
                return appending(sr, result, i);

            sr[++i] = 'S';
            result[i] = 9;
            ms.delete(0,9);

            counter = 1;
            while (ms.charAt(0) != '}') {
                counter++;
                ms.delete(0,1);
            }
            ms.delete(0,1);
            sr[++i] = 'R';
            result[i] = counter;
        }
        sr[++i] = 'R';
        result[i] = 1;
        ms.delete(0,1);

        // FINAL
        return appending(sr, result, i);
    }


}
