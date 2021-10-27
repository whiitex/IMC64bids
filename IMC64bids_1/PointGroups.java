package IMC64bids_1;
import java.util.*;

// ENUM CRESCENTE DECRESCENTE IN SENSO ANTI-ORARIO
enum Mirror {
    CRESCENTE, DECRESCENTE, DIAGONALE
}

// CLASSE PUNTO
class Point{
    public int x, y;
    public Point(int a, int b) {
        this.x = a;
        this.y = b;
    }
    public Point(int a) {
        this(a-(a/1000)*1000,a/1000);
    }

    public boolean uguale(Point a) {
        return this.x==a.x && this.y==a.y;
    }

}

// CLASSE GRUPPI DI PUNTI
public class PointGroups {
    public PointGroups() {}

    public int getx(int a) { return a-(a/1000)*1000; }
    public int gety(int a) { return a/1000; }

    public Point[] ordinaOrizz(Point i, Point j, Point k) {
        Point[] ret = new Point[3];
        if (i.x<=j.x && i.x<=k.x) {
            ret[0] = i;
            if (j.x<=k.x) {
                ret[1] = j; ret[2] = k;
            } else {
                ret[1] = k; ret[2] = j;
            }
        } else if (j.x<=i.x && j.x<=k.x) {
            ret[0] = j;
            if (i.x<=k.x) {
                ret[1] = i; ret[2] = k;
            } else {
                ret[1] = k; ret[2] = i;
            }
        } else {
            ret[0] = k;
            if (j.x<=i.x) {
                ret[1] = j; ret[2] = i;
            } else {
                ret[1] = i; ret[2] = j;
            }
        } return ret;
    }

    /** NON CI SONO PUNTI COINCIDENTI NEI SET DELLA TRACCIA...*/
    public int[] puntiCoincidenti(List<Integer> returning, Point[] listaPunti1, Point[] listaPunti2, int[] lista1, int[] lista2, int l1, int l2, int i, int j, int group, int asdr) {
        int segnalatore, cont, flag;

        while (i<l1-1) {
            segnalatore = 0; cont = 0; flag=0;
            if (lista1[i] == lista1[i+1]) {
                while (i<l1-1 && lista1[i] == lista1[++i])
                    segnalatore++;
                while (cont != segnalatore && j<l2-1 && flag==0) {
                    if (lista2[j] == lista2[j+1]) {
                        flag++;
                        while (j<l2-1 && lista2[j] == lista2[++j])
                            cont++;
                    } else j++;
                }
                if (segnalatore>0 && cont >0) {
                    if (segnalatore > cont)
                        segnalatore = cont;

                    segnalatore++;
                    group++;
                    Point pLista1 = new Point(lista1[i-1]);
                    int[] numero1 = {0,0,0,0,0,0,0,0,0,0,0,0}; int contatore = 0;
                    while (contatore < segnalatore) {
                        if (pLista1.uguale(listaPunti1[numero1[contatore]])) {
                            returning.set(numero1[contatore],group);
                            contatore++;
                            numero1[contatore] = numero1[contatore-1];
                        } numero1[contatore]++;
                    }

                    Point pLista2 = new Point(lista2[j-1]);
                    int[] numero2 = {0,0,0,0,0,0,0,0,0,0,0,0}; contatore = 0;
                    while (contatore != segnalatore) {
                        if (pLista2.uguale(listaPunti2[numero2[contatore]])) {
                            returning.set(l1+numero2[contatore],group);
                            contatore++;
                            numero2[contatore] = numero2[contatore-1];
                        } numero2[contatore]++;
                    }
                    do {asdr++;} while (asdr==numero1[0] || asdr==numero1[1] || asdr==numero2[0] || asdr==numero2[1] );
                    returning.set(asdr, group);
                    returning.set(asdr+l1, group);
                    return new int[] {i, j, group, asdr};
                }
            } else i++;
        }
        return new int[] {i, j, group, asdr};
    }

