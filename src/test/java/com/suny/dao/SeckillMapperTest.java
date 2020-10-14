package com.suny.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.suny.entity.Seckill;

import ch.qos.logback.core.util.DatePatternToRegexUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:ApplicationContext.xml"})
public class SeckillMapperTest {
	@Resource
	private SeckillMapper seckillMapper;
	
	@Test
	   public void reduceNumber() throws Exception {
	       long seckillId=1000;
	       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM:SS"); 
	       LocalDateTime localDateTime=LocalDateTime.now();
	    //   int i = seckillMapper.reduceNumber(seckillId, localDateTime.format(formatter));
	       try {
	    	   int i = seckillMapper.reduceNumber(seckillId, localDateTime);
	       } catch (Exception e) {
	    	   System.out.println(e);
	       }
	       //int i = seckillMapper.reduceNumber(seckillId, "2020-09-13 00:00:00");
	       System.out.println(localDateTime.format(formatter));
	   }

//	   @Test
	   public void queryById() throws Exception {
	       long seckillId = 1000;
	       Seckill seckill = seckillMapper.queryById(seckillId);
	       System.out.println(seckill.toString());
	   }

//	   @Test
	   public void queryAll() throws Exception {
	       List<Seckill> seckills = seckillMapper.queryAll(0, 100);
	       for (Seckill seckill : seckills) {
	           System.out.println(seckill.toString());
	       }
	   }
}
