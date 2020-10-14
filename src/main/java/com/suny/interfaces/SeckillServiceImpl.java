package com.suny.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.apache.taglibs.standard.tag.common.core.RedirectSupport;
import org.slf4j.Logger;

import com.suny.SeckillStatEnum.SeckillStatEnum;
import com.suny.dao.RedisDao;
import com.suny.dao.SeckillMapper;
import com.suny.dao.SuccessKilledMapper;
import com.suny.dto.Exposer;
import com.suny.dto.SeckillExecution;
import com.suny.entity.Seckill;
import com.suny.entity.SuccessKilled;
import com.suny.exception.RepeatKillException;
import com.suny.exception.SeckillCloseException;
import com.suny.exception.SeckillException;


@Service
public class SeckillServiceImpl implements SeckillService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final String salt = "thisIsASaltValue";
	
	@Autowired
	private SeckillMapper seckillMapper;
	@Autowired
	private SuccessKilledMapper successKilledMapper;
	@Autowired
	private RedisDao redisDao;
	
	
	@Override
	public List<Seckill> getSeckillList() {
		return seckillMapper.queryAll(0, 4);
	}

	@Override
	public Seckill getById(long seckillId) {
		return seckillMapper.queryById(seckillId);
	}
	
	private String getMD5(long seckillId) {
		String base = seckillId + "/" + salt;
		return DigestUtils.md5DigestAsHex(base.getBytes());
	}

	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		// read data from cache
		Seckill seckill = redisDao.getSeckill(seckillId);
		// read data from db
		if(seckill == null) {
			seckill = seckillMapper.queryById(seckillId);
			if (seckill == null) {
				return new Exposer(false,seckillId);
			}else {
				// Add seckill to cache
				redisDao.putSeckill(seckill);
			}
		}
		
		LocalDateTime startTime = seckill.getStartTime();
		LocalDateTime endTime = seckill.getEndTime();
		LocalDateTime nowTime = LocalDateTime.now();
		
		if (nowTime.isAfter(startTime) && nowTime.isBefore(endTime)) {
			String md5 = getMD5(seckillId);
			return new Exposer(true, md5, seckillId);
		}
		return new Exposer(false, seckillId, nowTime, startTime, endTime);
	}

	@Transactional
	@Override
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		if(md5 == null || !md5.equals(getMD5(seckillId))){
			logger.error("seckill data is been rewrite");
			throw new SeckillException("seckill data rewrite");
		}
		LocalDateTime nowTime = LocalDateTime.now();
		try {
			int reduceNumber = seckillMapper.reduceNumber(seckillId, nowTime);
			if (reduceNumber <= 0) {
				logger.warn("The db data can not be updated, since the num is 0");
				throw new SeckillCloseException("seckill is closed");
			} else {
				// Add a seckill success recoder	
				int insertCount = successKilledMapper.insertSuccessKilled(seckillId, userPhone);
				if(insertCount <= 0){ // repeat seckill
					throw new RepeatKillException("seckill repeated");
				} else {
					SuccessKilled successKilled = successKilledMapper.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
				}
			}
		} catch (SeckillCloseException | RepeatKillException e1) {
			throw e1;
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new SeckillException("seckill inner error: " + e.getMessage());
		}
	}

}
