package com.charles.common.hash;

import java.util.HashSet;
import java.util.Iterator;

import static com.charles.utils.LineSeparators.hyphenSeparator;

/**
 * 验证HashSet中的对象实现和不实现hashcode和equals方法的区别
 */
public class Student {
    private static class RawStudent {
        private int num;
        private String name;

        RawStudent(int num, String name) {
            this.num = num;
            this.name = name;
        }

        public String toString() {
            return num + ":" + name;
        }
    }

    private static class NewStudent {
        private int num;
        private String name;

        NewStudent(int num, String name) {
            this.num = num;
            this.name = name;
        }

        public int hashCode() {
            return num * name.hashCode();
        }

        public boolean equals(Object o) {
            NewStudent s = (NewStudent) o;
            return num == s.num && name.equals(s.name);
        }

        public String toString() {
            return num + ":" + name;
        }
    }

    private static HashSet rawSet() {
        HashSet<RawStudent> hs = new HashSet<>();
        hs.add(new RawStudent(1, "zhangsan"));
        hs.add(new RawStudent(2, "lisi"));
        hs.add(new RawStudent(3, "wangwu"));
        hs.add(new RawStudent(1, "zhangsan"));
        return hs;
    }

    private static HashSet newSet() {
        HashSet<NewStudent> hs = new HashSet<>();
        hs.add(new NewStudent(1, "zhangsan"));
        hs.add(new NewStudent(2, "lisi"));
        hs.add(new NewStudent(3, "wangwu"));
        hs.add(new NewStudent(1, "zhangsan"));
        return hs;
    }

    private static void print(HashSet set) {
        Iterator it = set.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public static void main(String[] args) {
        print(rawSet());
        hyphenSeparator();
        print(newSet());
    }
}