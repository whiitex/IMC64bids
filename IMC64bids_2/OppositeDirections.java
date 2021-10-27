//package directions;
package IMC64bids_2;

import java.util.*;

class Pcomparator implements Comparator<List<Integer>> {
    @Override
    public int compare(List<Integer> a, List<Integer> b) {
        return a.get(1)+a.get(3) < b.get(1)+b.get(3) ? -1 : a.get(1)+a.get(3) == b.get(1)+b.get(3) ? 0 : 1;
    } }

class Mcomparator implements Comparator<List<Integer>> {
    @Override
    public int compare(List<Integer> a, List<Integer> b) {
        return a.get(1)+a.get(3) > b.get(1)+b.get(3) ? -1 : a.get(1)+a.get(3) == b.get(1)+b.get(3) ? 0 : 1;
    } }


public class OppositeDirections {

    public List<Integer> chooseOpportunities(List<List<Integer>> opportunities) {
        LinkedList<Integer> ret = new LinkedList<>();

        // DICHIARAZIONI
        LinkedList<List<Integer>> mmmm = new LinkedList<>();
        LinkedList<List<Integer>> pppp = new LinkedList<>();

        LinkedList<List<Integer>> pmmm = new LinkedList<>();
        LinkedList<List<Integer>> mpmm = new LinkedList<>();
        LinkedList<List<Integer>> mmpm = new LinkedList<>();
        LinkedList<List<Integer>> mmmp = new LinkedList<>();

        LinkedList<List<Integer>> ppmm = new LinkedList<>();
        LinkedList<List<Integer>> pmpm = new LinkedList<>();
        LinkedList<List<Integer>> pmmp = new LinkedList<>();
        LinkedList<List<Integer>> mppm = new LinkedList<>();
        LinkedList<List<Integer>> mpmp = new LinkedList<>();
        LinkedList<List<Integer>> mmpp = new LinkedList<>();

        LinkedList<List<Integer>> pppm = new LinkedList<>();
        LinkedList<List<Integer>> ppmp = new LinkedList<>();
        LinkedList<List<Integer>> pmpp = new LinkedList<>();
        LinkedList<List<Integer>> mppp = new LinkedList<>();


        // CREAZIONE DELLE HASHMAP SEMPLIFICATIVE
        for (int i = 0; i<opportunities.size(); ++i) {
            // ++++
            if (opportunities.get(i).get(0)>=0 && opportunities.get(i).get(1)>=0 &&
                    opportunities.get(i).get(2)>=0 && opportunities.get(i).get(3)>=0)
                pppp.addLast(Arrays.asList(opportunities.get(i).get(0),opportunities.get(i).get(1),opportunities.get(i).get(2),opportunities.get(i).get(3),i));
                // ----
            else if (opportunities.get(i).get(0)<0 && opportunities.get(i).get(1)<0 &&
                    opportunities.get(i).get(2)<0 && opportunities.get(i).get(3)<0)
                mmmm.addLast(Arrays.asList(opportunities.get(i).get(0),opportunities.get(i).get(1),opportunities.get(i).get(2),opportunities.get(i).get(3),i));
                // -+-+
            else if (opportunities.get(i).get(0)<0 && opportunities.get(i).get(1)>=0 &&
                    opportunities.get(i).get(2)<0 && opportunities.get(i).get(3)>=0)
                mpmp.addLast(Arrays.asList(opportunities.get(i).get(0),opportunities.get(i).get(1),opportunities.get(i).get(2),opportunities.get(i).get(3),i));
                // +-+-
            else if (opportunities.get(i).get(0)>=0 && opportunities.get(i).get(1)<0 &&
                    opportunities.get(i).get(2)>=0 && opportunities.get(i).get(3)<0)
                pmpm.addLast(Arrays.asList(opportunities.get(i).get(0),opportunities.get(i).get(1),opportunities.get(i).get(2),opportunities.get(i).get(3),i));
                // --++
            else if (opportunities.get(i).get(0)<0 && opportunities.get(i).get(1)<0 &&
                    opportunities.get(i).get(2)>=0 && opportunities.get(i).get(3)>=0)
                mmpp.addLast(Arrays.asList(opportunities.get(i).get(0),opportunities.get(i).get(1),opportunities.get(i).get(2),opportunities.get(i).get(3),i));
                // ---+
            else if (opportunities.get(i).get(0)<0 && opportunities.get(i).get(1)<0 &&
                    opportunities.get(i).get(2)<0 && opportunities.get(i).get(3)>=0)
                mmmp.addLast(Arrays.asList(opportunities.get(i).get(0),opportunities.get(i).get(1),opportunities.get(i).get(2),opportunities.get(i).get(3),i));
                // --+-
            else if (opportunities.get(i).get(0)<0 && opportunities.get(i).get(1)<0 &&
                    opportunities.get(i).get(2)>=0 && opportunities.get(i).get(3)<0)
                mmpm.addLast(Arrays.asList(opportunities.get(i).get(0),opportunities.get(i).get(1),opportunities.get(i).get(2),opportunities.get(i).get(3),i));
                // +---
            else if (opportunities.get(i).get(0)>=0 && opportunities.get(i).get(1)<0 &&
                    opportunities.get(i).get(2)<0 && opportunities.get(i).get(3)<0)
                pmmm.addLast(Arrays.asList(opportunities.get(i).get(0),opportunities.get(i).get(1),opportunities.get(i).get(2),opportunities.get(i).get(3),i));
                // -+--
            else if (opportunities.get(i).get(0)<0 && opportunities.get(i).get(1)>=0 &&
                    opportunities.get(i).get(2)<0 && opportunities.get(i).get(3)<0)
                mpmm.addLast(Arrays.asList(opportunities.get(i).get(0),opportunities.get(i).get(1),opportunities.get(i).get(2),opportunities.get(i).get(3),i));
                // +++-
            else if (opportunities.get(i).get(0)>=0 && opportunities.get(i).get(1)>=0 &&
                    opportunities.get(i).get(2)>=0 && opportunities.get(i).get(3)<0)
                pppm.addLast(Arrays.asList(opportunities.get(i).get(0),opportunities.get(i).get(1),opportunities.get(i).get(2),opportunities.get(i).get(3),i));
                // ++-+
            else if (opportunities.get(i).get(0)>=0 && opportunities.get(i).get(1)>=0 &&
                    opportunities.get(i).get(2)<0 && opportunities.get(i).get(3)>=0)
                ppmp.addLast(Arrays.asList(opportunities.get(i).get(0),opportunities.get(i).get(1),opportunities.get(i).get(2),opportunities.get(i).get(3),i));
                // +-++
            else if (opportunities.get(i).get(0)>=0 && opportunities.get(i).get(1)<0 &&
                    opportunities.get(i).get(2)>=0 && opportunities.get(i).get(3)>=0)
                pmpp.addLast(Arrays.asList(opportunities.get(i).get(0),opportunities.get(i).get(1),opportunities.get(i).get(2),opportunities.get(i).get(3),i));
                // -+++
            else if (opportunities.get(i).get(0)<0 && opportunities.get(i).get(1)>=0 &&
                    opportunities.get(i).get(2)>=0 && opportunities.get(i).get(3)>=0)
                mppp.addLast(Arrays.asList(opportunities.get(i).get(0),opportunities.get(i).get(1),opportunities.get(i).get(2),opportunities.get(i).get(3),i));
                // ++--
            else if (opportunities.get(i).get(0)>=0 && opportunities.get(i).get(1)>=0 &&
                    opportunities.get(i).get(2)<0 && opportunities.get(i).get(3)<0)
                ppmm.addLast(Arrays.asList(opportunities.get(i).get(0),opportunities.get(i).get(1),opportunities.get(i).get(2),opportunities.get(i).get(3),i));
                // +--+
            else if (opportunities.get(i).get(0)>=0 && opportunities.get(i).get(1)<0 &&
                    opportunities.get(i).get(2)<0 && opportunities.get(i).get(3)>=0)
                pmmp.addLast(Arrays.asList(opportunities.get(i).get(0),opportunities.get(i).get(1),opportunities.get(i).get(2),opportunities.get(i).get(3),i));
                // -++-
            else if (opportunities.get(i).get(0)<0 && opportunities.get(i).get(1)>=0 &&
                    opportunities.get(i).get(2)>=0 && opportunities.get(i).get(3)<0)
                mppm.addLast(Arrays.asList(opportunities.get(i).get(0),opportunities.get(i).get(1),opportunities.get(i).get(2),opportunities.get(i).get(3),i));
        }


        //---- -1 -1
        if (mmmm.size()>0) {
            Collections.sort(mmmm,new Mcomparator());
            for (List<Integer> l: mmmm)
                ret.add(l.get(4)+1); }

        //--+- -2  0
        if (mmpm.size()>0) {
            Collections.sort(mmpm,new Mcomparator());
            for (List<Integer> l: mmpm)
                ret.add(l.get(4)+1); }

        //--++ -3  1
        if (mmpp.size()>0) {
            Collections.sort(mmpp,new Mcomparator());
            for (List<Integer> l: mmpp)
                ret.add(l.get(4)+1); }

        //---+ -4  0
        if (mmmp.size()>0) {
            Collections.sort(mmmp,new Mcomparator());
            for (List<Integer> l: mmmp)
                ret.add(l.get(4)+1); }
        //+--- -3 -1
        if (pmmm.size()>0) {
            Collections.sort(pmmm,new Pcomparator());
            for (List<Integer> l: pmmm)
                ret.add(l.get(4)+1); }
        //+-+- -2  0
        if (pmpm.size()>0) {
            Collections.sort(pmpm,new Pcomparator());
            for (List<Integer> l: pmpm)
                ret.add(l.get(4)+1); }

        //+-++ -1  1
        if (pmpp.size()>0) {
            Collections.sort(pmpp,new Pcomparator());
            for (List<Integer> l: pmpp)
                ret.add(l.get(4)+1); }
        //+--+  0  0
        if (pmmp.size()>0) {
            Collections.sort(pmmp,new Pcomparator());
            for (List<Integer> l: pmmp)
                ret.add(l.get(4)+1); }


        //++--  1 -1
        if (ppmm.size()>0) {
            Collections.sort(ppmm,new Pcomparator());
            for (List<Integer> l: ppmm)
                ret.add(l.get(4)+1); }

        //+++-  2  0
        if (pppm.size()>0) {
            Collections.sort(pppm,new Pcomparator());
            for (List<Integer> l: pppm)
                ret.add(l.get(4)+1); }

        //++++  3  1
        if (pppp.size()>0) {
            Collections.sort(pppp,new Pcomparator());
            for (List<Integer> l: pppp)
                ret.add(l.get(4)+1); }

        //++-+  4  0
        if (ppmp.size()>0) {
            Collections.sort(ppmp,new Pcomparator());
            for (List<Integer> l: ppmp)
                ret.add(l.get(4)+1); }
        //-+++  1  1
        if (mppp.size()>0) {
            Collections.sort(mppp,new Mcomparator());
            for (List<Integer> l: mppp)
                ret.add(l.get(4)+1); }
        //-+-+  0  0
        if (mpmp.size()>0) {
            Collections.sort(mpmp,new Mcomparator());
            for (List<Integer> l: mpmp)
                ret.add(l.get(4)+1); }
        //-+--  3 -1
        if (mpmm.size()>0) {
            Collections.sort(mpmm,new Mcomparator());
            for (List<Integer> l: mpmm)
                ret.add(l.get(4)+1); }
        //-++-  2  0
        if (mppm.size()>0) {
            Collections.sort(mppm,new Mcomparator());
            for (List<Integer> l: mppm)
                ret.add(l.get(4)+1); }


        // RETURN
        return ret;
    }
}