    public double distance(Point a, Point b) {
        return Math.pow(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2) ,0.5);
    }
    public double easyDistance(Point a, Point b) {
        return Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2);
    }

    public boolean similarity(Point i, Point j, Point k, Point a, Point b, Point c) {
        if (i.uguale(j) || i.uguale(k) || j.uguale(k))
            if (a.uguale(b) || a.uguale(c) || b.uguale(c))
                return true;

        double ab = distance(a,b);
        double ac = distance(a,c);
        double bc = distance(b,c);
        double[] abc = {ab, ac, bc};
        Arrays.sort(abc);

        double ij = distance(i,j);
        double ik = distance(i,k);
        double jk = distance(j,k);
        double[] ijk = {ij, ik, jk};
        Arrays.sort(ijk);

        double comp = abc[0] / ijk[0];
        return (abc[1] / ijk[1] == comp) && (abc[2] / ijk[2] == comp);
    }

    public boolean antiMirror(Point i, Point j, Point k, Point a, Point b, Point c) {
        Mirror firstCondition, secondCondition;
        Point[] ijk = ordinaOrizz(i,j,k);
        Point[] abc = ordinaOrizz(a,b,c);

        if (ijk[0].y==ijk[1].y && ijk[0].y==ijk[2].y && abc[0].y==abc[1].y && abc[0].y==abc[2].y)
            return true;
        else if (ijk[0].x==ijk[1].x && ijk[0].x==ijk[2].x && abc[0].x==abc[1].x && abc[0].x==abc[2].x)
            return true;

        // POINT I-J-K
        int ix = ijk[0].x, iy = ijk[0].y, kx = ijk[2].x, ky = ijk[2].y;
        ix -= ijk[1].x;
        iy -= ijk[1].y;
        kx -= ijk[1].x;
        ky -= ijk[1].y;
        double lato1 = easyDistance(ijk[0], ijk[1]);
        double lato2 = easyDistance(ijk[1], ijk[2]);
        double lato3 = easyDistance(ijk[2], ijk[0]);
        boolean b1 = lato1<=lato2 && lato2<=lato3 || lato2<=lato3 && lato3<=lato1 || lato3<=lato1 && lato1<=lato2;
        boolean b2 = lato1<=lato3 && lato3<=lato2 || lato3<=lato2 && lato2<=lato1 || lato2<=lato1 && lato1<=lato3;

        if (iy>=0 && ky>=0) {
            if (b1)
                firstCondition = Mirror.CRESCENTE;
            else firstCondition = Mirror.DECRESCENTE;
        }
        else if (iy<0 && ky<0){
            if (b2)
                firstCondition = Mirror.CRESCENTE;
            else firstCondition = Mirror.DECRESCENTE;
        }
        else if (iy>=0) {
            if (Math.abs(iy*kx) > Math.abs(ky*ix)) {
                if (b1)
                    firstCondition = Mirror.CRESCENTE;
                else firstCondition = Mirror.DECRESCENTE;
            } else if (Math.abs(iy*kx) < Math.abs(ky*ix)){
                if (b2)
                    firstCondition = Mirror.CRESCENTE;
                else firstCondition = Mirror.DECRESCENTE;
            } else firstCondition = Mirror.DIAGONALE;
        }
        else {
            if (Math.abs(iy*kx) < Math.abs(ky*ix)) {
                if (b1)
                    firstCondition = Mirror.CRESCENTE;
                else firstCondition = Mirror.DECRESCENTE;
            } else if (Math.abs(iy*kx) > Math.abs(ky*ix)){
                if (b2)
                    firstCondition = Mirror.CRESCENTE;
                else firstCondition = Mirror.DECRESCENTE;
            } else firstCondition = Mirror.DIAGONALE;
        }

        // POINT A-B-C
        int ax = abc[0].x, ay = abc[0].y, cx = abc[2].x, cy = abc[2].y;
        ax -= abc[1].x;
        ay -= abc[1].y;
        cx -= abc[1].x;
        cy -= abc[1].y;
        lato1 = easyDistance(abc[0], abc[1]);
        lato2 = easyDistance(abc[1], abc[2]);
        lato3 = easyDistance(abc[2], abc[0]);
        b1 = lato1<=lato2 && lato2<=lato3 || lato2<=lato3 && lato3<=lato1 || lato3<=lato1 && lato1<=lato2;
        b2 = lato1<=lato3 && lato3<=lato2 || lato3<=lato2 && lato2<=lato1 || lato2<=lato1 && lato1<=lato3;

        if (ay>=0 && cy>=0) {
            if (b1)
                secondCondition = Mirror.CRESCENTE;
            else secondCondition = Mirror.DECRESCENTE;
        }
        else if (ay<0 && cy<0){
            if (b2)
                secondCondition = Mirror.CRESCENTE;
            else secondCondition = Mirror.DECRESCENTE;
        }
        else if (ay>=0) {
            if (Math.abs(ay*cx) > Math.abs(cy*ax)) {
                if (b1)
                    secondCondition = Mirror.CRESCENTE;
                else secondCondition = Mirror.DECRESCENTE;
            } else if (Math.abs(ay*cx) < Math.abs(cy*ax)){
                if (b2)
                    secondCondition = Mirror.CRESCENTE;
                else secondCondition = Mirror.DECRESCENTE;
            } else secondCondition = Mirror.DIAGONALE;
        }
        else {
            if (Math.abs(ay*cx) < Math.abs(cy*ax)) {
                if (b1)
                    secondCondition = Mirror.CRESCENTE;
                else secondCondition = Mirror.DECRESCENTE;
            } else if (Math.abs(ay*cx) > Math.abs(cy*ax)){
                if (b2)
                    secondCondition = Mirror.CRESCENTE;
                else secondCondition = Mirror.DECRESCENTE;
            } else secondCondition = Mirror.DIAGONALE;
        }

        // FINAL COMPARE
        return firstCondition == secondCondition;
    }

    public boolean conditions(Point i, Point j, Point k, Point a, Point b, Point c) {
        if (antiMirror(i,j,k,a,b,c))
            return similarity(i,j,k,a,b,c);
        return false;
    }

    public void continuingFor(int l1, int l2, List<Integer> returning, Point[] listaPunti1, Point[] listaPunti2, int i, int j, int k, int a, int b, int c, int group) {
        int aumento = 30;
        if (k+aumento<l1 && c+aumento<l2) {
            for (int K = k+aumento; K<l1; K+=aumento)
                if (returning.get(K) == 0)
                    for (int C = c+aumento; C<l2; C+=aumento)
                        if (returning.get(l1+C) == 0)
                            if (conditions(listaPunti1[i],listaPunti1[j],listaPunti1[K],listaPunti2[a],listaPunti2[b],listaPunti2[C])) {
                                returning.set(K,group);
                                returning.set(l1+C,group);
                                continuingFor(l1,l2,returning,listaPunti1,listaPunti2,i,j,K,a,b,C,group);
                            }
        }
    }

    public boolean easySimilarity(int i, int j, int k, int a, int b, int c) {
        int[] x = {getx(i),getx(j),getx(k)};
        int[] y = {getx(a),getx(b),getx(c)};
        Arrays.sort(x);
        Arrays.sort(y);
        return (x[0]*y[1] == y[0]*x[1] && x[0]*y[2] == y[0]*x[2] && x[1]*y[2] == y[1]*x[2]);
    }

    public int[] lineGroups(ArrayList<Integer> returning, ArrayList<Integer> esclusione,int l1,int l2,int[] lista1, int[] lista2, int index, int i, int group) {
        ArrayList<Integer> uno = new ArrayList<>();
        ArrayList<Integer> due = new ArrayList<>();

        if (i<l1 && !esclusione.contains(index)) {
            while (gety(lista1[i]) == index && i<l1-1) {
                uno.add(lista1[i]);
                i++;
            }
            if (gety(lista1[i]) == 100) uno.add(l1-1);

            int j = 0;
            for (int jndex = 1; jndex<101; jndex++) {
                if (!esclusione.contains(jndex)) {
                    due.clear();
                    while (gety(lista2[j]) == jndex && j<l2-1) {
                        due.add(lista2[j]);
                        j++;
                    }
                    if (gety(lista2[j]) == 100) uno.add(l2-1);

                    if (uno.size()>2 && due.size()>2) {
                        int aumento = 1;
                        for (int p = 0; p<uno.size()-2 * aumento; p += aumento)
                            for (int a = 0; a<due.size()-2 * aumento; a += aumento)
                                for (int q = p+aumento; q<uno.size()-aumento; q += aumento)
                                    for (int b = a+aumento; b<due.size()-aumento; b += aumento)
                                        for (int l = q+aumento; l<uno.size(); l += aumento)
                                            for (int c = b+aumento; c<due.size(); c += aumento)
                                                if (easySimilarity(uno.get(p),uno.get(q),uno.get(l),due.get(a),due.get(b),due.get(c))) {
                                                    group++;
                                                    returning.set(p,group);
                                                    returning.set(q,group);
                                                    returning.set(l,group);
                                                    returning.set(l1+a,group);
                                                    returning.set(l1+b,group);
                                                    returning.set(l1+c,group);
                                                    esclusione.add(index);
                                                    esclusione.add(jndex);
                                                    return new int[] {i,group};
                                                }
                    }
                }
            }
        } return new int[] {i,group};
    }

    public List<Integer> findGroups(List<Integer> firstPoints, List<Integer> secondPoints) {

        Point[] listaPunti1 = new Point[firstPoints.size() / 2];
        for (int i = 0; i<listaPunti1.length; i++)
            listaPunti1[i] = new Point(firstPoints.get(2 * i),firstPoints.get(2*i +1));

        Point[] listaPunti2 = new Point[secondPoints.size() / 2];
        for (int i = 0; i<listaPunti2.length; i++)
            listaPunti2[i] = new Point(secondPoints.get(2 * i),secondPoints.get(2*i +1));

        ArrayList<Integer> returning = new ArrayList<>();
        int l1 = listaPunti1.length;
        int l2 = listaPunti2.length;
        for (int i = 0; i<l1+l2; i++)
            returning.add(0);


        int[] lista1 = new int[l1];
        int[] lista2 = new int[l2];
        for (int i=0; i<l1; i++){ lista1[i] = listaPunti1[i].x + 1000*listaPunti1[i].y; }
        for (int i=0; i<l2; i++){ lista2[i] = listaPunti2[i].x + 1000*listaPunti2[i].y; }
        Arrays.sort(lista1);
        Arrays.sort(lista2);

        /** NON CI SONO PUNTI COINCIDENTI */
        // PUNTI COINCIDENTI
/*
        int[] esito = puntiCoincidenti(returning,listaPunti1,listaPunti2, lista1, lista2, l1, l2, 0, 0, 0,0);
        while (esito[0] != l1-1 && esito[1] != l2-1 )
            esito = puntiCoincidenti(returning,listaPunti1,listaPunti2, lista1, lista2, l1, l2, esito[0],esito[1],esito[2],esito[3]);

        int group = esito[2];
*/

        // LINE GROUP
        int[] esito = new int[2];
        ArrayList<Integer> esclusione = new ArrayList<>();
        int numeratore =0;
        for (int index=1; index<101; index++) {
            if (numeratore == 0) {
                numeratore++;
                esito = lineGroups(returning,esclusione,l1,l2,lista1,lista2,index,0,0);
            } else
                esito = lineGroups(returning,esclusione,l1,l2,lista1,lista2,index,esito[0],esito[1]);
        }

        int group = esito[1];
/*
        // FORZA BRUTA
        int aumento = 30;
        if (2*aumento<l1 && 2*aumento<l2) {
        for (int i = 0; i<l1-2*aumento; i+=aumento)
            if (returning.get(i)==0)
            for (int a = 0; a<l2-2*aumento; a+=aumento)
                if (returning.get(l1+a)==0)
                for (int j = i+aumento; j<l1-aumento; j+=aumento)
                    if (returning.get(j)==0)
                    for (int b = a+aumento; b<l2-aumento; b+=aumento)
                        if (returning.get(l1+b)==0)
                        for (int k = j+aumento; k<l1; k+=aumento)
                            if (returning.get(k) == 0)
                            for (int c = b+aumento; c<l2; c+=aumento)
                                if (returning.get(l1+c) == 0)
                                    if (conditions(listaPunti1[i],listaPunti1[j],listaPunti1[k],listaPunti2[a],listaPunti2[b],listaPunti2[c])) {
                                        group++;
                                        returning.set(i,group);
                                        returning.set(j,group);
                                        returning.set(k,group);
                                        returning.set(l1+a,group);
                                        returning.set(l1+b,group);
                                        returning.set(l1+c,group);
                                        continuingFor(l1,l2,returning,listaPunti1, listaPunti2, i,j,k,a,b,c, group);
                                        k = l1; c = l2;
                                        i++; j++; a++; b++;
                                    }
        }
*/
        int flag=0;
        int save = ++group;
        for (int i=0; i<l1; i++)
            if (returning.get(i)==0)
                if (flag < 2) {
                    flag++;
                    returning.set(i,group);
                } else {
                    flag = 1;
                    returning.set(i,++group);
                }

        flag = 0;
        group = save;
        for (int i=0; i<l2; i++)
            if (returning.get(l1+i)==0)
                if (flag < 2) {
                    flag++;
                    returning.set(l1+i,group);
                } else {
                    flag = 1;
                    returning.set(l1+i,++group);
                }

        return returning;
    }

}



