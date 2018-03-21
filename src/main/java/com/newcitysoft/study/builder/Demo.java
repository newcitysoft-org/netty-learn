package com.newcitysoft.study.builder;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/21 11:09
 */
public class Demo {
    public static void main(String[] args) {
        Student student = new Student.Builder("tianlixin", "male")
                            .age(26)
                            .location("北京")
                            .major("软件工程")
                            .stuId("120402033")
                            .build();

        System.out.println(student.toString());
    }
}
