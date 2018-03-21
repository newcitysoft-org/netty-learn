package com.newcitysoft.study.builder;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/21 11:01
 */
public class Student {
    private String name;
    private String gender;
    private String stuId;
    private String location;
    private int age;
    private String major;

    public static class Builder {
        private final String name;
        private final String gender;

        private String stuId;
        private String location;
        private int age;
        private String major;

        public Builder(String name, String gender) {
            this.name = name;
            this.gender = gender;
        }

        public Builder stuId(String id) {
            this.stuId = id;
            return this;
        }

        public Builder location(String loc) {
            this.location = loc;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder major(String major) {
            this.major = major;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }

    private Student(Builder builder) {
        this.name = builder.name;
        this.gender = builder.gender;
        this.stuId = builder.stuId;
        this.location = builder.location;
        this.age = builder.age;
        this.major = builder.major;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", stuId='" + stuId + '\'' +
                ", location='" + location + '\'' +
                ", age=" + age +
                ", major='" + major + '\'' +
                '}';
    }
}
