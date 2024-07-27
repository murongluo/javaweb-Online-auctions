package com.example.demopmxt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    Integer code;
    String msg;
    Object data;
    public static Result success(Object data){
        return new Result(1,"sucess",data);
    }
    public static Result success(){
        return new Result(1,"sucess",null);
    }
    public static Result error(String msg){
        return new Result(0,msg,null);
    }

}
