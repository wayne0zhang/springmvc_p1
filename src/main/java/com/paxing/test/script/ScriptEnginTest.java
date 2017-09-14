package com.paxing.test.script;

import javax.script.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ScriptEnginTest {
    public static void main(String[] args) throws ScriptException, NoSuchMethodException, FileNotFoundException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        // 建立上下文环境
        Bindings bindings = engine.createBindings();
        bindings.put("factor", 1);
        // 绑定上下文
        engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
        Scanner input = new Scanner(System.in);
        while (input.hasNextInt()) {
            int first = input.nextInt();
            int sec = input.nextInt();
            System.out.println("输入参数是：" + first + "," + sec);
            //执行js代码
            engine.eval(new FileReader("F:\\model.js"));
            //是否可调用方法
            if (engine instanceof Invocable) {
                Invocable in = (Invocable) engine;
                // 执行特定函数
                Double result = (Double) in.invokeFunction("formula", first, sec);
                System.out.println("运算结果是：" + result.intValue());
            }
        }
    }
}
