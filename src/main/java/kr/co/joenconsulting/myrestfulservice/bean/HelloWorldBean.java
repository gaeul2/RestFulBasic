package kr.co.joenconsulting.myrestfulservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor//이거 쓸거면 생성자 없어야함.
public class HelloWorldBean {
    private String message;

//    public HelloWorldBean(String message) {
//        this.message = message;
//    }
}