/**     GROUP NUMBERS OUT OF RANGE                      */
/*      ArrayList<Integer> ret = new ArrayList<>();
        for (int k=0; k<2; k++) {
            for (int i = 1; i <=firstPoints.size(); i++) {
                ret.add(i);
            }
        }return ret;*/


/**     GROUP NUMBERS OUT OF RANGE DAMN!                */
/*      ArrayList<Integer> ret = new ArrayList<>();
        int count = 0;

        if (firstPoints.size() % 4 == 0) {
            while (count < 2) {
                for (int i = 0; i<firstPoints.size() / 4; i++) {
                    ret.add(i);
                    ret.add(i);
                } count++;
            }
        } else {
            while (count < 2) {
                for (int i = 0; i<firstPoints.size() / 2; i++) {
                    ret.add(i);
                    ret.add(i);
                }
                ret.add(firstPoints.size() / 2+1);
                count++;
            }
        } return ret;*/


/**     SCORE = 50.01                                   */
/*      package IMCbids64;
        import java.util.*;

        // CLASSE PUNTO
        class Point{
            public int x, y;
            public Point(int a, int b) {
                this.x = a;
                this.y = b;
            } }

        // CLASSE GRUPPI DI PUNTI
        public class PointGroups {
            public PointGroups() {}

            public List<Integer> findGroups(List<Integer> firstPoints, List<Integer> secondPoints) {

                Point[] listaPunti1 = new Point[firstPoints.size() / 2];
                for (int i = 0; i<listaPunti1.length; i++)
                    listaPunti1[i] = new Point(firstPoints.get(2 * i),firstPoints.get(2*i +1));

                Point[] listaPunti2 = new Point[secondPoints.size() / 2];
                for (int i = 0; i<listaPunti2.length; i++)
                    listaPunti2[i] = new Point(secondPoints.get(2 * i),secondPoints.get(2*i +1));

                ArrayList<Integer> returning = new ArrayList<>();
                int l1 = listaPunti1.length;
                int l2 = listaPunti2.length;
                int group = 0;
                for (int i = 0; i<l1+l2; i++)
                    returning.add(0);

                int flag=0;
                int save = ++group;
                for (int i=0; i<l1; i++)
                    if (returning.get(i)==0)
                        if (flag < 2) {
                            flag++;
                            returning.set(i,group);
                        } else {
                            flag = 1;
                            returning.set(i,++group);
                        }

                flag = 0;
                group = save;
                for (int i=0; i<l2; i++)
                    if (returning.get(l1+i)==0)
                        if (flag < 2) {
                            flag++;
                            returning.set(l1+i,group);
                        } else {
                            flag = 1;
                            returning.set(l1+i,++group);
                        }

                return returning;
            }

        }*/
