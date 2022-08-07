import java.util.*;
import java.util.Collections;

public class Graph {
    public static Scanner input = new Scanner(System.in);
    public static ArrayList<Person_info> p = new ArrayList<Person_info>();

    public static boolean Check_inArray(String name, ArrayList<Person_info> arr) {
        for (Person_info person : arr) {
            if (person.esm.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static int Find_Loc(String name, ArrayList<Person_info> exp) {
        for (int i = 0; i < exp.size(); i++) {
            if (exp.get(i).esm.equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public static void Checking(){
        int n = Integer.parseInt(input.nextLine());
        for (int i = 0; i < n; i++) {
            String[] in = input.nextLine().split(" ");
            Person_info son = new Person_info(in[0]);
            Person_info father = new Person_info(in[1]);
            son.jadeBozorg = father.esm;

            if (Check_inArray(father.esm, p) && !Check_inArray(son.esm, p)) {
                int loc = Find_Loc(father.esm, p);
                p.get(loc).children.add(son.esm);
                p.add(son);
            } else if (!Check_inArray(father.esm, p) && Check_inArray(son.esm, p)) {
                int loc = Find_Loc(son.esm, p);
                p.get(loc).jadeBozorg = father.esm;
                father.children.add(son.esm);
                p.add(father);
            } else if (Check_inArray(father.esm, p) && Check_inArray(son.esm, p)) {
                int f_loc = Find_Loc(father.esm, p);
                int s_loc = Find_Loc(son.esm, p);
                p.get(f_loc).children.add(son.esm);
                p.get(s_loc).jadeBozorg = father.esm;
            } else {
                father.children.add(son.esm);
                p.add(father);
                p.add(son);
            }
        }
    }

    public static void Sorting(){

        for (Person_info person : p) {
            Collections.sort(person.children);
        }

        for (int i = 0; i < p.size(); i++) {
            for (int j = 0; j < p.size() - i; j++) {
                if (p.get(i).esm.compareTo(p.get(j).esm) > 0) {
                    Collections.swap(p, i, j);
                }
            }
        }
    }

    public static void Making_Graph(){
        ArrayList<Person_info> sortedPerson = new ArrayList<Person_info>();
        ArrayList<Person_info> HelpingSortedPerson = new ArrayList<Person_info>();

        for (Person_info person : p) {
            if (person.jadeBozorg.equals("alone")) {
                sortedPerson.add(person);
            }
        }

        while (sortedPerson.size() != 0) {
            for (Person_info person1 : sortedPerson) {
                if (person1.children.size() > 0) {
                    System.out.print(person1.esm + "->");
                } else {
                    System.out.print(person1.esm);
                }
                for (String person2 : person1.children) {
                    System.out.print(person2 + " ");
                    int location = Find_Loc(person2, p);
                    HelpingSortedPerson.add(p.get(location));
                }
                System.out.println();
            }
            while (sortedPerson.size() != 0) {
                sortedPerson.remove(0);
            }
            for (Person_info person : HelpingSortedPerson) {
                sortedPerson.add(person);
            }
            while (HelpingSortedPerson.size() != 0) {
                HelpingSortedPerson.remove(0);
            }
        }
    }

    public static void main(String[] args) {
        Checking();
        Sorting();
        Making_Graph();
    }
}

class Person_info {

    public String jadeBozorg = "alone";
    public ArrayList<String> children = new ArrayList<String>();
    public String esm;

    Person_info(String n) {
        this.esm = n;
    }
}