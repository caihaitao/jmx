package com.cc.miaosha;

import com.cc.miaosha.model.CustomerRequest;
import com.cc.miaosha.service.MiaoshaService;
import com.cc.miaosha.service.MiaoshaTask;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * TODO 类的描述
 *
 * @author 蔡海涛
 * @createTime 2018-02-05 11:59:29
 */
public class MSTest extends BaseJunitTest {
    @Resource
    private MiaoshaService redisMiaoshaImpl;

    private int totalConcurrent;
    private static  final String  totalNum = "900";

    private static final String IPHONEX = "IPhoneX";

    @Resource
    private RedisTemplate<Object,Object> redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    private static final String MS_CUSTOMER = "MS_CUSTOMER";

    private BlockingQueue<Runnable> linkedList = new LinkedBlockingQueue<>(1024);

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,25,1L, TimeUnit.MINUTES,linkedList);
    List<Future<Integer>> futures = new ArrayList<>();

    @Before
    public void init() {
        totalConcurrent = 1000;
        stringRedisTemplate.opsForValue().set(IPHONEX,totalNum);

    }

    @Test
    public void testMiaosha() throws InterruptedException, ExecutionException {
        for(int i=0;i<totalConcurrent;i++) {
            redisTemplate.opsForList().leftPush(MS_CUSTOMER,new CustomerRequest(i,IPHONEX,4));

            futures.add(threadPoolExecutor.submit(new MiaoshaTask(redisTemplate,redisMiaoshaImpl,MS_CUSTOMER)));
        }

        int soldPhone = 0;
        int totalFailed = 0;
        for(Future<Integer> future : futures) {
            Integer val = future.get();
            if(val != null) {
                if(val == 0) {
                    totalFailed ++;
                } else {
                    soldPhone += val;
                }
            } else {
                totalFailed ++;
            }
        }
        System.out.println(IPHONEX+" has been sold :"+ soldPhone);
        System.out.println("total faied :"+totalFailed+"===========total successed:"+(soldPhone/4));
    }
}
