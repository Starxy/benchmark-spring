package cc.starxy.benchmarkspring;

import cn.hutool.jwt.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class BenchmarkSpringApplicationTests {

    @Test
    void contextLoads() {
        Map<String, Object> map = new HashMap<>();
        map.put("amework.boot.autoconfigure.Sprin", "org.springframework.boot.SpringApplication;");
        map.put("ngframework.web.bind.annotation.Po", "package benchmark.springboot;");
        System.out.println(JWTUtil.createToken(map, "token".getBytes()));
        String s = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhbWV3b3JrLmJvb3QuYXV0b2NvbmZpZ3VyZS5TcHJpbiI6Im9yZy5zcHJpbmdmcmFtZXdvcmsuYm9vdC5TcHJpbmdBcHBsaWNhdGlvbjsiLCJuZ2ZyYW1ld29yay53ZWIuYmluZC5hbm5vdGF0aW9uLlBvIjoicGFja2FnZSBiZW5jaG1hcmsuc3ByaW5nYm9vdDsifQ.stW_THMKovex8CTYYssuhFsfKSi9KblUo_u2MoTMoyk";
    }

}
