package com.darren.java8.optional;

import com.darren.java8.lambda.Employee;
import org.junit.Test;

import java.util.Optional;

/**
 * @author Darren
 * @date 2018/4/16
 */
public class OptionalTest {
    /**
     * 一、Optional 容器类：用于尽量避免空指针异常
     * Optional.of(T t) : 创建一个 Optional 实例
     * Optional.empty() : 创建一个空的 Optional 实例
     * Optional.ofNullable(T t):若 t 不为 null,创建 Optional 实例,否则创建空实例
     * isPresent() : 判断是否包含值
     * orElse(T t) :  如果调用对象包含值，返回该值，否则返回t
     * orElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回 s 获取的值
     * map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回 Optional.empty()
     * flatMap(Function mapper):与 map 类似，要求返回值必须是Optional
     */

    @Test
    public void optionalInAction() {
        String godnessName = acquireGodnessName(Optional.empty());
        System.out.println(godnessName);
    }

    private String acquireGodnessName(Optional<Man> man) {
        return man.orElse(new Man(Optional.ofNullable(null)))
                .getGodness()
                .orElse(new Godness("caroline"))
                .getName();
    }

    @Test
    public void methodTest() {

        Optional<Godness> of = Optional.of(new Godness());
        System.out.println(of.get());
        Optional<Godness> empty = Optional.empty();
        Optional<Godness> nullAble = Optional.ofNullable(new Godness());
        System.out.println(nullAble.get());
        if (nullAble.isPresent()) {
            System.out.println("optional非空");
        }
        Godness sharon = empty.orElse(new Godness("sharon"));
        System.out.println(sharon.getName());
        Godness godness = empty.orElseGet(Godness::new);
        System.out.println(godness);

    }

    @Test
    public void optionalMethodTest() {
        Optional<Employee> optional = Optional.ofNullable(null);
        System.out.println(optional.orElse(new Employee()));

    }
}
