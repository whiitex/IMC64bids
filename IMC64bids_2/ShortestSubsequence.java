package IMC64bids_2;

import java.util.*;

public class ShortestSubsequence {

    public int binomioDiNewton(int n, int k) {
        double ret=1; int o=n-k;
        for (int i=0; i<49; i++) {
            if(n>k) ret *= n--;
            if(o>1) ret /= o--;
        }
        return (int)ret;
    }

    public boolean occurrence(String sigla, String parola) {
        int len = sigla.length(), count =0;
        for (int i=0; i<parola.length() && count<len; ++i)
            if (sigla.charAt(count)==parola.charAt(i))
                ++count;
        return count==len;
    }


    public List<String> findShortVersions(List<String> names) {
        ArrayList<String> ret = new ArrayList<>();

        for (String name: names) {
            int casi = (int) Math.pow(2,name.length());

            for (int k = 2; k<=name.length(); ++k) {  // PER OGNI LUNGHEZZA DELLA SIGLA (dalla più piccola)
                if (k == name.length()) ret.add(name);
                else {

                    int disposizioni = binomioDiNewton(name.length(),k);
                    int fineDisp = 0;

                    for (int i = (int) Math.pow(2,k)-1; i<casi && fineDisp<disposizioni; ++i) {
                        StringBuilder ssigla = new StringBuilder();
                        StringBuilder ref = new StringBuilder(Integer.toBinaryString(i));
                        String reference = ref.reverse().toString();

                        for (int j = 0; j<reference.length(); ++j)
                            if (reference.charAt(j) == '1')
                                ssigla.append(name.charAt(j));
                        boolean checkLen = ssigla.length() == k;

                        if (checkLen) {
                            fineDisp++;
                            String sigla = ssigla.toString();
                            int contatore = 0;
                            for (String comp : names) {
                                if (!comp.equals(name)) {
                                    if (!occurrence(sigla,comp))
                                        ++contatore;
                                    else break;
                                }
                            }
                            if (contatore == names.size()-1) {
                                ret.add(sigla);
                                fineDisp = disposizioni+1;
                                k = name.length();
                            }
                        }
                    }
                }
            }
        }
        return ret;
    }

}


/** 60% *//*
package subsequence;

import java.util.*;

public class ShortestSubsequence {

    public int binomioDiNewton(int n, int k) {
        int ret=1, o=n-k, m=1;
        for (int i=0; i<49; i++) {
            while(n>k) ret *= n--;
            while(o>1) m *= o--;

        }
        return (int)(ret/m);
    }

    public boolean occurrence(String sigla, String parola) {
        int len = sigla.length(), count =0;
        for (int i=0; i<parola.length() && count<len; ++i)
            if (sigla.charAt(count)==parola.charAt(i))
                ++count;
        return count==len;
    }


    public List<String> findShortVersions(List<String> names) {
        ArrayList<String> ret = new ArrayList<>();

        for (String name: names) {
            int casi = (int) Math.pow(2,name.length());

            for (int k = 1; k<=name.length(); ++k) {  // PER OGNI LUNGHEZZA DELLA SIGLA (dalla più piccola)
                if (k == name.length()) ret.add(name);
                else {
                int disposizioni= binomioDiNewton(name.length(),k);
                int fineDisp = 0;

                for (int i = (int)Math.pow(2,k)-1; i<casi && fineDisp<disposizioni; ++i) {
                    StringBuilder ssigla = new StringBuilder();
                    StringBuilder ref = new StringBuilder(Integer.toBinaryString(i));
                    String reference = ref.reverse().toString();

                    for (int j = 0; j<reference.length(); ++j)
                        if (reference.charAt(j) == '1')
                            ssigla.append(name.charAt(j));
                    boolean checkLen = ssigla.length()==k;

                    if (checkLen) {
                        fineDisp++;
                        String sigla = ssigla.toString();
                        int contatore = 0;
                        for (String comp: names) {
                            if (!comp.equals(name)) {
                                if (!occurrence(sigla,comp))
                                    ++contatore;
                                else break;
                            }
                        }
                        if (contatore == names.size()-1) {
                            ret.add(sigla);
                            fineDisp = disposizioni+1;
                            k=name.length();
                        }
                    }
                }
                }
            }
        }
        return ret;
    }

}
*/
