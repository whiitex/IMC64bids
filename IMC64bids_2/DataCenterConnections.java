package IMC64bids_2;

import java.util.*;

public class DataCenterConnections {
    private enum MF {MICROWAVE, FIBER}
    private static Object mock = new Object();

    public int findShortestDistance(int n,
            List<List<Integer>> microwaveConnections,
            List<List<Integer>> fiberConnections) {

        // DECLARATIONS
        Graph graph = new Graph();
        Map<String,Boolean> visited = new HashMap<>();
        Map<String, MF> queue = new LinkedHashMap<>();
        Map<String,Integer> childQueue3 = new HashMap<>();
        Map<String,Integer> childQueue2 = new HashMap<>();
        String TO = Integer.toString(n);

        // UTILITY
        for (List<Integer> list: microwaveConnections) {
            String a = list.get(0).toString(); String b = list.get(1).toString();
            graph.add(a,b,MF.MICROWAVE); //micro
        } for (List<Integer> list: fiberConnections) {
            String a = list.get(0).toString(); String b = list.get(1).toString();
            graph.add(a, b,MF.FIBER); // fiber
        }


        /** FOLLOWING BASIC PATH: micro-fiber-micro-fiber */
        Node.get("1").setLevel(0);
        queue.put("1",MF.FIBER);
        Node nodeTo = null;
        boolean Continue = true;
        while (queue.keySet().size()>0 && Continue) {
            HashSet<String> lista = new HashSet<>(queue.keySet());

            for (String nexxt : lista) {
                Node next = Node.get(nexxt);

                // BASE-CASE
                if (next.key.equals(TO)) {
                    nodeTo = next;
                    Continue = false;
                    break;
                }

                for (Node adj : graph.adjacents(next.key)) {
                    if (visited.get(adj.key) == null) {

                        /** ARRIVA DA UN MICRO */
                        if (queue.get(next.key) == MF.MICROWAVE) {

                            //  collegamento in FIBER
                            if (queue.get(adj.key) == null && next.Micro_Fiber.get(adj.key) == MF.FIBER) {

                                queue.put(adj.key,MF.FIBER);
                                if (adj.addParent(next)) {
                                    childQueue3.remove(adj.key);
                                    childQueue2.remove(adj.key);
                                }
                            }

                            // REPLACE colleg. in MICRO nella queue
                            if (queue.get(adj.key) == MF.MICROWAVE && !lista.contains(adj.key) && next.Micro_Fiber.get(adj.key) == MF.FIBER) {

                                queue.replace(adj.key,MF.FIBER);
                            }

                            //  collegamento in MICRO
                            else if (queue.get(adj.key) == null && next.Micro_Fiber.get(adj.key) == MF.MICROWAVE) {
                                boolean glag = false;
                                for (Node test : graph.adjacents(next.key)) {
                                    if (next.Micro_Fiber.get(test.key) == MF.FIBER && test.level==next.level)
                                        glag = true;
                                }
                                if (glag && adj.addParent2(next)) {
                                    if (!childQueue2.containsKey(adj.key))
                                        childQueue2.put(adj.key,0);
                                }


                                boolean flag = false;
                                for (Node test : graph.adjacents(next.key)) {
                                    if (next.Micro_Fiber.get(test.key) == MF.FIBER)
                                        flag = true;
                                }
                                if (flag && adj.addParent3(next)) {
                                    if (!childQueue3.containsKey(adj.key))
                                        childQueue3.put(adj.key,0);
                                }

                            }
                        }

                        /** ARRIVA DA UN FIBER */
                        else {

                            //  collegamento in FIBER
                            if (queue.get(adj.key) == null && next.Micro_Fiber.get(adj.key) == MF.FIBER) {

                                queue.put(adj.key,MF.FIBER);
                                if (adj.addParent(next)) {
                                    childQueue3.remove(adj.key);
                                    childQueue2.remove(adj.key);
                                }
                            }

                            // REPLACE colleg. in MICRO nella queue
                            if (queue.get(adj.key) == MF.MICROWAVE && !lista.contains(adj.key) && next.Micro_Fiber.get(adj.key) == MF.FIBER) {

                                queue.replace(adj.key,MF.FIBER);
                            }

                            //  collegamento in MICRO
                            else if (queue.get(adj.key) == null && next.Micro_Fiber.get(adj.key) == MF.MICROWAVE) {
                                queue.put(adj.key,MF.MICROWAVE);
                                if (adj.addParent(next)) {
                                    childQueue3.remove(adj.key);
                                    childQueue2.remove(adj.key);
                                }
                            }
                        }
                    }
                }
                visited.put(next.key,true);
                queue.remove(next.key);
            }

            Iterator<String> it = childQueue3.keySet().iterator();
            while (it.hasNext()) {
                String str = it.next();
                childQueue3.replace(str,childQueue3.get(str)+1);
                if (childQueue3.get(str) == 3) {
                    queue.put(str,MF.MICROWAVE);
                    it.remove();
                } }

            it = childQueue2.keySet().iterator();
            while (it.hasNext()) {
                String str = it.next();
                childQueue2.replace(str,childQueue2.get(str)+1);
                if (childQueue2.get(str) == 2) {
                    queue.put(str,MF.MICROWAVE);
                    it.remove();
                } }

        }

        return (nodeTo == null) ? -1 : nodeTo.level;

    }

