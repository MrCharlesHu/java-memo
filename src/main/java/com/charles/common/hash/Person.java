package com.charles.common.hash;

import java.util.HashSet;
import java.util.Set;

import static com.charles.utils.LineSeparators.hyphenSeparator;
import static com.charles.utils.LineSeparators.startSeparator;

/**
 * 验证当Hashcode一样时会调用equals方法
 */
public class Person {
    private int age;
    private int sex;    // 0：男，1：女
    private String name;
    private final int PRIME = 37;

    Person(int age, int sex, String name) {
        this.age = age;
        this.sex = sex;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 省略getter、setter方法
     **/
    @Override
    public int hashCode() {
        System.out.println("调用hashCode方法...........");
        int hashResult = 1;
        hashResult = (hashResult + Integer.valueOf(age).hashCode() + Integer.valueOf(sex).hashCode()) * PRIME;
        hashResult = PRIME * hashResult + ((name == null) ? 0 : name.hashCode());
        System.out.println("name:" + name + " hashCode:" + hashResult);
        return hashResult;
    }

    /**
     * 重写hashCode()
     */
    public boolean equals(Object obj) {
        System.out.println("调用equals方法...........");
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Person person = (Person) obj;
        if (getAge() == person.getAge() && getSex() == person.getSex() && getName().equals(person.getName())) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Set<Person> set = new HashSet<>();
        Person p1 = new Person(11, 1, "张三");
        Person p2 = new Person(12, 1, "李四");
        Person p3 = new Person(11, 1, "张三");
        Person p4 = new Person(11, 1, "李四");
        //只验证p1、p3
        System.out.println("p1 == p3? :" + (p1 == p3));
        System.out.println("p1.equals(p3)?:" + p1.equals(p3));
        startSeparator();
        set.add(p1);
        hyphenSeparator();
        set.add(p2);
        hyphenSeparator();
        set.add(p3);
        hyphenSeparator();
        set.add(p4);
        hyphenSeparator();
        System.out.println("set.size()=" + set.size());
    }
}