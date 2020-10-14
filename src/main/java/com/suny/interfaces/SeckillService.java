package com.suny.interfaces;

import java.util.List;

import com.suny.dto.Exposer;
import com.suny.dto.SeckillExecution;
import com.suny.entity.Seckill;
import com.suny.exception.RepeatKillException;
import com.suny.exception.SeckillCloseException;
import com.suny.exception.SeckillException;

public interface SeckillService {
	/*
	 * get all seckill recorder 
	 * */
	List<Seckill> getSeckillList();	
	
	Seckill getById(long seckillId);
	
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	   * 执行秒杀操作,有可能是失败的,失败我们就抛出异常
	   * @param seckillId  秒杀的商品ID
	   * @param userPhone 手机号码
	   * @param md5   md5加密值
	   * @return   根据不同的结果返回不同的实体信息
	   */
	  SeckillExecution executeSeckill(long seckillId,long userPhone,String md5)throws SeckillException, RepeatKillException, SeckillCloseException;
}
