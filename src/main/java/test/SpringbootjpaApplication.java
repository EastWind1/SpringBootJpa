package test;

import jdk.nashorn.internal.runtime.regexp.RegExp;
import jdk.nashorn.internal.runtime.regexp.RegExpMatcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
@EnableCaching
public class SpringbootjpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootjpaApplication.class, args);
    }
}
