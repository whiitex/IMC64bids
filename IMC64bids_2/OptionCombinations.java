package IMC64bids_2;

import java.math.*;

public class OptionCombinations {
    public String countCombinations(int strikes, int expiries) {
        int contr = 2 * strikes * expiries;
        BigInteger due = new BigInteger("2");
        BigInteger def = due.pow(contr);
        BigInteger sub = new BigInteger(String.valueOf(contr + 1));
        BigInteger ret = def.subtract(sub);
        return String.valueOf(ret);
    }
}
