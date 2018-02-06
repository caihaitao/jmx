package com.cc.miaosha;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MiaoshaApplicationTests {

	@Test
	public void contextLoads() {
		//String str = "*11aaa";
		List<String> lst = Arrays.asList("aaaaaaa","*12312a#","111111","1********a","你好我是java11","111AAAAA");
			String regEx = "(?=.*\\d)(?=.*[a-z])[\\x20-\\x7f]*";
			Pattern pattern = Pattern.compile(regEx,Pattern.CASE_INSENSITIVE);
			lst.forEach(s -> {
				Matcher m =pattern.matcher(s);
				System.out.println(m.matches());
			});
			/*Matcher matcher = pattern.matcher(str);
			boolean rs = matcher.matches();
			System.out.println(rs);*/
	}

}
