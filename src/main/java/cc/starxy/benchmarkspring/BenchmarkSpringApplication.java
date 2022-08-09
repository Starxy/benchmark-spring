package cc.starxy.benchmarkspring;

import cc.starxy.benchmarkspring.aspect.LogOperation;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DONG Jixing
 */
@SpringBootApplication
public class BenchmarkSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(BenchmarkSpringApplication.class, args);
    }

    @Slf4j
    @RestController
    public static class BenchmarkController {
        @GetMapping("/")
        public String root() {
            log.info(DateUtil.now());
            return "root";
        }

        @GetMapping("/user/{id}")
        public Integer userId(@PathVariable Integer id) {
            return id;
        }

        @GetMapping("/user")
        public void user() {
            log.info(DateUtil.now());
        }

        @LogOperation
        @GetMapping("/user2")
        public void user2() {
            log.info(DateUtil.now());
        }

    }

}