    /** NODE-CLASS  NODE-CLASS  NODE-CLASS */
    private static class Node {
        private static Map<String, Node> map = new HashMap<>();
        LinkedList<Node> parents = new LinkedList<>();
        final String key;
        int level = -1;
        private HashMap<String,MF> Micro_Fiber = new HashMap<>(); // TRUE -> micro; FALSE -> fiber

        private Node(String key, int level) {
            this.key = key;
            this.level = level;
        }

        public static Node get(String key) {
            // inner interning of nodes
            Node res = map.get(key);
            if (res == null) {
                res = new Node(key, -1);
                map.put(key, res);
            }
            return res;
        }

        public boolean addParent(Node n) {
            // forbidding child parenting
            if (n.level >= level && this.level != -1)
                return false;

            this.level = n.level + 1;
            parents.add(n);
            return true;
        }

        public boolean addParent2(Node n) {
            // forbidding worsing
            if (n.level+1 >= level && this.level != -1)
                return false;

            this.level = n.level + 2;
            parents.add(n);
            return true;
        }

        public boolean addParent3(Node n) {
            // forbidding worsing
            if (n.level+2 >= level && this.level != -1)
                return false;

            this.level = n.level + 3;
            parents.add(n);
            return true;
        }

        public void setType(String str, MF type){
            if (Micro_Fiber.get(str)==null)
                Micro_Fiber.put(str,type);
            else Micro_Fiber.replace(str,type); // replace only if type_0 = MF.MICROWAVE
        }

        public boolean equals(Object n) { return key.equals(((Node) n).key); }

        public void setLevel(int l) { this.level = l; }
    }


    /** GRAPH-CLASS  GRAPH-CLASS  GRAPH-CLASS */
    private static class Graph {
        Map<String, Set<Node>> adjacencyList = new HashMap<>(); // nodo identificato da una stringa
        public void add(String from, String to, MF type) {
            addEdge(to, from, type);
            addEdge(from, to, type);
        }

        private void addEdge(String from, String to, MF type) { // funzione interna
            Set<Node> list = adjacencyList.get(from);

            if (list == null) { // se lista null -> creala e mettila nella HashMap
                list = new LinkedHashSet<>();
                adjacencyList.put(from, list);
            }
            list.add(Node.get(to));

            Node.get(from).setType(to, type);

        }

        public Set<Node> adjacents(String v) {
            Set<Node> nodes = adjacencyList.get(v);
            return nodes == null ? Collections.emptySet() : nodes;
        }
    }


}
